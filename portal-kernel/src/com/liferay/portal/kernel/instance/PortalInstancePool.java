/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.instance;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalInstancePool {

	public static void add(Company company) {
		_portalInstances.put(company.getCompanyId(), company.getWebId());
	}

	public static long[] getCompanyIds() {
		return ArrayUtil.toLongArray(_portalInstances.keySet());
	}

	public static long[] getCompanyIdsBySQL() throws SQLException {
		List<Long> companyIds = new ArrayList<>();

		long defaultCompanyId = getDefaultCompanyIdBySQL();

		if (defaultCompanyId != 0) {
			companyIds.add(defaultCompanyId);
		}

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_GET_COMPANY_IDS);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				if (companyId != defaultCompanyId) {
					companyIds.add(companyId);
				}
			}
		}

		return ArrayUtil.toArray(companyIds.toArray(new Long[0]));
	}

	public static long getDefaultCompanyId() {
		long[] companyIds = getCompanyIds();

		if (companyIds.length == 0) {
			try {
				return getDefaultCompanyIdBySQL();
			}
			catch (SQLException sqlException) {
				_log.error(
					"Unable to get the default company ID by SQL",
					sqlException);

				throw new RuntimeException(sqlException);
			}
		}

		for (Map.Entry<Long, String> entry : _portalInstances.entrySet()) {
			if (Objects.equals(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID),
					entry.getValue())) {

				return entry.getKey();
			}
		}

		throw new IllegalStateException("Unable to get default company ID");
	}

	public static long getDefaultCompanyIdBySQL() throws SQLException {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company where webId = '" +
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID) + "'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return 0;
	}

	public static String getWebId(long companyId) {
		return _portalInstances.get(companyId);
	}

	public static String[] getWebIds() {
		return ArrayUtil.toStringArray(_portalInstances.values());
	}

	public static void remove(long companyId) {
		_portalInstances.remove(companyId);
	}

	private static final String _GET_COMPANY_IDS =
		"select companyId from Company";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstancePool.class);

	private static final Map<Long, String> _portalInstances =
		new ConcurrentHashMap<>();

}