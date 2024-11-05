/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useMemo, useState} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {isItemHidden} from '../utils/isItemHidden';
import {isLayoutDataItemDeleted} from '../utils/isLayoutDataItemDeleted';
import {useSelector} from './StoreContext';

const LayoutKeyboardContext = React.createContext({
	itemList: [],
	setTargetId: () => {},
	targetId: null,
});

function LayoutKeyboardContextProvider({children}) {
	const layoutData = useSelector((state) => state.layoutData);
	const viewportSize = useSelector((state) => state.selectedViewportSize);

	const itemList = useMemo(() => {
		const list = [];

		visit(layoutData.rootItems.main, layoutData, list, viewportSize);

		return list;
	}, [layoutData, viewportSize]);

	const [targetId, setTargetId] = useState(null);
	const [targetIndex, setTargetIndex] = useState(null);

	// Store target index

	useEffect(() => {
		if (targetId) {
			setTargetIndex(itemList.indexOf(targetId));
		}
	}, [itemList, setTargetId, targetId]);

	// When removing or hiding an item, target the next/previous

	useEffect(() => {
		if (targetId && targetIndex && !itemList.includes(targetId)) {
			const nextIndex =
				targetIndex < itemList.length ? targetIndex : targetIndex - 1;

			const nextId = itemList[nextIndex];

			setTargetId(nextId || null);
			setTargetIndex(null);
		}
	}, [itemList, setTargetId, targetId, targetIndex]);

	return (
		<LayoutKeyboardContext.Provider
			value={{itemList, setTargetId, targetId}}
		>
			{children}
		</LayoutKeyboardContext.Provider>
	);
}

function visit(itemId, layoutData, list, viewportSize) {
	const {items} = layoutData;

	const item = items[itemId];

	if (
		isSelectable(item) &&
		!isLayoutDataItemDeleted(layoutData, itemId) &&
		!isItemHidden(layoutData, itemId, viewportSize, {recursive: true})
	) {
		list.push(itemId);
	}

	if (!item.children.length) {
		return;
	}

	for (const childId of item.children) {
		visit(childId, layoutData, list, viewportSize);
	}
}

function isSelectable(item) {
	if (Liferay.FeatureFlags['LPD-18221']) {
		if (
			item.type === LAYOUT_DATA_ITEM_TYPES.root &&
			!item.children.length
		) {
			return true;
		}

		return [
			LAYOUT_DATA_ITEM_TYPES.column,
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.form,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.formStep,
			LAYOUT_DATA_ITEM_TYPES.formStepContainer,
			LAYOUT_DATA_ITEM_TYPES.fragmentDropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
		].includes(item.type);
	}
	else {
		return [
			LAYOUT_DATA_ITEM_TYPES.column,
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.form,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.formStepContainer,
			LAYOUT_DATA_ITEM_TYPES.row,
		].includes(item.type);
	}
}

export {LayoutKeyboardContext, LayoutKeyboardContextProvider};
