/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBCategoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryService
 * @generated
 */
public class MBCategoryServiceWrapper implements MBCategoryService,
	ServiceWrapper<MBCategoryService> {
	public MBCategoryServiceWrapper(MBCategoryService mbCategoryService) {
		_mbCategoryService = mbCategoryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _mbCategoryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mbCategoryService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.addCategory(userId, parentCategoryId, name,
			description, serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.addCategory(parentCategoryId, name,
			description, displayStyle, emailAddress, inProtocol, inServerName,
			inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName, outServerPort,
			outUseSSL, outUserName, outPassword, mailingListActive,
			allowAnonymousEmail, serviceContext);
	}

	@Override
	public void deleteCategory(long categoryId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryService.deleteCategory(categoryId, includeTrashedEntries);
	}

	@Override
	public void deleteCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryService.deleteCategory(groupId, categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId, status);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId, parentCategoryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId, parentCategoryId,
			status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId, parentCategoryIds,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategories(groupId, parentCategoryIds,
			status, start, end);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategoriesCount(groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategoriesCount(groupId, parentCategoryId,
			status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategoriesCount(groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategoriesCount(groupId,
			parentCategoryIds, status);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategory(categoryId);
	}

	@Override
	public long[] getCategoryIds(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getCategoryIds(groupId, categoryId);
	}

	@Override
	public java.util.List<java.lang.Long> getSubcategoryIds(
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getSubscribedCategories(groupId, userId,
			start, end);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.getSubscribedCategoriesCount(groupId, userId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategory(
		long categoryId, long parentCategoryId, boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.moveCategory(categoryId, parentCategoryId,
			mergeWithParentCategory);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategoryFromTrash(
		long categoryId, long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.moveCategoryFromTrash(categoryId,
			newCategoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategoryToTrash(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.moveCategoryToTrash(categoryId);
	}

	@Override
	public void restoreCategoryFromTrash(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryService.restoreCategoryFromTrash(categoryId);
	}

	@Override
	public void subscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryService.subscribeCategory(groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryService.unsubscribeCategory(groupId, categoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryService.updateCategory(categoryId, parentCategoryId,
			name, description, displayStyle, emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, mergeWithParentCategory,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MBCategoryService getWrappedMBCategoryService() {
		return _mbCategoryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMBCategoryService(MBCategoryService mbCategoryService) {
		_mbCategoryService = mbCategoryService;
	}

	@Override
	public MBCategoryService getWrappedService() {
		return _mbCategoryService;
	}

	@Override
	public void setWrappedService(MBCategoryService mbCategoryService) {
		_mbCategoryService = mbCategoryService;
	}

	private MBCategoryService _mbCategoryService;
}