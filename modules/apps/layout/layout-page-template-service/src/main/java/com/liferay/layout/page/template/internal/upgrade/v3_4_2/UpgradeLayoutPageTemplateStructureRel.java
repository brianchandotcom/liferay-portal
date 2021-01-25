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

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutPageTemplateStructureRel extends UpgradeProcess {

	public UpgradeLayoutPageTemplateStructureRel(
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser) {

		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateStructureRel();
	}

	private boolean _isEmpty(JSONObject jsonObject) {
		if (jsonObject == null) {
			return true;
		}

		Set<String> keySet = jsonObject.keySet();

		return keySet.isEmpty();
	}

	private void _replaceAlign(
		JSONObject fragmentConfigValuesJSONObject,
		JSONObject stylesJSONObject) {

		for (String key : _ALIGN_KEYS) {
			if (!fragmentConfigValuesJSONObject.has(key)) {
				continue;
			}

			stylesJSONObject.put(
				"textAlign", fragmentConfigValuesJSONObject.get(key));

			break;
		}
	}

	private void _replaceBorderRadius(
		JSONObject fragmentConfigValuesJSONObject,
		JSONObject stylesJSONObject) {

		if (!fragmentConfigValuesJSONObject.has("borderRadius")) {
			return;
		}

		String borderRadius = fragmentConfigValuesJSONObject.getString(
			"borderRadius");

		if (_borderRadiuses.containsKey(borderRadius)) {
			stylesJSONObject.put(
				"borderRadius", _borderRadiuses.get(borderRadius));
		}
	}

	private void _replaceBottomSpacing(
		JSONObject fragmentConfigDefaultValuesJSONObject,
		JSONObject fragmentConfigValuesJSONObject,
		JSONObject stylesJSONObject) {

		if (!fragmentConfigDefaultValuesJSONObject.has("bottomSpacing") &&
			!fragmentConfigDefaultValuesJSONObject.has("marginBottom") &&
			!fragmentConfigValuesJSONObject.has("bottomSpacing") &&
			!fragmentConfigValuesJSONObject.has("marginBottom")) {

			return;
		}

		String marginBottom = fragmentConfigValuesJSONObject.getString(
			"bottomSpacing",
			fragmentConfigDefaultValuesJSONObject.getString("bottomSpacing"));

		if (Validator.isNull(marginBottom)) {
			marginBottom = fragmentConfigValuesJSONObject.getString(
				"marginBottom",
				fragmentConfigDefaultValuesJSONObject.getString(
					"marginBottom"));
		}

		stylesJSONObject.put("marginBottom", marginBottom);
	}

	private void _replaceShadow(
		JSONObject fragmentConfigValuesJSONObject,
		JSONObject stylesJSONObject) {

		if (!fragmentConfigValuesJSONObject.has("boxShadow")) {
			return;
		}

		String shadowCssClass = fragmentConfigValuesJSONObject.getString(
			"boxShadow");

		if (_shadows.containsKey(shadowCssClass)) {
			stylesJSONObject.put("shadow", _shadows.get(shadowCssClass));
		}
	}

	private void _replaceTextColor(
		JSONObject fragmentConfigValuesJSONObject,
		JSONObject stylesJSONObject) {

		JSONObject textColorJSONObject =
			fragmentConfigValuesJSONObject.getJSONObject("textColor");

		if (textColorJSONObject == null) {
			return;
		}

		if (Validator.isNotNull(textColorJSONObject.getString("cssClass"))) {
			stylesJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("cssClass"),
					textColorJSONObject.getString("cssClass")));
		}
		else if (Validator.isNotNull(textColorJSONObject.getString("color"))) {
			stylesJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("color"),
					textColorJSONObject.getString("color")));
		}
		else if (Validator.isNotNull(
					textColorJSONObject.getString("rgbValue"))) {

			stylesJSONObject.put(
				"textColor", textColorJSONObject.getString("rgbValue"));
		}
	}

	private String _upgradeLayoutData(String data) throws Exception {
		LayoutStructure layoutStructure = LayoutStructure.of(data);

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)layoutStructureItem;

				Map<String, JSONObject> viewportConfigurations =
					columnLayoutStructureItem.getViewportConfigurations();

				JSONObject mobileLandscapeJSONObject =
					viewportConfigurations.get(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

				JSONObject portraitMobileJSONObject =
					viewportConfigurations.get(
						ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());

				JSONObject tabletJSONObject = viewportConfigurations.get(
					ViewportSize.TABLET.getViewportSizeId());

				if (_isEmpty(mobileLandscapeJSONObject) &&
					_isEmpty(portraitMobileJSONObject) &&
					_isEmpty(tabletJSONObject)) {

					columnLayoutStructureItem.setViewportConfiguration(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
						JSONUtil.put("size", "12"));
				}
			}

			if (layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)layoutStructureItem;

				JSONObject itemConfigJSONObject =
					fragmentStyledLayoutStructureItem.getItemConfigJSONObject();

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				if (stylesJSONObject == null) {
					stylesJSONObject = JSONFactoryUtil.createJSONObject();

					itemConfigJSONObject.put("styles", stylesJSONObject);
				}

				FragmentEntryLink fragmentEntryLink =
					FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
						fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkId());

				JSONObject editableValuesJSONObject =
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues());

				JSONObject fragmentConfigValuesJSONObject =
					editableValuesJSONObject.getJSONObject(
						"com.liferay.fragment.entry.processor.freemarker." +
							"FreeMarkerFragmentEntryProcessor");

				_replaceBottomSpacing(
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(
							fragmentEntryLink.getConfiguration()),
					fragmentConfigValuesJSONObject, stylesJSONObject);

				if (_isEmpty(fragmentConfigValuesJSONObject)) {
					continue;
				}

				_replaceAlign(fragmentConfigValuesJSONObject, stylesJSONObject);
				_replaceBorderRadius(
					fragmentConfigValuesJSONObject, stylesJSONObject);
				_replaceShadow(
					fragmentConfigValuesJSONObject, stylesJSONObject);
				_replaceTextColor(
					fragmentConfigValuesJSONObject, stylesJSONObject);
			}
		}

		JSONObject jsonObject = layoutStructure.toJSONObject();

		return jsonObject.toString();
	}

	private void _upgradeLayoutPageTemplateStructureRel() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select lPageTemplateStructureRelId, segmentsExperienceId, " +
					"data_ from LayoutPageTemplateStructureRel");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateStructureRel set data_ = ? " +
						"where lPageTemplateStructureRelId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateStructureRelId = rs.getLong(
					"lPageTemplateStructureRelId");

				String data = rs.getString("data_");

				ps.setString(1, _upgradeLayoutData(data));

				ps.setLong(2, layoutPageTemplateStructureRelId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private static final String[] _ALIGN_KEYS = {
		"buttonAlign", "contentAlign", "imageAlign", "textAlign"
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

	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;

}