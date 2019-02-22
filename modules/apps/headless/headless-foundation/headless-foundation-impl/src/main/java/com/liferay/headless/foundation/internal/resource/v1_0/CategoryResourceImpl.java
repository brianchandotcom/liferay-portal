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
import com.liferay.headless.foundation.internal.dto.v1_0.ParentVocabularyImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.CategoryEntityModel;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	scope = ServiceScope.PROTOTYPE,
	service = {CategoryResource.class, EntityModelResource.class}
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
		return _entityModel;
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

		return _toCategory(
			_assetCategoryService.addCategory(
				assetCategory.getGroupId(), categoryId,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					category.getName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					category.getDescription()),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	@Override
	public Category postVocabularyCategory(Long vocabularyId, Category category)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		return _toCategory(
			_assetCategoryService.addCategory(
				assetVocabulary.getGroupId(), 0,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					category.getName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					category.getDescription()),
				vocabularyId, null, new ServiceContext()));
	}

	@Override
	public Category putCategory(Long categoryId, Category category)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			categoryId);

		return _toCategory(
			_assetCategoryService.updateCategory(
				categoryId, assetCategory.getParentCategoryId(),
				LocalizedMapUtil.merge(
					assetCategory.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						category.getName())),
				LocalizedMapUtil.merge(
					assetCategory.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						category.getDescription())),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	private Page<Category> _getCategoriesPage(
			Consumer<BooleanQuery> booleanQueryConsumer, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<AssetCategory> assetCategories = new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(AssetCategory.class),
			pagination, booleanQueryConsumer,
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ASSET_CATEGORY_ID);
			},
			searchContext -> {
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

				parentVocabulary = new ParentVocabularyImpl() {
					{
						id = assetCategory.getVocabularyId();

						setName(
							() -> {
								AssetVocabulary assetVocabulary =
									_assetVocabularyService.getVocabulary(
										assetCategory.getVocabularyId());

								return assetVocabulary.getName();
							});
					}
				};
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

	private static final EntityModel _entityModel = new CategoryEntityModel();

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

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