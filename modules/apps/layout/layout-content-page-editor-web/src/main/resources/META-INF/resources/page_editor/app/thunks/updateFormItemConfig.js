/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import updateFormItemConfigAction from '../actions/updateFormItemConfig';
import FormService from '../services/FormService';

export default function updateFormItemConfig({fields, itemConfig, itemIds}) {
	const isMapping = Boolean(itemConfig.classNameId);
	const [itemId] = itemIds;

	return (dispatch, getState) => {
		return FormService.updateFormItemConfig({
			fields,
			itemConfig,
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(
			({
				addedFragmentEntryLinks,
				addedItemIds,
				errorMessage,
				layoutData,
				movedItemIds,
				removedItemIds,
			}) => {
				dispatch(
					updateFormItemConfigAction({
						addedFragmentEntryLinks,
						addedItemIds,
						isMapping,
						itemIds: [itemId],
						layoutData,
						movedItemIds,
						removedFragmentEntryLinkIds: removedItemIds
							.map((itemId) => {
								const item = layoutData.items[itemId];

								return item?.config?.fragmentEntryLinkId;
							})
							.filter(Boolean),
						removedItemIds,
						restoredFragmentEntryLinkIds: addedItemIds
							.map((itemId) => {
								const item = layoutData.items[itemId];

								return item?.config?.fragmentEntryLinkId;
							})
							.filter(Boolean),
					})
				);

				if (errorMessage) {
					openToast({
						message: errorMessage,
						type: 'danger',
					});
				}
				else if (isMapping && itemConfig.classNameId !== '0') {
					openToast({
						message: Liferay.Language.get(
							'your-form-has-been-successfully-loaded'
						),
						type: 'success',
					});
				}
			}
		);
	};
}
