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

import addFragmentEntryLinks from '../actions/addFragmentEntryLinks';
import WidgetService from '../services/WidgetService';

export default function addWidget({
	parentItemId,
	portletId,
	portletItemId,
	position,
	selectItem = () => {},
}) {
	return (dispatch, getState) => {
		WidgetService.addPortlet({
			onNetworkStatus: dispatch,
			parentItemId,
			portletId,
			portletItemId,
			position,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(({addedItemId, fragmentEntryLink, layoutData}) => {
			dispatch(
				addFragmentEntryLinks({
					addedItemId,
					fragmentEntryLinks: [fragmentEntryLink],
					layoutData,
				})
			);

			if (addedItemId) {
				selectItem(addedItemId);
			}
		});
	};
}
