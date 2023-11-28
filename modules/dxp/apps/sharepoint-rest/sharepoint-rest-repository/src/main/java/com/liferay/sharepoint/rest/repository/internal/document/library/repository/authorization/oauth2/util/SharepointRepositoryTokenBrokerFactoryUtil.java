/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryTokenBroker;

import java.io.IOException;

import java.util.Dictionary;
import java.util.NoSuchElementException;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Adolfo Pérez
 */
public class SharepointRepositoryTokenBrokerFactoryUtil {

	public static SharepointRepositoryTokenBroker create(
		ConfigurationAdmin configurationAdmin, String configurationPid) {

		try {
			Configuration[] configurations =
				configurationAdmin.listConfigurations(
					"(service.factoryPid=" +
						SharepointRepositoryConfiguration.class.getName() +
							")");

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if ((name != null) && name.equals(configurationPid)) {
					return create(
						ConfigurableUtil.createConfigurable(
							SharepointRepositoryConfiguration.class,
							properties));
				}
			}

			throw new NoSuchElementException(
				"No configuration found with name " + configurationPid);
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new SystemException(exception);
		}
	}

	public static SharepointRepositoryTokenBroker create(
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration) {

		return new SharepointRepositoryTokenBroker(
			sharepointRepositoryConfiguration);
	}

}