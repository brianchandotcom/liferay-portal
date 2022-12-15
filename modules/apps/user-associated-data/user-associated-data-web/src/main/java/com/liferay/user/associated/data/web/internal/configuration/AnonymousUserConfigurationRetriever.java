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

package com.liferay.user.associated.data.web.internal.configuration;

import java.io.IOException;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = AnonymousUserConfigurationRetriever.class)
public class AnonymousUserConfigurationRetriever {

	public Configuration get(long companyId)
		throws InvalidSyntaxException, IOException {

		String filterString = String.format(
			"(&(service.factoryPid=%s)(companyId=%s))", _getFactoryPid(),
			companyId);

		return _get(filterString);
	}

	public Configuration get(long companyId, long userId)
		throws InvalidSyntaxException, IOException {

		String filterString = String.format(
			"(&(service.factoryPid=%s)(companyId=%s)(userId=%s))",
			_getFactoryPid(), String.valueOf(companyId),
			String.valueOf(userId));

		return _get(filterString);
	}

	private Configuration _get(String filterString)
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private String _getFactoryPid() {
		return AnonymousUserConfiguration.class.getName() + ".scoped";
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}