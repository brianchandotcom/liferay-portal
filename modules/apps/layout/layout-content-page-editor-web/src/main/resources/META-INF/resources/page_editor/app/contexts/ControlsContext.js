/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	useCallback,
	useContext,
	useEffect,
	useReducer,
	useRef,
	useState,
} from 'react';

import {ITEM_TYPES} from '../config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {MULTI_SELECT_TYPES} from '../config/constants/multiSelectTypes';
import {useSelectorRef} from './StoreContext';

const ACTIVE_INITIAL_STATE = {
	activationOrigin: null,
	activeItemIds: [],
	activeItemType: null,
	rangeLimitIds: [],
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

const MultiSelectStateContext = React.createContext({
	multiSelectType: null,
});

const MultiSelectStateRefContext = React.createContext({
	multiSelectionTypeRef: React.createRef(),
});
const MultiSelectDispatchContext = React.createContext(() => {});

/**
 * This method includes a new item in the active items. If this item is already
 * belongs to the active items, it is removed.
 *
 * @param {array} activeItemIds Active item ids.
 * @param {string} itemId Item id to be included in active items.
 */

function getActiveItemIds(activeItemIds, itemId) {
	return activeItemIds.includes(itemId)
		? activeItemIds.filter((activeItemId) => activeItemId !== itemId)
		: [...activeItemIds, itemId];
}

/**
 * This method gets all elements within a selection range
 *
 * First it looks for the item at the start of the range and enable a flag to mark
 * all the elements iterated as included until the end of the range is found.
 *
 * @param {array} items Items to analyze if they are within the range.
 * @param {object} layoutDataItems Layout data items.
 * @param {array} rangeLimitIds This array contains the beginning and end of the range.
 */

export function getItemsWithinRange({itemIds, layoutDataItems, rangeLimitIds}) {
	let activateSelection = false;
	const selectedItems = [];

	const findItemsWithinRange = ({
		itemIds,
		layoutDataItems,
		rangeLimitIds,
	}) => {
		for (const childId of itemIds) {
			const item = layoutDataItems[childId];

			const isLimitId =
				rangeLimitIds.start === childId ||
				rangeLimitIds.end === childId;

			if (isLimitId) {
				activateSelection = !activateSelection;
			}

			if (
				(isLimitId || activateSelection) &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.formStep &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone
			) {
				selectedItems.push(childId);
			}

			findItemsWithinRange({
				itemIds: item.children,
				layoutDataItems,
				rangeLimitIds,
			});
		}
	};

	findItemsWithinRange({
		itemIds,
		layoutDataItems,
		rangeLimitIds,
	});

	return selectedItems;
}

const reducer = (state, action) => {
	const {
		activeItemIds,
		itemId,
		itemType,
		layoutData,
		multiSelect,
		origin,
		parentId,
		type,
	} = action;

	let nextState = state;

	if (type === HOVER_ITEM && itemId !== nextState.hoveredItemId) {
		nextState = {
			...nextState,
			activationOrigin: origin,
			hoveredItemId: itemId,
			hoveredItemType: itemType,
		};
	}
	else if (type === SELECT_ITEM) {
		let rangeLimitIds = {};
		let nextActiveItemIds = [itemId];
		let nextItemType = itemType;

		if (state.activeItemType === ITEM_TYPES.editable) {
			nextActiveItemIds = itemId ? [itemId] : [];
		}
		else if (!itemId) {
			nextActiveItemIds = [];
		}
		else if (multiSelect === MULTI_SELECT_TYPES.simple) {
			if (itemType === ITEM_TYPES.editable) {
				if (state.activeItemIds.includes(parentId)) {
					return state;
				}

				nextActiveItemIds = getActiveItemIds(
					nextState.activeItemIds,
					parentId
				);

				nextItemType = ITEM_TYPES.layoutDataItem;
			}
			else {
				nextActiveItemIds = getActiveItemIds(
					nextState.activeItemIds,
					itemId
				);
			}
		}
		else if (multiSelect === MULTI_SELECT_TYPES.range) {
			let initialActiveItemIds = state.activeItemIds;

			// The last active item id is taken when the first item in the
			// range is selected.

			let startLimitId = [...state.activeItemIds].pop();

			if (
				itemType === ITEM_TYPES.editable &&
				state.activeItemIds.length
			) {
				nextItemType = ITEM_TYPES.layoutDataItem;
			}

			if (state.rangeLimitIds.end) {

				// If a range selection has just been made, and another range
				// selection is made immediately after, the first item id of
				// the range is kept and the activeItemIds from the last range
				// selection are removed.

				startLimitId = state.rangeLimitIds.start || startLimitId;

				initialActiveItemIds = state.activeItemIds.slice(
					0,
					Math.min(
						state.activeItemIds.indexOf(startLimitId),
						state.activeItemIds.indexOf(state.rangeLimitIds.end)
					)
				);
			}

			rangeLimitIds = {end: parentId || itemId, start: startLimitId};

			if (!state.activeItemIds.length) {
				nextActiveItemIds = [itemId];
			}
			else if (
				!rangeLimitIds.start ||
				rangeLimitIds.end === rangeLimitIds.start
			) {

				// If the start and end of the range are the same id, only
				// this item is selected

				nextActiveItemIds = [parentId || itemId];
			}
			else {
				const root = layoutData.items[layoutData.rootItems.main];

				nextActiveItemIds = getItemsWithinRange({
					itemIds: root.children,
					layoutDataItems: layoutData.items,
					rangeLimitIds,
				});

				nextActiveItemIds = [
					...new Set([...initialActiveItemIds, ...nextActiveItemIds]),
				];
			}
		}

		nextState = {
			...nextState,
			activationOrigin: origin,
			activeItemIds: nextActiveItemIds,
			activeItemType: nextItemType,
			rangeLimitIds,
		};
	}
	else if (type === MULTI_SELECT) {
		nextState = {
			...state,
			activeItemIds: activeItemIds || state.activeItemIds,
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

const MultiSelectProvider = ({children}) => {
	const [multiSelectType, setMultiSelectType] = useState(null);
	const multiSelectionTypeRef = useRef(multiSelectType);

	useEffect(() => {
		multiSelectionTypeRef.current = multiSelectType;
	}, [multiSelectType]);

	return (
		<MultiSelectDispatchContext.Provider value={setMultiSelectType}>
			<MultiSelectStateRefContext.Provider value={multiSelectionTypeRef}>
				<MultiSelectStateContext.Provider value={multiSelectType}>
					{children}
				</MultiSelectStateContext.Provider>
			</MultiSelectStateRefContext.Provider>
		</MultiSelectDispatchContext.Provider>
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
				<MultiSelectProvider>{children}</MultiSelectProvider>
			</HoverProvider>
		</ActiveProvider>
	);
};

const useActivationOrigin = () =>
	useContext(ActiveStateContext).activationOrigin;

const useActiveItemIds = () => useContext(ActiveStateContext).activeItemIds;

const useActiveItemType = () => useContext(ActiveStateContext).activeItemType;

const useHoveredItemId = () => useContext(HoverStateContext).hoveredItemId;

const useHoveredItemType = () => useContext(HoverStateContext).hoveredItemType;

const useHoveringOrigin = () => useContext(HoverStateContext).activationOrigin;

const useHoverItem = () => {
	const dispatch = useContext(HoverDispatchContext);

	return useCallback(
		(
			itemId,
			{itemType = ITEM_TYPES.layoutDataItem, origin = null} = {
				itemType: ITEM_TYPES.layoutDataItem,
			}
		) =>
			dispatch({
				itemId,
				itemType,
				origin,
				type: HOVER_ITEM,
			}),
		[dispatch]
	);
};

const useIsActive = () => {
	const {activeItemIds} = useContext(ActiveStateContext);

	return useCallback(
		(itemId) => activeItemIds.includes(itemId),
		[activeItemIds]
	);
};

const useIsHovered = () => {
	const {hoveredItemId} = useContext(HoverStateContext);

	return useCallback((itemId) => hoveredItemId === itemId, [hoveredItemId]);
};

const useSelectItem = () => {
	const activeDispatch = useContext(ActiveDispatchContext);
	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const multiSelectTypeRef = useContext(MultiSelectStateRefContext);

	return useCallback(
		(
			itemId,
			{
				parentId = null,
				itemType = ITEM_TYPES.layoutDataItem,
				origin = null,
			} = {
				itemType: ITEM_TYPES.layoutDataItem,
			}
		) => {
			activeDispatch({
				itemId,
				itemType,
				layoutData: layoutDataRef.current,
				multiSelect: multiSelectTypeRef.current,
				origin,
				parentId,
				type: SELECT_ITEM,
			});
		},
		[activeDispatch, layoutDataRef, multiSelectTypeRef]
	);
};

const useActivateMultiSelect = () => {
	const setMultiSelectType = useContext(MultiSelectDispatchContext);

	return useCallback(
		(multiSelect = null) => {
			setMultiSelectType(multiSelect);
		},
		[setMultiSelectType]
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

const useMultiSelectType = () => useContext(MultiSelectStateContext);

const useMultiSelectTypeRef = () => useContext(MultiSelectStateRefContext);

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
	useMultiSelectType,
	useMultiSelectTypeRef,
	useSelectItem,
	useSelectMultipleItems,
};
