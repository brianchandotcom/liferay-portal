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
import com.liferay.message.boards.kernel.service.MBDiscussionLocalService;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalServiceWrapper;
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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBDiscussionLocalServiceWrapper
	extends MBDiscussionLocalServiceWrapper {

	public ModularMBDiscussionLocalServiceWrapper() {
		super(null);
	}

	public ModularMBDiscussionLocalServiceWrapper(
		MBDiscussionLocalService mbDiscussionLocalService) {

		super(mbDiscussionLocalService);
	}

	@Override
	public MBDiscussion addDiscussion(
			long userId, long groupId, long classNameId, long classPK,
			long threadId, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.addDiscussion(
				userId, groupId, classNameId, classPK, threadId,
				serviceContext));
	}

	@Override
	public MBDiscussion addDiscussion(
			long userId, long classNameId, long classPK, long threadId,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.addDiscussion(
				userId, serviceContext.getScopeGroupId(), classNameId, classPK,
				threadId, serviceContext));
	}

	@Override
	public MBDiscussion addMBDiscussion(MBDiscussion mbDiscussion) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.addMBDiscussion(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBDiscussion.class,
					mbDiscussion)));
	}

	@Override
	public MBDiscussion createMBDiscussion(long discussionId) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.createMBDiscussion(discussionId));
	}

	@Override
	public MBDiscussion deleteMBDiscussion(long discussionId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.deleteMBDiscussion(discussionId));
	}

	@Override
	public MBDiscussion deleteMBDiscussion(MBDiscussion mbDiscussion) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.deleteMBDiscussion(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBDiscussion.class,
					mbDiscussion)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBDiscussion.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbDiscussionLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbDiscussionLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbDiscussionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbDiscussionLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbDiscussionLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbDiscussionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBDiscussion fetchDiscussion(long discussionId) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.fetchDiscussion(discussionId));
	}

	@Override
	public MBDiscussion fetchDiscussion(String className, long classPK) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.fetchDiscussion(className, classPK));
	}

	@Override
	public MBDiscussion fetchMBDiscussion(long discussionId) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.fetchMBDiscussion(discussionId));
	}

	@Override
	public MBDiscussion fetchMBDiscussionByUuidAndGroupId(
		String uuid, long groupId) {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.fetchMBDiscussionByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public MBDiscussion fetchThreadDiscussion(long threadId) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.fetchThreadDiscussion(threadId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbDiscussionLocalService.getActionableDynamicQuery();
	}

	@Override
	public MBDiscussion getDiscussion(long discussionId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getDiscussion(discussionId));
	}

	@Override
	public MBDiscussion getDiscussion(String className, long classPK)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getDiscussion(className, classPK));
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbDiscussionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbDiscussionLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBDiscussion getMBDiscussion(long discussionId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getMBDiscussion(discussionId));
	}

	@Override
	public MBDiscussion getMBDiscussionByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getMBDiscussionByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public List<MBDiscussion> getMBDiscussions(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getMBDiscussions(start, end));
	}

	@Override
	public List<MBDiscussion> getMBDiscussionsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getMBDiscussionsByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBDiscussion> getMBDiscussionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getMBDiscussionsByUuidAndCompanyId(
				uuid, companyId, start, end,
				ModelAdapterUtil.adapt(MBDiscussion.class, orderByComparator)));
	}

	@Override
	public int getMBDiscussionsCount() {
		return _mbDiscussionLocalService.getMBDiscussionsCount();
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbDiscussionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.getThreadDiscussion(threadId));
	}

	@Override
	public MBDiscussionLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void setWrappedService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		super.setWrappedService(mbDiscussionLocalService);
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
	public MBDiscussion updateMBDiscussion(MBDiscussion mbDiscussion) {
		return ModelAdapterUtil.adapt(
			MBDiscussion.class,
			_mbDiscussionLocalService.updateMBDiscussion(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBDiscussion.class,
					mbDiscussion)));
	}

	@Reference
	private com.liferay.message.boards.service.MBDiscussionLocalService
		_mbDiscussionLocalService;

}