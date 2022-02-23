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

export function SelectLayoutTree({
	filter,
	followURLOnTitleClick,
	itemSelectorSaveEvent,
	items: initialItems,
	multiSelection,
	selectedLayoutIds,
}) {
	const [items, setItems] = useState(initialItems);

	const [selectedKeys, setSelectionChange] = useState(
		new Set(selectedLayoutIds)
	);

	const filteredItems = useMemo(() => {
		if (!filter) {
			return items;
		}

		return performFilter(filter.toLowerCase(), [...items]);
	}, [items, filter]);

	const selectedItemsRef = useRef(new Map());

	const handleMultipleSelectionChange = (selection, item) => {
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

		if (!selectedItemsRef.current.size) {
			return;
		}

		if (followURLOnTitleClick) {
			Liferay.Util.getOpener().document.location.href = item.url;
		}
		else {
			const data = Array.from(selectedItemsRef.current.values());

			Liferay.fire(itemSelectorSaveEvent, {
				data,
			});

			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data,
			});
		}
	};

	const handleSingleSelectionChange = (event, item) => {
		event.preventDefault();

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

		setSelectionChange(new Set([item.id]));

		if (followURLOnTitleClick) {
			Liferay.Util.getOpener().document.location.href = item.url;
		}
		else {
			Liferay.fire(itemSelectorSaveEvent, {
				data,
			});

			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data,
			});
		}
	};

	return (
		<ClayTreeView
			items={filteredItems}
			onItemsChange={(items) => setItems(items)}
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
