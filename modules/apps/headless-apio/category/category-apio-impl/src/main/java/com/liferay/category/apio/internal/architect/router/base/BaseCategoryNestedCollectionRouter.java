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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.AssetCategoryNestedForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

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
			this::getPageItems
		).build();
	}

	protected AssetCategory addAssetCategory(
			Long id, AssetCategoryNestedForm assetCategoryNestedForm)
		throws PortalException {

		AssetVocabulary vocabulary = getAssetVocabularyService().getVocabulary(
			assetCategoryNestedForm.getVocabularyId());
		long parentCategoryId = assetCategoryNestedForm.getParentCategoryId();

		Group group = getGroupLocalService().getGroup(vocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		AssetCategory assetCategory = getAssetCategoryService().addCategory(
			group.getGroupId(), parentCategoryId,
			assetCategoryNestedForm.getTitleMap(locale),
			assetCategoryNestedForm.getDescriptionMap(locale),
			vocabulary.getVocabularyId(), null, serviceContext);

		AssetEntry assetEntry = getAssetEntryLocalService().getEntry(
			getClassName(), id);

		long[] categoryIds = ArrayUtil.append(
			assetEntry.getCategoryIds(), assetCategory.getCategoryId());

		getAssetEntryService().updateEntry(
			assetEntry.getGroupId(), assetEntry.getCreateDate(), null,
			assetEntry.getClassName(), assetEntry.getClassPK(),
			assetEntry.getClassUuid(), assetEntry.getClassTypeId(), categoryIds,
			assetEntry.getTagNames(), assetEntry.isListable(),
			assetEntry.isVisible(), assetEntry.getStartDate(),
			assetEntry.getEndDate(), assetEntry.getPublishDate(),
			assetEntry.getExpirationDate(), assetEntry.getMimeType(),
			assetEntry.getTitle(), assetEntry.getDescription(),
			assetEntry.getSummary(), assetEntry.getUrl(),
			assetEntry.getLayoutUuid(), assetEntry.getHeight(),
			assetEntry.getWidth(), assetEntry.getPriority());

		return assetCategory;
	}

	protected abstract AssetCategoryService getAssetCategoryService();

	protected abstract AssetEntryLocalService getAssetEntryLocalService();

	protected abstract AssetEntryService getAssetEntryService();

	protected abstract AssetVocabularyService getAssetVocabularyService();

	protected abstract String getClassName();

	protected abstract long getClassNameId();

	protected abstract GroupLocalService getGroupLocalService();

	protected PageItems<AssetCategory> getPageItems(
			Pagination pagination, Long id)
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