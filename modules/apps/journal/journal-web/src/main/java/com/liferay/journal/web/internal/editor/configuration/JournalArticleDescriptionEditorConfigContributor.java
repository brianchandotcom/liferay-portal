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

package com.liferay.journal.web.internal.editor.configuration;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"editor.config.key=descriptionMapAsXMLEditor",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = EditorConfigContributor.class
)
public class JournalArticleDescriptionEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put(
			"allowedContent", "p br strong i ol ul li u link pre em a"
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		return JSONUtil.put("styles", getToolbarsStylesJSONObject(locale));
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		return JSONUtil.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale)
		).put(
			"tabIndex", 1
		);
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.put(getToolbarsStylesSelectionsTextJSONObject(locale));
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll("bold", "italic", "underline", "ol", "ul", "link")
		).put(
			"name", "text"
		).put(
			"test", "AlloyEditor.SelectionTest.text"
		);
	}

}