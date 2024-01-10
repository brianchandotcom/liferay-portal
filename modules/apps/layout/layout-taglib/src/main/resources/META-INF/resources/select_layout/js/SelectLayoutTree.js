/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classnames from 'classnames';
import {debounce, fetch, getOpener, openToast, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

export function SelectLayoutTree({
	checkDisplayPage,
	config,
	filter,
	groupId,
	itemSelectorReturnType,
	itemSelectorSaveEvent,
	items: initialItems = [],
	onItemsCountChange,
	privateLayout,
	multiSelection,
	selectedLayoutIds,
}) {
	const {loadMoreItemsURL, maxPageSize} = config;

	const [items, setItems] = useState(initialItems);

	const [selectedKeys, setSelectedKeys] = useState(
		new Set(selectedLayoutIds)
	);

	const selectedItemsRef = useRef(new Map());

	const updateSelectedItems = (item, selection, recursive) => {
		if (!selection.has(item.id)) {
			selectedItemsRef.current.set(item.id, {
				groupId: item.groupId,
				id: item.id,
				layoutId: item.layoutId,
				name: item.value,
				privateLayout: item.privateLayout,
				returnType: item.returnType,
				title: item.name,
				value: item.payload,
			});
		}
		else {
			selectedItemsRef.current.delete(item.id);
		}

		if (item.children && recursive) {
			item.children.forEach((child) =>
				updateSelectedItems(child, selection, recursive)
			);
		}
	};

	const handleMultipleSelectionChange = (item, selection, recursive) => {
		selection.toggle(item.id, {
			parentSelection: false,
			selectionMode: recursive ? 'multiple-recursive' : null,
		});

		updateSelectedItems(item, selection, recursive);

		if (onItemsCountChange) {
			onItemsCountChange(selectedItemsRef.current.size);
		}

		if (!selectedItemsRef.current.size) {
			return;
		}

		const data = Array.from(selectedItemsRef.current.values());

		Liferay.fire(itemSelectorSaveEvent, {
			data,
		});

		getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data,
		});
	};

	const handleSingleSelection = (item, selection) => {
		const data = {
			groupId: item.groupId,
			id: item.id,
			layoutId: item.layoutId,
			name: item.value,
			privateLayout: item.privateLayout,
			returnType: item.returnType,
			title: item.name,
			value: item.payload,
		};

		Liferay.fire(itemSelectorSaveEvent, {
			data,
		});

		getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data,
		});

		if (selection) {
			requestAnimationFrame(() => {
				selection.toggle(item.id);
			});
		}
	};

	const onClick = (event, item, selection, expand, load) => {
		event.preventDefault();

		if (!item.disabled) {
			if (multiSelection) {
				handleMultipleSelectionChange(item, selection, event.shiftKey);
			}
			else {
				handleSingleSelection(item, selection);
			}
		}

		if (item.hasChildren) {
			if (!item.children) {
				load.loadMore(item.id, item).then(() => expand.toggle(item.id));
			}
			else {
				expand.toggle(item.id);
			}
		}
	};

	const onKeyDown = (event, item, selection, expand, load) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.stopPropagation();

			onClick(event, item, selection, expand, load);
		}
	};

	const onSearchResultSelect = (item, selection) => {
		if (selection) {
			handleMultipleSelectionChange(item, selection);
		}
		else {
			handleSingleSelection(item, selection);
		}
	};

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
					[`checkDisplayPage`]: checkDisplayPage,
					[`groupId`]: groupId,
					[`itemSelectorReturnType`]: itemSelectorReturnType,
					[`layoutUuid`]: item.id,
					[`parentLayoutId`]: item.layoutId,
					[`privateLayout`]: privateLayout,
					[`redirect`]:
						window.location.pathname + window.location.search,
					[`start`]: cursor * maxPageSize,
				}),
				method: 'post',
			})
				.then((response) => response.json())
				.then(({hasMoreElements, items: nextItems}) => ({
					cursor: hasMoreElements ? cursor + 1 : null,
					items: nextItems,
				}))
				.catch(() =>
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					})
				);
		},
		[
			checkDisplayPage,
			groupId,
			itemSelectorReturnType,
			loadMoreItemsURL,
			privateLayout,
			maxPageSize,
		]
	);

	return items.length ? (
		<div className="cadmin pt-3 px-3">
			{multiSelection && !filter && (
				<p
					className="mb-4"
					dangerouslySetInnerHTML={{
						__html: sub(
							Liferay.Language.get(
								'press-x-to-select-or-deselect-a-parent-node-and-all-its-child-items'
							),
							'<kbd class="c-kbd c-kbd-light">⇧</kbd>'
						),
					}}
				/>
			)}

			<ClayTreeView
				defaultExpandedKeys={new Set(['0'])}
				items={items}
				onItemsChange={(items) => setItems(items)}
				onLoadMore={onLoadMore}
				onSelectionChange={(keys) => setSelectedKeys(keys)}
				selectedKeys={selectedKeys}
				selectionMode={multiSelection ? 'multiple' : 'single'}
				showExpanderOnHover={false}
			>
				{(item, selection, expand, load) => {
					if (filter) {
						return (
							<SearchResults
								checkDisplayPage={checkDisplayPage}
								filter={filter}
								findLayoutsURL={config.findLayoutsURL}
								groupId={groupId}
								itemSelectorReturnType={itemSelectorReturnType}
								multiSelection={multiSelection}
								onSelect={onSearchResultSelect}
								selection={selection}
							/>
						);
					}

					return (
						<ClayTreeView.Item active={false}>
							<ClayTreeView.ItemStack
								active={false}
								onClick={(event) =>
									onClick(
										event,
										item,
										selection,
										expand,
										load
									)
								}
								onKeyDown={(event) =>
									onKeyDown(
										event,
										item,
										selection,
										expand,
										load
									)
								}
							>
								{multiSelection && !item.disabled && (
									<Checkbox
										checked={selection.has(item.id)}
										onChange={(event) =>
											handleMultipleSelectionChange(
												item,
												selection,
												event.nativeEvent.shiftKey
											)
										}
										onClick={(event) =>
											event.stopPropagation()
										}
										tabIndex="-1"
									/>
								)}

								<ClayIcon symbol={item.icon} />

								<div
									className={classnames('d-flex', {
										'align-items-center c-ml-1':
											Liferay.FeatureFlags['LPS-196847'],
									})}
								>
									<span
										className="flex-grow-0"
										title={item.url}
									>
										{item.name}
									</span>

									{Liferay.FeatureFlags['LPS-196847'] &&
									item.id !== '0' &&
									!item.hasGuestViewPermission ? (
										<ClayIcon
											className="c-ml-2 c-mt-0 lfr-portal-tooltip text-4"
											data-title={Liferay.Language.get(
												'restricted-page'
											)}
											symbol="lock"
										/>
									) : null}
								</div>
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item
										disabled={
											item.disabled && !item.hasChildren
										}
										expandable={item.hasChildren}
										onClick={(event) =>
											onClick(
												event,
												item,
												selection,
												expand,
												load
											)
										}
										onKeyDown={(event) =>
											onKeyDown(
												event,
												item,
												selection,
												expand,
												load
											)
										}
									>
										{multiSelection && !item.disabled && (
											<Checkbox
												checked={selection.has(item.id)}
												onChange={(event) =>
													handleMultipleSelectionChange(
														item,
														selection,
														event.nativeEvent
															.shiftKey
													)
												}
												onClick={(event) =>
													event.stopPropagation()
												}
												tabIndex="-1"
											/>
										)}

										<ClayIcon symbol={item.icon} />

										<div
											className={classnames('d-flex', {
												'align-items-center c-ml-1':
													Liferay.FeatureFlags[
														'LPS-196847'
													],
											})}
										>
											<span
												className="flex-grow-0"
												title={item.url}
											>
												{item.name}
											</span>

											{Liferay.FeatureFlags[
												'LPS-196847'
											] &&
												item.hasGuestViewPermission ===
													false && (
													<ClayIcon
														className="c-ml-2 c-mt-0 lfr-portal-tooltip text-4"
														data-title={Liferay.Language.get(
															'restricted-page'
														)}
														symbol="lock"
													/>
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
										className="ml-3 mt-2 text-secondary"
										displayType="secondary"
										onClick={() =>
											load.loadMore(item.id, item)
										}
									>
										{Liferay.Language.get(
											'load-more-results'
										)}
									</ClayButton>
								)}
						</ClayTreeView.Item>
					);
				}}
			</ClayTreeView>
		</div>
	) : (
		<ClayEmptyState
			description={Liferay.Language.get(
				'try-again-with-a-different-search'
			)}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
			small
			title={Liferay.Language.get('no-results-found')}
		/>
	);
}

