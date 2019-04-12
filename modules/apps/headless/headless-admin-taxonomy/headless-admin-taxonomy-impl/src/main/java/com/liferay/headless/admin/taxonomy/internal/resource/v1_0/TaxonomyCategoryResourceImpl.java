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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.ParentTaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.taxonomy.internal.odata.entity.v1_0.CategoryEntityModel;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ContentLanguageUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.AbstractMap;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/taxonomy-category.properties",
	scope = ServiceScope.PROTOTYPE, service = TaxonomyCategoryResource.class
)
public class TaxonomyCategoryResourceImpl
	extends BaseTaxonomyCategoryResourceImpl implements EntityModelResource {

	@Override
	public void deleteTaxonomyCategory(Long taxonomyCategoryId)
		throws Exception {

		_assetCategoryService.deleteCategory(taxonomyCategoryId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public TaxonomyCategory getTaxonomyCategory(Long taxonomyCategoryId)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			taxonomyCategoryId);

		ContentLanguageUtil.addContentLanguageHeader(
			assetCategory.getAvailableLanguageIds(),
			assetCategory.getDefaultLanguageId(), _httpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		return _toTaxonomyCategory(assetCategory);
	}

	@Override
	public Page<TaxonomyCategory> getTaxonomyCategoryTaxonomyCategoriesPage(
			Long parentTaxonomyCategoryId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getCategoriesPage(
			booleanQuery -> {
				if (parentTaxonomyCategoryId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.ASSET_PARENT_CATEGORY_ID,
							String.valueOf(parentTaxonomyCategoryId)),
						BooleanClauseOccur.MUST);
				}
			},
			search, filter, pagination, sorts);
	}

	@Override
	public Page<TaxonomyCategory> getTaxonomyVocabularyTaxonomyCategoriesPage(
			Long taxonomyVocabularyId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getCategoriesPage(
			booleanQuery -> {
				if (taxonomyVocabularyId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.ASSET_PARENT_CATEGORY_ID,
							String.valueOf(
								AssetCategoryConstants.
									DEFAULT_PARENT_CATEGORY_ID)),
						BooleanClauseOccur.MUST);
					booleanFilter.add(
						new TermFilter(
							Field.ASSET_VOCABULARY_ID,
							String.valueOf(taxonomyVocabularyId)),
						BooleanClauseOccur.MUST);
				}
			},
			search, filter, pagination, sorts);
	}

	@Override
	public TaxonomyCategory patchTaxonomyCategory(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			taxonomyCategoryId);

		if (!ArrayUtil.contains(
				assetCategory.getAvailableLanguageIds(),
				contextAcceptLanguage.getPreferredLanguageId())) {

			throw new BadRequestException(
				StringBundler.concat(
					"Unable to patch taxonomy category with language ",
					LocaleUtil.toW3cLanguageId(
						contextAcceptLanguage.getPreferredLanguageId()),
					" because it is only available in the following languages ",
					LocaleUtil.toW3cLanguageIds(
						assetCategory.getAvailableLanguageIds())));
		}

		return _toTaxonomyCategory(
			_assetCategoryService.updateCategory(
				taxonomyCategoryId, assetCategory.getParentCategoryId(),
				LocalizedMapUtil.patch(
					assetCategory.getTitleMap(),
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyCategory.getName()),
				LocalizedMapUtil.patch(
					assetCategory.getDescriptionMap(),
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyCategory.getDescription()),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	@Override
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			Long parentTaxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			parentTaxonomyCategoryId);

		return _addTaxonomyCategory(
			assetCategory.getGroupId(), assetCategory.getDefaultLanguageId(),
			taxonomyCategory, assetCategory.getCategoryId(),
			assetCategory.getVocabularyId());
	}

	@Override
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			Long taxonomyVocabularyId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			taxonomyVocabularyId);

		return _addTaxonomyCategory(
			assetVocabulary.getGroupId(),
			assetVocabulary.getDefaultLanguageId(), taxonomyCategory, 0,
			assetVocabulary.getVocabularyId());
	}

	@Override
	public TaxonomyCategory putTaxonomyCategory(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			taxonomyCategoryId);

		return _toTaxonomyCategory(
			_assetCategoryService.updateCategory(
				taxonomyCategoryId, assetCategory.getParentCategoryId(),
				LocalizedMapUtil.merge(
					assetCategory.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						taxonomyCategory.getName())),
				LocalizedMapUtil.merge(
					assetCategory.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						taxonomyCategory.getDescription())),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	private TaxonomyCategory _addTaxonomyCategory(
			long groupId, String languageId, TaxonomyCategory taxonomyCategory,
			long taxonomyCategoryId, long taxonomyVocabularyId)
		throws Exception {

		if (!LocaleUtil.equals(
				LocaleUtil.fromLanguageId(languageId),
				contextAcceptLanguage.getPreferredLocale())) {

			String w3cLanguageId = LocaleUtil.toW3cLanguageId(languageId);

			throw new BadRequestException(
				"Taxonomy categories can only be created with the default " +
					"language " + w3cLanguageId);
		}

		return _toTaxonomyCategory(
			_assetCategoryService.addCategory(
				groupId, taxonomyCategoryId,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyCategory.getName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyCategory.getDescription()),
				taxonomyVocabularyId, null,
				ServiceContextUtil.createServiceContext(
					groupId, taxonomyCategory.getViewableByAsString())));
	}

	private Page<TaxonomyCategory> _getCategoriesPage(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQueryUnsafeConsumer, filter, AssetCategory.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ASSET_CATEGORY_ID),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> _toTaxonomyCategory(
				_assetCategoryService.getCategory(
					GetterUtil.getLong(document.get(Field.ASSET_CATEGORY_ID)))),
			sorts);
	}

	private ParentTaxonomyCategory _toParentCategory(
		AssetCategory parentCategory) {

		return new ParentTaxonomyCategory() {
			{
				id = parentCategory.getCategoryId();
				name = parentCategory.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private TaxonomyCategory _toTaxonomyCategory(AssetCategory assetCategory)
		throws Exception {

		return new TaxonomyCategory() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					assetCategory.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(assetCategory.getUserId()));
				dateCreated = assetCategory.getCreateDate();
				dateModified = assetCategory.getModifiedDate();
				description = assetCategory.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = assetCategory.getCategoryId();
				name = assetCategory.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				numberOfTaxonomyCategories =
					_assetCategoryService.getChildCategoriesCount(
						assetCategory.getCategoryId());
				parentTaxonomyVocabulary = new ParentTaxonomyVocabulary() {
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

				setParentTaxonomyCategory(
					() -> {
						if (assetCategory.getParentCategory() == null) {
							return null;
						}

						return _toParentCategory(
							assetCategory.getParentCategory());
					});
			}
		};
	}

	private static final EntityModel _entityModel = new CategoryEntityModel();

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Context
	private HttpServletResponse _httpServletResponse;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}