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
package com.liferay.sharepoint.repository.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import java.util.Dictionary;
import java.util.NoSuchElementException;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = SharepointOAuth2AuthorizationServerFactory.class)
public class SharepointOAuth2AuthorizationServerFactory {

	public SharepointOAuth2AuthorizationServer create(
		SharepointOAuth2Configuration sharepointOAuth2Configuration) {

		return new SharepointOAuth2AuthorizationServer(
			sharepointOAuth2Configuration);
	}

	public SharepointOAuth2AuthorizationServer create(String configurationId) {
		return create(_getConfiguration(configurationId));
	}

	private SharepointOAuth2Configuration _getConfiguration(
		String configurationId) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPID=" + _FACTORY_PID + ")");

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if ((name != null) && name.equals(configurationId)) {
					return ConfigurableUtil.createConfigurable(
						SharepointOAuth2Configuration.class, properties);
				}
			}

			throw new NoSuchElementException(
				"No configuration found with name " + configurationId);
		}
		catch (InvalidSyntaxException | IOException e) {
			throw new SystemException(e);
		}
	}

	private static final String _FACTORY_PID =
		"com.liferay.sharepoint.repository.internal." +
			"SharepointOAuth2Configuration";

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}