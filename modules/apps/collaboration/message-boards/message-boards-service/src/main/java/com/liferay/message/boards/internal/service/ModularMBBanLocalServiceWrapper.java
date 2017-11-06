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
import com.liferay.message.boards.kernel.model.MBBan;
import com.liferay.message.boards.kernel.service.MBBanLocalService;
import com.liferay.message.boards.kernel.service.MBBanLocalServiceWrapper;
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
public class ModularMBBanLocalServiceWrapper extends MBBanLocalServiceWrapper {

	public ModularMBBanLocalServiceWrapper() {
		super(null);
	}

	public ModularMBBanLocalServiceWrapper(
		MBBanLocalService mbBanLocalService) {

		super(mbBanLocalService);
	}

	@Override
	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.addBan(userId, banUserId, serviceContext));
	}

	@Override
	public MBBan addMBBan(MBBan mbBan) {
		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.addMBBan(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBBan.class, mbBan)));
	}

	@Override
	public void checkBan(long groupId, long banUserId) throws PortalException {
		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	@Override
	public MBBan createMBBan(long banId) {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.createMBBan(banId));
	}

	@Override
	public void deleteBan(long banId) throws PortalException {
		_mbBanLocalService.deleteBan(banId);
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext) {
		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	@Override
	public void deleteBan(MBBan ban) {
		_mbBanLocalService.deleteBan(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBBan.class, ban));
	}

	@Override
	public void deleteBansByBanUserId(long banUserId) {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	@Override
	public void deleteBansByGroupId(long groupId) {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	@Override
	public MBBan deleteMBBan(long banId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.deleteMBBan(banId));
	}

	@Override
	public MBBan deleteMBBan(MBBan mbBan) {
		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.deleteMBBan(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBBan.class, mbBan)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBBan.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbBanLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbBanLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbBanLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbBanLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbBanLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public void expireBans() {
		_mbBanLocalService.expireBans();
	}

	@Override
	public MBBan fetchMBBan(long banId) {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.fetchMBBan(banId));
	}

	@Override
	public MBBan fetchMBBanByUuidAndGroupId(String uuid, long groupId) {
		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.fetchMBBanByUuidAndGroupId(uuid, groupId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbBanLocalService.getActionableDynamicQuery();
	}

	@Override
	public List<MBBan> getBans(long groupId, int start, int end) {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.getBans(groupId, start, end));
	}

	@Override
	public int getBansCount(long groupId) {
		return _mbBanLocalService.getBansCount(groupId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbBanLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbBanLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBBan getMBBan(long banId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.getMBBan(banId));
	}

	@Override
	public MBBan getMBBanByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.getMBBanByUuidAndGroupId(uuid, groupId));
	}

	@Override
	public List<MBBan> getMBBans(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.getMBBans(start, end));
	}

	@Override
	public List<MBBan> getMBBansByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.getMBBansByUuidAndCompanyId(uuid, companyId));
	}

	@Override
	public List<MBBan> getMBBansByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBBan> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.getMBBansByUuidAndCompanyId(
				uuid, companyId, start, end,
				ModelAdapterUtil.adapt(MBBan.class, orderByComparator)));
	}

	@Override
	public int getMBBansCount() {
		return _mbBanLocalService.getMBBansCount();
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbBanLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public MBBanLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	@Override
	public void setWrappedService(MBBanLocalService mbBanLocalService) {
		super.setWrappedService(mbBanLocalService);
	}

	@Override
	public MBBan updateMBBan(MBBan mbBan) {
		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.updateMBBan(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBBan.class, mbBan)));
	}

	@Reference
	private com.liferay.message.boards.service.MBBanLocalService
		_mbBanLocalService;

}