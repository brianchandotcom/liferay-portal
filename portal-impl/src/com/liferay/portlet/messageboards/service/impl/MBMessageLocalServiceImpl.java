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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutURLUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.messageboards.DiscussionMaxCommentsException;
import com.liferay.portlet.messageboards.MBGroupServiceSettings;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
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
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.messageboards.social.MBActivityKeys;
import com.liferay.portlet.messageboards.util.MBSubscriptionSender;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.MailingListThreadLocal;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;
import com.liferay.portlet.messageboards.util.comparator.ThreadLastPostDateComparator;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.util.SerializableUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

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

	@Override
	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, int workflowAction)
		throws PortalException {

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

		// Message

		validateDiscussionMaxComments(className, classPK);

		long categoryId = MBCategoryConstants.DISCUSSION_CATEGORY_ID;

		if (Validator.isNull(subject)) {
			if (Validator.isNotNull(body)) {
				int pos = Math.min(body.length(), 50);

				subject = body.substring(0, pos) + "...";
			}
			else {
				throw new MessageBodyException();
			}
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
			subject, body, PropsValues.DISCUSSION_COMMENTS_FORMAT,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);

		// Discussion

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			long classNameId = classNameLocalService.getClassNameId(className);

			MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
				classNameId, classPK);

			if (discussion == null) {
				mbDiscussionLocalService.addDiscussion(
					userId, groupId, classNameId, classPK,
					message.getThreadId(), serviceContext);
			}
		}

		return message;
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			long threadId, long parentMessageId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		// Message

		User user = userPersistence.findByPrimaryKey(userId);
		userName = user.isDefaultUser() ? userName : user.getFullName();
		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(groupId);

		if (mbGroupServiceSettings != null) {
			if (!mbGroupServiceSettings.isAllowAnonymousPosting()) {
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

		Map<String, Object> options = new HashMap<>();

		boolean discussion = false;

		if (categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID) {
			discussion = true;
		}

		options.put("discussion", discussion);

		body = SanitizerUtil.sanitize(
			user.getCompanyId(), groupId, userId, MBMessage.class.getName(),
			messageId, "text/" + format, Sanitizer.MODE_ALL, body, options);

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

		if (thread == null) {
			if (parentMessageId ==
					MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

				thread = mbThreadLocalService.addThread(
					categoryId, message, serviceContext);
			}
			else {
				throw new NoSuchThreadException("{threadId=" + threadId + "}");
			}
		}

		if ((priority != MBThreadConstants.PRIORITY_NOT_GIVEN) &&
			(thread.getPriority() != priority)) {

			thread.setPriority(priority);

			mbThreadPersistence.update(thread);

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
		message.setAnonymous(anonymous);

		if (message.isDiscussion()) {
			long classNameId = classNameLocalService.getClassNameId(
				(String)serviceContext.getAttribute("className"));
			long classPK = ParamUtil.getLong(serviceContext, "classPK");

			message.setClassNameId(classNameId);
			message.setClassPK(classPK);
		}

		message.setExpandoBridgeAttributes(serviceContext);

		mbMessagePersistence.update(message);

		// Attachments

		if (ListUtil.isNotEmpty(inputStreamOVPs)) {
			Folder folder = message.addAttachmentsFolder();

			PortletFileRepositoryUtil.addPortletFileEntries(
				message.getGroupId(), userId, MBMessage.class.getName(),
				message.getMessageId(), PortletKeys.MESSAGE_BOARDS,
				folder.getFolderId(), inputStreamOVPs);
		}

		// Resources

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("propagatePermissions"))) {

			MBUtil.propagatePermissions(
				message.getCompanyId(), groupId, parentMessageId,
				serviceContext);
		}

		if (!message.isDiscussion()) {
			if (user.isDefaultUser()) {
				addMessageResources(message, true, true);
			}
			else if (serviceContext.isAddGroupPermissions() ||
					 serviceContext.isAddGuestPermissions()) {

				addMessageResources(
					message, serviceContext.isAddGroupPermissions(),
					serviceContext.isAddGuestPermissions());
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
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.isAssetEntryVisible());

		// Workflow

		startWorkflowInstance(userId, message, serviceContext);

		return message;
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		return addMessage(
			userId, userName, groupId, categoryId, 0, 0, subject, body,
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String fileName, File file,
			ServiceContext serviceContext)
		throws FileNotFoundException, PortalException {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>(1);

		InputStream inputStream = new FileInputStream(file);

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<>(fileName, inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return addMessage(
			userId, userName, groupId, categoryId, 0, 0, subject, body,
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		long threadId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;

		return addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addMessage(long, String,
	 *             long, long, String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public MBMessage addMessage(
			long userId, String userName, long categoryId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			MBCategory category = mbCategoryPersistence.findByPrimaryKey(
				categoryId);

			groupId = category.getGroupId();
		}

		return addMessage(
			userId, userName, groupId, categoryId, subject, body,
			serviceContext);
	}

	@Override
	public void addMessageResources(
			long messageId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		addMessageResources(message, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addMessageResources(
			long messageId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		addMessageResources(message, groupPermissions, guestPermissions);
	}

	@Override
	public void addMessageResources(
			MBMessage message, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			message.getCompanyId(), message.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addMessageResources(
			MBMessage message, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			message.getCompanyId(), message.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(), groupPermissions,
			guestPermissions);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMessage deleteDiscussionMessage(long messageId)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		deleteDiscussionSocialActivities(BlogsEntry.class.getName(), message);

		return mbMessageLocalService.deleteMessage(message);
	}

	@Override
	public void deleteDiscussionMessages(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to delete discussion message for class name " +
						className + " and class PK " + classPK +
							" because it does not exist");
			}

			return;
		}

		List<MBMessage> messages = mbMessagePersistence.findByT_P(
			discussion.getThreadId(),
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, 0, 1);

		if (!messages.isEmpty()) {
			MBMessage message = messages.get(0);

			deleteDiscussionSocialActivities(
				BlogsEntry.class.getName(), message);

			mbThreadLocalService.deleteThread(message.getThreadId());
		}

		mbDiscussionPersistence.remove(discussion);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMessage deleteMessage(long messageId) throws PortalException {
		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		return mbMessageLocalService.deleteMessage(message);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public MBMessage deleteMessage(MBMessage message) throws PortalException {

		// Attachments

		long folderId = message.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deletePortletFolder(folderId);
		}

		// Thread

		int count = mbMessagePersistence.countByThreadId(message.getThreadId());

		if (count == 1) {

			// Attachments

			long threadAttachmentsFolderId =
				message.getThreadAttachmentsFolderId();

			if (threadAttachmentsFolderId !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				PortletFileRepositoryUtil.deletePortletFolder(
					threadAttachmentsFolderId);
			}

			// Subscriptions

			subscriptionLocalService.deleteSubscriptions(
				message.getCompanyId(), MBThread.class.getName(),
				message.getThreadId());

			// Thread

			MBThread thread = mbThreadPersistence.findByPrimaryKey(
				message.getThreadId());

			mbThreadPersistence.remove(thread);

			// Category

			if ((message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				(message.getCategoryId() !=
					MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

				MBUtil.updateCategoryStatistics(
					message.getCompanyId(), message.getCategoryId());
			}

			// Indexer

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				MBThread.class);

			indexer.delete(thread);
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

					mbMessagePersistence.update(childMessage);

					List<MBMessage> repliesMessages =
						mbMessagePersistence.findByThreadReplies(
							message.getThreadId());

					for (MBMessage repliesMessage : repliesMessages) {
						repliesMessage.setRootMessageId(
							childMessage.getMessageId());

						mbMessagePersistence.update(repliesMessage);
					}

					thread.setRootMessageId(childMessage.getMessageId());
					thread.setRootMessageUserId(childMessage.getUserId());

					mbThreadPersistence.update(thread);
				}
			}

			// Message is a child message

			else {
				List<MBMessage> childrenMessages =
					mbMessagePersistence.findByT_P(
						message.getThreadId(), message.getMessageId());

				// Message has children messages

				if (!childrenMessages.isEmpty()) {
					for (MBMessage childMessage : childrenMessages) {
						childMessage.setParentMessageId(
							message.getParentMessageId());

						mbMessagePersistence.update(childMessage);
					}
				}
				else if (message.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

					MessageCreateDateComparator comparator =
						new MessageCreateDateComparator(true);

					MBMessage[] prevAndNextMessages =
						mbMessagePersistence.findByT_S_PrevAndNext(
							message.getMessageId(), thread.getThreadId(),
							WorkflowConstants.STATUS_APPROVED, comparator);

					if (prevAndNextMessages[2] == null) {
						thread.setLastPostByUserId(
							prevAndNextMessages[0].getUserId());
						thread.setLastPostDate(
							prevAndNextMessages[0].getModifiedDate());

						mbThreadPersistence.update(thread);
					}
				}
			}

			// Thread

			if (message.isApproved()) {
				MBUtil.updateThreadMessageCount(
					thread.getCompanyId(), thread.getThreadId());
			}

			// Category

			if ((message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				(message.getCategoryId() !=
					MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
				!message.isDraft()) {

				MBUtil.updateCategoryMessageCount(
					message.getCompanyId(), message.getCategoryId());
			}

			// Indexer

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				MBThread.class);

			indexer.reindex(thread);
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			message.getWorkflowClassName(), message.getMessageId());

		// Expando

		expandoRowLocalService.deleteRows(message.getMessageId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			message.getWorkflowClassName(), message.getMessageId());

		// Resources

		if (!message.isDiscussion()) {
			resourceLocalService.deleteResource(
				message.getCompanyId(), message.getWorkflowClassName(),
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

		return message;
	}

	@Override
	public void deleteMessageAttachment(long messageId, String fileName)
		throws PortalException {

		MBMessage message = getMessage(messageId);

		long folderId = message.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		PortletFileRepositoryUtil.deletePortletFileEntry(
			message.getGroupId(), folderId, fileName);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws PortalException {

		MBMessage message = getMessage(messageId);

		long folderId = message.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		PortletFileRepositoryUtil.deletePortletFileEntries(
			message.getGroupId(), folderId);
	}

	@Override
	public void emptyMessageAttachments(long messageId) throws PortalException {
		MBMessage message = getMessage(messageId);

		long folderId = message.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		PortletFileRepositoryUtil.deletePortletFileEntries(
			message.getGroupId(), folderId, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_C(
				groupId, categoryId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_C_S(
				groupId, categoryId, status, start, end);
		}
	}

	@Override
	public List<MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_C(
				groupId, categoryId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_C_S(
				groupId, categoryId, status, start, end, obc);
		}
	}

	@Override
	public int getCategoryMessagesCount(
		long groupId, long categoryId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbMessagePersistence.countByG_C_S(
				groupId, categoryId, status);
		}
	}

	@Override
	public List<MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByCompanyId(companyId, start, end);
		}
		else {
			return mbMessagePersistence.findByC_S(
				companyId, status, start, end);
		}
	}

	@Override
	public List<MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByC_S(
				companyId, status, start, end, obc);
		}
	}

	@Override
	public int getCompanyMessagesCount(long companyId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByCompanyId(companyId);
		}
		else {
			return mbMessagePersistence.countByC_S(companyId, status);
		}
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status)
		throws PortalException {

		return getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status,
			new MessageThreadComparator());
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, Comparator<MBMessage> comparator)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		MBMessage message = null;

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, classPK);

		if (discussion != null) {
			message = mbMessagePersistence.findByT_P_First(
				discussion.getThreadId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, null);
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

				message = mbMessagePersistence.fetchByT_P_First(
					0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, null);

				if (message == null) {
					throw se;
				}
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}

		return getMessageDisplay(
			userId, message, status, MBThreadConstants.THREAD_VIEW_COMBINATION,
			false, comparator);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getDiscussionMessageDisplay(long, long, String, long, int)}
	 */
	@Deprecated
	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, String threadView)
		throws PortalException {

		return getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status);
	}

	@Override
	public int getDiscussionMessagesCount(
		long classNameId, long classPK, int status) {

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

	@Override
	public int getDiscussionMessagesCount(
		String className, long classPK, int status) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return getDiscussionMessagesCount(classNameId, classPK, status);
	}

	@Override
	public List<MBDiscussion> getDiscussions(String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.findByClassNameId(classNameId);
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByGroupId(groupId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_S(groupId, status, start, end);
		}
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByGroupId(groupId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_S(
				groupId, status, start, end, obc);
		}
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_U(groupId, userId, start, end);
		}
		else {
			return mbMessagePersistence.findByG_U_S(
				groupId, userId, status, start, end);
		}
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByG_U_S(
				groupId, userId, status, start, end, obc);
		}
	}

	@Override
	public int getGroupMessagesCount(long groupId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByGroupId(groupId);
		}
		else {
			return mbMessagePersistence.countByG_S(groupId, status);
		}
	}

	@Override
	public int getGroupMessagesCount(long groupId, long userId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByG_U(groupId, userId);
		}
		else {
			return mbMessagePersistence.countByG_U_S(groupId, userId, status);
		}
	}

	@Override
	public MBMessage getMessage(long messageId) throws PortalException {
		return mbMessagePersistence.findByPrimaryKey(messageId);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, long messageId, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		MBMessage message = getMessage(messageId);

		return getMessageDisplay(
			userId, message, status, threadView, includePrevAndNext);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		return getMessageDisplay(
			userId, message, status, threadView, includePrevAndNext,
			new MessageThreadComparator());
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status, String threadView,
			boolean includePrevAndNext, Comparator<MBMessage> comparator)
		throws PortalException {

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

				socialActivityLocalService.addActivity(
					userId, rootMessage.getGroupId(), MBMessage.class.getName(),
					rootMessage.getMessageId(),
					SocialActivityConstants.TYPE_VIEW, StringPool.BLANK, 0);
			}
		}

		MBThread previousThread = null;
		MBThread nextThread = null;

		if (message.isApproved() && includePrevAndNext) {
			ThreadLastPostDateComparator threadLastPostDateComparator =
				new ThreadLastPostDateComparator(false);

			MBThread[] prevAndNextThreads = null;

			if (status == WorkflowConstants.STATUS_ANY) {
				prevAndNextThreads =
					mbThreadPersistence.filterFindByG_C_NotS_PrevAndNext(
						message.getThreadId(), message.getGroupId(),
						message.getCategoryId(),
						WorkflowConstants.STATUS_IN_TRASH,
						threadLastPostDateComparator);
			}
			else {
				prevAndNextThreads =
					mbThreadPersistence.filterFindByG_C_S_PrevAndNext(
						message.getThreadId(), message.getGroupId(),
						message.getCategoryId(), status,
						threadLastPostDateComparator);
			}

			previousThread = prevAndNextThreads[0];
			nextThread = prevAndNextThreads[2];
		}

		return new MBMessageDisplayImpl(
			message, parentMessage, category, thread, previousThread,
			nextThread, status, threadView, this, comparator);
	}

	@Override
	public List<MBMessage> getMessages(
		String className, long classPK, int status) {

		long classNameId = classNameLocalService.getClassNameId(className);

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByC_C(classNameId, classPK);
		}
		else {
			return mbMessagePersistence.findByC_C_S(
				classNameId, classPK, status);
		}
	}

	@Override
	public List<MBMessage> getNoAssetMessages() {
		return mbMessageFinder.findByNoAssets();
	}

	@Override
	public int getPositionInThread(long messageId) throws PortalException {
		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		return mbMessageFinder.countByC_T(
			message.getCreateDate(), message.getThreadId());
	}

	@Override
	public List<MBMessage> getThreadMessages(long threadId, int status) {
		return getThreadMessages(
			threadId, status, new MessageThreadComparator());
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long threadId, int status, Comparator<MBMessage> comparator) {

		List<MBMessage> messages = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			messages = mbMessagePersistence.findByThreadId(threadId);
		}
		else {
			messages = mbMessagePersistence.findByT_S(threadId, status);
		}

		return ListUtil.sort(messages, comparator);
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long threadId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByThreadId(threadId, start, end);
		}
		else {
			return mbMessagePersistence.findByT_S(threadId, status, start, end);
		}
	}

	@Override
	public int getThreadMessagesCount(long threadId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByThreadId(threadId);
		}
		else {
			return mbMessagePersistence.countByT_S(threadId, status);
		}
	}

	@Override
	public List<MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByThreadReplies(
				threadId, start, end);
		}
		else {
			return mbMessagePersistence.findByTR_S(
				threadId, status, start, end);
		}
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, long classNameId, long classPK, int status, int start,
		int end, OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByU_C_C(
				userId, classNameId, classPK, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByU_C_C_S(
				userId, classNameId, classPK, status, start, end, obc);
		}
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, long[] classNameIds, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.findByU_C(
				userId, classNameIds, start, end, obc);
		}
		else {
			return mbMessagePersistence.findByU_C_S(
				userId, classNameIds, status, start, end, obc);
		}
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, String className, long classPK, int status, int start,
		int end, OrderByComparator<MBMessage> obc) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return getUserDiscussionMessages(
			userId, classNameId, classPK, status, start, end, obc);
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, long classNameId, long classPK, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByU_C_C(
				userId, classNameId, classPK);
		}
		else {
			return mbMessagePersistence.countByU_C_C_S(
				userId, classNameId, classPK, status);
		}
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, long[] classNameIds, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.countByU_C(userId, classNameIds);
		}
		else {
			return mbMessagePersistence.countByU_C_S(
				userId, classNameIds, status);
		}
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, String className, long classPK, int status) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return getUserDiscussionMessagesCount(
			userId, classNameId, classPK, status);
	}

	@Override
	public long moveMessageAttachmentToTrash(
			long userId, long messageId, String fileName)
		throws PortalException {

		MBMessage message = getMessage(messageId);

		long folderId = message.getAttachmentsFolderId();

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			message.getGroupId(), folderId, fileName);

		PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			userId, fileEntry.getFileEntryId());

		return fileEntry.getFileEntryId();
	}

	@Override
	public void restoreMessageAttachmentFromTrash(
			long userId, long messageId, String deletedFileName)
		throws PortalException {

		MBMessage message = getMessage(messageId);

		Folder folder = message.addAttachmentsFolder();

		PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
			message.getGroupId(), userId, folder.getFolderId(),
			deletedFileName);
	}

	@Override
	public void subscribeMessage(long userId, long messageId)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		subscriptionLocalService.addSubscription(
			userId, message.getGroupId(), MBThread.class.getName(),
			message.getThreadId());
	}

	@Override
	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		subscriptionLocalService.deleteSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		updateAnswer(message, answer, cascade);
	}

	@Override
	public void updateAnswer(MBMessage message, boolean answer, boolean cascade)
		throws PortalException {

		if (message.isAnswer() != answer) {
			message.setAnswer(answer);

			mbMessagePersistence.update(message);
		}

		if (cascade) {
			List<MBMessage> messages = mbMessagePersistence.findByT_P(
				message.getThreadId(), message.getMessageId());

			for (MBMessage curMessage : messages) {
				updateAnswer(curMessage, answer, cascade);
			}
		}
	}

	@Override
	public void updateAsset(
			long userId, MBMessage message, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException {

		updateAsset(
			userId, message, assetCategoryIds, assetTagNames, assetLinkEntryIds,
			true);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			long userId, long messageId, String className, long classPK,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(subject)) {
			if (Validator.isNotNull(body)) {
				int pos = Math.min(body.length(), 50);

				subject = body.substring(0, pos) + "...";
			}
			else {
				throw new MessageBodyException();
			}
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs = null;
		List<String> existingFiles = null;
		double priority = 0.0;
		boolean allowPingbacks = false;

		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));

		return updateMessage(
			userId, messageId, subject, body, inputStreamOVPs, existingFiles,
			priority, allowPingbacks, serviceContext);
	}

	@Override
	public MBMessage updateMessage(
			long userId, long messageId, String body,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		return updateMessage(
			userId, messageId, message.getSubject(), body, null, null,
			message.getPriority(), message.isAllowPingbacks(), serviceContext);
	}

	@Override
	public MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		// Message

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		int oldStatus = message.getStatus();

		Date modifiedDate = serviceContext.getModifiedDate(new Date());
		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);

		Map<String, Object> options = new HashMap<>();

		options.put("discussion", message.isDiscussion());

		body = SanitizerUtil.sanitize(
			message.getCompanyId(), message.getGroupId(), userId,
			MBMessage.class.getName(), messageId, "text/" + message.getFormat(),
			Sanitizer.MODE_ALL, body, options);

		validate(subject, body);

		subject = getSubject(subject, body);
		body = getBody(subject, body);

		message.setModifiedDate(modifiedDate);
		message.setSubject(subject);
		message.setBody(body);
		message.setAllowPingbacks(allowPingbacks);

		if (priority != MBThreadConstants.PRIORITY_NOT_GIVEN) {
			message.setPriority(priority);
		}

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			if (!message.isDraft() && !message.isPending()) {
				message.setStatus(WorkflowConstants.STATUS_DRAFT);

				// Thread

				User user = userPersistence.findByPrimaryKey(userId);

				updateThreadStatus(
					thread, message, user, oldStatus, modifiedDate);

				// Asset

				assetEntryLocalService.updateVisible(
					message.getWorkflowClassName(), message.getMessageId(),
					false);

				if (!message.isDiscussion()) {

					// Indexer

					Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
						MBMessage.class);

					indexer.delete(message);
				}
			}
		}

		// Attachments

		if ((inputStreamOVPs != null) || (existingFiles != null)) {
			if (ListUtil.isNotEmpty(inputStreamOVPs) ||
				ListUtil.isNotEmpty(existingFiles)) {

				List<FileEntry> fileEntries =
					message.getAttachmentsFileEntries();

				for (FileEntry fileEntry : fileEntries) {
					String fileEntryId = String.valueOf(
						fileEntry.getFileEntryId());

					if (!existingFiles.contains(fileEntryId)) {
						if (!TrashUtil.isTrashEnabled(message.getGroupId())) {
							deleteMessageAttachment(
								messageId, fileEntry.getTitle());
						}
						else {
							moveMessageAttachmentToTrash(
								userId, messageId, fileEntry.getTitle());
						}
					}
				}

				Folder folder = message.addAttachmentsFolder();

				PortletFileRepositoryUtil.addPortletFileEntries(
					message.getGroupId(), userId, MBMessage.class.getName(),
					message.getMessageId(), PortletKeys.MESSAGE_BOARDS,
					folder.getFolderId(), inputStreamOVPs);
			}
			else {
				if (TrashUtil.isTrashEnabled(message.getGroupId())) {
					List<FileEntry> fileEntries =
						message.getAttachmentsFileEntries();

					for (FileEntry fileEntry : fileEntries) {
						moveMessageAttachmentToTrash(
							userId, messageId, fileEntry.getTitle());
					}
				}
				else {
					deleteMessageAttachments(message.getMessageId());
				}
			}
		}

		message.setExpandoBridgeAttributes(serviceContext);

		mbMessagePersistence.update(message);

		// Statistics

		if ((serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) &&
			!message.isDiscussion()) {

			mbStatsUserLocalService.updateStatsUser(
				message.getGroupId(), userId, message.getModifiedDate());
		}

		// Thread

		if ((priority != MBThreadConstants.PRIORITY_NOT_GIVEN) &&
			(thread.getPriority() != priority)) {

			thread.setPriority(priority);

			mbThreadPersistence.update(thread);

			updatePriorities(thread.getThreadId(), priority);
		}

		// Asset

		updateAsset(
			userId, message, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Workflow

		startWorkflowInstance(userId, message, serviceContext);

		return message;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MBMessage updateMessage(long messageId, String body)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		message.setBody(body);

		mbMessagePersistence.update(message);

		return message;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	 *             int, ServiceContext, Map)}
	 */
	@Deprecated
	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return updateStatus(
			userId, messageId, status, serviceContext,
			new HashMap<String, Serializable>());
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Message

		MBMessage message = getMessage(messageId);

		int oldStatus = message.getStatus();

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		Date modifiedDate = serviceContext.getModifiedDate(now);

		message.setStatus(status);
		message.setStatusByUserId(userId);
		message.setStatusByUserName(user.getFullName());
		message.setStatusDate(modifiedDate);

		mbMessagePersistence.update(message);

		// Thread

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		updateThreadStatus(thread, message, user, oldStatus, modifiedDate);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (oldStatus != WorkflowConstants.STATUS_APPROVED) {

				// Asset

				if (serviceContext.isAssetEntryVisible() &&
					((message.getClassNameId() == 0) ||
					 (message.getParentMessageId() != 0))) {

					Date publishDate = null;

					AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
						message.getWorkflowClassName(), message.getMessageId());

					if ((assetEntry != null) &&
						(assetEntry.getPublishDate() != null)) {

						publishDate = assetEntry.getPublishDate();
					}
					else {
						publishDate = now;

						serviceContext.setCommand(Constants.ADD);
					}

					assetEntryLocalService.updateEntry(
						message.getWorkflowClassName(), message.getMessageId(),
						publishDate, true);
				}

				if (serviceContext.isCommandAdd()) {

					// Social

					JSONObject extraDataJSONObject =
						JSONFactoryUtil.createJSONObject();

					extraDataJSONObject.put("title", message.getSubject());

					if (!message.isDiscussion() ) {
						if (!message.isAnonymous() && !user.isDefaultUser()) {
							long receiverUserId = 0;

							MBMessage parentMessage =
								mbMessagePersistence.fetchByPrimaryKey(
									message.getParentMessageId());

							if (parentMessage != null) {
								receiverUserId = parentMessage.getUserId();
							}

							socialActivityLocalService.addActivity(
								message.getUserId(), message.getGroupId(),
								MBMessage.class.getName(),
								message.getMessageId(),
								MBActivityKeys.ADD_MESSAGE,
								extraDataJSONObject.toString(), receiverUserId);

							if ((parentMessage != null) &&
								(receiverUserId != message.getUserId())) {

								socialActivityLocalService.addActivity(
									message.getUserId(),
									parentMessage.getGroupId(),
									MBMessage.class.getName(),
									parentMessage.getMessageId(),
									MBActivityKeys.REPLY_MESSAGE,
									extraDataJSONObject.toString(), 0);
							}
						}
					}
					else {
						String className = (String)serviceContext.getAttribute(
							"className");
						long classPK = ParamUtil.getLong(
							serviceContext, "classPK");
						long parentMessageId = message.getParentMessageId();

						if (parentMessageId !=
								MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

							AssetEntry assetEntry =
								assetEntryLocalService.fetchEntry(
									className, classPK);

							if (assetEntry != null) {
								extraDataJSONObject.put(
									"messageId", message.getMessageId());

								socialActivityLocalService.addActivity(
									message.getUserId(),
									assetEntry.getGroupId(), className, classPK,
									SocialActivityConstants.TYPE_ADD_COMMENT,
									extraDataJSONObject.toString(),
									assetEntry.getUserId());
							}
						}
					}
				}
			}

			// Subscriptions

			notifySubscribers(
				userId, (MBMessage)message.clone(),
				(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
				serviceContext);

			// Indexer

			indexer.reindex(message);

			// Ping

			pingPingback(message, serviceContext);
		}
		else if (oldStatus == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateVisible(
				message.getWorkflowClassName(), message.getMessageId(), false);

			// Indexer

			indexer.delete(message);
		}

		// Statistics

		if (!message.isDiscussion()) {
			mbStatsUserLocalService.updateStatsUser(
				message.getGroupId(), userId,
				serviceContext.getModifiedDate(now));
		}

		return message;
	}

	@Override
	public void updateUserName(long userId, String userName) {
		List<MBMessage> messages = mbMessagePersistence.findByUserId(userId);

		for (MBMessage message : messages) {
			message.setUserName(userName);

			mbMessagePersistence.update(message);
		}
	}

	protected void deleteDiscussionSocialActivities(
			String className, MBMessage message)
		throws PortalException {

		MBDiscussion discussion = mbDiscussionPersistence.findByThreadId(
			message.getThreadId());

		long classNameId = classNameLocalService.getClassNameId(className);
		long classPK = discussion.getClassPK();

		if (discussion.getClassNameId() != classNameId) {
			return;
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

			if (message.getMessageId() == extraDataMessageId) {
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

	protected String getMessageURL(
			MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		String entryURL = GetterUtil.getString(
			serviceContext.getAttribute("entryURL"));

		if (Validator.isNotNull(entryURL)) {
			return entryURL;
		}

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			if (Validator.isNull(serviceContext.getLayoutFullURL())) {
				return StringPool.BLANK;
			}

			return serviceContext.getLayoutFullURL() +
				Portal.FRIENDLY_URL_SEPARATOR + "message_boards/view_message/" +
					message.getMessageId();
		}

		String layoutURL = LayoutURLUtil.getLayoutURL(
			message.getGroupId(), PortletKeys.MESSAGE_BOARDS, serviceContext);

		if (Validator.isNotNull(layoutURL)) {
			return layoutURL + Portal.FRIENDLY_URL_SEPARATOR +
				"message_boards/view_message/" + message.getMessageId();
		}
		else {
			long controlPanelPlid = PortalUtil.getControlPanelPlid(
				serviceContext.getCompanyId());

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, PortletKeys.MESSAGE_BOARDS_ADMIN, controlPanelPlid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"struts_action", "/message_boards_admin/view_message");
			portletURL.setParameter(
				"messageId", String.valueOf(message.getMessageId()));

			return portletURL.toString();
		}
	}

	protected String getSubject(String subject, String body) {
		if (Validator.isNull(subject)) {
			return StringUtil.shorten(body);
		}

		return subject;
	}

	protected void notifyDiscussionSubscribers(
			long userId, MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		if (!PrefsPropsUtil.getBoolean(
				message.getCompanyId(),
				PropsKeys.DISCUSSION_EMAIL_COMMENTS_ADDED_ENABLED)) {

			return;
		}

		MBDiscussion mbDiscussion =
			mbDiscussionLocalService.getThreadDiscussion(message.getThreadId());

		String contentURL = (String)serviceContext.getAttribute("contentURL");

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
		subscriptionSender.setClassName(MBDiscussion.class.getName());
		subscriptionSender.setClassPK(mbDiscussion.getDiscussionId());
		subscriptionSender.setContextAttribute(
			"[$COMMENTS_BODY$]", message.getBody(true), false);
		subscriptionSender.setContextAttributes(
			"[$COMMENTS_USER_ADDRESS$]", userAddress, "[$COMMENTS_USER_NAME$]",
			userName, "[$CONTENT_URL$]", contentURL);
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(message.getBody());
		subscriptionSender.setEntryURL(contentURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		Date modifiedDate = message.getModifiedDate();

		subscriptionSender.setMailId(
			"mb_discussion", message.getCategoryId(), message.getMessageId(),
			modifiedDate.getTime());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(PortletKeys.COMMENTS);
		subscriptionSender.setScopeGroupId(message.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUniqueMailId(false);

		String className = (String)serviceContext.getAttribute("className");
		long classPK = ParamUtil.getLong(serviceContext, "classPK");

		subscriptionSender.addPersistedSubscribers(className, classPK);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void notifySubscribers(
			long userId, MBMessage message, String messageURL,
			ServiceContext serviceContext)
		throws PortalException {

		if (!message.isApproved() || Validator.isNull(messageURL)) {
			return;
		}

		if (message.isDiscussion()) {
			try {
				notifyDiscussionSubscribers(userId, message, serviceContext);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			return;
		}

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(message.getGroupId());

		if (serviceContext.isCommandAdd() &&
			mbGroupServiceSettings.isEmailMessageAddedEnabled()) {
		}
		else if (serviceContext.isCommandUpdate() &&
				 mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) {
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
			fullName = serviceContext.translate("anonymous");
		}

		MBCategory category = message.getCategory();

		String categoryName = category.getName();

		if (category.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			categoryName = serviceContext.translate("message-boards-home");

			categoryName += " - " + group.getDescriptiveName();
		}

		List<Long> categoryIds = new ArrayList<>();

		categoryIds.add(message.getCategoryId());

		if (message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			categoryIds.addAll(category.getAncestorCategoryIds());
		}

		String entryTitle = message.getSubject();

		String fromName = mbGroupServiceSettings.getEmailFromName();
		String fromAddress = mbGroupServiceSettings.getEmailFromAddress();

		String replyToAddress = StringPool.BLANK;

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			replyToAddress = MBUtil.getReplyToAddress(
				message.getCategoryId(), message.getMessageId(),
				company.getMx(), fromAddress);
		}

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageUpdatedSubject();
			bodyLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageAddedSubject();
			bodyLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageAddedBody();
		}

		boolean htmlFormat = mbGroupServiceSettings.isEmailHtmlFormat();

		String messageBody = message.getBody();

		if (htmlFormat && message.isFormatBBCode()) {
			try {
				messageBody = BBCodeTranslatorUtil.getHTML(messageBody);

				HttpServletRequest request = serviceContext.getRequest();

				if (request != null) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					messageBody = MBUtil.replaceMessageBodyPaths(
						themeDisplay, messageBody);
				}
			}
			catch (Exception e) {
				_log.error(
					"Could not parse message " + message.getMessageId() +
						" " + e.getMessage());
			}
		}

		String inReplyTo = null;

		if (message.getParentMessageId() !=
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			MBMessage parentMessage = mbMessageLocalService.getMessage(
				message.getParentMessageId());

			Date modifiedDate = parentMessage.getModifiedDate();

			inReplyTo = PortalUtil.getMailId(
				company.getMx(), MBUtil.MESSAGE_POP_PORTLET_PREFIX,
				message.getCategoryId(), parentMessage.getMessageId(),
				modifiedDate.getTime());
		}

		SubscriptionSender subscriptionSenderPrototype =
			new MBSubscriptionSender(MBPermission.RESOURCE_NAME);

		subscriptionSenderPrototype.setBulk(
			PropsValues.MESSAGE_BOARDS_EMAIL_BULK);
		subscriptionSenderPrototype.setClassName(message.getModelClassName());
		subscriptionSenderPrototype.setClassPK(message.getMessageId());
		subscriptionSenderPrototype.setCompanyId(message.getCompanyId());
		subscriptionSenderPrototype.setContextAttribute(
			"[$MESSAGE_BODY$]", messageBody, false);
		subscriptionSenderPrototype.setContextAttributes(
			"[$CATEGORY_NAME$]", categoryName, "[$MAILING_LIST_ADDRESS$]",
			replyToAddress, "[$MESSAGE_ID$]", message.getMessageId(),
			"[$MESSAGE_SUBJECT$]", entryTitle, "[$MESSAGE_URL$]", messageURL,
			"[$MESSAGE_USER_ADDRESS$]", emailAddress, "[$MESSAGE_USER_NAME$]",
			fullName);
		subscriptionSenderPrototype.setCurrentUserId(userId);
		subscriptionSenderPrototype.setEntryTitle(entryTitle);
		subscriptionSenderPrototype.setEntryURL(messageURL);
		subscriptionSenderPrototype.setFrom(fromAddress, fromName);
		subscriptionSenderPrototype.setHtmlFormat(htmlFormat);
		subscriptionSenderPrototype.setInReplyTo(inReplyTo);

		if (bodyLocalizedValuesMap != null) {
			subscriptionSenderPrototype.setLocalizedBodyMap(
				LocalizationUtil.getMap(bodyLocalizedValuesMap));
		}

		if (subjectLocalizedValuesMap != null) {
			subscriptionSenderPrototype.setLocalizedSubjectMap(
				LocalizationUtil.getMap(subjectLocalizedValuesMap));
		}

		Date modifiedDate = message.getModifiedDate();

		subscriptionSenderPrototype.setMailId(
			MBUtil.MESSAGE_POP_PORTLET_PREFIX, message.getCategoryId(),
			message.getMessageId(), modifiedDate.getTime());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSenderPrototype.setNotificationType(notificationType);

		subscriptionSenderPrototype.setPortletId(PortletKeys.MESSAGE_BOARDS);
		subscriptionSenderPrototype.setReplyToAddress(replyToAddress);
		subscriptionSenderPrototype.setScopeGroupId(message.getGroupId());
		subscriptionSenderPrototype.setServiceContext(serviceContext);
		subscriptionSenderPrototype.setUniqueMailId(false);

		SubscriptionSender subscriptionSender =
			(SubscriptionSender)SerializableUtil.clone(
				subscriptionSenderPrototype);

		subscriptionSender.addPersistedSubscribers(
			MBCategory.class.getName(), message.getGroupId());

		for (long categoryId : categoryIds) {
			if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				subscriptionSender.addPersistedSubscribers(
					MBCategory.class.getName(), categoryId);
			}
		}

		subscriptionSender.addPersistedSubscribers(
			MBThread.class.getName(), message.getThreadId());

		subscriptionSender.flushNotificationsAsync();

		if (!MailingListThreadLocal.isSourceMailingList()) {
			for (long categoryId : categoryIds) {
				MBSubscriptionSender sourceMailingListSubscriptionSender =
					(MBSubscriptionSender)SerializableUtil.clone(
						subscriptionSenderPrototype);

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

	protected void startWorkflowInstance(
			long userId, MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(
			WorkflowConstants.CONTEXT_URL,
			getMessageURL(message, serviceContext));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			message.getCompanyId(), message.getGroupId(), userId,
			message.getWorkflowClassName(), message.getMessageId(), message,
			serviceContext, workflowContext);
	}

	protected void updateAsset(
			long userId, MBMessage message, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds,
			boolean assetEntryVisible)
		throws PortalException {

		boolean visible = false;

		if (assetEntryVisible && message.isApproved() &&
			((message.getClassNameId() == 0) ||
			 (message.getParentMessageId() != 0))) {

			visible = true;
		}

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, message.getGroupId(), message.getCreateDate(),
			message.getModifiedDate(), message.getWorkflowClassName(),
			message.getMessageId(), message.getUuid(), 0, assetCategoryIds,
			assetTagNames, visible, null, null, null, ContentTypes.TEXT_HTML,
			message.getSubject(), null, null, null, null, 0, 0, null);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	protected void updatePriorities(long threadId, double priority) {
		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			if (message.getPriority() != priority) {
				message.setPriority(priority);

				mbMessagePersistence.update(message);
			}
		}
	}

	protected void updateThreadStatus(
			MBThread thread, MBMessage message, User user, int oldStatus,
			Date modifiedDate)
		throws PortalException {

		MBCategory category = null;

		int status = message.getStatus();

		if ((thread.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(thread.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = mbCategoryPersistence.findByPrimaryKey(
				thread.getCategoryId());
		}

		if ((thread.getRootMessageId() == message.getMessageId()) &&
			(oldStatus != status)) {

			thread.setModifiedDate(modifiedDate);
			thread.setStatus(status);
			thread.setStatusByUserId(user.getUserId());
			thread.setStatusByUserName(user.getFullName());
			thread.setStatusDate(modifiedDate);
		}

		if (status == oldStatus) {
			return;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (message.isAnonymous()) {
				thread.setLastPostByUserId(0);
			}
			else {
				thread.setLastPostByUserId(message.getUserId());
			}

			thread.setLastPostDate(modifiedDate);

			if (category != null) {
				category.setLastPostDate(modifiedDate);

				category = mbCategoryPersistence.update(category);
			}
		}

		if ((oldStatus == WorkflowConstants.STATUS_APPROVED) ||
			(status == WorkflowConstants.STATUS_APPROVED)) {

			// Thread

			MBUtil.updateThreadMessageCount(
				thread.getCompanyId(), thread.getThreadId());

			// Category

			if ((category != null) &&
				(thread.getRootMessageId() == message.getMessageId())) {

				MBUtil.updateCategoryStatistics(
					category.getCompanyId(), category.getCategoryId());
			}

			if ((category != null) &&
				!(thread.getRootMessageId() == message.getMessageId())) {

				MBUtil.updateCategoryMessageCount(
					category.getCompanyId(), category.getCategoryId());
			}
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBThread.class);

		indexer.reindex(thread);

		mbThreadPersistence.update(thread);
	}

	protected void validate(String subject, String body)
		throws PortalException {

		if (Validator.isNull(subject) && Validator.isNull(body)) {
			throw new MessageSubjectException();
		}
	}

	protected void validateDiscussionMaxComments(String className, long classPK)
		throws PortalException {

		if (PropsValues.DISCUSSION_MAX_COMMENTS <= 0) {
			return;
		}

		int count = getDiscussionMessagesCount(
			className, classPK, WorkflowConstants.STATUS_APPROVED);

		if (count >= PropsValues.DISCUSSION_MAX_COMMENTS) {
			throw new DiscussionMaxCommentsException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageLocalServiceImpl.class);

}