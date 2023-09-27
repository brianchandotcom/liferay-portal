/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updatePageContents from '../../../app/actions/updatePageContents';
import ExperienceService from '../../../app/services/ExperienceService';
import InfoItemService from '../../../app/services/InfoItemService';
import selectExperienceAction from '../actions/selectExperience';

export default function selectExperience({id}) {
	return (dispatch, getState) => {
		const loadedSegmentsExperiences = getState().loadedSegmentsExperiences;

		return ExperienceService.selectExperience({
			body: {
				loadFragmentEntryLinks: !loadedSegmentsExperiences.includes(id),
				segmentsExperienceId: id,
			},
			dispatch,
		})
			.then(({fragmentEntryLinks, portletIds}) => {
				return dispatch(
					selectExperienceAction({
						fragmentEntryLinks,
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
