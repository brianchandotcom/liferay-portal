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

package com.liferay.portal.vulcan.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Javier Gamarra
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration",
	service = ConfigurationModelListener.class
)
public class VulcanConfigurationModelListener
	extends BaseConfigurationModelListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		init(bundleContext, _configurationAdmin, _serviceComponentRuntime);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}