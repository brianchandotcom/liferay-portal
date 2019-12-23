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

package com.liferay.frontend.js.web.internal;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true, service = PortalWebResources.class)
public class JavaScriptPortalWebResources implements PortalWebResources {

	@Override
	public String getContextPath() {
		return _servletContext.getContextPath();
	}

	@Override
	public long getLastModified() {
		return _lastModified.get();
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_JS;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		Bundle bundle = _bundleContext.getBundle();

		_lastModified = new AtomicLong(bundle.getLastModified());
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext.removeServiceListener(_resourceBundleServiceListener);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		target = ModuleServiceLifecycle.PORTAL_INITIALIZED
	)
	protected void setModuleServiceLifecycle(
			ModuleServiceLifecycle moduleServiceLifecycle)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			JavaScriptPortalWebResources.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.addServiceListener(
			_resourceBundleServiceListener,
			"(&(!(javax.portlet.name=*))(language.id=*)(objectClass=" +
				ResourceBundle.class.getName() + "))");
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.js.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void unsetModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		_bundleContext.removeServiceListener(_resourceBundleServiceListener);
	}

	private BundleContext _bundleContext;
	private AtomicLong _lastModified;
	private final ResourceBundleServiceListener _resourceBundleServiceListener =
		new ResourceBundleServiceListener();
	private ServletContext _servletContext;

	private class ResourceBundleServiceListener implements ServiceListener {

		@Override
		public void serviceChanged(ServiceEvent serviceEvent) {
			ServiceReference<?> serviceReference =
				serviceEvent.getServiceReference();

			Bundle bundle = serviceReference.getBundle();

			_lastModified.getAndUpdate(
				lastModified -> Math.max(
					lastModified, bundle.getLastModified()));
		}

	}

}