/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration;

import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtractionConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class DBPartitionVirtualInstanceExtraction
	extends BaseVirtualInstanceOperation {

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> _companyLocalService.extractCompany(
				(long)properties.get("companyId")),
			properties);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

}