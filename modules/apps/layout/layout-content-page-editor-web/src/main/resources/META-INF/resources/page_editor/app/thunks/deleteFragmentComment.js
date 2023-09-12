/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import deleteFragmentEntryLinkComment from '../actions/deleteFragmentEntryLinkComment';
import FragmentService from '../services/FragmentService';

/**
 * @param {string} options.commentId
 * @param {string} options.fragmentEntryLinkId
 * @param {string} [options.parentCommentId]
 */
export default function deleteFragmentComment({
	commentId,
	fragmentEntryLinkId,
	parentCommentId,
}) {
	return (dispatch) => {
		return FragmentService.deleteComment({
			commentId,
			onNetworkStatus: dispatch,
		}).then(() => {
			dispatch(
				deleteFragmentEntryLinkComment({
					commentId,
					fragmentEntryLinkId,
					parentCommentId,
				})
			);
		});
	};
}
