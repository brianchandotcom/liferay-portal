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
import React, {useRef} from 'react';

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

const generateItem = columns =>
	columns.reduce(
		(acc, column) => ({
			...acc,
			[column]: `-`
		}),
		{}
	);

const getColumnNode = (container, index) => {
	return container.querySelector(
		`table tbody > tr:first-of-type > td:nth-of-type(${index + 1})`
	);
};

const getPlaceholderLeft = (container, index) => {
	switch (index) {
		case 0: {
			return getColumnNode(container, index).offsetLeft;
		}
		case 1: {
			const columnNode = getColumnNode(container, index);
			const previousColumnNode = getColumnNode(container, index - 1);

			return columnNode.offsetLeft - previousColumnNode.offsetWidth / 2;
		}
		default: {
			return getColumnNode(container, index - 1).offsetLeft;
		}
	}
};

const getPlaceholderWidth = (container, index, total) => {
	switch (index) {
		case 0: {
			return getColumnNode(container, index).offsetWidth / 2;
		}
		case 1: {
			return getColumnNode(container, index - 1).offsetWidth / 2;
		}
		case total - 1: {
			return getColumnNode(container, index - 1).offsetWidth;
		}
		default: {
			return getColumnNode(container, index).offsetWidth;
		}
	}
};

const getPlaceholderDropZoneStyle = (container, index, total) => {
	return {
		height: container.offsetHeight,
		left: getPlaceholderLeft(container, index),
		position: 'absolute',
		top: container.offsetTop,
		width: getPlaceholderWidth(container, index, total)
	};
};

const PlaceholderDropZone = ({container, index, onAddFieldName, total}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: 'fieldType',
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: ({data: {name}}) => {
			onAddFieldName(name, index);
		}
	});

	return (
		<div
			className={classNames({
				'column-drop-zone-left': overTarget && index === 0,
				'column-drop-zone-right': overTarget && index > 0,
				invisible: !canDrop,
				'target-over': overTarget
			})}
			ref={drop}
			style={getPlaceholderDropZoneStyle(container, index, total)}
		></div>
	);
};

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

	const containerRef = useRef();

	if (fields.length == 0) {
		return (
			<div className="p-4 sheet">
				<div className="empty-drop-zone-header"></div>
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
			</div>
		);
	}

	const columnPlaceholders = [];

	for (let i = 0; i < fields.length + 1; i++) {
		columnPlaceholders.push(fields[i] ? fields[i].name : 'last');
	}

	return (
		<div ref={containerRef}>
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

			{containerRef.current &&
				columnPlaceholders.map((key, index) => (
					<PlaceholderDropZone
						container={containerRef.current}
						index={index}
						key={key}
						onAddFieldName={onAddFieldName}
						total={columnPlaceholders.length}
					/>
				))}
		</div>
	);
};

export default DropZone;
