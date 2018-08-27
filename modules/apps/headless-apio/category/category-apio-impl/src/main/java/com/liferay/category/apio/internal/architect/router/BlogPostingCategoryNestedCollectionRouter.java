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
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.LinkedCategoryForm;
import com.liferay.category.apio.internal.architect.router.base.BaseCategoryNestedCollectionRouter;
import com.liferay.portal.apio.permission.HasPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the {@code Category} resources
 * contained inside a <a href="http://schema.org/BlogPosting">BlogPosting</a>
 * through a web API. The resources are mapped from the internal model {@link
 * AssetCategory} and {@code BlogsEntry}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class BlogPostingCategoryNestedCollectionRouter
	extends BaseCategoryNestedCollectionRouter<BlogPostingIdentifier>
	implements NestedCollectionRouter
		<AssetCategory, Long, CategoryIdentifier, Long, BlogPostingIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long, Long> collectionRoutes(
		Builder<AssetCategory, Long, Long> builder) {

		return builder.addGetter(
			this::getPageItems
		).addCreator(
			this::linkAssetCategory,
			_hasPermission.forAddingIn(BlogPostingIdentifier.class),
			LinkedCategoryForm::buildForm
		).build();
	}

	@Override
	protected String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetCategory)"
	)
	private HasPermission<Long> _hasPermission;

}