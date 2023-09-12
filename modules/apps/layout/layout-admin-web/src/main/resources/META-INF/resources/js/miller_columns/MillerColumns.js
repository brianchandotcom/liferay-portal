/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {usePrevious} from '@liferay/frontend-js-react-web';
import {DragPreview} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import MillerColumnsColumn from './MillerColumnsColumn';

const getItemsMap = (columns, oldItems = new Map()) => {
	const map = new Map();

	let parentId;
	let parentKey;

	columns.forEach((column, columnIndex) => {
		let childrenCount = 0;
		let newParentId;
		let newParentKey;

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
	initialColumns = [],
	isLayoutSetPrototype,
	isPrivateLayoutsEnabled,
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

	const getMovedItems = (sources, newParentId, targetIndex) => {
		const targetColumnItems = Array.from(items.values()).filter(
			(item) => item.parentId === newParentId
		);

		// Return sources if target column has no items

		if (!targetColumnItems.length) {
			return sources.map((source, index) => ({
				plid: source.id,
				position: targetIndex + index,
			}));
		}

		// Calculate all items of the target column that are changing position

		const movedItems = new Map();

		let previousSources = 0;

		targetColumnItems.forEach((item, index) => {

			// Iterating on target position. Insert sources into movedItems

			if (index === targetIndex) {
				sources.forEach((source, sourceIndex) => {
					movedItems.set(source.id, {
						plid: source.id,
						position: index - previousSources + sourceIndex,
					});
				});

				previousSources = sources.length - previousSources;
			}

			// Iterating on a source. Don't insert it into movedItems but we
			// update previous sources counter

			if (sources.some((source) => source.id === item.id)) {
				previousSources =
					index < targetIndex
						? previousSources + 1
						: previousSources - 1;
			}

			// Insert item that is not a source into movedItems if it has a new
			// position

			else if (previousSources) {
				movedItems.set(item.id, {
					plid: item.id,
					position:
						index < targetIndex
							? index - previousSources
							: index + previousSources,
				});
			}
		});

		// Insert sources into movedItems in the case we are targeting last
		// position of column so the loop does not reach it

		sources.forEach((source, index) => {
			if (!movedItems.has(source.id)) {
				movedItems.set(source.id, {
					plid: source.id,
					position: targetIndex + index,
				});
			}
		});

		return Array.from(movedItems.values());
	};

	const onItemDrop = (sources, newParentId, targetIndex) => {

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
			getMovedItems(sources, newParentId, targetIndex),
			newParentId
		);
	};

	const getDragPreviewLabel = (item) => {
		const items = item?.items;

		if (items) {
			if (items.length > 1) {
				return sub(Liferay.Language.get('x-elements'), items.length);
			}
			else {
				const [item] = items;

				return item.title;
			}
		}
	};

	return (
		<DndProvider backend={HTML5Backend}>
			<DragPreview getLabel={getDragPreviewLabel} />

			<div className="bg-white miller-columns-row" ref={ref}>
				{columns.map((column, index) => (
					<MillerColumnsColumn
						columnItems={column.items}
						columnsContainer={ref}
						isLayoutSetPrototype={isLayoutSetPrototype}
						isPrivateLayoutsEnabled={isPrivateLayoutsEnabled}
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
