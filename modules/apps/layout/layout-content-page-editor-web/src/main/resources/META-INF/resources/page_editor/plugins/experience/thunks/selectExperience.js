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

import updatePageContents from '../../../app/actions/updatePageContents';
import ExperienceService from '../../../app/services/ExperienceService';
import InfoItemService from '../../../app/services/InfoItemService';
import selectExperienceAction from '../actions/selectExperience';

export default function selectExperience({id}) {
	return (dispatch) => {
		return ExperienceService.selectExperience({
			body: {
				segmentsExperienceId: id,
			},
			dispatch,
		})
			.then((portletIds) => {
				return dispatch(
					selectExperienceAction({
						portletIds,
						segmentsExperienceId: id,
					})
				);
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
					segmentsExperienceId: id,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			})
			.catch((error) => {
				return error;
			});
	};
}
