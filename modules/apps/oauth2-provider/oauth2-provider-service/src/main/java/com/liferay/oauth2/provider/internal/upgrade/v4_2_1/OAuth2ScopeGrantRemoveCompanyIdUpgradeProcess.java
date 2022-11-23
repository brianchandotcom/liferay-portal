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

package com.liferay.oauth2.provider.internal.upgrade.v4_2_1;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Carlos Correa
 */
public class OAuth2ScopeGrantRemoveCompanyIdUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			ResultSet oAuth2ScopeGrantResultSet =
				_getOAuth2ScopeGrantResultSet()) {

			while (oAuth2ScopeGrantResultSet.next()) {
				String applicationName = oAuth2ScopeGrantResultSet.getString(
					"applicationName");
				long companyId = oAuth2ScopeGrantResultSet.getLong("companyId");

				if (!StringUtil.endsWith(
						applicationName, String.valueOf(companyId))) {

					continue;
				}

				List<String> scopeAliases = Arrays.asList(
					StringUtil.split(
						oAuth2ScopeGrantResultSet.getString("scopeAliases"),
						StringPool.SPACE));

				String newApplicationName = StringUtil.removeLast(
					applicationName, String.valueOf(companyId));

				List<String> newScopeAliases = TransformUtil.transform(
					scopeAliases,
					scopeAlias -> StringUtil.replace(
						scopeAlias, applicationName, newApplicationName));

				_updateOAuth2ScopeGrant(
					newApplicationName,
					oAuth2ScopeGrantResultSet.getLong("oauth2ScopeGrantId"),
					newScopeAliases);
			}
		}
	}

	private ResultSet _getOAuth2ScopeGrantResultSet() throws SQLException {
		String sql =
			"select * from OAuth2ScopeGrant where " +
				"bundleSymbolicName='com.liferay.object.rest.impl'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		return preparedStatement.executeQuery();
	}

	private void _updateOAuth2ScopeGrant(
			String applicationName, long oAuth2ScopeGrantId,
			List<String> scopeAliases)
		throws SQLException {

		String sql =
			"update OAuth2ScopeGrant set applicationName = ?," +
				"scopeAliases = ? where oAuth2ScopeGrantId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(
				2,
				StringUtil.merge(
					ListUtil.sort(new ArrayList<>(scopeAliases)),
					StringPool.SPACE));
			preparedStatement.setLong(3, oAuth2ScopeGrantId);

			preparedStatement.execute();
		}
	}

}