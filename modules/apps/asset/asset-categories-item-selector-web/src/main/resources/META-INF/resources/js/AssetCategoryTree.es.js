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

const nodeByName = (items, name) => {
	return items.reduce(function reducer(acc, item) {
		if (item.name?.toLowerCase().includes(name.toLowerCase())) {
			acc.push(item);
		}
		else if (item.children) {
			acc.concat(item.children.reduce(reducer, acc));
		}

		return acc;
	}, []);
};

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

		return nodeByName(items, filterQuery);
	}, [items, filterQuery]);

	const selectedItemsRef = useRef(new Map());

	const handleMultipleSelectionChange = (item, selection) => {
		selection.toggle(item.id);

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

	const handleSingleSelectionChange = (item) => {
		Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
			data: {
				className: item.className,
				classNameId: item.classNameId,
				classPK: item.id,
				title: item.name,
			},
		});
	};

	const onClick = (event, item, selection) => {
		event.preventDefault();

		if (item.disabled) {
			return;
		}

		if (multiSelection) {
			handleMultipleSelectionChange(item, selection);
		}
		else {
			handleSingleSelectionChange(item);
		}
	};

	const onKeyDownCapture = (event, item, selection) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.preventDefault();
			event.stopPropagation();

			if (item.disabled) {
				return;
			}

			if (multiSelection) {
				handleMultipleSelectionChange(item, selection);
			}
			else {
				handleSingleSelectionChange(item);
			}
		}
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
						onClick={(event) => onClick(event, item, selection)}
						onKeyDownCapture={(event) =>
							onKeyDownCapture(event, item, selection)
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
								tabIndex="-1"
							/>
						)}

						<ClayIcon symbol={item.icon} />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								onClick={(event) =>
									onClick(event, item, selection)
								}
								onKeyDownCapture={(event) =>
									onKeyDownCapture(event, item, selection)
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
										tabIndex="-1"
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
