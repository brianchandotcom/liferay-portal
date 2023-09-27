/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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