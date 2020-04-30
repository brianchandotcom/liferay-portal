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
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import MillerColumnsColumn from './MillerColumnsColumn';

const getItemsMap = (columns) => {
	const map = new Map();

	let parentId, parentKey;

	columns.forEach((column, columnIndex) => {
		let childrenCount = 0;
		let newParentId, newParentKey;

		column.forEach((item) => {
			childrenCount++;

			map.set(item.key, {
				...item,
				columnIndex,
				parentId,
				parentKey,
			});

			if (item.active && item.hasChild) {
				newParentId = item.id;
				newParentKey = item.key;
			}
		});

		if (parentKey) {
			map.set(parentKey, {
				...map.get(parentKey),
				childrenCount,
			});
		}

		parentId = newParentId;
		parentKey = newParentKey;
	});

	return map;
};

const noop = () => {};

const MillerColumns = ({
	actionHandlers,
	initialColumns = [],
	namespace,
	onColumnsChange = noop,
	onItemMove = noop,
	onItemStayHover,
}) => {
	const ref = useRef();

	const [items, setItems] = useState(() => getItemsMap(initialColumns));

	// Transform items map into a columns-like array.

	const columns = useMemo(() => {
		const columns = [];

		// eslint-disable-next-line no-for-of-loops/no-for-of-loops
		for (const item of items.values()) {
			if (!columns[item.columnIndex]) {
				columns[item.columnIndex] = {
					items: [],
					parent: items.get(item.parentId),
				};
			}

			const column = columns[item.columnIndex];

			column.items.push(item);
		}

		// Add empty column in the end if last column has an active item

		const lastColumnActiveItem = columns[columns.length - 1].items.find(
			(item) => item.active
		);
		if (lastColumnActiveItem && !lastColumnActiveItem.hasChild) {
			columns.push({
				items: [],
				parent: lastColumnActiveItem,
			});
		}

		return columns;
	}, [items]);

	const previousColumnsValue = usePrevious(columns);
	const previousInitialColumnsValue = usePrevious(initialColumns);

	useEffect(() => {
		if (previousInitialColumnsValue !== initialColumns) {
			setItems(getItemsMap(initialColumns));
		}
	}, [initialColumns, previousInitialColumnsValue]);

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

	const onItemDrop = (sourceId, newParentId, newIndex) => {
		const newItems = new Map();

		const itemsArray = Array.from(items.values());

		const source = itemsArray.find((item) => item.id === sourceId);
		const parent = itemsArray.find((item) => item.id === newParentId);

		// If no newIndex is provided set it as the last of the siblings.

		if (typeof newIndex !== 'number') {
			newIndex = parent.childrenCount || 0;
		}

		const newSource = {
			...source,
			active: newParentId === source.parentId && source.active,
			columnIndex: parent.columnIndex + 1,
			parentId: newParentId,
		};

		let prevColumnIndex;
		let itemIndex = 0;

		// eslint-disable-next-line no-for-of-loops/no-for-of-loops
		for (let item of items.values()) {
			const columnIndex = item.columnIndex;

			if (item.columnIndex > prevColumnIndex) {

				// Exit if source was active but not anymore and we are on the
				// next column to where source used to live to avoid saving its
				// children (which must not be shown anymore)

				if (
					source.active &&
					!newSource.active &&
					columnIndex > newSource.columnIndex + 1
				) {
					break;
				}

				// Reset itemIndex counter on each column

				itemIndex = 0;
			}

			// Skip the source item iteration

			if (item.id === sourceId) {
				itemIndex++;
				prevColumnIndex = item.columnIndex;
				continue;
			}

			if (item.id === newParentId) {
				let newChildrenCount = item.childrenCount;

				if (newParentId !== source.parentId) {
					newChildrenCount++;
				}

				item = {
					...item,
					childrenCount: newChildrenCount,
					hasChild: true,
				};
			}
			else if (item.id === source.parentId) {
				const newChildrenCount = item.childrenCount - 1;

				item = {
					...item,
					childrenCount: newChildrenCount,
					hasChild: newChildrenCount > 0,
				};
			}

			if (
				itemIndex === newIndex &&
				columnIndex === newSource.columnIndex &&
				parent.active
			) {
				newItems.set(newSource.key, newSource);
			}

			newItems.set(item.key, {...item});

			itemIndex++;
			prevColumnIndex = item.columnIndex;
		}

		// If source parent is active (children are visible) set (again or not)
		// the newSource in the map in case it's being placed as the last
		// element (so won't reach that position in the loop).

		if (parent.active) {
			newItems.set(newSource.id, newSource);
		}

		setItems(newItems);
		onItemMove(sourceId, newParentId, newIndex);
	};

	return (
		<DndProvider backend={HTML5Backend}>
			<div className="bg-white miller-columns-row" ref={ref}>
				{columns.map((column, index) => (
					<MillerColumnsColumn
						actionHandlers={actionHandlers}
						items={column.items}
						key={index}
						namespace={namespace}
						onItemDrop={onItemDrop}
						onItemStayHover={onItemStayHover}
						parent={column.parent}
					/>
				))}
			</div>
		</DndProvider>
	);
};

export default MillerColumns;
