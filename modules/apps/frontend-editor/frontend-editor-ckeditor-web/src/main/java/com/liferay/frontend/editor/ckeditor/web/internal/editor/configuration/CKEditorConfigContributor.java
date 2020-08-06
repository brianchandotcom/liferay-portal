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

import com.liferay.frontend.editor.ckeditor.web.internal.constants.CKEditorConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.XugglerUtil;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ckeditor", service = EditorConfigContributor.class
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

		jsonObject.put("autoSaveTimeout", 3000);

		ColorScheme colorScheme = themeDisplay.getColorScheme();

		String cssClasses = (String)inputEditorTaglibAttributes.get(
			CKEditorConstants.ATTRIBUTE_NAMESPACE + ":cssClasses");

		jsonObject.put(
			"bodyClass",
			StringBundler.concat(
				"html-editor ", HtmlUtil.escape(colorScheme.getCssClass()), " ",
				HtmlUtil.escape(cssClasses))
		).put(
			"closeNoticeTimeout", 8000
		).put(
			"entities", Boolean.FALSE
		);

		String extraPlugins =
			"a11yhelpbtn,addimages,itemselector,lfrpopup,media";

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
			"stylesSet", getStyleFormatsJSONArray(themeDisplay.getLocale())
		).put(
			"title", false
		).put(
			"toolbar_editInPlace",
			getToolbarEditInPlaceJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_email",
			getToolbarEmailJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_liferay",
			getToolbarLiferayJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_liferayArticle",
			getToolbarLiferayArticleJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_phone",
			getToolbarPhoneJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_simple",
			getToolbarSimpleJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_tablet",
			getToolbarTabletJSONArray(inputEditorTaglibAttributes)
		);
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject = JSONUtil.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put(
			"element", element
		).put(
			"name", styleFormatName
		);

		return styleJSONObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException missingResourceException) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "div",
				"portlet-msg-info"),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "div",
				"portlet-msg-alert"),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "div",
				"portlet-msg-error"));
	}

	protected JSONArray getToolbarEditInPlaceJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"),
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"),
			"/", toJSONArray("['Styles']"),
			toJSONArray("['SpellChecker', 'Scayt', '-', 'SpecialChar']"),
			toJSONArray("['Undo', 'Redo']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarEmailJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', " +
					"'RemoveFormat']"),
			toJSONArray("['TextColor', 'BGColor']"),
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"),
			toJSONArray("['FontSize']"), toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['ImageSelector']"), "/",
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll', '-', 'Undo', 'Redo' ]"),
			toJSONArray("['SpellChecker', 'Scayt']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarLiferayArticleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons = "['Table', '-', 'ImageSelector',";

		if (XugglerUtil.isEnabled()) {
			buttons += " 'AudioSelector', 'VideoSelector',";
		}

		buttons +=
			" 'Flash', '-', 'LiferayPageBreak', '-', 'Smiley', 'SpecialChar']";

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"),
			toJSONArray("['TextColor', 'BGColor']"),
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"),
			toJSONArray(
				"['NumberedList', 'BulletedList', '-' ,'Outdent', 'Indent', " +
					"'-', 'Blockquote']"),
			"/", toJSONArray("['Styles', 'FontSize']"),
			toJSONArray("['Link', 'Unlink', 'Anchor']"), toJSONArray(buttons),
			"/",
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll' , '-', 'Undo', 'Redo']"),
			toJSONArray("['Find', 'Replace', '-', 'SpellChecker', 'Scayt']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarLiferayJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons = "['Table', '-', 'ImageSelector',";

		if (XugglerUtil.isEnabled()) {
			buttons = buttons.concat(" 'AudioSelector', 'VideoSelector',");
		}

		buttons = buttons.concat(" 'Flash', '-', 'Smiley', 'SpecialChar']");

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray(
				"['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', " +
					"'Superscript', '-', 'RemoveFormat']"),
			toJSONArray("['TextColor', 'BGColor']"),
			toJSONArray(
				"['JustifyLeft', 'JustifyCenter', 'JustifyRight', " +
					"'JustifyBlock']"),
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"),
			"/", toJSONArray("['Styles', 'FontSize']"),
			toJSONArray("['Link', 'Unlink', 'Anchor']"), toJSONArray(buttons),
			"/");

		boolean inlineEdit = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEdit"));

		if (inlineEdit) {
			jsonArray.put(toJSONArray("['AjaxSave', '-', 'Restore']"));
		}

		jsonArray.put(
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll' , '-', 'Undo', 'Redo']")
		).put(
			toJSONArray("['Find', 'Replace', '-', 'SpellChecker', 'Scayt']")
		);

		if (!inlineEdit && isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarPhoneJSONArray(
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

	protected JSONArray getToolbarSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic', 'Underline', 'Strike']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['Table', 'ImageSelector']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarTabletJSONArray(
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

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}