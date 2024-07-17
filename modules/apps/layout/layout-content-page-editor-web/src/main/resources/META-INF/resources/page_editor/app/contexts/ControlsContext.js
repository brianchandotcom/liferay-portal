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
	activeItemIds: Liferay.FeatureFlags['LPD-18221'] ? [] : null,
	activeItemType: null,
	multiSelectIsActive: false,
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

const getActiveItemIds = (activeItemIds, itemId) =>
	activeItemIds.includes(itemId)
		? activeItemIds.filter((activeItemId) => activeItemId !== itemId)
		: [...activeItemIds, itemId];

const reducer = (state, action) => {
	const {itemId, itemType, multiSelectIsActive, origin, type} = action;
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
			itemId !== nextState.activeItemIds)
	) {
		nextState = {
			...nextState,
			activationOrigin: origin,
			activeItemIds: Liferay.FeatureFlags['LPD-18221']
				? nextState.multiSelectIsActive
					? getActiveItemIds(nextState.activeItemIds, itemId)
					: itemId
						? [itemId]
						: []
				: itemId,
			activeItemType: itemType,
		};
	}
	else if (type === MULTI_SELECT) {
		nextState = {
			...nextState,
			multiSelectIsActive,
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
	const {activeItemIds} = useContext(ActiveStateContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(itemId) =>
			Liferay.FeatureFlags['LPD-18221']
				? activeItemIds.includes(toControlsId(itemId))
				: activeItemIds === toControlsId(itemId),
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
	const sidebarPanelId = useSelector((state) =>
		state.sidebar?.open ? state.sidebar?.panelId : null
	);
	const sidebarHidden = useSelector((state) => state.sidebar?.hidden);
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
				!sidebarHidden &&
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
		[
			activeDispatch,
			sidebarHidden,
			sidebarPanelId,
			storeDispatch,
			toControlsId,
		]
	);
};

const useActivateMultiSelect = () => {
	const activeDispatch = useContext(ActiveDispatchContext);

	return useCallback(
		(multiSelectIsActive = false) => {
			activeDispatch({
				multiSelectIsActive,
				type: MULTI_SELECT,
			});
		},
		[activeDispatch]
	);
};

const useMultiSelectIsActivated = () =>
	useContext(ActiveStateContext).multiSelectIsActive;

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
};
