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

import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useMemo, useRef, useState} from 'react';

function performFilter(value, tree) {
	const getItems = (previous, item) => {
		if (!item.vocabulary && item.name.toLowerCase().indexOf(value) !== -1) {
			const immutableItem = {...item};

			if (Array.isArray(immutableItem.children)) {
				immutableItem.children = immutableItem.children.reduce(
					getItems,
					[]
				);
			}

			previous.push(immutableItem);

			return previous;
		}

		if (Array.isArray(item.children)) {
			const children = item.children.reduce(getItems, []);

			if (children.length) {
				previous.push({...item, children});
			}
		}

		return previous;
	};

	return tree.reduce(getItems, []);
}

function visit(nodes, callback) {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

function computePreSelectedItems(items) {
	const result = {};

	visit(items, (item) => {
		if (item.selected) {
			result[item.id] = {
				categoryId: item.vocabulary ? 0 : item.id,
				nodePath: item.nodePath,
				value: item.name,
				vocabularyId: item.vocabulary ? item.id : 0,
			};
		}
	});

	return result;
}

export function SelectTree({
	filterQuery,
	itemSelectorSaveEvent,
	items,
	multiSelection,
	onItems,
	selectedCategoryIds,
}) {
	const [selectedKeys, setSelectionChange] = useState(
		new Set(selectedCategoryIds)
	);

	const isComputePreSelectedRef = useRef(false);

	const selectedItemsRef = useRef({});

	const filteredItems = useMemo(() => {
		if (!filterQuery) {
			return items;
		}

		return performFilter(filterQuery.toLowerCase(), [...items]);
	}, [items, filterQuery]);

	const handleMultipleSelectionChange = (selection, item) => {
		let selected = true;

		if (!selection.has(item.id)) {
			selected = false;
		}

		// Traverse the tree only once to get the pre-selected items. It is cheaper
		// to control the unchecked state by iterating over the array instead of
		// traversing the tree each change.

		if (!isComputePreSelectedRef.current) {
			selectedItemsRef.current = {
				...selectedItemsRef.current,
				...computePreSelectedItems(items),
			};

			isComputePreSelectedRef.current = true;
		}

		selectedItemsRef.current = {
			...selectedItemsRef.current,
			[item.id]: {
				categoryId: item.vocabulary ? 0 : item.id,
				nodePath: item.nodePath,
				unchecked: selected,
				value: item.name,
				vocabularyId: item.vocabulary ? item.id : 0,
			},
		};

		Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data: selectedItemsRef.current,
		});
	};

	const handleSingleSelectionChange = (event, item) => {
		event.preventDefault();

		Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data: {
				[item.id]: {
					categoryId: item.vocabulary ? 0 : item.id,
					nodePath: item.nodePath,
					value: item.name,
					vocabularyId: item.vocabulary ? item.id : 0,
				},
			},
		});
	};

	return (
		<ClayTreeView
			items={filteredItems}
			onItemsChange={(items) => onItems(items)}
			onSelectionChange={(keys) => setSelectionChange(keys)}
			selectedKeys={selectedKeys}
			selectionMode={multiSelection ? 'multiple' : 'single'}
			showExpanderOnHover={false}
		>
			{(item, selection) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						onClick={(event) =>
							!multiSelection &&
							!item.disabled &&
							handleSingleSelectionChange(event, item)
						}
					>
						{multiSelection && !item.disabled && (
							<ClayCheckbox
								onChange={() =>
									handleMultipleSelectionChange(
										selection,
										item
									)
								}
							/>
						)}

						<ClayIcon symbol={item.icon} />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								onClick={(event) =>
									!multiSelection &&
									!item.disabled &&
									handleSingleSelectionChange(event, item)
								}
							>
								{multiSelection && !item.disabled && (
									<ClayCheckbox
										onChange={() =>
											handleMultipleSelectionChange(
												selection,
												item
											)
										}
									/>
								)}

								<ClayIcon symbol={item.icon} />

								{item.name}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
}
