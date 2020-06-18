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

import DragPreview from './DragPreview';
import MillerColumnsColumn from './MillerColumnsColumn';

const getItemsMap = (columns, oldItems = new Map()) => {
	const map = new Map();

	let parentId, parentKey;

	columns.forEach((column, columnIndex) => {
		let childrenCount = 0;
		let newParentId, newParentKey;

		column.forEach((item) => {
			childrenCount++;

			const oldItem = Array.from(oldItems.values()).find(
				(oldItem) => oldItem.id === item.id
			);

			map.set(item.key, {
				...item,
				checked: oldItem ? oldItem.checked : false,
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
	rtl,
	searchContainer,
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
					parent: Array.from(items.values()).find(
						(_item) => _item.id === item.parentId
					),
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
			setItems(getItemsMap(initialColumns, items));
		}
	}, [initialColumns, items, previousInitialColumnsValue]);

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

	useEffect(() => {
		if (searchContainer) {
			searchContainer.on('rowToggled', (event) => {
				setItems((oldItems) => {
					const newItems = new Map(oldItems);

					newItems.forEach((item) => {
						const itemNode = event.elements.allElements._nodes.find(
							(node) => node.value === item.id
						);

						if (itemNode) {
							newItems.set(item.id, {
								...newItems.get(item.id),
								checked: itemNode.checked,
							});
						}
					});

					return newItems;
				});
			});
		}
	}, [searchContainer]);

	const onItemDrop = (sources, newParentId, newIndex) => {

		// Update checked items to keep them selected after updating items
		// with server response

		const newItems = new Map(items);

		sources.forEach((source) => {
			if (source.checked) {
				newItems.set(source.id, source);
			}
		});

		setItems(newItems);

		onItemMove(
			sources.map((item, index) => ({
				plid: item.id,
				position: newIndex + index,
			})),
			newParentId
		);
	};

	return (
		<DndProvider backend={HTML5Backend}>
			<DragPreview rtl={rtl} />
			<div className="bg-white miller-columns-row" ref={ref}>
				{columns.map((column, index) => (
					<MillerColumnsColumn
						actionHandlers={actionHandlers}
						columnItems={column.items}
						items={items}
						key={index}
						namespace={namespace}
						onItemDrop={onItemDrop}
						onItemStayHover={onItemStayHover}
						parent={column.parent}
						rtl={rtl}
					/>
				))}
			</div>
		</DndProvider>
	);
};

export default MillerColumns;
