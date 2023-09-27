/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_4_1;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;
import java.util.Objects;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryLinkEditableValuesUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId,editableValues,rendererKey from " +
					"FragmentEntryLink where rendererKey like " +
						"'BASIC_COMPONENT%'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FragmentEntryLink set editableValues = ? where " +
						"fragmentEntryLinkId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						resultSet.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						FragmentEntryProcessorConstants.
							KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

				if (configurationJSONObject == null) {
					continue;
				}

				_replaceAlign(configurationJSONObject);
				_replaceBorderRadius(configurationJSONObject);
				_replaceBottomSpacing(configurationJSONObject);
				_replaceShadow(configurationJSONObject);
				_replaceTextColor(configurationJSONObject);

				String rendererKey = resultSet.getString("rendererKey");

				if (Objects.equals(rendererKey, "BASIC_COMPONENT-separator") &&
					configurationJSONObject.has("borderColor")) {

					configurationJSONObject.put(
						"separatorColor",
						configurationJSONObject.remove("borderColor"));
				}

				if (Objects.equals(rendererKey, "BASIC_COMPONENT-video")) {
					if (configurationJSONObject.has("height")) {
						configurationJSONObject.put(
							"videoHeight",
							configurationJSONObject.remove("height"));
					}

					if (configurationJSONObject.has("width")) {
						configurationJSONObject.put(
							"videoWidth",
							configurationJSONObject.remove("width"));
					}
				}

				preparedStatement2.setString(1, editablesJSONObject.toString());
				preparedStatement2.setLong(
					2, resultSet.getLong("fragmentEntryLinkId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _replaceAlign(JSONObject configurationJSONObject) {
		for (String key : _ALIGN_KEYS) {
			if (!configurationJSONObject.has(key)) {
				continue;
			}

			configurationJSONObject.put(
				"textAlign", configurationJSONObject.remove(key));

			break;
		}
	}

	private void _replaceBorderRadius(JSONObject configurationJSONObject) {
		if (!configurationJSONObject.has("borderRadius")) {
			return;
		}

		configurationJSONObject.put(
			"borderRadius",
			_borderRadiuses.get(
				configurationJSONObject.remove("borderRadius")));
	}

	private void _replaceBottomSpacing(JSONObject configurationJSONObject) {
		if (!configurationJSONObject.has("bottomSpacing")) {
			return;
		}

		configurationJSONObject.put(
			"marginBottom", configurationJSONObject.remove("bottomSpacing"));
	}

	private void _replaceShadow(JSONObject configurationJSONObject) {
		if (!configurationJSONObject.has("boxShadow")) {
			return;
		}

		String shadowCssClass = GetterUtil.getString(
			configurationJSONObject.remove("boxShadow"));

		if (!_shadows.containsKey(shadowCssClass)) {
			return;
		}

		configurationJSONObject.put("shadow", _shadows.get(shadowCssClass));
	}

	private void _replaceTextColor(JSONObject configurationJSONObject) {
		JSONObject textColorJSONObject = configurationJSONObject.getJSONObject(
			"textColor");

		if (textColorJSONObject == null) {
			return;
		}

		if (Validator.isNotNull(textColorJSONObject.getString("cssClass"))) {
			configurationJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("cssClass"),
					textColorJSONObject.getString("cssClass")));
		}
		else if (Validator.isNotNull(textColorJSONObject.getString("color"))) {
			configurationJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("color"),
					textColorJSONObject.getString("color")));
		}
		else if (Validator.isNotNull(
					textColorJSONObject.getString("rgbValue"))) {

			configurationJSONObject.put(
				"textColor", textColorJSONObject.getString("rgbValue"));
		}
	}

	private static final String[] _ALIGN_KEYS = {
		"buttonAlign", "contentAlign", "imageAlign"
	};

	private static final Map<String, String> _borderRadiuses =
		HashMapBuilder.put(
			"lg", "0.375rem"
		).put(
			"none", StringPool.BLANK
		).put(
			"sm", "0.1875rem"
		).build();
	private static final Map<String, String> _colors = HashMapBuilder.put(
		"danger", "#DA1414"
	).put(
		"dark", "#272833"
	).put(
		"gray-dark", "#393A4A"
	).put(
		"info", "#2E5AAC"
	).put(
		"light", "#F1F2F5"
	).put(
		"lighter", "#F7F8F9"
	).put(
		"primary", "#0B5FFF"
	).put(
		"secondary", "#6B6C7E"
	).put(
		"success", "#287D3C"
	).put(
		"warning", "#B95000"
	).put(
		"white", "#FFFFFF"
	).build();
	private static final Map<String, String> _shadows = HashMapBuilder.put(
		"lg", "0 1rem 3rem rgba(0, 0, 0, .175)"
	).put(
		"sm", "0 .125rem .25rem rgba(0, 0, 0, .075)"
	).build();

}