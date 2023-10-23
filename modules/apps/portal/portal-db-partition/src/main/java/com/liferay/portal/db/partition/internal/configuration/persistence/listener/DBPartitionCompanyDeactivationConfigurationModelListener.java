/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.portal.db.partition.internal.configuration.DBPartitionCompanyDeactivationConfiguration",
	service = ConfigurationModelListener.class
)
public class DBPartitionCompanyDeactivationConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			Company company = _companyLocalService.getCompanyByWebId(
				(String)properties.get("webId"));

			if (_log.isInfoEnabled()) {
				_log.info(
					"Company " + company.getCompanyId() +
						" is going to be deactivated ");
			}

			_companyLocalService.deleteCompany(company.getCompanyId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Company " + company.getCompanyId() +
						" has been deactivated ");
			}
		}
		catch (PortalException portalException) {
			throw new ConfigurationModelListenerException(
				portalException.getMessage(),
				DBPartitionCompanyDeactivationConfigurationModelListener.class,
				getClass(), properties);
		}
		finally {
			_deleteConfiguration(pid);
		}
	}

	private void _deleteConfiguration(String pid) {
		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, "?");

			if (configuration != null) {
				Files.deleteIfExists(
					Paths.get(
						PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, _FILE_NAME));
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private static final String _FILE_NAME =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionCompanyDeactivationConfiguration.config";

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionCompanyDeactivationConfigurationModelListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}