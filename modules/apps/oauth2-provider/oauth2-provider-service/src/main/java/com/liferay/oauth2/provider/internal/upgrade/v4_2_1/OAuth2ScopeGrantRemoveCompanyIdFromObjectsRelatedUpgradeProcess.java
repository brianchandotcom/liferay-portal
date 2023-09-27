/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.util.List;

/**
 * @author Carlos Correa
 */
public class OAuth2ScopeGrantRemoveCompanyIdFromObjectsRelatedUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select oAuth2ScopeGrantId, companyId, applicationName, " +
					"scopeAliases from OAuth2ScopeGrant where " +
						"bundleSymbolicName = ?")) {

			preparedStatement.setString(1, "com.liferay.object.rest.impl");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String companyId = String.valueOf(
					resultSet.getLong("companyId"));
				String applicationName = resultSet.getString("applicationName");

				if (!StringUtil.endsWith(applicationName, companyId)) {
					continue;
				}

				String newApplicationName = applicationName.substring(
					0, applicationName.length() - companyId.length());

				_updateOAuth2ScopeGrant(
					newApplicationName, resultSet.getLong("oAuth2ScopeGrantId"),
					TransformUtil.transformToList(
						StringUtil.split(
							resultSet.getString("scopeAliases"),
							StringPool.SPACE),
						scopeAlias -> StringUtil.replace(
							scopeAlias, applicationName, newApplicationName)));
			}
		}
	}

	private void _updateOAuth2ScopeGrant(
			String applicationName, long oAuth2ScopeGrantId,
			List<String> scopeAliasesList)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update OAuth2ScopeGrant set applicationName = ?," +
					"scopeAliases = ? where oAuth2ScopeGrantId = ?")) {

			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(
				2,
				StringUtil.merge(
					ListUtil.sort(scopeAliasesList), StringPool.SPACE));
			preparedStatement.setLong(3, oAuth2ScopeGrantId);

			preparedStatement.execute();
		}
	}

}