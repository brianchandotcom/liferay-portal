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
			nextState = createExperienceReducer(nextState, action.payload);
			break;
		case DELETE_SEGMENTS_EXPERIENCE:
			nextState = deleteExperienceReducer(nextState, action.payload);
			break;
		case SELECT_SEGMENTS_EXPERIENCE:
			nextState = selectExperienceReducer(nextState, action.payload);
			break;
		case UPDATE_SEGMENTS_EXPERIENCE:
			nextState = updateExperienceReducer(nextState, action.payload);
			break;
		case UPDATE_SEGMENTS_EXPERIENCES_LIST:
			nextState = updateExperiencesListReducer(nextState, action.payload);
			break;
		default:
			break;
	}

	return nextState;
};

export default reducer;
