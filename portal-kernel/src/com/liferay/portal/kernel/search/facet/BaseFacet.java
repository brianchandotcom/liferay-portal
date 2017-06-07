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

package com.liferay.portal.kernel.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.BaseFacetValueValidator;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Raymond Augé
 */
public abstract class BaseFacet implements Facet {

	public BaseFacet(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFacetFilterBooleanClause}
	 */
	@Deprecated
	@Override
	public BooleanClause<Query> getFacetClause() {
		return doGetFacetClause();
	}

	@Override
	public FacetCollector getFacetCollector() {
		return _facetCollector;
	}

	@Override
	public FacetConfiguration getFacetConfiguration() {
		return _facetConfiguration;
	}

	@Override
	public BooleanClause<Filter> getFacetFilterBooleanClause() {
		return doGetFacetFilterBooleanClause();
	}

	@Override
	public FacetValueValidator getFacetValueValidator() {
		if (_facetValueValidator == null) {
			_facetValueValidator = new BaseFacetValueValidator();
		}

		return _facetValueValidator;
	}

	@Override
	public String getFieldId() {
		return StringUtil.replace(
			getFieldName(), CharPool.SLASH, CharPool.UNDERLINE);
	}

	@Override
	public String getFieldName() {
		return _facetConfiguration.getFieldName();
	}

	@Override
	public SearchContext getSearchContext() {
		return _searchContext;
	}

	@Override
	public boolean isStatic() {
		return _facetConfiguration.isStatic();
	}

	@Override
	public void setFacetCollector(FacetCollector facetCollector) {
		_facetCollector = facetCollector;
	}

	@Override
	public void setFacetConfiguration(FacetConfiguration facetConfiguration) {
		_facetConfiguration = facetConfiguration;
	}

	@Override
	public void setFacetValueValidator(
		FacetValueValidator facetValueValidator) {

		_facetValueValidator = facetValueValidator;
	}

	@Override
	public void setFieldName(String fieldName) {
		_facetConfiguration.setFieldName(fieldName);
	}

	@Override
	public void setStatic(boolean isStatic) {
		_facetConfiguration.setStatic(isStatic);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #doGetFacetFilterBooleanClause}
	 */
	@Deprecated
	protected BooleanClause<Query> doGetFacetClause() {
		return null;
	}

	protected abstract BooleanClause<Filter> doGetFacetFilterBooleanClause();

	private FacetCollector _facetCollector = new NullFacetCollector();
	private FacetConfiguration _facetConfiguration = new FacetConfiguration();
	private FacetValueValidator _facetValueValidator;
	private final SearchContext _searchContext;

	private class NullFacetCollector implements FacetCollector {

		@Override
		public String getFieldName() {
			return _facetConfiguration.getFieldName();
		}

		@Override
		public TermCollector getTermCollector(String term) {
			return null;
		}

		@Override
		public List<TermCollector> getTermCollectors() {
			return Collections.<TermCollector>emptyList();
		}

	}

}