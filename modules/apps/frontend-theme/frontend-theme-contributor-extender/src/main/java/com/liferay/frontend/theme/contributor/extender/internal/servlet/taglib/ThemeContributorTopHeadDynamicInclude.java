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

import com.liferay.frontend.theme.contributor.extender.internal.BundleWebResources;
import com.liferay.petra.string.StringBundler;
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
@Component(service = DynamicInclude.class)
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

		if (!_portal.isCDNDynamicResourcesEnabled(
				themeDisplay.getCompanyId())) {

			portalCDNURL = themeDisplay.getPortalURL();
		}

		ResourceURLsBag resourceURLsBag = _getResourceURLsBag();

		if (resourceURLsBag._cssResourceURLs.length > 0) {
			if (themeDisplay.isThemeCssFastLoad()) {
				_renderComboCSS(
					themeLastModified, httpServletRequest, portalCDNURL,
					httpServletResponse.getWriter());
			}
			else {
				_renderSimpleCSS(
					themeLastModified, httpServletRequest, portalCDNURL,
					httpServletResponse.getWriter(),
					resourceURLsBag._cssResourceURLs);
			}
		}

		if (resourceURLsBag._jsResourceURLs.length == 0) {
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
				httpServletResponse.getWriter(),
				resourceURLsBag._jsResourceURLs);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_comboContextPath = _portal.getPathContext() + "/combo";
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

			_resourceURLsBag = null;
		}
	}

	protected void removeBundleWebResources(
		ServiceReference<BundleWebResources>
			bundleWebResourcesServiceReference) {

		synchronized (_bundleWebResourcesServiceReferences) {
			_bundleWebResourcesServiceReferences.remove(
				bundleWebResourcesServiceReference);

			_resourceURLsBag = null;
		}
	}

	private ResourceURLsBag _getResourceURLsBag() {
		ResourceURLsBag resourceURLsBag = _resourceURLsBag;

		if (resourceURLsBag != null) {
			return resourceURLsBag;
		}

		synchronized (_bundleWebResourcesServiceReferences) {
			if (_resourceURLsBag != null) {
				return _resourceURLsBag;
			}

			_resourceURLsBag = _rebuild();

			return _resourceURLsBag;
		}
	}

	private ResourceURLsBag _rebuild() {
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

		StringBundler sb1 = new StringBundler((cssResourceURLs.size() * 2) + 1);

		for (String cssResourceURL : cssResourceURLs) {
			sb1.append("&");
			sb1.append(cssResourceURL);
		}

		sb1.append("\" rel=\"stylesheet\" type = \"text/css\" />\n");

		StringBundler sb2 = new StringBundler((jsResourceURLs.size() * 2) + 1);

		for (String jsResourceURL : jsResourceURLs) {
			sb2.append("&");
			sb2.append(jsResourceURL);
		}

		sb2.append("\" type = \"text/javascript\"></script>\n");

		return new ResourceURLsBag(
			cssResourceURLs.toArray(new String[0]),
			jsResourceURLs.toArray(new String[0]), sb1.toString(),
			sb2.toString());
	}

	private void _renderComboCSS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter) {

		printWriter.write("<link data-senna-track=\"permanent\" href=\"");

		String staticResourceURL = _portal.getStaticResourceURL(
			httpServletRequest, _comboContextPath, "minifierType=css",
			themeLastModified);

		printWriter.write(portalURL + staticResourceURL);

		ResourceURLsBag resourceURLsBag = _getResourceURLsBag();

		printWriter.write(resourceURLsBag._mergedCSSResourceURLs);
	}

	private void _renderComboJS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter) {

		printWriter.write("<script data-senna-track=\"permanent\" src=\"");

		String staticResourceURL = _portal.getStaticResourceURL(
			httpServletRequest, _comboContextPath, "minifierType=js",
			themeLastModified);

		printWriter.write(portalURL + staticResourceURL);

		ResourceURLsBag resourceURLsBag = _getResourceURLsBag();

		printWriter.write(resourceURLsBag._mergedJSResourceURLs);
	}

	private void _renderSimpleCSS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter, String[] resourceURLs) {

		for (String resourceURL : resourceURLs) {
			printWriter.write("<link data-senna-track=\"permanent\" href=\"");
			printWriter.write(
				_portal.getStaticResourceURL(
					httpServletRequest,
					StringBundler.concat(
						portalURL, _portal.getPathProxy(), resourceURL),
					themeLastModified));
			printWriter.write("\" rel=\"stylesheet\" type = \"text/css\" />\n");
		}
	}

	private void _renderSimpleJS(
		long themeLastModified, HttpServletRequest httpServletRequest,
		String portalURL, PrintWriter printWriter, String[] resourceURLs) {

		for (String resourceURL : resourceURLs) {
			printWriter.write("<script data-senna-track=\"permanent\" src=\"");
			printWriter.write(
				_portal.getStaticResourceURL(
					httpServletRequest,
					StringBundler.concat(
						portalURL, _portal.getPathProxy(), resourceURL),
					themeLastModified));
			printWriter.write("\" type = \"text/javascript\"></script>\n");
		}
	}

	private BundleContext _bundleContext;
	private final Collection<ServiceReference<BundleWebResources>>
		_bundleWebResourcesServiceReferences = new TreeSet<>();
	private String _comboContextPath;

	@Reference
	private Portal _portal;

	private volatile ResourceURLsBag _resourceURLsBag;

	private static class ResourceURLsBag {

		public ResourceURLsBag(
			String[] cssResourceURLs, String[] jsResourceURLs,
			String mergedCSSResourceURLs, String mergedJSResourceURLs) {

			_cssResourceURLs = cssResourceURLs;
			_jsResourceURLs = jsResourceURLs;
			_mergedCSSResourceURLs = mergedCSSResourceURLs;
			_mergedJSResourceURLs = mergedJSResourceURLs;
		}

		private final String[] _cssResourceURLs;
		private final String[] _jsResourceURLs;
		private final String _mergedCSSResourceURLs;
		private final String _mergedJSResourceURLs;

	}

}