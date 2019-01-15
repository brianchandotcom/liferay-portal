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

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = JSBundleTracker.class)
public class NPMResolverJSBundleTracker implements JSBundleTracker {

	@Override
	public void addedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		String npmResolvedPackageName = _getNpmResolvedPackageName(
			bundle, npmRegistry);

		if (npmResolvedPackageName == null) {
			return;
		}

		try {
			NPMResolvedPackageNameRegistrar npmResolvedPackageNameRegistrar =
				new NPMResolvedPackageNameRegistrar(
					_bundleContext, bundle, npmResolvedPackageName);

			_npmResolvedPackageNameRegistrarMap.put(
				bundle, npmResolvedPackageNameRegistrar);

			npmResolvedPackageNameRegistrar.open();
		}
		catch (InvalidSyntaxException ise) {
			_log.error(
				"Unable to track servlet context for bundle " +
					bundle.getBundleId(),
				ise);
		}
	}

	@Override
	public void removedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		NPMResolvedPackageNameRegistrar npmResolvedPackageNameRegistrar =
			_npmResolvedPackageNameRegistrarMap.remove(bundle);

		if (npmResolvedPackageNameRegistrar != null) {
			npmResolvedPackageNameRegistrar.close();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getNpmResolvedPackageName(
		Bundle bundle, NPMRegistry npmRegistry) {

		try {
			NPMResolver npmResolver = new NPMResolverImpl(
				bundle, _jsonFactory, npmRegistry);

			URL url = bundle.getResource("META-INF/resources/package.json");

			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			String name = jsonObject.getString("name");

			return npmResolver.resolveModuleName(name);
		}
		catch (Exception e) {
			_log.error(
				"Unable to read META-INF/resources/package.json in " +
					bundle.getSymbolicName(),
				e);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NPMResolverJSBundleTracker.class);

	private BundleContext _bundleContext;

	@Reference
	private JSONFactory _jsonFactory;

	private final Map<Bundle, NPMResolvedPackageNameRegistrar>
		_npmResolvedPackageNameRegistrarMap = new ConcurrentHashMap<>();

}