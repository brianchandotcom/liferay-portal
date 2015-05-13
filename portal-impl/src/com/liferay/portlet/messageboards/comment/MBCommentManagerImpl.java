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

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentConstants;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
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
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		MBMessageDisplay messageDisplay =
			_mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		List<MBMessage> messages = _mbMessageLocalService.getThreadMessages(
			thread.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		for (MBMessage message : messages) {
			String messageBody = message.getBody();

			if (messageBody.equals(body)) {
				throw new DuplicateCommentException();
			}
		}

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

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
	public long addComment(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK,
			long permissionOwnerId, long parentMessageId, String subject,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		long threadId = parentMessage.getThreadId();

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

		MBMessage message = MBMessageServiceUtil.addDiscussionMessage(
			serviceContext.getScopeGroupId(), className, classPK,
			permissionClassName, permissionClassPK, permissionOwnerId, threadId,
			parentMessageId, subject, body, serviceContext);

		return message.getMessageId();
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
	public void deleteComment(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK,
			long permissionOwnerId, long commentId)
		throws PortalException {

		_mbMessageService.deleteDiscussionMessage(
			groupId, className, classPK, permissionClassName, permissionClassPK,
			permissionOwnerId, commentId);
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

	@Override
	public Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_ANY, new MessageThreadComparator());

		MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

		List<MBMessage> messages = treeWalker.getMessages();

		List<RatingsEntry> ratingsEntries = Collections.emptyList();
		List<RatingsStats> ratingsStats = Collections.emptyList();

		if (messages.size() > 1) {
			List<Long> classPKs = new ArrayList<>();

			for (MBMessage curMessage : messages) {
				if (!curMessage.isRoot()) {
					classPKs.add(curMessage.getMessageId());
				}
			}

			ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(
				userId, CommentConstants.getDiscussionClassName(), classPKs);
			ratingsStats = RatingsStatsLocalServiceUtil.getStats(
				CommentConstants.getDiscussionClassName(), classPKs);
		}

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		Comment rootComment = new MBCommentImpl(
			treeWalker.getRoot(), treeWalker, ratingsEntries, ratingsStats,
			themeDisplay.getPathThemeImages());

		return new MBDiscussionImpl(
			rootComment, messageDisplay.isDiscussionMaxComments());
	}

	@Override
	public DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker) {

		return new MBDiscussionPermissionImpl(permissionChecker);
	}

	public void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	public void setMBMessageService(MBMessageService mbMessageService) {
		_mbMessageService = mbMessageService;
	}

	@Override
	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		_mbDiscussionLocalService.subscribeDiscussion(
			userId, groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		_mbDiscussionLocalService.unsubscribeDiscussion(
			userId, className, classPK);
	}

	@Override
	public long updateComment(
			String className, long classPK, String permissionClassName,
			long permissionClassPK, long permissionOwnerId, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		ServiceContext serviceContext = serviceContextFunction.apply(
			MBMessage.class.getName());

		MBMessage message = _mbMessageService.updateDiscussionMessage(
			className, classPK, permissionClassName, permissionClassPK,
			permissionOwnerId, commentId, subject, body, serviceContext);

		return message.getMessageId();
	}

	private MBDiscussionLocalService _mbDiscussionLocalService;
	private MBMessageLocalService _mbMessageLocalService;
	private MBMessageService _mbMessageService;

}