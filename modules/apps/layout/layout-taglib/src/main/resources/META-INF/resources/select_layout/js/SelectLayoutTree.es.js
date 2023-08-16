/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {getOpener, sub} from 'frontend-js-web';
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
	itemSelectorSaveEvent,
	items: initialItems = [],
	onItemsCountChange,
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

		requestAnimationFrame(() => {
			selection.toggle(item.id);
		});
	};

	const onClick = (event, item, selection, expand) => {
		event.preventDefault();

		if (item.disabled) {
			expand.toggle(item.id);

			return;
		}

		if (multiSelection) {
			handleMultipleSelectionChange(item, selection, event.shiftKey);
		}
		else {
			handleSingleSelection(item, selection);
		}
	};

	const onKeyDown = (event, item, selection) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.stopPropagation();

			if (multiSelection) {
				handleMultipleSelectionChange(item, selection, event.shiftKey);
			}
			else {
				handleSingleSelection(item, selection);
			}
		}
	};

	return filteredItems.length ? (
		<div className="pt-3 px-3">
			{multiSelection && (
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
							onKeyDown={(event) =>
								onKeyDown(event, item, selection)
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
									onClick={(event) => event.stopPropagation()}
									tabIndex="-1"
								/>
							)}

							<ClayIcon symbol={item.icon} />

							<div className="d-flex">
								<span className="flex-grow-0" title={item.url}>
									{item.name}
								</span>
							</div>
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

									<div className="d-flex">
										<span
											className="flex-grow-0"
											title={item.url}
										>
											{item.name}
										</span>
									</div>
								</ClayTreeView.Item>
							)}
						</ClayTreeView.Group>
					</ClayTreeView.Item>
				)}
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
