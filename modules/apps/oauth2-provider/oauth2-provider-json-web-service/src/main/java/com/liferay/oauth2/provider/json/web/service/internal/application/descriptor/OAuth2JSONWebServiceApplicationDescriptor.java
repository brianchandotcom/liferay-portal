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

package com.liferay.oauth2.provider.json.web.service.internal.application.descriptor;

import com.liferay.oauth2.provider.json.web.service.internal.configuration.OAuth2JSONWebServiceConfiguration;
import com.liferay.oauth2.provider.json.web.service.internal.constants.OAuth2JSONWebServiceConstants;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.json.web.service.internal.configuration.OAuth2JSONWebServiceConfiguration",
	property = "osgi.jaxrs.name=" + OAuth2JSONWebServiceConstants.LIFERAY_JSON_WEB_SERVICES
)
public class OAuth2JSONWebServiceApplicationDescriptor
	implements ApplicationDescriptor {

	@Override
	public String describeApplication(Locale locale) {
		String applicationDescription = ResourceBundleUtil.getString(
			_resourceBundleLoader.loadResourceBundle(locale),
			_applicationDescription);

		if (applicationDescription == null) {
			return _applicationDescription;
		}

		return applicationDescription;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		OAuth2JSONWebServiceConfiguration oAuth2JSONWebServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2JSONWebServiceConfiguration.class, properties);

		_applicationDescription =
			oAuth2JSONWebServiceConfiguration.applicationDescription();
	}

	private String _applicationDescription;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.oauth2.provider.json.web.service)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}