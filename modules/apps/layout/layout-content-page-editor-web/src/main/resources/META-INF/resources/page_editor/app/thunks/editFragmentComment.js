/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import editFragmentEntryLinkComment from '../actions/editFragmentEntryLinkComment';
import FragmentService from '../services/FragmentService';

/**
 * @param {string} options.body
 * @param {string} options.commentId
 * @param {string} options.fragmentEntryLinkId
 * @param {string} [options.parentCommentId]
 * @param {boolean} [options.resolved]
 */
export default function editFragmentComment({
	body,
	commentId,
	fragmentEntryLinkId,
	parentCommentId,
	resolved = false,
}) {
	return (dispatch) => {
		return FragmentService.editComment({
			body,
			commentId,
			onNetworkStatus: dispatch,
			resolved,
		}).then((fragmentEntryLinkComment) => {
			dispatch(
				editFragmentEntryLinkComment({
					fragmentEntryLinkComment,
					fragmentEntryLinkId,
					parentCommentId,
				})
			);
		});
	};
}
