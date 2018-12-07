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

package com.liferay.portal.apio.test.util;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Javier Gamarra
 */
public class AuthConfigurationTestUtil {

	public static void deployOAuthConfiguration(BundleContext bundleContext)
		throws Exception {

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		Configuration[] configurations = configurationAdmin.listConfigurations(
			bundleContext.createFilter(
				"(service.factoryPid=com.liferay.apio.architect.internal." +
					"application.ApioApplication)").toString());

		Dictionary<String, Object> dictionary =
			new Hashtable<String, Object>() {
				{
					put(
						"auth.verifier.auth.verifier." +
							"BasicAuthHeaderAuthVerifier.urls.includes",
						"*");
					put(
						"auth.verifier.auth.verifier.OAuth2RestAuthVerifier." +
							"urls.includes",
						"*");
					put("auth.verifier.guest.allowed", "true");
					put("oauth2.scopechecker.type", "none");
				}
			};

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				configuration.update(dictionary);
			}
		}
	}

}