/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.servlet.filter;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Dante Wang
 */
@Component(
	property = {
		"before-filter=Session Id Filter", "dispatcher=REQUEST",
		"init-param.logLevel=ERROR", "servlet-context-name=",
		"servlet-filter-name=Search API Endpoint Compatibility Filter",
		"url-pattern=/o/portal-search-rest/*"
	},
	service = Filter.class
)
public class SearchAPIEndpointCompatibilityFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		RequestDispatcher requestDispatcher =
			httpServletRequest.getRequestDispatcher(
				StringUtil.replace(
					httpServletRequest.getRequestURI(), "portal-search-rest",
					"search"));

		requestDispatcher.forward(httpServletRequest, httpServletResponse);
	}

}