/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.servlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.PortalRequestThreadLocal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Tavares
 */
@Component(
	property = {
		"servlet-context-name=",
		"servlet-filter-name=Object Definition REST Context Path Filter",
		"url-pattern=/o/c/*"
	},
	service = Filter.class
)
public class ObjectDefinitionRESTContextPathFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		PortalRequestThreadLocal.setHttpServletRequest(httpServletRequest);

		filterChain.doFilter(httpServletRequest, httpServletResponse);

		PortalRequestThreadLocal.setHttpServletRequest(null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionRESTContextPathFilter.class);

}