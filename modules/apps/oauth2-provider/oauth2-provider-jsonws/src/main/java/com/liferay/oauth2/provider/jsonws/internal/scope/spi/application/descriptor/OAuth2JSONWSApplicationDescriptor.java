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

package com.liferay.oauth2.provider.jsonws.internal.scope.spi.application.descriptor;

import com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration;
import com.liferay.oauth2.provider.jsonws.internal.constants.OAuth2JSONWSConstants;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration",
	property = "osgi.jaxrs.name=" + OAuth2JSONWSConstants.APPLICATION_NAME,
	service = ApplicationDescriptor.class
)
public class OAuth2JSONWSApplicationDescriptor
	implements ApplicationDescriptor {

	@Override
	public String describeApplication(Locale locale) {
		String applicationDescription = ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, OAuth2JSONWSApplicationDescriptor.class),
			_applicationDescription);

		if (applicationDescription == null) {
			return _applicationDescription;
		}

		return applicationDescription;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		OAuth2JSONWSConfiguration oAuth2JSONWSConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2JSONWSConfiguration.class, properties);

		_applicationDescription =
			oAuth2JSONWSConfiguration.applicationDescription();
	}

	private String _applicationDescription;

}