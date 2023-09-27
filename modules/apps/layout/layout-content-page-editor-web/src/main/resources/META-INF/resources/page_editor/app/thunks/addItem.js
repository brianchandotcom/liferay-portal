/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addItemAction from '../actions/addItem';
import LayoutService from '../services/LayoutService';

export default function addItem({
	itemType,
	parentItemId,
	position,
	selectItem = () => {},
}) {
	return (dispatch, getState) => {
		const {pageContents, segmentsExperienceId} = getState();

		return LayoutService.addItem({
			itemType,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId,
		}).then(({addedItemId, layoutData}) => {
			dispatch(
				addItemAction({itemId: addedItemId, layoutData, pageContents})
			);

			if (addedItemId) {
				selectItem(addedItemId);
			}
		});
	};
}
