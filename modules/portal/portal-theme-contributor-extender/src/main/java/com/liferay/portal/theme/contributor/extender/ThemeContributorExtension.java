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

package com.liferay.portal.theme.contributor.extender;

import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael Bradford
 */
public class ThemeContributorExtension implements Extension {

	public ThemeContributorExtension(Bundle bundle, String contributorType) {
		_bundle = bundle;
		_contributorType = contributorType;

		_scanForResources();
	}

	@Override
	public void destroy() throws Exception {
		_contextPath = null;
		_contributorType = null;
		_cssResourcePaths = null;
		_jsResourcePaths = null;

		if (_dynamicIncludeServiceRegistration != null) {
			_dynamicIncludeServiceRegistration.unregister();
		}

		if (_portalWebResourcesServiceRegistration != null) {
			_portalWebResourcesServiceRegistration.unregister();
		}
	}

	@Override
	public void start() throws Exception {
		if (_contributorType == null) {
			return;
		}

		BundleContext bundleContext = _bundle.getBundleContext();

		Iterator<ServiceReference<ServletContext>> iterator = null;

		while ((iterator == null) || !iterator.hasNext()) {
			Collection<ServiceReference<ServletContext>> serviceReferences =
				bundleContext.getServiceReferences(
					ServletContext.class,
				"(osgi.web.symbolicname=" + _bundle.getSymbolicName() + ")");

			iterator = serviceReferences.iterator();
		}

		ServiceReference<ServletContext> reference = iterator.next();

		ServletContext themeContributorServletContext =
			bundleContext.getService(reference);

		if (themeContributorServletContext != null) {
			_contextPath = themeContributorServletContext.getContextPath();

			_portalWebResourcesServiceRegistration =
				bundleContext.registerService(
					PortalWebResources.class.getName(),
				new ThemeContributorPortalWebResources(
					themeContributorServletContext),
				null);
		}

		_dynamicIncludeServiceRegistration = bundleContext.registerService(
			DynamicInclude.class.getName(),
			new ThemeContributorDynamicInclude(), null);
	}

	private void _scanForResources() {
		Enumeration<URL> cssEntries = _bundle.findEntries(
			"/META-INF/resources", "*.css", true);
		Enumeration<URL> jsEntries = _bundle.findEntries(
			"/META-INF/resources", "*.js", true);

		if (cssEntries != null) {
			while (cssEntries.hasMoreElements()) {
				URL entry = cssEntries.nextElement();

				String path = entry.getFile();

				path = path.replace("/META-INF/resources", "");

				int lastIndexOfSlash = path.lastIndexOf('/');

				if (!StringPool.UNDERLINE.equals(
						path.charAt(lastIndexOfSlash + 1)) &&
					!path.endsWith("_rtl.css")) {

					_cssResourcePaths.add(path);
				}
			}
		}

		if (jsEntries != null) {
			while (jsEntries.hasMoreElements()) {
				URL entry = jsEntries.nextElement();

				String path = entry.getFile();

				_jsResourcePaths.add(path.replace("/META-INF/resources", ""));
			}
		}
	}

	private final Bundle _bundle;
	private String _contextPath;
	private String _contributorType;
	private Collection<String> _cssResourcePaths = new ArrayList<>();
	private ServiceRegistration<?> _dynamicIncludeServiceRegistration;
	private Collection<String> _jsResourcePaths = new ArrayList<>();
	private ServiceRegistration<?> _portalWebResourcesServiceRegistration;

	private class ThemeContributorDynamicInclude implements DynamicInclude {

		@Override
		public void include(
				HttpServletRequest request, HttpServletResponse response,
				String key)
			throws IOException {

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long bundleLastModified = _bundle.getLastModified();

			String basePath =
				themeDisplay.getPortalURL() + themeDisplay.getPathContext() +
				_contextPath;

			for (String resourcePath : _cssResourcePaths) {
				PrintWriter printWriter = response.getWriter();

				StringBundler sb = new StringBundler(3);

				String resourceUrl = PortalUtil.getStaticResourceURL(
					request, basePath + resourcePath, bundleLastModified);

				sb.append("<link href=\"");
				sb.append(resourceUrl);
				sb.append("\" rel=\"stylesheet\" type = \"text/css\" />");

				printWriter.println(sb.toString());
			}

			for (String resourcePath : _jsResourcePaths) {
				PrintWriter printWriter = response.getWriter();

				StringBundler sb = new StringBundler(3);

				String resourceUrl = PortalUtil.getStaticResourceURL(
					request, basePath + resourcePath, bundleLastModified);

				sb.append("<script src=\"");
				sb.append(resourceUrl);
				sb.append("\" type = \"text/javascript\"></script>");

				printWriter.println(sb.toString());
			}
		}

		@Override
		public void register(
			DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

			dynamicIncludeRegistry.register(
				"/html/common/themes/top_head.jsp#post");
		}

	}

	private class ThemeContributorPortalWebResources
		implements PortalWebResources {

		public ThemeContributorPortalWebResources(
			ServletContext servletContext) {

			_servletContext = servletContext;
		}

		@Override
		public String getContextPath() {
			return _servletContext.getContextPath();
		}

		@Override
		public long getLastModified() {
			return _bundle.getLastModified();
		}

		@Override
		public String getResourceType() {
			return PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR;
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		protected void setServletContext(ServletContext servletContext) {
			_servletContext = servletContext;
		}

		private volatile ServletContext _servletContext;

	}

}