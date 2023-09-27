/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.configuration;

import com.liferay.depot.configuration.DepotConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DepotConfiguration.class)
public class DepotConfigurationImpl implements DepotConfiguration {

	@Override
	public boolean isEnabled() {
		return true;
	}

}