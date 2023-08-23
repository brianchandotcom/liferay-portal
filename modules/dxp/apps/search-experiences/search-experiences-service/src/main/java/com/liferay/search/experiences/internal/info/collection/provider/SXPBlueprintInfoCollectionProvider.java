/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.filter.TagsInfoFilter;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Tibor Lipusz
 * @author Gustavo Lima
 */
public class SXPBlueprintInfoCollectionProvider
	implements FilteredInfoCollectionProvider<AssetEntry>,
			   SingleFormVariationInfoCollectionProvider<AssetEntry> {

	public SXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		_assetHelper = assetHelper;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
		_sxpBlueprint = sxpBlueprint;
	}

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Pagination pagination = collectionQuery.getPagination();

			SearchRequestBuilder searchRequestBuilder =
				_getSearchRequestBuilder(collectionQuery, pagination);

			SearchResponse searchResponse = _searcher.search(
				searchRequestBuilder.build());

			return InfoPage.of(
				_assetHelper.getAssetEntries(searchResponse.getSearchHits()),
				collectionQuery.getPagination(), searchResponse.getTotalHits());
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getFormVariationKey() {
		return _sxpBlueprint.getExternalReferenceCode();
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			SingleFormVariationInfoCollectionProvider.super.getKey(), "_",
			_sxpBlueprint.getCompanyId(), "_",
			_sxpBlueprint.getExternalReferenceCode());
	}

	@Override
	public String getLabel(Locale locale) {
		return _sxpBlueprint.getTitle(locale);
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters() {
		return Arrays.asList(
			new CategoriesInfoFilter(), new KeywordsInfoFilter(),
			new TagsInfoFilter());
	}

	@Override
	public boolean isAvailable() {
		return FeatureFlagManagerUtil.isEnabled("LPS-129412");
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		CollectionQuery collectionQuery, Pagination pagination) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return _searchRequestBuilderFactory.builder(
		).companyId(
			serviceContext.getCompanyId()
		).from(
			pagination.getStart()
		).emptySearchEnabled(
			true
		).size(
			pagination.getEnd() - pagination.getStart()
		).withSearchContext(
			searchContext -> {
				CategoriesInfoFilter categoriesInfoFilter =
					collectionQuery.getInfoFilter(CategoriesInfoFilter.class);

				if ((categoriesInfoFilter != null) &&
					!ArrayUtil.isEmpty(categoriesInfoFilter.getCategoryIds())) {

					long[] categoryIds = ArrayUtil.append(
						categoriesInfoFilter.getCategoryIds());

					categoryIds = ArrayUtil.unique(categoryIds);

					searchContext.setAssetCategoryIds(categoryIds);
				}

				TagsInfoFilter tagsInfoFilter = collectionQuery.getInfoFilter(
					TagsInfoFilter.class);

				if ((tagsInfoFilter != null) &&
					!ArrayUtil.isEmpty(tagsInfoFilter.getTagNames())) {

					String[] tagNames = ArrayUtil.append(
						tagsInfoFilter.getTagNames());

					tagNames = ArrayUtil.unique(tagNames);

					searchContext.setAssetTagNames(tagNames);
				}

				searchContext.setAttribute(
					"search.experiences.blueprint.external.reference.code",
					_sxpBlueprint.getExternalReferenceCode());
				searchContext.setAttribute(
					"search.experiences.ip.address",
					serviceContext.getRemoteAddr());

				ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

				searchContext.setAttribute(
					"search.experiences.scope.group.id",
					themeDisplay.getScopeGroupId());

				KeywordsInfoFilter keywordsInfoFilter =
					collectionQuery.getInfoFilter(KeywordsInfoFilter.class);

				if (keywordsInfoFilter != null) {
					searchContext.setKeywords(keywordsInfoFilter.getKeywords());
				}

				searchContext.setLocale(serviceContext.getLocale());
				searchContext.setTimeZone(serviceContext.getTimeZone());
				searchContext.setUserId(serviceContext.getUserId());
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintInfoCollectionProvider.class);

	private final AssetHelper _assetHelper;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private final SXPBlueprint _sxpBlueprint;

}