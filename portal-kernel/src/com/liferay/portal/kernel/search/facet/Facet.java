/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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