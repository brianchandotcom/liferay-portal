/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moveItemAction from '../actions/moveItem';
import LayoutService from '../services/LayoutService';

export default function moveItem({itemIds, parentItemIds, positions}) {
	return (dispatch, getState) => {
		return LayoutService.moveItem({
			itemIds,
			onNetworkStatus: dispatch,
			parentItemIds,
			positions,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then((layoutData) => {
			dispatch(moveItemAction({itemIds, layoutData}));
		});
	};
}
