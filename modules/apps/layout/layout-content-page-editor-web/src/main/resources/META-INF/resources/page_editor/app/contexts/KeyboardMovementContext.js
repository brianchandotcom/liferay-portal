/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncer} from '@liferay/layout-js-components-web';
import React, {useCallback, useContext, useRef, useState} from 'react';

import {TARGET_POSITIONS} from '../utils/drag_and_drop/constants/targetPositions';
import isItemContainerFlex from '../utils/isItemContainerFlex';
import {useSelectorRef} from './StoreContext';

const INITIAL_STATE = {
	setSources: () => {},
	setTarget: () => {},
	sources: [],
	target: {
		itemId: null,
		position: null,
	},
};

const KeyboardMovementContext = React.createContext(INITIAL_STATE);

function KeyboardMovementContextProvider({children}) {
	const [sources, setSources] = useState([]);
	const [target, setTarget] = useState({
		itemId: null,
		position: null,
	});
	const screenReaderAnnouncerRef = useRef();

	const setText = useCallback((text) => {
		const ref = screenReaderAnnouncerRef;

		if (ref.current) {
			ref.current?.sendMessage(text);
		}
	}, []);

	return (
		<KeyboardMovementContext.Provider
			value={{
				setSources,
				setTarget,
				setText,
				sources,
				target,
			}}
		>
			<ScreenReaderAnnouncer
				aria-live="assertive"
				ref={screenReaderAnnouncerRef}
			/>

			{children}
		</KeyboardMovementContext.Provider>
	);
}

function useDisableKeyboardMovement() {
	const {setSources, setTarget} = useContext(KeyboardMovementContext);

	return useCallback(() => {
		setSources([]);
		setTarget({
			itemId: null,
			position: null,
		});
	}, [setSources, setTarget]);
}

function useMovementSources() {
	return useContext(KeyboardMovementContext).sources;
}

function useMovementTarget() {
	return useContext(KeyboardMovementContext).target;
}

function useMovementTargetPosition() {
	const {target} = useContext(KeyboardMovementContext);
	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const targetItem = layoutDataRef.current?.items[target.itemId];
	const parentItem = layoutDataRef.current?.items[targetItem?.parentId];

	if (!parentItem || !isItemContainerFlex(parentItem)) {
		return target.position;
	}

	return target.position === TARGET_POSITIONS.BOTTOM
		? TARGET_POSITIONS.RIGHT
		: TARGET_POSITIONS.LEFT;
}

function useSetMovementSources() {
	return useContext(KeyboardMovementContext).setSources;
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
	useMovementSources,
	useMovementTarget,
	useMovementTargetPosition,
	useSetMovementSources,
	useSetMovementTarget,
	useSetMovementText,
};
