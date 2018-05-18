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

package com.liferay.commerce.organization.internal.verify;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
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
	property = "verify.process.name=com.liferay.commerce.organization.service",
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