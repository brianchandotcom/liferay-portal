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

package com.liferay.remote.web.component.admin.web.internal.form;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	property = "configurationPid=com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	service = ConfigurationDDMFormDeclaration.class
)
public class RemoteWebComponentConfigurationForm
	implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return RemoteWebComponentConfiguration.class;
	}

}