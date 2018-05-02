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
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.model.MBThreadFlag;
import com.liferay.message.boards.kernel.service.MBThreadFlagLocalService;
import com.liferay.message.boards.kernel.service.MBThreadFlagLocalServiceWrapper;
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
public class ModularMBThreadFlagLocalServiceWrapper
	extends MBThreadFlagLocalServiceWrapper {

	public ModularMBThreadFlagLocalServiceWrapper() {
		super(null);
	}

	public ModularMBThreadFlagLocalServiceWrapper(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		super(mbThreadFlagLocalService);
	}

	@Override
	public MBThreadFlag addMBThreadFlag(MBThreadFlag mbThreadFlag) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.addMBThreadFlag(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThreadFlag.class,
					mbThreadFlag)));
	}

	@Override
	public MBThreadFlag addThreadFlag(
			long userId, MBThread thread, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.addThreadFlag(
				userId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class, thread),
				serviceContext));
	}

	@Override
	public MBThreadFlag createMBThreadFlag(long threadFlagId) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.createMBThreadFlag(threadFlagId));
	}

	@Override
	public MBThreadFlag deleteMBThreadFlag(long threadFlagId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.deleteMBThreadFlag(threadFlagId));
	}

	@Override
	public MBThreadFlag deleteMBThreadFlag(MBThreadFlag mbThreadFlag) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.deleteMBThreadFlag(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThreadFlag.class,
					mbThreadFlag)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThreadFlag.class,
					persistedModel)));
	}

	@Override
	public void deleteThreadFlag(long threadFlagId) throws PortalException {
		_mbThreadFlagLocalService.deleteThreadFlag(threadFlagId);
	}

	@Override
	public void deleteThreadFlag(MBThreadFlag threadFlag) {
		_mbThreadFlagLocalService.deleteThreadFlag(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBThreadFlag.class,
				threadFlag));
	}

	@Override
	public void deleteThreadFlagsByThreadId(long threadId) {
		_mbThreadFlagLocalService.deleteThreadFlagsByThreadId(threadId);
	}

	@Override
	public void deleteThreadFlagsByUserId(long userId) {
		_mbThreadFlagLocalService.deleteThreadFlagsByUserId(userId);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbThreadFlagLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbThreadFlagLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbThreadFlagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbThreadFlagLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbThreadFlagLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbThreadFlagLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBThreadFlag fetchMBThreadFlag(long threadFlagId) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.fetchMBThreadFlag(threadFlagId));
	}

	@Override
	public MBThreadFlag fetchMBThreadFlagByUuidAndGroupId(
		String uuid, long groupId) {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.fetchMBThreadFlagByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbThreadFlagLocalService.getActionableDynamicQuery();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbThreadFlagLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbThreadFlagLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBThreadFlag getMBThreadFlag(long threadFlagId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getMBThreadFlag(threadFlagId));
	}

	@Override
	public MBThreadFlag getMBThreadFlagByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getMBThreadFlagByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public List<MBThreadFlag> getMBThreadFlags(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getMBThreadFlags(start, end));
	}

	@Override
	public List<MBThreadFlag> getMBThreadFlagsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getMBThreadFlagsByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBThreadFlag> getMBThreadFlagsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getMBThreadFlagsByUuidAndCompanyId(
				uuid, companyId, start, end,
				(OrderByComparator)orderByComparator));
	}

	@Override
	public int getMBThreadFlagsCount() {
		return _mbThreadFlagLocalService.getMBThreadFlagsCount();
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadFlagLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public MBThreadFlag getThreadFlag(long userId, MBThread thread)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.getThreadFlag(
				userId,
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThread.class, thread)));
	}

	@Override
	public boolean hasThreadFlag(long userId, MBThread thread)
		throws PortalException {

		return _mbThreadFlagLocalService.hasThreadFlag(
			userId,
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBThread.class, thread));
	}

	@Override
	public MBThreadFlag updateMBThreadFlag(MBThreadFlag mbThreadFlag) {
		return ModelAdapterUtil.adapt(
			MBThreadFlag.class,
			_mbThreadFlagLocalService.updateMBThreadFlag(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBThreadFlag.class,
					mbThreadFlag)));
	}

	@Reference
	private com.liferay.message.boards.service.MBThreadFlagLocalService
		_mbThreadFlagLocalService;

}