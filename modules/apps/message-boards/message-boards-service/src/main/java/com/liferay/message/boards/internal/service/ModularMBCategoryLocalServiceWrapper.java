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
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.service.MBCategoryLocalService;
import com.liferay.message.boards.kernel.service.MBCategoryLocalServiceWrapper;
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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBCategoryLocalServiceWrapper
	extends MBCategoryLocalServiceWrapper {

	public ModularMBCategoryLocalServiceWrapper() {
		super(null);
	}

	public ModularMBCategoryLocalServiceWrapper(
		MBCategoryLocalService mbCategoryLocalService) {

		super(mbCategoryLocalService);
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.addCategory(
				userId, parentCategoryId, name, description, serviceContext));
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous,
			boolean mailingListActive, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.addCategory(
				userId, parentCategoryId, name, description, displayStyle,
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive,
				serviceContext));
	}

	@Override
	public void addCategoryResources(
			long categoryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_mbCategoryLocalService.addCategoryResources(
			categoryId, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			long categoryId, ModelPermissions modelPermissions)
		throws PortalException {

		_mbCategoryLocalService.addCategoryResources(
			categoryId, modelPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_mbCategoryLocalService.addCategoryResources(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBCategory.class, category),
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, ModelPermissions modelPermissions)
		throws PortalException {

		_mbCategoryLocalService.addCategoryResources(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBCategory.class, category),
			modelPermissions);
	}

	@Override
	public MBCategory addMBCategory(MBCategory mbCategory) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.addMBCategory(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBCategory.class,
					mbCategory)));
	}

	@Override
	public MBCategory createMBCategory(long categoryId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.createMBCategory(categoryId));
	}

	@Override
	public void deleteCategories(long groupId) throws PortalException {
		_mbCategoryLocalService.deleteCategories(groupId);
	}

	@Override
	public void deleteCategory(long categoryId) throws PortalException {
		_mbCategoryLocalService.deleteCategory(categoryId);
	}

	@Override
	public void deleteCategory(MBCategory category) throws PortalException {
		_mbCategoryLocalService.deleteCategory(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBCategory.class, category));
	}

	@Override
	public void deleteCategory(
			MBCategory category, boolean includeTrashedEntries)
		throws PortalException {

		_mbCategoryLocalService.deleteCategory(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBCategory.class, category),
			includeTrashedEntries);
	}

	@Override
	public MBCategory deleteMBCategory(long categoryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.deleteMBCategory(categoryId));
	}

	@Override
	public MBCategory deleteMBCategory(MBCategory mbCategory) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.deleteMBCategory(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBCategory.class,
					mbCategory)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBCategory.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbCategoryLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbCategoryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbCategoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbCategoryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBCategory fetchMBCategory(long categoryId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.fetchMBCategory(categoryId));
	}

	@Override
	public MBCategory fetchMBCategoryByUuidAndGroupId(
		String uuid, long groupId) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.fetchMBCategoryByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbCategoryLocalService.getActionableDynamicQuery();
	}

	@Override
	public List<MBCategory> getCategories(long groupId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class, _mbCategoryLocalService.getCategories(groupId));
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(groupId, status));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, parentCategoryId, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, parentCategoryId, status, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, excludedCategoryId, parentCategoryId, status, start,
				end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, parentCategoryIds, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start,
		int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, parentCategoryIds, status, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCategories(
				groupId, excludedCategoryIds, parentCategoryIds, status, start,
				end));
	}

	@Override
	public List<Object> getCategoriesAndThreads(long groupId, long categoryId) {
		return _mbCategoryLocalService.getCategoriesAndThreads(
			groupId, categoryId);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status) {

		return _mbCategoryLocalService.getCategoriesAndThreads(
			groupId, categoryId, status);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return _mbCategoryLocalService.getCategoriesAndThreads(
			groupId, categoryId, status, start, end);
	}

	@Override
	public int getCategoriesAndThreadsCount(long groupId, long categoryId) {
		return _mbCategoryLocalService.getCategoriesAndThreadsCount(
			groupId, categoryId);
	}

	@Override
	public int getCategoriesAndThreadsCount(
		long groupId, long categoryId, int status) {

		return _mbCategoryLocalService.getCategoriesAndThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId) {
		return _mbCategoryLocalService.getCategoriesCount(groupId);
	}

	@Override
	public int getCategoriesCount(long groupId, int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return _mbCategoryLocalService.getCategoriesCount(
			groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long parentCategoryId, int status) {

		return _mbCategoryLocalService.getCategoriesCount(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status) {

		return _mbCategoryLocalService.getCategoriesCount(
			groupId, excludedCategoryId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds) {
		return _mbCategoryLocalService.getCategoriesCount(
			groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] parentCategoryIds, int status) {

		return _mbCategoryLocalService.getCategoriesCount(
			groupId, parentCategoryIds, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status) {

		return _mbCategoryLocalService.getCategoriesCount(
			groupId, excludedCategoryIds, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBCategory.class, _mbCategoryLocalService.getCategory(categoryId));
	}

	@Override
	public List<MBCategory> getCompanyCategories(
		long companyId, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getCompanyCategories(
				companyId, start, end));
	}

	@Override
	public int getCompanyCategoriesCount(long companyId) {
		return _mbCategoryLocalService.getCompanyCategoriesCount(companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _mbCategoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public List<MBCategory> getMBCategories(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getMBCategories(start, end));
	}

	@Override
	public List<MBCategory> getMBCategoriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getMBCategoriesByUuidAndCompanyId(
				uuid, companyId));
	}

	@Override
	public List<MBCategory> getMBCategoriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBCategory> orderByComparator) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getMBCategoriesByUuidAndCompanyId(
				uuid, companyId, start, end,
				ModelAdapterUtil.adapt(MBCategory.class, orderByComparator)));
	}

	@Override
	public int getMBCategoriesCount() {
		return _mbCategoryLocalService.getMBCategoriesCount();
	}

	@Override
	public MBCategory getMBCategory(long categoryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getMBCategory(categoryId));
	}

	@Override
	public MBCategory getMBCategoryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getMBCategoryByUuidAndGroupId(
				uuid, groupId));
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public List<Long> getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		return _mbCategoryLocalService.getSubcategoryIds(
			categoryIds, groupId, categoryId);
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.getSubscribedCategories(
				groupId, userId, start, end));
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		return _mbCategoryLocalService.getSubscribedCategoriesCount(
			groupId, userId);
	}

	@Override
	public MBCategoryLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void moveCategoriesToTrash(long groupId, long userId)
		throws PortalException {

		_mbCategoryLocalService.moveCategoriesToTrash(groupId, userId);
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.moveCategory(
				categoryId, parentCategoryId, mergeWithParentCategory));
	}

	@Override
	public MBCategory moveCategoryFromTrash(
			long userId, long categoryId, long newCategoryId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.moveCategoryFromTrash(
				userId, categoryId, newCategoryId));
	}

	@Override
	public MBCategory moveCategoryToTrash(long userId, long categoryId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.moveCategoryToTrash(userId, categoryId));
	}

	@Override
	public void restoreCategoryFromTrash(long userId, long categoryId)
		throws PortalException {

		_mbCategoryLocalService.restoreCategoryFromTrash(userId, categoryId);
	}

	@Override
	public void setWrappedService(
		MBCategoryLocalService mbCategoryLocalService) {

		super.setWrappedService(mbCategoryLocalService);
	}

	@Override
	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException {

		_mbCategoryLocalService.subscribeCategory(userId, groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException {

		_mbCategoryLocalService.unsubscribeCategory(
			userId, groupId, categoryId);
	}

	@Override
	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean mailingListActive, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateCategory(
				categoryId, parentCategoryId, name, description, displayStyle,
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive,
				mergeWithParentCategory, serviceContext));
	}

	@Override
	public MBCategory updateMBCategory(MBCategory mbCategory) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateMBCategory(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBCategory.class,
					mbCategory)));
	}

	@Override
	public MBCategory updateMessageCount(long categoryId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateMessageCount(categoryId));
	}

	@Override
	public MBCategory updateStatistics(long categoryId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateStatistics(categoryId));
	}

	@Override
	public MBCategory updateStatus(long userId, long categoryId, int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateStatus(userId, categoryId, status));
	}

	@Override
	public MBCategory updateThreadCount(long categoryId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryLocalService.updateThreadCount(categoryId));
	}

	@Reference
	private com.liferay.message.boards.service.MBCategoryLocalService
		_mbCategoryLocalService;

}