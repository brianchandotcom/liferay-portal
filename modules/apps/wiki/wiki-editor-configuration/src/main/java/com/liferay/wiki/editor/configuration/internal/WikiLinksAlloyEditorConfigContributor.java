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

package com.liferay.wiki.editor.configuration.internal;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(
	property = {
		"editor.config.key=contentEditor", "editor.name=alloyeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY
	},
	service = EditorConfigContributor.class
)
public class WikiLinksAlloyEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject == null) {
			return;
		}

		JSONObject stylesToolbarJSONObject = toolbarsJSONObject.getJSONObject(
			"styles");

		if (stylesToolbarJSONObject == null) {
			return;
		}

		JSONArray selectionsJSONArray = stylesToolbarJSONObject.getJSONArray(
			"selections");

		if (selectionsJSONArray == null) {
			return;
		}

		for (int i = 0; i < selectionsJSONArray.length(); i++) {
			JSONObject selectionJSONObject = selectionsJSONArray.getJSONObject(
				i);

			JSONArray buttonsJSONArray = selectionJSONObject.getJSONArray(
				"buttons");

			selectionJSONObject.put(
				"buttons", updateButtonsJSONArray(buttonsJSONArray));
		}
	}

	protected JSONObject getWikiLinkButtonJSONObject(String buttonName) {
		JSONObject cfgJSONObject = JSONUtil.put("appendProtocol", false);

		return JSONUtil.put(
			"cfg", cfgJSONObject
		).put(
			"name", buttonName
		);
	}

	protected JSONArray updateButtonsJSONArray(JSONArray oldButtonsJSONArray) {
		JSONArray newButtonsJSONArray = JSONFactoryUtil.createJSONArray();

		for (int j = 0; j < oldButtonsJSONArray.length(); j++) {
			JSONObject buttonJSONObject = oldButtonsJSONArray.getJSONObject(j);

			if (buttonJSONObject == null) {
				String buttonName = oldButtonsJSONArray.getString(j);

				if (buttonName.equals("link") ||
					buttonName.equals("linkEdit")) {

					buttonJSONObject = getWikiLinkButtonJSONObject(buttonName);

					newButtonsJSONArray.put(buttonJSONObject);
				}
				else {
					newButtonsJSONArray.put(buttonName);
				}
			}
			else {
				String buttonName = buttonJSONObject.getString("name");

				if (buttonName.equals("link") ||
					buttonName.equals("linkEdit")) {

					JSONObject cfgJSONObject = buttonJSONObject.getJSONObject(
						"cfg");

					if (cfgJSONObject == null) {
						cfgJSONObject = JSONFactoryUtil.createJSONObject();

						buttonJSONObject.put("cfg", cfgJSONObject);
					}

					cfgJSONObject.put("appendProtocol", false);
				}

				newButtonsJSONArray.put(buttonJSONObject);
			}
		}

		return newButtonsJSONArray;
	}

}