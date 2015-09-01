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

package com.liferay.frontend.editors.web.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.util.Locale;
import java.util.Map;
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
public class AlloyEditorConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		String contentsLanguageDir = getContentsLanguageDir(
			inputEditorTaglibAttributes);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));

		String contentsLanguageId = getContentsLanguageId(
			inputEditorTaglibAttributes);

		jsonObject.put(
			"contentsLanguage", contentsLanguageId.replace("iw_", "he_"));

		jsonObject.put(
			"extraPlugins",
			"ae_autolink,ae_dragresize,ae_addimages,ae_placeholder," +
				"ae_selectionregion,ae_tableresize,ae_tabletools,ae_uicore");

		jsonObject.put("filebrowserBrowseUrl", "");
		jsonObject.put("filebrowserImageBrowseLinkUrl", "");
		jsonObject.put("filebrowserImageBrowseUrl", "");

		String languageId = getLanguageId(themeDisplay);

		jsonObject.put("language", languageId.replace("iw_", "he_"));

		jsonObject.put(
			"removePlugins",
			"contextmenu,elementspath,image,link,liststyle,resize,tabletools," +
				"toolbar");

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:namespace"));

		String name =
			namespace +
				GetterUtil.getString(
					inputEditorTaglibAttributes.get(
						"liferay-ui:input-editor:name"));

		jsonObject.put("srcNode", name);

		jsonObject.put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale()));
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
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

		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

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
		styleFormatsJSONObject.put("name", "styles");
		styleFormatsJSONObject.put("cfg", stylesJSONObject);

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

		jsonNObject.put("buttons", toJSONArray("['imageLeft', 'imageRight']"));
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
		jsonArray.put("link");
		jsonArray.put("twitter");

		jsonObject.put("buttons", jsonArray);

		jsonObject.put("name", "text");
		jsonObject.put("test", "AlloyEditor.SelectionTest.text");

		return jsonObject;
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private ItemSelector _itemSelector;

}