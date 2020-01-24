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

import ExperienceService from '../../../app/services/ExperienceService';
import createExperienceAction from '../actions/createExperience';
import selectExperienceAction from '../actions/selectExperience';

export default function createExperience({name, segmentsEntryId}, config) {
	return dispatch => {
		return ExperienceService.createExperience({
			body: {
				name,
				segmentsEntryId
			},
			config,
			dispatch
		}).then(({fragmentEntryLinks, layoutData, segmentsExperience}) => {
			ExperienceService.selectExperience({
				body: {
					segmentsExperienceId:
						segmentsExperience.segmentsExperienceId
				},
				config,
				dispatch
			})
				.then(portletIds => {
					return dispatch(
						selectExperienceAction({
							portletIds,
							segmentsExperienceId:
								segmentsExperience.segmentsExperienceId
						})
					);
				})
				.catch(error => {
					return error;
				});

			return dispatch(
				createExperienceAction({
					fragmentEntryLinks,
					layoutData,
					segmentsExperience
				})
			);
		});
	};
}
