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

import {getRowFragmentEntryLinkIds} from '../utils/FragmentsEditorGetUtils.es';
import {updateFragmentEntryLinkContent} from './updateEditableValue.es';
import {SELECT_SEGMENTS_EXPERIENCE} from './actions.es';

/**
 * Changes segments experience action
 * @param {number} segmentsExperienceId
 * @review
 */
function selectSegmentExperienceAction(segmentsExperienceId) {
	return function(dispatch) {
		dispatch({
			segmentsExperienceId,
			type: SELECT_SEGMENTS_EXPERIENCE
		});

		dispatch(_renderFragmentEntryLinkContent(segmentsExperienceId));
	};
}

/**
 * Render the fragment entry links when changing the segments experience
 * This will apply configuration changes
 * @param {number} segmentsExperienceId
 * @review
 */
function _renderFragmentEntryLinkContent(segmentsExperienceId) {
	return function(dispatch, getState) {
		const state = getState();

		const layoutDataLists = state.layoutDataList;

		const {layoutData} = layoutDataLists.find(
			layoutData =>
				layoutData.segmentsExperienceId === segmentsExperienceId
		);

		if (!layoutData) {
			return;
		}

		const fragmentEntryLinkIds = _getLayoutDataFragmentEntryLinkIds(
			layoutData
		);

		fragmentEntryLinkIds.forEach(fragmentEntryLinkId => {
			dispatch(
				updateFragmentEntryLinkContent(
					fragmentEntryLinkId,
					segmentsExperienceId
				)
			);
		});
	};
}

/**
 * Get the fragment entry link ids from the layout data object
 * @param {object} layoutData
 * @review
 */
function _getLayoutDataFragmentEntryLinkIds(layoutData) {
	return layoutData.structure.reduce(
		(acc, row) => [...acc, ...getRowFragmentEntryLinkIds(row)],
		[]
	);
}

export {selectSegmentExperienceAction};
