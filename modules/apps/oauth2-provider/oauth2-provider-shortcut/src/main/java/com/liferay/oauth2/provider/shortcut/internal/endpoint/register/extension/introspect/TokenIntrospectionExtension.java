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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.introspect;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.Extension;
import com.liferay.portal.kernel.model.User;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration",
	immediate = true, property = "type=token_introspection",
	service = Extension.class
)
public class TokenIntrospectionExtension implements Extension {

	@Override
	public void postProcess(Object object, OAuth2Application oAuth2Application)
		throws Exception {

		List<String> featuresList = new ArrayList<>();

		featuresList.add("token_introspection");

		oAuth2Application.setFeaturesList(featuresList);

		_oAuth2ApplicationLocalService.updateOAuth2Application(
			oAuth2Application);
	}

	@Override
	public void preProcess(Object object, User user) throws Exception {
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}