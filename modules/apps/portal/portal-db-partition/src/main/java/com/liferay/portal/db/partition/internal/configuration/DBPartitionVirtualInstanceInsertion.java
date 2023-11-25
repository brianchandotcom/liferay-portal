/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceInsertionConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class DBPartitionVirtualInstanceInsertion
	extends BaseVirtualInstanceOperation {

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				long companyId = GetterUtil.getLong(
					properties.get("companyId"));

				if (_companyLocalService.fetchCompany(companyId) == null) {
					Company company =
						_companyLocalService.addDBPartitionCompany(
							companyId, (String)properties.get("newName"),
							(String)properties.get("newVirtualHostName"),
							(String)properties.get("newWebId"));

					_portalInstancesLocalService.synchronizePortalInstances();

					return company;
				}

				return null;
			},
			properties);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}