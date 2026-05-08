/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValuesAction from '../actions/updateEditableValues';
import FragmentService from '../services/FragmentService';
import {clearPageContents} from '../utils/usePageContents';

const pendingRequests = new Map();

function enqueueByKey(key, work) {
	const previous = pendingRequests.get(key) ?? Promise.resolve();

	const current = previous
		.catch(() => {})
		.then(() => work(() => pendingRequests.get(key) === current))
		.finally(() => {
			if (pendingRequests.get(key) === current) {
				pendingRequests.delete(key);
			}
		});

	pendingRequests.set(key, current);

	return current;
}

export default function updateEditableValues({
	editableValues,
	fragmentEntryLinkId,
}) {
	return (dispatch, getState) => {
		const {languageId, segmentsExperienceId} = getState();

		return enqueueByKey(fragmentEntryLinkId, async (isLastInQueue) => {
			const {fragmentEntryLink} =
				await FragmentService.updateEditableValues({
					editableValues,
					fragmentEntryLinkId,
					languageId,
					onNetworkStatus: dispatch,
					segmentsExperienceId,
				});

			dispatch(
				updateEditableValuesAction({
					content: fragmentEntryLink.content,
					editableValues,
					fragmentEntryLinkId,
					segmentsExperienceId,
				})
			);

			if (isLastInQueue()) {
				clearPageContents();
			}
		});
	};
}
