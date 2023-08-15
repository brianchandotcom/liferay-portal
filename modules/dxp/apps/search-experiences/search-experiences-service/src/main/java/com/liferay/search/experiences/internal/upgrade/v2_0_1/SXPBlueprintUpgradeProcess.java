/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v2_0_1;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.io.IOException;

import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Bryan Engler
 * @author Wade Cao
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpBlueprintId, elementInstancesJSON from " +
					"SXPBlueprint");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ? where " +
						"sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String elementInstancesJSON = resultSet.getString(
						"elementInstancesJSON");

					try {
						ElementInstanceUtil.toElementInstances(
							elementInstancesJSON);

						continue;
					}
					catch (RuntimeException runtimeException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Search experiences blueprint with ID ",
									resultSet.getLong("sxpBlueprintId"),
									" contains corrupted element instances ",
									"JSON"),
								runtimeException);
						}
					}

					preparedStatement2.setString(
						1, _fixElementInstancesJSON(elementInstancesJSON));

					preparedStatement2.setLong(
						2, resultSet.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private String _fixElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		Matcher matcher = _pattern.matcher(elementInstancesJSON);

		if (!matcher.find()) {
			return elementInstancesJSON;
		}

		elementInstancesJSON = matcher.replaceAll(StringPool.CLOSE_BRACKET);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			elementInstancesJSON);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONObject sxpElementJSONObject = jsonObject.getJSONObject(
				"sxpElement");

			String externalReferenceCode = sxpElementJSONObject.getString(
				"externalReferenceCode");

			if (!ArrayUtil.contains(
					_EXTERNAL_REFERENCE_CODES, externalReferenceCode)) {

				continue;
			}

			String elementDefinitionJSON = sxpElementJSONObject.getString(
				"elementDefinition");

			elementDefinitionJSON = StringUtil.replace(
				elementDefinitionJSON, "\"defaultValue\":[]",
				_defaultValues.get(externalReferenceCode));

			sxpElementJSONObject.put(
				"elementDefinition",
				JSONFactoryUtil.createJSONObject(elementDefinitionJSON));
		}

		return jsonArray.toString();
	}

	private Map<String, String> _initDefaultValues() {
		Map<String, String> defaultValues = new HashMap<>();

		Bundle bundle = FrameworkUtil.getBundle(
			SXPBlueprintUpgradeProcess.class);

		Package pkg = SXPBlueprintUpgradeProcess.class.getPackage();

		String path =
			StringUtil.replace(pkg.getName(), CharPool.PERIOD, CharPool.SLASH) +
				"/dependencies/";

		for (String externalReferenceCode : _EXTERNAL_REFERENCE_CODES) {
			URL url = bundle.getEntry(
				path + StringUtil.toLowerCase(externalReferenceCode) + ".txt");

			try {
				defaultValues.put(
					externalReferenceCode,
					StreamUtil.toString(url.openStream()));
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to get default value for element with external " +
						"reference code: " + externalReferenceCode,
					ioException);
			}
		}

		return defaultValues;
	}

	private static final String[] _EXTERNAL_REFERENCE_CODES = {
		"BOOST_ALL_KEYWORDS_MATCH", "BOOST_PROXIMITY",
		"SEARCH_WITH_QUERY_STRING_SYNTAX", "TEXT_MATCH_OVER_MULTIPLE_FIELDS"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUpgradeProcess.class);

	private static final Pattern _pattern = Pattern.compile(
		"Ljava\\.lang\\.Object;@\\w{8}");

	private final Map<String, String> _defaultValues = _initDefaultValues();

}