/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.base.MBCategoryServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBCategoryServiceImpl extends MBCategoryServiceBaseImpl {

	public MBCategory addCategory(
			long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return mbCategoryLocalService.addCategory(
			getUserId(), parentCategoryId, name, description, displayStyle,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, serviceContext);
	}

	public void deleteCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.DELETE);

		mbCategoryLocalService.deleteCategory(categoryId);
	}

	public List<MBCategory> getCategories(long groupId) throws SystemException {
		return mbCategoryPersistence.filterFindByGroupId(groupId);
	}

	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.filterFindByG_P(
			groupId, parentCategoryId, start, end);
	}

	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.filterFindByG_P(
			groupId, parentCategoryIds, start, end);
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.filterCountByG_P(
			groupId, parentCategoryId);
	}

	public int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws SystemException {

		return mbCategoryPersistence.filterCountByG_P(
			groupId, parentCategoryIds);
	}

	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryLocalService.getCategory(categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.VIEW);

		return category;
	}

	public long[] getCategoryIds(long groupId, long categoryId)
		throws SystemException {

		List<Long> categoryIds = new ArrayList<Long>();

		categoryIds.add(categoryId);

		getSubcategoryIds(categoryIds, groupId, categoryId);

		return ArrayUtil.toArray(
			categoryIds.toArray(new Long[categoryIds.size()]));
	}

	public List<Long> getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

		List<MBCategory> categories = mbCategoryPersistence.filterFindByG_P(
			groupId, categoryId);

		for (MBCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}

		return categoryIds;
	}

	public List<MBCategory> getSubscribedCategories(
			long groupId, long userId, int start, int end)
		throws SystemException {

		long[] categoryIds = getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return Collections.emptyList();
		}
		else {
			return mbCategoryFinder.filterFindByS_G_U_P(
				groupId, userId, categoryIds, start, end);
		}
	}

	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws SystemException {

		long[] categoryIds = getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return 0;
		}
		else {
			return mbCategoryFinder.filterCountByS_G_U_P(
				groupId, userId, categoryIds);
		}
	}

	public void subscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.subscribeCategory(
			getUserId(), groupId, categoryId);
	}

	public void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.unsubscribeCategory(
			getUserId(), groupId, categoryId);
	}

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
		throws PortalException, SystemException {

		MBCategory category = mbCategoryLocalService.getCategory(categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return mbCategoryLocalService.updateCategory(
			categoryId, parentCategoryId, name, description, displayStyle,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, allowAnonymousEmail, mergeWithParentCategory,
			serviceContext);
	}

}