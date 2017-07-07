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

package com.liferay.frontend.editor.tinymce.web.internal.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=tinymce"}, service = EditorConfigContributor.class
)
public class TinyMCEEditorConfigContributor
	extends BaseTinyMCEEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put("mode", "exact");
		jsonObject.put(
			"plugins", getPluginsJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"style_formats",
			getStyleFormatsJSONArray(themeDisplay.getLocale()));
		jsonObject.put(
			"toolbar",
			getToolbarJSONArray(inputEditorTaglibAttributes, themeDisplay));
	}

	@Override
	protected ItemSelector getItemSelector() {
		return _itemSelector;
	}

	protected JSONArray getPluginsJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"advlist autolink autosave link image lists charmap print " +
				"preview hr anchor");
		jsonArray.put("searchreplace wordcount fullscreen media");

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put("code");
		}

		jsonArray.put(
			"table contextmenu emoticons textcolor paste fullpage textcolor " +
				"colorpicker textpattern");

		return jsonArray;
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String type, String element,
		String cssClasses) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		styleJSONObject.put(type, element);
		styleJSONObject.put("classes", cssClasses);
		styleJSONObject.put("title", styleFormatName);

		return styleJSONObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "inline", "p",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "block",
				"h1", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "block",
				"h2", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "block",
				"h3", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "block",
				"h4", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "block",
				"pre", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "inline",
				"cite", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "inline",
				"code", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "block",
				"div", "portlet-msg-info"));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "block",
				"div", "portlet-msg-alert"));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "block",
				"div", "portlet-msg-error"));

		return jsonArray;
	}

	protected JSONArray getToolbarJSONArray(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay) {

		JSONObject toolbarsJSONObject = getToolbarsJSONObject(
			inputEditorTaglibAttributes);

		String toolbarSet = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:toolbarSet");

		String currentToolbarSet = TextFormatter.format(
			HtmlUtil.escapeJS(toolbarSet), TextFormatter.M);

		if (_browserSniffer.isMobile(themeDisplay.getRequest())) {
			currentToolbarSet = "phone";
		}

		JSONArray toolbarJSONArray = toolbarsJSONObject.getJSONArray(
			currentToolbarSet);

		if (toolbarJSONArray == null) {
			toolbarJSONArray = toolbarsJSONObject.getJSONArray("liferay");
		}

		return toolbarJSONArray;
	}

	protected JSONArray getToolbarsEmailJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"fontselect fontsizeselect | forecolor backcolor | bold italic " +
				"underline strikethrough | alignleft aligncenter alignright " +
					"alignjustify");

		String buttons =
			"cut copy paste bullist numlist | blockquote | undo redo | link " +
				"unlink image ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code ";
		}

		buttons += "| hr removeformat | preview print fullscreen";

		jsonArray.put(buttons);

		return jsonArray;
	}

	protected JSONObject getToolbarsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"email", getToolbarsEmailJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"liferay",
			getToolbarsLiferayJSONArray(inputEditorTaglibAttributes));
		jsonObject.put("phone", getToolbarsPhoneJSONArray());
		jsonObject.put(
			"simple", getToolbarsSimpleJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"tablet", getToolbarsTabletJSONArray(inputEditorTaglibAttributes));

		return jsonObject;
	}

	protected JSONArray getToolbarsLiferayJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | forecolor backcolor | " +
				"bold italic underline strikethrough | alignleft aligncenter " +
					"alignright alignjustify");

		String buttons =
			"cut copy paste searchreplace bullist numlist | outdent indent " +
				"blockquote | undo redo | link unlink anchor image media ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code";
		}

		jsonArray.put(buttons);

		jsonArray.put(
			"table | hr removeformat | subscript superscript | charmap " +
				"emoticons | preview print fullscreen");

		return jsonArray;
	}

	protected JSONArray getToolbarsPhoneJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("bold italic underline | bullist numlist");
		jsonArray.put("link unlink image");

		return jsonArray;
	}

	protected JSONArray getToolbarsSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String buttons =
			"bold italic underline strikethrough | bullist numlist | table | " +
				"link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		jsonArray.put(buttons);

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | bold italic underline " +
				"strikethrough | alignleft aligncenter alignright " +
					"alignjustify");

		String buttons = "bullist numlist | link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		jsonArray.put(buttons);

		return jsonArray;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	@Reference
	private BrowserSniffer _browserSniffer;

	@Reference
	private ItemSelector _itemSelector;

	private volatile ResourceBundleLoader _resourceBundleLoader;

}