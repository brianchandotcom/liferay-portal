/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addStepperAction from '../actions/addStepper';
import {FORM_DEFAULT_NUMBER_OF_STEPS} from '../config/constants/formDefaultNumberOfSteps';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import FragmentService from '../services/FragmentService';
import selectFirstControlsItem from '../utils/selectFirstControlsItem';

export default function addStepper({
	fragmentEntryKey,
	groupId,
	parentItemId,
	position,
	selectItems = () => {},
	type,
}) {
	return (dispatch, getState) => {
		const form = getState().layoutData.items[parentItemId];

		const numberOfSteps =
			form.config.formType === 'simple'
				? FORM_DEFAULT_NUMBER_OF_STEPS
				: form.config.numberOfSteps;

		return FragmentService.addStepperFragmentEntryLink({
			fragmentEntryKey,
			groupId,
			numberOfSteps,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId: getState().segmentsExperienceId,
			type,
		}).then(
			({
				addedItemIds,
				fragmentEntryLinks,
				layoutData,
				movedItemIds,
				removedItemIds,
			}) => {
				const stepperId = addedItemIds.find(
					(id) =>
						layoutData.items[id].type ===
						LAYOUT_DATA_ITEM_TYPES.fragment
				);

				dispatch(
					addStepperAction({
						addedItemIds,
						formId: parentItemId,
						fragmentEntryLinks,
						itemId: stepperId,
						layoutData,
						movedItemIds,
						removedItemIds,
					})
				);

				selectFirstControlsItem({
					itemId: stepperId,
					layoutData,
					selectItems,
				});
			}
		);
	};
}
