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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetDisplayContext {

	public ScopeSearchFacetDisplayContext(
		Facet facet, String fieldParam, Locale locale, int countThreshold,
		int maxTerms, boolean showCounts, GroupLocalService groupLocalService) {

		_facet = facet;
		_fieldParam = fieldParam;
		_locale = locale;
		_countThreshold = countThreshold;
		_maxTerms = maxTerms;
		_showCounts = showCounts;
		_groupLocalService = groupLocalService;

		_groupId = GetterUtil.getLong(fieldParam);
	}

	public String getFieldParamInputName() {
		return _facet.getFieldId();
	}

	public String getFieldParamInputValue() {
		return _fieldParam;
	}

	public List<ScopeSearchFacetTermDisplayContext> getTermDisplayContexts() {
		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (termCollectors.isEmpty()) {
			return getEmptySearchResultTermDisplayContexts();
		}

		List<ScopeSearchFacetTermDisplayContext>
			scopeSearchFacetTermDisplayContexts = new ArrayList<>(
				termCollectors.size());

		int limit = termCollectors.size();

		if ((_maxTerms > 0) && (limit > _maxTerms)) {
			limit = _maxTerms;
		}

		for (int i = 0; i < limit; i++) {
			TermCollector termCollector = termCollectors.get(i);

			long groupId = GetterUtil.getLong(termCollector.getTerm());

			ScopeSearchFacetTermDisplayContext
				scopeSearchFacetTermDisplayContext = getTermDisplayContext(
					groupId, termCollector);

			if (scopeSearchFacetTermDisplayContext != null) {
				scopeSearchFacetTermDisplayContexts.add(
					scopeSearchFacetTermDisplayContext);
			}
		}

		return scopeSearchFacetTermDisplayContexts;
	}

	public boolean isNothingSelected() {
		if (_fieldParam.equals("0")) {
			return true;
		}

		return false;
	}

	public boolean isRenderNothing() {
		if (_groupId != 0) {
			return false;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (!termCollectors.isEmpty()) {
			return false;
		}

		return true;
	}

	protected List<ScopeSearchFacetTermDisplayContext>
		getEmptySearchResultTermDisplayContexts() {

		ScopeSearchFacetTermDisplayContext scopeSearchFacetTermDisplayContext =
			getTermDisplayContext(_groupId, 0, true);

		if (scopeSearchFacetTermDisplayContext == null) {
			return Collections.emptyList();
		}

		return Collections.singletonList(scopeSearchFacetTermDisplayContext);
	}

	protected ScopeSearchFacetTermDisplayContext getTermDisplayContext(
		long groupId, int count, boolean selected) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return new ScopeSearchFacetTermDisplayContext(
			group, selected, count, _showCounts, _locale);
	}

	protected ScopeSearchFacetTermDisplayContext getTermDisplayContext(
		long groupId, TermCollector termCollector) {

		int count = termCollector.getFrequency();

		if ((_countThreshold > 0) && (_countThreshold > count)) {
			return null;
		}

		boolean selected = false;

		if (groupId == _groupId) {
			selected = true;
		}

		return getTermDisplayContext(groupId, count, selected);
	}

	private final int _countThreshold;
	private final Facet _facet;
	private final String _fieldParam;
	private final long _groupId;
	private final GroupLocalService _groupLocalService;
	private final Locale _locale;
	private final int _maxTerms;
	private final boolean _showCounts;

}