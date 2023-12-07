/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtractionConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtractionConfiguration",
	service = ConfigurationModelListener.class
)
public class DBPartitionVirtualInstanceExtractionConfigurationModelListener
	extends BaseConfigurationModelListener {

	public DBPartitionVirtualInstanceExtractionConfigurationModelListener() {
		super(
			"com.liferay.portal.db.partition.internal.configuration." +
				"DBPartitionVirtualInstanceExtractionConfiguration");
	}

	@Override
	public void doOnAfterSave(Dictionary<String, Object> properties)
		throws Exception {

		_companyLocalService.extractDBPartitionCompany(
			(long)properties.get("companyId"));
	}

	@Override
	public Class<?> getConfigurationClass() {
		return DBPartitionVirtualInstanceExtractionConfiguration.class;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

}