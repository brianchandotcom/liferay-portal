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

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ambrin Chaudhary
 */
public class BaseTinyMCEEditorConfigConfigurator
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		String themeCssPath = HtmlUtil.escape(themeDisplay.getPathThemeCss());

		jsonObject.put(
			"content_css",
			themeCssPath + "/aui.css," + themeCssPath + "/main.css");

		jsonObject.put("convert_urls", Boolean.FALSE);

		jsonObject.put("extended_valid_elements", _EXTENDED_VALID_ELEMENTS);

		jsonObject.put("invalid_elements", "script");

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		jsonObject.put("language", getTinyMCELanguage(contentsLanguageId));

		jsonObject.put("menubar", Boolean.FALSE);
		jsonObject.put("mode", "textareas");
		jsonObject.put("relative_urls", Boolean.FALSE);
		jsonObject.put("remove_script_host", Boolean.FALSE);

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:namespace"));

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		jsonObject.put("selector", "#" + namespace + name);

		jsonObject.put(
			"toolbar",
			"bold italic underline | alignleft aligncenter alignright | " +
				"preview print");
		jsonObject.put("toolbar_items_size", "small");
	}

	protected String getTinyMCELanguage(String contentsLanguageId) {
		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		String tinyMCELanguage = _tinyMCELanguages.get(contentsLanguageId);

		if (Validator.isNull(tinyMCELanguage)) {
			tinyMCELanguage = _tinyMCELanguages.get("en_US");
		}

		return tinyMCELanguage;
	}

	protected boolean isShowSource(
		Map<String, Object> inputEditorTaglibAttributes) {

		return GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:showSource"));
	}

	private static final String _EXTENDED_VALID_ELEMENTS =
		"a[name|href|target|title|onclick],img[class|src|border=0" +
			"|alt|title|hspace|vspace|width|height|align|onmouseover" +
				"|onmouseout|name|usemap],hr[class|width|size|noshade]," +
					"font[face|size|color|style],span[class|align|style]";

	private static final Map<String, String> _tinyMCELanguages =
		new HashMap<>();

	static {
		_tinyMCELanguages.put("ar_SA", "ar");
		_tinyMCELanguages.put("bg_BG", "bg_BG");
		_tinyMCELanguages.put("ca_ES", "ca");
		_tinyMCELanguages.put("cs_CZ", "cs");
		_tinyMCELanguages.put("de_DE", "de");
		_tinyMCELanguages.put("el_GR", "el");
		_tinyMCELanguages.put("en_AU", "en_GB");
		_tinyMCELanguages.put("en_GB", "en_GB");
		_tinyMCELanguages.put("en_US", "en_GB");
		_tinyMCELanguages.put("es_ES", "es");
		_tinyMCELanguages.put("et_EE", "et");
		_tinyMCELanguages.put("eu_ES", "eu");
		_tinyMCELanguages.put("fa_IR", "fa");
		_tinyMCELanguages.put("fi_FI", "fi");
		_tinyMCELanguages.put("fr_FR", "fr_FR");
		_tinyMCELanguages.put("gl_ES", "gl");
		_tinyMCELanguages.put("hr_HR", "hr");
		_tinyMCELanguages.put("hu_HU", "hu_HU");
		_tinyMCELanguages.put("in_ID", "id");
		_tinyMCELanguages.put("it_IT", "it");
		_tinyMCELanguages.put("iw_IL", "he_IL");
		_tinyMCELanguages.put("ja_JP", "ja");
		_tinyMCELanguages.put("ko_KR", "ko_KR");
		_tinyMCELanguages.put("lt_LT", "lt");
		_tinyMCELanguages.put("nb_NO", "nb_NO");
		_tinyMCELanguages.put("nl_NL", "nl");
		_tinyMCELanguages.put("pl_PL", "pl");
		_tinyMCELanguages.put("pt_BR", "pt_BR");
		_tinyMCELanguages.put("pt_PT", "pt_PT");
		_tinyMCELanguages.put("ro_RO", "ro");
		_tinyMCELanguages.put("ru_RU", "ru");
		_tinyMCELanguages.put("sk_SK", "sk");
		_tinyMCELanguages.put("sl_SI", "sl_SI");
		_tinyMCELanguages.put("sr_RS", "sr");
		_tinyMCELanguages.put("sv_SE", "sv_SE");
		_tinyMCELanguages.put("tr_TR", "tr_TR");
		_tinyMCELanguages.put("uk_UA", "uk");
		_tinyMCELanguages.put("vi_VN", "vi");
		_tinyMCELanguages.put("zh_CN", "zh_CN");
		_tinyMCELanguages.put("zh_TW", "zh_TW");
	}

}