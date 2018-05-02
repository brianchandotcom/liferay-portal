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
import com.liferay.message.boards.kernel.model.MBMailingList;
import com.liferay.message.boards.kernel.service.MBMailingListLocalService;
import com.liferay.message.boards.kernel.service.MBMailingListLocalServiceWrapper;
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
public class ModularMBMailingListLocalServiceWrapper
	extends MBMailingListLocalServiceWrapper {

	public ModularMBMailingListLocalServiceWrapper() {
		super(null);
	}

	public ModularMBMailingListLocalServiceWrapper(
		MBMailingListLocalService mbMailingListLocalService) {

		super(mbMailingListLocalService);
	}

	@Override
	public MBMailingList addMailingList(
			long userId, long groupId, long categoryId, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.addMailingList(
				userId, groupId, categoryId, emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				allowAnonymous, active, serviceContext));
	}

	@Override
	public MBMailingList addMBMailingList(MBMailingList mbMailingList) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.addMBMailingList(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMailingList.class,
					mbMailingList)));
	}

	@Override
	public MBMailingList createMBMailingList(long mailingListId) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.createMBMailingList(mailingListId));
	}

	@Override
	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws PortalException {

		_mbMailingListLocalService.deleteCategoryMailingList(
			groupId, categoryId);
	}

	@Override
	public void deleteMailingList(long mailingListId) throws PortalException {
		_mbMailingListLocalService.deleteMailingList(mailingListId);
	}

	@Override
	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException {

		_mbMailingListLocalService.deleteMailingList(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBMailingList.class,
				mailingList));
	}

	@Override
	public MBMailingList deleteMBMailingList(long mailingListId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.deleteMBMailingList(mailingListId));
	}

	@Override
	public MBMailingList deleteMBMailingList(MBMailingList mbMailingList) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.deleteMBMailingList(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMailingList.class,
					mbMailingList)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMailingList.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbMailingListLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbMailingListLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbMailingListLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbMailingListLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbMailingListLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbMailingListLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBMailingList fetchCategoryMailingList(
		long groupId, long categoryId) {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.fetchCategoryMailingList(
				groupId, categoryId));
	}

	@Override
	public MBMailingList fetchMBMailingList(long mailingListId) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.fetchMBMailingList(mailingListId));
	}

	@Override
	public MBMailingList fetchMBMailingListByUuidAndGroupId(
		String uuid, long groupId) {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.fetchMBMailingListByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbMailingListLocalService.getActionableDynamicQuery();
	}

	@Override
	public MBMailingList getCategoryMailingList(long groupId, long categoryId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getCategoryMailingList(
				groupId, categoryId));
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbMailingListLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbMailingListLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public MBMailingList getMBMailingList(long mailingListId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getMBMailingList(mailingListId));
	}

	@Override
	public MBMailingList getMBMailingListByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getMBMailingListByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public List<MBMailingList> getMBMailingLists(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getMBMailingLists(start, end));
	}

	@Override
	public List<MBMailingList> getMBMailingListsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getMBMailingListsByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBMailingList> getMBMailingListsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBMailingList> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getMBMailingListsByUuidAndCompanyId(
				uuid, companyId, start, end,
				(OrderByComparator)orderByComparator));
	}

	@Override
	public int getMBMailingListsCount() {
		return _mbMailingListLocalService.getMBMailingListsCount();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public MBMailingList updateMailingList(
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.updateMailingList(
				mailingListId, emailAddress, inProtocol, inServerName,
				inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
				outEmailAddress, outCustom, outServerName, outServerPort,
				outUseSSL, outUserName, outPassword, allowAnonymous, active,
				serviceContext));
	}

	@Override
	public MBMailingList updateMBMailingList(MBMailingList mbMailingList) {
		return ModelAdapterUtil.adapt(
			MBMailingList.class,
			_mbMailingListLocalService.updateMBMailingList(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBMailingList.class,
					mbMailingList)));
	}

	@Reference
	private com.liferay.message.boards.service.MBMailingListLocalService
		_mbMailingListLocalService;

}