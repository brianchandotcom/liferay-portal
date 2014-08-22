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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

import java.util.List;

/**
 * @author André de Oliveira
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class MBCommentManagerImpl implements CommentManager {

	@Override
	public void addComment(
			long userId, long groupId, String className, long classPK,
			String body, ServiceContext serviceContext)
		throws PortalException {

		MBMessageDisplay messageDisplay =
			_mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		List<MBMessage> messages =
			_mbMessageLocalService.getThreadMessages(
				thread.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		for (MBMessage message : messages) {
			String messageBody = message.getBody();

			if (messageBody.equals(body)) {
				throw new DuplicateCommentException();
			}
		}

		_mbMessageLocalService.addDiscussionMessage(
			userId, StringPool.BLANK, groupId, className, classPK,
			thread.getThreadId(), thread.getRootMessageId(), StringPool.BLANK,
			body, serviceContext);
	}

	@Override
	public long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		MBMessageDisplay mbMessageDisplay =
			_mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread mbThread = mbMessageDisplay.getThread();

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

		MBMessage mbMessage = _mbMessageLocalService.addDiscussionMessage(
			userId, userName, groupId, className, classPK,
			mbThread.getThreadId(), mbThread.getRootMessageId(), subject, body,
			serviceContext);

		return mbMessage.getMessageId();
	}

	@Override
	public void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		_mbMessageLocalService.addDiscussionMessage(
			userId, userName, groupId, className, classPK,
			WorkflowConstants.ACTION_PUBLISH);
	}

	@Override
	public void deleteComment(long commentId) throws PortalException {
		_mbMessageLocalService.deleteDiscussionMessage(commentId);
	}

	@Override
	public void deleteDiscussion(String className, long classPK)
		throws PortalException {

		_mbMessageLocalService.deleteDiscussionMessages(className, classPK);
	}

	@Override
	public int getCommentsCount(String className, long classPK) {
		long classNameId = PortalUtil.getClassNameId(className);

		return _mbMessageLocalService.getDiscussionMessagesCount(
			classNameId, classPK, WorkflowConstants.STATUS_APPROVED);
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;

}