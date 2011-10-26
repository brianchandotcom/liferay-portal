/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageDisplayImpl;
import com.liferay.portlet.messageboards.service.base.MBMessageLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.social.MBActivityKeys;
import com.liferay.portlet.messageboards.util.MBSubscriptionSender;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.MailingListThreadLocal;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;
import com.liferay.portlet.messageboards.util.comparator.ThreadLastPostDateComparator;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.util.SerializableUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletPreferences;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Mika Koivisto
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Shuyang Zhou
 */
public class MBMessageLocalServiceImpl extends MBMessageLocalServiceBaseImpl {

	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, int workflowAction)
		throws PortalException, SystemException {

		long threadId = 0;
		long parentMessageId = 0;
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

	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, long threadId, long parentMessageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Message

		long categoryId = MBCategoryConstants.DISCUSSION_CATEGORY_ID;

		if (Validator.isNull(subject)) {
			subject = body.substring(0, Math.min(body.length(), 50)) + "...";
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));

		MBMessage message = addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, MBMessageConstants.DEFAULT_FORMAT,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);

		// Discussion

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			long classNameId = PortalUtil.getClassNameId(className);

			MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
				classNameId, classPK);

			if (discussion == null) {
				discussion = mbDiscussionLocalService.addDiscussion(
					classNameId, classPK, message.getThreadId());
			}
		}

		return message;
	}

	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			long threadId, long parentMessageId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Message

		User user = userPersistence.findByPrimaryKey(userId);
		userName = user.isDefaultUser() ? userName : user.getFullName();
		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences != null) {
			if (!MBUtil.isAllowAnonymousPosting(preferences)) {
				if (anonymous || user.isDefaultUser()) {
					throw new PrincipalException();
				}
			}
		}

		if (user.isDefaultUser()) {
			anonymous = true;
		}

		Date now = new Date();

		long messageId = counterLocalService.increment();

		body = SanitizerUtil.sanitize(
			user.getCompanyId(), groupId, userId, MBMessage.class.getName(),
			messageId, "text/" + format, body);

		validate(subject, body);

		subject = getSubject(subject, body);
		body = getBody(subject, body);

		MBMessage message = mbMessagePersistence.create(messageId);

		message.setUuid(serviceContext.getUuid());
		message.setGroupId(groupId);
		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(userName);
		message.setCreateDate(serviceContext.getCreateDate(now));
		message.setModifiedDate(serviceContext.getModifiedDate(now));

		if (threadId > 0) {
			message.setThreadId(threadId);
		}

		if (priority != MBThreadConstants.PRIORITY_NOT_GIVEN) {
			message.setPriority(priority);
		}

		message.setAllowPingbacks(allowPingbacks);
		message.setStatus(WorkflowConstants.STATUS_DRAFT);
		message.setStatusByUserId(user.getUserId());
		message.setStatusByUserName(userName);
		message.setStatusDate(serviceContext.getModifiedDate(now));

		// Thread

		if (parentMessageId != MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			MBMessage parentMessage = mbMessagePersistence.fetchByPrimaryKey(
				parentMessageId);

			if (parentMessage == null) {
				parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
			}
		}

		MBThread thread = null;

		if (threadId > 0) {
			thread = mbThreadPersistence.fetchByPrimaryKey(threadId);
		}

		if ((thread == null) ||
			(parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID)) {

			thread = mbThreadLocalService.addThread(categoryId, message);
		}

		if ((priority != MBThreadConstants.PRIORITY_NOT_GIVEN) &&
			(thread.getPriority() != priority)) {

			thread.setPriority(priority);

			mbThreadPersistence.update(thread, false);

			updatePriorities(thread.getThreadId(), priority);
		}

		// Message

		message.setCategoryId(categoryId);
		message.setThreadId(thread.getThreadId());
		message.setRootMessageId(thread.getRootMessageId());
		message.setParentMessageId(parentMessageId);
		message.setSubject(subject);
		message.setBody(body);
		message.setFormat(format);
		message.setAttachments(!inputStreamOVPs.isEmpty());
		message.setAnonymous(anonymous);

		if (message.isDiscussion()) {
			long classNameId = PortalUtil.getClassNameId(
				(String)serviceContext.getAttribute("className"));
			long classPK = GetterUtil.getLong(
				(String)serviceContext.getAttribute("classPK"));

			message.setClassNameId(classNameId);
			message.setClassPK(classPK);
		}

		mbMessagePersistence.update(message, false);

		// Attachments

		if (!inputStreamOVPs.isEmpty()) {
			long companyId = message.getCompanyId();
			long repositoryId = CompanyConstants.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
				DLStoreUtil.deleteDirectory(companyId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsde.getMessage());
				}
			}

			DLStoreUtil.addDirectory(companyId, repositoryId, dirName);

			for (int i = 0; i < inputStreamOVPs.size(); i++) {
				ObjectValuePair<String, InputStream> inputStreamOVP =
					inputStreamOVPs.get(i);

				String fileName = inputStreamOVP.getKey();
				InputStream inputStream = inputStreamOVP.getValue();

				try {
					DLStoreUtil.addFile(
						companyId, repositoryId, dirName + "/" + fileName,
						inputStream);
				}
				catch (DuplicateFileException dfe) {
					if (_log.isDebugEnabled()) {
						_log.debug(dfe.getMessage());
					}
				}
			}
		}

		// Resources

		if (!message.isDiscussion()) {
			if (user.isDefaultUser()) {
				addMessageResources(message, true, true);
			}
			else if (serviceContext.getAddGroupPermissions() ||
					 serviceContext.getAddGuestPermissions()) {

				addMessageResources(
					message, serviceContext.getAddGroupPermissions(),
					serviceContext.getAddGuestPermissions());
			}
			else {
				addMessageResources(
					message, serviceContext.getGroupPermissions(),
					serviceContext.getGuestPermissions());
			}
		}

		// Asset

		updateAsset(
			userId, message, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Expando

		ExpandoBridge expandoBridge = message.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId,
			message.getWorkflowClassName(), message.getMessageId(), message,
			serviceContext);

		// Testing roll back

		/*if (true) {
			throw new SystemException("Testing roll back");
		}*/

		return message;
	}

	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long threadId = 0;
		long parentMessageId = 0;

		return addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public void addMessageResources(
			long messageId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		addMessageResources(message, addGroupPermissions, addGuestPermissions);
	}

	public void addMessageResources(
			long messageId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		addMessageResources(message, groupPermissions, guestPermissions);
	}

	public void addMessageResources(
			MBMessage message, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			message.getCompanyId(), message.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(),
			false, addGroupPermissions, addGuestPermissions);
	}

	public void addMessageResources(
			MBMessage message, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			message.getCompanyId(), message.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(), groupPermissions,
			guestPermissions);
	}

	public void deleteDiscussionMessage(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		List<MBMessage> messages = new ArrayList<MBMessage>();

		messages.add(message);

		deleteDiscussionSocialActivities(BlogsEntry.class.getName(), messages);

		deleteMessage(message);
	}

	public void deleteDiscussionMessages(String className, long classPK)
		throws PortalException, SystemException {

		try {
			long classNameId = PortalUtil.getClassNameId(className);

			MBDiscussion discussion = mbDiscussionPersistence.findByC_C(
				classNameId, classPK);

			List<MBMessage> messages = mbMessagePersistence.findByT_P(
				discussion.getThreadId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, 0, 1);

			deleteDiscussionSocialActivities(
				BlogsEntry.class.getName(), messages);

			if (!messages.isEmpty()) {
				MBMessage message = messages.get(0);

				mbThreadLocalService.deleteThread(message.getThreadId());
			}

			mbDiscussionPersistence.remove(discussion);
		}
		catch (NoSuchDiscussionException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteMessage(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		deleteMessage(message);
	}

	public void deleteMessage(MBMessage message)
		throws PortalException, SystemException {

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		indexer.delete(message);

		// Attachments

		if (message.isAttachments()) {
			long companyId = message.getCompanyId();
			long repositoryId = CompanyConstants.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
				DLStoreUtil.deleteDirectory(companyId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsde.getMessage());
				}
			}
		}

		// Thread

		int count = mbMessagePersistence.countByThreadId(message.getThreadId());

		if (count == 1) {

			// Attachments

			long companyId = message.getCompanyId();
			long repositoryId = CompanyConstants.SYSTEM;
			String dirName = message.getThreadAttachmentsDir();

			try {
				DLStoreUtil.deleteDirectory(companyId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsde.getMessage());
				}
			}

			// Subscriptions

			subscriptionLocalService.deleteSubscriptions(
				message.getCompanyId(), MBThread.class.getName(),
				message.getThreadId());

			// Thread

			mbThreadPersistence.remove(message.getThreadId());

			// Category

			if ((message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				(message.getCategoryId() !=
					MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

				MBCategory category = mbCategoryPersistence.findByPrimaryKey(
					message.getCategoryId());

				category.setThreadCount(category.getThreadCount() - 1);
				category.setMessageCount(category.getMessageCount() - 1);

				mbCategoryPersistence.update(category, false);
			}
		}
		else {
			MBThread thread = mbThreadPersistence.findByPrimaryKey(
				message.getThreadId());

			// Message is a root message

			if (thread.getRootMessageId() == message.getMessageId()) {
				List<MBMessage> childrenMessages =
					mbMessagePersistence.findByT_P(
						message.getThreadId(), message.getMessageId());

				if (childrenMessages.size() > 1) {
					throw new RequiredMessageException(
						String.valueOf(message.getMessageId()));
				}
				else if (childrenMessages.size() == 1) {
					MBMessage childMessage = childrenMessages.get(0);

					childMessage.setRootMessageId(childMessage.getMessageId());
					childMessage.setParentMessageId(
						MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

					mbMessagePersistence.update(childMessage, false);

					thread.setRootMessageId(childMessage.getMessageId());
					thread.setRootMessageUserId(childMessage.getUserId());

					mbThreadPersistence.update(thread, false);
				}
			}

			// Message is a child message

			else {
				List<MBMessage> childrenMessages =
					mbMessagePersistence.findByT_P(
						message.getThreadId(), message.getMessageId());

				// Message has children messages

				if (!childrenMessages.isEmpty()) {
					Iterator<MBMessage> itr = childrenMessages.iterator();

					while (itr.hasNext()) {
						MBMessage childMessage = itr.next();

						childMessage.setParentMessageId(
							message.getParentMessageId());

						mbMessagePersistence.update(childMessage, false);
					}
				}
				else {
					MessageCreateDateComparator comparator =
						new MessageCreateDateComparator(true);

					MBMessage lastMessage = mbMessagePersistence.findByT_S_Last(
						thread.getThreadId(), WorkflowConstants.STATUS_APPROVED,
						comparator);

					if (message.getMessageId() == lastMessage.getMessageId()) {
						MBMessage parentMessage =
							mbMessagePersistence.findByPrimaryKey(
								message.getParentMessageId());

						thread.setLastPostByUserId(parentMessage.getUserId());
						thread.setLastPostDate(parentMessage.getModifiedDate());
					}
				}
			}

			// Thread

			if (message.isApproved()) {
				int messageCount = mbMessagePersistence.countByT_S(
					message.getThreadId(), WorkflowConstants.STATUS_APPROVED);

				thread.setMessageCount(messageCount - 1);
			}

			mbThreadPersistence.update(thread, false);

			// Category

			if ((message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				(message.getCategoryId() !=
					MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
				!message.isDraft()) {

				MBCategory category = mbCategoryPersistence.findByPrimaryKey(
					message.getCategoryId());

				category.setMessageCount(category.getMessageCount() - 1);

				mbCategoryPersistence.update(category, false);
			}
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			MBMessage.class.getName(), message.getMessageId());

		// Expando

		expandoValueLocalService.deleteValues(
			MBMessage.class.getName(), message.getMessageId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			MBMessage.class.getName(), message.getMessageId());

		// Resources

		if (!message.isDiscussion()) {
			resourceLocalService.deleteResource(
				message.getCompanyId(), MBMessage.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, message.getMessageId());
		}

		// Message

		mbMessagePersistence.remove(message);

		// Statistics

		if (!message.isDiscussion()) {
			mbStatsUserLocalService.updateStatsUser(
				message.getGroupId(), message.getUserId());
		}

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			message.getCompanyId(), message.getGroupId(),
			message.getWorkflowClassName(), message.getMessageId());
	}

	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_C(
				groupId, categoryId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_C_S(
				groupId, categoryId, status, start, end);
		}
	}

	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_C(
				groupId, categoryId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_C_S(
				groupId, categoryId, status, start, end, obc);
		}
	}

	public int getCategoryMessagesCount(
			long groupId, long categoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbMessagePersistence.countByG_C_S(
				groupId, categoryId, status);
		}
	}

	public List<MBMessage> getCompanyMessages(
			long companyId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByCompanyId(companyId, start, end);
		}
		else {
			return mbMessagePersistence.findByC_S(
				companyId, status, start, end);
		}
	}

	public List<MBMessage> getCompanyMessages(
			long companyId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByC_S(
				companyId, status, start, end, obc);
		}
	}

	public int getCompanyMessagesCount(long companyId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByCompanyId(companyId);
		}
		else {
			return mbMessagePersistence.countByC_S(companyId, status);
		}
	}

	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status)
		throws PortalException, SystemException {

		return getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status,
			MBThreadConstants.THREAD_VIEW_COMBINATION);
	}

	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, String threadView)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		MBMessage message = null;

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, classPK);

		if (discussion != null) {
			List<MBMessage> messages = mbMessagePersistence.findByT_P(
				discussion.getThreadId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

			message = messages.get(0);
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

				List<MBMessage> messages = mbMessagePersistence.findByT_P(
					0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

				if (messages.isEmpty()) {
					throw se;
				}

				message = messages.get(0);
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}

		return getMessageDisplay(userId, message, status, threadView, false);
	}

	public int getDiscussionMessagesCount(
			long classNameId, long classPK, int status)
		throws SystemException {

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			return 0;
		}

		int count = 0;

		if (status == WorkflowConstants.STATUS_ANY) {
			count = mbMessagePersistence.countByThreadId(
				discussion.getThreadId());
		}
		else {
			count = mbMessagePersistence.countByT_S(
				discussion.getThreadId(), status);
		}

		if (count >= 1) {
			return count - 1;
		}
		else {
			return 0;
		}
	}

	public int getDiscussionMessagesCount(
			String className, long classPK, int status)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDiscussionMessagesCount(classNameId, classPK, status);
	}

	public List<MBDiscussion> getDiscussions(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mbDiscussionPersistence.findByClassNameId(classNameId);
	}

	public List<MBMessage> getGroupMessages(
			long groupId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByGroupId(groupId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_S(groupId, status, start, end);
		}
	}

	public List<MBMessage> getGroupMessages(
			long groupId, int status, int start, int end, OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByGroupId(groupId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_S(
				groupId, status, start, end, obc);
		}
	}

	public List<MBMessage> getGroupMessages(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_U(groupId, userId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_U_S(
				groupId, userId, status, start, end);
		}
	}

	public List<MBMessage> getGroupMessages(
			long groupId, long userId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_U_S(
				groupId, userId, status, start, end, obc);
		}
	}

	public int getGroupMessagesCount(long groupId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByGroupId(groupId);
		}
		else {
			return mbMessagePersistence.countByG_S(groupId, status);
		}
	}

	public int getGroupMessagesCount(long groupId, long userId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByG_U(groupId, userId);
		}
		else {
			return mbMessagePersistence.countByG_U_S(groupId, userId, status);
		}
	}

	public MBMessage getMessage(long messageId)
		throws PortalException, SystemException {

		return mbMessagePersistence.findByPrimaryKey(messageId);
	}

	public MBMessageDisplay getMessageDisplay(
			long userId, long messageId, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException, SystemException {

		MBMessage message = getMessage(messageId);

		return getMessageDisplay(
			userId, message, status, threadView, includePrevAndNext);
	}

	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException, SystemException {

		MBCategory category = null;

		if ((message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(message.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = mbCategoryPersistence.findByPrimaryKey(
				message.getCategoryId());
		}
		else {
			category = new MBCategoryImpl();

			category.setCategoryId(message.getCategoryId());
			category.setDisplayStyle(MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		}

		MBMessage parentMessage = null;

		if (message.isReply()) {
			parentMessage = mbMessagePersistence.findByPrimaryKey(
				message.getParentMessageId());
		}

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		if (message.isApproved() && !message.isDiscussion()) {
			mbThreadLocalService.incrementViewCounter(thread.getThreadId(), 1);

			if (thread.getRootMessageUserId() != userId) {
				MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
					thread.getRootMessageId());

				socialEquityLogLocalService.addEquityLogs(
					userId, MBMessage.class.getName(),
					rootMessage.getMessageId(), ActionKeys.VIEW,
					StringPool.BLANK);
			}
		}

		MBThread previousThread = null;
		MBThread nextThread = null;

		if (message.isApproved() && includePrevAndNext) {
			ThreadLastPostDateComparator comparator =
				new ThreadLastPostDateComparator(false);

			MBThread[] prevAndNextThreads =
				mbThreadPersistence.findByG_C_PrevAndNext(
					message.getThreadId(), message.getGroupId(),
					message.getCategoryId(), comparator);

			previousThread = prevAndNextThreads[0];
			nextThread = prevAndNextThreads[2];
		}

		return new MBMessageDisplayImpl(
			message, parentMessage, category, thread,
			previousThread, nextThread, status, threadView, this);
	}

	public List<MBMessage> getMessages(
			String className, long classPK, int status)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByC_C(classNameId, classPK);
		}
		else {
			return mbMessagePersistence.findByC_C_S(
				classNameId, classPK, status);
		}
	}

	public List<MBMessage> getNoAssetMessages() throws SystemException {
		return mbMessageFinder.findByNoAssets();
	}

	public int getPositionInThread(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		return mbMessageFinder.countByC_T(
			message.getCreateDate(), message.getThreadId());
	}

	public List<MBMessage> getThreadMessages(long threadId, int status)
		throws SystemException {

		return getThreadMessages(
			threadId, status, new MessageThreadComparator());
	}

	public List<MBMessage> getThreadMessages(
			long threadId, int status, Comparator<MBMessage> comparator)
		throws SystemException {

		List<MBMessage> messages = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			messages = mbMessagePersistence.findByThreadId(threadId);
		}
		else {
			messages = mbMessagePersistence.findByT_S(threadId, status);
		}

		return ListUtil.sort(messages, comparator);
	}

	public List<MBMessage> getThreadMessages(
			long threadId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByThreadId(threadId, start, end);
		}
		else {
			return mbMessagePersistence.findByT_S(threadId, status, start, end);
		}
	}

	public int getThreadMessagesCount(long threadId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByThreadId(threadId);
		}
		else {
			return mbMessagePersistence.countByT_S(threadId, status);
		}
	}

	public List<MBMessage> getThreadRepliesMessages(
			long threadId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByThreadReplies(
				threadId, start, end);
		}
		else {
			return mbMessagePersistence.findByTR_S(
				threadId, status, start, end);
		}
	}

	public List<MBMessage> getUserDiscussionMessages(
			long userId, long classNameId, long classPK, int status, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByU_C_C(
				userId, classNameId, classPK, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByU_C_C_S(
				userId, classNameId, classPK, status, start, end, obc);
		}
	}

	public List<MBMessage> getUserDiscussionMessages(
			long userId, long[] classNameIds, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByU_C(
				userId, classNameIds, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByU_C_S(
				userId, classNameIds, status, start, end, obc);
		}
	}

	public List<MBMessage> getUserDiscussionMessages(
			long userId, String className, long classPK, int status, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getUserDiscussionMessages(
			userId, classNameId, classPK, status, start, end, obc);
	}

	public int getUserDiscussionMessagesCount(
			long userId, long classNameId, long classPK, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByU_C_C(
				userId, classNameId, classPK);
		}
		else {
			return mbMessagePersistence.countByU_C_C_S(
				userId, classNameId, classPK, status);
		}
	}

	public int getUserDiscussionMessagesCount(
			long userId, long[] classNameIds, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByU_C(userId, classNameIds);
		}
		else {
			return mbMessagePersistence.countByU_C_S(
				userId, classNameIds, status);
		}
	}

	public int getUserDiscussionMessagesCount(
			long userId, String className, long classPK, int status)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getUserDiscussionMessagesCount(
			userId, classNameId, classPK, status);
	}

	public void subscribeMessage(long userId, long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		subscriptionLocalService.addSubscription(
			userId, message.getGroupId(), MBThread.class.getName(),
			message.getThreadId());
	}

	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		subscriptionLocalService.deleteSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		updateAnswer(message, answer, cascade);
	}

	public void updateAnswer(MBMessage message, boolean answer, boolean cascade)
		throws PortalException, SystemException {

		if (message.isAnswer() != answer) {
			message.setAnswer(answer);

			mbMessagePersistence.update(message, false);
		}

		if (cascade) {
			List<MBMessage> messages = mbMessagePersistence.findByT_P(
				message.getThreadId(), message.getMessageId());

			for (MBMessage curMessage : messages) {
				updateAnswer(curMessage, answer, cascade);
			}
		}
	}

	public void updateAsset(
			long userId, MBMessage message, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		boolean visible = false;

		if (message.isApproved() &&
			((message.getClassNameId() == 0) ||
			 (message.getParentMessageId() != 0))) {

			visible = true;
		}

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, message.getGroupId(), message.getWorkflowClassName(),
			message.getMessageId(), message.getUuid(), 0, assetCategoryIds,
			assetTagNames, visible, null, null, null, null,
			ContentTypes.TEXT_HTML, message.getSubject(), null, null, null,
			null, 0, 0, null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	public MBMessage updateDiscussionMessage(
			long userId, long messageId, String className, long classPK,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNull(subject)) {
			subject = body.substring(0, Math.min(body.length(), 50)) + "...";
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		List<String> existingFiles = new ArrayList<String>();
		double priority = 0.0;
		boolean allowPingbacks = false;

		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));

		return updateMessage(
			userId, messageId, subject, body, inputStreamOVPs, existingFiles,
			priority, allowPingbacks, serviceContext);
	}

	public MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Message

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);
		body = SanitizerUtil.sanitize(
			message.getCompanyId(), message.getGroupId(), userId,
			MBMessage.class.getName(), messageId, "text/" + message.getFormat(),
			body);
		Date now = new Date();

		validate(subject, body);

		subject = getSubject(subject, body);
		body = getBody(subject, body);

		message.setModifiedDate(serviceContext.getModifiedDate(now));
		message.setSubject(subject);
		message.setBody(body);
		message.setAttachments(
			!inputStreamOVPs.isEmpty() || !existingFiles.isEmpty());
		message.setAllowPingbacks(allowPingbacks);

		if (priority != MBThreadConstants.PRIORITY_NOT_GIVEN) {
			message.setPriority(priority);
		}

		if (!message.isPending() &&
			(serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT)) {

			message.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		// Attachments

		long companyId = message.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = message.getAttachmentsDir();

		if (!inputStreamOVPs.isEmpty() || !existingFiles.isEmpty()) {
			try {
				DLStoreUtil.addDirectory(companyId, repositoryId, dirName);
			}
			catch (DuplicateDirectoryException dde) {
			}

			String[] fileNames = DLStoreUtil.getFileNames(
				companyId, repositoryId, dirName);

			for (String fileName: fileNames) {
				if (!existingFiles.contains(fileName)) {
					DLStoreUtil.deleteFile(companyId, repositoryId, fileName);
				}
			}

			for (int i = 0; i < inputStreamOVPs.size(); i++) {
				ObjectValuePair<String, InputStream> inputStreamOVP =
					inputStreamOVPs.get(i);

				String fileName = inputStreamOVP.getKey();
				InputStream inputStream = inputStreamOVP.getValue();

				try {
					DLStoreUtil.addFile(
						companyId, repositoryId, dirName + "/" + fileName,
						inputStream);
				}
				catch (DuplicateFileException dfe) {
				}
			}
		}
		else {
			try {
				DLStoreUtil.deleteDirectory(companyId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
			}
		}

		mbMessagePersistence.update(message, false);

		// Thread

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		if ((priority != MBThreadConstants.PRIORITY_NOT_GIVEN) &&
			(thread.getPriority() != priority)) {

			thread.setPriority(priority);

			mbThreadPersistence.update(thread, false);

			updatePriorities(thread.getThreadId(), priority);
		}

		// Asset

		updateAsset(
			userId, message, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Expando

		ExpandoBridge expandoBridge = message.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Workflow

		serviceContext.setAttribute("update", Boolean.TRUE.toString());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			companyId, message.getGroupId(), userId,
			message.getWorkflowClassName(), message.getMessageId(), message,
			serviceContext);

		return message;
	}

	public MBMessage updateMessage(long messageId, String body)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		message.setBody(body);

		mbMessagePersistence.update(message, false);

		return message;
	}

	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Message

		MBMessage message = getMessage(messageId);

		int oldStatus = message.getStatus();

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		message.setStatus(status);
		message.setStatusByUserId(userId);
		message.setStatusByUserName(user.getFullName());
		message.setStatusDate(serviceContext.getModifiedDate(now));

		mbMessagePersistence.update(message, false);

		// Thread

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		MBCategory category = null;

		if ((thread.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(thread.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = mbCategoryPersistence.findByPrimaryKey(
				thread.getCategoryId());
		}

		if ((thread.getRootMessageId() == message.getMessageId()) &&
			(oldStatus != status)) {

			thread.setStatus(status);
			thread.setStatusByUserId(userId);
			thread.setStatusByUserName(user.getFullName());
			thread.setStatusDate(serviceContext.getModifiedDate(now));
		}

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (oldStatus != WorkflowConstants.STATUS_APPROVED) {

				// Thread

				if ((category != null) &&
					(thread.getRootMessageId() == message.getMessageId())) {

					category.setThreadCount(category.getThreadCount() + 1);

					mbCategoryPersistence.update(category, false);
				}

				thread.setMessageCount(thread.getMessageCount() + 1);

				if (message.isAnonymous()) {
					thread.setLastPostByUserId(0);
				}
				else {
					thread.setLastPostByUserId(message.getUserId());
				}

				thread.setLastPostDate(serviceContext.getModifiedDate(now));

				// Category

				if (category != null) {
					category.setMessageCount(category.getMessageCount() + 1);
					category.setLastPostDate(
						serviceContext.getModifiedDate(now));

					mbCategoryPersistence.update(category, false);

				}

				// Asset

				if ((message.getClassNameId() == 0) ||
					(message.getParentMessageId() != 0)) {

					assetEntryLocalService.updateVisible(
						message.getWorkflowClassName(), message.getMessageId(),
						true);
				}

				if (!message.isDiscussion()) {

					// Social

					if (!message.isAnonymous() && !user.isDefaultUser()) {
						long receiverUserId = 0;
						MBMessage socialEquityLogMessage = message;
						String actionId = ActionKeys.ADD_MESSAGE;

						MBMessage parentMessage =
							mbMessagePersistence.fetchByPrimaryKey(
								message.getParentMessageId());

						if (parentMessage != null) {
							receiverUserId = parentMessage.getUserId();

							if (receiverUserId != userId) {
								socialEquityLogMessage = parentMessage;
								actionId = ActionKeys.REPLY_TO_MESSAGE;
							}
						}

						socialActivityLocalService.addActivity(
							userId, message.getGroupId(),
							MBMessage.class.getName(), message.getMessageId(),
							MBActivityKeys.ADD_MESSAGE, StringPool.BLANK,
							receiverUserId);

						if ((parentMessage != null) &&
							(receiverUserId != userId)) {

							socialActivityLocalService.addActivity(
								userId, parentMessage.getGroupId(),
								MBMessage.class.getName(),
								parentMessage.getMessageId(),
								MBActivityKeys.REPLY_MESSAGE, StringPool.BLANK,
								0);
						}

						socialEquityLogLocalService.addEquityLogs(
							userId, MBMessage.class.getName(),
							socialEquityLogMessage.getMessageId(), actionId,
							StringPool.BLANK);
					}
				}
				else {
					String className = (String)serviceContext.getAttribute(
						"className");
					long classPK = GetterUtil.getLong(
						(String)serviceContext.getAttribute("classPK"));

					// Social

					if (!message.isRoot()) {
						socialEquityLogLocalService.addEquityLogs(
							userId, className, classPK,
							ActionKeys.ADD_DISCUSSION, StringPool.BLANK);
					}

					long parentMessageId = message.getParentMessageId();

					if (parentMessageId !=
							MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

						AssetEntry assetEntry =
							assetEntryLocalService.fetchEntry(
								className, classPK);

						if (assetEntry != null) {
							JSONObject extraDataJSONObject =
								JSONFactoryUtil.createJSONObject();

							extraDataJSONObject.put(
								"messageId", message.getMessageId());

							socialActivityLocalService.addActivity(
								userId, assetEntry.getGroupId(), className,
								classPK,
								SocialActivityConstants.TYPE_ADD_COMMENT,
								extraDataJSONObject.toString(),
								assetEntry.getUserId());
						}
					}
				}

				// Subscriptions

				notifySubscribers(message, serviceContext);
			}

			// Indexer

			if (!message.isDiscussion()) {
				indexer.reindex(message);
			}

			// Ping

			pingPingback(message, serviceContext);
		}
		else if ((oldStatus == WorkflowConstants.STATUS_APPROVED) &&
				 (status != WorkflowConstants.STATUS_APPROVED)) {

			// Thread

			if ((category != null) &&
				(thread.getRootMessageId() == message.getMessageId())) {

				category.setThreadCount(category.getThreadCount() - 1);

				mbCategoryPersistence.update(category, false);
			}

			thread.setMessageCount(thread.getMessageCount() - 1);

			// Category

			if (category != null) {
				category.setMessageCount(category.getMessageCount() - 1);

				mbCategoryPersistence.update(category, false);
			}

			// Asset

			assetEntryLocalService.updateVisible(
				message.getWorkflowClassName(), message.getMessageId(), false);

			if (!message.isDiscussion()) {

				// Indexer

				indexer.delete(message);
			}
		}

		if (status != oldStatus) {
			mbThreadPersistence.update(thread, false);
		}

		// Statistics

		if (!message.isDiscussion()) {
			mbStatsUserLocalService.updateStatsUser(
				message.getGroupId(), userId,
				serviceContext.getModifiedDate(now));
		}

		return message;
	}

	public void updateUserName(long userId, String userName)
		throws SystemException {

		List<MBMessage> messages = mbMessagePersistence.findByUserId(userId);

		for (MBMessage message : messages) {
			message.setUserName(userName);

			mbMessagePersistence.update(message, false);
		}
	}

	protected void deleteDiscussionSocialActivities(
			String className, List<MBMessage> messages)
		throws PortalException, SystemException {

		if (messages.size() == 0) {
			return;
		}

		MBMessage message = messages.get(0);

		MBDiscussion discussion = mbDiscussionPersistence.findByThreadId(
			message.getThreadId());

		long classNameId = PortalUtil.getClassNameId(className);
		long classPK = discussion.getClassPK();

		if (discussion.getClassNameId() != classNameId) {
			return;
		}

		Set<Long> messageIds = new HashSet<Long>();

		for (MBMessage curMessage : messages) {
			messageIds.add(curMessage.getMessageId());
		}

		List<SocialActivity> socialActivities =
			socialActivityLocalService.getActivities(
				0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity socialActivity : socialActivities) {
			if (Validator.isNull(socialActivity.getExtraData())) {
				continue;
			}

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				socialActivity.getExtraData());

			long extraDataMessageId = extraDataJSONObject.getLong("messageId");

			if (messageIds.contains(extraDataMessageId)) {
				socialActivityLocalService.deleteActivity(
					socialActivity.getActivityId());
			}
		}
	}

	protected String getBody(String subject, String body) {
		if (Validator.isNull(body)) {
			return subject;
		}

		return body;
	}

	protected String getSubject(String subject, String body) {
		if (Validator.isNull(subject)) {
			return StringUtil.shorten(body);
		}

		return subject;
	}

	protected void notifyDiscussionSubscribers(
			MBMessage message, ServiceContext serviceContext)
		throws SystemException {

		if (!PrefsPropsUtil.getBoolean(
				message.getCompanyId(),
				PropsKeys.DISCUSSION_EMAIL_COMMENTS_ADDED_ENABLED)) {

			return;
		}

		String contentURL = (String)serviceContext.getAttribute("redirect");

		String userAddress = StringPool.BLANK;
		String userName = (String)serviceContext.getAttribute(
			"pingbackUserName");

		if (Validator.isNull(userName)) {
			userAddress = PortalUtil.getUserEmailAddress(message.getUserId());
			userName = PortalUtil.getUserName(
				message.getUserId(), StringPool.BLANK);
		}

		String fromName = PrefsPropsUtil.getString(
			message.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			message.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		String subject = PrefsPropsUtil.getContent(
			message.getCompanyId(), PropsKeys.DISCUSSION_EMAIL_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			message.getCompanyId(), PropsKeys.DISCUSSION_EMAIL_BODY);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(message.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$COMMENTS_BODY$]", message.getBody(true),
			"[$COMMENTS_USER_ADDRESS$]", userAddress, "[$COMMENTS_USER_NAME$]",
			userName, "[$CONTENT_URL$]", contentURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId(
			"mb_discussion", message.getCategoryId(), message.getMessageId());
		subscriptionSender.setScopeGroupId(message.getGroupId());
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUserId(message.getUserId());

		String className = (String)serviceContext.getAttribute("className");
		long classPK = GetterUtil.getLong(
			(String)serviceContext.getAttribute("classPK"));

		subscriptionSender.addPersistedSubscribers(className, classPK);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void notifySubscribers(
			MBMessage message, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (!message.isApproved() || Validator.isNull(layoutFullURL)) {
			return;
		}

		if (message.isDiscussion()) {
			try{
				notifyDiscussionSubscribers(message, serviceContext);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			return;
		}

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = message.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.MESSAGE_BOARDS;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				message.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		boolean update = GetterUtil.getBoolean(
			(String)serviceContext.getAttribute("update"));

		if (!update && MBUtil.getEmailMessageAddedEnabled(preferences)) {
		}
		else if (update && MBUtil.getEmailMessageUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			message.getCompanyId());

		Group group = groupPersistence.findByPrimaryKey(message.getGroupId());

		String emailAddress = PortalUtil.getUserEmailAddress(
			message.getUserId());
		String fullName = PortalUtil.getUserName(
			message.getUserId(), message.getUserName());

		if (message.isAnonymous()) {
			emailAddress = StringPool.BLANK;
			fullName = LanguageUtil.get(
				ServiceContextUtil.getLocale(serviceContext), "anonymous");
		}

		MBCategory category = message.getCategory();

		String categoryName = category.getName();

		if (category.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			categoryName = LanguageUtil.get(
				ServiceContextUtil.getLocale(serviceContext),
					"message-boards-home");

			categoryName += " - " + group.getDescriptiveName();
		}

		List<Long> categoryIds = new ArrayList<Long>();

		categoryIds.add(message.getCategoryId());

		if ((message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(message.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			categoryIds.addAll(category.getAncestorCategoryIds());
		}

		String messageURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR +
				"message_boards/view_message/" + message.getMessageId();

		String fromName = MBUtil.getEmailFromName(
			preferences, message.getCompanyId());
		String fromAddress = MBUtil.getEmailFromAddress(
			preferences, message.getCompanyId());

		String mailingListAddress = StringPool.BLANK;

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			mailingListAddress = MBUtil.getMailingListAddress(
				message.getGroupId(), message.getCategoryId(),
				message.getMessageId(), company.getMx(), fromAddress);
		}

		String subjectPrefix = null;
		String body = null;
		String signature = null;

		if (update) {
			subjectPrefix = MBUtil.getEmailMessageUpdatedSubjectPrefix(
				preferences);
			body = MBUtil.getEmailMessageUpdatedBody(preferences);
			signature = MBUtil.getEmailMessageUpdatedSignature(preferences);
		}
		else {
			subjectPrefix = MBUtil.getEmailMessageAddedSubjectPrefix(
				preferences);
			body = MBUtil.getEmailMessageAddedBody(preferences);
			signature = MBUtil.getEmailMessageAddedSignature(preferences);
		}

		String subject = message.getSubject();

		if (subject.indexOf(subjectPrefix) == -1) {
			subject = subjectPrefix.trim() + " " + subject.trim();
		}

		if (Validator.isNotNull(signature)) {
			body +=  "\n--\n" + signature;
		}

		String inReplyTo = null;

		if (message.getParentMessageId() !=
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			inReplyTo = PortalUtil.getMailId(
				company.getMx(), MBUtil.MESSAGE_POP_PORTLET_PREFIX,
				message.getCategoryId(), message.getParentMessageId());
		}

		SubscriptionSender subscriptionSenderPrototype =
			new MBSubscriptionSender();

		subscriptionSenderPrototype.setBody(body);
		subscriptionSenderPrototype.setBulk(true);
		subscriptionSenderPrototype.setCompanyId(message.getCompanyId());
		subscriptionSenderPrototype.setContextAttributes(
			"[$CATEGORY_NAME$]", categoryName, "[$MAILING_LIST_ADDRESS$]",
			mailingListAddress, "[$MESSAGE_BODY$]", message.getBody(),
			"[$MESSAGE_ID$]", message.getMessageId(), "[$MESSAGE_SUBJECT$]",
			message.getSubject(), "[$MESSAGE_URL$]", messageURL,
			"[$MESSAGE_USER_ADDRESS$]", emailAddress, "[$MESSAGE_USER_NAME$]",
			fullName);
		subscriptionSenderPrototype.setFrom(fromAddress, fromName);
		subscriptionSenderPrototype.setHtmlFormat(
			MBUtil.getEmailHtmlFormat(preferences));
		subscriptionSenderPrototype.setInReplyTo(inReplyTo);
		subscriptionSenderPrototype.setMailId(
			MBUtil.MESSAGE_POP_PORTLET_PREFIX, message.getCategoryId(),
			message.getMessageId());
		subscriptionSenderPrototype.setPortletId(PortletKeys.MESSAGE_BOARDS);
		subscriptionSenderPrototype.setReplyToAddress(mailingListAddress);
		subscriptionSenderPrototype.setScopeGroupId(message.getGroupId());
		subscriptionSenderPrototype.setSubject(subject);
		subscriptionSenderPrototype.setUserId(message.getUserId());

		SubscriptionSender subscriptionSender =
			(SubscriptionSender)SerializableUtil.clone(
				subscriptionSenderPrototype);

		for (long categoryId : categoryIds) {
			if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				categoryId = message.getGroupId();
			}

			subscriptionSender.addPersistedSubscribers(
				MBCategory.class.getName(), categoryId);
		}

		subscriptionSender.addPersistedSubscribers(
			MBThread.class.getName(), message.getThreadId());

		subscriptionSender.flushNotificationsAsync();

		if (!MailingListThreadLocal.isSourceMailingList()) {
			for (long categoryId : categoryIds) {
				MBSubscriptionSender sourceMailingListSubscriptionSender =
					(MBSubscriptionSender)SerializableUtil.clone(
						subscriptionSenderPrototype);

				sourceMailingListSubscriptionSender.initialize();

				sourceMailingListSubscriptionSender.setBulk(false);

				sourceMailingListSubscriptionSender.addMailingListSubscriber(
					message.getGroupId(), categoryId);

				sourceMailingListSubscriptionSender.flushNotificationsAsync();
			}
		}
	}

	protected void pingPingback(
		MBMessage message, ServiceContext serviceContext) {

		if (!PropsValues.BLOGS_PINGBACK_ENABLED ||
			!message.isAllowPingbacks() || !message.isApproved()) {

			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		String sourceUri =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR +
				"message_boards/view_message/" + message.getMessageId();

		Source source = new Source(message.getBody(true));

		List<StartTag> startTags = source.getAllStartTags("a");

		for (StartTag startTag : startTags) {
			String targetUri = startTag.getAttributeValue("href");

			if (Validator.isNotNull(targetUri)) {
				try {
					LinkbackProducerUtil.sendPingback(sourceUri, targetUri);
				}
				catch (Exception e) {
					_log.error("Error while sending pingback " + targetUri, e);
				}
			}
		}
	}

	protected void updatePriorities(long threadId, double priority)
		throws SystemException {

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			if (message.getPriority() != priority) {
				message.setPriority(priority);

				mbMessagePersistence.update(message, false);
			}
		}
	}

	protected void validate(String subject, String body)
		throws PortalException {

		if (Validator.isNull(subject) && Validator.isNull(body)) {
			throw new MessageSubjectException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBMessageLocalServiceImpl.class);

}