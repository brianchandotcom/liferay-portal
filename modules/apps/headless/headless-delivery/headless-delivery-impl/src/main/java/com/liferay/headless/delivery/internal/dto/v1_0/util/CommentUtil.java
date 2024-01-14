/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Javier Gamarra
 */
public class CommentUtil {

	public static Comment toComment(
			com.liferay.portal.kernel.comment.Comment comment,
			CommentManager commentManager, Portal portal)
		throws Exception {

		if (comment == null) {
			return null;
		}

		return new Comment() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						null, portal, comment.getUser()));
				setDateCreated(comment::getCreateDate);
				setDateModified(comment::getModifiedDate);
				setExternalReferenceCode(comment::getExternalReferenceCode);
				setId(comment::getCommentId);
				setNumberOfComments(
					() -> commentManager.getChildCommentsCount(
						comment.getCommentId(),
						WorkflowConstants.STATUS_APPROVED));
				setParentCommentId(comment::getParentCommentId);
				setText(comment::getBody);
			}
		};
	}

}