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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.util.JavaScriptBundleUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = DynamicInclude.class)
public class TopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isThemeJsFastLoad()) {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleComboURLs(
					httpServletRequest, httpServletResponse, _jsResourceURLs);
			}
			else {
				_renderBundleComboURLs(
					httpServletRequest, httpServletResponse,
					_allJsResourceURLs);
			}
		}
		else {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleURLs(
					httpServletRequest, httpServletResponse, _jsResourceURLs);
			}
			else {
				_renderBundleURLs(
					httpServletRequest, httpServletResponse,
					_allJsResourceURLs);
			}
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Reference(unbind = "-")
	public void setPortal(Portal portal) {
		_portal = portal;

		_rebuild();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_rebuild();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addPortalWebResources(
		PortalWebResources portalWebResources) {

		String resourceType = portalWebResources.getResourceType();

		if (resourceType.equals(PortalWebResourceConstants.RESOURCE_TYPE_JS)) {
			_portalWebResources = portalWebResources;

			_rebuild();
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addTopHeadResources(
		ServiceReference<TopHeadResources> topHeadResourcesServiceReference) {

		synchronized (_topHeadResourcesServiceReferences) {
			_topHeadResourcesServiceReferences.add(
				topHeadResourcesServiceReference);
		}

		_rebuild();
	}

	protected void removePortalWebResources(
		PortalWebResources portalWebResources) {

		String resourceType = portalWebResources.getResourceType();

		if (resourceType.equals(PortalWebResourceConstants.RESOURCE_TYPE_JS)) {
			_portalWebResources = null;

			_rebuild();
		}
	}

	protected void removeTopHeadResources(
		ServiceReference<TopHeadResources> topHeadResourcesServiceReference) {

		synchronized (_topHeadResourcesServiceReferences) {
			_topHeadResourcesServiceReferences.remove(
				topHeadResourcesServiceReference);
		}

		_rebuild();
	}

	private void _addPortalBundles(List<String> urls, String propsKey) {
		String[] fileNames = JavaScriptBundleUtil.getFileNames(propsKey);

		for (String fileName : fileNames) {
			urls.add(fileName);
		}
	}

	private synchronized void _rebuild() {
		if ((_bundleContext == null) || (_portal == null) ||
			(_portalWebResources == null)) {

			return;
		}

		_allJsResourceURLs.clear();

		_addPortalBundles(
			_allJsResourceURLs, PropsKeys.JAVASCRIPT_EVERYTHING_FILES);

		_jsResourceURLs.clear();

		_addPortalBundles(_jsResourceURLs, PropsKeys.JAVASCRIPT_BAREBONE_FILES);

		synchronized (_topHeadResourcesServiceReferences) {
			for (ServiceReference<TopHeadResources>
					topHeadResourcesServiceReference :
						_topHeadResourcesServiceReferences) {

				TopHeadResources topHeadResources = _bundleContext.getService(
					topHeadResourcesServiceReference);

				try {
					String servletContextPath =
						topHeadResources.getServletContextPath();

					for (String jsResourcePath :
							topHeadResources.getJsResourcePaths()) {

						String url = servletContextPath.concat(jsResourcePath);

						_allJsResourceURLs.add(url);
						_jsResourceURLs.add(url);
					}

					for (String jsResourcePath :
							topHeadResources.
								getAuthenticatedJsResourcePaths()) {

						_allJsResourceURLs.add(
							servletContextPath.concat(jsResourcePath));
					}
				}
				finally {
					_bundleContext.ungetService(
						topHeadResourcesServiceReference);
				}
			}
		}
	}

	private void _renderBundleComboURLs(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, List<String> urls)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler();

		long jsLastModified = -1;

		if (_portalWebResources != null) {
			jsLastModified = _portalWebResources.getLastModified();
		}

		String comboURL = _portal.getStaticResourceURL(
			httpServletRequest, "/combo", "minifierType=js", jsLastModified);

		for (String url : urls) {
			if ((sb.length() + url.length() + 1) >= 2000) {
				_renderScriptURL(printWriter, sb.toString());

				sb = new StringBundler();
			}

			if (sb.length() == 0) {
				AbsolutePortalURLBuilder absolutePortalURLBuilder =
					_absolutePortalURLBuilderFactory.
						getAbsolutePortalURLBuilder(httpServletRequest);

				sb.append(
					absolutePortalURLBuilder.forResource(
						comboURL
					).build());
			}

			sb.append(StringPool.AMPERSAND);
			sb.append(url);
		}

		if (sb.length() > 0) {
			_renderScriptURL(printWriter, sb.toString());
		}
	}

	private void _renderBundleURLs(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, List<String> urls)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (String url : urls) {
			AbsolutePortalURLBuilder absolutePortalURLBuilder =
				_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
					httpServletRequest);

			url = absolutePortalURLBuilder.forResource(
				url
			).build();

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

	private volatile List<String> _allJsResourceURLs = new ArrayList<>();
	private BundleContext _bundleContext;
	private volatile List<String> _jsResourceURLs = new ArrayList<>();
	private Portal _portal;
	private PortalWebResources _portalWebResources;
	private final Collection<ServiceReference<TopHeadResources>>
		_topHeadResourcesServiceReferences = new TreeSet<>();

}