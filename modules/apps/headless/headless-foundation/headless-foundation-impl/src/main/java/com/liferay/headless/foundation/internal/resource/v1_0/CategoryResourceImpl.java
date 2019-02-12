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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.internal.util.UserAccountUtil;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/category.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryResource.class
)
public class CategoryResourceImpl extends BaseCategoryResourceImpl {

	@Override
	public Category getCategories(Long categoriesId) throws Exception {
		return _toCategory(_assetCategoryService.getCategory(categoriesId));
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Long categoriesId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_assetCategoryService.getChildCategories(
					categoriesId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toCategory),
			pagination,
			_assetCategoryService.getChildCategoriesCount(categoriesId));
	}

	public Page<Category> getVocabulariesCategoriesPage(
			Long vocabulariesId, Pagination pagination)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabulariesId);

		return Page.of(
			transform(
				_assetCategoryService.getVocabularyRootCategories(
					assetVocabulary.getGroupId(), vocabulariesId,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toCategory),
			pagination,
			_assetCategoryService.getVocabularyRootCategoriesCount(
				assetVocabulary.getGroupId(), vocabulariesId));
	}

	public Category postCategoriesCategories(
			Long categoriesId, Category category)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			categoriesId);

		Group group = _groupService.getGroup(assetCategory.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				group.getGroupId(), categoriesId,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	public Category postVocabulariesCategories(
			Long vocabulariesId, Category category)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabulariesId);

		Group group = _groupService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				assetVocabulary.getGroupId(), 0,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				vocabulariesId, null, new ServiceContext()));
	}

	private UserAccount _getCreator(long userId) throws PortalException {
		return UserAccountUtil.toUserAccount(_userService.getUserById(userId));
	}

	private Category[] _toCategories(AssetCategory assetCategory)
		throws PortalException {

		return transform(
			assetCategory.getAncestors(), this::_toCategory
		).toArray(
			new Category[0]
		);
	}

	private Category _toCategory(AssetCategory assetCategory)
		throws PortalException {

		return new Category() {
			{
				Locale preferredLocale = acceptLanguage.getPreferredLocale();

				setAvailableLanguages(assetCategory.getAvailableLanguageIds());

				if (assetCategory.getParentCategory() != null) {
					setCategory(_toCategory(assetCategory.getParentCategory()));
					setCategoryId(assetCategory.getParentCategoryId());
				}

				setCreator(_getCreator(assetCategory.getUserId()));
				setCreatorId(assetCategory.getUserId());
				setDateCreated(assetCategory.getCreateDate());
				setDateModified(assetCategory.getModifiedDate());
				setDescription(assetCategory.getDescription(preferredLocale));
				setId(assetCategory.getCategoryId());
				setName(assetCategory.getTitle(preferredLocale));
				setSubcategories(_toCategories(assetCategory));
				setVocabularyId(assetCategory.getVocabularyId());
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private GroupService _groupService;

	@Reference
	private UserService _userService;

}