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

package com.liferay.frontend.js.bundle.config.extender.internal;

import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.servlet.delegate.ServletContextDelegate;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 * @author Chema Balsas
 */
@Component(enabled = false, immediate = true, service = {})
public class JSBundleConfigPortalWebResources {

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			PortalWebResources portalWebResources =
				new InternalPortalWebResources(
					_jsBundleConfigServlet.getServletContext());

			_serviceRegistration = bundleContext.registerService(
				PortalWebResources.class, portalWebResources, null);
		}
		catch (NoClassDefFoundError ncdfe) {
			throw new RuntimeException(ncdfe);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	private JSBundleConfigServlet _jsBundleConfigServlet;

	@Reference
	private JSBundleConfigTracker _jsBundleConfigTracker;

	private ServiceRegistration<?> _serviceRegistration;

	private class InternalPortalWebResources implements PortalWebResources {

		@Override
		public String getContextPath() {
			return _servletContext.getContextPath();
		}

		@Override
		public long getLastModified() {
			return _jsBundleConfigTracker.getLastModified();
		}

		@Override
		public String getResourceType() {
			return PortalWebResourceConstants.RESOURCE_TYPE_JS_BUNDLE_CONFIG;
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		private InternalPortalWebResources(ServletContext servletContext) {
			_servletContext = ServletContextDelegate.create(servletContext);
		}

		private final ServletContext _servletContext;

	}

}