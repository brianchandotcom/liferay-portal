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
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.LinkedCategoryForm;
import com.liferay.category.apio.internal.architect.form.NestedCategoryForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base class for the {@code *CategoryNestedCollectionRouter}
 * classes.
 *
 * @author Eduardo Pérez
 */
public abstract class BaseCategoryNestedCollectionRouter
	<T extends Identifier<Long>>
		implements NestedCollectionRouter
			<AssetCategory, Long, CategoryIdentifier, Long, T> {

	/**
	 * Creates a new {@code AssetCategory} and links it to the {@code
	 * AssetEntry} that corresponds to the provided ID.
	 *
	 * @param  classPK the {@code AssetEntry} ID
	 * @param  nestedCategoryForm the form containing the new category data
	 * @return the new {@code AssetCategory}
	 */
	protected AssetCategory addAssetCategory(
			long classPK, NestedCategoryForm nestedCategoryForm)
		throws PortalException {

		AssetVocabulary assetVocabulary = assetVocabularyService.getVocabulary(
			nestedCategoryForm.getVocabularyId());

		Group group = groupLocalService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		long parentCategoryId = nestedCategoryForm.getParentCategoryId();

		AssetCategory assetCategory = assetCategoryService.addCategory(
			group.getGroupId(), parentCategoryId,
			nestedCategoryForm.getTitles(locale),
			nestedCategoryForm.getDescriptions(locale),
			assetVocabulary.getVocabularyId(), null, new ServiceContext());

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			getClassName(), classPK);

		long[] categoryIds = ArrayUtil.append(
			assetEntry.getCategoryIds(), assetCategory.getCategoryId());

		assetEntryService.updateEntry(
			assetEntry.getGroupId(), assetEntry.getCreateDate(), null,
			assetEntry.getClassName(), classPK, assetEntry.getClassUuid(),
			assetEntry.getClassTypeId(), categoryIds, assetEntry.getTagNames(),
			assetEntry.isListable(), assetEntry.isVisible(),
			assetEntry.getStartDate(), assetEntry.getEndDate(),
			assetEntry.getPublishDate(), assetEntry.getExpirationDate(),
			assetEntry.getMimeType(), assetEntry.getTitle(),
			assetEntry.getDescription(), assetEntry.getSummary(),
			assetEntry.getUrl(), assetEntry.getLayoutUuid(),
			assetEntry.getHeight(), assetEntry.getWidth(),
			assetEntry.getPriority());

		return assetCategory;
	}

	protected abstract String getClassName();

	protected PageItems<AssetCategory> getPageItems(
		Pagination pagination, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(getClassName());

		List<AssetCategory> assetCategories =
			assetCategoryService.getCategories(
				classNameId, classPK, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = assetCategoryService.getCategoriesCount(
			classNameId, classPK);

		return new PageItems<>(assetCategories, count);
	}

	/**
	 * Links an {@code AssetCategory} to an {@code AssetEntry} that corresponds
	 * to the provided ID.
	 *
	 * @param  classPK the {@code AssetEntry} ID
	 * @param  linkedCategoryForm the form containing the category to link
	 * @return the new {@code AssetCategory}
	 */
	protected AssetCategory linkAssetCategory(
			long classPK, LinkedCategoryForm linkedCategoryForm)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			getClassName(), classPK);

		long categoryId = linkedCategoryForm.getCategoryId();

		AssetCategory assetCategory = assetCategoryService.getCategory(
			categoryId);

		long[] categoryIds = ArrayUtil.append(
			assetEntry.getCategoryIds(), categoryId);

		assetEntryService.updateEntry(
			assetEntry.getGroupId(), assetEntry.getCreateDate(), null,
			assetEntry.getClassName(), classPK, assetEntry.getClassUuid(),
			assetEntry.getClassTypeId(), categoryIds, assetEntry.getTagNames(),
			assetEntry.isListable(), assetEntry.isVisible(),
			assetEntry.getStartDate(), assetEntry.getEndDate(),
			assetEntry.getPublishDate(), assetEntry.getExpirationDate(),
			assetEntry.getMimeType(), assetEntry.getTitle(),
			assetEntry.getDescription(), assetEntry.getSummary(),
			assetEntry.getUrl(), assetEntry.getLayoutUuid(),
			assetEntry.getHeight(), assetEntry.getWidth(),
			assetEntry.getPriority());

		return assetCategory;
	}

	@Reference
	protected AssetCategoryService assetCategoryService;

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected AssetEntryService assetEntryService;

	@Reference
	protected AssetVocabularyService assetVocabularyService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

}