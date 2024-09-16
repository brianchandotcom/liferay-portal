/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moveStepperAction from '../actions/moveStepper';
import {FORM_DEFAULT_NUMBER_OF_STEPS} from '../config/constants/formDefaultNumberOfSteps';
import LayoutService from '../services/LayoutService';

export default function moveStepper({itemId, parentItemId, position = 0}) {
	return (dispatch, getState) => {
		const {fragmentEntryLinks, layoutData, segmentsExperienceId} =
			getState();

		const item = layoutData.items[itemId];

		const fragment = fragmentEntryLinks[item.config.fragmentEntryLinkId];

		const form = layoutData.items[parentItemId];

		const numberOfSteps =
			form.config.formType === 'simple'
				? FORM_DEFAULT_NUMBER_OF_STEPS
				: form.config.numberOfSteps;

		return LayoutService.moveStepper({
			fragmentEntryLinkId: fragment.fragmentEntryLinkId,
			itemId,
			numberOfSteps,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId,
		}).then(({addedItemIds, layoutData, movedItemIds, removedItemIds}) => {
			dispatch(
				moveStepperAction({
					addedItemIds,
					formId: parentItemId,
					itemId,
					layoutData,
					movedItemIds,
					removedItemIds,
				})
			);
		});
	};
}
