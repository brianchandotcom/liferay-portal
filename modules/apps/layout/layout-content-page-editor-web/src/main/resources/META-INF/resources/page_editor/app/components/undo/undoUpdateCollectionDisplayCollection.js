/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateCollectionDisplayCollection from '../../actions/updateCollectionDisplayCollection';
import LayoutService from '../../services/LayoutService';
import {setIn} from '../../utils/setIn';

const undoAction = ({action, store}) => (dispatch) =>
	LayoutService.restoreCollectionDisplayConfig({
		filterFragmentEntryLinks: action.filterFragmentEntryLinks.map(
			(filterFragmentEntryLink) => ({
				editableValues: filterFragmentEntryLink.editableValues,
				fragmentEntryLinkId:
					filterFragmentEntryLink.fragmentEntryLinkId,
			})
		),
		itemConfig: action.itemConfig,
		itemId: action.itemId,
		onNetworkStatus: dispatch,
		segmentsExperienceId: store.segmentsExperienceId,
	}).then(() => {
		dispatch(
			updateCollectionDisplayCollection({
				fragmentEntryLinks: action.filterFragmentEntryLinks,
				itemId: action.itemId,
				layoutData: setIn(
					store.layoutData,
					['items', action.itemId, 'config'],
					action.itemConfig
				),
				pageContents: action.pageContents,
			})
		);
	});

const getDerivedStateForUndo = ({action, state}) => ({
	filterFragmentEntryLinks: action.fragmentEntryLinks.map(
		({fragmentEntryLinkId}) => {
			const fragmentEntryLink =
				state.fragmentEntryLinks[fragmentEntryLinkId];

			return {
				content: fragmentEntryLink.content,
				editableValues: fragmentEntryLink.editableValues,
				fragmentEntryLinkId,
			};
		}
	),
	itemConfig: state.layoutData.items[action.itemId].config,
	itemId: action.itemId,
	pageContents: state.pageContents,
});

export {undoAction, getDerivedStateForUndo};
