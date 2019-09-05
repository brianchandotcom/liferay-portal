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

import React from 'react';
import {useDrop} from 'react-dnd';
import Button from '../../components/button/Button.es';
import Table from '../../components/table/Table.es';

const generateItems = (columns, rows = 10) => {
	const items = [];

	for (let i = 0; i < rows; i++) {
		items.push(generateItem(columns, i));
	}

	return items;
};

const generateItem = (columns, index) =>
	columns.reduce(
		(acc, column) => ({
			...acc,
			[column]: `${column} ${index + 1}`
		}),
		{}
	);

const DropZone = ({columns, onAddColumn, onRemoveColumn}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: 'fieldType',
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: item => {
			onAddColumn(item.label);
		}
	});

	if (columns.length == 0) {
		return (
			<div className="empty-drop-zone" ref={drop}>
				{Liferay.Language.get(
					'drag-columns-from-the-sidebar-and-drop-here'
				)}
			</div>
		);
	}

	return (
		<Table
			actions={[]}
			columns={columns.map(fieldName => ({
				key: fieldName,
				value: (
					<div className="container p-0">
						<div className="row align-items-center">
							<div className="col">{fieldName}</div>
							<div className="col-md-auto">
								<Button
									borderless
									displayType="secondary"
									onClick={() => onRemoveColumn(fieldName)}
									symbol="trash"
									tooltip={Liferay.Language.get('remove')}
								/>
							</div>
						</div>
					</div>
				)
			}))}
			items={generateItems(columns)}
			ref={drop}
		/>
	);
};

export default DropZone;
