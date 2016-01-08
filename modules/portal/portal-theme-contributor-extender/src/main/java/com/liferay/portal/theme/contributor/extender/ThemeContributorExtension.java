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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.contributor.extender.ThemeContributorExtender.BundleWebResources;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 * @author Michael Bradford
 */
public class ThemeContributorExtension implements Extension {

	public ThemeContributorExtension(
		Bundle bundle, BundleWebResources bundleWebResources) {

		_bundle = bundle;
		_bundleWebResources = bundleWebResources;
	}

	@Override
	public void destroy() throws Exception {
		_serviceTracker.close();
	}

	@Override
	public void start() throws Exception {
		final BundleContext bundleContext = _bundle.getBundleContext();

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=" + ServletContext.class.getName() +
			")(osgi.web.symbolicname=" + _bundle.getSymbolicName() + "))");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new ServiceTrackerCustomizer
				<ServletContext, Collection<ServiceRegistration<?>>>() {

			@Override
			public Collection<ServiceRegistration<?>> addingService(
				ServiceReference<ServletContext> reference) {

				ServletContext servletContext = bundleContext.getService(
					reference);

				Collection<ServiceRegistration<?>> serviceRegistrations =
					new ArrayList<>();

				serviceRegistrations.add(
					bundleContext.registerService(
						PortalWebResources.class.getName(),
						new ThemeContributorPortalWebResources(servletContext),
					null));

				String contextPath = servletContext.getContextPath();

				serviceRegistrations.add(
					bundleContext.registerService(
						DynamicInclude.class.getName(),
						new ThemeContributorDynamicInclude(
							_bundle, contextPath, _bundleWebResources),
					null));

				return serviceRegistrations;
			}

			@Override
			public void modifiedService(
				ServiceReference<ServletContext> reference,
				Collection<ServiceRegistration<?>> service) {

				removedService(reference, service);

				addingService(reference);
			}

			@Override
			public void removedService(
				ServiceReference<ServletContext> reference,
				Collection<ServiceRegistration<?>> serviceRegistrations) {

				for (ServiceRegistration<?> serviceRegistration :
						serviceRegistrations) {

					serviceRegistration.unregister();
				}

				bundleContext.ungetService(reference);
			}

		});

		_serviceTracker.open();
	}

	private final Bundle _bundle;
	private BundleWebResources _bundleWebResources;
	private ServiceTracker<ServletContext, Collection<ServiceRegistration<?>>>
		_serviceTracker;

	private static class ThemeContributorDynamicInclude
		implements DynamicInclude {

		public ThemeContributorDynamicInclude(
			Bundle bundle, String contextPath,
			BundleWebResources bundleWebResources) {

			_bundle = bundle;
			_contextPath = contextPath;
			_bundleWebResources = bundleWebResources;
		}

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

			Collection<String> cssResourcePaths =
				_bundleWebResources.getCssResourcePaths();

			for (String resourcePath : cssResourcePaths) {
				PrintWriter printWriter = response.getWriter();

				StringBundler sb = new StringBundler(3);

				String resourceUrl = PortalUtil.getStaticResourceURL(
					request, basePath + resourcePath, bundleLastModified);

				sb.append("<link href=\"");
				sb.append(resourceUrl);
				sb.append("\" rel=\"stylesheet\" type = \"text/css\" />");

				printWriter.println(sb.toString());
			}

			Collection<String> jsResourcePaths =
				_bundleWebResources.getJsResourcePaths();

			for (String resourcePath : jsResourcePaths) {
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

		private final Bundle _bundle;
		private final BundleWebResources _bundleWebResources;
		private final String _contextPath;

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