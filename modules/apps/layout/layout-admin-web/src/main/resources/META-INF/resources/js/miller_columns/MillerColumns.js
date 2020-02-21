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

import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import DndProvider from './DndProvider';
import MillerColumnsColumn from './MillerColumnsColumn';

const getItemsMap = columns => {
	const map = new Map();

	let parentId;

	columns.forEach((column, columnIndex) => {
		const childrenIds = [];
		let newParentId;

		column.forEach((item, itemIndex) => {
			childrenIds.push(item.id);

			map.set(item.id, {
				...item,
				columnIndex,
				itemIndex,
				parentId
			});

			if (item.active && item.hasChild) {
				newParentId = item.id;
			}
		});

		if (parentId && !!childrenIds.length) {
			map.set(parentId, {
				...map.get(parentId),
				childrenIds
			});
		}

		parentId = newParentId;
	});

	return map;
};

const noop = () => {};

const MillerColumns = ({
	actionHandlers,
	initialColumns = [],
	namespace,
	onColumnsChange = noop,
	onItemMove = noop
}) => {
	const ref = useRef();

	const [items, setItems] = useState(() => getItemsMap(initialColumns));

	// Transform items map into a columns-like array.
	const columns = useMemo(() => {
		const columns = [];

		// eslint-disable-next-line no-for-of-loops/no-for-of-loops, no-unused-vars
		for (const item of items.values()) {
			if (!columns[item.columnIndex]) {
				columns[item.columnIndex] = {
					items: [],
					parent: items.get(item.parentId)
				};
			}

			const column = columns[item.columnIndex];

			if (
				column.items[item.itemIndex] &&
				column.items[item.itemIndex].itemIndex > item.itemIndex
			) {
				column.items.splice(item.itemIndex, 0, item);
			}
			else {
				column.items.push(item);
			}
		}

		// Add empty column in the end if last column has an active item
		const lastColumnActiveItem = columns[columns.length - 1].items.find(
			item => item.active
		);
		if (lastColumnActiveItem && !lastColumnActiveItem.hasChild) {
			columns.push({
				items: [],
				parent: lastColumnActiveItem
			});
		}

		return columns;
	}, [items]);

	const previousColumnsValue = usePrevious(columns);

	useEffect(() => {
		if (previousColumnsValue !== columns) {
			onColumnsChange(columns);
		}
	}, [columns, onColumnsChange, previousColumnsValue]);

	useEffect(() => {
		if (ref.current) {
			ref.current.scrollLeft = ref.current.scrollWidth;
		}
	}, []);

	const onItemDrop = (sourceId, parentId, newIndex) => {
		const newItems = new Map(items);

		const newSource = {...newItems.get(sourceId)};
		const newParent = {...newItems.get(parentId)};

		// Exit if source is being move to the same position.
		if (
			newSource.itemIndex === newIndex &&
			newSource.parentId === parentId
		) {
			return;
		}

		// If it wasn't before, now new parent obviously will have children.
		newParent.hasChild = true;
		newItems.set(parentId, newParent);

		// If no newIndex is provided set it as the last of the siblings.
		// If new parent doen't have childrenIds (is not active) we don't have to
		// worry about this
		if (typeof newIndex !== 'number' && newParent.childrenIds) {
			newIndex = newParent.childrenIds.length;

			// If source has been moved on the same column adjust the
			// newIndex.
			if (newSource.parentId === parentId) {
				newIndex--;
			}
		}

		// If source has been moved to other column we remove its id from
		// its previous parent child ids list and update all its indexes.
		if (newSource.parentId !== parentId) {
			const newPreviousSourceParent = {
				...newItems.get(newSource.parentId)
			};

			// Remove source id from previous parent child list.
			newPreviousSourceParent.childrenIds.splice(
				newPreviousSourceParent.childrenIds.indexOf(sourceId),
				1
			);

			// Update previous source parent child indexes.
			newPreviousSourceParent.childrenIds.forEach(childId => {
				const newChild = {...newItems.get(childId)};

				if (newChild.itemIndex > newSource.itemIndex) {
					newChild.itemIndex = newChild.itemIndex - 1;
					newItems.set(childId, newChild);
				}
			});

			newPreviousSourceParent.hasChild = !!newPreviousSourceParent
				.childrenIds.length;

			newItems.set(newSource.parentId, newPreviousSourceParent);

			// If source was active mark it as inactive and remove all children
			// recursively (will no longer be visible).
			if (newSource.active) {
				newSource.active = false;

				const deleteChildren = childrenIds => {
					if (childrenIds) {
						childrenIds.forEach(childId => {
							const childrenItem = newItems.get(childId);
							deleteChildren(childrenItem.childrenIds);
							newItems.delete(childId);
						});
					}
				};

				deleteChildren(newSource.childrenIds);
			}

			// If newParent is active we add source id to new parent child
			// ids list.
			if (newParent.active) {
				if (newParent.childrenIds) {
					newParent.childrenIds.push(sourceId);
				}
				else {
					newParent.childrenIds = [sourceId];
				}
				newItems.set(parentId, newParent);
			}
		}

		// Update new source siblings indexes.
		if (newParent.childrenIds) {
			// They have to be more than 1 to have to worry about sorting.
			if (newParent.childrenIds.length > 1) {
				newParent.childrenIds.forEach(childId => {
					if (childId !== sourceId) {
						const newChild = {...newItems.get(childId)};

						let newChildIndex = newChild.itemIndex;

						if (newSource.parentId !== parentId) {
							if (newChild.itemIndex >= newIndex) {
								newChildIndex++;
							}
						}
						else {
							// If it's been moved down.
							if (newSource.itemIndex < newIndex) {
								if (
									newChild.itemIndex <= newIndex &&
									newChild.itemIndex > newSource.itemIndex
								) {
									newChildIndex--;
								}
							}
							// If it's been moved up.
							else if (newSource.itemIndex > newIndex) {
								if (
									newChild.itemIndex >= newIndex &&
									newChild.itemIndex < newSource.itemIndex
								) {
									newChildIndex++;
								}
							}
						}

						// Update child item only if position changed.
						if (newChild.itemIndex !== newChildIndex) {
							newChild.itemIndex = newChildIndex;
							newItems.set(childId, newChild);
						}
					}
				});
			}
		}

		// Update only source if it's going to be seen, if not, delete it.
		if (newParent.active) {
			newSource.columnIndex = newParent.columnIndex + 1;
			newSource.itemIndex = newIndex;
			newSource.parentId = parentId;
			newItems.set(sourceId, newSource);
		}
		else {
			newItems.delete(sourceId);
		}

		// Save
		setItems(newItems);
		onItemMove(sourceId, parentId, newIndex);
	};

	return (
		<DndProvider>
			<div className="bg-white miller-columns-row" ref={ref}>
				{columns.map((column, index) => (
					<MillerColumnsColumn
						actionHandlers={actionHandlers}
						items={column.items}
						key={index}
						namespace={namespace}
						onItemDrop={onItemDrop}
						parent={column.parent}
					/>
				))}
			</div>
		</DndProvider>
	);
};

export default MillerColumns;
