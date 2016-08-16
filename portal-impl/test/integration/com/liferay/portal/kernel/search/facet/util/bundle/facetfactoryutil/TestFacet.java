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

package com.liferay.portal.kernel.search.facet.util.bundle.facetfactoryutil;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.search.filter.Filter;

/**
 * @author Peter Fellwock
 */
public class TestFacet implements Facet {

	public static final String FIELD_NAME = "FIELD_NAME";

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public BooleanClause<Query> getFacetClause() {
		return null;
	}

	@Override
	public FacetCollector getFacetCollector() {
		return null;
	}

	@Override
	public FacetConfiguration getFacetConfiguration() {
		return null;
	}

	@Override
	public BooleanClause<Filter> getFacetFilterBooleanClause() {
		return null;
	}

	@Override
	public FacetValueValidator getFacetValueValidator() {
		return null;
	}

	@Override
	public String getFieldId() {
		return null;
	}

	@Override
	public String getFieldName() {
		return FIELD_NAME;
	}

	@Override
	public SearchContext getSearchContext() {
		return null;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public void setFacetCollector(FacetCollector facetCollector) {
	}

	@Override
	public void setFacetConfiguration(FacetConfiguration facetConfiguration) {
	}

	@Override
	public void setFacetValueValidator(
		FacetValueValidator facetValueValidator) {
	}

	@Override
	public void setFieldName(String fieldName) {
	}

	@Override
	public void setStatic(boolean isStatic) {
	}

}