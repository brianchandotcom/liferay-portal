/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function updateExperiencePriorityReducer(state, {subtarget, target}) {
	const experiences = state.availableSegmentsExperiences;

	const targetExperience = {
		...experiences[target.segmentsExperienceId],
		priority: target.priority,
	};
	const subtargetExperience = {
		...experiences[subtarget.segmentsExperienceId],
		priority: subtarget.priority,
	};

	const updatedExperiences = {
		...experiences,
		[target.segmentsExperienceId]: targetExperience,
		[subtarget.segmentsExperienceId]: subtargetExperience,
	};

	return {
		...state,
		availableSegmentsExperiences: updatedExperiences,
	};
}

export default updateExperiencePriorityReducer;
