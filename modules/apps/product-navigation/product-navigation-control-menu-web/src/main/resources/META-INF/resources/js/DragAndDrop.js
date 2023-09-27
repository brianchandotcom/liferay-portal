/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
