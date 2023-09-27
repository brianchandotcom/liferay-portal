/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

const ACTION_COPY_PAGE = 'copy-page';
const ACTION_DELETE = 'delete';
const ENTER_KEYCODE = 13;
const ROOT_ITEM_ID = '0';
const NOT_DROPPABLE_TYPES = ['url', 'link_to_layout'];

export default function PagesTree({
	config,
	isPrivateLayoutsTree,
	items,
	selectedLayoutId,
	selectedLayoutPath,
}) {
	const {
		isLayoutSetPrototype,
		loadMoreItemsURL,
		maxPageSize,
		moveItemURL,
		namespace,
	} = config;

	const onLoadMore = useCallback(
		(item) => {
			if (!item.hasChildren) {
				return Promise.resolve({
					cursor: null,
					items: null,
				});
			}

			const cursor = item.children
				? Math.floor(item.children.length / maxPageSize)
				: 0;

			return fetch(loadMoreItemsURL, {
				body: Liferay.Util.objectToURLSearchParams({
					[`${namespace}parentLayoutId`]: item.layoutId,
					[`${namespace}privateLayout`]: isPrivateLayoutsTree,
					[`${namespace}redirect`]:
						window.location.pathname + window.location.search,
					[`${namespace}selPlid`]: item.plid,
					[`${namespace}start`]: cursor * maxPageSize,
				}),
				method: 'post',
			})
				.then((response) => response.json())
				.then(({hasMoreElements, items: nextItems}) => ({
					cursor: hasMoreElements ? cursor + 1 : null,
					items: nextItems,
				}))
				.catch(() => openErrorToast());
		},
		[isPrivateLayoutsTree, loadMoreItemsURL, maxPageSize, namespace]
	);

	const onItemMove = useCallback(
		(item, parentItem, {next: priority}) => {
			if (NOT_DROPPABLE_TYPES.includes(parentItem.type)) {
				return false;
			}

			return fetch(moveItemURL, {
				body: Liferay.Util.objectToURLSearchParams({
					parentPlid: parentItem.plid,
					plid: item.plid,
					priority,
				}),
				method: 'post',
			}).catch(() => openErrorToast());
		},
		[moveItemURL]
	);

	const [expandedKeys, setExpandedKeys] = useState(selectedLayoutPath);

	useEffect(() => {
		const activeElement = document.querySelector(
			'.pages-tree .treeview-link.active'
		);

		if (activeElement) {
			activeElement.scrollIntoView({
				behavior: 'auto',
				block: 'center',
				inline: 'center',
			});
		}
	}, []);

	return (
		<div className="mx-3 pages-tree">
			<ClayTreeView
				defaultItems={items}
				displayType="dark"
				dragAndDrop={!config.stagingEnabled}
				expandedKeys={new Set(expandedKeys)}
				onExpandedChange={(keys) => {
					setExpandedKeys(Array.from(keys));
				}}
				onItemMove={onItemMove}
				onLoadMore={onLoadMore}
				selectionMode={null}
				showExpanderOnHover={false}
			>
				{(item, selection, expand, load) => (
					<TreeItem
						config={config}
						expand={expand}
						isLayoutSetPrototype={isLayoutSetPrototype}
						item={item}
						load={load}
						namespace={namespace}
						selectedLayoutId={selectedLayoutId}
					/>
				)}
			</ClayTreeView>
		</div>
	);
}

