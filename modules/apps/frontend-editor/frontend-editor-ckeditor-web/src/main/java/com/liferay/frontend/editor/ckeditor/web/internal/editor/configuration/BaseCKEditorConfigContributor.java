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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Ambrín Chaudhary
 */
public class BaseCKEditorConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put("allowedContent", Boolean.TRUE);

		StringBundler sb = new StringBundler(5);

		sb.append("cke_editable html-editor");

		ColorScheme colorScheme = themeDisplay.getColorScheme();

		if (Validator.isNotNull(colorScheme.getCssClass())) {
			sb.append(StringPool.SPACE);
			sb.append(HtmlUtil.escape(colorScheme.getCssClass()));
		}

		String cssClasses = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":cssClasses"));

		if (Validator.isNotNull(cssClasses)) {
			sb.append(StringPool.SPACE);
			sb.append(HtmlUtil.escape(cssClasses));
		}

		jsonObject.put(
			"bodyClass", sb.toString()
		).put(
			"contentsCss",
			JSONUtil.putAll(
				HtmlUtil.escape(
					PortalUtil.getStaticResourceURL(
						themeDisplay.getRequest(),
						themeDisplay.getPathThemeCss() + "/clay.css")),
				HtmlUtil.escape(
					PortalUtil.getStaticResourceURL(
						themeDisplay.getRequest(),
						themeDisplay.getPathThemeCss() + "/main.css")),
				HtmlUtil.escape(
					PortalUtil.getStaticResourceURL(
						themeDisplay.getRequest(),
						PortalUtil.getPathContext() +
							"/o/frontend-editor-ckeditor-web/ckeditor/skins" +
								"/moono-lexicon/editor.css")))
		).put(
			"contentsLangDirection",
			HtmlUtil.escapeJS(
				getContentsLanguageDir(inputEditorTaglibAttributes))
		);

		String contentsLanguageId = getContentsLanguageId(
			inputEditorTaglibAttributes);

		contentsLanguageId = StringUtil.replace(contentsLanguageId, "iw", "he");
		contentsLanguageId = StringUtil.replace(contentsLanguageId, '_', '-');

		jsonObject.put(
			"contentsLanguage", contentsLanguageId
		).put(
			"height", 265
		);

		String languageId = getLanguageId(themeDisplay);

		languageId = StringUtil.replace(languageId, "iw", "he");
		languageId = StringUtil.replace(languageId, '_', '-');

		jsonObject.put("language", languageId);

		boolean resizable = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":resizable"));

		if (resizable) {
			jsonObject.put("resize_dir", "vertical");
		}

		jsonObject.put("resize_enabled", resizable);
	}

	protected boolean isShowSource(
		Map<String, Object> inputEditorTaglibAttributes) {

		return GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":showSource"),
			true);
	}

}