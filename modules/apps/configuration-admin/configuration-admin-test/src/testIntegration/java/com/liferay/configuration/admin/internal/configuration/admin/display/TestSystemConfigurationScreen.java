/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thiago Buarque
 */
@Component(service = ConfigurationScreen.class)
public class TestSystemConfigurationScreen extends BaseConfigurationScreen {

	@Override
	public String getScope() {
		return "system";
	}

}