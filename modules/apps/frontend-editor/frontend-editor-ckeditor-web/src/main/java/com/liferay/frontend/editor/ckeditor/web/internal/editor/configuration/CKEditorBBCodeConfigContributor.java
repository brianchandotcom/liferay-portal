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

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ckeditor_bbcode",
	service = EditorConfigContributor.class
)
public class CKEditorBBCodeConfigContributor
	extends BaseCKEditorConfigContributor {

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
		).put(
			"extraPlugins", "a11yhelpbtn,bbcode,itemselector,wikilink"
		).put(
			"fontSize_defaultLabel", "14"
		).put(
			"fontSize_sizes",
			"10/10px;12/12px;14/14px;16/16px;18/18px;24/24px;32/32px;48/48px"
		).put(
			"format_tags", "p;pre"
		).put(
			"imagesPath",
			HtmlUtil.escape(themeDisplay.getPathThemeImages()) +
				"/message_boards/"
		).put(
			"lang", getLangJSONObject(inputEditorTaglibAttributes)
		).put(
			"newThreadURL", MBThreadConstants.NEW_THREAD_URL
		).put(
			"removePlugins",
			"bidi,div,elementspath,flash,forms,indentblock,keystrokes,link," +
				"maximize,newpage,pagebreak,preview,print,save,showblocks," +
					"templates,video"
		).put(
			"smiley_descriptions",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonDescriptions())
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
			"toolbar_bbcode",
			getToolbarsBBCodeJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_phone",
			getToolbarsPhoneJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_tablet",
			getToolbarsTabletJSONArray(inputEditorTaglibAttributes)
		);
	}

	protected JSONObject getLangJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.put(
			"code",
			LanguageUtil.get(
				getContentsLocale(inputEditorTaglibAttributes), "code"));
	}

	protected JSONArray getToolbarsBBCodeJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"),
			toJSONArray("['TextColor']"),
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"),
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', " +
					"'-', 'Blockquote', '-', 'Code']"),
			"/", toJSONArray("['Format', 'Font', 'FontSize']"),
			toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['ImageSelector', '-', 'Smiley']"), "/",
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'SelectAll', '-', 'Undo', " +
					"'Redo']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarsPhoneJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic', 'Underline']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"),
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Styles', 'FontSize']"),
			toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

}