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

import {useEffect, useState} from 'react';

import {ContextMenuState} from '../components/ContextMenu';

const useContextMenu = (active: boolean) => {
	const [contextMenuState, setContextMenuState] = useState<ContextMenuState>({
		actions: [],
		item: {},
		position: {x: 0, y: 0},
		rowIndex: 0,
		visible: false,
	});

	const handleContext = ({
		actions,
		event,
		item,
		rowIndex,
	}: {
		actions: any;
		event: React.MouseEvent;
		item: any;
		rowIndex: number;
	}) => {
		event.preventDefault();

		setContextMenuState({
			actions,
			item,
			position: {
				x: event.clientX,
				y: event.clientY,
			},
			rowIndex,
			visible: !contextMenuState.visible,
		});
	};

	useEffect(() => {
		if (!active) {
			return;
		}

		const handleClick = () =>
			setContextMenuState({
				...contextMenuState,
				visible: false,
			});

		if (contextMenuState.visible) {
			window.addEventListener('click', handleClick);
		}

		return () => {
			if (!active) {
				return;
			}

			return window.removeEventListener('click', handleClick);
		};
	}, [contextMenuState, setContextMenuState, active]);

	return {
		contextMenuState,
		handleContext,
		setContextMenuState,
	};
};

export default useContextMenu;
