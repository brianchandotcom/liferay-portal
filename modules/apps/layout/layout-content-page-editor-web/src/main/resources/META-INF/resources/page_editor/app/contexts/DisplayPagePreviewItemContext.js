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

export const DisplayPagePreviewItemContextProvider = ({children}) => {
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
};

export const useDisplayPagePreviewItem = () =>
	useContext(SelectedItemStateContext).selectedItem;

export const useDisplayPageRecentPreviewItemList = () =>
	useContext(SelectedItemStateContext).recentItemList;

export const useSelectDisplayPagePreviewItem = () => {
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
};
