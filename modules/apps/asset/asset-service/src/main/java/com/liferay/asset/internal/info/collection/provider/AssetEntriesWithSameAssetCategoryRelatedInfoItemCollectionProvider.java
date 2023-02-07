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

package com.liferay.asset.internal.info.collection.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.info.localized.bundle.ResourceBundleInfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
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

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "item.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = RelatedInfoItemCollectionProvider.class
)
public class AssetEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider
	implements ConfigurableInfoCollectionProvider<AssetEntry>,
			   RelatedInfoItemCollectionProvider<AssetEntry, AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		if (!(relatedItem instanceof AssetEntry)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetEntry assetEntry = (AssetEntry)relatedItem;

		if (ArrayUtil.isEmpty(assetEntry.getCategoryIds())) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
			assetEntry, collectionQuery);

		try {
			SearchContext searchContext = _getSearchContext(assetEntry);

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
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				_getItemTypesInfoField()
			).descriptionInfoLocalizedValue(
				InfoLocalizedValue.localize(
					getClass(),
					"by-filtering,-you-can-narrow-down-the-results-that-" +
						"appear-on-the-page")
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
					SelectInfoFieldType.INLINE, true
				).attribute(
					SelectInfoFieldType.OPTIONS,
					ListUtil.fromArray(
						new SelectInfoFieldType.Option(
							true,
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
					CategoriesInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"specificAssetCategoryJSONObject"
				).attribute(
					CategoriesInfoFieldType.DEPENDENCY,
					new KeyValuePair(
						"assetCategoryRule", "specificAssetCategory")
				).attribute(
					CategoriesInfoFieldType.INFO_ITEM_SELECTOR_URL,
					_getItemSelectorURL()
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "category")
				).localizable(
					false
				).build()
			).descriptionInfoLocalizedValue(
				InfoLocalizedValue.localize(
					getClass(),
					"you-can-also-add-a-rule-for-more-accurate-results")
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "advanced-rule")
			).name(
				"advanced-rule"
			).build()
		).build();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "items-with-same-categories");
	}

	@Override
	public Class<?> getSourceItemClass() {
		return AssetEntry.class;
	}

	@Override
	public boolean isAvailable() {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166036"))) {
			return false;
		}

		return true;
	}

	private BooleanClause[] _getAssetEntryIdBooleanClause(
		AssetEntry assetEntry) {

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter assetEntryIdBooleanFilter = new BooleanFilter();

		TermsFilter assetEntryIdTermsFilter = new TermsFilter(
			Field.ASSET_ENTRY_ID);

		assetEntryIdTermsFilter.addValue(
			String.valueOf(assetEntry.getEntryId()));

		assetEntryIdBooleanFilter.add(
			assetEntryIdTermsFilter, BooleanClauseOccur.MUST_NOT);

		booleanQueryImpl.setPreBooleanFilter(assetEntryIdBooleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
	}

	private AssetEntryQuery _getAssetEntryQuery(
		AssetEntry assetEntry, CollectionQuery collectionQuery) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		assetEntryQuery.setAnyCategoryIds(assetEntry.getCategoryIds());
		assetEntryQuery.setClassNameIds(_getClassNameIds(collectionQuery));
		assetEntryQuery.setEnablePermissions(true);

		Pagination pagination = collectionQuery.getPagination();

		if (pagination != null) {
			assetEntryQuery.setEnd(pagination.getEnd());
		}

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

		return AssetRendererFactoryRegistryUtil.getIndexableClassNameIds(
			serviceContext.getCompanyId(), true);
	}

	private String _getItemSelectorURL() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return null;
		}

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(AssetCategory.class.getName());

		String namespace = StringPool.BLANK;

		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		if (liferayPortletResponse != null) {
			namespace = liferayPortletResponse.getNamespace();
		}

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
				namespace + "selectInfoItem", itemSelectorCriterion)
		).buildString();
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

	private SearchContext _getSearchContext(AssetEntry assetEntry) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		SearchContext searchContext = SearchContextFactory.getInstance(
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

		searchContext.setBooleanClauses(
			_getAssetEntryIdBooleanClause(assetEntry));

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider.
			class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}