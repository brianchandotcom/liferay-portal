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

import updateLayoutData from '../actions/updateLayoutData';
import LayoutService from '../services/LayoutService';

export default function addItem({
	itemType,
	parentItemId,
	position,
	selectItem = () => {},
	store
}) {
	return dispatch => {
		const {segmentsExperienceId} = store;

		LayoutService.addItem({
			itemType,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId
		}).then(({addedItemId, layoutData}) => {
			dispatch(updateLayoutData({layoutData}));

			if (addedItemId) {
				selectItem(addedItemId);
			}
		});
	};
}
