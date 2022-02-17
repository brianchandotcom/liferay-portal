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

export function AssetCategoryTree({
	filterQuery,
	itemSelectedEventName,
	items,
	multiSelection,
	onItems,
	onSelectedItemsCount,
}) {
	const [selectedKeys, setSelectionChange] = useState(new Set());

	const filteredItems = useMemo(() => {
		if (!filterQuery) {
			return items;
		}

		return performFilter(filterQuery.toLowerCase(), [...items]);
	}, [items, filterQuery]);

	const selectedItemsRef = useRef(new Map());

	const handleMultipleSelectionChange = (selection, item) => {
		if (!selection.has(item.id)) {
			selectedItemsRef.current.set(item.id, {
				className: item.className,
				classNameId: item.classNameId,
				classPK: item.id,
				title: item.name,
			});
		}
		else {
			selectedItemsRef.current.delete(item.id);
		}

		if (multiSelection) {
			onSelectedItemsCount(selectedItemsRef.current.size);
		}

		if (!selectedItemsRef.current.size) {
			return;
		}

		Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
			data: Array.from(selectedItemsRef.current.values()),
		});
	};

	const handleSingleSelectionChange = (event, item) => {
		event.preventDefault();

		Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
			data: {
				className: item.className,
				classNameId: item.classNameId,
				classPK: item.id,
				title: item.name,
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
