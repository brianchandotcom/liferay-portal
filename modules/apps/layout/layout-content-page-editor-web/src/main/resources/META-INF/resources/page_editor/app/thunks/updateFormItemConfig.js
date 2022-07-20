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

import updateFormItemConfigAction from '../actions/updateFormItemConfig';
import updateItemLocalConfig from '../actions/updateItemLocalConfig';
import FormService from '../services/FormService';

export default function updateFormItemConfig({itemConfig, itemId}) {
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
				layoutData,
				removedFragmentEntryLinkIds,
			}) => {
				dispatch(
					updateFormItemConfigAction({
						addedFragmentEntryLinks,
						itemId,
						layoutData,
						overridePreviousConfig: true,
						removedFragmentEntryLinkIds,
					})
				);
			}
		);
	};
}
