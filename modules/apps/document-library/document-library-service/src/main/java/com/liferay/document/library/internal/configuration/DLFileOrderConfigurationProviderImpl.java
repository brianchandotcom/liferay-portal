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
import com.liferay.document.library.internal.configuration.admin.service.DLFileOrderManagedServiceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sam Ziemer
 */
@Component(service = DLFileOrderConfigurationProvider.class)
public class DLFileOrderConfigurationProviderImpl
	implements DLFileOrderConfigurationProvider {

	@Override
	public String getCompanyOrderByColumn(long companyId) {
		return _dlFileOrderManagedServiceFactory.getCompanyOrderByColumn(
			companyId);
	}

	@Override
	public String getCompanySortBy(long companyId) {
		return _dlFileOrderManagedServiceFactory.getCompanySortBy(companyId);
	}

	@Override
	public String getGroupOrderByColumn(long groupId) {
		return _dlFileOrderManagedServiceFactory.getGroupOrderByColumn(groupId);
	}

	@Override
	public String getGroupSortBy(long groupId) {
		return _dlFileOrderManagedServiceFactory.getGroupSortBy(groupId);
	}

	@Override
	public String getSystemOrderByColumn() {
		return _dlFileOrderManagedServiceFactory.getSystemOrderByColumn();
	}

	@Override
	public String getSystemSortBy() {
		return _dlFileOrderManagedServiceFactory.getSystemSortBy();
	}

	@Reference
	private DLFileOrderManagedServiceFactory _dlFileOrderManagedServiceFactory;

}