const Checkbox = (props) => <ClayCheckbox {...props} />;

function SearchResults({
	checkDisplayPage,
	filter,
	findLayoutsURL,
	groupId,
	itemSelectorReturnType,
	multiSelection,
	onSelect,
	selection,
}) {
	const [results, setResults] = useState([]);
	const [loading, setLoading] = useState(false);

	const onFindLayouts = useCallback((layouts) => {
		setLoading(false);

		setResults(layouts);
	}, []);

	useEffect(() => {
		setLoading(true);

		debouncedFindLayouts(
			findLayoutsURL,
			checkDisplayPage,
			groupId,
			itemSelectorReturnType,
			filter,
			onFindLayouts
		);
	}, [
		checkDisplayPage,
		filter,
		findLayoutsURL,
		groupId,
		itemSelectorReturnType,
		onFindLayouts,
	]);

	if (loading) {
		return <ClayLoadingIndicator displayType="secondary" />;
	}

	return (
		<div className="pt-3">
			{results.map((layout) => (
				<SearchResult
					key={layout.id}
					layout={layout}
					multiSelection={multiSelection}
					onSelect={onSelect}
					selection={selection}
				/>
			))}
		</div>
	);
}

function SearchResult({layout, multiSelection, onSelect, selection}) {
	return (
		<div className="align-items-center d-flex pb-2">
			{multiSelection && (
				<Checkbox
					checked={selection.has(layout.id)}
					containerProps={{className: 'mr-3 my-0'}}
					disabled={layout.disabled}
					onChange={() => onSelect(layout, selection)}
				/>
			)}

			{layout.path.map((ancestor, index) => (
				<span className="pr-2 text-secondary" key={index}>
					{ancestor}

					<ClayIcon className="ml-2" symbol="angle-right-small" />
				</span>
			))}

			{multiSelection ? (
				<span className="font-weight-semi-bold p-0">{layout.name}</span>
			) : (
				<ClayButton
					className="font-weight-semi-bold px-0 py-1 search-result-button"
					disabled={layout.disabled}
					displayType="unstyled"
					onClick={() => onSelect(layout)}
				>
					{layout.name}
				</ClayButton>
			)}
		</div>
	);
}

function findLayouts(
	url,
	checkDisplayPage,
	groupId,
	itemSelectorReturnType,
	keywords,
	onFindLayouts
) {
	fetch(url, {
		body: Liferay.Util.objectToURLSearchParams({
			[`checkDisplayPage`]: checkDisplayPage,
			[`groupId`]: groupId,
			[`itemSelectorReturnType`]: itemSelectorReturnType,
			[`keywords`]: keywords,
		}),
		method: 'post',
	})
		.then((response) => response.json())
		.then(({layouts}) => {
			onFindLayouts(layouts);
		})
		.catch(() =>
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				title: Liferay.Language.get('error'),
				type: 'danger',
			})
		);
}

const debouncedFindLayouts = debounce(findLayouts, 300);
