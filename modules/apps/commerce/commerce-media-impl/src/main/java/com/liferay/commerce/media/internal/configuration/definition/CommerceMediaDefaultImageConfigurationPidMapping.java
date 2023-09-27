/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.media.internal.configuration.definition;

import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.media.internal.configuration.CommerceMediaDefaultImageConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(service = ConfigurationPidMapping.class)
public class CommerceMediaDefaultImageConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceMediaDefaultImageConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return CommerceMediaConstants.SERVICE_NAME;
	}

}