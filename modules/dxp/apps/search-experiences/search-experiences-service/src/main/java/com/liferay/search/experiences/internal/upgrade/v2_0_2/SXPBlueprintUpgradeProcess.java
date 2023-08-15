/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v2_0_2;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;

import java.io.IOException;

import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPBlueprints();

		_upgradeSXPElements();
	}

	private Map<String, SXPElement> _getSXPElements() {
		if (_sxpElements != null) {
			return _sxpElements;
		}

		Bundle bundle = FrameworkUtil.getBundle(CompanyModelListener.class);

		Package pkg = CompanyModelListener.class.getPackage();

		String path = StringUtil.replace(
			pkg.getName(), CharPool.PERIOD, CharPool.SLASH);

		_sxpElements = new HashMap<>();

		Enumeration<URL> enumeration = bundle.findEntries(
			path.concat("/dependencies"), "*.json", false);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				SXPElement sxpElement = SXPElementUtil.toSXPElement(
					StreamUtil.toString(url.openStream()));

				_sxpElements.put(
					sxpElement.getExternalReferenceCode(), sxpElement);
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return _sxpElements;
	}

	private String _upgradeElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		if (elementInstancesJSON.equals("[]") ||
			elementInstancesJSON.equals("{}")) {

			return "[]";
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			elementInstancesJSON);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject1 = jsonArray.getJSONObject(i);

			JSONObject sxpElementJSONObject = jsonObject1.getJSONObject(
				"sxpElement");

			Map<String, SXPElement> sxpElements = _getSXPElements();

			SXPElement sxpElement = sxpElements.get(
				sxpElementJSONObject.getString("externalReferenceCode"));

			if (sxpElement == null) {
				continue;
			}

			Map<String, String> descriptionMap =
				sxpElement.getDescription_i18n();

			sxpElementJSONObject.put(
				"description",
				descriptionMap.get(LocaleUtil.toLanguageId(LocaleUtil.US))
			).put(
				"description_i18n",
				JSONFactoryUtil.createJSONObject(descriptionMap)
			);

			Map<String, String> titleMap = sxpElement.getTitle_i18n();

			sxpElementJSONObject.put(
				"title", titleMap.get(LocaleUtil.toLanguageId(LocaleUtil.US))
			).put(
				"title_i18n", JSONFactoryUtil.createJSONObject(titleMap)
			);
		}

		return jsonArray.toString();
	}

	private void _upgradeSXPBlueprints() throws Exception {
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
					try {
						preparedStatement2.setString(
							1,
							_upgradeElementInstancesJSON(
								resultSet.getString("elementInstancesJSON")));
						preparedStatement2.setLong(
							2, resultSet.getLong("sxpBlueprintId"));

						preparedStatement2.addBatch();
					}
					catch (RuntimeException runtimeException) {
						_log.error(
							StringBundler.concat(
								"Search experiences blueprint ",
								resultSet.getLong("sxpBlueprintId"),
								" contains corrupted element instances JSON"),
							runtimeException);
					}
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSXPElements() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select externalReferenceCode, readOnly from SXPElement");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPElement set description = ?, " +
						"elementDefinitionJSON = ?, title = ? where " +
							"externalReferenceCode = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					if (!resultSet.getBoolean("readOnly")) {
						continue;
					}

					Map<String, SXPElement> sxpElements = _getSXPElements();

					SXPElement sxpElement = sxpElements.get(
						resultSet.getString("externalReferenceCode"));

					if (sxpElement == null) {
						continue;
					}

					preparedStatement2.setString(
						1,
						LocalizationUtil.getXml(
							sxpElement.getDescription_i18n(),
							LocaleUtil.toLanguageId(LocaleUtil.US),
							"description"));
					preparedStatement2.setString(
						2, String.valueOf(sxpElement.getElementDefinition()));
					preparedStatement2.setString(
						3,
						LocalizationUtil.getXml(
							sxpElement.getTitle_i18n(),
							LocaleUtil.toLanguageId(LocaleUtil.US), "title"));
					preparedStatement2.setString(
						4, resultSet.getString("externalReferenceCode"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUpgradeProcess.class);

	private Map<String, SXPElement> _sxpElements;

}