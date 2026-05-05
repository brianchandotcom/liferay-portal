/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValuesAction from '../actions/updateEditableValues';
import FragmentService from '../services/FragmentService';
import {clearPageContents} from '../utils/usePageContents';

let nextRequestId = 0;
const latestRequestIds = new Map();

export default function updateEditableValues({
	editableValues,
	fragmentEntryLinkId,
}) {
	return async (dispatch, getState) => {
		const requestId = ++nextRequestId;

		latestRequestIds.set(fragmentEntryLinkId, requestId);

		const {languageId, segmentsExperienceId} = getState();

		const {fragmentEntryLink} = await FragmentService.updateEditableValues({
			editableValues,
			fragmentEntryLinkId,
			languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		});

		// If a newer request started for this fragment, skip this response
		// so we don't replace the latest state with old data. Nothing is
		// lost: each request sends the full editable values.

		if (latestRequestIds.get(fragmentEntryLinkId) !== requestId) {
			return;
		}

		latestRequestIds.delete(fragmentEntryLinkId);

		dispatch(
			updateEditableValuesAction({
				content: fragmentEntryLink.content,
				editableValues,
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);

		clearPageContents();
	};
}
