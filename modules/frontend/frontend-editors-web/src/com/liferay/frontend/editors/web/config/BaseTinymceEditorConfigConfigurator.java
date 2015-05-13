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

package com.liferay.frontend.editors.web.config;

import com.liferay.portal.kernel.editor.config.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ambrin Chaudhary
 */
public class BaseTinymceEditorConfigConfigurator
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		String pathThemeCss = HtmlUtil.escape(themeDisplay.getPathThemeCss());

		jsonObject.put(
			"content_css",
			pathThemeCss + "/aui.css," + pathThemeCss + "/main.css");

		jsonObject.put("convert_urls", Boolean.FALSE);

		StringBundler sb = new StringBundler(5);

		sb.append("a[name|href|target|title|onclick],");
		sb.append("img[class|src|border=0|alt|title|hspace|vspace|width|");
		sb.append("height|align|onmouseover|onmouseout|name|usemap],");
		sb.append("hr[class|width|size|noshade],font[face|size|color|style],");
		sb.append("span[class|align|style]");

		jsonObject.put("extended_valid_elements", sb.toString());

		jsonObject.put("invalid_elements", "script");

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		jsonObject.put("language", getTinymceLanguage(contentsLanguageId));
		jsonObject.put("menubar", Boolean.FALSE);
		jsonObject.put("mode", "textareas");
		jsonObject.put("relative_urls", Boolean.FALSE);
		jsonObject.put("remove_script_host", Boolean.FALSE);

		String name =
			liferayPortletResponse.getNamespace() +
				GetterUtil.getString(
					(String)inputEditorTaglibAttributes.get(
						"liferay-ui:input-editor:name"));

		jsonObject.put("selector", "#" + name);
		jsonObject.put(
			"toolbar",
			"bold italic underline | alignleft aligncenter alignright | " +
				"preview print");
		jsonObject.put("toolbar_items_size", "small");
	}

	@Override
	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {
	}

	protected String getTinymceLanguage(String contentsLanguageId) {
		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		String tinymceLanguage = _tinymceLanguages.get(contentsLanguageId);

		if (Validator.isNull(tinymceLanguage)) {
			tinymceLanguage = _tinymceLanguages.get("en_US");
		}

		return tinymceLanguage;
	}

	private static final Map<String, String> _tinymceLanguages =
		new HashMap<>();

	static {
		_tinymceLanguages.put("ar_SA", "ar");
		_tinymceLanguages.put("bg_BG", "bg_BG");
		_tinymceLanguages.put("ca_ES", "ca");
		_tinymceLanguages.put("cs_CZ", "cs");
		_tinymceLanguages.put("de_DE", "de");
		_tinymceLanguages.put("el_GR", "el");
		_tinymceLanguages.put("en_AU", "en_GB");
		_tinymceLanguages.put("en_GB", "en_GB");
		_tinymceLanguages.put("en_US", "en_GB");
		_tinymceLanguages.put("es_ES", "es");
		_tinymceLanguages.put("et_EE", "et");
		_tinymceLanguages.put("eu_ES", "eu");
		_tinymceLanguages.put("fa_IR", "fa");
		_tinymceLanguages.put("fi_FI", "fi");
		_tinymceLanguages.put("fr_FR", "fr_FR");
		_tinymceLanguages.put("gl_ES", "gl");
		_tinymceLanguages.put("hr_HR", "hr");
		_tinymceLanguages.put("hu_HU", "hu_HU");
		_tinymceLanguages.put("in_ID", "id");
		_tinymceLanguages.put("it_IT", "it");
		_tinymceLanguages.put("iw_IL", "he_IL");
		_tinymceLanguages.put("ja_JP", "ja");
		_tinymceLanguages.put("ko_KR", "ko_KR");
		_tinymceLanguages.put("lt_LT", "lt");
		_tinymceLanguages.put("nb_NO", "nb_NO");
		_tinymceLanguages.put("nl_NL", "nl");
		_tinymceLanguages.put("pl_PL", "pl");
		_tinymceLanguages.put("pt_BR", "pt_BR");
		_tinymceLanguages.put("pt_PT", "pt_PT");
		_tinymceLanguages.put("ro_RO", "ro");
		_tinymceLanguages.put("ru_RU", "ru");
		_tinymceLanguages.put("sk_SK", "sk");
		_tinymceLanguages.put("sl_SI", "sl_SI");
		_tinymceLanguages.put("sr_RS", "sr");
		_tinymceLanguages.put("sv_SE", "sv_SE");
		_tinymceLanguages.put("tr_TR", "tr_TR");
		_tinymceLanguages.put("uk_UA", "uk");
		_tinymceLanguages.put("vi_VN", "vi");
		_tinymceLanguages.put("zh_CN", "zh_CN");
		_tinymceLanguages.put("zh_TW", "zh_TW");
	}

}