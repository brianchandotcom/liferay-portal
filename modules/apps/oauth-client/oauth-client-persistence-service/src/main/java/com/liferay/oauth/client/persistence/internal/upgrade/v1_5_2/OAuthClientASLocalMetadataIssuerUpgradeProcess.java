/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_5_2;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Jorge García Jiménez
 */
public class OAuthClientASLocalMetadataIssuerUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update OAuthClientASLocalMetadata set issuer = ? where " +
						"oAuthClientASLocalMetadataId = ?");

			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(
				"select oAuthClientASLocalMetadataId, metadataJSON from " +
					"OAuthClientASLocalMetadata")) {

			while (resultSet.next()) {
				long oAuthClientASLocalMetadataId = resultSet.getLong(
					"oAuthClientASLocalMetadataId");

				String metadataJSON = resultSet.getString("metadataJSON");

				if (Validator.isNull(metadataJSON)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update issuer for OAuth 2 client " +
								"authorization server local metadata " +
									oAuthClientASLocalMetadataId);
					}

					continue;
				}

				OIDCProviderMetadata oidcProviderMetadata;

				try {
					oidcProviderMetadata = OIDCProviderMetadata.parse(
						metadataJSON);
				}
				catch (ParseException parseException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to parse metadata JSON for OAuth 2 " +
								"client authorization server local metadata " +
									oAuthClientASLocalMetadataId,
							parseException);
					}

					continue;
				}

				if ((oidcProviderMetadata == null) ||
					(oidcProviderMetadata.getIssuer() == null)) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update issuer for OAuth 2 client " +
								"authorization server local metadata " +
									oAuthClientASLocalMetadataId);
					}

					continue;
				}

				preparedStatement.setString(
					1, String.valueOf(oidcProviderMetadata.getIssuer()));
				preparedStatement.setLong(2, oAuthClientASLocalMetadataId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.alterColumnType(
				"OAuthClientASLocalMetadata", "issuer", "VARCHAR(256) null")
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientASLocalMetadataIssuerUpgradeProcess.class);

}