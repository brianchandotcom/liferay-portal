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

import deleteWidgets from '../actions/deleteWidgets';
import updateLayoutData from '../actions/updateLayoutData';
import updatePageContents from '../actions/updatePageContents';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';

export default function deleteItem({itemId, store}) {
	return dispatch => {
		const {segmentsExperienceId} = store;

		return LayoutService.deleteItem({
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId
		})
			.then(({deletedFragmentEntryLinkIds = [], layoutData}) => {
				const deletedWidgets = deletedFragmentEntryLinkIds
					.map(
						fragmentEntryLinkId =>
							store.fragmentEntryLinks[fragmentEntryLinkId]
					)
					.filter(
						fragmentEntryLink =>
							fragmentEntryLink.editableValues.portletId
					);

				if (deletedWidgets.length) {
					dispatch(deleteWidgets(deletedWidgets));
				}

				dispatch(
					updateLayoutData({deletedFragmentEntryLinkIds, layoutData})
				);
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch
				}).then(pageContents => {
					dispatch(
						updatePageContents({
							pageContents
						})
					);
				});
			});
	};
}
