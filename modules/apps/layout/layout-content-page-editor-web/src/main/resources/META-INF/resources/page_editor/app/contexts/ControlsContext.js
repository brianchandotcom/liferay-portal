/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useContext, useReducer} from 'react';

import {fromControlsId} from '../components/layout_data_items/Collection';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import {useToControlsId} from './CollectionItemContext';
import {useDispatch, useSelector} from './StoreContext';

const ACTIVE_INITIAL_STATE = {
	activationOrigin: null,
	activeItemId: null,
	activeItemType: null,
};

const HOVER_INITIAL_STATE = {
	hoveredItemId: null,
};

const HOVER_ITEM = 'HOVER_ITEM';
const SELECT_ITEM = 'SELECT_ITEM';

const ActiveStateContext = React.createContext(ACTIVE_INITIAL_STATE);
const ActiveDispatchContext = React.createContext(() => {});

const HoverStateContext = React.createContext(HOVER_INITIAL_STATE);
const HoverDispatchContext = React.createContext(() => {});

const reducer = (state, action) => {
	const {itemId, itemType, origin, type} = action;
	let nextState = state;

	if (type === HOVER_ITEM && itemId !== nextState.hoveredItemId) {
		nextState = {
			...nextState,
			activationOrigin: origin,
			hoveredItemId: itemId,
			hoveredItemType: itemType,
		};
	}
	else if (type === SELECT_ITEM && itemId !== nextState.activeItemId) {
		nextState = {
			...nextState,
			activationOrigin: origin,
			activeItemId: itemId,
			activeItemType: itemType,
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

const useActiveItemId = () =>
	fromControlsId(useContext(ActiveStateContext).activeItemId);

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
			{
				itemType = ITEM_TYPES.layoutDataItem,
				origin = ITEM_ACTIVATION_ORIGINS.pageEditor,
			} = {
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
	const {activeItemId} = useContext(ActiveStateContext);
	const toControlsId = useToControlsId();

	return useCallback((itemId) => activeItemId === toControlsId(itemId), [
		activeItemId,
		toControlsId,
	]);
};

const useIsHovered = () => {
	const {hoveredItemId} = useContext(HoverStateContext);
	const toControlsId = useToControlsId();

	return useCallback((itemId) => hoveredItemId === toControlsId(itemId), [
		hoveredItemId,
		toControlsId,
	]);
};

const useSelectItem = () => {
	const activeDispatch = useContext(ActiveDispatchContext);
	const sidebarPanelId = useSelector((state) =>
		state.sidebar?.open ? state.sidebar?.panelId : null
	);
	const storeDispatch = useDispatch();
	const toControlsId = useToControlsId();

	return useCallback(
		(
			itemId,
			{
				itemType = ITEM_TYPES.layoutDataItem,
				origin = ITEM_ACTIVATION_ORIGINS.pageEditor,
			} = {
				itemType: ITEM_TYPES.layoutDataItem,
			}
		) => {
			activeDispatch({
				itemId: toControlsId(itemId),
				itemType,
				origin,
				type: SELECT_ITEM,
			});

			if (
				itemId &&
				sidebarPanelId &&
				!['browser', 'comments', 'page_content'].includes(
					sidebarPanelId
				)
			) {
				storeDispatch(
					switchSidebarPanel({
						sidebarOpen: true,
						sidebarPanelId: 'browser',
					})
				);
			}
		},
		[activeDispatch, sidebarPanelId, storeDispatch, toControlsId]
	);
};

export {
	ControlsProvider,
	reducer,
	useActivationOrigin,
	useActiveItemId,
	useActiveItemType,
	useHoveredItemId,
	useHoveredItemType,
	useHoveringOrigin,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useSelectItem,
};
