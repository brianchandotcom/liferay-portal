/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.internal.upgrade.v3_0_0;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Stian Sigvartsen
 */
public class SamlIdpSpSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("SamlIdpSpSession", "samlPeerBindingId")) {
				alterTableAddColumn(
					"SamlIdpSpSession", "samlPeerBindingId", "LONG null");
			}

			runSQL("delete from SamlPeerBinding");

			int samlIdpSpSessionIdOffset = _getSamlIdpSpSessionIdOffset();

			int latestSamlPeerBindingId = _getLatestSamlPeerBindingId();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select min(samlIdpSpSessionId) as ",
							"samlIdpSpSessionId, companyId, min(createDate) ",
							"as createDate, userId, userName, nameIdFormat, ",
							"nameIdValue, samlSpEntityId from ",
							"SamlIdpSpSession group by companyId, userId, ",
							"userName, nameIdFormat, nameIdValue, ",
							"samlSpEntityId"));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					int samlIdpSpSessionId = resultSet.getInt(
						"samlIdpSpSessionId");
					long companyId = resultSet.getLong("companyId");
					Timestamp createDate = resultSet.getTimestamp("createDate");
					long userId = resultSet.getLong("userId");
					String userName = resultSet.getString("userName");
					String nameIdFormat = resultSet.getString("nameIdFormat");
					String nameIdValue = resultSet.getString("nameIdValue");
					String samlSpEntityId = resultSet.getString(
						"samlSpEntityId");

					int samlPeerBindingId =
						samlIdpSpSessionId + -samlIdpSpSessionIdOffset +
							latestSamlPeerBindingId;

					String sql = StringBundler.concat(
						"insert into SamlPeerBinding (samlPeerBindingId, ",
						"companyId, createDate, userId, userName, deleted, ",
						"samlNameIdFormat, samlNameIdNameQualifier, ",
						"samlNameIdSpNameQualifier, samlNameIdSpProvidedId, ",
						"samlNameIdValue, samlPeerEntityId) values (?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?, ?)");

					try (PreparedStatement insertPreparedStatement =
							connection.prepareStatement(sql)) {

						insertPreparedStatement.setInt(1, samlPeerBindingId);
						insertPreparedStatement.setLong(2, companyId);
						insertPreparedStatement.setTimestamp(3, createDate);
						insertPreparedStatement.setLong(4, userId);
						insertPreparedStatement.setString(5, userName);
						insertPreparedStatement.setBoolean(6, false);
						insertPreparedStatement.setString(7, nameIdFormat);
						insertPreparedStatement.setString(8, null);
						insertPreparedStatement.setString(9, null);
						insertPreparedStatement.setString(10, null);
						insertPreparedStatement.setString(11, nameIdValue);
						insertPreparedStatement.setString(12, samlSpEntityId);

						insertPreparedStatement.executeUpdate();
					}
				}
			}

			runSQL(
				StringBundler.concat(
					"update SamlIdpSpSession set samlPeerBindingId = (",
					"select samlPeerBindingId from SamlPeerBinding where ",
					"SamlIdpSpSession.companyId = SamlPeerBinding.companyId ",
					"and SamlIdpSpSession.userId = SamlPeerBinding.userId and ",
					"SamlIdpSpSession.samlSpEntityId = ",
					"SamlPeerBinding.samlPeerEntityId and ",
					"SamlIdpSpSession.nameIdFormat = ",
					"SamlPeerBinding.samlNameIdFormat and ",
					"SamlIdpSpSession.nameIdValue = ",
					"SamlPeerBinding.samlNameIdValue)"));

			CounterLocalServiceUtil.reset(
				"com.liferay.saml.persistence.model.SamlPeerBinding",
				_getLatestSamlPeerBindingId() + 1);
		}
	}

	private int _getLatestSamlPeerBindingId() throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select max(samlPeerBindingId) from SamlPeerBinding");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		return 0;
	}

	private int _getSamlIdpSpSessionIdOffset() throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select min(samlIdpSpSessionId) - 1 from SamlIdpSpSession");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		return 0;
	}

}