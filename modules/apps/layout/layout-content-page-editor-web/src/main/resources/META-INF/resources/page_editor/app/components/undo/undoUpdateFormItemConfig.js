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

import updateFormItemConfig from '../../actions/updateFormItemConfig';
import updateItemLocalConfig from '../../actions/updateItemLocalConfig';
import LayoutService from '../../services/LayoutService';
import getFragmentItem from '../../utils/getFragmentItem';

function undoAction({action, store}) {
	const {
		config,
		deletedItems,
		isMapping,
		itemId,
		removedFragmentEntryLinkIds,
		restoredFragmentEntryLinkIds,
	} = action;

	const {layoutData} = store;

	const removedItems = removedFragmentEntryLinkIds.map((id) => ({
		itemId: getFragmentItem(layoutData, id).itemId,
	}));

	const restoredItemIds = restoredFragmentEntryLinkIds.map(
		(id) => getFragmentItem(layoutData, id).itemId
	);

	const item = layoutData.items[itemId];

	const nextLayoutData = {
		...layoutData,
		deletedItems: [...deletedItems, ...removedItems],
		items: {
			...layoutData.items,
			[itemId]: {
				...item,
				children: isMapping ? restoredItemIds : item.children,
				config,
			},
		},
	};

	return (dispatch) => {
		if (isMapping) {
			dispatch(
				updateItemLocalConfig({
					disableUndo: true,
					itemConfig: {
						loading: true,
					},
					itemId,
				})
			);
		}

		return LayoutService.updateLayoutData({
			layoutData: nextLayoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(() => {
			dispatch(
				updateFormItemConfig({
					deletedItems,
					isMapping,
					itemId,
					layoutData: nextLayoutData,
					removedFragmentEntryLinkIds,
					restoredFragmentEntryLinkIds,
				})
			);
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {
		addedFragmentEntryLinks,
		isMapping,
		itemId,
		removedFragmentEntryLinkIds,
		restoredFragmentEntryLinkIds,
	} = action;

	const {layoutData} = state;

	const item = layoutData.items[itemId];

	return {
		config: {...item.config, loading: false},
		deletedItems: layoutData.deletedItems,
		isMapping,
		itemId,
		removedFragmentEntryLinkIds: addedFragmentEntryLinks
			? Object.keys(addedFragmentEntryLinks)
			: restoredFragmentEntryLinkIds,
		restoredFragmentEntryLinkIds: removedFragmentEntryLinkIds,
	};
}

export {undoAction, getDerivedStateForUndo};
