/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ExperienceService from '../../../app/services/ExperienceService';
import updateExperiencesListAction from '../actions/updateExperiencesList';

export default function updateExperiencePriority({
	priority,
	segmentsExperienceId,
}) {
	return (dispatch) => {
		return ExperienceService.updateExperiencePriority({
			body: {
				newPriority: priority,
				segmentsExperienceId,
			},
			dispatch,
		}).then(({availableSegmentsExperiences}) => {
			return dispatch(
				updateExperiencesListAction(availableSegmentsExperiences)
			);
		});
	};
}
