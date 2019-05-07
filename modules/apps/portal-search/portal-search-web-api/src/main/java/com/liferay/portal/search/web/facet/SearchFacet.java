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

package com.liferay.portal.search.web.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

import java.io.IOException;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public interface SearchFacet {

	public String getClassName();

	public JSONObject getData();

	public FacetConfiguration getDefaultConfiguration(long companyId);

	public Facet getFacet();

	public String getFacetClassName();

	public FacetConfiguration getFacetConfiguration();

	public String getFieldName();

	public String getId();

	public JSONObject getJSONData(ActionRequest actionRequest);

	public String getLabel();

	public String getOrder();

	public String getTitle();

	public double getWeight();

	public abstract void includeConfiguration(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	public abstract void includeView(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	public void init(long companyId, String searchConfiguration)
		throws Exception;

	public void init(
			long companyId, String searchConfiguration,
			SearchContext searchContext)
		throws Exception;

	public boolean isStatic();

}