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

import {
	removeExperience,
	removeFragmentEntryLinks
} from '../utils/FragmentsEditorFetchUtils.es';
import {containsFragmentEntryLinkId} from '../utils/LayoutDataList.es';
import {REMOVE_FRAGMENT_ENTRY_LINK} from './actions.es';
import {updatePageContentsAction} from './updatePageContents.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';
import {updateWidgetsAction} from './updateWidgets.es';

/**
 * Removes a list of fragment entry links
 * @param {array} fragmentEntryLinks
 * @review
 */
function removeFragmentEntryLinksAction(fragmentEntryLinks) {
	return function(dispatch) {
		return removeFragmentEntryLinks(fragmentEntryLinks).then(() =>
			dispatch(updatePageContentsAction())
		);
	};
}

/**
 * Removes a fragment entry link
 * @param {string} fragmentEntryLinkId
 * @review
 */
function removeFragmentEntryLinkAction(fragmentEntryLinkId) {
	return function(dispatch, getState) {
		const state = getState();

		const fragmentEntryLinkIsUsedInOtherExperience = containsFragmentEntryLinkId(
			state.layoutDataList,
			fragmentEntryLinkId,
			state.segmentsExperienceId || state.defaultSegmentsExperienceId
		);

		dispatch(updateWidgetsAction([fragmentEntryLinkId]));

		dispatch(
			_removeFragmentEntryAction(
				fragmentEntryLinkId,
				fragmentEntryLinkIsUsedInOtherExperience
			)
		);

		dispatch(updatePageEditorLayoutDataAction());

		if (!fragmentEntryLinkIsUsedInOtherExperience) {
			dispatch(removeFragmentEntryLinksAction([fragmentEntryLinkId]));
		}
		else {
			removeExperience(
				state.segmentsExperienceId,
				[fragmentEntryLinkId],
				false
			);
		}
	};
}

function _removeFragmentEntryAction(
	fragmentEntryLinkId,
	fragmentEntryLinkIsUsedInOtherExperience
) {
	return {
		fragmentEntryLinkId,
		fragmentEntryLinkIsUsedInOtherExperience,
		type: REMOVE_FRAGMENT_ENTRY_LINK
	};
}

export {removeFragmentEntryLinksAction, removeFragmentEntryLinkAction};
