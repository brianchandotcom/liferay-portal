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

package com.liferay.document.library.internal.configuration;

import com.liferay.document.library.configuration.DLFileOrderConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sam Ziemer
 */
@Component(service = DLFileOrderConfigurationProvider.class)
public class DLFileOrderConfigurationProviderImpl
	implements DLFileOrderConfigurationProvider {

	public String getCompanyOrderByColumn(long compnayId)
		throws ConfigurationException {

		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DLFileOrderConfiguration.class, compnayId);

		return dlFileOrderConfiguration.orderByColumn();
	}

	public String getCompanySortBy(long companyId)
		throws ConfigurationException {

		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DLFileOrderConfiguration.class, companyId);

		return dlFileOrderConfiguration.sortBy();
	}

	public String getGroupOrderByColumn(long groupId)
		throws ConfigurationException {

		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getGroupConfiguration(
				DLFileOrderConfiguration.class, groupId);

		return dlFileOrderConfiguration.orderByColumn();
	}

	public String getGroupSortBy(long groupId) throws ConfigurationException {
		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getGroupConfiguration(
				DLFileOrderConfiguration.class, groupId);

		return dlFileOrderConfiguration.sortBy();
	}

	public String getSystemOrderByColumn() throws ConfigurationException {
		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getSystemConfiguration(
				DLFileOrderConfiguration.class);

		return dlFileOrderConfiguration.orderByColumn();
	}

	public String getSystemSortBy() throws ConfigurationException {
		DLFileOrderConfiguration dlFileOrderConfiguration =
			_configurationProvider.getSystemConfiguration(
				DLFileOrderConfiguration.class);

		return dlFileOrderConfiguration.sortBy();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}