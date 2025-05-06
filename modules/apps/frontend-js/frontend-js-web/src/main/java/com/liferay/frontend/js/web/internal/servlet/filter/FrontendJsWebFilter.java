/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.filter;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.frontend.js.web.internal.hashed.files.request.AbstractRequestHelper;
import com.liferay.frontend.js.web.internal.hashed.files.request.helper.LanguageRequestHelper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.esm.FrontendESMUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		_abstractRequestHelpers.add(
			new LanguageRequestHelper(
				_configurationProvider, _jsonFactory, _language, _portal,
				_serviceTrackerMap));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_serviceTrackerMap = null;

		_abstractRequestHelpers.clear();
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		for (AbstractRequestHelper<?> abstractRequestHelper :
				_abstractRequestHelpers) {

			if (abstractRequestHelper.isAcceptableRequest(httpServletRequest)) {
				abstractRequestHelper.process(
					httpServletRequest, httpServletResponse);

				return;
			}
		}

		super.processFilter(
			httpServletRequest, httpServletResponse, filterChain);
	}

	private final List<AbstractRequestHelper> _abstractRequestHelpers =
		new ArrayList<>();

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private HashedFilesRegistry _hashedFilesRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}