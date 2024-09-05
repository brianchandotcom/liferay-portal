/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentEntryLinks from '../actions/addFragmentEntryLinks';
import {FORM_DEFAULT_NUMBER_OF_STEPS} from '../config/constants/formDefaultNumberOfSteps';
import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import FragmentService from '../services/FragmentService';
import selectFirstControlsItem from '../utils/selectFirstControlsItem';

export default function addFragment({
	fieldTypes,
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

		const updateState = (fragmentEntryLinks, layoutData, itemId) => {
			dispatch(
				addFragmentEntryLinks({
					addedItemId: itemId,
					fragmentEntryLinks,
					layoutData,
				})
			);

			selectFirstControlsItem({
				itemId,
				layoutData,
				selectItems,
			});
		};

		if (type === FRAGMENT_ENTRY_TYPES.composition) {
			return FragmentService.addFragmentEntryLinks(params).then(
				({addedItemId, fragmentEntryLinks, layoutData}) => {
					updateState(
						Object.values(fragmentEntryLinks),
						layoutData,
						addedItemId
					);
				}
			);
		}
		else {
			if (fieldTypes.includes('stepper')) {
				const form = getState().layoutData.items[parentItemId];

				params.numberOfSteps =
					form.config.formType === 'simple'
						? FORM_DEFAULT_NUMBER_OF_STEPS
						: form.config.numberOfSteps;

				return FragmentService.addStepperFragmentEntryLink(params).then(
					({addedItemId, fragmentEntryLinks, layoutData}) => {
						updateState(
							fragmentEntryLinks,
							layoutData,
							addedItemId
						);
					}
				);
			}

			return FragmentService.addFragmentEntryLink(params).then(
				({addedItemId, fragmentEntryLink, layoutData}) => {
					updateState([fragmentEntryLink], layoutData, addedItemId);
				}
			);
		}
	};
}
