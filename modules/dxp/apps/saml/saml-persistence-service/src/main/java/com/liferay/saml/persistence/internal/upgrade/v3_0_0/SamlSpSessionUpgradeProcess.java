/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class SamlSpSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterTableAddColumn(
				"SamlSpSession", "samlPeerBindingId", "LONG null");

			runSQL(
				StringBundler.concat(
					"delete from SamlPeerBinding where ",
					"SamlPeerBinding.samlPeerBindingId not in (select ",
					"samlPeerBindingId from SamlIdpSpSession)"));

			int latestSamlPeerBindingId = _getLatestSamlPeerBindingId();
			int samlSpSessionIdOffset = _getSamlSpSessionIdOffset();
			String sql1 = StringBundler.concat(
				"select min(samlSpSessionId) as samlSpSessionId, companyId, ",
				"min(createDate) as createDate, userId, userName, ",
				"nameIdFormat, nameIdNameQualifier, nameIdValue, ",
				"samlIdpEntityId from SamlSpSession group by companyId, ",
				"userId, userName, nameIdFormat, nameIdNameQualifier, ",
				"nameIdValue, samlIdpEntityId");
			String sql2 = StringBundler.concat(
				"insert into SamlPeerBinding (samlPeerBindingId, companyId, ",
				"createDate, userId, userName, deleted, samlNameIdFormat, ",
				"samlNameIdNameQualifier, samlNameIdSpNameQualifier, ",
				"samlNameIdSpProvidedId, samlNameIdValue, samlPeerEntityId) ",
				"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(sql1);
				ResultSet resultSet = preparedStatement1.executeQuery();
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sql2)) {

				while (resultSet.next()) {
					preparedStatement2.setInt(
						1,
						resultSet.getInt("samlSpSessionId") +
							-samlSpSessionIdOffset + latestSamlPeerBindingId);
					preparedStatement2.setLong(
						2, resultSet.getLong("companyId"));
					preparedStatement2.setTimestamp(
						3, resultSet.getTimestamp("createDate"));
					preparedStatement2.setLong(4, resultSet.getLong("userId"));
					preparedStatement2.setString(
						5, resultSet.getString("userName"));
					preparedStatement2.setBoolean(6, false);
					preparedStatement2.setString(
						7, resultSet.getString("nameIdFormat"));
					preparedStatement2.setString(
						8, resultSet.getString("nameIdNameQualifier"));
					preparedStatement2.setString(9, null);
					preparedStatement2.setString(10, null);
					preparedStatement2.setString(
						11, resultSet.getString("nameIdValue"));
					preparedStatement2.setString(
						12, resultSet.getString("samlIdpEntityId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}

			runSQL(
				StringBundler.concat(
					"update SamlSpSession set samlPeerBindingId = (",
					"select samlPeerBindingId from SamlPeerBinding where ",
					"SamlSpSession.companyId = SamlPeerBinding.companyId and ",
					"SamlSpSession.userId = SamlPeerBinding.userId and ",
					"SamlSpSession.samlIdpEntityId = ",
					"SamlPeerBinding.samlPeerEntityId and ",
					"SamlSpSession.nameIdFormat = ",
					"SamlPeerBinding.samlNameIdFormat and ",
					"SamlSpSession.nameIdNameQualifier = ",
					"SamlPeerBinding.samlNameIdNameQualifier and ",
					"SamlSpSession.nameIdValue = ",
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

	private int _getSamlSpSessionIdOffset() throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select min(samlSpSessionId) - 1 from SamlSpSession");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		return 0;
	}

}