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

package com.liferay.portal.search.web.internal.facet.display.builder;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetTermDisplayContext;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lino Alves
 */
public class AssetCategoriesSearchFacetDisplayBuilder implements Serializable {

	public AssetCategoriesSearchFacetDisplayContext build() {
		_buckets = collectBuckets(_facet.getFacetCollector());

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext =
				new AssetCategoriesSearchFacetDisplayContext();

		assetCategoriesSearchFacetDisplayContext.setCloud(isCloud());
		assetCategoriesSearchFacetDisplayContext.setNothingSelected(
			isNothingSelected());
		assetCategoriesSearchFacetDisplayContext.setParameterName(
			_parameterName);
		assetCategoriesSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValueString());
		assetCategoriesSearchFacetDisplayContext.setParameterValues(
			getParameterValueStrings());
		assetCategoriesSearchFacetDisplayContext.setRenderNothing(
			isRenderNothing());
		assetCategoriesSearchFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts());

		return assetCategoriesSearchFacetDisplayContext;
	}

	public void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	public void setAssetCategoryPermissionChecker(
		AssetCategoryPermissionChecker assetCategoryPermissionChecker) {

		_assetCategoryPermissionChecker = assetCategoryPermissionChecker;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		setParameterValues(GetterUtil.getString(parameterValue));
	}

	public void setParameterValues(String... parameterValues) {
		Stream<String> parameterValuesStream = Stream.of(
			Objects.requireNonNull(parameterValues));

		_selectedCategoryIds = parameterValuesStream.map(
			GetterUtil::getLong
		).filter(
			categoryId -> categoryId > 0
		).collect(
			Collectors.toList()
		);
	}

	protected AssetCategoriesSearchFacetTermDisplayContext
		buildTermDisplayContext(
			AssetCategory assetCategory, int frequency, boolean selected,
			int popularity) {

		AssetCategoriesSearchFacetTermDisplayContext
			assetCategoriesSearchFacetTermDisplayContext =
				new AssetCategoriesSearchFacetTermDisplayContext();

		assetCategoriesSearchFacetTermDisplayContext.setAssetCategoryId(
			assetCategory.getCategoryId());
		assetCategoriesSearchFacetTermDisplayContext.setFrequency(frequency);
		assetCategoriesSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		assetCategoriesSearchFacetTermDisplayContext.setPopularity(popularity);
		assetCategoriesSearchFacetTermDisplayContext.setSelected(selected);
		assetCategoriesSearchFacetTermDisplayContext.setDisplayName(
			assetCategory.getTitle(_locale));

		return assetCategoriesSearchFacetTermDisplayContext;
	}

	protected List<AssetCategoriesSearchFacetTermDisplayContext>
		buildTermDisplayContexts() {

		if (_buckets.isEmpty()) {
			return getEmptyTermDisplayContexts();
		}

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts = new ArrayList<>(
				_buckets.size());

		int maxCount = 1;
		int minCount = 1;

		if (_frequenciesVisible && _displayStyle.equals("cloud")) {

			// The cloud style may not list tags in the order of frequency,
			// so keep looking through the results until we reach the maximum
			// number of terms or we run out of terms.

			for (int i = 0, j = 0; i < _buckets.size(); i++, j++) {
				if (j >= _maxTerms) {
					break;
				}

				Tuple tuple = _buckets.get(i);

				Integer frequency = (Integer)tuple.getObject(1);

				if (_frequencyThreshold > frequency) {
					j--;

					continue;
				}

				maxCount = Math.max(maxCount, frequency);
				minCount = Math.min(minCount, frequency);
			}
		}

		double multiplier = 1;

		if (maxCount != minCount) {
			multiplier = (double)5 / (maxCount - minCount);
		}

		for (int i = 0, j = 0; i < _buckets.size(); i++, j++) {
			if ((_maxTerms > 0) && (j >= _maxTerms)) {
				break;
			}

			Tuple tuple = _buckets.get(i);

			Integer frequency = (Integer)tuple.getObject(1);

			if (_frequencyThreshold > frequency) {
				j--;

				continue;
			}

			int popularity = (int)getPopularity(
				frequency, maxCount, minCount, multiplier);

			AssetCategory assetCategory = (AssetCategory)tuple.getObject(0);

			assetCategoriesSearchFacetTermDisplayContexts.add(
				buildTermDisplayContext(
					assetCategory, frequency,
					isSelected(assetCategory.getCategoryId()), popularity));
		}

		return assetCategoriesSearchFacetTermDisplayContexts;
	}

	protected List<Tuple> collectBuckets(FacetCollector facetCollector) {
		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<Tuple> buckets = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			long assetCategoryId = GetterUtil.getLong(termCollector.getTerm());

			if (assetCategoryId > 0) {
				AssetCategory assetCategory = _fetchAssetCategory(
					assetCategoryId);

				if (assetCategory != null) {
					buckets.add(
						new Tuple(assetCategory, termCollector.getFrequency()));
				}
			}
		}

		return buckets;
	}

	protected Optional<AssetCategoriesSearchFacetTermDisplayContext>
		getEmptyTermDisplayContext(long assetCategoryId) {

		Optional<AssetCategory> assetCategoryOptional = Optional.ofNullable(
			_fetchAssetCategory(assetCategoryId));

		return assetCategoryOptional.map(
			assetCategory -> buildTermDisplayContext(
				assetCategory, 0, true, 1));
	}

	protected List<AssetCategoriesSearchFacetTermDisplayContext>
		getEmptyTermDisplayContexts() {

		Stream<Long> categoryIdsStream = _selectedCategoryIds.stream();

		return categoryIdsStream.map(
			this::getEmptyTermDisplayContext
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	protected String getFirstParameterValueString() {
		if (_selectedCategoryIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return String.valueOf(_selectedCategoryIds.get(0));
	}

	protected List<String> getParameterValueStrings() {
		Stream<Long> categoryIdsStream = _selectedCategoryIds.stream();

		return categoryIdsStream.map(
			String::valueOf
		).collect(
			Collectors.toList()
		);
	}

	protected double getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		popularity = 1 + (popularity * multiplier);

		return popularity;
	}

	protected boolean isCloud() {
		if (_frequenciesVisible && _displayStyle.equals("cloud")) {
			return true;
		}

		return false;
	}

	protected boolean isNothingSelected() {
		if (_selectedCategoryIds.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isRenderNothing() {
		if (isNothingSelected() && _buckets.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isSelected(long categoryId) {
		if (_selectedCategoryIds.contains(categoryId)) {
			return true;
		}

		return false;
	}

	private AssetCategory _fetchAssetCategory(long assetCategoryId) {
		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(assetCategoryId);

		if ((assetCategory != null) &&
			_assetCategoryPermissionChecker.hasPermission(assetCategory)) {

			return assetCategory;
		}

		return null;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;
	private AssetCategoryPermissionChecker _assetCategoryPermissionChecker;
	private List<Tuple> _buckets;
	private String _displayStyle;
	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private Locale _locale;
	private int _maxTerms;
	private String _parameterName;
	private List<Long> _selectedCategoryIds = Collections.emptyList();

}