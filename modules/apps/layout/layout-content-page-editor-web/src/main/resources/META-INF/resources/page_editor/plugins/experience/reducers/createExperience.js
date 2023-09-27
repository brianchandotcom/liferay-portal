/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addExperience, storeNewLayoutData} from './utils';

function createExperienceReducer(state, payload) {
	let nextState = state;

	const {fragmentEntryLinks, layoutData, segmentsExperience} = payload;

	const newExperience = {
		...segmentsExperience,
		hasLockedSegmentsExperiment: false,
	};

	nextState = addExperience(nextState, newExperience);
	nextState = storeNewLayoutData(
		nextState,
		newExperience.segmentsExperienceId,
		layoutData
	);
	nextState = {
		...nextState,
		fragmentEntryLinks: {
			...nextState.fragmentEntryLinks,
			...fragmentEntryLinks,
		},
	};

	return nextState;
}

export default createExperienceReducer;
