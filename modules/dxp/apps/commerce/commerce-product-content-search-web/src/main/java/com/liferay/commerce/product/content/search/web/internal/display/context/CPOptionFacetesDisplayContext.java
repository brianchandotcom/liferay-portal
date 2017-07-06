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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPOptionFacetesDisplayContext {

	public CPOptionFacetesDisplayContext(
		HttpServletRequest httpServletRequest, List<Facet> facets,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_httpServletRequest = httpServletRequest;
		_facets = facets;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_locale = httpServletRequest.getLocale();
	}

	public List<Facet> getFacets() {
		return _facets;
	}

	private final List<Facet> _facets;
	private final HttpServletRequest _httpServletRequest;
	private final Locale _locale;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;

}