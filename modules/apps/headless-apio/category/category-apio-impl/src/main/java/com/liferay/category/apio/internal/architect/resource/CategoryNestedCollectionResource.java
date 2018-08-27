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

package com.liferay.category.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.CategoryForm;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.vocabulary.apio.architect.identifier.VocabularyIdentifier;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Category} resources
 * through a web API. The resources are mapped from the internal model {@code
 * AssetCategory}.
 *
 * @author Javier Gamarra
 * @author Eduardo Pérez
 * @review
 */
@Component(immediate = true)
public class CategoryNestedCollectionResource
	implements NestedCollectionResource
		<AssetCategory, Long, CategoryIdentifier, Long, VocabularyIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetCategory, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetCategory,
			_hasPermission.forAddingIn(VocabularyIdentifier.class),
			CategoryForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "categories";
	}

	@Override
	public ItemRoutes<AssetCategory, Long> itemRoutes(
		ItemRoutes.Builder<AssetCategory, Long> builder) {

		return builder.addGetter(
			this::_getAssetCategory
		).addUpdater(
			this::_updateAssetCategory, _hasPermission::forUpdating,
			CategoryForm::buildForm
		).addRemover(
			idempotent(_assetCategoryService::deleteCategory),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<AssetCategory> representor(
		Representor.Builder<AssetCategory, Long> builder) {

		return builder.types(
			"Category"
		).identifier(
			AssetCategory::getCategoryId
		).addBidirectionalModel(
			"category", "subcategories", CategoryIdentifier.class,
			AssetCategory::getParentCategoryId
		).addBidirectionalModel(
			"vocabulary", "vocabularyCategories", VocabularyIdentifier.class,
			AssetCategory::getVocabularyId
		).addDate(
			"dateCreated", AssetCategory::getCreateDate
		).addDate(
			"dateModified", AssetCategory::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetCategory::getUserId
		).addLocalizedStringByLocale(
			"description", AssetCategory::getDescription
		).addLocalizedStringByLocale(
			"name", AssetCategory::getTitle
		).build();
	}

	private AssetCategory _addAssetCategory(
			long vocabularyId, CategoryForm categoryForm)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		Group group = _groupLocalService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		return _assetCategoryService.addCategory(
			assetVocabulary.getGroupId(), 0, categoryForm.getTitles(locale),
			categoryForm.getDescriptions(locale), vocabularyId, null,
			serviceContext);
	}

	private AssetCategory _getAssetCategory(long assetCategoryId)
		throws PortalException {

		return _assetCategoryService.getCategory(assetCategoryId);
	}

	private PageItems<AssetCategory> _getPageItems(
			Pagination pagination, long assetVocabularyId)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			assetVocabularyId);

		List<AssetCategory> categories =
			_assetCategoryService.getVocabularyRootCategories(
				assetVocabulary.getGroupId(), assetVocabularyId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
		int count = _assetCategoryService.getVocabularyRootCategoriesCount(
			assetVocabulary.getGroupId(), assetVocabularyId);

		return new PageItems<>(categories, count);
	}

	private AssetCategory _updateAssetCategory(
			long assetCategoryId, CategoryForm categoryForm)
		throws PortalException {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			assetCategoryId);

		Group group = _groupLocalService.getGroup(assetCategory.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		return _assetCategoryService.updateCategory(
			assetCategoryId, assetCategory.getParentCategoryId(),
			categoryForm.getTitles(locale),
			categoryForm.getDescriptions(locale),
			assetCategory.getVocabularyId(), null, serviceContext);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetCategory)"
	)
	private HasPermission<Long> _hasPermission;

}