/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addItemAction from '../actions/addItem';
import LayoutService from '../services/LayoutService';
import selectFirstControlsItem from '../utils/selectFirstControlsItem';
import {clearPageContents} from '../utils/usePageContents';

export default function addItem({
	itemType,
	parentItemId,
	position,
	selectItems = () => {},
}) {
	return (dispatch, getState) => {
		const {segmentsExperienceId} = getState();

		return LayoutService.addItem({
			itemType,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId,
		}).then(({addedItemId, layoutData}) => {
			dispatch(addItemAction({itemIds: [addedItemId], layoutData}));

			clearPageContents();

			if (addedItemId) {
				selectFirstControlsItem({
					itemId: addedItemId,
					layoutData,
					selectItems,
				});
			}
		});
	};
}
