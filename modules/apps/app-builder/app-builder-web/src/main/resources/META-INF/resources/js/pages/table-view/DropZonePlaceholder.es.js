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
import {DragTypes} from 'data-engine-taglib';
import React, {useLayoutEffect, useState} from 'react';
import {useDrop} from 'react-dnd';

import {getColumnNode, getColumns} from './utils.es';

const getLeft = (container, index) => {
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

const getWidth = (container, index, total) => {
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

const getStyle = (container, index, total) => {
	return {
		height: container.offsetHeight,
		left: getLeft(container, index),
		position: 'absolute',
		top: container.offsetTop,
		width: getWidth(container, index, total),
	};
};

const Placeholder = ({container, index, onAddFieldName, total}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: DragTypes.DRAG_FIELD_TYPE,
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver(),
		}),
		drop: ({data: {name}}) => {
			onAddFieldName(name, index);
		},
	});

	return (
		<div
			className={classNames({
				'column-drop-zone-left': overTarget && index === 0,
				'column-drop-zone-right': overTarget && index > 0,
				hide: !canDrop,
				'target-over': overTarget,
			})}
			ref={drop}
			style={getStyle(container, index, total)}
		></div>
	);
};

export default ({container, fields, onAddFieldName}) => {
	const [empty, setEmpty] = useState(true);

	useLayoutEffect(() => {
		const columns = getColumns(container);

		setEmpty(columns.length === 0);
	}, [container, fields]);

	if (empty) {
		return null;
	}

	const columnPlaceholders = [];

	for (let i = 0; i < fields.length + 1; i++) {
		columnPlaceholders.push(fields[i] ? fields[i].name : 'last');
	}

	return columnPlaceholders.map((key, index) => (
		<Placeholder
			container={container}
			index={index}
			key={key}
			onAddFieldName={onAddFieldName}
			total={columnPlaceholders.length}
		/>
	));
};
