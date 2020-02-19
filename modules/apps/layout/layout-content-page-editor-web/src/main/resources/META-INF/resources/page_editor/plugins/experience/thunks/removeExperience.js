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

import {config} from '../../../app/config/index';
import ExperienceService from '../../../app/services/ExperienceService';
import deleteExperienceAction from '../actions/deleteExperience';
import selectExperienceAction from '../actions/selectExperience';

export default function removeExperience({
	fragmentEntryLinkIds,
	segmentsExperienceId,
	selectedExperienceId
}) {
	return dispatch => {
		if (segmentsExperienceId === selectedExperienceId) {
			return ExperienceService.selectExperience({
				body: {
					segmentsExperienceId: config.defaultSegmentsExperienceId
				},
				dispatch
			}).then(portletIds => {
				dispatch(
					selectExperienceAction({
						portletIds,
						segmentsExperienceId: config.defaultSegmentsExperienceId
					})
				);

				ExperienceService.removeExperience({
					body: {
						fragmentEntryLinkIds,
						segmentsExperienceId
					},
					dispatch
				}).then(() => {
					return dispatch(
						deleteExperienceAction({
							segmentsExperienceId
						})
					);
				});
			});
		}
		else {
			return ExperienceService.removeExperience({
				body: {
					fragmentEntryLinkIds,
					segmentsExperienceId
				},
				dispatch
			}).then(() => {
				return dispatch(
					deleteExperienceAction({
						segmentsExperienceId
					})
				);
			});
		}
	};
}
