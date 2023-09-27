/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolverServiceFactory implements ServiceFactory<NPMResolver> {

	public NPMResolverServiceFactory(
		JSONFactory jsonFactory, NPMRegistry npmRegistry) {

		_jsonFactory = jsonFactory;
		_npmRegistry = npmRegistry;
	}

	@Override
	public NPMResolver getService(
		Bundle bundle, ServiceRegistration<NPMResolver> serviceRegistration) {

		URL packageURL = bundle.getEntry("META-INF/resources/package.json");

		if (packageURL == null) {
			return new InvalidNPMResolverImpl(bundle);
		}

		JSONObject packageJSONObject = _createJSONObject(packageURL);

		URL manifestURL = bundle.getEntry("META-INF/resources/manifest.json");

		if (manifestURL == null) {
			return new UnsupportedNPMResolverImpl(bundle);
		}

		JSONObject manifestJSONObject = _createJSONObject(manifestURL);

		JSONObject packagesJSONObject = manifestJSONObject.getJSONObject(
			"packages");

		if (packagesJSONObject == null) {
			return new UnsupportedNPMResolverImpl(bundle);
		}

		return new NPMResolverImpl(
			bundle, _npmRegistry, packageJSONObject, packagesJSONObject);
	}

	@Override
	public void ungetService(
		Bundle bundle, ServiceRegistration<NPMResolver> serviceRegistration,
		NPMResolver npmResolver) {
	}

	private JSONObject _createJSONObject(URL url) {
		try {
			return _jsonFactory.createJSONObject(
				StringUtil.read(url.openStream()));
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException("Unable to read " + url, exception);
		}
	}

	private final JSONFactory _jsonFactory;
	private final NPMRegistry _npmRegistry;

}