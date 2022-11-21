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

import React, {useCallback, useContext, useState} from 'react';

import {TARGET_POSITIONS} from '../utils/drag-and-drop/constants/targetPositions';
import isItemContainerFlex from '../utils/isItemContainerFlex';
import {useSelectorRef} from './StoreContext';

const INITIAL_STATE = {
	setSource: () => {},
	setTarget: () => {},
	setText: () => {},
	source: null,
	target: {
		itemId: null,
		position: null,
	},
	text: null,
};

const KeyboardMovementContext = React.createContext(INITIAL_STATE);

function KeyboardMovementContextProvider({children}) {
	const [source, setSource] = useState(null);
	const [target, setTarget] = useState({
		itemId: null,
		position: null,
	});
	const [text, setText] = useState(null);

	return (
		<KeyboardMovementContext.Provider
			value={{
				setSource,
				setTarget,
				setText,
				source,
				target,
				text,
			}}
		>
			{children}
		</KeyboardMovementContext.Provider>
	);
}

function useDisableKeyboardMovement() {
	const {setSource, setTarget} = useContext(KeyboardMovementContext);

	return useCallback(() => {
		setSource(null);
		setTarget({
			itemId: null,
			position: null,
		});
	}, [setSource, setTarget]);
}

function useMovementSource() {
	return useContext(KeyboardMovementContext).source;
}

function useMovementTarget() {
	return useContext(KeyboardMovementContext).target;
}

function useMovementTargetPosition() {
	const {target} = useContext(KeyboardMovementContext);
	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const targetItem = layoutDataRef.current.items[target.itemId];
	const parentItem = layoutDataRef.current.items[targetItem?.parentId];

	if (!parentItem || !isItemContainerFlex(parentItem)) {
		return target.position;
	}

	return target.position === TARGET_POSITIONS.BOTTOM
		? TARGET_POSITIONS.RIGHT
		: TARGET_POSITIONS.LEFT;
}

function useMovementText() {
	return useContext(KeyboardMovementContext).text;
}

function useSetMovementSource() {
	return useContext(KeyboardMovementContext).setSource;
}

function useSetMovementTarget() {
	return useContext(KeyboardMovementContext).setTarget;
}

function useSetMovementText() {
	return useContext(KeyboardMovementContext).setText;
}

export {
	KeyboardMovementContextProvider,
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useMovementTargetPosition,
	useMovementText,
	useSetMovementSource,
	useSetMovementTarget,
	useSetMovementText,
};
