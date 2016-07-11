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

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Map;

/**
 * @author Ambrin Chaudhary
 */
public class BaseCKEditorConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put("allowedContent", Boolean.TRUE);

		String cssClasses = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:cssClasses"));

		jsonObject.put(
			"bodyClass", "html-editor " + HtmlUtil.escape(cssClasses));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String cssPath = HtmlUtil.escape(themeDisplay.getPathThemeCss());

		jsonArray.put(cssPath + "/aui.css");
		jsonArray.put(cssPath + "/main.css");

		jsonObject.put("contentsCss", jsonArray);

		String contentsLanguageDir = getContentsLanguageDir(
			inputEditorTaglibAttributes);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));

		String contentsLanguageId = getContentsLanguageId(
			inputEditorTaglibAttributes);

		contentsLanguageId = contentsLanguageId.replace("iw", "he");
		contentsLanguageId = contentsLanguageId.replace("_", "-");

		jsonObject.put("contentsLanguage", contentsLanguageId);

		jsonObject.put("height", 265);

		String languageId = getLanguageId(themeDisplay);

		languageId = languageId.replace("iw", "he");
		languageId = languageId.replace("_", "-");

		jsonObject.put("language", languageId);

		boolean resizable = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:resizable"));

		if (resizable) {
			jsonObject.put("resize_dir", "vertical");
		}

		jsonObject.put("resize_enabled", resizable);
	}

	protected boolean isShowSource(
		Map<String, Object> inputEditorTaglibAttributes) {

		return GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:showSource"));
	}

}