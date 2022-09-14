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
	segmentsExperienceId,
	selectedExperienceId,
}) {
	return (dispatch, getState) => {
		if (segmentsExperienceId === selectedExperienceId) {
			const loadedSegmentsExperiences = getState()
				.loadedSegmentsExperiences;

			return ExperienceService.selectExperience({
				body: {
					loadFragmentEntryLinks: !loadedSegmentsExperiences.includes(
						config.defaultSegmentsExperienceId
					),
					segmentsExperienceId: config.defaultSegmentsExperienceId,
				},
				dispatch,
			})
				.then(({fragmentEntryLinks, portletIds}) => {
					return dispatch(
						selectExperienceAction({
							fragmentEntryLinks,
							portletIds,
							segmentsExperienceId:
								config.defaultSegmentsExperienceId,
						})
					);
				})
				.then(() => {
					ExperienceService.removeExperience({
						body: {
							segmentsExperienceId,
						},
						dispatch,
					}).then(() => {
						return dispatch(
							deleteExperienceAction({
								segmentsExperienceId,
							})
						);
					});
				});
		}
		else {
			return ExperienceService.removeExperience({
				body: {
					segmentsExperienceId,
				},
				dispatch,
			}).then(() => {
				return dispatch(
					deleteExperienceAction({
						segmentsExperienceId,
					})
				);
			});
		}
	};
}
