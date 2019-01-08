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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.internal.npm.flat.FlatNPMBundleProcessor;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolverHelperUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
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
@Component(immediate = true, service = ServiceTrackerCustomizer.class)
public class NPMResolverServletContextTracker
	implements ServiceTrackerCustomizer<ServletContext, ServletContext> {

	@Override
	public ServletContext addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		URL url = bundle.getResource(FlatNPMBundleProcessor.PACKAGE_JSON_PATH);

		if (url == null) {
			return null;
		}

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		String npmResolvedPackageName = _getNpmResolvedPackageName(bundle);

		if (npmResolvedPackageName != null) {
			servletContext.setAttribute(
				NPMResolverHelperUtil.NPM_RESOLVED_PACKAGE_NAME,
				npmResolvedPackageName);
		}

		_bundleContext.ungetService(serviceReference);

		return null;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServletContext servletContext) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServletContext servletContext) {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker(
			bundleContext, ServletContext.class, this);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	private String _getNpmResolvedPackageName(Bundle bundle) {
		try {
			NPMResolver npmResolver = new NPMResolverImpl(
				bundle, _jsonFactory, _npmRegistry);

			URL url = bundle.getResource(
				FlatNPMBundleProcessor.PACKAGE_JSON_PATH);

			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			String name = jsonObject.getString("name");

			return npmResolver.resolveModuleName(name);
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to read ", FlatNPMBundleProcessor.PACKAGE_JSON_PATH,
					" file from bundle ", bundle.getSymbolicName()),
				e);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NPMResolverServletContextTracker.class);

	private BundleContext _bundleContext;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMRegistry _npmRegistry;

	private ServiceTracker _serviceTracker;

}