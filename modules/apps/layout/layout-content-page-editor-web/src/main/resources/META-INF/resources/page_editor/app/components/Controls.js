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

import React, {useCallback, useContext, useReducer} from 'react';

import {ITEM_TYPES} from '../config/constants/itemTypes';

const INITIAL_STATE = {
	activeItemId: null,
	editingItemId: null,
	hoveredItemId: null,
	selectedItemsIds: []
};

const HOVER_ITEM = 'HOVER_ITEM';
const SELECT_EDITING_ITEM = 'SELECT_EDITING_ITEM';
const SELECT_ITEM = 'SELECT_ITEM';

const ControlsContext = React.createContext([INITIAL_STATE, () => {}]);
const ControlsConsumer = ControlsContext.Consumer;

const reducer = (state, action) => {
	const {itemId, itemType, multiSelect, type} = action;
	let nextState = state;

	if (type === HOVER_ITEM && itemId !== nextState.hoveredItemId) {
		nextState = {
			...nextState,
			hoveredItemId: itemId,
			hoveredItemType: itemType
		};
	}
	else if (
		type === SELECT_EDITING_ITEM &&
		itemId !== nextState.editingItemId
	) {
		nextState = {...nextState, editingItemId: itemId};
	}
	else if (type === SELECT_ITEM) {
		if (multiSelect && itemId) {
			const wasSelected = state.selectedItemsIds.includes(itemId);

			nextState = {
				...nextState,
				activeItemId: wasSelected ? null : itemId,
				activeItemType: itemType,
				selectedItemsIds: wasSelected
					? state.selectedItemsIds.filter(id => id !== itemId)
					: state.selectedItemsIds.concat([itemId])
			};
		}
		else if (itemId && itemId !== nextState.activeItemId) {
			nextState = {
				...nextState,
				activeItemId: itemId,
				activeItemType: itemType,
				selectedItemsIds: [itemId]
			};
		}
		else if (
			nextState.activeItemId ||
			nextState.selectedItemsIds.length
		) {
			nextState = {
				...nextState,
				activeItemId: null,
				activeItemType: null,
				selectedItemsIds: []
			};
		}
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

const useActiveItemId = () => {
	const [state] = useContext(ControlsContext);

	return state.activeItemId;
};

const useEditingItemId = () => {
	const [state] = useContext(ControlsContext);

	return state.editingItemId;
};

const useHoveredItemId = () => {
	const [state] = useContext(ControlsContext);

	return state.hoveredItemId;
};

const useHoveredItemType = () => {
	const [state] = useContext(ControlsContext);

	return state.hoveredItemType;
};

const useHoverItem = () => {
	const [, dispatch] = useContext(ControlsContext);

	return useCallback(
		(
			itemId,
			{itemType = ITEM_TYPES.layoutDataItem} = {
				itemType: ITEM_TYPES.layoutDataItem
			}
		) =>
			dispatch({
				itemId,
				itemType,
				type: HOVER_ITEM
			}),
		[dispatch]
	);
};

const useIsActive = () => {
	const [state] = useContext(ControlsContext);

	return useCallback(itemId => state.activeItemId === itemId, [
		state.activeItemId
	]);
};

const useIsHovered = () => {
	const [state] = useContext(ControlsContext);

	return useCallback(itemId => state.hoveredItemId === itemId, [
		state.hoveredItemId
	]);
};

const useIsSelected = () => {
	const [state] = useContext(ControlsContext);

	return useCallback(itemId => state.selectedItemsIds.includes(itemId), [
		state.selectedItemsIds
	]);
};

const useSelectEditingItem = () => {
	const [, dispatch] = useContext(ControlsContext);

	return useCallback(
		itemId =>
			dispatch({
				itemId,
				type: SELECT_EDITING_ITEM
			}),
		[dispatch]
	);
};

const useSelectItem = () => {
	const [, dispatch] = useContext(ControlsContext);

	return useCallback(
		(
			itemId,
			{multiSelect = false, itemType = ITEM_TYPES.layoutDataItem} = {
				itemType: ITEM_TYPES.layoutDataItem,
				multiSelect: false
			}
		) =>
			dispatch({
				itemId,
				itemType,
				multiSelect,
				type: SELECT_ITEM
			}),
		[dispatch]
	);
};

export {
	ControlsConsumer,
	ControlsProvider,
	useActiveItemId,
	useEditingItemId,
	useHoveredItemId,
	useHoveredItemType,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useIsSelected,
	useSelectEditingItem,
	useSelectItem
};
