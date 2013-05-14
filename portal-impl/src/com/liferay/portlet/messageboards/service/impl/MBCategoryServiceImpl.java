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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return mbCategoryLocalService.addCategory(
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

	@Override
	public void deleteCategory(long categoryId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.DELETE);

		mbCategoryLocalService.deleteCategory(category, includeTrashedEntries);
	}

	@Override
	public void deleteCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.DELETE);

		mbCategoryLocalService.deleteCategory(categoryId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId) throws SystemException {
		return mbCategoryPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status)
		throws SystemException {

		return mbCategoryPersistence.filterFindByG_S(groupId, status);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.filterFindByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.filterFindByG_P(
				groupId, parentCategoryId, start, end);
		}

		return mbCategoryPersistence.filterFindByG_P_S(
			groupId, parentCategoryId, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.filterFindByG_P(
			groupId, parentCategoryIds, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int status, int start,
			int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.filterFindByG_P(
				groupId, parentCategoryIds, start, end);
		}

		return mbCategoryPersistence.filterFindByG_P_S(
			groupId, parentCategoryIds, status, start, end);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.filterCountByG_P(
			groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long parentCategoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.filterCountByG_P(
				groupId, parentCategoryId);
		}

		return mbCategoryPersistence.filterCountByG_P_S(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws SystemException {

		return mbCategoryPersistence.filterCountByG_P(
			groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long[] parentCategoryIds, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.filterCountByG_P(
				groupId, parentCategoryIds);
		}

		return mbCategoryPersistence.filterCountByG_P_S(
			groupId, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.VIEW);

		return category;
	}

	@Override
	public long[] getCategoryIds(long groupId, long categoryId)
		throws SystemException {

		List<Long> categoryIds = new ArrayList<Long>();

		categoryIds.add(categoryId);

		getSubcategoryIds(categoryIds, groupId, categoryId);

		return ArrayUtil.toArray(
			categoryIds.toArray(new Long[categoryIds.size()]));
	}

	@Override
	public List<Long> getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

		List<MBCategory> categories = mbCategoryPersistence.filterFindByG_P(
			groupId, categoryId);

		for (MBCategory category : categories) {
			if (category.isInTrash() || category.isInTrashContainer()) {
				continue;
			}

			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}

		return categoryIds;
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
			long groupId, long userId, int start, int end)
		throws SystemException {

		long[] categoryIds = getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return Collections.emptyList();
		}
		else {
			QueryDefinition queryDefinition = new QueryDefinition(
				WorkflowConstants.STATUS_ANY, start, end, null);

			return mbCategoryFinder.filterFindByS_G_U_P(
				groupId, userId, categoryIds, queryDefinition);
		}
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws SystemException {

		long[] categoryIds = getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return 0;
		}
		else {
			QueryDefinition queryDefinition = new QueryDefinition(
				WorkflowConstants.STATUS_ANY);

			return mbCategoryFinder.filterCountByS_G_U_P(
				groupId, userId, categoryIds, queryDefinition);
		}
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return mbCategoryLocalService.moveCategory(
			categoryId, parentCategoryId, mergeWithParentCategory);
	}

	@Override
	public MBCategory moveCategoryFromTrash(long categoryId, long newCategoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return mbCategoryLocalService.moveCategoryFromTrash(
			getUserId(), categoryId, newCategoryId);
	}

	@Override
	public MBCategory moveCategoryToTrash(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.DELETE);

		return mbCategoryLocalService.moveCategoryToTrash(
			getUserId(), categoryId);
	}

	@Override
	public void restoreCategoryFromTrash(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.DELETE);

		mbCategoryLocalService.restoreCategoryFromTrash(
			getUserId(), categoryId);
	}

	@Override
	public void subscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.subscribeCategory(
			getUserId(), groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.unsubscribeCategory(
			getUserId(), groupId, categoryId);
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
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

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