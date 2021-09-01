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
