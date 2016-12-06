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

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetTermDisplayContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class UserSearchFacetDisplayBuilder {

	public UserSearchFacetDisplayContext build() throws Exception {
		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			new UserSearchFacetDisplayContext();

		userSearchFacetDisplayContext.setParamName(_paramName);
		userSearchFacetDisplayContext.setParamValue(_paramValue);

		boolean nothingSelected = false;

		if (Validator.isBlank(_paramValue)) {
			nothingSelected = true;
		}

		userSearchFacetDisplayContext.setNothingSelected(nothingSelected);

		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (nothingSelected && termCollectors.isEmpty()) {
			userSearchFacetDisplayContext.setRenderNothing(true);
		}

		userSearchFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts(termCollectors));

		return userSearchFacetDisplayContext;
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

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setParamName(String paramName) {
		_paramName = paramName;
	}

	public void setParamValue(String paramValue) {
		_paramValue = paramValue;
	}

	protected UserSearchFacetTermDisplayContext buildTermDisplayContext(
		String selection, TermCollector termCollector) {

		UserSearchFacetTermDisplayContext userSearchFacetTermDisplayContext =
			new UserSearchFacetTermDisplayContext();

		userSearchFacetTermDisplayContext.setFrequency(
			termCollector.getFrequency());
		userSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);

		String term = GetterUtil.getString(termCollector.getTerm());

		userSearchFacetTermDisplayContext.setSelected(selection.equals(term));
		userSearchFacetTermDisplayContext.setUserName(term);

		return userSearchFacetTermDisplayContext;
	}

	protected List<UserSearchFacetTermDisplayContext>
		buildTermDisplayContexts(List<TermCollector> termCollectors) {

		if (termCollectors.isEmpty()) {
			return getEmptyTermDisplayContexts();
		}

		List<UserSearchFacetTermDisplayContext>
			userSearchFacetTermDisplayContexts = new ArrayList<>(
				termCollectors.size());

		String userName = GetterUtil.getString(_paramValue);

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			if (((_maxTerms > 0) && (i >= _maxTerms)) ||
				((_frequencyThreshold > 0) &&
				 (_frequencyThreshold > termCollector.getFrequency()))) {

				break;
			}

			userSearchFacetTermDisplayContexts.add(
				buildTermDisplayContext(userName, termCollector));
		}

		return userSearchFacetTermDisplayContexts;
	}

	protected List<UserSearchFacetTermDisplayContext>
		getEmptyTermDisplayContexts() {

		if (Validator.isBlank(_paramValue)) {
			return Collections.emptyList();
		}

		UserSearchFacetTermDisplayContext userSearchFacetTermDisplayContext =
			new UserSearchFacetTermDisplayContext();

		userSearchFacetTermDisplayContext.setFrequency(0);
		userSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		userSearchFacetTermDisplayContext.setSelected(true);
		userSearchFacetTermDisplayContext.setUserName(_paramValue);

		return Collections.singletonList(userSearchFacetTermDisplayContext);
	}

	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private int _maxTerms;
	private String _paramName;
	private String _paramValue;

}