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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
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
@Component(immediate = true)
public class TopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isThemeJsFastLoad()) {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleComboURLs(request, response, _jsResourceURLs);
			}
			else {
				_renderBundleComboURLs(request, response, _allJsResourceURLs);
			}
		}
		else {
			if (themeDisplay.isThemeJsBarebone()) {
				_renderBundleURLs(request, response, _jsResourceURLs);
			}
			else {
				_renderBundleURLs(request, response, _allJsResourceURLs);
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
		String pathContext = portal.getPathContext();

		_comboContextPath = pathContext.concat("/combo");

		_portal = portal;

		_rebuild();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "removePortalWebResources"
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
		policy = ReferencePolicy.DYNAMIC, unbind = "removeTopHeadResources"
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
			urls.add(_jsContextPath + StringPool.SLASH + fileName);
		}
	}

	private synchronized void _rebuild() {
		if ((_portal == null) || (_portalWebResources == null)) {
			return;
		}

		_jsContextPath = _portal.getPathProxy();

		_jsContextPath = _jsContextPath.concat(
			_portalWebResources.getContextPath());

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
					String proxyPath = _portal.getPathProxy();

					String servletContextPath = proxyPath.concat(
						topHeadResources.getServletContextPath());

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
			HttpServletRequest request, HttpServletResponse response,
			List<String> urls)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		StringBundler sb = new StringBundler();

		long jsLastModified = -1;

		if (_portalWebResources != null) {
			jsLastModified = _portalWebResources.getLastModified();
		}

		String comboURL = _portal.getStaticResourceURL(
			request, _comboContextPath, "minifierType=js", jsLastModified);

		for (String url : urls) {
			if (sb.length() == 0) {
				sb.append("<script data-senna-track=\"permanent\" src=\"");

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				sb.append(themeDisplay.getCDNBaseURL() + comboURL);
			}

			sb.append(StringPool.AMPERSAND);
			sb.append(url);

			if (sb.length() >= 2048) {
				sb.append("\" type = \"text/javascript\"></script>");

				printWriter.println(sb.toString());

				sb = new StringBundler();
			}
		}

		if (sb.length() > 0) {
			sb.append("\" type = \"text/javascript\"></script>");

			printWriter.println(sb.toString());
		}
	}

	private void _renderBundleURLs(
			HttpServletRequest request, HttpServletResponse response,
			List<String> urls)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		for (String url : urls) {
			printWriter.print("<script data-senna-track=\"permanent\" src=\"");

			AbsolutePortalURLBuilder absolutePortalURLBuilder =
				_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
					request);

			printWriter.print(
				absolutePortalURLBuilder.forResource(
					url
				).build());

			printWriter.println("\" type=\"text/javascript\"></script>");
		}
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile List<String> _allJsResourceURLs = new ArrayList<>();
	private BundleContext _bundleContext;
	private String _comboContextPath;
	private String _jsContextPath = StringPool.BLANK;
	private volatile List<String> _jsResourceURLs = new ArrayList<>();
	private Portal _portal;
	private PortalWebResources _portalWebResources;
	private final Collection<ServiceReference<TopHeadResources>>
		_topHeadResourcesServiceReferences = new TreeSet<>();

}