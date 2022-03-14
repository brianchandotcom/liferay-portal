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

import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updatePageContents from '../actions/updatePageContents';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {config} from '../config/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';

export default function updateFragmentConfiguration({
	configurationValues,
	fragmentEntryLink,
}) {
	const {editableValues, fragmentEntryLinkId} = fragmentEntryLink;

	const nextEditableValues = {
		...editableValues,
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: configurationValues,
	};

	return (dispatch, getState) => {
		return FragmentService.updateConfigurationValues({
			editableValues: nextEditableValues,
			fragmentEntryLinkId,
			languageId: getState().languageId,
			onNetworkStatus: dispatch,
		})
			.then(({fragmentEntryLink, layoutData}) => {
				dispatch(
					updateFragmentEntryLinkConfiguration({
						fragmentEntryLink,
						fragmentEntryLinkId,
						layoutData,
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
	};
}
