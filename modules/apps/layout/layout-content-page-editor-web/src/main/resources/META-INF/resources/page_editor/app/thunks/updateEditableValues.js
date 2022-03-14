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

import updateEditableValuesAction from '../actions/updateEditableValues';
import updatePageContents from '../actions/updatePageContents';
import {config} from '../config/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';

export default function updateEditableValues({
	editableValues,
	fragmentEntryLinkId,
	languageId,
	segmentsExperienceId,
}) {
	return (dispatch) =>
		FragmentService.updateEditableValues({
			editableValues,
			fragmentEntryLinkId,
			languageId,
			onNetworkStatus: dispatch,
		})
			.then((fragmentEntryLink) => {
				dispatch(
					updateEditableValuesAction({
						content: fragmentEntryLink.content,
						editableValues,
						fragmentEntryLinkId,
						segmentsExperienceId,
					})
				);
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
							segmentsExperienceId:
								config.defaultSegmentsExperienceId,
						})
					);
				});
			});
}
