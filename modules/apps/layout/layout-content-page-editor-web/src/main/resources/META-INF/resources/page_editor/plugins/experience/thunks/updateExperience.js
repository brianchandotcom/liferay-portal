/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ExperienceService from '../../../app/services/ExperienceService';
import updateExperienceAction from '../actions/updateExperience';

export default function updateExperience({
	name,
	segmentsEntryId,
	segmentsExperienceId,
}) {
	return (dispatch) => {
		return ExperienceService.updateExperience({
			body: {
				active: true,
				name,
				segmentsEntryId,
				segmentsExperienceId,
			},
			dispatch,
		}).then(() => {
			return dispatch(
				updateExperienceAction({
					name,
					segmentsEntryId,
					segmentsExperienceId,
				})
			);
		});
	};
}
