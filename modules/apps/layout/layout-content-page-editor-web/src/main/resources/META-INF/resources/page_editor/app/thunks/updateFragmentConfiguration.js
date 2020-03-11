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

import updateEditableValues from '../actions/updateEditableValues';
import updateFragmentEntryLinkContent from '../actions/updateFragmentEntryLinkContent';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import FragmentService from '../services/FragmentService';

export default function updateFragmentConfiguration({
	configurationValues,
	fragmentEntryLink,
	prefixedSegmentsExperienceId,
	segmentsExperienceId,
}) {
	const {editableValues, fragmentEntryLinkId} = fragmentEntryLink;

	const nextEditableValues = {
		...editableValues,
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: prefixedSegmentsExperienceId
			? {
					...editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR],
					[prefixedSegmentsExperienceId]: configurationValues,
			  }
			: configurationValues,
	};

	return dispatch => {
		return FragmentService.updateConfigurationValues({
			configurationValues: nextEditableValues,
			fragmentEntryLinkId,
			onNetworkStatus: dispatch,
		}).then(({content, editableValues}) => {
			dispatch(
				updateEditableValues({
					editableValues,
					fragmentEntryLinkId,
					segmentsExperienceId,
				})
			);

			dispatch(
				updateFragmentEntryLinkContent({
					content,
					fragmentEntryLinkId,
				})
			);
		});
	};
}
