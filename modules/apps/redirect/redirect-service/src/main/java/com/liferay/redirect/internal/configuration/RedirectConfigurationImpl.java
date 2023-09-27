/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.redirect.internal.configuration.RedirectConfiguration",
	service = com.liferay.redirect.configuration.RedirectConfiguration.class
)
public class RedirectConfigurationImpl
	implements com.liferay.redirect.configuration.RedirectConfiguration {

	@Override
	public boolean isRedirectNotFoundEnabled() {
		return _redirectConfiguration.enabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_redirectConfiguration = ConfigurableUtil.createConfigurable(
			RedirectConfiguration.class, properties);
	}

	private volatile RedirectConfiguration _redirectConfiguration;

}