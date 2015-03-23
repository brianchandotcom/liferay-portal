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

package com.liferay.comments.portlet.editor.conf;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.editor.config.PortletEditorConfigContributor;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(property = {"editor.impl=alloyeditor"})
public class AlloyEditorConfigContributor
	implements PortletEditorConfigContributor {

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

		LiferayPortletURL documentSelectorURL =
			liferayPortletResponse.createRenderURL(
				PortletKeys.DOCUMENT_SELECTOR);

		documentSelectorURL.setParameter("mvcPath", "/view.jsp");
		documentSelectorURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		String name =
			liferayPortletResponse.getNamespace() +
				GetterUtil.getString((String)inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:name"));

		documentSelectorURL.setParameter("eventName", name + "selectDocument");
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

		jsonObject.put("filebrowserBrowseUrl", documentSelectorURL.toString());
		jsonObject.put(
			"filebrowserFlashBrowseUrl",
			documentSelectorURL.toString() + "&Type=flash");
		jsonObject.put(
			"filebrowserImageBrowseLinkUrl",
			documentSelectorURL.toString() + "&Type=image");
		jsonObject.put(
			"filebrowserImageBrowseUrl",
			documentSelectorURL.toString() + "&Type=image");

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		jsonObject.put("language", languageId.replace("iw_", "he_"));
		jsonObject.put("srcNode", "#" + name);
	}

	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {
	}

}