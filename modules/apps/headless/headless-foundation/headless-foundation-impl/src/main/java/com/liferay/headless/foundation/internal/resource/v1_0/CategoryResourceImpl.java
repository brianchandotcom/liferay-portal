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
import com.liferay.headless.foundation.dto.v1_0.ParentCategory;
import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ParentCategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.CategoryEntityModel;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.ws.rs.core.MultivaluedMap;

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
public class CategoryResourceImpl
	extends BaseCategoryResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteCategory(Long categoryId) throws Exception {
		_assetCategoryService.deleteCategory(categoryId);

		return true;
	}

	@Override
	public Category getCategory(Long categoryId) throws Exception {
		return _toCategory(_assetCategoryService.getCategory(categoryId));
	}

	@Override
	public Page<Category> getCategoryCategoriesPage(
			Long categoryId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getCategoriesPage(
			booleanQuery -> {
				if (categoryId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.ASSET_PARENT_CATEGORY_ID,
							String.valueOf(categoryId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _categoryEntityModel;
	}

	@Override
	public Page<Category> getVocabularyCategoriesPage(
			Long vocabularyId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getCategoriesPage(
			booleanQuery -> {
				if (vocabularyId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.ASSET_VOCABULARY_ID,
							String.valueOf(vocabularyId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, pagination, sorts);
	}

	@Override
	public Category postCategoryCategory(Long categoryId, Category category)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			categoryId);

		Group group = _groupService.getGroup(assetCategory.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				group.getGroupId(), categoryId,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	@Override
	public Category postVocabularyCategory(Long vocabularyId, Category category)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		Group group = _groupService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				assetVocabulary.getGroupId(), 0,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				vocabularyId, null, new ServiceContext()));
	}

	private Page<Category> _getCategoriesPage(
			Consumer<BooleanQuery> booleanQueryConsumer, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<AssetCategory> assetCategories = new ArrayList<>();

		ClassName className = _classNameService.fetchClassName(
			AssetCategory.class.getName());

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(AssetCategory.class),
			pagination, booleanQueryConsumer,
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ASSET_CATEGORY_ID);
			},
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID, className.getClassNameId());
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			_searchResultPermissionFilterFactory, sorts);

		for (Document document : hits.getDocs()) {
			AssetCategory assetCategory = _assetCategoryService.getCategory(
				GetterUtil.getLong(document.get(Field.ASSET_CATEGORY_ID)));

			assetCategories.add(assetCategory);
		}

		return Page.of(
			transform(assetCategories, this::_toCategory), pagination,
			assetCategories.size());
	}

	private Category _toCategory(AssetCategory assetCategory) throws Exception {
		return new CategoryImpl() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					assetCategory.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(assetCategory.getUserId()));
				creatorId = assetCategory.getUserId();
				dateCreated = assetCategory.getCreateDate();
				dateModified = assetCategory.getModifiedDate();
				description = assetCategory.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = assetCategory.getCategoryId();
				name = assetCategory.getTitle(
					contextAcceptLanguage.getPreferredLocale());

				if (assetCategory.getParentCategory() != null) {
					parentCategory = _toParentCategory(
						assetCategory.getParentCategory());
				}

				parentVocabularyId = assetCategory.getVocabularyId();
			}
		};
	}

	private ParentCategory _toParentCategory(AssetCategory parentCategory) {
		return new ParentCategoryImpl() {
			{
				id = parentCategory.getCategoryId();
				name = parentCategory.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private static final CategoryEntityModel _categoryEntityModel =
		new CategoryEntityModel();

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private GroupService _groupService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}