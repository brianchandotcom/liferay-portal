/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
