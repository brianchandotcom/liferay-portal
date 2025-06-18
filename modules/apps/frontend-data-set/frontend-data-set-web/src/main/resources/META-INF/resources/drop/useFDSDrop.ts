/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFileDropSettings} from '..';
import classNames from 'classnames';
import {
	MutableRefObject,
	RefObject,
	useCallback,
	useContext,
	useEffect,
	useRef,
} from 'react';
import {type DropTargetMonitor, useDrop} from 'react-dnd';
import {NativeTypes} from 'react-dnd-html5-backend';

import FrontendDataSetContext from '../FrontendDataSetContext';
import isFileDropEnabled from '../utils/isFileDropEnabled';

const useFDSDrop = ({
	fileDropSettings,
	handleFileDrop,
	item,
	targetDropRef,
}: {
	fileDropSettings?: IFileDropSettings;
	handleFileDrop?: Function;
	item?: any;
	targetDropRef?: RefObject<HTMLElement>;
}) => {
	const {
		fileDropSettings: contextFileDropSettings,
		handleFileDrop: contextHandleFileDrop,
	} = useContext(FrontendDataSetContext);

	if (!handleFileDrop) {
		handleFileDrop = contextHandleFileDrop;
	}

	if (!fileDropSettings) {
		fileDropSettings = contextFileDropSettings;
	}

	const nonDroppableRef: MutableRefObject<null> = useRef(null);

	const canDrop = useCallback(
		(item?: any) => {
			if (!item) {
				return true;
			}

			return fileDropSettings?.canReceiveDrop
				? fileDropSettings.canReceiveDrop({item})
				: true;
		},
		[fileDropSettings]
	);

	const [{isOverCurrent}, dropRef] = useDrop({
		accept: isFileDropEnabled(fileDropSettings) ? [NativeTypes.FILE] : [],
		canDrop() {
			return (
				isFileDropEnabled(fileDropSettings as IFileDropSettings) &&
				canDrop(item)
			);
		},
		collect: (monitor: DropTargetMonitor) => {
			return {
				isOverCurrent:
					isFileDropEnabled(fileDropSettings as IFileDropSettings) &&
					canDrop(item) &&
					monitor.isOver({shallow: true}),
			};
		},
		drop(fileItem: any, monitor) {
			if (monitor.isOver({shallow: true})) {
				handleFileDrop && handleFileDrop(fileItem, item);
			}
		},
	});

	useEffect(() => {
		if (
			targetDropRef &&
			targetDropRef.current &&
			canDrop(item) &&
			isFileDropEnabled(fileDropSettings as IFileDropSettings)
		) {
			dropRef(targetDropRef);
		}
	}, [canDrop, dropRef, item, fileDropSettings, targetDropRef]);

	return {
		className: classNames({'drop-target': isOverCurrent}),
		dropRef: canDrop(item) ? dropRef : nonDroppableRef,
		isOverCurrent,
	};
};

export default useFDSDrop;
