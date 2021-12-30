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

import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=alloyeditor_bbcode",
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

		jsonObject.put(
			"allowedContent", Boolean.TRUE
		).put(
			"enterMode", 2
		);

		String extraPlugins = jsonObject.getString("extraPlugins");

		extraPlugins = extraPlugins.concat(",bbcode,itemselector");

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"format_tags", "p;pre"
		).put(
			"lang", _getLangJSONObject(inputEditorTaglibAttributes)
		).put(
			"newThreadURL", MBThreadConstants.NEW_THREAD_URL
		);

		String removePlugins = jsonObject.getString("removePlugins");

		jsonObject.put(
			"removePlugins",
			StringBundler.concat(
				removePlugins, ",",
				StringBundler.concat(
					"bidi,div,font,forms,indentblock,keystrokes,maximize,",
					"newpage,pagebreak,preview,print,save,showblocks,smiley,",
					"stylescombo,templates,video"))
		).put(
			"smiley_images",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonFiles())
		).put(
			"smiley_path",
			HtmlUtil.escape(themeDisplay.getPathThemeImages()) + "/emoticons/"
		).put(
			"smiley_symbols",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonSymbols())
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass, int type) {

		return JSONUtil.put(
			"name", styleFormatName
		).put(
			"style", getStyleJSONObject(element, cssClass, type)
		);
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "normal"), "p", null,
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "cited-work"), "cite", null,
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "computer-code"), "code", null,
				_CKEDITOR_STYLE_INLINE));
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		return JSONUtil.put(
			"cfg", JSONUtil.put("styles", getStyleFormatsJSONArray(locale))
		).put(
			"name", "styles"
		);
	}

	protected JSONObject getStyleJSONObject(
		String element, String cssClass, int type) {

		return JSONUtil.put(
			"attributes",
			() -> {
				if (Validator.isNotNull(cssClass)) {
					return JSONUtil.put("class", cssClass);
				}

				return null;
			}
		).put(
			"element", element
		).put(
			"type", type
		);
	}

	protected JSONObject getToolbarsAddJSONObject() {
		return JSONUtil.put(
			"buttons", JSONUtil.put("image")
		).put(
			"tabIndex", 2
		);
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		return JSONUtil.put(
			"add", getToolbarsAddJSONObject()
		).put(
			"styles", getToolbarsStylesJSONObject(locale)
		);
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		return JSONUtil.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale)
		).put(
			"tabIndex", 1
		);
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale));
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		return JSONUtil.put(
			"buttons", toJSONArray("['linkEditBrowse']")
		).put(
			"name", "link"
		).put(
			"test", "AlloyEditor.SelectionTest.link"
		);
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				getStyleFormatsJSONObject(locale), "bold", "italic",
				"underline", "ol", "ul", "linkBrowse", "quote")
		).put(
			"name", "text"
		).put(
			"test", "AlloyEditor.SelectionTest.text"
		);
	}

	private JSONObject _getLangJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.put(
			"code",
			LanguageUtil.get(
				getContentsLocale(inputEditorTaglibAttributes), "code"));
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

}