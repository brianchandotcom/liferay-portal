/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItemAction from '../actions/duplicateItem';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import FragmentService from '../services/FragmentService';
import getFirstControlsId from '../utils/getFirstControlsId';
import filterSelectedItems from './filterSelectedItems';

export default function duplicateItem({itemIds, selectItems = () => {}}) {
	return (dispatch, getState) => {
		const {layoutData} = getState();

		FragmentService.duplicateItem({
			itemIds: filterSelectedItems(itemIds, layoutData),
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
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
					const itemIds = duplicatedItemIds.map((itemId) =>
						getFirstControlsId({
							item: nextLayoutData.items[itemId],
							layoutData: nextLayoutData,
						})
					);

					selectItems(itemIds, {
						origin: ITEM_ACTIVATION_ORIGINS.itemActions,
					});
				}
			}
		);
	};
}
