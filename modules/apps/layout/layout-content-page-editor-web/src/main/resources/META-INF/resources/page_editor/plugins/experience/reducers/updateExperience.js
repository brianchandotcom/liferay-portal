/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function updateExperienceReducer(state, payload) {
	let nextState = state;

	const {updatedExperience} = payload;

	const experience =
		state.availableSegmentsExperiences[
			updatedExperience.segmentsExperienceId
		];

	if (experience) {
		nextState = {
			...nextState,
			availableSegmentsExperiences: {
				...nextState.availableSegmentsExperiences,
				[experience.segmentsExperienceId]: {
					...experience,
					...updatedExperience,
				},
			},
		};
	}

	return nextState;
}

export default updateExperienceReducer;
