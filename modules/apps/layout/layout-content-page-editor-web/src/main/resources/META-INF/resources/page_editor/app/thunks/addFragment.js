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
import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import FragmentService from '../services/FragmentService';

export default function addFragment({
	fragmentEntryKey,
	groupId,
	parentItemId,
	position,
	selectItem = () => {},
	store,
	type,
}) {
	return (dispatch) => {
		const {segmentsExperienceId} = store;

		const params = {
			fragmentEntryKey,
			groupId,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId,
			type,
		};

		const updateState = (fragmentEntryLinks, layoutData, itemId) => {
			dispatch(
				addFragmentEntryLinks({
					addedItemId: itemId,
					fragmentEntryLinks,
					layoutData,
				})
			);

			selectItem(itemId);
		};

		if (type === FRAGMENT_ENTRY_TYPES.composition) {
			FragmentService.addFragmentEntryLinks(params).then(
				({addedItemId, fragmentEntryLinks, layoutData}) => {
					updateState(
						Object.values(fragmentEntryLinks),
						layoutData,
						addedItemId
					);
				}
			);
		}
		else {
			FragmentService.addFragmentEntryLink(params).then(
				({addedItemId, fragmentEntryLink, layoutData}) => {
					updateState([fragmentEntryLink], layoutData, addedItemId);
				}
			);
		}
	};
}
