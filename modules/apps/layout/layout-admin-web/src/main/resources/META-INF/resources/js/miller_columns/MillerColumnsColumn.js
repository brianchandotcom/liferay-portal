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
            (sourceItem.columnIndex > parentItem.columnIndex + 1 ||
                (sourceItem.columnIndex === parentItem.columnIndex + 1 &&
                    sourceItem.itemIndex < parentItem.childrenCount - 1) ||
                (parentItem.parentable &&
                    sourceItem.columnIndex <= parentItem.columnIndex &&
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
				onItemDrop(sourceItem.id, parent.id);
			}
		}
	});

	useEffect(() => {
		drop(ref);
	}, [drop]);

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
					item={{...item, itemIndex: index}}
					key={item.url}
					namespace={namespace}
					onItemDrop={onItemDrop}
				/>
			))}
		</ul>
	);
};

export default MillerColumnsColumn;
