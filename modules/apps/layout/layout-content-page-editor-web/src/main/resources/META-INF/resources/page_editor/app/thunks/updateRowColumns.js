/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateRowColumnsAction from '../actions/updateRowColumns';
import LayoutService from '../services/LayoutService';

export default function updateRowColumns(payload) {
	return (dispatch, getState) =>
		LayoutService.updateRowColumns({
			...payload,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(({layoutData, pageContents}) => {
			dispatch(
				updateRowColumnsAction({
					...payload,
					layoutData,
					pageContents,
				})
			);
		});
}
