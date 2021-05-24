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

import {ReactPortal} from '@liferay/frontend-js-react-web';
import React, {useState} from 'react';

import {
	DragAndDropProvider,
	useDropClear,
	useDropTarget,
} from './useDragAndDrop';

const DragAndDropElements = () => {
	const [dragIndicatorPosition, setDragIndicatorPosition] = useState({});
	const [dropTargetColumn, setDropTargetColumn] = useState(null);
	const [dropTargetItem, setDropTargetItem] = useState(null);

	const columnHasChildren =
		dropTargetColumn &&
		!dropTargetColumn.firstElementChild.classList.contains('empty');
	const dropItems = Array.from(
		document.querySelectorAll(
			'.portlet-dropzone, .portlet-dropzone .portlet-boundary'
		)
	);
	const body = document.body;

	return (
		<DragAndDropProvider
			value={{
				dragIndicatorPosition,
				dropTargetColumn,
				dropTargetItem,
				setDragIndicatorPosition,
				setDropTargetColumn,
				setDropTargetItem,
			}}
		>
			{columnHasChildren && (
				<ReactPortal>
					<DragIndicator position={dragIndicatorPosition} />
				</ReactPortal>
			)}

			{dropItems.map((dropItem, index) => (
				<DropTarget dropItem={dropItem} key={index} />
			))}
			<Wrapper item={body} />
		</DragAndDropProvider>
	);
};

const DragIndicator = ({position}) => {
	return (
		<div
			className="sortable-layout-drag-indicator sortable-layout-drag-target-indicator"
			style={{
				left: position.clientX,
				top: position.clientY,
				width: position.width,
			}}
		></div>
	);
};

const DropTarget = ({dropItem}) => {
	useDropTarget(dropItem);

	return null;
};

const Wrapper = ({item}) => {
	useDropClear(item);

	return null;
};

export default DragAndDropElements;
