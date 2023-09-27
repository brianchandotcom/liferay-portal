/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import FragmentService from '../services/FragmentService';

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
		const {languageId, segmentsExperienceId} = getState();

		return FragmentService.updateConfigurationValues({
			editableValues: nextEditableValues,
			fragmentEntryLinkId,
			languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({fragmentEntryLink, layoutData, pageContents}) => {
			dispatch(
				updateFragmentEntryLinkConfiguration({
					fragmentEntryLink,
					fragmentEntryLinkId,
					layoutData,
					pageContents,
				})
			);
		});
	};
}
