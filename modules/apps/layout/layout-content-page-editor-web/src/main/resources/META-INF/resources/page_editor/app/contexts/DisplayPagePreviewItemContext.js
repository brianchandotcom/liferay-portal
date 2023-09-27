/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useContext, useState} from 'react';

import {deepEqual} from '../utils/checkDeepEqual';

/**
 * @typedef PreviewItem
 * @property {string} label
 * @property {object} data
 */

const MAX_RECENT_ITEMS = 100;

const SelectedItemStateContext = React.createContext({

	/** @type {PreviewItem[]} */
	recentItemList: [],

	/** @type {PreviewItem|null} */
	selectedItem: null,
});

const SelectedItemDispatchContext = React.createContext(() => {});

/**
 * @param {PreviewItem} itemA
 * @param {PreviewItem} itemB
 * @returns {boolean}
 */
function itemsAreEqual(itemA, itemB) {
	return deepEqual(itemA, itemB);
}

export function DisplayPagePreviewItemContextProvider({children}) {
	const [state, setState] = useState(() => ({
		recentItemList: [],
		selectedItem: null,
	}));

	return (
		<SelectedItemDispatchContext.Provider value={setState}>
			<SelectedItemStateContext.Provider value={state}>
				{children}
			</SelectedItemStateContext.Provider>
		</SelectedItemDispatchContext.Provider>
	);
}

export function useDisplayPagePreviewItem() {
	return useContext(SelectedItemStateContext).selectedItem;
}

export function useDisplayPageRecentPreviewItemList() {
	return useContext(SelectedItemStateContext).recentItemList;
}

export function useSelectDisplayPagePreviewItem() {
	const setState = useContext(SelectedItemDispatchContext);

	return useCallback(

		/** @param {PreviewItem|null} selectedItem */
		(selectedItem) =>
			setState(({recentItemList}) => {
				let nextRecentItemList = recentItemList;

				if (selectedItem) {
					nextRecentItemList = [
						selectedItem,
						...recentItemList,
					].slice(0, MAX_RECENT_ITEMS);

					nextRecentItemList = nextRecentItemList.filter(
						(item, index) =>
							index ===
							nextRecentItemList.findIndex((recentItem) =>
								itemsAreEqual(item, recentItem)
							)
					);
				}

				return {
					recentItemList: nextRecentItemList,
					selectedItem,
				};
			}),
		[setState]
	);
}
