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

package com.liferay.asset.categories.admin.web.internal.info.collection.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.info.localized.bundle.ResourceBundleInfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "item.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = RelatedInfoItemCollectionProvider.class
)
public class AssetEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider
	implements ConfigurableInfoCollectionProvider<AssetEntry>,
			   RelatedInfoItemCollectionProvider<AssetCategory, AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItemObject();

		if (!(relatedItem instanceof AssetCategory)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetCategory assetCategory = (AssetCategory)relatedItem;

		AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
			assetCategory, collectionQuery);

		try {
			SearchContext searchContext = _getSearchContext();

			Hits hits = _assetHelper.search(
				searchContext, assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			Long count = _assetHelper.searchCount(
				searchContext, assetEntryQuery);

			return InfoPage.of(
				_assetHelper.getAssetEntries(hits),
				collectionQuery.getPagination(), count.intValue());
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getCollectionItemClassName() {
		return AssetEntry.class.getName();
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166275"))) {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getItemTypesInfoField()
			).build();
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				_getItemTypesInfoField()
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "filter")
			).name(
				"filter"
			).build()
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				InfoField.builder(
				).infoFieldType(
					SelectInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"assetCategoryRule"
				).attribute(
					SelectInfoFieldType.OPTIONS,
					ListUtil.fromArray(
						new SelectInfoFieldType.Option(
							new ResourceBundleInfoLocalizedValue(
								getClass(), "not-selected"),
							StringPool.BLANK),
						new SelectInfoFieldType.Option(
							new ResourceBundleInfoLocalizedValue(
								getClass(),
								"any-category-of-the-same-vocabulary"),
							"anyAssetCategoryOfTheSameVocabulary"),
						new SelectInfoFieldType.Option(
							new ResourceBundleInfoLocalizedValue(
								getClass(), "a-specific-category"),
							"specificAssetCategory"))
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "and-contains")
				).localizable(
					true
				).build()
			).infoFieldSetEntry(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"specificAssetCategoryId"
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "category")
				).localizable(
					false
				).build()
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "advanced-rule")
			).name(
				"advanced-rule"
			).build()
		).build();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "items-with-this-category");
	}

	@Override
	public Class<?> getSourceItemClass() {
		return AssetCategory.class;
	}

	private AssetEntryQuery _getAssetEntryQuery(
		AssetCategory assetCategory, CollectionQuery collectionQuery) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setAllCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		String assetCategoryRule = StringPool.BLANK;
		long specificAssetCategoryId = 0;

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166275")) &&
			(configuration != null) &&
			!ArrayUtil.isEmpty(configuration.get("assetCategoryRule"))) {

			String[] assetCategoryRules = configuration.get(
				"assetCategoryRule");

			assetCategoryRule = assetCategoryRules[0];

			if (Objects.equals(assetCategoryRule, "specificAssetCategory") &&
				!ArrayUtil.isEmpty(
					configuration.get("specificAssetCategoryId"))) {

				String[] specificAssetCategoryIds = configuration.get(
					"specificAssetCategoryId");

				specificAssetCategoryId = GetterUtil.getLong(
					specificAssetCategoryIds[0]);
			}
		}

		if (Objects.equals(
				assetCategoryRule, "anyAssetCategoryOfTheSameVocabulary")) {

			assetEntryQuery.setAnyCategoryIds(
				ArrayUtil.filter(
					ArrayUtil.toArray(
						ListUtil.toArray(
							_assetCategoryLocalService.getVocabularyCategories(
								assetCategory.getVocabularyId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
							AssetCategory.CATEGORY_ID_ACCESSOR)),
					categoryId -> categoryId != assetCategory.getCategoryId()));
		}
		else if ((specificAssetCategoryId > 0) &&
				 (specificAssetCategoryId != assetCategory.getCategoryId())) {

			assetEntryQuery.setAllCategoryIds(
				new long[] {
					assetCategory.getCategoryId(), specificAssetCategoryId
				});
		}

		assetEntryQuery.setClassNameIds(_getClassNameIds(collectionQuery));
		assetEntryQuery.setEnablePermissions(true);

		Pagination pagination = collectionQuery.getPagination();

		if (pagination != null) {
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		assetEntryQuery.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});

		assetEntryQuery.setOrderByCol1(Field.MODIFIED_DATE);
		assetEntryQuery.setOrderByType1("DESC");

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
		}

		return assetEntryQuery;
	}

	private long[] _getClassNameIds(CollectionQuery collectionQuery) {
		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		if (MapUtil.isNotEmpty(configuration) &&
			ArrayUtil.isNotEmpty(configuration.get("item_types"))) {

			List<Long> classNameIds = new ArrayList<>();

			String[] itemTypes = configuration.get("item_types");

			for (String itemType : itemTypes) {
				if (Validator.isNotNull(itemType)) {
					classNameIds.add(_portal.getClassNameId(itemType));
				}
			}

			if (ListUtil.isNotEmpty(classNameIds)) {
				return ArrayUtil.toArray(classNameIds.toArray(new Long[0]));
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return ArrayUtil.filter(
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				serviceContext.getCompanyId(), true),
			classNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					_portal.getClassName(classNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});
	}

	private InfoField _getItemTypesInfoField() {
		List<SelectInfoFieldType.Option> options = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.filter(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				serviceContext.getCompanyId(), true),
			assetRendererFactory -> {
				if (!assetRendererFactory.isCategorizable()) {
					return false;
				}

				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					_portal.getClassName(
						assetRendererFactory.getClassNameId()));

				if (indexer == null) {
					return false;
				}

				return true;
			});

		Locale locale = serviceContext.getLocale();

		assetRendererFactories.sort(
			new AssetRendererFactoryTypeNameComparator(locale));

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			options.add(
				new SelectInfoFieldType.Option(
					new ModelResourceLocalizedValue(
						assetRendererFactory.getClassName()),
					assetRendererFactory.getClassName()));
		}

		InfoField.FinalStep finalStep = InfoField.builder(
		).infoFieldType(
			SelectInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"item_types"
		).attribute(
			SelectInfoFieldType.MULTIPLE, true
		).attribute(
			SelectInfoFieldType.OPTIONS, options
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "item-type")
		).localizable(
			true
		);

		return finalStep.build();
	}

	private SearchContext _getSearchContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return SearchContextFactory.getInstance(
			new long[0], new String[0],
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"head", true
			).put(
				"latest", true
			).build(),
			serviceContext.getCompanyId(), null, themeDisplay.getLayout(), null,
			serviceContext.getScopeGroupId(), null, serviceContext.getUserId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider.
			class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}