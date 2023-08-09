/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemConfigAction from '../actions/updateItemConfig';
import LayoutService from '../services/LayoutService';

export default function updateItemConfig({
	itemConfig,
	itemId,
	overridePreviousConfig = false,
}) {
	return (dispatch, getState) => {
		const {segmentsExperienceId} = getState();

		return LayoutService.updateItemConfig({
			itemConfig,
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({layoutData, pageContents}) => {
			dispatch(
				updateItemConfigAction({
					itemId,
					layoutData,
					overridePreviousConfig,
					pageContents,
				})
			);
		});
	};
}
