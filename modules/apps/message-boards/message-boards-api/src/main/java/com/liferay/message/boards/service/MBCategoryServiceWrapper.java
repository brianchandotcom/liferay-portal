/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBCategoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryService
 * @generated
 */
public class MBCategoryServiceWrapper
	implements MBCategoryService, ServiceWrapper<MBCategoryService> {

	public MBCategoryServiceWrapper() {
		this(null);
	}

	public MBCategoryServiceWrapper(MBCategoryService mbCategoryService) {
		_mbCategoryService = mbCategoryService;
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.addCategory(
			userId, parentCategoryId, name, description, serviceContext);
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
			boolean allowAnonymousEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.addCategory(
			parentCategoryId, name, description, displayStyle, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, serviceContext);
	}

	@Override
	public void deleteCategory(long categoryId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbCategoryService.deleteCategory(categoryId, includeTrashedEntries);
	}

	@Override
	public void deleteCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbCategoryService.deleteCategory(groupId, categoryId);
	}

	@Override
	public MBCategory fetchMBCategory(long groupId, String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.fetchMBCategory(groupId, friendlyURL);
	}

	@Override
	public java.util.List<MBCategory> getCategories(long groupId) {
		return _mbCategoryService.getCategories(groupId);
	}

	@Override
	public java.util.List<MBCategory> getCategories(long groupId, int status) {
		return _mbCategoryService.getCategories(groupId, status);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return _mbCategoryService.getCategories(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {

		return _mbCategoryService.getCategories(
			groupId, parentCategoryId, status, start, end);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {

		return _mbCategoryService.getCategories(
			groupId, excludedCategoryId, parentCategoryId, status, start, end);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBCategory>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getCategories(
			groupId, parentCategoryId, queryDefinition);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {

		return _mbCategoryService.getCategories(
			groupId, parentCategoryIds, start, end);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start,
		int end) {

		return _mbCategoryService.getCategories(
			groupId, parentCategoryIds, status, start, end);
	}

	@Override
	public java.util.List<MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {

		return _mbCategoryService.getCategories(
			groupId, excludedCategoryIds, parentCategoryIds, status, start,
			end);
	}

	@Override
	public java.util.List<Object> getCategoriesAndThreads(
		long groupId, long categoryId) {

		return _mbCategoryService.getCategoriesAndThreads(groupId, categoryId);
	}

	@Override
	public java.util.List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status);
	}

	@Override
	public java.util.List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status, start, end);
	}

	@Override
	public java.util.List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<Object> getCategoriesAndThreads(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getCategoriesAndThreads(
			groupId, categoryId, queryDefinition);
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
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getCategoriesAndThreadsCount(
			groupId, categoryId, queryDefinition);
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
	public int getCategoriesCount(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getCategoriesCount(
			groupId, parentCategoryId, queryDefinition);
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
	public MBCategory getCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getCategory(categoryId);
	}

	@Override
	public long[] getCategoryIds(long groupId, long categoryId) {
		return _mbCategoryService.getCategoryIds(groupId, categoryId);
	}

	@Override
	public MBCategory getMBCategory(long groupId, String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.getMBCategory(groupId, friendlyURL);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<Long> getSubcategoryIds(
		java.util.List<Long> categoryIds, long groupId, long categoryId) {

		return _mbCategoryService.getSubcategoryIds(
			categoryIds, groupId, categoryId);
	}

	@Override
	public java.util.List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		return _mbCategoryService.getSubscribedCategories(
			groupId, userId, start, end);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		return _mbCategoryService.getSubscribedCategoriesCount(groupId, userId);
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.moveCategory(
			categoryId, parentCategoryId, mergeWithParentCategory);
	}

	@Override
	public MBCategory moveCategoryFromTrash(long categoryId, long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.moveCategoryFromTrash(
			categoryId, newCategoryId);
	}

	@Override
	public MBCategory moveCategoryToTrash(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.moveCategoryToTrash(categoryId);
	}

	@Override
	public void restoreCategoryFromTrash(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbCategoryService.restoreCategoryFromTrash(categoryId);
	}

	@Override
	public void subscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbCategoryService.subscribeCategory(groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

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
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbCategoryService.updateCategory(
			categoryId, parentCategoryId, name, description, displayStyle,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, mergeWithParentCategory,
			serviceContext);
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