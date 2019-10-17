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
import {useDrop} from 'react-dnd';
import React from 'react';

import Button from '../../components/button/Button.es';
import Table from '../../components/table/Table.es';
import {DRAG_FIELD_TYPE} from '../../utils/dragTypes.es';

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

const DropZone = ({fields, onAddFieldName, onRemoveFieldName}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: DRAG_FIELD_TYPE,
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: ({data: {name}}) => {
			onAddFieldName(name);
		}
	});

	if (fields.length == 0) {
		return (
			<div
				className={classNames('empty-drop-zone', {
					'target-droppable': canDrop,
					'target-over': overTarget
				})}
				ref={drop}
			>
				<p className="m-0">
					{Liferay.Language.get(
						'drag-columns-from-the-sidebar-and-drop-here'
					)}
				</p>
			</div>
		);
	}

	return (
		<Table
			actions={[]}
			columns={fields.map(({label: {en_US: label}, name}) => ({
				key: label,
				value: (
					<div className="container p-0">
						<div className="row align-items-center">
							<div className="col">{label}</div>
							<div className="col-md-auto">
								<Button
									borderless
									displayType="secondary"
									onClick={() => onRemoveFieldName(name)}
									symbol="trash"
									tooltip={Liferay.Language.get('remove')}
								/>
							</div>
						</div>
					</div>
				)
			}))}
			items={generateItems(fields.map(field => field.label.en_US))}
			ref={drop}
		/>
	);
};

export default DropZone;
