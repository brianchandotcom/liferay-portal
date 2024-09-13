/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addStepperAction from '../actions/addStepper';
import {FORM_DEFAULT_NUMBER_OF_STEPS} from '../config/constants/formDefaultNumberOfSteps';
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
		const params = {
			fragmentEntryKey,
			groupId,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId: getState().segmentsExperienceId,
			type,
		};

		const form = getState().layoutData.items[parentItemId];

		params.numberOfSteps =
			form.config.formType === 'simple'
				? FORM_DEFAULT_NUMBER_OF_STEPS
				: form.config.numberOfSteps;

		return FragmentService.addStepperFragmentEntryLink(params).then(
			({
				addedItemIds,
				fragmentEntryLinks,
				layoutData,
				movedItemIds,
				removedItemIds,
			}) => {
				dispatch(
					addStepperAction({
						addedItemIds,
						formId: parentItemId,
						fragmentEntryLinks,
						layoutData,
						movedItemIds,
						removedItemIds,
					})
				);

				const [itemId] = addedItemIds;

				selectFirstControlsItem({
					itemId,
					layoutData,
					selectItems,
				});
			}
		);
	};
}
