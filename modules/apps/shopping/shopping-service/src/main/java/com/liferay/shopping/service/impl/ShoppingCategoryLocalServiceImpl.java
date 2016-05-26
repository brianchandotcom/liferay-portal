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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.shopping.exception.CategoryNameException;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCategoryConstants;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.service.base.ShoppingCategoryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {"model.class.name=com.liferay.shopping.model.ShoppingItem"}
)
public class ShoppingCategoryLocalServiceImpl
	extends ShoppingCategoryLocalServiceBaseImpl {

	@Override
	public ShoppingCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		long categoryId = counterLocalService.increment();

		ShoppingCategory category = shoppingCategoryPersistence.create(
			categoryId);

		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		shoppingCategoryPersistence.update(category);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getModelPermissions());
		}

		return category;
	}

	@Override
	public void addCategoryResources(
			long categoryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		addCategoryResources(
			category, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			long categoryId, ModelPermissions modelPermissions)
		throws PortalException {

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		addCategoryResources(category, modelPermissions);
	}

	@Override
	public void addCategoryResources(
			ShoppingCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), ShoppingCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			ShoppingCategory category, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), ShoppingCategory.class.getName(),
			category.getCategoryId(), modelPermissions);
	}

	@Override
	public void deleteCategories(long groupId) throws PortalException {
		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByGroupId(groupId);

		for (ShoppingCategory category : categories) {
			deleteCategory(category);
		}
	}

	@Override
	public void deleteCategory(long categoryId) throws PortalException {
		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		deleteCategory(category);
	}

	@Override
	public void deleteCategory(ShoppingCategory category)
		throws PortalException {

		// Categories

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(
				category.getGroupId(), category.getCategoryId());

		for (ShoppingCategory curCategory : categories) {
			deleteCategory(curCategory);
		}

		// Category

		shoppingCategoryPersistence.remove(category);

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), ShoppingCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Items

		shoppingItemLocalService.deleteItems(
			category.getGroupId(), category.getCategoryId());
	}

	@Override
	public List<ShoppingCategory> getCategories(long groupId) {
		return shoppingCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return shoppingCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return shoppingCategoryPersistence.countByG_P(
			groupId, parentCategoryId);
	}

	@Override
	public ShoppingCategory getCategory(long categoryId)
		throws PortalException {

		return shoppingCategoryPersistence.findByPrimaryKey(categoryId);
	}

	@Override
	public ShoppingCategory getCategory(long groupId, String categoryName) {
		return shoppingCategoryPersistence.fetchByG_N(groupId, categoryName);
	}

	@Override
	public List<ShoppingCategory> getParentCategories(long categoryId)
		throws PortalException {

		return getParentCategories(
			shoppingCategoryPersistence.findByPrimaryKey(categoryId));
	}

	@Override
	public List<ShoppingCategory> getParentCategories(ShoppingCategory category)
		throws PortalException {

		List<ShoppingCategory> parentCategories = new ArrayList<>();

		ShoppingCategory tempCategory = category;

		while (true) {
			parentCategories.add(tempCategory);

			if (tempCategory.getParentCategoryId() ==
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				break;
			}

			tempCategory = shoppingCategoryPersistence.findByPrimaryKey(
				tempCategory.getParentCategoryId());
		}

		Collections.reverse(parentCategories);

		return parentCategories;
	}

	@Override
	public ShoppingCategory getParentCategory(ShoppingCategory category)
		throws PortalException {

		ShoppingCategory parentCategory =
			shoppingCategoryPersistence.findByPrimaryKey(
				category.getParentCategoryId());

		return parentCategory;
	}

	@Override
	public void getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(groupId, categoryId);

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

		// Merge categories

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory && (categoryId != parentCategoryId) &&
			(parentCategoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		shoppingCategoryPersistence.update(category);

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId) {
		if (parentCategoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			ShoppingCategory parentCategory =
				shoppingCategoryPersistence.fetchByPrimaryKey(parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId =
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
		ShoppingCategory category, long parentCategoryId) {

		if (parentCategoryId ==
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}

		ShoppingCategory parentCategory =
			shoppingCategoryPersistence.fetchByPrimaryKey(parentCategoryId);

		if ((parentCategory == null) ||
			(category.getGroupId() != parentCategory.getGroupId())) {

			return category.getParentCategoryId();
		}

		List<Long> subcategoryIds = new ArrayList<>();

		getSubcategoryIds(
			subcategoryIds, category.getGroupId(), category.getCategoryId());

		if (subcategoryIds.contains(parentCategoryId)) {
			return category.getParentCategoryId();
		}

		return parentCategoryId;
	}

	protected void mergeCategories(
			ShoppingCategory fromCategory, long toCategoryId)
		throws PortalException {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(
				fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (ShoppingCategory category : categories) {
			mergeCategories(category, toCategoryId);
		}

		List<ShoppingItem> items = shoppingItemPersistence.findByG_C(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (ShoppingItem item : items) {

			// Item

			item.setCategoryId(toCategoryId);

			shoppingItemPersistence.update(item);
		}

		deleteCategory(fromCategory);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name) || name.contains("\\\\") ||
			name.contains("//")) {

			throw new CategoryNameException();
		}
	}

}