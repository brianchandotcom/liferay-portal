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

package com.liferay.frontend.theme.contributor.extender.internal.servlet.taglib;

import com.liferay.frontend.theme.contributor.extender.BundleWebResources;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
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
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = DynamicInclude.class)
public class ThemeContributorTopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		long themeLastModified = PortalWebResourcesUtil.getLastModified(
			PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String portalCDNURL = themeDisplay.getCDNBaseURL();

		if (_cssResourceURLs.length > 0) {
			if (themeDisplay.isThemeCssFastLoad()) {
				_renderComboCSS(
					themeLastModified, httpServletRequest, portalCDNURL,
					httpServletResponse.getWriter());
			}
			else {
				_renderSimpleCSS(
					themeLastModified, httpServletRequest, portalCDNURL,
					httpServletResponse.getWriter(), _cssResourceURLs);
			}
		}

		if (_jsResourceURLs.length == 0) {
			return;
		}

		if (themeDisplay.isThemeJsFastLoad()) {
			_renderComboJS(
				themeLastModified, httpServletRequest, portalCDNURL,
				httpServletResponse.getWriter());
		}
		else {
			_renderSimpleJS(
				themeLastModified, httpServletRequest, portalCDNURL,
				httpServletResponse.getWriter(), _jsResourceURLs);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Reference(unbind = "-")
	public void setPortal(Portal portal) {
		String pathContext = portal.getPathContext();

		_comboContextPath = pathContext.concat("/combo");

		_portal = portal;
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
	protected void addBundleWebResources(
		ServiceReference<BundleWebResources>
			bundleWebResourcesServiceReference) {

		synchronized (_bundleWebResourcesServiceReferences) {
			_bundleWebResourcesServiceReferences.add(
				bundleWebResourcesServiceReference);

			_rebuild();
		}
	}

	protected void removeBundleWebResources(
		ServiceReference<BundleWebResources>
			bundleWebResourcesServiceReference) {

		synchronized (_bundleWebResourcesServiceReferences) {
			_bundleWebResourcesServiceReferences.remove(
				bundleWebResourcesServiceReference);

			_rebuild();
		}
	}

	private void _rebuild() {
		if (_bundleContext == null) {
			return;
		}

		Collection<String> cssResourceURLs = new ArrayList<>();
		Collection<String> jsResourceURLs = new ArrayList<>();

		for (ServiceReference<BundleWebResources>
				bundleWebResourcesServiceReference :
					_bundleWebResourcesServiceReferences) {

			BundleWebResources bundleWebResources = _bundleContext.getService(
				bundleWebResourcesServiceReference);

			try {
				String servletContextPath =
					bundleWebResources.getServletContextPath();

				for (String cssResourcePath :
						bundleWebResources.getCssResourcePaths()) {

					cssResourceURLs.add(
						servletContextPath.concat(cssResourcePath));
				}

				for (String jsResourcePath :
						bundleWebResources.getJsResourcePaths()) {

					jsResourceURLs.add(
						servletContextPath.concat(jsResourcePath));
				}
			}
			finally {
				_bundleContext.ungetService(bundleWebResourcesServiceReference);
			}
		}

		_cssResourceURLs = cssResourceURLs.toArray(new String[0]);

		StringBundler sb = new StringBundler(cssResourceURLs.size() * 2 + 1);

		for (String cssResourceURL : cssResourceURLs) {
			sb.append("&");
			sb.append(cssResourceURL);
		}

		sb.append("\" rel=\"stylesheet\" type = \"text/css\" />\n");

		_mergedCSSResourceURLs = sb.toString();

		_jsResourceURLs = jsResourceURLs.toArray(new String[0]);

		sb = new StringBundler(jsResourceURLs.size() * 2 + 1);

		for (String jsResourceURL : jsResourceURLs) {
			sb.append("&");
			sb.append(jsResourceURL);
		}

		sb.append("\" type = \"text/javascript\"></script>\n");

		_mergedJSResourceURLs = sb.toString();
	}

	private void _renderComboCSS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter) {

		printWriter.write("<link data-senna-track=\"permanent\" href=\"");

		String staticResourceURL = _portal.getStaticResourceURL(
			httpServletRequest, _comboContextPath, "minifierType=css",
			themeLastModified);

		printWriter.write(portalURL + staticResourceURL);

		printWriter.write(_mergedCSSResourceURLs);
	}

	private void _renderComboJS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter) {

		printWriter.write("<script data-senna-track=\"permanent\" src=\"");

		String staticResourceURL = _portal.getStaticResourceURL(
			httpServletRequest, _comboContextPath, "minifierType=js",
			themeLastModified);

		printWriter.write(portalURL + staticResourceURL);

		printWriter.write(_mergedJSResourceURLs);
	}

	private void _renderSimpleCSS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter, String[] resourceURLs) {

		for (String resourceURL : resourceURLs) {
			String staticResourceURL = _portal.getStaticResourceURL(
				httpServletRequest,
				portalURL.concat(
					_portal.getPathProxy()
				).concat(
					resourceURL
				),
				themeLastModified);

			printWriter.write("<link data-senna-track=\"permanent\" href=\"");
			printWriter.write(staticResourceURL);
			printWriter.write("\" rel=\"stylesheet\" type = \"text/css\" />\n");
		}
	}

	private void _renderSimpleJS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter, String[] resourceURLs) {

		for (String resourceURL : resourceURLs) {
			String staticResourceURL = _portal.getStaticResourceURL(
				httpServletRequest,
				portalURL.concat(
					_portal.getPathProxy()
				).concat(
					resourceURL
				),
				themeLastModified);

			printWriter.write("<script data-senna-track=\"permanent\" src=\"");
			printWriter.write(staticResourceURL);
			printWriter.write("\" type = \"text/javascript\"></script>\n");
		}
	}

	private BundleContext _bundleContext;
	private final Collection<ServiceReference<BundleWebResources>>
		_bundleWebResourcesServiceReferences = new TreeSet<>();
	private String _comboContextPath;
	private volatile String[] _cssResourceURLs = StringPool.EMPTY_ARRAY;
	private volatile String[] _jsResourceURLs = StringPool.EMPTY_ARRAY;
	private volatile String _mergedCSSResourceURLs;
	private volatile String _mergedJSResourceURLs;
	private Portal _portal;

}