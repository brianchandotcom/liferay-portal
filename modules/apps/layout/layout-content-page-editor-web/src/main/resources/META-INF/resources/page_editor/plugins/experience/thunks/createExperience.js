/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ExperienceService from '../../../app/services/ExperienceService';
import createExperienceAction from '../actions/createExperience';

export default function createExperience({name, segmentsEntryId}) {
	return (dispatch) => {
		return ExperienceService.createExperience({
			body: {
				name,
				segmentsEntryId,
			},
			dispatch,
		}).then(({fragmentEntryLinks, layoutData, segmentsExperience}) => {
			return ExperienceService.selectExperience({
				body: {
					segmentsExperienceId:
						segmentsExperience.segmentsExperienceId,
				},
				dispatch,
			})
				.then((portletIds) => {
					return dispatch(
						createExperienceAction({
							fragmentEntryLinks,
							layoutData,
							portletIds,
							segmentsExperience,
						})
					);
				})
				.catch((error) => {
					return error;
				});
		});
	};
}
