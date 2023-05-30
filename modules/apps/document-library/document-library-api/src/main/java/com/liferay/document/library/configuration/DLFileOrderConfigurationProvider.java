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

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Sam Ziemer
 */
@ProviderType
public interface DLFileOrderConfigurationProvider {

	public String getCompanyOrderByColumn(long companyId)
		throws ConfigurationException;

	public String getCompanySortBy(long companyId)
		throws ConfigurationException;

	public String getGroupOrderByColumn(long groupId)
		throws ConfigurationException;

	public String getGroupSortBy(long groupId) throws ConfigurationException;

	public String getSystemOrderByColumn() throws ConfigurationException;

	public String getSystemSortBy() throws ConfigurationException;

}