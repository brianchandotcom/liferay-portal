/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useSessionState} from '@liferay/layout-content-page-editor-web';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

const ROOT_ITEM_ID = '0';

export default function PagesTree({
	config,
	isPrivateLayoutsTree,
	items,
	selectedLayoutId,
}) {
	const {loadMoreItemsURL, maxPageSize, namespace} = config;

	const onLoadMore = useCallback(
		(item, initialCursor = 1) => {
			if (!item.hasChildren) {
				return Promise.resolve({
					cursor: null,
					items: [],
				});
			}

			const cursor = item.children ? initialCursor : 0;

			return fetch(loadMoreItemsURL, {
				body: Liferay.Util.objectToURLSearchParams({
					[`${namespace}parentLayoutId`]: item.layoutId,
					[`${namespace}privateLayout`]: isPrivateLayoutsTree,
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

	const [
		expandedKeys,
		setExpandedKeys,
	] = useSessionState(`${namespace}_expandedKeys`, [ROOT_ITEM_ID]);

	return (
		<div className="pages-tree">
			<ClayTreeView
				defaultItems={items}
				displayType="dark"
				expandedKeys={new Set(expandedKeys)}
				onExpandedChange={(keys) => {
					setExpandedKeys(Array.from(keys));
				}}
				onLoadMore={onLoadMore}
			>
				{(item, selection, expand, load) => (
					<ClayTreeView.Item>
						<ClayTreeView.ItemStack
							active={
								selectedLayoutId === item.id ? 'true' : null
							}
						>
							{item.icon && <ClayIcon symbol={item.icon} />}

							<div className="align-items-center d-flex pl-2">
								<div className="pages-tree__item-name">
									<a href={item.regularURL} title={item.name}>
										{item.name}
									</a>
								</div>
							</div>
						</ClayTreeView.ItemStack>

						<ClayTreeView.Group items={item.children}>
							{(item) => (
								<ClayTreeView.Item
									active={
										selectedLayoutId === item.id
											? 'true'
											: null
									}
								>
									{item.icon && (
										<ClayIcon symbol={item.icon} />
									)}

									<div className="align-items-center d-flex pl-2">
										<div className="pages-tree__item-name">
											<a
												href={item.regularURL}
												title={item.name}
											>
												{item.name}
											</a>
										</div>
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

function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
