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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public ModularMBMessageLocalServiceWrapper() {
		super(null);
	}

	public ModularMBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {

		super(mbMessageLocalService);
	}

	@Override
	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, int workflowAction)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addDiscussionMessage(
				userId, userName, groupId, className, classPK, workflowAction));
	}

	@Override
	public MBMessage addDiscussionMessage(
			long userId, String userName, long groupId, String className,
			long classPK, long threadId, long parentMessageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addDiscussionMessage(
				userId, userName, groupId, className, classPK, threadId,
				parentMessageId, subject, body, serviceContext));
	}

	@Override
	public MBMessage addMBMessage(MBMessage mbMessage) {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addMBMessage(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class,
					mbMessage)));
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

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addMessage(
				userId, userName, groupId, categoryId, threadId,
				parentMessageId, subject, body, format, inputStreamOVPs,
				anonymous, priority, allowPingbacks, serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addMessage(
				userId, userName, groupId, categoryId, subject, body,
				serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addMessage(
				userId, userName, groupId, categoryId, subject, body, format,
				inputStreamOVPs, anonymous, priority, allowPingbacks,
				serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format, String fileName,
			File file, boolean anonymous, double priority,
			boolean allowPingbacks, ServiceContext serviceContext)
		throws FileNotFoundException, PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.addMessage(
				userId, userName, groupId, categoryId, subject, body, format,
				fileName, file, anonymous, priority, allowPingbacks,
				serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long categoryId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		return super.addMessage(
			userId, userName, categoryId, subject, body, serviceContext);
	}

	@Override
	public void addMessageAttachment(
			long userId, long messageId, String fileName, File file,
			String mimeType)
		throws PortalException {

		_mbMessageLocalService.addMessageAttachment(
			userId, messageId, fileName, file, mimeType);
	}

	@Override
	public void addMessageResources(
			long messageId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_mbMessageLocalService.addMessageResources(
			messageId, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addMessageResources(
			long messageId, ModelPermissions modelPermissions)
		throws PortalException {

		_mbMessageLocalService.addMessageResources(messageId, modelPermissions);
	}

	@Override
	public void addMessageResources(
			MBMessage message, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_mbMessageLocalService.addMessageResources(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBMessage.class, message),
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addMessageResources(
			MBMessage message, ModelPermissions modelPermissions)
		throws PortalException {

		_mbMessageLocalService.addMessageResources(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBMessage.class, message),
			modelPermissions);
	}

	@Override
	public MBMessage createMBMessage(long messageId) {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.createMBMessage(messageId));
	}

	@Override
	public MBMessage deleteDiscussionMessage(long messageId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.deleteDiscussionMessage(messageId));
	}

	@Override
	public void deleteDiscussionMessages(String className, long classPK)
		throws PortalException {

		_mbMessageLocalService.deleteDiscussionMessages(className, classPK);
	}

	@Override
	public MBMessage deleteMBMessage(long messageId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.deleteMBMessage(messageId));
	}

	@Override
	public MBMessage deleteMBMessage(MBMessage mbMessage) {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.deleteMBMessage(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class,
					mbMessage)));
	}

	@Override
	public MBMessage deleteMessage(long messageId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.deleteMessage(messageId));
	}

	@Override
	public MBMessage deleteMessage(MBMessage message) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.deleteMessage(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class,
					message)));
	}

	@Override
	public void deleteMessageAttachment(long messageId, String fileName)
		throws PortalException {

		_mbMessageLocalService.deleteMessageAttachment(messageId, fileName);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws PortalException {

		_mbMessageLocalService.deleteMessageAttachments(messageId);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbMessageLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbMessageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbMessageLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbMessageLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbMessageLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public void emptyMessageAttachments(long messageId) throws PortalException {
		_mbMessageLocalService.emptyMessageAttachments(messageId);
	}

	@Override
	public MBMessage fetchFirstMessage(long threadId, long parentMessageId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.fetchFirstMessage(
				threadId, parentMessageId));
	}

	@Override
	public MBMessage fetchMBMessage(long messageId) {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.fetchMBMessage(messageId));
	}

	@Override
	public MBMessage fetchMBMessageByUuidAndGroupId(String uuid, long groupId) {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.fetchMBMessageByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbMessageLocalService.getActionableDynamicQuery();
	}

	@Override
	public List<MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getCategoryMessages(
				groupId, categoryId, status, start, end));
	}

	@Override
	public List<MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getCategoryMessages(
				groupId, categoryId, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public int getCategoryMessagesCount(
		long groupId, long categoryId, int status) {

		return _mbMessageLocalService.getCategoryMessagesCount(
			groupId, categoryId, status);
	}

	@Override
	public List<MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getCompanyMessages(
				companyId, status, start, end));
	}

	@Override
	public List<MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getCompanyMessages(
				companyId, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public int getCompanyMessagesCount(long companyId, int status) {
		return _mbMessageLocalService.getCompanyMessagesCount(
			companyId, status);
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK, status));
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, Comparator<MBMessage> comparator)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK, status,
				ModelAdapterUtil.adapt(MBMessage.class, comparator)));
	}

	@Override
	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, long groupId, String className, long classPK,
			int status, String threadView)
		throws PortalException {

		return super.getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status, threadView);
	}

	@Override
	public int getDiscussionMessagesCount(
		long classNameId, long classPK, int status) {

		return _mbMessageLocalService.getDiscussionMessagesCount(
			classNameId, classPK, status);
	}

	@Override
	public int getDiscussionMessagesCount(
		String className, long classPK, int status) {

		return _mbMessageLocalService.getDiscussionMessagesCount(
			className, classPK, status);
	}

	@Override
	public List<MBDiscussion> getDiscussions(String className) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbMessageLocalService.getDiscussions(className));
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbMessageLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public MBMessage getFirstMessage(long threadId, long parentMessageId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getFirstMessage(threadId, parentMessageId));
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getGroupMessages(
				groupId, status, start, end));
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getGroupMessages(
				groupId, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getGroupMessages(
				groupId, userId, status, start, end));
	}

	@Override
	public List<MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getGroupMessages(
				groupId, userId, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public int getGroupMessagesCount(long groupId, int status) {
		return _mbMessageLocalService.getGroupMessagesCount(groupId, status);
	}

	@Override
	public int getGroupMessagesCount(long groupId, long userId, int status) {
		return _mbMessageLocalService.getGroupMessagesCount(
			groupId, userId, status);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbMessageLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBMessage getMBMessage(long messageId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.getMBMessage(messageId));
	}

	@Override
	public MBMessage getMBMessageByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getMBMessageByUuidAndGroupId(uuid, groupId));
	}

	@Override
	public List<MBMessage> getMBMessages(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.getMBMessages(start, end));
	}

	@Override
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getMBMessagesByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getMBMessagesByUuidAndCompanyId(
				uuid, companyId, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, orderByComparator)));
	}

	@Override
	public int getMBMessagesCount() {
		return _mbMessageLocalService.getMBMessagesCount();
	}

	@Override
	public MBMessage getMessage(long messageId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.getMessage(messageId));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, long messageId, int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageLocalService.getMessageDisplay(
				userId, messageId, status));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, long messageId, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		return super.getMessageDisplay(
			userId, messageId, status, threadView, includePrevAndNext);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageLocalService.getMessageDisplay(
				userId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class, message),
				status));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status,
			Comparator<MBMessage> comparator)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageLocalService.getMessageDisplay(
				userId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class, message),
				status, ModelAdapterUtil.adapt(MBMessage.class, comparator)));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		return super.getMessageDisplay(
			userId, message, status, threadView, includePrevAndNext);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long userId, MBMessage message, int status, String threadView,
			boolean includePrevAndNext, Comparator<MBMessage> comparator)
		throws PortalException {

		return super.getMessageDisplay(
			userId, message, status, threadView, includePrevAndNext,
			comparator);
	}

	@Override
	public List<MBMessage> getMessages(
		String className, long classPK, int status) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getMessages(className, classPK, status));
	}

	@Override
	public List<MBMessage> getNoAssetMessages() {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageLocalService.getNoAssetMessages());
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbMessageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public int getPositionInThread(long messageId) throws PortalException {
		return _mbMessageLocalService.getPositionInThread(messageId);
	}

	@Override
	public List<MBMessage> getThreadMessages(long threadId, int status) {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getThreadMessages(threadId, status));
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long threadId, int status, Comparator<MBMessage> comparator) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getThreadMessages(
				threadId, status,
				ModelAdapterUtil.adapt(MBMessage.class, comparator)));
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long threadId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getThreadMessages(
				threadId, status, start, end));
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long userId, long threadId, int status, int start, int end,
		Comparator<MBMessage> comparator) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getThreadMessages(
				userId, threadId, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, comparator)));
	}

	@Override
	public int getThreadMessagesCount(long threadId, int status) {
		return _mbMessageLocalService.getThreadMessagesCount(threadId, status);
	}

	@Override
	public List<MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getThreadRepliesMessages(
				threadId, status, start, end));
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, long classNameId, long classPK, int status, int start,
		int end, OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getUserDiscussionMessages(
				userId, classNameId, classPK, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, long[] classNameIds, int status, int start, int end,
		OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getUserDiscussionMessages(
				userId, classNameIds, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public List<MBMessage> getUserDiscussionMessages(
		long userId, String className, long classPK, int status, int start,
		int end, OrderByComparator<MBMessage> obc) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.getUserDiscussionMessages(
				userId, className, classPK, status, start, end,
				ModelAdapterUtil.adapt(MBMessage.class, obc)));
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, long classNameId, long classPK, int status) {

		return _mbMessageLocalService.getUserDiscussionMessagesCount(
			userId, classNameId, classPK, status);
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, long[] classNameIds, int status) {

		return _mbMessageLocalService.getUserDiscussionMessagesCount(
			userId, classNameIds, status);
	}

	@Override
	public int getUserDiscussionMessagesCount(
		long userId, String className, long classPK, int status) {

		return _mbMessageLocalService.getUserDiscussionMessagesCount(
			userId, className, classPK, status);
	}

	@Override
	public MBMessageLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public long moveMessageAttachmentToTrash(
			long userId, long messageId, String fileName)
		throws PortalException {

		return _mbMessageLocalService.moveMessageAttachmentToTrash(
			userId, messageId, fileName);
	}

	@Override
	public void restoreMessageAttachmentFromTrash(
			long userId, long messageId, String deletedFileName)
		throws PortalException {

		_mbMessageLocalService.restoreMessageAttachmentFromTrash(
			userId, messageId, deletedFileName);
	}

	@Override
	public void setWrappedService(MBMessageLocalService mbMessageLocalService) {
		super.setWrappedService(mbMessageLocalService);
	}

	@Override
	public void subscribeMessage(long userId, long messageId)
		throws PortalException {

		_mbMessageLocalService.subscribeMessage(userId, messageId);
	}

	@Override
	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException {

		_mbMessageLocalService.unsubscribeMessage(userId, messageId);
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException {

		_mbMessageLocalService.updateAnswer(messageId, answer, cascade);
	}

	@Override
	public void updateAnswer(MBMessage message, boolean answer, boolean cascade)
		throws PortalException {

		_mbMessageLocalService.updateAnswer(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBMessage.class, message),
			answer, cascade);
	}

	@Override
	public void updateAsset(
			long userId, MBMessage message, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException {

		_mbMessageLocalService.updateAsset(
			userId,
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBMessage.class, message),
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			long userId, long messageId, String className, long classPK,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.updateDiscussionMessage(
				userId, messageId, className, classPK, subject, body,
				serviceContext));
	}

	@Override
	public MBMessage updateMBMessage(MBMessage mbMessage) {
		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.updateMBMessage(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class,
					mbMessage)));
	}

	@Override
	public MBMessage updateMessage(
			long userId, long messageId, String body,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.updateMessage(
				userId, messageId, body, serviceContext));
	}

	@Override
	public MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.updateMessage(
				userId, messageId, subject, body, inputStreamOVPs,
				existingFiles, priority, allowPingbacks, serviceContext));
	}

	@Override
	public MBMessage updateMessage(long messageId, String body)
		throws PortalException {

		return super.updateMessage(messageId, body);
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return super.updateStatus(userId, messageId, status, serviceContext);
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageLocalService.updateStatus(
				userId, messageId, status, serviceContext, workflowContext));
	}

	@Override
	public void updateUserName(long userId, String userName) {
		_mbMessageLocalService.updateUserName(userId, userName);
	}

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private com.liferay.message.boards.service.MBMessageLocalService
		_mbMessageLocalService;

}