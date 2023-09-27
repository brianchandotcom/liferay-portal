/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateColSize from '../actions/updateColSize';
import LayoutService from '../services/LayoutService';

export default function resizeColumns({layoutData, rowItemId}) {
	return (dispatch, getState) => {
		return LayoutService.updateLayoutData({
			layoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(() => {
			dispatch(updateColSize({layoutData, rowItemId}));
		});
	};
}
