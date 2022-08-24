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
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stian Sigvartsen
 */
public class SamlIdpSpSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterTableAddColumn(
				"SamlIdpSpSession", "samlPeerBindingId", "LONG null");

			runSQL("delete from SamlPeerBinding");

			int samlIdpSpSessionIdOffset = _getSamlIdpSpSessionIdOffset();

			int latestSamlPeerBindingId = _getLatestSamlPeerBindingId();

			String sql = StringBundler.concat(
				"insert into SamlPeerBinding (samlPeerBindingId, companyId, ",
				"createDate, userId, userName, deleted, samlNameIdFormat, ",
				"samlNameIdNameQualifier, samlNameIdSpNameQualifier, ",
				"samlNameIdSpProvidedId, samlNameIdValue, samlPeerEntityId) ",
				"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
				ResultSet resultSet = preparedStatement.executeQuery();
				PreparedStatement insertPreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sql)) {

				while (resultSet.next()) {
					insertPreparedStatement.setInt(
						1,
						resultSet.getInt("samlIdpSpSessionId") +
							-samlIdpSpSessionIdOffset +
								latestSamlPeerBindingId);
					insertPreparedStatement.setLong(
						2, resultSet.getLong("companyId"));
					insertPreparedStatement.setTimestamp(
						3, resultSet.getTimestamp("createDate"));
					insertPreparedStatement.setLong(
						4, resultSet.getLong("userId"));
					insertPreparedStatement.setString(
						5, resultSet.getString("userName"));
					insertPreparedStatement.setBoolean(6, false);
					insertPreparedStatement.setString(
						7, resultSet.getString("nameIdFormat"));
					insertPreparedStatement.setString(8, null);
					insertPreparedStatement.setString(9, null);
					insertPreparedStatement.setString(10, null);
					insertPreparedStatement.setString(
						11, resultSet.getString("nameIdValue"));
					insertPreparedStatement.setString(
						12, resultSet.getString("samlSpEntityId"));

					insertPreparedStatement.addBatch();
				}

				insertPreparedStatement.executeBatch();
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