/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.configuration.definition;

import com.liferay.portal.kernel.settings.PortalSettings;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = ConfigurationPidMapping.class)
public class PortalConfigurationPidMapping implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return PortalSettings.class;
	}

	@Override
	public String getConfigurationPid() {
		return "com.liferay.portal";
	}

}