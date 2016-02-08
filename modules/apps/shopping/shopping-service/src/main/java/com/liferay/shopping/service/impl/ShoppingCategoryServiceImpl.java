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

package com.liferay.shopping.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.service.base.ShoppingCategoryServiceBaseImpl;
import com.liferay.shopping.service.permission.ShoppingCategoryPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryServiceImpl
	extends ShoppingCategoryServiceBaseImpl {

	@Override
	public ShoppingCategory addCategory(
			long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		shoppingCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return shoppingCategoryLocalService.addCategory(
			getUserId(), parentCategoryId, name, description, serviceContext);
	}

	@Override
	public void deleteCategory(long categoryId) throws PortalException {
		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		shoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.DELETE);

		shoppingCategoryLocalService.deleteCategory(categoryId);
	}

	@Override
	public List<ShoppingCategory> getCategories(long groupId) {
		return shoppingCategoryPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return shoppingCategoryPersistence.filterFindByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public List<Object> getCategoriesAndItems(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<?> obc) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY, start, end,
			(OrderByComparator<Object>)obc);

		return shoppingCategoryFinder.filterFindC_I_ByG_C(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public int getCategoriesAndItemsCount(long groupId, long categoryId) {
		return shoppingCategoryFinder.countC_I_ByG_C(groupId, categoryId);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return shoppingCategoryPersistence.filterCountByG_P(
			groupId, parentCategoryId);
	}

	@Override
	public ShoppingCategory getCategory(long categoryId)
		throws PortalException {

		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		shoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.VIEW);

		return category;
	}

	@Override
	public void getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.filterFindByG_P(groupId, categoryId);

		for (ShoppingCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}
	}

	@Override
	public ShoppingCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException {

		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		shoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return shoppingCategoryLocalService.updateCategory(
			categoryId, parentCategoryId, name, description,
			mergeWithParentCategory, serviceContext);
	}

	@ServiceReference(type = ShoppingCategoryPermission.class)
	protected ShoppingCategoryPermission shoppingCategoryPermission;

}