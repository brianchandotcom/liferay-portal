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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	property = {"editor.name=alloyeditor"},
	service = EditorConfigContributor.class
)
public class AlloyEditorConfigContributor
	extends BaseAlloyEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",itemselector,media";
		}
		else {
			extraPlugins = "itemselector,media";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		jsonObject.put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale()));
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass, int type) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("name", styleFormatName);
		jsonObject.put("style", getStyleJSONObject(element, cssClass, type));

		return jsonObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException mre) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null,
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				null, _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				null, _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				null, _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				null, _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre",
				null, _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null,
				_CKEDITOR_STYLE_INLINE));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code", null,
				_CKEDITOR_STYLE_INLINE));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "div",
				"portlet-msg-info", _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "div",
				"portlet-msg-alert", _CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "div",
				"portlet-msg-error", _CKEDITOR_STYLE_BLOCK));

		return jsonArray;
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

		stylesJSONObject.put("styles", getStyleFormatsJSONArray(locale));

		JSONObject styleFormatsJSONObject = JSONFactoryUtil.createJSONObject();

		styleFormatsJSONObject.put("cfg", stylesJSONObject);
		styleFormatsJSONObject.put("name", "styles");

		return styleFormatsJSONObject;
	}

	protected JSONObject getStyleJSONObject(
		String element, String cssClass, int type) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject =
				JSONFactoryUtil.createJSONObject();

			attributesJSONObject.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put("element", element);
		styleJSONObject.put("type", type);

		return styleJSONObject;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['image', 'table', 'hline']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddJSONObject());
		jsonObject.put("styles", getToolbarsStylesJSONObject(locale));

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale));
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsImageJSONObject() {
		JSONObject jsonNObject = JSONFactoryUtil.createJSONObject();

		jsonNObject.put(
			"buttons",
			toJSONArray("['imageLeft', 'imageCenter', 'imageRight', 'link']"));
		jsonNObject.put("name", "image");
		jsonNObject.put("test", "AlloyEditor.SelectionTest.image");

		return jsonNObject;
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getToolbarsStylesSelectionsLinkJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsImageJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTextJSONObject(locale));
		jsonArray.put(getToolbarsStylesSelectionsTableJSONObject());

		return jsonArray;
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['linkEdit']"));
		jsonObject.put("name", "link");
		jsonObject.put("test", "AlloyEditor.SelectionTest.link");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTableJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons",
			toJSONArray(
				"['tableHeading', 'tableRow', 'tableColumn', 'tableCell', " +
					"'tableRemove']"));
		jsonObject.put(
			"getArrowBoxClasses",
			"AlloyEditor.SelectionGetArrowBoxClasses.table");
		jsonObject.put("name", "table");
		jsonObject.put("setPosition", "AlloyEditor.SelectionSetPosition.table");
		jsonObject.put("test", "AlloyEditor.SelectionTest.table");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getStyleFormatsJSONObject(locale));
		jsonArray.put("bold");
		jsonArray.put("italic");
		jsonArray.put("underline");
		jsonArray.put("ol");
		jsonArray.put("ul");
		jsonArray.put("link");
		jsonArray.put("twitter");

		jsonObject.put("buttons", jsonArray);

		jsonObject.put("name", "text");
		jsonObject.put("test", "AlloyEditor.SelectionTest.text");

		return jsonObject;
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

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private volatile ResourceBundleLoader _resourceBundleLoader;

}