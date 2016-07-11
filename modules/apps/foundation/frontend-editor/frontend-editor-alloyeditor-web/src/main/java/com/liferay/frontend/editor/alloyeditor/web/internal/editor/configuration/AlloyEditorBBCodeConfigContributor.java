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

import com.liferay.message.boards.kernel.model.MBThreadConstants;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = {"editor.name=alloyeditor_bbcode"},
	service = EditorConfigContributor.class
)
public class AlloyEditorBBCodeConfigContributor
	extends BaseAlloyEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put("allowedContent", Boolean.TRUE);
		jsonObject.put("enterMode", 1);

		String extraPlugins = jsonObject.getString("extraPlugins");

		extraPlugins = extraPlugins.concat(",bbcode,itemselector");

		jsonObject.put("extraPlugins", extraPlugins);

		jsonObject.put("forceEnterMode", Boolean.TRUE);
		jsonObject.put("format_tags", "p;pre");
		jsonObject.put("lang", getLangJSONObject(inputEditorTaglibAttributes));
		jsonObject.put("newThreadURL", MBThreadConstants.NEW_THREAD_URL);

		String removePlugins = jsonObject.getString("removePlugins");

		StringBundler sb = new StringBundler();

		sb.append("bidi,colorbutton,colordialog,div,flash,font,forms,");
		sb.append("indentblock,keystrokes,maximize,newpage,pagebreak,");
		sb.append("preview,print,save,showblocks,smiley,stylescombo,");
		sb.append("templates,video");

		jsonObject.put(
			"removePlugins", removePlugins.concat(",").concat(sb.toString()));

		jsonObject.put(
			"smiley_images",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonFiles()));
		jsonObject.put(
			"smiley_path",
			HtmlUtil.escape(themeDisplay.getPathThemeImages()) + "/emoticons/");
		jsonObject.put(
			"smiley_symbols",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonSymbols()));

		jsonObject.put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale()));
	}

	protected JSONObject getLangJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"code",
			LanguageUtil.get(
				getContentsLocale(inputEditorTaglibAttributes), "code"));

		return jsonObject;
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
			resourceBundle = _resourceBundleLoader.loadResourceBundle(
				LocaleUtil.toLanguageId(locale));
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
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null,
				_CKEDITOR_STYLE_INLINE));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code", null,
				_CKEDITOR_STYLE_INLINE));

		return jsonArray;
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		JSONObject styleFormatsJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

		stylesJSONObject.put("styles", getStyleFormatsJSONArray(locale));

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

		JSONArray buttonsJSONArray = JSONFactoryUtil.createJSONArray();

		buttonsJSONArray.put("image");

		jsonObject.put("buttons", buttonsJSONArray);

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

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getToolbarsStylesSelectionsLinkJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTextJSONObject(locale));

		return jsonArray;
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['linkEdit']"));
		jsonObject.put("name", "link");
		jsonObject.put("test", "AlloyEditor.SelectionTest.link");

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
		jsonArray.put("quote");

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

		ClassLoader classLoader =
			AlloyEditorConfigContributor.class.getClassLoader();

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", classLoader),
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private volatile ResourceBundleLoader _resourceBundleLoader;

}