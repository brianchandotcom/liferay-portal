/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValuesAction from '../actions/updateEditableValues';
import FragmentService from '../services/FragmentService';

export default function updateEditableValues({
	editableValues,
	fragmentEntryLinkId,
}) {
	return (dispatch, getState) => {
		const {languageId, segmentsExperienceId} = getState();

		return FragmentService.updateEditableValues({
			editableValues,
			fragmentEntryLinkId,
			languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({fragmentEntryLink, pageContents}) => {
			dispatch(
				updateEditableValuesAction({
					content: fragmentEntryLink.content,
					editableValues,
					fragmentEntryLinkId,
					pageContents,
					segmentsExperienceId,
				})
			);
		});
	};
}
