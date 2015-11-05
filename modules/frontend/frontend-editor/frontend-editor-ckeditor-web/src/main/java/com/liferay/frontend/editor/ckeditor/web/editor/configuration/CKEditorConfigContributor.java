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

import com.liferay.frontend.editor.lang.FrontendEditorLang;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=ckeditor"}, service = EditorConfigContributor.class
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

		jsonObject.put("autoParagraph", Boolean.FALSE);
		jsonObject.put("autoSaveTimeout", 3000);

		ColorScheme colorScheme = themeDisplay.getColorScheme();

		String cssClasses = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:cssClasses");

		jsonObject.put(
			"bodyClass",
			"html-editor " + HtmlUtil.escape(colorScheme.getCssClass()) + " " +
				HtmlUtil.escape(cssClasses));

		jsonObject.put("closeNoticeTimeout", 8000);
		jsonObject.put("entities", Boolean.FALSE);

		String extraPlugins = "a11yhelpbtn,itemselector,lfrpopup,media";

		boolean inlineEdit = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:inlineEdit"));

		if (inlineEdit) {
			extraPlugins += ",ajaxsave,restore";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		jsonObject.put(
			"filebrowserWindowFeatures",
			"title=" + LanguageUtil.get(themeDisplay.getLocale(), "browse"));
		jsonObject.put("pasteFromWordRemoveFontStyles", Boolean.FALSE);
		jsonObject.put("pasteFromWordRemoveStyles", Boolean.FALSE);
		jsonObject.put(
			"stylesSet", getStyleFormatsJSONArray(themeDisplay.getLocale()));
		jsonObject.put(
			"toolbar_editInPlace",
			getToolbarEditInPlaceJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_email",
			getToolbarEmailJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_liferay",
			getToolbarLiferayJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_liferayArticle",
			getToolbarLiferayArticleJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_phone",
			getToolbarPhoneJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_simple",
			getToolbarSimpleJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_tablet",
			getToolbarTabletJSONArray(inputEditorTaglibAttributes));
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject =
				JSONFactoryUtil.createJSONObject();

			attributesJSONObject.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put("name", styleFormatName);
		styleJSONObject.put("element", element);

		return styleJSONObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		resourceBundle = new AggregateResourceBundle(
			resourceBundle,
			ResourceBundleUtil.getBundle(
				"content.Language", locale, FrontendEditorLang.class));

		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code",
				null));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "div",
				"portlet-msg-info"));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "div",
				"portlet-msg-alert"));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "div",
				"portlet-msg-error"));

		return jsonArray;
	}

	protected JSONArray getToolbarEditInPlaceJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
		jsonArray.put("/");
		jsonArray.put(toJSONArray("['Styles']"));
		jsonArray.put(
			toJSONArray("['SpellChecker', 'Scayt', '-', 'SpecialChar']"));
		jsonArray.put(toJSONArray("['Undo', 'Redo']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarEmailJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', " +
					"'RemoveFormat']"));
		jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));
		jsonArray.put(
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"));
		jsonArray.put(toJSONArray("['FontSize']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));
		jsonArray.put(toJSONArray("['ImageSelector']"));
		jsonArray.put("/");
		jsonArray.put(
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll', '-', 'Undo', 'Redo' ]"));
		jsonArray.put(toJSONArray("['SpellChecker', 'Scayt']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarLiferayArticleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"));
		jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));
		jsonArray.put(
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-' ,'Outdent', 'Indent', " +
					"'-', 'Blockquote']"));
		jsonArray.put("/");
		jsonArray.put(toJSONArray("['Styles', 'FontSize']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));

		String buttons = "['Table', '-', 'ImageSelector',";

		if (XugglerUtil.isEnabled()) {
			buttons += " 'AudioSelector', 'VideoSelector',";
		}

		buttons += " 'Flash', '-', 'LiferayPageBreak', '-', " +
			"'Smiley', 'SpecialChar']";

		jsonArray.put(toJSONArray(buttons));

		jsonArray.put("/");
		jsonArray.put(
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll' , '-', 'Undo', 'Redo']"));
		jsonArray.put(
			toJSONArray("['Find', 'Replace', '-', 'SpellChecker', 'Scayt']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarLiferayJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"));
		jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));
		jsonArray.put(
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
		jsonArray.put("/");
		jsonArray.put(toJSONArray("['Styles', 'FontSize']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));

		String buttons = "['Table', '-', 'ImageSelector',";

		if (XugglerUtil.isEnabled()) {
			buttons = buttons.concat(" 'AudioSelector', 'VideoSelector',");
		}

		buttons = buttons.concat(" 'Flash', '-', 'Smiley', 'SpecialChar']");

		jsonArray.put(toJSONArray(buttons));

		jsonArray.put("/");

		boolean inlineEdit = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:inlineEdit"));

		if (inlineEdit) {
			jsonArray.put(toJSONArray("['AjaxSave', '-', 'Restore']"));
		}

		jsonArray.put(
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll' , '-', 'Undo', 'Redo']"));
		jsonArray.put(
			toJSONArray("['Find', 'Replace', '-', 'SpellChecker', 'Scayt']"));

		if (!inlineEdit && isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarPhoneJSONArray(
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

	protected JSONArray getToolbarSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"));
		jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));
		jsonArray.put(toJSONArray("['Table', 'ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarTabletJSONArray(
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