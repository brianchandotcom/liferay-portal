/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CREATE_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	SELECT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCES_LIST,
} from '../actions';
import createExperienceReducer from './createExperience';
import deleteExperienceReducer from './deleteExperience';
import selectExperienceReducer from './selectExperience';
import updateExperienceReducer from './updateExperience';
import updateExperiencesListReducer from './updateExperiencesList';

const reducer = (state, action) => {
	let nextState = state;

	switch (action.type) {
		case CREATE_SEGMENTS_EXPERIENCE:
			nextState = createExperienceReducer(nextState, {
				fragmentEntryLinks: action.payload.fragmentEntryLinks,
				layoutData: action.payload.layoutData,
				segmentsExperience: action.payload.segmentsExperience,
			});
			nextState = selectExperienceReducer(nextState, {
				segmentsExperienceId:
					action.payload.segmentsExperience.segmentsExperienceId,
			});
			break;
		case DELETE_SEGMENTS_EXPERIENCE:
			nextState = deleteExperienceReducer(nextState, {
				segmentsExperienceId: action.payload.segmentsExperienceId,
			});
			break;
		case SELECT_SEGMENTS_EXPERIENCE:
			nextState = selectExperienceReducer(nextState, {
				fragmentEntryLinks: action.payload.fragmentEntryLinks,
				segmentsExperienceId: action.payload.segmentsExperienceId,
			});
			break;
		case UPDATE_SEGMENTS_EXPERIENCE:
			nextState = updateExperienceReducer(nextState, {
				updatedExperience: action.payload,
			});
			break;
		case UPDATE_SEGMENTS_EXPERIENCES_LIST:
			nextState = updateExperiencesListReducer(nextState, {
				availableSegmentsExperiences:
					action.payload.availableSegmentsExperiences,
			});
			break;
		default:
			if (!state.loadedSegmentsExperiences) {
				nextState = {
					...state,
					loadedSegmentsExperiences: [state.segmentsExperienceId],
				};
			}

			break;
	}

	return nextState;
};

export default reducer;
