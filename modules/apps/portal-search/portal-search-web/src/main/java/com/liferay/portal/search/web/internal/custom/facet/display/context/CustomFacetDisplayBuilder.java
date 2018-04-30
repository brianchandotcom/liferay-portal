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

package com.liferay.portal.search.web.internal.custom.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Wade Cao
 */
public class CustomFacetDisplayBuilder {

	public CustomFacetDisplayContext build() {
		boolean nothingSelected = isNothingSelected();

		List<TermCollector> termCollectors = getTermsCollectors();

		boolean renderNothing = false;

		if (nothingSelected && termCollectors.isEmpty()) {
			renderNothing = true;
		}

		CustomFacetDisplayContext customFacetDisplayContext =
			new CustomFacetDisplayContext();

		customFacetDisplayContext.setDisplayCaption(getDisplayCaption());
		customFacetDisplayContext.setNothingSelected(nothingSelected);
		customFacetDisplayContext.setParameterName(_parameterName);
		customFacetDisplayContext.setParameterValue(getFirstParameterValue());
		customFacetDisplayContext.setParameterValues(_parameterValues);
		customFacetDisplayContext.setRenderNothing(renderNothing);
		customFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts(termCollectors));

		return customFacetDisplayContext;
	}

	public void setCustomDisplayCaption(String customDisplayCaption) {
		_customDisplayCaption = customDisplayCaption;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFieldToAggregate(String fieldToAggregate) {
		_fieldToAggregate = fieldToAggregate;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		parameterValue = StringUtil.trim(
			Objects.requireNonNull(parameterValue));

		if (parameterValue.isEmpty()) {
			return;
		}

		_parameterValues = Collections.singletonList(parameterValue);
	}

	public void setParameterValues(List<String> parameterValues) {
		_parameterValues = parameterValues;
	}

	protected CustomFacetTermDisplayContext buildTermDisplayContext(
		TermCollector termCollector) {

		String term = GetterUtil.getString(termCollector.getTerm());

		CustomFacetTermDisplayContext customFacetTermDisplayContext =
			new CustomFacetTermDisplayContext();

		customFacetTermDisplayContext.setFrequency(
			termCollector.getFrequency());
		customFacetTermDisplayContext.setFrequencyVisible(_frequenciesVisible);
		customFacetTermDisplayContext.setSelected(isSelected(term));
		customFacetTermDisplayContext.setFieldName(term);

		return customFacetTermDisplayContext;
	}

	protected List<CustomFacetTermDisplayContext> buildTermDisplayContexts(
		List<TermCollector> termCollectors) {

		if (termCollectors.isEmpty()) {
			return getEmptyTermDisplayContexts();
		}

		List<CustomFacetTermDisplayContext> customFacetTermDisplayContexts =
			new ArrayList<>(termCollectors.size());

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			if (((_maxTerms > 0) && (i >= _maxTerms)) ||
				((_frequencyThreshold > 0) &&
				 (_frequencyThreshold > termCollector.getFrequency()))) {

				break;
			}

			customFacetTermDisplayContexts.add(
				buildTermDisplayContext(termCollector));
		}

		return customFacetTermDisplayContexts;
	}

	protected String getDisplayCaption() {
		Optional<String> optional1 = SearchStringUtil.maybe(
			_customDisplayCaption);

		Optional<String> optional2 = SearchStringUtil.maybe(
			optional1.orElse(_fieldToAggregate));

		return optional2.orElse("custom");
	}

	protected List<CustomFacetTermDisplayContext>
		getEmptyTermDisplayContexts() {

		if (_parameterValues.isEmpty()) {
			return Collections.emptyList();
		}

		CustomFacetTermDisplayContext customFacetTermDisplayContext =
			new CustomFacetTermDisplayContext();

		customFacetTermDisplayContext.setFrequency(0);
		customFacetTermDisplayContext.setFrequencyVisible(_frequenciesVisible);
		customFacetTermDisplayContext.setSelected(true);
		customFacetTermDisplayContext.setFieldName(_parameterValues.get(0));

		return Collections.singletonList(customFacetTermDisplayContext);
	}

	protected String getFirstParameterValue() {
		if (_parameterValues.isEmpty()) {
			return StringPool.BLANK;
		}

		return _parameterValues.get(0);
	}

	protected List<TermCollector> getTermsCollectors() {
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

	private String _customDisplayCaption;
	private Facet _facet;
	private String _fieldToAggregate;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private int _maxTerms;
	private String _parameterName;
	private List<String> _parameterValues = Collections.emptyList();

}