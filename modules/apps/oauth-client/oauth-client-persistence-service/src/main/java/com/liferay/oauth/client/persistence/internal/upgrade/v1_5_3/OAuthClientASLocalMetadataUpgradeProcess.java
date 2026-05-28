/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_5_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.minidev.json.JSONObject;

/**
 * @author Christian Moura
 */
public class OAuthClientASLocalMetadataUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update OAuthClientASLocalMetadata set metadataJSON = ?, " +
						"oAuthASMetadataJSON = ? where " +
							"oAuthClientASLocalMetadataId = ?");

			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(
				"select oAuthClientASLocalMetadataId, metadataJSON, " +
					"oAuthASMetadataJSON from OAuthClientASLocalMetadata")) {

			while (resultSet.next()) {
				long oAuthClientASLocalMetadataId = resultSet.getLong(
					"oAuthClientASLocalMetadataId");

				String metadataJSON = resultSet.getString("metadataJSON");

				String updatedMetadataJSON = _removeNullEntries(
					metadataJSON, oAuthClientASLocalMetadataId);

				String oAuthASMetadataJSON = resultSet.getString(
					"oAuthASMetadataJSON");

				String updatedOAuthASMetadataJSON = _removeNullEntries(
					oAuthASMetadataJSON, oAuthClientASLocalMetadataId);

				if (Objects.equals(metadataJSON, updatedMetadataJSON) &&
					Objects.equals(
						oAuthASMetadataJSON, updatedOAuthASMetadataJSON)) {

					continue;
				}

				preparedStatement.setString(1, updatedMetadataJSON);
				preparedStatement.setString(2, updatedOAuthASMetadataJSON);
				preparedStatement.setLong(3, oAuthClientASLocalMetadataId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private String _removeNullEntries(
		String json, long oAuthClientASLocalMetadataId) {

		if (Validator.isNull(json)) {
			return json;
		}

		JSONObject jsonObject;

		try {
			jsonObject = JSONObjectUtils.parse(json);
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to parse JSON for OAuth 2 client authorization " +
						"server local metadata " + oAuthClientASLocalMetadataId,
					parseException);
			}

			return json;
		}

		if (jsonObject == null) {
			return json;
		}

		Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();

		boolean removed = entrySet.removeIf(
			entry -> Objects.equals(entry.getValue(), "null"));

		if (!removed) {
			return json;
		}

		return jsonObject.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientASLocalMetadataUpgradeProcess.class);

}