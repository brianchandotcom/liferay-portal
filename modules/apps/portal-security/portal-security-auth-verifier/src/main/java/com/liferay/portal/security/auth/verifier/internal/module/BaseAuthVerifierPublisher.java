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

package com.liferay.portal.security.auth.verifier.internal.module;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.auth.AuthVerifierPipeline;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseAuthVerifierPublisher {

	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		Boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));

		if ((enabled == null) || !enabled) {
			return;
		}

		AuthVerifier authVerifier = getAuthVerifierInstance();

		Class<?> clazz = authVerifier.getClass();

		String authVerifierPropertyName =
			AuthVerifierPipeline.getAuthVerifierPropertyName(clazz.getName());

		Dictionary<String, Object> authVerifierProperties = new Hashtable<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = translateKey(authVerifierPropertyName, entry.getKey());

			authVerifierProperties.put(key, entry.getValue());
		}

		_authVerifierRegistration = bundleContext.registerService(
			AuthVerifier.class, authVerifier, authVerifierProperties);
	}

	protected void deactivate() {
		if (_authVerifierRegistration != null) {
			_authVerifierRegistration.unregister();

			_authVerifierRegistration = null;
		}
	}

	protected abstract AuthVerifier getAuthVerifierInstance();

	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		deactivate();

		activate(bundleContext, properties);
	}

	protected String translateKey(String authVerifierPropertyName, String key) {
		if (key.equals("hostsAllowed")) {
			key = "hosts.allowed";
		}
		else if (key.equals("urlsExcludes")) {
			key = "urls.excludes";
		}
		else if (key.equals("urlsIncludes")) {
			key = "urls.includes";
		}

		return authVerifierPropertyName + key;
	}

	private ServiceRegistration<AuthVerifier> _authVerifierRegistration;

}