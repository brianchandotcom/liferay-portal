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

import {removeExperience} from '../utils/FragmentsEditorFetchUtils.es';
import {
	getRowFragmentEntryLinkIds,
	getRowIndex
} from '../utils/FragmentsEditorGetUtils.es';
import {containsFragmentEntryLinkId} from '../utils/LayoutDataList.es';
import {REMOVE_ROW} from './actions.es';
import {removeFragmentEntryLinksAction} from './removeFragmentEntryLinks.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';
import {updateWidgetsAction} from './updateWidgets.es';

/**
 * Removes a row of the layout data structure
 * @param {number} rowId
 * @review
 */
function removeRowAction(rowId) {
	return function(dispatch, getState) {
		const state = getState();

		dispatch(_removeRowAction(rowId));

		const fragmentEntryLinkIds = getRowFragmentEntryLinkIds(
			state.layoutData.structure[
				getRowIndex(state.layoutData.structure, rowId)
			]
		);

		const fragmentEntryLinkIdsToRemove = fragmentEntryLinkIds.filter(
			fragmentEntryLinkId =>
				!containsFragmentEntryLinkId(
					state.layoutDataList,
					fragmentEntryLinkId,
					state.segmentsExperienceId ||
						state.defaultSegmentsExperienceId
				)
		);

		const fragmentEntryLinkIdsToRemoveExperience = fragmentEntryLinkIds.filter(
			fragmentEntryLinkId =>
				containsFragmentEntryLinkId(
					state.layoutDataList,
					fragmentEntryLinkId,
					state.segmentsExperienceId ||
						state.defaultSegmentsExperienceId
				)
		);

		if (fragmentEntryLinkIdsToRemoveExperience.length > 0) {
			removeExperience(
				state.segmentsExperienceId || state.defaultSegmentsExperienceId,
				fragmentEntryLinkIdsToRemoveExperience,
				false
			);
		}

		dispatch(updateWidgetsAction(fragmentEntryLinkIds));

		dispatch(updatePageEditorLayoutDataAction());

		dispatch(removeFragmentEntryLinksAction(fragmentEntryLinkIdsToRemove));
	};
}

function _removeRowAction(rowId) {
	return {
		rowId,
		type: REMOVE_ROW
	};
}

export {removeRowAction};
