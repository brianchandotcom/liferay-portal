/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {objectToFormData} from 'frontend-js-web';

import ApiHelper from '../../../common/services/ApiHelper';

export type Comment = {
	author: {
		fullName: string;
		portraitURL: string;
		userId: string;
	};
	body: string;
	children: Comment[];
	className: string;
	commentId: string;
	dateDescription: string;
	edited: boolean;
	negativeVotes: number;
	positiveVotes: number;
	rootComment: boolean;
};

async function addComment({
	content,
	parentCommentId = null,
	url,
}: {
	content: string;
	parentCommentId?: string | null;
	url: string;
}): Promise<Comment> {
	const {data, error} = await ApiHelper.postFormData(
		objectToFormData({
			body: content,
			parentCommentId,
		}),
		url
	);

	if (error) {
		throw new Error(error);
	}

	return data as Comment;
}

export default {
	addComment,
};
