/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.servlet.filter;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaime León
 */
@Component(
	property = {
		"servlet-context-name=",
		"servlet-filter-name=Mock Service Worker Context Filter",
		"url-pattern=/o/exportimport-web/mockServiceWorker.js"
	},
	service = Filter.class
)
public class MockServiceWorkerContextFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (FeatureFlagManagerUtil.isEnabled(
				_portal.getCompanyId(httpServletRequest), "LPD-11309")) {

			try {
				httpServletResponse.setHeader("Service-Worker-Allowed", "/");
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MockServiceWorkerContextFilter.class);

	@Reference
	private Portal _portal;

}