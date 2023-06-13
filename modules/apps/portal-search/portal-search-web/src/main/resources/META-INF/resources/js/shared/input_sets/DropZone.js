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

import getCN from 'classnames';
import React from 'react';
import {useDrop} from 'react-dnd';

import {ITEM_TYPES} from './itemTypes';

function DropZone({index, move}) {
	const [{isOver}, drop] = useDrop(
		{
			accept: ITEM_TYPES.ITEM,
			collect: (monitor) => ({
				isOver: !!monitor.isOver(),
			}),
			drop: (source) => {
				move(source.index, index);
			},
		},
		[move]
	);

	return (
		<div
			className="input-sets-item-drop-zone-root"
			ref={drop}
			style={{padding: '10px 0'}}
		>
			<div
				className={getCN('input-sets-item-drop-zone-over', {
					'bg-primary': isOver,
				})}
				style={{height: '4px'}}
			/>
		</div>
	);
}

export default DropZone;
