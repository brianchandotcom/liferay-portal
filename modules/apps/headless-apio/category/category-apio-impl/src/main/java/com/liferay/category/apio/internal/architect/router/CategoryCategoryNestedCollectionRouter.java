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

package com.liferay.category.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class CategoryCategoryNestedCollectionRouter implements
	NestedCollectionRouter
		<AssetCategory, Long, CategoryIdentifier, Long, CategoryIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetCategory, Long, Long> builder) {

		return builder.addGetter(
			this::_getItems
		).build();
	}

	private PageItems<AssetCategory> _getItems(
			Pagination pagination, Long parentCategoryId)
		throws PortalException {

		List<AssetCategory> childCategories =
			_assetCategoryService.getChildCategories(
				parentCategoryId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _assetCategoryService.getChildCategoriesCount(
			parentCategoryId);

		return new PageItems<>(childCategories, count);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

}