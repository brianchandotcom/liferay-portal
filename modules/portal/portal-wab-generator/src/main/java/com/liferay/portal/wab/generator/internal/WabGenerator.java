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

package com.liferay.portal.wab.generator.internal;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.wab.generator.internal.artifact.WarArtifactUrlTransformer;
import com.liferay.portal.wab.generator.internal.handler.WabURLStreamHandlerService;

import java.util.Dictionary;

import javax.servlet.ServletContext;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(immediate = true)
public class WabGenerator {

	@Activate
	public void start(BundleContext bundleContext) throws Exception {
		registerURLStreamHandlerService(bundleContext);

		registerArtifactUrlTransformer(bundleContext);
	}

	@Deactivate
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	protected void registerArtifactUrlTransformer(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ArtifactUrlTransformer.class, new WarArtifactUrlTransformer(),
			null);
	}

	protected void registerURLStreamHandlerService(
		BundleContext bundleContext) {

		Bundle bundle = bundleContext.getBundle(0);

		Class<?> clazz = bundle.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"webbundle"});

		bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new WabURLStreamHandlerService(bundleContext, classLoader),
			properties);
	}

	/**
	 * This reference is held to force a dependency on the portal's complete
	 * startup.
	 */
	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void unsetModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		_servletContext = null;
	}

	private ServiceRegistration<ArtifactUrlTransformer> _serviceRegistration;
	private ServletContext _servletContext;

}