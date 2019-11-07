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

import com.liferay.frontend.editor.tinymce.web.internal.constants.TinyMCEEditorConstants;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Ambrín Chaudhary
 */
public abstract class BaseTinyMCEEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		StringBundler sb = new StringBundler(3);

		sb.append(
			HtmlUtil.escape(
				PortalUtil.getStaticResourceURL(
					themeDisplay.getRequest(),
					themeDisplay.getPathThemeCss() + "/clay.css")));
		sb.append(StringPool.COMMA);
		sb.append(
			HtmlUtil.escape(
				PortalUtil.getStaticResourceURL(
					themeDisplay.getRequest(),
					themeDisplay.getPathThemeCss() + "/main.css")));

		jsonObject.put(
			"content_css", sb.toString()
		).put(
			"convert_urls", Boolean.FALSE
		).put(
			"extended_valid_elements", _EXTENDED_VALID_ELEMENTS
		);

		ItemSelector itemSelector = getItemSelector();

		String filebrowserImageBrowseUrl = jsonObject.getString(
			"filebrowserImageBrowseUrl");

		String itemSelectedEventName = itemSelector.getItemSelectedEventName(
			filebrowserImageBrowseUrl);

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(filebrowserImageBrowseUrl);

		ItemSelectorCriterion itemSelectorCriterion =
			new ImageItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		itemSelectorCriteria.add(itemSelectorCriterion);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		jsonObject.put(
			"filebrowserImageBrowseUrl", itemSelectorURL.toString()
		).put(
			"invalid_elements", "script"
		);

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":contentsLanguageId");

		jsonObject.put(
			"language", getTinyMCELanguage(contentsLanguageId)
		).put(
			"menubar", Boolean.FALSE
		).put(
			"mode", "textareas"
		).put(
			"relative_urls", Boolean.FALSE
		).put(
			"remove_script_host", Boolean.FALSE
		);

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":namespace"));

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

		jsonObject.put(
			"selector", "#" + namespace + name
		).put(
			"toolbar",
			"bold italic underline | alignleft aligncenter alignright | " +
				"preview print"
		).put(
			"toolbar_items_size", "small"
		);
	}

	protected abstract ItemSelector getItemSelector();

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
				TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":showSource"));
	}

	private static final String _EXTENDED_VALID_ELEMENTS = StringBundler.concat(
		"a[name|href|target|title|onclick],img[class|src|border=0",
		"|alt|title|hspace|vspace|width|height|align|onmouseover",
		"|onmouseout|name|usemap],hr[class|width|size|noshade],",
		"font[face|size|color|style],span[class|align|style]");

	private static final Map<String, String> _tinyMCELanguages =
		HashMapBuilder.<String, String>put(
			"ar_SA", "ar"
		).put(
			"bg_BG", "bg_BG"
		).put(
			"ca_ES", "ca"
		).put(
			"cs_CZ", "cs"
		).put(
			"de_DE", "de"
		).put(
			"el_GR", "el"
		).put(
			"en_AU", "en_GB"
		).put(
			"en_GB", "en_GB"
		).put(
			"en_US", "en_GB"
		).put(
			"es_ES", "es"
		).put(
			"et_EE", "et"
		).put(
			"eu_ES", "eu"
		).put(
			"fa_IR", "fa"
		).put(
			"fi_FI", "fi"
		).put(
			"fr_FR", "fr_FR"
		).put(
			"gl_ES", "gl"
		).put(
			"hr_HR", "hr"
		).put(
			"hu_HU", "hu_HU"
		).put(
			"in_ID", "id"
		).put(
			"it_IT", "it"
		).put(
			"iw_IL", "he_IL"
		).put(
			"ja_JP", "ja"
		).put(
			"ko_KR", "ko_KR"
		).put(
			"lt_LT", "lt"
		).put(
			"nb_NO", "nb_NO"
		).put(
			"nl_NL", "nl"
		).put(
			"pl_PL", "pl"
		).put(
			"pt_BR", "pt_BR"
		).put(
			"pt_PT", "pt_PT"
		).put(
			"ro_RO", "ro"
		).put(
			"ru_RU", "ru"
		).put(
			"sk_SK", "sk"
		).put(
			"sl_SI", "sl_SI"
		).put(
			"sr_RS", "sr"
		).put(
			"sv_SE", "sv_SE"
		).put(
			"tr_TR", "tr_TR"
		).put(
			"uk_UA", "uk"
		).put(
			"vi_VN", "vi"
		).put(
			"zh_CN", "zh_CN"
		).put(
			"zh_TW", "zh_TW"
		).build();

}