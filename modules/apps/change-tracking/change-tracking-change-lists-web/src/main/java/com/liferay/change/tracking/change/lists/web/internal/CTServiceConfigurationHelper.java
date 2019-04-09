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

package com.liferay.change.tracking.change.lists.web.internal;

import com.liferay.change.tracking.configuration.CTServiceConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ChangeTrackingThreadLocal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = {})
public class CTServiceConfigurationHelper {

	@Activate
	protected void activate() {
		try {
			CTServiceConfiguration ctServiceConfiguration =
				_configurationProvider.getSystemConfiguration(
					CTServiceConfiguration.class);

			ChangeTrackingThreadLocal.setLayoutTrackingDefaultEnabled(
				ctServiceConfiguration.enableLayoutTracking());
		}
		catch (ConfigurationException ce) {
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}