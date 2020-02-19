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

import addFragmentEntryLink from '../actions/addFragmentEntryLink';
import FragmentService from '../services/FragmentService';

export default function addFragment({
	config,
	fragmentEntryKey,
	groupId,
	parentItemId,
	position,
	selectItem = () => {},
	store
}) {
	return dispatch => {
		const {segmentsExperienceId} = store;

		FragmentService.addFragmentEntryLink({
			fragmentEntryKey,
			groupId,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId
		}).then(({addedItemId, fragmentEntryLink, layoutData}) => {
			dispatch(
				addFragmentEntryLink({
					fragmentEntryLink,
					layoutData
				})
			);

			if (addedItemId) {
				selectItem(addedItemId);
			}
		});
	};
}
