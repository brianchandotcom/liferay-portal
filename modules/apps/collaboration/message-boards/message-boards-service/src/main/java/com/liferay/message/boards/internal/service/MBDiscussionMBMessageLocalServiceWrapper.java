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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.kernel.exception.MessageBodyException;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.message.boards.kernel.util.comparator.MessageThreadComparator;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class MBDiscussionMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public MBDiscussionMBMessageLocalServiceWrapper() {
		super(null);
	}

	public MBDiscussionMBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {

		super(mbMessageLocalService);
	}

	@Override
	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, int workflowAction)
		throws PortalException {

		super.addDiscussionMessage(
			userId, userName, groupId, className, classPK, workflowAction);

		long threadId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;

		String subject = String.valueOf(classPK);

		String body = subject;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setWorkflowAction(workflowAction);

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		WorkflowThreadLocal.setEnabled(false);

		try {
			return addDiscussionMessage(
				userId, userName, groupId, className, classPK, threadId,
				parentMessageId, subject, body, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	@Override
	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, long threadId, long parentMessageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		super.addDiscussionMessage(
			userId, userName, groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);

		// Message

		validateDiscussionMaxComments(className, classPK);

		long categoryId = MBCategoryConstants.DISCUSSION_CATEGORY_ID;
		subject = getDiscussionMessageSubject(subject, body);
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));

		Date now = new Date();

		if (serviceContext.getCreateDate() == null) {
			serviceContext.setCreateDate(now);
		}

		if (serviceContext.getModifiedDate() == null) {
			serviceContext.setModifiedDate(now);
		}

		MBMessage message = addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, PropsValues.DISCUSSION_COMMENTS_FORMAT,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);

		// Discussion

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			long classNameId = _classNameLocalService.getClassNameId(className);

			MBDiscussion discussion = _mbDiscussionLocalService.fetchDiscussion(
				classNameId, classPK);

			if (discussion == null) {
				_mbDiscussionLocalService.addDiscussion(
					userId, groupId, classNameId, classPK,
					message.getThreadId(), serviceContext);
			}
		}

		return message;
	}

	@Override
	public void deleteDiscussionMessages(String className, long classPK)
		throws PortalException {

		super.deleteDiscussionMessages(className, classPK);

		MBDiscussion discussion = _mbDiscussionLocalService.fetchDiscussion(
			className, classPK);

		if (discussion == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Unable to delete discussion message for class name ",
						className, " and class PK ", String.valueOf(classPK),
						" because it does not exist"));
			}

			return;
		}

		MBMessage message = _mbMessageLocalService.fetchFirstMessage(
			discussion.getThreadId(),
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

		if (message != null) {
			SocialActivityManagerUtil.deleteActivities(message);

			_mbThreadLocalService.deleteThread(message.getThreadId());
		}

		_mbDiscussionLocalService.deleteMBDiscussion(discussion);
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status)
		throws PortalException {

		super.getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status);

		return getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status,
			new MessageThreadComparator());
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, Comparator<MBMessage> comparator)
		throws PortalException {

		super.getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status, comparator);

		MBMessage message = null;

		MBDiscussion discussion = _mbDiscussionLocalService.fetchDiscussion(
			className, classPK);

		if (discussion != null) {
			message = _mbMessageLocalService.getFirstMessage(
				discussion.getThreadId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);
		}
		else {
			boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

			WorkflowThreadLocal.setEnabled(false);

			try {
				String subject = String.valueOf(classPK);
				//String body = subject;

				message = addDiscussionMessage(
					userId, null, groupId, className, classPK, 0,
					MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, subject,
					subject, new ServiceContext());
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {threadId=0, parentMessageId=" +
							MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID + "}");
				}

				message = _mbMessageLocalService.fetchFirstMessage(
					0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

				if (message == null) {
					throw se;
				}
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}

		return getMessageDisplay(userId, message, status, comparator);
	}

	@Override
	public int getDiscussionMessagesCount(
		long classNameId, long classPK, int status) {

		super.getDiscussionMessagesCount(classNameId, classPK, status);

		MBDiscussion discussion = _mbDiscussionLocalService.fetchDiscussion(
			classNameId, classPK);

		if (discussion == null) {
			return 0;
		}

		int count = _mbMessageLocalService.getThreadMessagesCount(
			discussion.getThreadId(), status);

		if (count >= 1) {
			return count - 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public int getDiscussionMessagesCount(
		String className, long classPK, int status) {

		super.getDiscussionMessagesCount(className, classPK, status);

		long classNameId = _classNameLocalService.getClassNameId(className);

		return getDiscussionMessagesCount(classNameId, classPK, status);
	}

	@Override
	public List<com.liferay.message.boards.kernel.model.MBDiscussion>
		getDiscussions(String className) {

		super.getDiscussions(className);

		return ModelAdapterUtil.adapt(
			com.liferay.message.boards.kernel.model.MBDiscussion.class,
			_mbDiscussionLocalService.getDiscussions(className));
	}

	protected String getDiscussionMessageSubject(String subject, String body)
		throws MessageBodyException {

		if (Validator.isNotNull(subject)) {
			return subject;
		}

		if (Validator.isNull(body)) {
			throw new MessageBodyException("Body is null");
		}

		subject = HtmlUtil.extractText(body);

		if (subject.length() <= MBMessageConstants.MESSAGE_SUBJECT_MAX_LENGTH) {
			return subject;
		}

		String subjectSubstring = subject.substring(
			0, MBMessageConstants.MESSAGE_SUBJECT_MAX_LENGTH);

		return subjectSubstring + StringPool.TRIPLE_PERIOD;
	}

	protected void validateDiscussionMaxComments(String className, long classPK)
		throws PortalException {

		if (PropsValues.DISCUSSION_MAX_COMMENTS <= 0) {
			return;
		}

		int count = getDiscussionMessagesCount(
			className, classPK, WorkflowConstants.STATUS_APPROVED);

		if (count >= PropsValues.DISCUSSION_MAX_COMMENTS) {
			int max = PropsValues.DISCUSSION_MAX_COMMENTS - 1;

			throw new DiscussionMaxCommentsException(count + " exceeds " + max);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBDiscussionMBMessageLocalServiceWrapper.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}