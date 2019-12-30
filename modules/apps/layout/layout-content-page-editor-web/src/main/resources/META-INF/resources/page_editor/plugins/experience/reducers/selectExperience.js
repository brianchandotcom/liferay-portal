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

import {switchLayoutData, selectExperience} from './utils';

function selectExperienceReducer(state, payload) {
	let nextState = state;

	const newExperience = payload;

	// TODO updatePageEditorLayoutData call

	nextState = switchLayoutData(nextState, {
		currentExperienceId: nextState.segmentsExperienceId,
		targetExperienceId: newExperience.segmentsExperienceId
	});
	nextState = selectExperience(nextState, newExperience.segmentsExperienceId);

	return nextState;
}

export default selectExperienceReducer;
