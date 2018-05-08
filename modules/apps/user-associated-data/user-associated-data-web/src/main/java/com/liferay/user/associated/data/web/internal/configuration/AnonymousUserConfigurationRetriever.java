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

import java.util.Optional;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, service = AnonymousUserConfigurationRetriever.class
)
public class AnonymousUserConfigurationRetriever {

	public Optional<Configuration> getOptional(long companyId)
		throws InvalidSyntaxException, IOException {

		String filterString = String.format(
			"(&(service.factoryPid=%s)(companyId=%s))", _getFactoryPid(),
			companyId);

		return _getOptional(filterString);
	}

	public Optional<Configuration> getOptional(long companyId, long userId)
		throws InvalidSyntaxException, IOException {

		String filterString = String.format(
			"(&(service.factoryPid=%s)(companyId=%s)(userId=%s))",
			_getFactoryPid(), String.valueOf(companyId),
			String.valueOf(userId));

		return _getOptional(filterString);
	}

	private String _getFactoryPid() {
		return AnonymousUserConfiguration.class.getName();
	}

	private Optional<Configuration> _getOptional(String filterString)
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return Optional.empty();
		}

		return Optional.of(configurations[0]);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}