/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.portal.security.auto.login.internal.module.configuration.definition;

import com.liferay.commerce.punchout.portal.security.auto.login.internal.constants.PunchOutAutoLoginConstants;
import com.liferay.commerce.punchout.portal.security.auto.login.internal.module.configuration.PunchOutAccessTokenAutoLoginConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jaclyn Ong
 */
@Component(service = ConfigurationPidMapping.class)
public class PunchOutAccessTokenAutoLoginCompanyServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return PunchOutAccessTokenAutoLoginConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return PunchOutAutoLoginConstants.SERVICE_NAME;
	}

}