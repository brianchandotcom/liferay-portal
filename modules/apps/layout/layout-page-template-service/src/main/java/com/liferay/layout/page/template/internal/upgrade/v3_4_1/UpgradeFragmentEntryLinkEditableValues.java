/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.upgrade.v3_4_1;

import com.liferay.petra.string.StringPool;
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
public class UpgradeFragmentEntryLinkEditableValues extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select fragmentEntryLinkId,editableValues,rendererKey from " +
					"FragmentEntryLink where rendererKey like " +
						"'BASIC_COMPONENT%'");
			PreparedStatement ps2 = connection.prepareStatement(
				"update FragmentEntryLink set editableValues = ? where " +
					"fragmentEntryLinkId = ?");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						rs.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						"com.liferay.fragment.entry.processor.freemarker." +
							"FreeMarkerFragmentEntryProcessor");

				if (configurationJSONObject == null) {
					continue;
				}

				_replaceAlign(configurationJSONObject);
				_replaceBottomSpacing(configurationJSONObject);
				_replaceBorderRadius(configurationJSONObject);
				_replaceShadow(configurationJSONObject);
				_replaceTextColor(configurationJSONObject);

				String rendererKey = rs.getString("rendererKey");

				if (Objects.equals(rendererKey, "BASIC_COMPONENT-separator") &&
					configurationJSONObject.has("borderColor")) {

					configurationJSONObject.put(
						"separatorColor",
						configurationJSONObject.remove("borderColor"));
				}

				ps2.setString(1, editablesJSONObject.toString());
				ps2.setLong(2, rs.getLong("fragmentEntryLinkId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
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