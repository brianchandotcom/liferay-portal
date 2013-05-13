/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.BaseFacetValueValidator;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;

/**
 * @author Raymond Augé
 */
public abstract class BaseFacet implements Facet {

	public BaseFacet(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public BooleanClause getFacetClause() {
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
	public FacetValueValidator getFacetValueValidator() {
		if (_facetValueValidator == null) {
			_facetValueValidator = new BaseFacetValueValidator();
		}

		return _facetValueValidator;
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

	protected abstract BooleanClause doGetFacetClause();

	private FacetCollector _facetCollector;
	private FacetConfiguration _facetConfiguration = new FacetConfiguration();
	private FacetValueValidator _facetValueValidator;
	private SearchContext _searchContext;

}