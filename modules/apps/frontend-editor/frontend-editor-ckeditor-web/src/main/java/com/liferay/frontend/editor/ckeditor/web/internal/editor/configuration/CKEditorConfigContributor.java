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

import com.liferay.document.library.kernel.util.AudioProcessorUtil;
import com.liferay.frontend.editor.ckeditor.web.internal.constants.CKEditorConstants;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = {"editor.name=ckeditor", "editor.name=ckeditor_classic"},
	service = EditorConfigContributor.class
)
public class CKEditorConfigContributor extends BaseCKEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put(
			"autoSaveTimeout", 3000
		).put(
			"closeNoticeTimeout", 8000
		).put(
			"entities", Boolean.FALSE
		);

		String extraPlugins =
			"addimages,autogrow,autolink,colordialog,filebrowser," +
				"itemselector,lfrpopup,media,stylescombo,videoembed";

		boolean inlineEdit = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEdit"));

		if (inlineEdit) {
			extraPlugins += ",ajaxsave,restore";
		}

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"filebrowserWindowFeatures",
			"title=" + LanguageUtil.get(themeDisplay.getLocale(), "browse")
		).put(
			"pasteFromWordRemoveFontStyles", Boolean.FALSE
		).put(
			"pasteFromWordRemoveStyles", Boolean.FALSE
		).put(
			"removePlugins", "elementspath"
		).put(
			"stylesSet", _getStyleFormatsJSONArray(themeDisplay.getLocale())
		).put(
			"title", false
		);

		JSONArray toolbarSimpleJSONArray = _getToolbarSimpleJSONArray(
			inputEditorTaglibAttributes);

		jsonObject.put(
			"toolbar_editInPlace", toolbarSimpleJSONArray
		).put(
			"toolbar_email", toolbarSimpleJSONArray
		).put(
			"toolbar_liferay", toolbarSimpleJSONArray
		).put(
			"toolbar_liferayArticle", toolbarSimpleJSONArray
		).put(
			"toolbar_phone", toolbarSimpleJSONArray
		).put(
			"toolbar_simple", toolbarSimpleJSONArray
		).put(
			"toolbar_tablet", toolbarSimpleJSONArray
		).put(
			"toolbar_text_advanced",
			_getToolbarTextAdvancedJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_text_simple",
			_getToolbarTextSimpleJSONArray(inputEditorTaglibAttributes)
		);
	}

	private JSONObject _getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass) {

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
			"name", styleFormatName
		);
	}

	private JSONArray _getStyleFormatsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "normal"), "p", null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "1"), "h1", null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "2"), "h2", null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "3"), "h3", null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "4"), "h4", null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "preformatted-text"), "pre", null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "cited-work"), "cite", null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "computer-code"), "code", null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "info-message"), "div",
				"overflow-auto portlet-msg-info"),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "alert-message"), "div",
				"overflow-auto portlet-msg-alert"),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "error-message"), "div",
				"overflow-auto portlet-msg-error"));
	}

	private JSONArray _getToolbarSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"),
			toJSONArray("['Styles', 'Bold', 'Italic', 'Underline']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', Unlink]"),
			toJSONArray("['Table', 'ImageSelector', 'VideoSelector']")
		).put(
			() -> {
				if (AudioProcessorUtil.isEnabled()) {
					return toJSONArray("['AudioSelector']");
				}

				return null;
			}
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source', 'Expand']");
				}

				return null;
			}
		);
	}

	private JSONArray _getToolbarTextAdvancedJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"), toJSONArray("['Styles']"),
			toJSONArray("['FontColor', 'BGColor']"),
			toJSONArray("['Bold', 'Italic', 'Underline', 'Strikethrough']"),
			toJSONArray("['RemoveFormat']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['IncreaseIndent', 'DecreaseIndent']"),
			toJSONArray("['IncreaseIndent', 'DecreaseIndent']"),
			toJSONArray("['Link', Unlink]")
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source', 'Expand']");
				}

				return null;
			}
		);
	}

	private JSONArray _getToolbarTextSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"),
			toJSONArray("['Styles', 'Bold', 'Italic', 'Underline']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', Unlink]")
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source', 'Expand']");
				}

				return null;
			}
		);
	}

}