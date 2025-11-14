/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItemAction from '../actions/duplicateItem';
import {ITEM_INTERACTION_ORIGINS} from '../config/constants/itemInteractionOrigins';
import FragmentService from '../services/FragmentService';
import filterSelectedItems from './filterSelectedItems';

export default function duplicateItem({itemIds, selectItems = () => {}}) {
	return (dispatch, getState) => {
		const {layoutData, segmentsExperienceId} = getState();

		FragmentService.duplicateItem({
			itemIds: filterSelectedItems(itemIds, layoutData.items),
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(
			({
				duplicatedFragmentEntryLinks,
				duplicatedItemIds,
				layoutData: nextLayoutData,
				restrictedItemIds,
			}) => {
				dispatch(
					duplicateItemAction({
						addedFragmentEntryLinks: duplicatedFragmentEntryLinks,
						itemIds: duplicatedItemIds,
						layoutData: nextLayoutData,
						restrictedItemIds,
					})
				);

				if (duplicatedItemIds) {
					selectItems(duplicatedItemIds, {
						origin: ITEM_INTERACTION_ORIGINS.itemActions,
					});
				}
			}
		);
	};
}
