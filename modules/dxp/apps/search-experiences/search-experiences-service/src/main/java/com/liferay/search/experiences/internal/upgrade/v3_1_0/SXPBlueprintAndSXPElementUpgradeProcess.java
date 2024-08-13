/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;

/**
 * @author Gustavo Lima
 */
public class SXPBlueprintAndSXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPElement();

		_upgradeSXPBlueprint();
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"SXPElement", "fallbackDescription STRING null",
				"fallbackTitle VARCHAR(500) null")
		};
	}

	private String _addFieldsToElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		if ((elementInstancesJSON == null) || elementInstancesJSON.isEmpty()) {
			return elementInstancesJSON;
		}

		JSONArray elementInstancesJSONArray = JSONFactoryUtil.createJSONArray(
			elementInstancesJSON);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select sxpElementId, title, description from SXPElement " +
					"where sxpElementId = ?")) {

			for (int i = 0; i < elementInstancesJSONArray.length(); i++) {
				JSONObject elementInstanceJSONObject =
					elementInstancesJSONArray.getJSONObject(i);

				if (elementInstanceJSONObject == null) {
					continue;
				}

				JSONObject sxpElementJSONObject =
					elementInstanceJSONObject.getJSONObject("sxpElement");

				if (sxpElementJSONObject == null) {
					continue;
				}

				preparedStatement.setLong(
					1, sxpElementJSONObject.getLong("id"));

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						sxpElementJSONObject.put(
							"fallbackDescription",
							_getDefaultValue(
								"Description",
								resultSet.getString("description"))
						).put(
							"fallbackTitle",
							_getDefaultValue(
								"Title", resultSet.getString("title"))
						);
					}
					else {
						sxpElementJSONObject.put(
							"fallbackDescription",
							_getFirstLocalizedValue(
								sxpElementJSONObject.getJSONObject(
									"title_i18n"))
						).put(
							"fallbackTitle",
							_getFirstLocalizedValue(
								sxpElementJSONObject.getJSONObject(
									"description_i18n"))
						);
					}
				}
			}
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqlException);
			}
		}

		return elementInstancesJSONArray.toString();
	}

	private String _getDefaultValue(String fieldName, String xml) {
		if (!Validator.isBlank(xml)) {
			int start = xml.indexOf(">", xml.indexOf("<" + fieldName));

			int end = xml.indexOf("<", start);

			if ((end != -1) && (start != -1)) {
				return xml.substring(start + 1, end);
			}
		}

		return StringPool.BLANK;
	}

	private Object _getFirstLocalizedValue(JSONObject i18nJSONObject) {
		Iterator<String> iterator = i18nJSONObject.keys();

		if (!iterator.hasNext()) {
			return StringPool.BLANK;
		}

		return i18nJSONObject.get(iterator.next());
	}

	private void _upgradeSXPBlueprint() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpBlueprintId, elementInstancesJSON from " +
					"SXPBlueprint");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ? where " +
						"sxpBlueprintId = ?")) {

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setString(
						1,
						_addFieldsToElementInstancesJSON(
							resultSet1.getString("elementInstancesJSON")));
					preparedStatement2.setLong(
						2, resultSet1.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSXPElement() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpElementId, title, description from SXPElement");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPElement set fallbackDescription = ?, " +
						"fallbackTitle = ? where sxpElementId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						_getDefaultValue(
							"Description", resultSet.getString("description")));
					preparedStatement2.setString(
						2,
						_getDefaultValue(
							"Title", resultSet.getString("title")));
					preparedStatement2.setLong(
						3, resultSet.getLong("sxpElementId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintAndSXPElementUpgradeProcess.class);

}