PagesTree.propTypes = {
	config: PropTypes.object.isRequired,
	isPrivateLayoutsTree: PropTypes.bool.isRequired,
	items: PropTypes.array.isRequired,
	selectedLayoutId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

function TreeItem({
	config,
	expand,
	isSiteTemplate,
	item,
	load,
	namespace,
	selectedLayoutId,
}) {
	const stackAnchorRef = useRef(null);
	const itemAnchorRef = useRef(null);

	const warningMessage = isSiteTemplate
		? Liferay.Language.get(
				'there-is-a-page-with-the-same-friendly-url-in-a-site-using-this-site-template'
		  )
		: Liferay.Language.get(
				'there-is-a-page-with-the-same-friendly-url-in-the-site-template'
		  );

	return (
		<ClayTreeView.Item
			actions={
				!config.stagingEnabled &&
				item.actions && (
					<ClayDropDownWithItems
						items={normalizeActions(item.actions, namespace)}
						renderMenuOnClick
						trigger={
							<ClayButtonWithIcon
								className="component-action quick-action-item"
								displayType={null}
								size="sm"
								symbol="ellipsis-v"
							/>
						}
					/>
				)
			}
		>
			<ClayTreeView.ItemStack
				active={selectedLayoutId === item.id ? 'true' : null}
				draggable={item.id !== ROOT_ITEM_ID}
				onKeyDown={(event) => {
					if (event.keyCode === ENTER_KEYCODE && item.regularURL) {
						stackAnchorRef.current.click();
					}
				}}
			>
				{item.icon && <ClayIcon symbol={item.icon} />}

				<div className="align-items-center d-flex pl-2">
					{item.regularURL ? (
						<a
							className="flex-grow-1 text-decoration-none text-truncate w-100"
							data-tooltip-floating="true"
							href={item.regularURL}
							ref={stackAnchorRef}
							tabIndex="-1"
							target={item.target}
							title={item.name}
						>
							{item.name}
						</a>
					) : (
						<span>{item.name}</span>
					)}
				</div>
			</ClayTreeView.ItemStack>

			<ClayTreeView.Group items={item.children}>
				{(item) => (
					<ClayTreeView.Item
						actions={
							!config.stagingEnabled &&
							item.actions && (
								<ClayDropDownWithItems
									items={normalizeActions(
										item.actions,
										namespace
									)}
									renderMenuOnClick
									trigger={
										<ClayButtonWithIcon
											className="component-action quick-action-item"
											displayType={null}
											size="sm"
											symbol="ellipsis-v"
										/>
									}
								/>
							)
						}
						active={selectedLayoutId === item.id ? 'true' : null}
						expandable={item.hasChildren}
						onKeyDown={(event) => {
							if (
								event.keyCode === ENTER_KEYCODE &&
								item.regularURL
							) {
								itemAnchorRef.current.click();
							}
						}}
					>
						{item.icon && <ClayIcon symbol={item.icon} />}

						<div className="align-items-center d-flex pl-2">
							{item.regularURL ? (
								<a
									aria-label={
										Liferay.FeatureFlags['LPS-174417'] &&
										item.hasDuplicatedFriendlyURL
											? `${item.name}. ${warningMessage}`
											: item.name
									}
									className="flex-grow-1 text-decoration-none text-truncate-inline"
									href={item.regularURL}
									ref={itemAnchorRef}
									tabIndex="-1"
									target={item.target}
								>
									<span
										className="text-truncate"
										data-title={item.name}
									>
										{item.name}
									</span>

									{Liferay.FeatureFlags['LPS-174417'] &&
									item.hasDuplicatedFriendlyURL ? (
										<ClayIcon
											className="align-self-center flex-shrink-0 icon-warning lfr-portal-tooltip"
											data-title={warningMessage}
											symbol="warning-full"
										/>
									) : null}
								</a>
							) : (
								<span>{item.name}</span>
							)}
						</div>
					</ClayTreeView.Item>
				)}
			</ClayTreeView.Group>

			{load.get(item.id) !== null &&
				expand.has(item.id) &&
				item.paginated && (
					<ClayButton
						borderless
						className="ml-3 text-light"
						displayType="secondary"
						onClick={() => load.loadMore(item.id, item)}
					>
						{Liferay.Language.get('load-more-results')}
					</ClayButton>
				)}
		</ClayTreeView.Item>
	);
}

TreeItem.propTypes = {
	expand: PropTypes.object.isRequired,
	item: PropTypes.object.isRequired,
	load: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	selectedLayoutId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

function normalizeActions(actions, namespace) {
	return actions.map((group) => ({
		...group,
		items: group.items.map((item) => {
			const nextItem = {...item};

			delete nextItem.data;

			if (item.data?.url) {
				nextItem.onClick = (event) => {
					event.preventDefault();

					let modalData = {
						id: `${namespace}pagesTreeModal`,
						title: item.data.modalTitle,
						url: item.data.url,
					};

					if (item.id === ACTION_DELETE) {
						delete modalData.url;

						modalData = {
							...modalData,
							bodyHTML: item.data.message,
							buttons: [
								{
									autoFocus: true,
									displayType: 'secondary',
									label: Liferay.Language.get('cancel'),
									type: 'cancel',
								},
								{
									displayType: 'danger',
									label: Liferay.Language.get('delete'),
									onClick: ({processClose}) => {
										processClose();

										fetch(item.data.url, {
											method: 'post',
										})
											.then((response) => {
												if (response.redirected) {
													navigate(response.url);
												}

												openToast({
													message: Liferay.Language.get(
														'your-request-processed-successfully'
													),
													toastProps: {
														autoClose: 5000,
													},
													type: 'success',
												});
											})
											.catch(() => openErrorToast());
									},
								},
							],
							status: 'danger',
						};
					}
					else if (item.id === ACTION_COPY_PAGE) {
						modalData = {
							...modalData,
							containerProps: {
								className: 'cadmin copy-page-modal',
							},
							id: 'addLayoutDialog',
							size: 'md',
						};
					}

					openModal(modalData);
				};
			}

			return nextItem;
		}),
	}));
}

function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
