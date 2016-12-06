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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContext {

	public AssetTagsSearchFacetDisplayContext(
		Facet facet, String fieldParam, String displayStyle,
		int frequencyThreshold, int maxTerms, boolean showFrequencies) {

		_facet = facet;
		_fieldParam = fieldParam;
		_displayStyle = displayStyle;
		_frequencyThreshold = frequencyThreshold;
		_maxTerms = maxTerms;
		_showFrequencies = showFrequencies;
	}

	public String getFieldParamInputName() {
		return _facet.getFieldId();
	}

	public String getFieldParamInputValue() {
		return _fieldParam;
	}

	public List<AssetTagsSearchFacetTermDisplayContext>
		getTermDisplayContexts() {

		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors =
			Collections.<TermCollector>emptyList();

		if (facetCollector != null) {
			termCollectors = facetCollector.getTermCollectors();
		}

		if (termCollectors.isEmpty()) {
			return getEmptySearchResultTermDisplayContexts();
		}

		List<AssetTagsSearchFacetTermDisplayContext>
			assetTagsSearchFacetTermDisplayContexts = new ArrayList<>(
				termCollectors.size());

		int maxCount = 1;
		int minCount = 1;

		if (isCloudWithCount()) {

			// The cloud style may not list tags in the order of frequency,
			// so keep looking through the results until we reach the maximum
			// number of terms or we run out of terms.

			for (int i = 0, j = 0; i < termCollectors.size(); i++, j++) {
				if ((_maxTerms > 0) && (j >= _maxTerms)) {
					break;
				}

				TermCollector termCollector = termCollectors.get(i);

				int frequency = termCollector.getFrequency();

				if ((_frequencyThreshold > 0) &&
					(_frequencyThreshold > frequency)) {

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

		for (int i = 0, j = 0; i < termCollectors.size(); i++, j++) {
			if ((_maxTerms > 0) && (j >= _maxTerms)) {
				break;
			}

			TermCollector termCollector = termCollectors.get(i);

			int frequency = termCollector.getFrequency();

			if ((_frequencyThreshold > 0) &&
				(_frequencyThreshold > frequency)) {

				j--;

				continue;
			}

			AssetTagsSearchFacetTermDisplayContext
				assetTagsSearchFacetTermDisplayContext = getTermDisplayContext(
					termCollector, maxCount, minCount, multiplier);

			if (assetTagsSearchFacetTermDisplayContext != null) {
				assetTagsSearchFacetTermDisplayContexts.add(
					assetTagsSearchFacetTermDisplayContext);
			}
		}

		return assetTagsSearchFacetTermDisplayContexts;
	}

	public boolean isCloudWithCount() {
		if (_showFrequencies && _displayStyle.equals("cloud")) {
			return true;
		}

		return false;
	}

	public boolean isNothingSelected() {
		if (Validator.isNull(_fieldParam)) {
			return true;
		}

		return false;
	}

	public boolean isRenderNothing() {
		if (!Validator.isBlank(_fieldParam)) {
			return false;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (!termCollectors.isEmpty()) {
			return false;
		}

		return true;
	}

	protected List<AssetTagsSearchFacetTermDisplayContext>
		getEmptySearchResultTermDisplayContexts() {

		if (Validator.isNull(_fieldParam)) {
			return Collections.emptyList();
		}

		AssetTagsSearchFacetTermDisplayContext
			assetTagsSearchFacetTermDisplayContext =
				new AssetTagsSearchFacetTermDisplayContext(
					_fieldParam, 0, 0, true, _showFrequencies);

		return Collections.singletonList(
			assetTagsSearchFacetTermDisplayContext);
	}

	protected double getPopularity(
		int frequency, int minCount, int maxCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		popularity = 1 + (popularity * multiplier);

		return popularity;
	}

	protected AssetTagsSearchFacetTermDisplayContext getTermDisplayContext(
		TermCollector termCollector, int maxCount, int minCount,
		double multiplier) {

		int frequency = termCollector.getFrequency();

		boolean selected = false;

		String value = termCollector.getTerm();

		if (_fieldParam.equals(value)) {
			selected = true;
		}

		int popularity = (int)getPopularity(
			frequency, minCount, maxCount, multiplier);

		return new AssetTagsSearchFacetTermDisplayContext(
			value, frequency, popularity, selected, _showFrequencies);
	}

	private final String _displayStyle;
	private final Facet _facet;
	private final String _fieldParam;
	private final int _frequencyThreshold;
	private final int _maxTerms;
	private final boolean _showFrequencies;

}