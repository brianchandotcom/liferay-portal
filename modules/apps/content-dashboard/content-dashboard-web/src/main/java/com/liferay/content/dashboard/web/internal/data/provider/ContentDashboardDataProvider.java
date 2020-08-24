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

package com.liferay.content.dashboard.web.internal.data.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.content.dashboard.web.internal.model.AssetCategoryMetric;
import com.liferay.content.dashboard.web.internal.model.AssetVocabularyMetric;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardSearchContextBuilder;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class ContentDashboardDataProvider {

	public ContentDashboardDataProvider(
		Aggregations aggregations,
		ContentDashboardSearchContextBuilder
			contentDashboardSearchContextBuilder,
		ContentDashboardSearchRequestBuilderFactory
			contentDashboardSearchRequestBuilderFactory,
		Locale locale, Searcher searcher) {

		_aggregations = aggregations;
		_locale = locale;
		_searcher = searcher;

		_searchRequestBuilder =
			contentDashboardSearchRequestBuilderFactory.builder(
				contentDashboardSearchContextBuilder.build());
	}

	public AssetVocabularyMetric getAssetVocabularyMetric(
		List<AssetVocabulary> assetVocabularies) {

		if (ListUtil.isEmpty(assetVocabularies)) {
			return AssetVocabularyMetric.empty();
		}

		if (assetVocabularies.size() == 1) {
			return _getAssetVocabularyMetric(assetVocabularies.get(0));
		}

		return _getAssetVocabularyMetric(
			assetVocabularies.get(0), assetVocabularies.get(1));
	}

	private Map<String, String> _getAssetCategoryTitlesMap(
		AssetVocabulary assetVocabulary, Locale locale) {

		List<AssetCategory> categories = assetVocabulary.getCategories();

		Stream<AssetCategory> stream = categories.stream();

		return stream.collect(
			Collectors.toMap(
				entry -> String.valueOf(entry.getCategoryId()),
				entry -> entry.getTitle(locale)));
	}

	private AssetVocabularyMetric _getAssetVocabularyMetric(
		AssetVocabulary assetVocabulary) {

		Map<String, String> assetCategoryTitlesMap = _getAssetCategoryTitlesMap(
			assetVocabulary, _locale);

		return _toAssetVocabularyMetric(
			assetCategoryTitlesMap, assetVocabulary,
			_getBuckets(
				_getTermsAggregation(
					assetVocabulary, assetCategoryTitlesMap.keySet())));
	}

	private AssetVocabularyMetric _getAssetVocabularyMetric(
		AssetVocabulary assetVocabulary, AssetVocabulary childAssetVocabulary) {

		Map<String, String> assetCategoryTitlesMap = _getAssetCategoryTitlesMap(
			assetVocabulary, _locale);

		Map<String, String> childAssetCategoryTitlesMap =
			_getAssetCategoryTitlesMap(childAssetVocabulary, _locale);

		TermsAggregation termsAggregation = _getTermsAggregation(
			assetVocabulary, assetCategoryTitlesMap.keySet());

		termsAggregation.addChildAggregation(
			_getTermsAggregation(
				childAssetVocabulary, childAssetCategoryTitlesMap.keySet()));

		return new AssetVocabularyMetric(
			String.valueOf(assetVocabulary.getVocabularyId()),
			assetVocabulary.getTitle(_locale),
			_toAssetCategoryMetrics(
				assetCategoryTitlesMap, _getBuckets(termsAggregation),
				childAssetCategoryTitlesMap, childAssetVocabulary));
	}

	private Collection<Bucket> _getBuckets(TermsAggregation termsAggregation) {
		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilder.addAggregation(
				termsAggregation
			).size(
				0
			).build());

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"categories");

		return termsAggregationResult.getBuckets();
	}

	private TermsAggregation _getTermsAggregation(
		AssetVocabulary assetVocabulary, Set<String> assetCategoryIds) {

		TermsAggregation termsAggregation = null;

		if ((assetVocabulary != null) &&
			(assetVocabulary.getVisibilityType() ==
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL)) {

			termsAggregation = _aggregations.terms(
				"categories", Field.ASSET_INTERNAL_CATEGORY_IDS);
		}
		else {
			termsAggregation = _aggregations.terms(
				"categories", Field.ASSET_CATEGORY_IDS);
		}

		termsAggregation.setIncludeExcludeClause(
			new IncludeExcludeClauseImpl(
				assetCategoryIds.toArray(new String[0]), new String[0]));

		return termsAggregation;
	}

	private List<AssetCategoryMetric> _toAssetCategoryMetrics(
		Map<String, String> assetCategoryTitlesMap, Collection<Bucket> buckets,
		Map<String, String> childAssetCategoryTitlesMap,
		AssetVocabulary childAssetVocabulary) {

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			bucket -> {
				TermsAggregationResult termsAggregationResult =
					(TermsAggregationResult)bucket.getChildAggregationResult(
						"categories");

				return new AssetCategoryMetric(
					_toAssetVocabularyMetric(
						childAssetCategoryTitlesMap, childAssetVocabulary,
						termsAggregationResult.getBuckets()),
					bucket.getKey(),
					assetCategoryTitlesMap.get(bucket.getKey()),
					bucket.getDocCount());
			}
		).collect(
			Collectors.toList()
		);
	}

	private AssetVocabularyMetric _toAssetVocabularyMetric(
		Map<String, String> assetCategoryTitlesMap,
		AssetVocabulary assetVocabulary, Collection<Bucket> buckets) {

		Stream<Bucket> stream = buckets.stream();

		return new AssetVocabularyMetric(
			String.valueOf(assetVocabulary.getVocabularyId()),
			assetVocabulary.getTitle(_locale),
			stream.map(
				bucket -> new AssetCategoryMetric(
					bucket.getKey(),
					assetCategoryTitlesMap.get(bucket.getKey()),
					bucket.getDocCount())
			).collect(
				Collectors.toList()
			));
	}

	private final Aggregations _aggregations;
	private final Locale _locale;
	private final Searcher _searcher;
	private final SearchRequestBuilder _searchRequestBuilder;

	private static class IncludeExcludeClauseImpl
		implements IncludeExcludeClause {

		public IncludeExcludeClauseImpl(
			String includeRegex, String excludeRegex) {

			_includeRegex = includeRegex;
			_excludeRegex = excludeRegex;
		}

		public IncludeExcludeClauseImpl(
			String[] includedValues, String[] excludedValues) {

			_includedValues = includedValues;
			_excludedValues = excludedValues;
		}

		@Override
		public String[] getExcludedValues() {
			return _excludedValues;
		}

		@Override
		public String getExcludeRegex() {
			return _excludeRegex;
		}

		@Override
		public String[] getIncludedValues() {
			return _includedValues;
		}

		@Override
		public String getIncludeRegex() {
			return _includeRegex;
		}

		private String[] _excludedValues;
		private String _excludeRegex;
		private String[] _includedValues;
		private String _includeRegex;

	}

}