/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
