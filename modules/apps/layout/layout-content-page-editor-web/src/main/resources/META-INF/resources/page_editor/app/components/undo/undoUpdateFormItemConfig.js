/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateFormItemConfig from '../../actions/updateFormItemConfig';
import LayoutService from '../../services/LayoutService';
import {getStepperChild} from '../../utils/getStepperChild';

function undoAction({action, store}) {
	const {
		addedItemIds,
		config,
		deletedItems,
		isMapping,
		itemIds,
		movedItemIds,
		removedItemIds,
		stepperFragmentEntryLinkId,
	} = action;

	const [itemId] = itemIds;

	const nextMovedItems = [];

	movedItemIds.forEach((movedItem) => {
		const item = store.layoutData.items[movedItem.itemId];

		nextMovedItems.push({itemId: item.itemId, parentId: item.parentId});
	});

	return (dispatch) => {
		return LayoutService.undoUpdateFormConfig({
			addedItemIds: removedItemIds,
			itemConfig: config,
			itemId,
			movedItemIds,
			onNetworkStatus: dispatch,
			removedItemIds: addedItemIds,
			segmentsExperienceId: store.segmentsExperienceId,
			stepperFragmentEntryLinkId,
		}).then(({fragmentEntryLinks, layoutData}) => {
			dispatch(
				updateFormItemConfig({
					addedItemIds: removedItemIds,
					deletedItems,
					fragmentEntryLinks,
					isMapping,
					itemIds: [itemId],
					layoutData,
					movedItemIds: nextMovedItems,
					removedFragmentEntryLinkIds: addedItemIds
						.map((itemId) => {
							const item = layoutData.items[itemId];

							return item?.config?.fragmentEntryLinkId;
						})
						.filter(Boolean),
					removedItemIds: addedItemIds,
					restoredFragmentEntryLinkIds: removedItemIds
						.map((itemId) => {
							const item = layoutData.items[itemId];

							return item?.config?.fragmentEntryLinkId;
						})
						.filter(Boolean),
					triggerItemId: action.triggerItemId,
				})
			);
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {
		addedItemIds,
		isMapping,
		itemIds,
		movedItemIds,
		removedItemIds,
		triggerItemId,
	} = action;

	const {fragmentEntryLinks, layoutData} = state;
	const [itemId] = itemIds;

	const item = layoutData.items[itemId];

	const stepper = getStepperChild(item, layoutData, fragmentEntryLinks);

	return {
		addedItemIds,
		config: {...item.config, loading: false},
		deletedItems: layoutData.deletedItems,
		isMapping,
		itemIds: [itemId],
		movedItemIds,
		removedItemIds,
		stepperFragmentEntryLinkId: stepper?.config.fragmentEntryLinkId,
		triggerItemId,
	};
}

export {getDerivedStateForUndo, undoAction};
