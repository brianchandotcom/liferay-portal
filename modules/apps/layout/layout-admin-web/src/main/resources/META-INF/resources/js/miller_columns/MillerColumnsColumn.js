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

import classNames from 'classnames';
import React, {useRef} from 'react';
import {useDrop} from 'react-dnd';

import MillerColumnsItem from './MillerColumnsItem';
import {ACCEPTING_TYPES} from './constants';

const MillerColumnsColumn = ({
	actionHandlers,
	items = [],
	namespace,
	onItemDrop,
	parent
}) => {
	const ref = useRef();

	const isValidTarget = (sourceItem, parentItem) => {
		let isValid;

		if (
			parentItem &&
			(sourceItem.columnId > parentItem.columnId ||
				(parentItem.parentable &&
					sourceItem.columnId <= parentItem.columnId &&
					!sourceItem.active))
		) {
			isValid = true;
		}

		return isValid;
	};

	const [{canDrop}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(sourceItem, monitor) {
			return (
				monitor.isOver({shallow: true}) &&
				isValidTarget(sourceItem, parent)
			);
		},
		collect: monitor => ({
			canDrop: !!monitor.canDrop()
		}),
		drop(sourceItem) {
			if (canDrop) {
				onItemDrop(sourceItem.id, parent.id, items.length);
			}
		}
	});

	drop(ref);

	return (
		<ul
			className={classNames(
				'col-11 col-lg-4 col-md-6 miller-columns-col show-quick-actions-on-hover',
				{
					'drop-target': canDrop
				}
			)}
			ref={ref}
		>
			{items.map((item, index) => (
				<MillerColumnsItem
					actionHandlers={actionHandlers}
					actions={item.actions}
					active={item.active}
					bulkActions={item.bulkActions}
					checked={item.checked}
					child={item.child}
					columnId={item.columnId}
					description={item.description}
					draggable={item.draggable}
					hasChild={item.hasChild}
					itemId={item.id}
					key={item.url}
					namespace={namespace}
					onItemDrop={onItemDrop}
					order={index}
					parent={item.parent}
					parentable={item.parentable}
					selectable={item.selectable}
					states={item.states}
					title={item.title}
					url={item.url}
				/>
			))}
		</ul>
	);
};

export default MillerColumnsColumn;
