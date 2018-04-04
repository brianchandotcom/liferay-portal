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

package com.liferay.commerce.organization.internal.verify;

import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.commerce.organization.service"},
	service =
		{CommerceOrganizationServiceVerifyProcess.class, VerifyProcess.class}
)
public class CommerceOrganizationServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyOrganizationTypes();
	}

	protected void verifyOrganizationTypes() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					_getConfigurationFilter());

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if (name.equals(OrganizationConstants.TYPE_ORGANIZATION)) {
					properties.put(
						"childrenTypes",
						new String[] {"account", "organization"});

					configuration.update(properties);
				}
			}
		}
	}

	private String _getConfigurationFilter() {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		sb.append(StringPool.EQUAL);
		sb.append(_ORGANIZATION_TYPE_CONFIGURATION_PID);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private static final String _ORGANIZATION_TYPE_CONFIGURATION_PID =
		"com.liferay.organizations.service.internal.configuration." +
			"OrganizationTypeConfiguration";

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}