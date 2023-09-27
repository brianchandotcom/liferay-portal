/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
