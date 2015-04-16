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

package com.liferay.frontend.alloy.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(property = {"editor.name=alloyeditor"})
public class AlloyEditorConfigContributor implements EditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		String contentsLanguageDir = LanguageUtil.get(
			contentsLocale, "lang.dir");

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));
		jsonObject.put(
			"contentsLanguage", contentsLanguageId.replace("iw_", "he_"));

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		jsonObject.put("language", languageId.replace("iw_", "he_"));

		if (liferayPortletResponse != null) {
			LiferayPortletURL documentSelectorURL =
				liferayPortletResponse.createRenderURL(
					PortletKeys.DOCUMENT_SELECTOR);

			documentSelectorURL.setParameter("mvcPath", "/view.jsp");
			documentSelectorURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

			String name =
				liferayPortletResponse.getNamespace() +
					GetterUtil.getString(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

			documentSelectorURL.setParameter(
				"eventName", name + "selectDocument");
			documentSelectorURL.setParameter(
				"showGroupsSelector", Boolean.TRUE.toString());

			Map<String, String> fileBrowserParamsMap =
				(Map<String, String>)inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:fileBrowserParams");

			if (fileBrowserParamsMap != null) {
				for (Map.Entry<String, String> entry :
						fileBrowserParamsMap.entrySet()) {

					documentSelectorURL.setParameter(
						entry.getKey(), entry.getValue());
				}
			}

			try {
				documentSelectorURL.setWindowState(LiferayWindowState.POP_UP);
			}
			catch (WindowStateException wse) {
			}

			jsonObject.put(
				"filebrowserBrowseUrl", documentSelectorURL.toString());
			jsonObject.put(
				"filebrowserFlashBrowseUrl",
				documentSelectorURL.toString() + "&Type=flash");
			jsonObject.put(
				"filebrowserImageBrowseLinkUrl",
				documentSelectorURL.toString() + "&Type=image");
			jsonObject.put(
				"filebrowserImageBrowseUrl",
				documentSelectorURL.toString() + "&Type=image");

			jsonObject.put("srcNode", name);
		}

		jsonObject.put(
			"removePlugins", "toolbar,elementspath,resize,liststyle,link");

		JSONObject toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONArray toolbarAddJSONArray = JSONFactoryUtil.createJSONArray(
				"['imageselector']");

			JSONArray toolbarImageJSONArray = JSONFactoryUtil.createJSONArray(
				"['left', 'right']");

			JSONArray toolbarStylesJSONArray = JSONFactoryUtil.createJSONArray(
				"['strong', 'em', 'u', 'h1', 'h2', 'a', 'twitter']");

			toolbarsJSONObject.put("add", toolbarAddJSONArray);
			toolbarsJSONObject.put("image", toolbarImageJSONArray);
			toolbarsJSONObject.put("styles", toolbarStylesJSONArray);
		}
		catch (JSONException jsone) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to create a JSON array from string");
			}
		}

		jsonObject.put("toolbarsJSONObject", toolbarsJSONObject);
	}

	@Override
	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AlloyEditorConfigContributor.class);

}