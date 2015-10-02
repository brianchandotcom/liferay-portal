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

package com.liferay.portal.security.auth.verifier.module;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.security.auth.verifier.basic.auth.header.module.configuration.BasicAuthHeaderAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.portal.session.module.configuration.PortalSessionAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.tunnel.module.configuration.TunnelAuthVerifierConfiguration;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class DefaultAuthVerifierConfiguration {

	@Activate
	public void activate() throws InvalidSyntaxException, IOException {
		initAuthVerifierConfiguration(
			BasicAuthHeaderAuthVerifierConfiguration.class.getName(), "enabled",
			Boolean.TRUE, "urlsExcludes", "/api/liferay/*", "urlsIncludes",
			"/api/*,/xmlrpc/*");

		initAuthVerifierConfiguration(
			PortalSessionAuthVerifierConfiguration.class.getName(), "enabled",
			Boolean.TRUE, "urlsIncludes",
			"/api/json/*,/api/jsonws/*,/c/portal/json_service/*");

		initAuthVerifierConfiguration(
			TunnelAuthVerifierConfiguration.class.getName(), "enabled",
			Boolean.TRUE, "hostsAllowed", "127.0.0.1,SERVER_IP", "urlsIncludes",
			"/api/liferay/do", "serviceAccessProfileName", "DEFAULT_USER");
	}

	protected void initAuthVerifierConfiguration(
			String factoryPid, Object... properties)
		throws InvalidSyntaxException, IOException {

		String filter = "(service.factoryPid=" + factoryPid + ")";

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filter);

		if (ArrayUtil.isNotEmpty(configurations)) {
			return;
		}

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(factoryPid);

		Dictionary<String, Object> configurationProperties = new Hashtable<>();

		for (int i = 0; i < properties.length - 1; i += 2) {
			configurationProperties.put((String)properties[i], properties[i+1]);
		}

		configuration.update(configurationProperties);
	}

	@Reference
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private ConfigurationAdmin _configurationAdmin;

}