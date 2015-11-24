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

package com.liferay.frontend.editor.ckeditor.web.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;
import com.liferay.portlet.messageboards.model.MBThreadConstants;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=ckeditor_bbcode"},
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

		jsonObject.put("allowedContent", Boolean.TRUE);
		jsonObject.put("enterMode", 2);
		jsonObject.put(
			"extraPlugins", "a11yhelpbtn,bbcode,itemselector,wikilink");
		jsonObject.put(
			"fontSize_sizes",
			"10/10px;12/12px;14/14px;16/16px;18/18px;24/24px;32/32px;48/48px");
		jsonObject.put("fontSize_defaultLabel", "14");
		jsonObject.put("format_tags", "p;pre");
		jsonObject.put(
			"imagesPath",
			HtmlUtil.escape(themeDisplay.getPathThemeImages()) +
				"/message_boards/");
		jsonObject.put("lang", getLangJSONObject(inputEditorTaglibAttributes));
		jsonObject.put("newThreadURL", MBThreadConstants.NEW_THREAD_URL);
		jsonObject.put(
			"removePlugins",
			"bidi,div,elementspath,flash,forms,indentblock,keystrokes,link," +
				"maximize,newpage,pagebreak,preview,print,save,showblocks," +
					"templates,video");
		jsonObject.put(
			"smiley_descriptions",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonDescriptions()));
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
			"toolbar_bbcode",
			getToolbarsBBCodeJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_phone",
			getToolbarsPhoneJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_tablet",
			getToolbarsTabletJSONArray(inputEditorTaglibAttributes));
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

	protected JSONArray getToolbarsBBCodeJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"));
		jsonArray.put(toJSONArray("['TextColor']"));
		jsonArray.put(
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', " +
					"'-', 'Blockquote', '-', 'Code']"));
		jsonArray.put("/");
		jsonArray.put(toJSONArray("['Format', 'Font', 'FontSize']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));
		jsonArray.put(toJSONArray("['ImageSelector', '-', 'Smiley']"));
		jsonArray.put("/");
		jsonArray.put(
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

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline']"));
		jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));
		jsonArray.put(toJSONArray("['ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"));
		jsonArray.put(
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"));
		jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));
		jsonArray.put(toJSONArray("['Styles', 'FontSize']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));
		jsonArray.put(toJSONArray("['ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

}