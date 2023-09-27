/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {selectExperience, setExperimentStatus, switchLayoutData} from './utils';

function selectExperienceReducer(state, payload) {
	let nextState = state;

	const {segmentsExperienceId} = payload;

	nextState = switchLayoutData(nextState, {
		currentExperienceId: nextState.segmentsExperienceId,
		targetExperienceId: segmentsExperienceId,
	});

	nextState = selectExperience(nextState, segmentsExperienceId);

	nextState = setExperimentStatus(nextState, segmentsExperienceId);

	nextState = {
		...nextState,
		fragmentEntryLinks: {
			...nextState.fragmentEntryLinks,
			...payload.fragmentEntryLinks,
		},
	};

	return nextState;
}

export default selectExperienceReducer;
