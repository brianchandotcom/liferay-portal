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

import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.router.base.BaseCategoryNestedCollectionRouter;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class BlogPostingCategoryNestedCollectionRouter extends
	BaseCategoryNestedCollectionRouter<BlogPostingIdentifier>
	implements NestedCollectionRouter
		<AssetCategory, Long, CategoryIdentifier, Long, BlogPostingIdentifier>{

	@Override
	protected AssetCategoryService getAssetCategoryService() {
		return _assetCategoryService;
	}

	@Override
	protected long getClassNameId() {
		return _classNameLocalService.getClassNameId(
			BlogsEntry.class.getName());
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}