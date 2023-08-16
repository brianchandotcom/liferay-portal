/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import updateFormItemConfigAction from '../actions/updateFormItemConfig';
import updateItemLocalConfig from '../actions/updateItemLocalConfig';
import FormService from '../services/FormService';

export default function updateFormItemConfig({
	itemConfig,
	itemId,
	overridePreviousConfig = true,
}) {
	const isMapping = Boolean(itemConfig.classNameId);

	return (dispatch, getState) => {
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

		return FormService.updateFormItemConfig({
			itemConfig,
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(
			({
				addedFragmentEntryLinks,
				errorMessage,
				layoutData,
				removedFragmentEntryLinkIds,
			}) => {
				dispatch(
					updateFormItemConfigAction({
						addedFragmentEntryLinks,
						isMapping,
						itemId,
						layoutData,
						overridePreviousConfig,
						removedFragmentEntryLinkIds,
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
