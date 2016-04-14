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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingCategoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryService
 * @generated
 */
@ProviderType
public class ShoppingCategoryServiceWrapper implements ShoppingCategoryService,
	ServiceWrapper<ShoppingCategoryService> {
	public ShoppingCategoryServiceWrapper(
		ShoppingCategoryService shoppingCategoryService) {
		_shoppingCategoryService = shoppingCategoryService;
	}

	@Override
	public com.liferay.shopping.model.ShoppingCategory addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCategoryService.addCategory(parentCategoryId, name,
			description, serviceContext);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCategoryService.getCategory(categoryId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCategoryService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory,
			serviceContext);
	}

	@Override
	public int getCategoriesAndItemsCount(long groupId, long categoryId) {
		return _shoppingCategoryService.getCategoriesAndItemsCount(groupId,
			categoryId);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return _shoppingCategoryService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCategory> getCategories(
		long groupId) {
		return _shoppingCategoryService.getCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {
		return _shoppingCategoryService.getCategories(groupId,
			parentCategoryId, start, end);
	}

	@Override
	public java.util.List<java.lang.Object> getCategoriesAndItems(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return _shoppingCategoryService.getCategoriesAndItems(groupId,
			categoryId, start, end, obc);
	}

	@Override
	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingCategoryService.deleteCategory(categoryId);
	}

	@Override
	public void getSubcategoryIds(java.util.List<java.lang.Long> categoryIds,
		long groupId, long categoryId) {
		_shoppingCategoryService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	@Override
	public ShoppingCategoryService getWrappedService() {
		return _shoppingCategoryService;
	}

	@Override
	public void setWrappedService(
		ShoppingCategoryService shoppingCategoryService) {
		_shoppingCategoryService = shoppingCategoryService;
	}

	private ShoppingCategoryService _shoppingCategoryService;
}