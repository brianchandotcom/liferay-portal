/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration;

import com.liferay.document.library.configuration.DLFileOrderConfigurationProvider;
import com.liferay.document.library.internal.configuration.admin.service.DLFileOrderManagedServiceFactory;

import org.osgi.service.cm.ManagedServiceFactory;
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
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getCompanyOrderByColumn(
			companyId);
	}

	@Override
	public String getCompanySortBy(long companyId) {
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getCompanySortBy(companyId);
	}

	@Override
	public String getGroupOrderByColumn(long groupId) {
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getGroupOrderByColumn(groupId);
	}

	@Override
	public String getGroupSortBy(long groupId) {
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getGroupSortBy(groupId);
	}

	@Override
	public String getSystemOrderByColumn() {
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getSystemOrderByColumn();
	}

	@Override
	public String getSystemSortBy() {
		if (_dlFileOrderManagedServiceFactory == null) {
			_dlFileOrderManagedServiceFactory =
				(DLFileOrderManagedServiceFactory)_managedServiceFactory;
		}

		return _dlFileOrderManagedServiceFactory.getSystemSortBy();
	}

	private DLFileOrderManagedServiceFactory _dlFileOrderManagedServiceFactory;

	@Reference(
		target = "(component.name=com.liferay.document.library.internal.configuration.admin.service.DLFileOrderManagedServiceFactory)"
	)
	private ManagedServiceFactory _managedServiceFactory;

}