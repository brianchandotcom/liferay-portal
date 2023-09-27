/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentEntryLinkComment from '../actions/addFragmentEntryLinkComment';
import FragmentService from '../services/FragmentService';

/**
 * @param {object} options
 * @param {string} options.body
 * @param {string} options.fragmentEntryLinkId
 * @param {string} [options.parentCommentId]
 */
export default function addFragmentComment({
	body,
	fragmentEntryLinkId,
	parentCommentId,
}) {
	return (dispatch) => {
		return FragmentService.addComment({
			body,
			fragmentEntryLinkId,
			onNetworkStatus: dispatch,
			parentCommentId,
		}).then((fragmentEntryLinkComment) => {
			dispatch(
				addFragmentEntryLinkComment({
					fragmentEntryLinkComment,
					fragmentEntryLinkId,
					parentCommentId,
				})
			);
		});
	};
}
