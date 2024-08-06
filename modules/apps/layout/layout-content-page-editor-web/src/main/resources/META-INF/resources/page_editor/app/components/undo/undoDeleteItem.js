/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addItem from '../../actions/addItem';
import LayoutService from '../../services/LayoutService';
import getFragmentEntryLinkIdsFromItemId from '../../utils/getFragmentEntryLinkIdsFromItemId';

function undoAction({action, store}) {
	const {itemIds, portletIds} = action;

	return (dispatch) => {
		return LayoutService.unmarkItemsForDeletion({
			itemIds,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(({layoutData}) => {
			const fragmentEntryLinkIds = itemIds.flatMap((itemId) =>
				getFragmentEntryLinkIdsFromItemId({
					itemId,
					layoutData,
				})
			);

			dispatch(
				addItem({
					fragmentEntryLinkIds,
					itemIds,
					layoutData,
					portletIds,
				})
			);
		});
	};
}

function getDerivedStateForUndo({action}) {
	return {
		itemIds: action.itemIds,
		portletIds: action.portletIds,
	};
}

export {undoAction, getDerivedStateForUndo};
