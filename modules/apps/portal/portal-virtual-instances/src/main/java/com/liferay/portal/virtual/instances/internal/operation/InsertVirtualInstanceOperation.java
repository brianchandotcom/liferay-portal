/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.instances.internal.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.virtual.instances.internal.configuration.InsertVirtualInstanceConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = "com.liferay.portal.virtual.instances.internal.configuration.InsertVirtualInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class InsertVirtualInstanceOperation
	extends BaseVirtualInstanceOperation {

	@Override
	public String getOperationCompletedMessage(long companyId) {
		return "Virtual instance with company ID " + companyId +
			" imported successfully";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		onVirtualInstance(
			() -> {
				InsertVirtualInstanceConfiguration
					insertVirtualInstanceConfiguration =
						ConfigurableUtil.createConfigurable(
							InsertVirtualInstanceConfiguration.class,
							properties);

				long companyId = insertVirtualInstanceConfiguration.companyId();

				if (_hasCompany(companyId)) {
					_log.error(
						StringBundler.concat(
							"Virtual instance with company ID ", companyId,
							" already exists"));

					return null;
				}

				return _companyLocalService.addDBPartitionCompany(
					companyId, insertVirtualInstanceConfiguration.newName(),
					insertVirtualInstanceConfiguration.newVirtualHostname(),
					insertVirtualInstanceConfiguration.newWebId());
			},
			properties);
	}

	private boolean _hasCompany(long companyId) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company where companyId = ?")) {

			preparedStatement.setLong(1, companyId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InsertVirtualInstanceOperation.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}