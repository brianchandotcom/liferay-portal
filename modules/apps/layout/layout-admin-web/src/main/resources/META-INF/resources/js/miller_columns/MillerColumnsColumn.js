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
import React, {useEffect, useRef} from 'react';
import {useDrop} from 'react-dnd';

import MillerColumnsItem from './MillerColumnsItem';
import {ACCEPTING_TYPES} from './constants';

const isValidTarget = (source, parent) => {
	return !!(
		parent &&
		(source.columnIndex > parent.columnIndex + 1 ||
			(source.columnIndex === parent.columnIndex + 1 &&
				source.itemIndex < parent.childrenCount - 1) ||
			(parent.parentable &&
				source.columnIndex <= parent.columnIndex &&
				!source.active))
	);
};

const MillerColumnsColumn = ({
	actionHandlers,
	items = [],
	namespace,
	onItemDrop,
	onItemStayHover,
	parent,
}) => {
	const ref = useRef();

	const [{canDrop}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(source, monitor) {
			return (
				monitor.isOver({shallow: true}) && isValidTarget(source, parent)
			);
		},
		collect: monitor => ({
			canDrop: !!monitor.canDrop(),
		}),
		drop(source) {
			if (canDrop) {
				onItemDrop(source.id, parent.id);
			}
		},
	});

	useEffect(() => {
		drop(ref);
	}, [drop]);

	return (
		<ul
			className={classNames(
				'col-11 col-lg-4 col-md-6 miller-columns-col show-quick-actions-on-hover',
				{
					'drop-target': canDrop,
				}
			)}
			ref={ref}
		>
			{items.map((item, index) => (
				<MillerColumnsItem
					actionHandlers={actionHandlers}
					item={{...item, itemIndex: index}}
					key={item.key}
					namespace={namespace}
					onItemDrop={onItemDrop}
					onItemStayHover={onItemStayHover}
				/>
			))}
		</ul>
	);
};

export default MillerColumnsColumn;
