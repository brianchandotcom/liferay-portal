/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for MBCategory. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBCategoryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryService
 * @generated
 */
public class MBCategoryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBCategoryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCategory(
			userId, parentCategoryId, name, description, serviceContext);
	}

	public static MBCategory addCategory(
			long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCategory(
			parentCategoryId, name, description, displayStyle, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, serviceContext);
	}

	public static void deleteCategory(
			long categoryId, boolean includeTrashedEntries)
		throws PortalException {

		getService().deleteCategory(categoryId, includeTrashedEntries);
	}

	public static void deleteCategory(long groupId, long categoryId)
		throws PortalException {

		getService().deleteCategory(groupId, categoryId);
	}

	public static MBCategory fetchMBCategory(long groupId, String friendlyURL)
		throws PortalException {

		return getService().fetchMBCategory(groupId, friendlyURL);
	}

	public static List<MBCategory> getCategories(long groupId) {
		return getService().getCategories(groupId);
	}

	public static List<MBCategory> getCategories(long groupId, int status) {
		return getService().getCategories(groupId, status);
	}

	public static List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return getService().getCategories(
			groupId, parentCategoryId, start, end);
	}

	public static List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {

		return getService().getCategories(
			groupId, parentCategoryId, status, start, end);
	}

	public static List<MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {

		return getService().getCategories(
			groupId, excludedCategoryId, parentCategoryId, status, start, end);
	}

	public static List<MBCategory> getCategories(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBCategory>
				queryDefinition)
		throws PortalException {

		return getService().getCategories(
			groupId, parentCategoryId, queryDefinition);
	}

	public static List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {

		return getService().getCategories(
			groupId, parentCategoryIds, start, end);
	}

	public static List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start,
		int end) {

		return getService().getCategories(
			groupId, parentCategoryIds, status, start, end);
	}

	public static List<MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {

		return getService().getCategories(
			groupId, excludedCategoryIds, parentCategoryIds, status, start,
			end);
	}

	public static List<Object> getCategoriesAndThreads(
		long groupId, long categoryId) {

		return getService().getCategoriesAndThreads(groupId, categoryId);
	}

	public static List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status) {

		return getService().getCategoriesAndThreads(
			groupId, categoryId, status);
	}

	public static List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return getService().getCategoriesAndThreads(
			groupId, categoryId, status, start, end);
	}

	public static List<Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getService().getCategoriesAndThreads(
			groupId, categoryId, status, start, end, orderByComparator);
	}

	public static List<Object> getCategoriesAndThreads(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws PortalException {

		return getService().getCategoriesAndThreads(
			groupId, categoryId, queryDefinition);
	}

	public static int getCategoriesAndThreadsCount(
		long groupId, long categoryId) {

		return getService().getCategoriesAndThreadsCount(groupId, categoryId);
	}

	public static int getCategoriesAndThreadsCount(
		long groupId, long categoryId, int status) {

		return getService().getCategoriesAndThreadsCount(
			groupId, categoryId, status);
	}

	public static int getCategoriesAndThreadsCount(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws PortalException {

		return getService().getCategoriesAndThreadsCount(
			groupId, categoryId, queryDefinition);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId) {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static int getCategoriesCount(
		long groupId, long parentCategoryId, int status) {

		return getService().getCategoriesCount(
			groupId, parentCategoryId, status);
	}

	public static int getCategoriesCount(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status) {

		return getService().getCategoriesCount(
			groupId, excludedCategoryId, parentCategoryId, status);
	}

	public static int getCategoriesCount(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws PortalException {

		return getService().getCategoriesCount(
			groupId, parentCategoryId, queryDefinition);
	}

	public static int getCategoriesCount(
		long groupId, long[] parentCategoryIds) {

		return getService().getCategoriesCount(groupId, parentCategoryIds);
	}

	public static int getCategoriesCount(
		long groupId, long[] parentCategoryIds, int status) {

		return getService().getCategoriesCount(
			groupId, parentCategoryIds, status);
	}

	public static int getCategoriesCount(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status) {

		return getService().getCategoriesCount(
			groupId, excludedCategoryIds, parentCategoryIds, status);
	}

	public static MBCategory getCategory(long categoryId)
		throws PortalException {

		return getService().getCategory(categoryId);
	}

	public static long[] getCategoryIds(long groupId, long categoryId) {
		return getService().getCategoryIds(groupId, categoryId);
	}

	public static MBCategory getMBCategory(long groupId, String friendlyURL)
		throws PortalException {

		return getService().getMBCategory(groupId, friendlyURL);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<Long> getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		return getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		return getService().getSubscribedCategories(
			groupId, userId, start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId) {
		return getService().getSubscribedCategoriesCount(groupId, userId);
	}

	public static MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException {

		return getService().moveCategory(
			categoryId, parentCategoryId, mergeWithParentCategory);
	}

	public static MBCategory moveCategoryFromTrash(
			long categoryId, long newCategoryId)
		throws PortalException {

		return getService().moveCategoryFromTrash(categoryId, newCategoryId);
	}

	public static MBCategory moveCategoryToTrash(long categoryId)
		throws PortalException {

		return getService().moveCategoryToTrash(categoryId);
	}

	public static void restoreCategoryFromTrash(long categoryId)
		throws PortalException {

		getService().restoreCategoryFromTrash(categoryId);
	}

	public static void subscribeCategory(long groupId, long categoryId)
		throws PortalException {

		getService().subscribeCategory(groupId, categoryId);
	}

	public static void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException {

		getService().unsubscribeCategory(groupId, categoryId);
	}

	public static MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail, boolean mergeWithParentCategory,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCategory(
			categoryId, parentCategoryId, name, description, displayStyle,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, mergeWithParentCategory,
			serviceContext);
	}

	public static MBCategoryService getService() {
		return _service;
	}

	public static void setService(MBCategoryService service) {
		_service = service;
	}

	private static volatile MBCategoryService _service;

}