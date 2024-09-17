/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormLayoutDataItem} from '../../../types/layout_data/FormLayoutDataItem';
import {FragmentLayoutDataItem} from '../../../types/layout_data/FragmentLayoutDataItem';
import addStepper from '../../actions/addStepper';
import updateFormItemConfig from '../../actions/updateFormItemConfig';
import {Dispatch} from '../../contexts/StoreContext';
import {State} from '../../reducers';
import LayoutService from '../../services/LayoutService';

function undoAction({
	action,
	store,
}: {
	action: ReturnType<typeof addStepper> & {form: FormLayoutDataItem};
	store: State;
}) {
	const {addedItemIds, form, movedItemIds, removedItemIds} = action;

	const nextMovedItems: {itemId: string; parentId: string}[] = [];

	movedItemIds.forEach((movedItem) => {
		const item = store.layoutData.items[movedItem.itemId];

		nextMovedItems.push({itemId: item.itemId, parentId: item.parentId});
	});

	return (dispatch: Dispatch) => {
		return LayoutService.undoUpdateFormConfig({
			addedItemIds: removedItemIds,
			itemConfig: form.config,
			itemId: form.itemId,
			movedItemIds,
			onNetworkStatus: dispatch,
			removedItemIds: addedItemIds,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(({layoutData}) => {
			dispatch(
				updateFormItemConfig({
					addedItemIds: removedItemIds,
					isMapping: false,
					itemIds: [form.itemId],
					layoutData,
					movedItemIds: nextMovedItems,
					removedFragmentEntryLinkIds: addedItemIds
						.map((itemId) => {
							const item = layoutData.items[
								itemId
							] as FragmentLayoutDataItem;

							return item?.config?.fragmentEntryLinkId;
						})
						.filter(Boolean),
					removedItemIds: addedItemIds,
					restoredFragmentEntryLinkIds: removedItemIds
						.map((itemId) => {
							const item = layoutData.items[
								itemId
							] as FragmentLayoutDataItem;

							return item?.config?.fragmentEntryLinkId;
						})
						.filter(Boolean),
					triggerItemId: action.itemId,
				})
			);
		});
	};
}

function getDerivedStateForUndo({
	action,
	state,
}: {
	action: ReturnType<typeof addStepper>;
	state: State;
}) {
	const form = state.layoutData.items[action.formId];

	return {...action, form};
}

export {getDerivedStateForUndo, undoAction};
