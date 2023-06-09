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

import duplicateItemAction from '../actions/duplicateItem';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import FragmentService from '../services/FragmentService';

export default function duplicateItem({itemId, selectItem = () => {}}) {
	return (dispatch, getState) => {
		FragmentService.duplicateItem({
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(
			({
				duplicatedFragmentEntryLinks,
				duplicatedItemId,
				layoutData,
				restrictedItemIds,
			}) => {
				dispatch(
					duplicateItemAction({
						addedFragmentEntryLinks: duplicatedFragmentEntryLinks,
						itemId: duplicatedItemId,
						layoutData,
						restrictedItemIds,
					})
				);

				if (duplicatedItemId) {
					selectItem(duplicatedItemId, {
						origin: ITEM_ACTIVATION_ORIGINS.itemActions,
					});
				}
			}
		);
	};
}
