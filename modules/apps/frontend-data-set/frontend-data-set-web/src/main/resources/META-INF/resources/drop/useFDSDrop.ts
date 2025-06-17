/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useCallback, useContext, useRef} from 'react';
import {type DropTargetMonitor, useDrop} from 'react-dnd';
import {NativeTypes} from 'react-dnd-html5-backend';

import FrontendDataSetContext from '../FrontendDataSetContext';
import isFileDropEnabled from '../utils/isFileDropEnabled';

const useFDSDrop = ({item}: {item?: any}) => {
	const {fileDropSettings, handleFileDrop} = useContext(
		FrontendDataSetContext
	);

	const nonDroppableRef = useRef(null);

	const canDrop = useCallback(
		(item: any) =>
			fileDropSettings?.canReceiveDrop
				? fileDropSettings.canReceiveDrop({item})
				: true,
		[fileDropSettings]
	);

	const isDropTarget = item ? canDrop(item) : true;

	const [{isOverCurrent}, dropRef] = useDrop({
		accept: isFileDropEnabled(fileDropSettings) ? [NativeTypes.FILE] : [],
		canDrop() {
			return isFileDropEnabled(fileDropSettings) && isDropTarget;
		},
		collect: (monitor: DropTargetMonitor) => {
			return {
				isOverCurrent:
					isFileDropEnabled(fileDropSettings) &&
					isDropTarget &&
					monitor.isOver({shallow: true}),
			};
		},
		drop(fileItem: any, monitor) {
			if (monitor.isOver({shallow: true})) {
				handleFileDrop(fileItem, item);
			}
		},
	});

	return {
		className: classNames({'drop-target': isOverCurrent}),
		dropRef: isDropTarget ? dropRef : nonDroppableRef,
		isOverCurrent,
	};
};

export default useFDSDrop;
