/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import pasteItemAction from '../actions/pasteItems';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import FragmentService from '../services/FragmentService';
import getFirstControlsId from '../utils/getFirstControlsId';
import sortItemIds from '../utils/sortItemIds';
import filterSelectedItems from './filterSelectedItems';

export default function pasteItems({
	clipboard = [],
	parentItemId,
	selectItems = () => {},
}) {
	return (dispatch, getState) => {
		const {layoutData, segmentsExperienceId} = getState();

		FragmentService.pasteItems({
			itemIds: sortItemIds(
				filterSelectedItems(clipboard, layoutData.items),
				layoutData
			),
			onNetworkStatus: dispatch,
			parentItemId,
			segmentsExperienceId,
		}).then(
			({
				copiedFragmentEntryLinks,
				copiedItemIds,
				layoutData: nextLayoutData,
				restrictedItemIds,
			}) => {
				dispatch(
					pasteItemAction({
						addedFragmentEntryLinks: copiedFragmentEntryLinks,
						itemIds: copiedItemIds,
						layoutData: nextLayoutData,
						restrictedItemIds,
					})
				);

				if (copiedItemIds) {
					const itemIds = copiedItemIds.map((itemId) =>
						getFirstControlsId({
							item: nextLayoutData.items[itemId],
							layoutData: nextLayoutData,
						})
					);

					selectItems(
						filterSelectedItems(itemIds, nextLayoutData.items),
						{
							origin: ITEM_ACTIVATION_ORIGINS.itemActions,
						}
					);
				}
			}
		);
	};
}
