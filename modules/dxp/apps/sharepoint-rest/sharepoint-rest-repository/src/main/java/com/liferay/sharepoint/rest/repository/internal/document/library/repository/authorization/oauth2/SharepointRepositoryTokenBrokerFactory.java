/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
					"(service.factoryPID=" +
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
		catch (InvalidSyntaxException | IOException e) {
			throw new SystemException(e);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}