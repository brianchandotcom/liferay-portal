/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.filter;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFileURIsRegistry;
import com.liferay.frontend.js.web.internal.hashed.files.request.helper.BaseRequestHelper;
import com.liferay.frontend.js.web.internal.hashed.files.request.helper.LanguageRequestHelperImpl;
import com.liferay.frontend.js.web.internal.hashed.files.request.helper.StaticFileRequestHelperImpl;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.esm.FrontendESMUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"before-filter=Language Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Frontend JS Web Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class FrontendJsWebFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return FrontendESMUtil.isInternalESMRequest(
			httpServletRequest.getRequestURI());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ServletContext.class, null,
			(serviceReference, emitter) -> {
				ServletContext servletContext = bundleContext.getService(
					serviceReference);

				try {
					emitter.emit(servletContext.getContextPath());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});

		_baseRequestHelpers.add(
			new LanguageRequestHelperImpl(
				_configurationProvider, _jsonFactory, _language, _portal,
				_serviceTrackerMap));

		_baseRequestHelpers.add(
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _hashedFileURIsRegistry,
				86400, "es-modules-max-age", _portal, false,
				"send-no-cache-for-es-modules", _serviceTrackerMap));

		_baseRequestHelpers.add(
			new StaticFileRequestHelperImpl(
				ContentTypes.APPLICATION_JSON, ".map", _hashedFileURIsRegistry,
				86400, "es-modules-max-age", _portal, false,
				"send-no-cache-for-es-modules", _serviceTrackerMap));

		_baseRequestHelpers.add(
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_CSS, ".css", _hashedFileURIsRegistry, 86400,
				"css-style-sheets-max-age", _portal, false,
				"send-no-cache-for-css-style-sheets", _serviceTrackerMap));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_serviceTrackerMap = null;

		_baseRequestHelpers.clear();
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		for (BaseRequestHelper<?> baseRequestHelper : _baseRequestHelpers) {
			if (baseRequestHelper.isAcceptableRequest(httpServletRequest)) {
				baseRequestHelper.process(
					httpServletRequest, httpServletResponse);

				return;
			}
		}

		super.processFilter(
			httpServletRequest, httpServletResponse, filterChain);
	}

	private final List<BaseRequestHelper> _baseRequestHelpers =
		new ArrayList<>();

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private HashedFileURIsRegistry _hashedFileURIsRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}