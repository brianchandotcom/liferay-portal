/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.configuration.definition;

import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(service = ConfigurationPidMapping.class)
public class CPDisplayLayoutConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CPDisplayLayoutConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT;
	}

}