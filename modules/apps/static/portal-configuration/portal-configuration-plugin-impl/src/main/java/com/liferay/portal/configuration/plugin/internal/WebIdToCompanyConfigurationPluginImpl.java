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

package com.liferay.portal.configuration.plugin.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationPlugin;

/**
 * @author Raymond Augé
 */
public class WebIdToCompanyConfigurationPluginImpl
	implements ConfigurationPlugin {

	public WebIdToCompanyConfigurationPluginImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void modifyConfiguration(
		ServiceReference<?> serviceReference,
		Dictionary<String, Object> properties) {

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNull(webId)) {
			return;
		}

		if (Objects.equals(webId, "default")) {
			webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
		}

		long companyId = -1;

		try {
			ServiceReference<DataSource> dataSourceServiceReference =
				_bundleContext.getServiceReference(DataSource.class);

			if (dataSourceServiceReference != null) {
				DataSource dataSource = _bundleContext.getService(
					dataSourceServiceReference);

				try (Connection connection = dataSource.getConnection();
					PreparedStatement preparedStatement =
						connection.prepareStatement(
							_db.buildSQL(_COMPANY_QUERY))) {

					preparedStatement.setString(1, webId);

					try (ResultSet resultSet =
							preparedStatement.executeQuery()) {

						if (resultSet.next()) {
							companyId = resultSet.getLong(1);
						}
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Skip web ID " + webId);
			}

			return;
		}

		if (companyId != -1) {
			properties.put("companyId", companyId);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Injected company ID ", companyId, " for web ID ",
						webId));
			}
		}
	}

	private static final String _COMPANY_QUERY =
		"select companyId from Company where webId = ?";

	private static final Log _log = LogFactoryUtil.getLog(
		WebIdToCompanyConfigurationPluginImpl.class);

	private final BundleContext _bundleContext;
	private final DB _db = DBManagerUtil.getDB();

}