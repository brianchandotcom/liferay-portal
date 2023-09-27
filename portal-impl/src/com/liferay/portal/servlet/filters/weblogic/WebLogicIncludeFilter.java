/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.weblogic;

import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Minhchau Dang
 */
public class WebLogicIncludeFilter
	extends BasePortalFilter implements WrapHttpServletResponseFilter {

	@Override
	public HttpServletResponse getWrappedHttpServletResponse(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		WebLogicIncludeServletResponseFactory
			webLogicIncludeServletResponseFactory =
				_webLogicIncludeServletResponseFactory;

		if (webLogicIncludeServletResponseFactory != null) {
			return webLogicIncludeServletResponseFactory.create(
				httpServletResponse);
		}

		return httpServletResponse;
	}

	@Override
	public boolean isFilterEnabled() {
		if (_webLogicIncludeServletResponseFactory == null) {
			return false;
		}

		return true;
	}

	private static volatile WebLogicIncludeServletResponseFactory
		_webLogicIncludeServletResponseFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				WebLogicIncludeServletResponseFactory.class,
				WebLogicIncludeFilter.class,
				"_webLogicIncludeServletResponseFactory", false, true);

}