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

package com.liferay.frontend.editors.web.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=tinymce"}, service = EditorConfigContributor.class
)
public class TinyMCEEditorConfigContributor
	extends BaseTinyMCEEditorConfigConfigurator {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			liferayPortletResponse);

		jsonObject.put("mode", "exact");

		boolean showSource = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:showSource"));

		jsonObject.put("plugins", getPluginsJSONArray(showSource));
		jsonObject.put("style_formats", getStyleFormatsJSONArray());
		jsonObject.put(
			"toolbar",
			getToolbarJSONArray(
				inputEditorTaglibAttributes, themeDisplay, showSource));
	}

	protected JSONArray getPluginsJSONArray(boolean showSource) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"advlist autolink autosave link image lists charmap print " +
				"preview hr anchor");

		jsonArray.put("searchreplace wordcount fullscreen media");

		if (showSource) {
			jsonArray.put("code");
		}

		jsonArray.put(
			"table contextmenu emoticons textcolor paste fullpage textcolor " +
				"colorpicker textpattern");

		return jsonArray;
	}

	protected JSONArray getStyleFormatsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String styleFormats[] = new String[] {
			"{inline: 'p', title: 'Normal'}",
			"{block: 'h1', title: 'Heading 1'}",
			"{block: 'h2', title: 'Heading 2'}",
			"{block: 'h3', title: 'Heading 3'}",
			"{block: 'h4', title: 'Heading 4'}",
			"{block: 'pre', title: 'Preformatted Text'}",
			"{inline: 'cite', title: 'Cited Work'}",
			"{inline: 'code', title: 'Computer Code'}",
			"{block: 'div', classes: 'portlet-msg-info', " +
				"title: 'Info Message'}",
			"{block: 'div', classes: 'portlet-msg-alert', " +
				"title: 'Alert Message'}",
			"{block: 'div', classes: 'portlet-msg-error', " +
				"title: 'Error Message'}"
		};

		for (String styleFormat : styleFormats) {
			try {
				jsonArray.put(JSONFactoryUtil.createJSONObject(styleFormat));
			}
			catch (JSONException jsone) {
				_log.error(
					"Unable to create a JSON object from: " + styleFormat,
					jsone);
			}
		}

		return jsonArray;
	}

	protected JSONArray getToolbarJSONArray(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay, boolean showSource) {

		JSONObject toolbarsJSONObject = getToolbarsJSONObject(showSource);

		String toolbarSet = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:toolbarSet");

		String currentToolbarSet = TextFormatter.format(
			HtmlUtil.escapeJS(toolbarSet), TextFormatter.M);

		if (BrowserSnifferUtil.isMobile(themeDisplay.getRequest())) {
			currentToolbarSet = "phone";
		}

		JSONArray currentToolbar = toolbarsJSONObject.getJSONArray(
			currentToolbarSet);

		if (currentToolbar == null) {
			currentToolbar = toolbarsJSONObject.getJSONArray("liferay");
		}

		return currentToolbar;
	}

	protected JSONArray getToolbarsEmailJSONArray(boolean showSource) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String firstRowButtons =
			"fontselect fontsizeselect | forecolor backcolor | " +
				"bold italic underline strikethrough | " +
					"alignleft aligncenter alignright alignjustify";

		jsonArray.put(firstRowButtons);

		String secondRowButtons =
			"cut copy paste bullist numlist | blockquote | undo redo | " +
				"link unlink image ";

		if (showSource) {
			secondRowButtons += "code ";
		}

		secondRowButtons += "| hr removeformat | preview print fullscreen";

		jsonArray.put(secondRowButtons);

		return jsonArray;
	}

	protected JSONObject getToolbarsJSONObject(boolean showSource) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("email", getToolbarsEmailJSONArray(showSource));
		jsonObject.put("liferay", getToolbarsLiferayJSONArray(showSource));
		jsonObject.put("phone", getToolbarsPhoneJSONArray());
		jsonObject.put("simple", getToolbarsSimpleJSONArray(showSource));
		jsonObject.put("tablet", getToolbarsTabletJSONArray(showSource));

		return jsonObject;
	}

	protected JSONArray getToolbarsLiferayJSONArray(boolean showSource) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | forecolor backcolor | " +
				"bold italic underline strikethrough | " +
					"alignleft aligncenter alignright alignjustify");

		String secondRowButtons =
			"cut copy paste searchreplace bullist numlist | " +
				"outdent indent blockquote | undo redo | " +
					"link unlink anchor image media ";

		if (showSource) {
			secondRowButtons += "code";
		}

		jsonArray.put(secondRowButtons);
		jsonArray.put(
			"table | hr removeformat | subscript superscript | " +
			"charmap emoticons | preview print fullscreen");

		return jsonArray;
	}

	protected JSONArray getToolbarsPhoneJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("bold italic underline | bullist numlist");
		jsonArray.put("link unlink image");

		return jsonArray;
	}

	protected JSONArray getToolbarsSimpleJSONArray(boolean showSource) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String firstRowButtons =
			"bold italic underline strikethrough | bullist numlist | table | " +
				"link unlink image";

		if (showSource) {
			firstRowButtons += " code";
		}

		jsonArray.put(firstRowButtons);

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(boolean showSource) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | " +
				"bold italic underline strikethrough | " +
					"alignleft aligncenter alignright alignjustify");

		String secondRowButtons = "bullist numlist | link unlink image";

		if (showSource) {
			secondRowButtons += " code";
		}

		jsonArray.put(secondRowButtons);

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TinyMCEEditorConfigContributor.class);

}