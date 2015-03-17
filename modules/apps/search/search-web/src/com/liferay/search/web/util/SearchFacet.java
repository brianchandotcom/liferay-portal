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

package com.liferay.search.web.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

/**
 * @author Eudaldo Alonso
 */
public interface SearchFacet {

	public String getClassName();

	public String getConfigurationView();

	public FacetConfiguration getDefaultConfiguration();

	public String getDisplayView();

	public Facet getFacet(
			SearchContext searchContext, String searchConfiguration)
		throws Exception;

	public FacetConfiguration getFacetConfiguration();

	public FacetConfiguration getFacetConfiguration(String searchConfiguration)
		throws JSONException;

	public String getTitle();

}