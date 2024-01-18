/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.web.internal.jsonws;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Istvan Sajtos
 */
public interface CommentManagerJSONWS {

	public long addComment(
			long groupId, String className, long classPK, String body)
		throws PortalException;

	public void deleteComment(long commentId) throws PortalException;

	public List<CommentJSONWS> getComments(long commentId, int start, int end)
		throws PortalException;

	public List<CommentJSONWS> getComments(
			long groupId, String className, long classPK, int start, int end)
		throws PortalException;

	public int getCommentsCount(long groupId, String className, long classPK)
		throws PortalException;

	public boolean hasDiscussion(long groupId, String className, long classPK)
		throws PortalException;

	public void subscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException;

	public void unsubscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException;

	public long updateComment(
			String className, long classPK, long commentId, String subject,
			String body)
		throws PortalException;

}