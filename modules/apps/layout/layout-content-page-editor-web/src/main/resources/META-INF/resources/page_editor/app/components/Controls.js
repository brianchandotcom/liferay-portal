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

import React, {useContext, useReducer} from 'react';

const INITIAL_STATE = {
	activeItemId: null,
	hoveredItemId: null,
	selectedItemsIds: []
};

const HOVER_ITEM = 'HOVER_ITEM';
const SELECT_ITEM = 'SELECT_ITEM';
const FLOATING_TOOLBAR_REFERENCE = 'FLOATING_TOOLBAR_REFERENCE';

const ControlsContext = React.createContext([INITIAL_STATE, () => {}]);
const ControlsConsumer = ControlsContext.Consumer;

const reducer = (state, action) => {
	const {floatingToolbarRef, itemId, multiSelect, type} = action;
	let nextState = state;

	if (type === HOVER_ITEM && itemId !== nextState.hoveredItemId) {
		nextState = {...nextState, hoveredItemId: itemId};
	} else if (type === SELECT_ITEM) {
		nextState = {...state, activeItemId: itemId};

		if (multiSelect && itemId) {
			nextState = {
				...nextState,
				selectedItemsIds: state.selectedItemsIds
					.filter(id => id !== itemId)
					.concat([itemId])
			};
		} else if (itemId) {
			nextState = {...nextState, selectedItemsIds: [itemId]};
		} else if (nextState.selectedItemsIds.length) {
			nextState = {...nextState, selectedItemsIds: []};
		}
	} else if (type === FLOATING_TOOLBAR_REFERENCE) {
		nextState = {...state, floatingToolbarRef};
	}

	return nextState;
};

const ControlsProvider = ({children}) => {
	const stateAndDispatch = useReducer(reducer, INITIAL_STATE);

	return (
		<ControlsContext.Provider value={stateAndDispatch}>
			{children}
		</ControlsContext.Provider>
	);
};

const useHoverItem = () => {
	const [, dispatch] = useContext(ControlsContext);

	return itemId =>
		dispatch({
			itemId,
			type: HOVER_ITEM
		});
};

const useIsActive = () => {
	const [state] = useContext(ControlsContext);
	return itemId => state.activeItemId === itemId;
};

const useIsHovered = () => {
	const [state] = useContext(ControlsContext);
	return itemId => state.hoveredItemId === itemId;
};

const useIsSelected = () => {
	const [state] = useContext(ControlsContext);
	return itemId => state.selectedItemsIds.includes(itemId);
};

const useSelectItem = () => {
	const [, dispatch] = useContext(ControlsContext);

	return (itemId, {multiSelect = false} = {multiSelect: false}) =>
		dispatch({
			itemId,
			multiSelect,
			type: SELECT_ITEM
		});
};

const useFloatingToolbar = () => {
	const [, dispatch] = useContext(ControlsContext);
	return floatingToolbarRef =>
		dispatch({
			floatingToolbarRef,
			type: FLOATING_TOOLBAR_REFERENCE
		});
};

const useCurrentFloatingToolbar = () => {
	const [state] = useContext(ControlsContext);
	return state.floatingToolbarRef;
};

export {
	ControlsConsumer,
	ControlsProvider,
	useCurrentFloatingToolbar,
	useFloatingToolbar,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useIsSelected,
	useSelectItem
};
