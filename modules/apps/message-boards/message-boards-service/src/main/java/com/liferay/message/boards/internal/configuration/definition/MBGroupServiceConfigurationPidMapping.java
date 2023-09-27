/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.configuration.definition;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = ConfigurationPidMapping.class)
public class MBGroupServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return MBGroupServiceSettings.class;
	}

	@Override
	public String getConfigurationPid() {
		return MBConstants.SERVICE_NAME;
	}

}