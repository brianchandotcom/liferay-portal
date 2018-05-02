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
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBThreadLocalServiceWrapper
	extends MBThreadLocalServiceWrapper {

	public ModularMBThreadLocalServiceWrapper() {
		super(null);
	}

	public ModularMBThreadLocalServiceWrapper(
		MBThreadLocalService mbThreadLocalService) {

		super(mbThreadLocalService);
	}

	@Override
	public MBThread addMBThread(MBThread mbThread) {
		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.addMBThread(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class,
					mbThread)));
	}

	@Override
	public MBThread addThread(
			long categoryId, MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.addThread(
				categoryId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMessage.class, message),
				serviceContext));
	}

	@Override
	public MBThread createMBThread(long threadId) {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.createMBThread(threadId));
	}

	@Override
	public MBThread deleteMBThread(long threadId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.deleteMBThread(threadId));
	}

	@Override
	public MBThread deleteMBThread(MBThread mbThread) {
		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.deleteMBThread(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class,
					mbThread)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class,
					persistedModel)));
	}

	@Override
	public void deleteThread(long threadId) throws PortalException {
		_mbThreadLocalService.deleteThread(threadId);
	}

	@Override
	public void deleteThread(MBThread thread) throws PortalException {
		_mbThreadLocalService.deleteThread(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBThread.class, thread));
	}

	@Override
	public void deleteThreads(long groupId, long categoryId)
		throws PortalException {

		_mbThreadLocalService.deleteThreads(groupId, categoryId);
	}

	@Override
	public void deleteThreads(
			long groupId, long categoryId, boolean includeTrashedEntries)
		throws PortalException {

		_mbThreadLocalService.deleteThreads(
			groupId, categoryId, includeTrashedEntries);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbThreadLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbThreadLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbThreadLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbThreadLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbThreadLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBThread fetchMBThread(long threadId) {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.fetchMBThread(threadId));
	}

	@Override
	public MBThread fetchMBThreadByUuidAndGroupId(String uuid, long groupId) {
		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.fetchMBThreadByUuidAndGroupId(uuid, groupId));
	}

	@Override
	public MBThread fetchThread(long threadId) {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.fetchThread(threadId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbThreadLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getCategoryThreadsCount(
		long groupId, long categoryId, int status) {

		return _mbThreadLocalService.getCategoryThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbThreadLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getGroupThreads(
				groupId, userId, subscribed, includeAnonymous,
				ModelAdapterUtil.adapt(MBThread.class, queryDefinition)));
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		QueryDefinition<MBThread> queryDefinition) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getGroupThreads(
				groupId, userId, subscribed,
				ModelAdapterUtil.adapt(MBThread.class, queryDefinition)));
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getGroupThreads(
				groupId, userId,
				ModelAdapterUtil.adapt(MBThread.class, queryDefinition)));
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, QueryDefinition<MBThread> queryDefinition) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getGroupThreads(
				groupId,
				ModelAdapterUtil.adapt(MBThread.class, queryDefinition)));
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId, subscribed, includeAnonymous,
			ModelAdapterUtil.adapt(MBThread.class, queryDefinition));
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed,
		QueryDefinition<MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId, subscribed,
			ModelAdapterUtil.adapt(MBThread.class, queryDefinition));
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId,
			ModelAdapterUtil.adapt(MBThread.class, queryDefinition));
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, QueryDefinition<MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, ModelAdapterUtil.adapt(MBThread.class, queryDefinition));
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbThreadLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBThread getMBThread(long threadId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.getMBThread(threadId));
	}

	@Override
	public MBThread getMBThreadByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getMBThreadByUuidAndGroupId(uuid, groupId));
	}

	@Override
	public List<MBThread> getMBThreads(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.getMBThreads(start, end));
	}

	@Override
	public List<MBThread> getMBThreadsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getMBThreadsByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBThread> getMBThreadsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getMBThreadsByUuidAndCompanyId(
				uuid, companyId, start, end,
				ModelAdapterUtil.adapt(MBThread.class, orderByComparator)));
	}

	@Override
	public int getMBThreadsCount() {
		return _mbThreadLocalService.getMBThreadsCount();
	}

	@Override
	public List<MBThread> getNoAssetThreads() {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.getNoAssetThreads());
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public List<MBThread> getPriorityThreads(long categoryId, double priority)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getPriorityThreads(categoryId, priority));
	}

	@Override
	public List<MBThread> getPriorityThreads(
			long categoryId, double priority, boolean inherit)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getPriorityThreads(
				categoryId, priority, inherit));
	}

	@Override
	public MBThread getThread(long threadId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.getThread(threadId));
	}

	@Override
	public List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.getThreads(
				groupId, categoryId, status, start, end));
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		return _mbThreadLocalService.getThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public MBThreadLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public boolean hasAnswerMessage(long threadId) {
		return _mbThreadLocalService.hasAnswerMessage(threadId);
	}

	@Override
	public void incrementViewCounter(long threadId, int increment)
		throws PortalException {

		_mbThreadLocalService.incrementViewCounter(threadId, increment);
	}

	@Override
	public void moveDependentsToTrash(
			long groupId, long threadId, long trashEntryId)
		throws PortalException {

		_mbThreadLocalService.moveDependentsToTrash(
			groupId, threadId, trashEntryId);
	}

	@Override
	public MBThread moveThread(long groupId, long categoryId, long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.moveThread(groupId, categoryId, threadId));
	}

	@Override
	public MBThread moveThreadFromTrash(
			long userId, long categoryId, long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.moveThreadFromTrash(
				userId, categoryId, threadId));
	}

	@Override
	public void moveThreadsToTrash(long groupId, long userId)
		throws PortalException {

		_mbThreadLocalService.moveThreadsToTrash(groupId, userId);
	}

	@Override
	public MBThread moveThreadToTrash(long userId, long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.moveThreadToTrash(userId, threadId));
	}

	@Override
	public MBThread moveThreadToTrash(long userId, MBThread thread)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.moveThreadToTrash(
				userId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class, thread)));
	}

	@Override
	public void restoreDependentsFromTrash(long groupId, long threadId)
		throws PortalException {

		_mbThreadLocalService.restoreDependentsFromTrash(groupId, threadId);
	}

	@Override
	public void restoreDependentsFromTrash(
			long groupId, long threadId, long trashEntryId)
		throws PortalException {

		super.restoreDependentsFromTrash(groupId, threadId, trashEntryId);
	}

	@Override
	public void restoreThreadFromTrash(long userId, long threadId)
		throws PortalException {

		_mbThreadLocalService.restoreThreadFromTrash(userId, threadId);
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		return _mbThreadLocalService.search(
			groupId, userId, creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, long startDate,
			long endDate, int status, int start, int end)
		throws PortalException {

		return _mbThreadLocalService.search(
			groupId, userId, creatorUserId, startDate, endDate, status, start,
			end);
	}

	@Override
	public void setWrappedService(MBThreadLocalService mbThreadLocalService) {
		super.setWrappedService(mbThreadLocalService);
	}

	@Override
	public MBThread splitThread(
			long userId, long messageId, String subject,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.splitThread(
				userId, messageId, subject, serviceContext));
	}

	@Override
	public MBThread updateMBThread(MBThread mbThread) {
		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.updateMBThread(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class,
					mbThread)));
	}

	@Override
	public MBThread updateMessageCount(long threadId) {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadLocalService.updateMessageCount(threadId));
	}

	@Override
	public void updateQuestion(long threadId, boolean question)
		throws PortalException {

		_mbThreadLocalService.updateQuestion(threadId, question);
	}

	@Override
	public MBThread updateStatus(long userId, long threadId, int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadLocalService.updateStatus(userId, threadId, status));
	}

	@Reference
	private com.liferay.message.boards.service.MBThreadLocalService
		_mbThreadLocalService;

}