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

package com.liferay.category.apio.internal.architect.router.base;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Eduardo Perez
 */
public abstract class BaseCategoryNestedCollectionRouter
	<T extends Identifier<Long>> implements
		NestedCollectionRouter
			<AssetCategory, Long, CategoryIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetCategory, Long, Long> builder) {

		return builder.addGetter(
			this::_getItems
		).build();
	}

	protected abstract AssetCategoryService getAssetCategoryService();

	protected abstract long getClassNameId();

	private PageItems<AssetCategory> _getItems(Pagination pagination, Long id)
		throws PortalException {

		long classNameId = getClassNameId();

		List<AssetCategory> assetCategories =
			getAssetCategoryService().getCategories(
				classNameId, id, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = getAssetCategoryService().getCategoriesCount(
			classNameId, id);

		return new PageItems<>(assetCategories, count);
	}

}