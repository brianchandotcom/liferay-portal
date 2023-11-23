/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceInsertionConfiguration;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceInsertionConfiguration",
	service = ConfigurationModelListener.class
)
public class DBPartitionVirtualInstanceInsertionConfigurationModelListener
	extends BaseConfigurationModelListener {

	public DBPartitionVirtualInstanceInsertionConfigurationModelListener() {
		super(
			"com.liferay.portal.db.partition.internal.configuration." +
				"DBPartitionVirtualInstanceInsertionConfiguration");
	}

	@Override
	public void doOnAfterSave(Dictionary<String, Object> properties)
		throws PortalException {

		_companyLocalService.insertCompany(
			GetterUtil.getLong(properties.get("companyId")),
			(String)properties.get("newName"),
			(String)properties.get("newVirtualHostName"),
			(String)properties.get("newWebId"));

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@Override
	public Class<?> getConfigurationClass() {
		return DBPartitionVirtualInstanceInsertionConfiguration.class;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}