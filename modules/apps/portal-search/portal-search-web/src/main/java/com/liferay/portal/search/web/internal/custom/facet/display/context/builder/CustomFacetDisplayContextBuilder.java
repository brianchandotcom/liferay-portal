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

package com.liferay.portal.search.web.internal.custom.facet.display.context.builder;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wade Cao
 */
public class CustomFacetDisplayContextBuilder {

	public CustomFacetDisplayContextBuilder(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public CustomFacetDisplayContext build() throws ConfigurationException {
		boolean nothingSelected = isNothingSelected();

		List<TermCollector> termCollectors = getTermCollectors();

		boolean renderNothing = false;

		if (nothingSelected && termCollectors.isEmpty()) {
			renderNothing = true;
		}

		CustomFacetDisplayContext customFacetDisplayContext =
			new CustomFacetDisplayContext(_httpServletRequest);

		customFacetDisplayContext.setDisplayCaption(getDisplayCaption());
		customFacetDisplayContext.setNothingSelected(nothingSelected);
		customFacetDisplayContext.setPaginationStartParameterName(
			_paginationStartParameterName);
		customFacetDisplayContext.setParameterName(_parameterName);
		customFacetDisplayContext.setParameterValue(_getFirstParameterValue());
		customFacetDisplayContext.setParameterValues(_parameterValues);
		customFacetDisplayContext.setRenderNothing(renderNothing);
		customFacetDisplayContext.setBucketDisplayContexts(
			_buildBucketDisplayContexts(termCollectors));

		return customFacetDisplayContext;
	}

	public CustomFacetDisplayContextBuilder setCustomDisplayCaption(
		Optional<String> customDisplayCaptionOptional) {

		customDisplayCaptionOptional.ifPresent(
			customDisplayCaption ->
				_customDisplayCaption = customDisplayCaption);

		return this;
	}

	public CustomFacetDisplayContextBuilder setFacet(Facet facet) {
		_facet = facet;

		return this;
	}

	public CustomFacetDisplayContextBuilder setFieldToAggregate(
		String fieldToAggregate) {

		_fieldToAggregate = fieldToAggregate;

		return this;
	}

	public CustomFacetDisplayContextBuilder setFrequenciesVisible(
		boolean frequenciesVisible) {

		_frequenciesVisible = frequenciesVisible;

		return this;
	}

	public CustomFacetDisplayContextBuilder setFrequencyThreshold(
		int frequencyThreshold) {

		_frequencyThreshold = frequencyThreshold;

		return this;
	}

	public CustomFacetDisplayContextBuilder setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;

		return this;
	}

	public CustomFacetDisplayContextBuilder setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;

		return this;
	}

	public CustomFacetDisplayContextBuilder setParameterName(
		String parameterName) {

		_parameterName = parameterName;

		return this;
	}

	public CustomFacetDisplayContextBuilder setParameterValue(
		String parameterValue) {

		parameterValue = StringUtil.trim(
			Objects.requireNonNull(parameterValue));

		if (!parameterValue.isEmpty()) {
			_parameterValues = Collections.singletonList(parameterValue);
		}

		return this;
	}

	public CustomFacetDisplayContextBuilder setParameterValues(
		Optional<List<String>> parameterValuesOptional) {

		parameterValuesOptional.ifPresent(
			parameterValues -> _parameterValues = parameterValues);

		return this;
	}

	protected String getDisplayCaption() {
		Optional<String> optional1 = SearchStringUtil.maybe(
			_customDisplayCaption);

		Optional<String> optional2 = SearchStringUtil.maybe(
			optional1.orElse(_fieldToAggregate));

		return optional2.orElse("custom");
	}

	protected List<TermCollector> getTermCollectors() {
		if (_facet != null) {
			FacetCollector facetCollector = _facet.getFacetCollector();

			if (facetCollector != null) {
				return facetCollector.getTermCollectors();
			}
		}

		return Collections.<TermCollector>emptyList();
	}

	protected boolean isNothingSelected() {
		if (_parameterValues.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isSelected(String value) {
		if (_parameterValues.contains(value)) {
			return true;
		}

		return false;
	}

	private BucketDisplayContext _buildBucketDisplayContext(
		TermCollector termCollector) {

		String term = GetterUtil.getString(termCollector.getTerm());

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setFrequency(termCollector.getFrequency());
		bucketDisplayContext.setFrequencyVisible(_frequenciesVisible);
		bucketDisplayContext.setSelected(isSelected(term));
		bucketDisplayContext.setBucketText(term);
		bucketDisplayContext.setFilterValue(term);

		return bucketDisplayContext;
	}

	private List<BucketDisplayContext> _buildBucketDisplayContexts(
		List<TermCollector> termCollectors) {

		if (termCollectors.isEmpty()) {
			return _getEmptyBucketDisplayContexts();
		}

		List<BucketDisplayContext> bucketDisplayContexts = new ArrayList<>(
			termCollectors.size());

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			if (((_maxTerms > 0) && (i >= _maxTerms)) ||
				((_frequencyThreshold > 0) &&
				 (_frequencyThreshold > termCollector.getFrequency()))) {

				break;
			}

			bucketDisplayContexts.add(
				_buildBucketDisplayContext(termCollector));
		}

		return bucketDisplayContexts;
	}

	private List<BucketDisplayContext> _getEmptyBucketDisplayContexts() {
		if (_parameterValues.isEmpty()) {
			return Collections.emptyList();
		}

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setFrequency(0);
		bucketDisplayContext.setFrequencyVisible(_frequenciesVisible);
		bucketDisplayContext.setSelected(true);
		bucketDisplayContext.setBucketText(_parameterValues.get(0));
		bucketDisplayContext.setFilterValue(_parameterValues.get(0));

		return Collections.singletonList(bucketDisplayContext);
	}

	private String _getFirstParameterValue() {
		if (_parameterValues.isEmpty()) {
			return StringPool.BLANK;
		}

		return _parameterValues.get(0);
	}

	private String _customDisplayCaption;
	private Facet _facet;
	private String _fieldToAggregate;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private final HttpServletRequest _httpServletRequest;
	private int _maxTerms;
	private String _paginationStartParameterName;
	private String _parameterName;
	private List<String> _parameterValues = Collections.emptyList();

}