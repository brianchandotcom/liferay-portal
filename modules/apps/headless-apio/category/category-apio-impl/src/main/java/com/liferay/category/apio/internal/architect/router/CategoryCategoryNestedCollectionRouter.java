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
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.CategoryForm;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the {@code Category} resources
 * contained inside another {@code Category} through a web API. The resources
 * are mapped from the internal model {@link AssetCategory}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class CategoryCategoryNestedCollectionRouter
	implements NestedCollectionRouter
		<AssetCategory, Long, CategoryIdentifier, Long, CategoryIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetCategory, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetCategory,
			_hasPermission.forAddingIn(CategoryIdentifier.class),
			CategoryForm::buildForm
		).build();
	}

	private AssetCategory _addAssetCategory(
			long parentCategoryId, CategoryForm categoryForm)
		throws PortalException {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			parentCategoryId);

		Group group = _groupLocalService.getGroup(assetCategory.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		return _assetCategoryService.addCategory(
			group.getGroupId(), parentCategoryId,
			categoryForm.getTitles(locale),
			categoryForm.getDescriptions(locale),
			assetCategory.getVocabularyId(), null, serviceContext);
	}

	private PageItems<AssetCategory> _getPageItems(
			Pagination pagination, long parentCategoryId)
		throws PortalException {

		List<AssetCategory> assetCategories =
			_assetCategoryService.getChildCategories(
				parentCategoryId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _assetCategoryService.getChildCategoriesCount(
			parentCategoryId);

		return new PageItems<>(assetCategories, count);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetCategory)"
	)
	private HasPermission<Long> _hasPermission;

}