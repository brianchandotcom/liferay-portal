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
import com.liferay.saml.persistence.internal.upgrade.v3_0_0.util.SamlIdpSpSessionTable;

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
			if (!hasColumn(
					SamlIdpSpSessionTable.TABLE_NAME, "samlPeerBindingId")) {

				alter(
					SamlIdpSpSessionTable.class,
					new AlterTableAddColumn("samlPeerBindingId", "LONG null"));
			}

			int samlIdpSpSessionIdOffset = 0;

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select min(samlIdpSpSessionId) - 1 from " +
							"SamlIdpSpSession");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					samlIdpSpSessionIdOffset = resultSet.getInt(1);
				}
			}

			int latestSamlPeerBindingId = _getLatestSamlPeerBindingId();

			runSQL(
				StringBundler.concat(
					"insert into SamlPeerBinding (samlPeerBindingId, ",
					"companyId, createDate, userId, userName, deleted, ",
					"samlNameIdFormat, samlNameIdNameQualifier, ",
					"samlNameIdSpNameQualifier, samlNameIdSpProvidedId, ",
					"samlNameIdValue, samlPeerEntityId) select ",
					"min(samlIdpSpSessionId) + ",
					-samlIdpSpSessionIdOffset + latestSamlPeerBindingId,
					", companyId, min(createDate), userId, userName, '0' as ",
					"deleted, nameIdFormat, null as nameIdNameQualifier, null ",
					"as samlNameIdSpNameQualifier, null as ",
					"nameIdSpProvidedId, nameIdValue, samlSpEntityId from ",
					"SamlIdpSpSession group by companyId, userId, userName, ",
					"samlSpEntityId, nameIdFormat, nameIdValue"));

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

}