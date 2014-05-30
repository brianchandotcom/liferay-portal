/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.comments;

import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

/**
 * @author André de Oliveira
 * @author Alexander Chow
 */
public class CommentsImpl implements Comments {

	@Override
	public long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException, SystemException {

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		long threadId = thread.getThreadId();
		long parentMessageId = thread.getRootMessageId();

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

		MBMessage message = MBMessageLocalServiceUtil.addDiscussionMessage(
			userId, userName, groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);

		return message.getMessageId();
	}

	@Override
	public void deleteComment(long commentId)
		throws PortalException, SystemException {

		MBMessageLocalServiceUtil.deleteDiscussionMessage(commentId);
	}

}