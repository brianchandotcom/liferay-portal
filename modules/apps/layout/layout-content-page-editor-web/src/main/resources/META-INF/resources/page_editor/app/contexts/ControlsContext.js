/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useContext, useReducer} from 'react';

import {fromControlsId} from '../components/layout_data_items/Collection';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {MULTI_SELECT_TYPES} from '../config/constants/multiSelectTypes';
import {useToControlsId} from './CollectionItemContext';

const ACTIVE_INITIAL_STATE = {
	activationOrigin: null,
	activeItemIds: [],
	activeItemType: null,
	multiSelect: null,
};

const HOVER_INITIAL_STATE = {
	hoveredItemId: null,
};

const HOVER_ITEM = 'HOVER_ITEM';
const MULTI_SELECT = 'MULTI_SELECT';
const SELECT_ITEM = 'SELECT_ITEM';

const ActiveStateContext = React.createContext(ACTIVE_INITIAL_STATE);
const ActiveDispatchContext = React.createContext(() => {});

const HoverStateContext = React.createContext(HOVER_INITIAL_STATE);
const HoverDispatchContext = React.createContext(() => {});

/**
 * This method includes a new item in the active items. If this item is already
 * belongs to the active items, it is removed.
 * @param {array} activeItemIds Active item ids.
 * @param {string} itemId Item id to be included in active items.
 */

function getActiveItemIds(activeItemIds, itemId) {
	return activeItemIds.includes(itemId)
		? activeItemIds.filter((activeItemId) => activeItemId !== itemId)
		: [...activeItemIds, itemId];
}

const reducer = (state, action) => {
	const {activeItemIds, itemId, itemType, multiSelect, origin, type} = action;
	let nextState = state;

	if (type === HOVER_ITEM && itemId !== nextState.hoveredItemId) {
		nextState = {
			...nextState,
			activationOrigin: origin,
			hoveredItemId: itemId,
			hoveredItemType: itemType,
		};
	}
	else if (
		type === SELECT_ITEM &&
		(Liferay.FeatureFlags['LPD-18221'] ||
			itemId !== nextState.activeItemIds[0])
	) {
		let nextActiveItemIds = [itemId];

		if (!Liferay.FeatureFlags['LPD-18221']) {
			nextActiveItemIds = itemId ? [itemId] : [];
		}
		else if (!itemId) {
			nextActiveItemIds = [];
		}
		else if (state.multiSelect === MULTI_SELECT_TYPES.simple) {
			nextActiveItemIds = getActiveItemIds(
				nextState.activeItemIds,
				itemId
			);
		}

		nextState = {
			...nextState,
			activationOrigin: origin,
			activeItemIds: nextActiveItemIds,
			activeItemType: itemType,
		};
	}
	else if (type === MULTI_SELECT) {
		nextState = {
			...state,
			activeItemIds: activeItemIds || state.activeItemIds,
			multiSelect,
		};
	}

	return nextState;
};

const ActiveProvider = ({children, initialState}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<ActiveDispatchContext.Provider value={dispatch}>
			<ActiveStateContext.Provider value={state}>
				{children}
			</ActiveStateContext.Provider>
		</ActiveDispatchContext.Provider>
	);
};

const HoverProvider = ({children, initialState}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<HoverDispatchContext.Provider value={dispatch}>
			<HoverStateContext.Provider value={state}>
				{children}
			</HoverStateContext.Provider>
		</HoverDispatchContext.Provider>
	);
};

const ControlsProvider = ({
	activeInitialState = ACTIVE_INITIAL_STATE,
	hoverInitialState = HOVER_INITIAL_STATE,
	children,
}) => {
	return (
		<ActiveProvider initialState={activeInitialState}>
			<HoverProvider initialState={hoverInitialState}>
				{children}
			</HoverProvider>
		</ActiveProvider>
	);
};

const useActivationOrigin = () =>
	useContext(ActiveStateContext).activationOrigin;

const useActiveItemIds = () =>
	fromControlsId(useContext(ActiveStateContext).activeItemIds);

const useActiveItemType = () => useContext(ActiveStateContext).activeItemType;

const useHoveredItemId = () =>
	fromControlsId(useContext(HoverStateContext).hoveredItemId);

const useHoveredItemType = () => useContext(HoverStateContext).hoveredItemType;

const useHoveringOrigin = () => useContext(HoverStateContext).activationOrigin;

const useHoverItem = () => {
	const dispatch = useContext(HoverDispatchContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(
			itemId,
			{itemType = ITEM_TYPES.layoutDataItem, origin = null} = {
				itemType: ITEM_TYPES.layoutDataItem,
			}
		) =>
			dispatch({
				itemId: toControlsId(itemId),
				itemType,
				origin,
				type: HOVER_ITEM,
			}),
		[dispatch, toControlsId]
	);
};

const useIsActive = () => {
	const {activeItemIds} = useContext(ActiveStateContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(itemId) => activeItemIds.includes(toControlsId(itemId)),
		[activeItemIds, toControlsId]
	);
};

const useIsHovered = () => {
	const {hoveredItemId} = useContext(HoverStateContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(itemId) => hoveredItemId === toControlsId(itemId),
		[hoveredItemId, toControlsId]
	);
};

const useSelectItem = () => {
	const activeDispatch = useContext(ActiveDispatchContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(
			itemId,
			{itemType = ITEM_TYPES.layoutDataItem, origin = null} = {
				itemType: ITEM_TYPES.layoutDataItem,
			}
		) => {
			activeDispatch({
				itemId: toControlsId(itemId),
				itemType,
				origin,
				type: SELECT_ITEM,
			});
		},
		[activeDispatch, toControlsId]
	);
};

const useActivateMultiSelect = () => {
	const activeDispatch = useContext(ActiveDispatchContext);

	return useCallback(
		(multiSelect = null) => {
			activeDispatch({
				multiSelect,
				type: MULTI_SELECT,
			});
		},
		[activeDispatch]
	);
};

const useSelectMultipleItems = () => {
	const activeDispatch = useContext(ActiveDispatchContext);

	return useCallback(
		(itemIds, {origin = null} = {}) => {
			activeDispatch({
				activeItemIds: itemIds || [],
				origin,
				type: MULTI_SELECT,
			});
		},
		[activeDispatch]
	);
};

const useMultiSelectIsActivated = () =>
	useContext(ActiveStateContext).multiSelect;

export {
	ControlsProvider,
	reducer,
	useActivateMultiSelect,
	useActivationOrigin,
	useActiveItemIds,
	useActiveItemType,
	useHoveredItemId,
	useHoveredItemType,
	useHoveringOrigin,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useMultiSelectIsActivated,
	useSelectItem,
	useSelectMultipleItems,
};
