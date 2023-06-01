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

package com.liferay.frontend.js.top.head.extender.internal.servlet.taglib;

import com.liferay.frontend.js.top.head.extender.TopHeadResources;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.url.builder.ComboRequestAbsolutePortalURLBuilder;
import com.liferay.portal.util.JavaScriptBundleUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicInclude.class)
public class TopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceURLsBag resourceURLsBag = _getResourceURLsBag();

		if (themeDisplay.isThemeJsFastLoad()) {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleComboURLs(
					httpServletRequest, httpServletResponse,
					resourceURLsBag._jsResourceURLs);
			}
			else {
				_renderBundleComboURLs(
					httpServletRequest, httpServletResponse,
					resourceURLsBag._allJsResourceURLs);
			}
		}
		else {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleURLs(
					httpServletResponse, resourceURLsBag._jsResourceURLs);
			}
			else {
				_renderBundleURLs(
					httpServletResponse, resourceURLsBag._allJsResourceURLs);
			}
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_topHeadResourcesServiceTracker = ServiceTrackerFactory.open(
			bundleContext, TopHeadResources.class,
			new ServiceTrackerCustomizer<TopHeadResources, TopHeadResources>() {

				@Override
				public TopHeadResources addingService(
					ServiceReference<TopHeadResources> serviceReference) {

					synchronized (_topHeadResourcesServiceReferences) {
						_topHeadResourcesServiceReferences.add(
							serviceReference);

						_resourceURLsBag = null;
					}

					return bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<TopHeadResources> serviceReference,
					TopHeadResources topHeadResources) {
				}

				@Override
				public void removedService(
					ServiceReference<TopHeadResources> serviceReference,
					TopHeadResources topHeadResources) {

					synchronized (_topHeadResourcesServiceReferences) {
						_topHeadResourcesServiceReferences.remove(
							serviceReference);

						_resourceURLsBag = null;
					}

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_topHeadResourcesServiceTracker.close();
	}

	private void _addPortalBundles(List<String> urls, String propsKey) {
		Collections.addAll(
			urls, Arrays.asList(JavaScriptBundleUtil.getFileNames(propsKey)));
	}

	private ResourceURLsBag _getResourceURLsBag() {
		ResourceURLsBag resourceURLsBag = _resourceURLsBag;

		if (resourceURLsBag != null) {
			return resourceURLsBag;
		}

		synchronized (_topHeadResourcesServiceReferences) {
			if (_resourceURLsBag != null) {
				return _resourceURLsBag;
			}

			_resourceURLsBag = _rebuild();

			return _resourceURLsBag;
		}
	}

	private ResourceURLsBag _rebuild() {
		PortalWebResources portalWebResources =
			PortalWebResourcesUtil.getPortalWebResources(
				PortalWebResourceConstants.RESOURCE_TYPE_JS);

		if (portalWebResources == null) {
			return null;
		}

		ResourceURLsBag resourceURLsBag = new ResourceURLsBag();

		_addPortalBundles(
			resourceURLsBag._allJsResourceURLs,
			PropsKeys.JAVASCRIPT_EVERYTHING_FILES);

		_addPortalBundles(
			resourceURLsBag._jsResourceURLs,
			PropsKeys.JAVASCRIPT_BAREBONE_FILES);

		for (ServiceReference<TopHeadResources>
				topHeadResourcesServiceReference :
					_topHeadResourcesServiceReferences) {

			TopHeadResources topHeadResources = _bundleContext.getService(
				topHeadResourcesServiceReference);

			try {
				String bundleContextPath = _portal.getPathContext(
					topHeadResources.getServletContextPath());

				String proxyPath = _portal.getPathProxy();

				String unproxiedBundleContextPath = bundleContextPath.substring(
					proxyPath.length());

				String urlPrefix = proxyPath + unproxiedBundleContextPath;

				for (String jsResourcePath :
						topHeadResources.getJsResourcePaths()) {

					String url = urlPrefix + jsResourcePath;

					resourceURLsBag._allJsResourceURLs.add(url);
					resourceURLsBag._jsResourceURLs.add(url);
				}

				for (String jsResourcePath :
						topHeadResources.getAuthenticatedJsResourcePaths()) {

					resourceURLsBag._allJsResourceURLs.add(
						urlPrefix + jsResourcePath);
				}
			}
			finally {
				_bundleContext.ungetService(topHeadResourcesServiceReference);
			}
		}

		return resourceURLsBag;
	}

	private void _renderBundleComboURLs(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, List<String> urls)
		throws IOException {

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		ComboRequestAbsolutePortalURLBuilder
			comboRequestAbsolutePortalURLBuilder =
				absolutePortalURLBuilder.forComboRequest();

		comboRequestAbsolutePortalURLBuilder.setTimestamp(
			PortalWebResourcesUtil.getLastModified(
				PortalWebResourceConstants.RESOURCE_TYPE_JS));

		String comboURL = comboRequestAbsolutePortalURLBuilder.build();

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler();

		for (String url : urls) {
			if ((sb.length() + url.length() + 1) >= 2000) {
				_renderScriptURL(printWriter, sb.toString());

				sb = new StringBundler();
			}

			if (sb.length() == 0) {
				sb.append(comboURL);
			}

			sb.append(StringPool.AMPERSAND);
			sb.append(url);
		}

		if (sb.length() > 0) {
			_renderScriptURL(printWriter, sb.toString());
		}
	}

	private void _renderBundleURLs(
			HttpServletResponse httpServletResponse, List<String> urls)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (String url : urls) {
			_renderScriptURL(printWriter, url);
		}
	}

	private void _renderScriptURL(PrintWriter printWriter, String url) {
		printWriter.print("<script data-senna-track=\"permanent\" src=\"");
		printWriter.print(url);
		printWriter.println("\" type=\"text/javascript\"></script>");
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

	private volatile ResourceURLsBag _resourceURLsBag;
	private final Collection<ServiceReference<TopHeadResources>>
		_topHeadResourcesServiceReferences = new TreeSet<>();
	private ServiceTracker<TopHeadResources, TopHeadResources>
		_topHeadResourcesServiceTracker;

	private static class ResourceURLsBag {

		private final List<String> _allJsResourceURLs = new ArrayList<>();
		private final List<String> _jsResourceURLs = new ArrayList<>();

	}

}