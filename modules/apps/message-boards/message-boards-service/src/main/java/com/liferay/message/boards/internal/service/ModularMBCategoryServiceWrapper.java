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

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.service.MBCategoryService;
import com.liferay.message.boards.kernel.service.MBCategoryServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBCategoryServiceWrapper extends MBCategoryServiceWrapper {

	public ModularMBCategoryServiceWrapper() {
		super(null);
	}

	public ModularMBCategoryServiceWrapper(
		MBCategoryService mbCategoryService) {

		super(mbCategoryService);
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.addCategory(
				userId, parentCategoryId, name, description, serviceContext));
	}

	@Override
	public MBCategory addCategory(
			long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.addCategory(
				parentCategoryId, name, description, displayStyle, emailAddress,
				inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
				inPassword, inReadInterval, outEmailAddress, outCustom,
				outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, mailingListActive, allowAnonymousEmail,
				serviceContext));
	}

	@Override
	public void deleteCategory(long categoryId, boolean includeTrashedEntries)
		throws PortalException {

		_mbCategoryService.deleteCategory(categoryId, includeTrashedEntries);
	}

	@Override
	public void deleteCategory(long groupId, long categoryId)
		throws PortalException {

		_mbCategoryService.deleteCategory(groupId, categoryId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId) {
		return ModelAdapterUtil.adapt(
			MBCategory.class, _mbCategoryService.getCategories(groupId));
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status) {
		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(groupId, status));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, parentCategoryId, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, parentCategoryId, status, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, excludedCategoryId, parentCategoryId, status, start,
				end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, parentCategoryIds, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start,
		int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, parentCategoryIds, status, start, end));
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getCategories(
				groupId, excludedCategoryIds, parentCategoryIds, status, start,
				end));
	}

	@Override
	public List<Object> getCategoriesAndThreads(long groupId, long categoryId) {
		return _mbCategoryService.getCategoriesAndThreads(groupId, categoryId);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status, start, end);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<?> obc) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status, start, end, obc);
	}

	@Override
	public List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, QueryDefinition<?> queryDefinition) {

		try {
			return _mbCategoryService.getCategoriesAndThreads(
				groupId, categoryId, queryDefinition);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public int getCategoriesAndThreadsCount(long groupId, long categoryId) {
		return _mbCategoryService.getCategoriesAndThreadsCount(
			groupId, categoryId);
	}

	@Override
	public int getCategoriesAndThreadsCount(
		long groupId, long categoryId, int status) {

		return _mbCategoryService.getCategoriesAndThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public int getCategoriesAndThreadsCount(
		long groupId, long categoryId, QueryDefinition<?> queryDefinition) {

		try {
			return _mbCategoryService.getCategoriesAndThreadsCount(
				groupId, categoryId, queryDefinition);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return _mbCategoryService.getCategoriesCount(groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long parentCategoryId, int status) {

		return _mbCategoryService.getCategoriesCount(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status) {

		return _mbCategoryService.getCategoriesCount(
			groupId, excludedCategoryId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds) {
		return _mbCategoryService.getCategoriesCount(
			groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] parentCategoryIds, int status) {

		return _mbCategoryService.getCategoriesCount(
			groupId, parentCategoryIds, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status) {

		return _mbCategoryService.getCategoriesCount(
			groupId, excludedCategoryIds, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBCategory.class, _mbCategoryService.getCategory(categoryId));
	}

	@Override
	public long[] getCategoryIds(long groupId, long categoryId) {
		return _mbCategoryService.getCategoryIds(groupId, categoryId);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public List<Long> getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		return _mbCategoryService.getSubcategoryIds(
			categoryIds, groupId, categoryId);
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.getSubscribedCategories(
				groupId, userId, start, end));
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		return _mbCategoryService.getSubscribedCategoriesCount(groupId, userId);
	}

	@Override
	public MBCategoryService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.moveCategory(
				categoryId, parentCategoryId, mergeWithParentCategory));
	}

	@Override
	public MBCategory moveCategoryFromTrash(long categoryId, long newCategoryId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.moveCategoryFromTrash(
				categoryId, newCategoryId));
	}

	@Override
	public MBCategory moveCategoryToTrash(long categoryId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.moveCategoryToTrash(categoryId));
	}

	@Override
	public void restoreCategoryFromTrash(long categoryId)
		throws PortalException {

		_mbCategoryService.restoreCategoryFromTrash(categoryId);
	}

	@Override
	public void setWrappedService(MBCategoryService mbCategoryService) {
		super.setWrappedService(mbCategoryService);
	}

	@Override
	public void subscribeCategory(long groupId, long categoryId)
		throws PortalException {

		_mbCategoryService.subscribeCategory(groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException {

		_mbCategoryService.unsubscribeCategory(groupId, categoryId);
	}

	@Override
	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBCategory.class,
			_mbCategoryService.updateCategory(
				categoryId, parentCategoryId, name, description, displayStyle,
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, mailingListActive, allowAnonymousEmail,
				mergeWithParentCategory, serviceContext));
	}

	@Reference
	private com.liferay.message.boards.service.MBCategoryService
		_mbCategoryService;

}