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
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.search.filter.Filter;

/**
 * @author Raymond Augé
 */
public interface Facet {

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getFacetFilterBooleanClause}
	 */
	@Deprecated
	public BooleanClause<Query> getFacetClause();

	public FacetCollector getFacetCollector();

	public FacetConfiguration getFacetConfiguration();

	public BooleanClause<Filter> getFacetFilterBooleanClause();

	public FacetValueValidator getFacetValueValidator();

	public String getFieldId();

	public String getFieldName();

	public SearchContext getSearchContext();

	public boolean isStatic();

	public void setFacetCollector(FacetCollector facetCollector);

	public void setFacetConfiguration(FacetConfiguration facetConfiguration);

	public void setFacetValueValidator(FacetValueValidator facetValueValidator);

	public void setFieldName(String fieldName);

	public void setStatic(boolean isStatic);

}