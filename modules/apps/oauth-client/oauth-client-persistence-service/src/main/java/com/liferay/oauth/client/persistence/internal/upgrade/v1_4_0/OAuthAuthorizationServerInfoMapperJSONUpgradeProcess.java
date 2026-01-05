/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_4_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alvaro Saugar
 */
public class OAuthAuthorizationServerInfoMapperJSONUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select oAuthClientASLocalMetadataId, metadataJSON from " +
					"OAuthClientASLocalMetadata");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long oAuthClientASLocalMetadataId = resultSet.getLong(
					"oAuthClientASLocalMetadataId");

				String metadataJSON = resultSet.getString("metadataJSON");

				if (Validator.isNull(metadataJSON)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update issuer for OAuth client " +
								"authorization server local metadata ID " +
									oAuthClientASLocalMetadataId);
					}

					continue;
				}

				OIDCProviderMetadata oidcProviderMetadata =
					OIDCProviderMetadata.parse(metadataJSON);

				if ((oidcProviderMetadata == null) ||
					(oidcProviderMetadata.getIssuer() == null)) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update issuer for OAuth client " +
								"authorization server local metadata ID " +
									oAuthClientASLocalMetadataId);
					}
				}
				else {
					String issuer = oidcProviderMetadata.getIssuer(
					).toString();

					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								"update OAuthClientASLocalMetadata set " +
									"issuer = ? WHERE " +
										"oAuthClientASLocalMetadataId = ?")) {

						preparedStatement2.setString(1, issuer);
						preparedStatement2.setLong(
							2, oAuthClientASLocalMetadataId);

						preparedStatement2.execute();
					}
				}
			}
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OAuthClientASLocalMetadata", "issuer TEXT null",
				"localWellKnownEnabled BOOLEAN false",
				"oAuthASLocalWellKnownURI TEXT null",
				"oAuthASMetadataJSON TEXT null")
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthAuthorizationServerInfoMapperJSONUpgradeProcess.class);

}