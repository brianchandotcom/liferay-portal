/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;

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
@Component(service = SharepointRepositoryTokenBrokerFactory.class)
public class SharepointRepositoryTokenBrokerFactory {

	public SharepointRepositoryTokenBroker create(
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration) {

		return new SharepointRepositoryTokenBroker(
			sharepointRepositoryConfiguration);
	}

	public SharepointRepositoryTokenBroker create(String configurationPid) {
		return create(_getSharepointRepositoryConfiguration(configurationPid));
	}

	private SharepointRepositoryConfiguration
		_getSharepointRepositoryConfiguration(String configurationPid) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPid=" +
						SharepointRepositoryConfiguration.class.getName() +
							")");

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if ((name != null) && name.equals(configurationPid)) {
					return ConfigurableUtil.createConfigurable(
						SharepointRepositoryConfiguration.class, properties);
				}
			}

			throw new NoSuchElementException(
				"No configuration found with name " + configurationPid);
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}