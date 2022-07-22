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
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {getOpener} from 'frontend-js-web';
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

export function SelectLayoutTree({
	filter,
	followURLOnTitleClick,
	itemSelectorSaveEvent,
	items: initialItems = [],
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

		return nodeByName(items, filter);
	}, [items, filter]);

	const selectedItemsRef = useRef(new Map());

	const handleMultipleSelectionChange = (item, selection) => {
		selection.toggle(item.id);

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
			getOpener().document.location.href = item.url;
		}
		else {
			const data = Array.from(selectedItemsRef.current.values());

			Liferay.fire(itemSelectorSaveEvent, {
				data,
			});

			getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data,
			});
		}
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

		requestAnimationFrame(() => {
			selection.toggle(item.id);
		});
	};

	const onClick = (event, item, selection, expand) => {
		event.preventDefault();

		if (followURLOnTitleClick) {
			getOpener().document.location.href = item.url;

			return;
		}

		if (item.disabled) {
			expand.toggle(item.id);

			return;
		}

		if (multiSelection) {
			handleMultipleSelectionChange(item, selection);
		}
		else {
			handleSingleSelection(item, selection);
		}
	};

	const onKeyDown = (event, item, selection) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.stopPropagation();

			if (multiSelection) {
				handleMultipleSelectionChange(item, selection);
			}
			else {
				handleSingleSelection(item, selection);
			}
		}
	};

	return filteredItems.length ? (
		<ClayTreeView
			items={filteredItems}
			onItemsChange={(items) => setItems(items)}
			onSelectionChange={(keys) => setSelectionChange(keys)}
			selectedKeys={selectedKeys}
			selectionMode={multiSelection ? 'multiple' : 'single'}
			showExpanderOnHover={false}
		>
			{(item, selection, expand) => (
				<ClayTreeView.Item active={false}>
					<ClayTreeView.ItemStack
						active={false}
						onClick={(event) =>
							onClick(event, item, selection, expand)
						}
						onKeyDown={(event) => onKeyDown(event, item, selection)}
					>
						{multiSelection && !item.disabled && (
							<ClayCheckbox
								onChange={() =>
									handleMultipleSelectionChange(
										item,
										selection
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
								disabled={item.disabled}
								expanderDisabled={false}
								onClick={(event) =>
									onClick(event, item, selection)
								}
								onKeyDown={(event) =>
									onKeyDown(event, item, selection)
								}
							>
								{multiSelection && !item.disabled && (
									<ClayCheckbox
										onChange={() =>
											handleMultipleSelectionChange(
												item,
												selection
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
