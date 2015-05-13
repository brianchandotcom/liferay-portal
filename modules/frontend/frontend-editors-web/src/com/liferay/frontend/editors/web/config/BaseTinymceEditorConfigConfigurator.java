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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ambrin Chaudhary
 */
public class BaseTinymceEditorConfigConfigurator extends BaseEditorConfigContributor {

    @Override
    public void populateConfigJSONObject(
            JSONObject jsonObject,
            Map<String, Object> inputEditorTaglibAttributes,
            ThemeDisplay themeDisplay,
            LiferayPortletResponse liferayPortletResponse) {

        jsonObject.put("content_css",
            HtmlUtil.escape(themeDisplay.getPathThemeCss()) + "/aui.css," +
                    HtmlUtil.escape(themeDisplay.getPathThemeCss()) + "/main.css");

        jsonObject.put("convert_urls", Boolean.FALSE);

        StringBundler sb = new StringBundler(4);

        sb.append("a[name|href|target|title|onclick],");
        sb.append("img[class|src|border=0|alt|title|hspace|vspace|width|height|align|");
        sb.append("onmouseover|onmouseout|name|usemap], hr[class|width|size|noshade],");
        sb.append("font[face|size|color|style],span[class|align|style]");

        jsonObject.put("extended_valid_elements", sb.toString());

        jsonObject.put("invalid_elements", "script");

        JSONObject languagesJSONObject = getLanguagesJSONObject();

        String contentsLanguageId =
                (String)inputEditorTaglibAttributes.get(
                        "liferay-ui:input-editor:contentsLanguageId");

        Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

        contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

        String tinyMCELanguage = languagesJSONObject.getString(
                HtmlUtil.escape(contentsLanguageId));

        if (Validator.isNull(tinyMCELanguage)) {
            tinyMCELanguage = languagesJSONObject.getString("en_US");
        }

        jsonObject.put("language", tinyMCELanguage);

        jsonObject.put("menubar", Boolean.FALSE);

        jsonObject.put("mode", "textareas");

        jsonObject.put("relative_urls", Boolean.FALSE);

        jsonObject.put("remove_script_host", Boolean.FALSE);

        String name =
                liferayPortletResponse.getNamespace() +
                        GetterUtil.getString(
                                (String) inputEditorTaglibAttributes.get(
                                        "liferay-ui:input-editor:name"));

        jsonObject.put("selector", "#" + name);

        jsonObject.put("toolbar", "bold italic underline | alignleft aligncenter alignright | preview print");

        jsonObject.put("toolbar_items_size", "small");
    }

    @Override
    public void populateOptionsJSONObject(
        JSONObject jsonObject,
        Map<String, Object> inputEditorTaglibAttributes,
        ThemeDisplay themeDisplay,
        LiferayPortletResponse liferayPortletResponse) {
    }

    protected JSONObject getLanguagesJSONObject() {
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

        StringBundler sb = new StringBundler(9);

        sb.append("{'ar_SA': 'ar', 'bg_BG': 'bg_BG', 'ca_ES': 'ca', 'cs_CZ': 'cs',");
        sb.append("'de_DE': 'de', 'el_GR': 'el', 'en_AU': 'en_GB', 'en_GB': 'en_GB','en_US': 'en_GB',");
        sb.append("'es_ES': 'es', 'et_EE': 'et', 'eu_ES': 'eu', 'fa_IR': 'fa', 'fi_FI': 'fi',");
        sb.append("'fr_FR': 'fr_FR', 'gl_ES': 'gl', 'hr_HR': 'hr', 'hu_HU': 'hu_HU','in_ID': 'id',");
        sb.append("'it_IT': 'it', 'iw_IL': 'he_IL', 'ja_JP': 'ja', 'ko_KR': 'ko_KR', 'lt_LT': 'lt',");
        sb.append("'nb_NO': 'nb_NO', 'nl_NL': 'nl', 'pl_PL': 'pl', 'pt_BR': 'pt_BR', 'pt_PT': 'pt_PT',");
        sb.append("'ro_RO': 'ro', 'ru_RU': 'ru', 'sk_SK': 'sk', 'sl_SI': 'sl_SI', 'sr_RS': 'sr',");
        sb.append("'sv_SE': 'sv_SE', 'tr_TR': 'tr_TR', 'uk_UA': 'uk','vi_VN': 'vi', 'zh_CN': 'zh_CN',");
        sb.append("'zh_TW': 'zh_TW'}");

        try {
            jsonObject = JSONFactoryUtil.createJSONObject(sb.toString());
        } catch (JSONException e) {
        }

        return jsonObject;
    }
}
