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

package com.liferay.wiki.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
		"editor.name=alloyeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY
	},
	service = EditorConfigContributor.class
)
public class WikiLinksEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject != null) {
			JSONObject stylesToolbarJSONObject =
				toolbarsJSONObject.getJSONObject("styles");

			if (stylesToolbarJSONObject != null) {
				JSONArray selectionsJSONArray =
					stylesToolbarJSONObject.getJSONArray("selections");

				if (selectionsJSONArray != null) {
					for (int i = 0; i < selectionsJSONArray.length(); i++) {
						JSONObject selectionJSONObject =
							selectionsJSONArray.getJSONObject(i);

						JSONArray buttonsJSONArray =
							selectionJSONObject.getJSONArray("buttons");

						selectionJSONObject.put(
							"buttons",
							updateAppendProtocolConfiguration(
								buttonsJSONArray));
					}
				}
			}
		}
	}

	protected JSONObject getWikiLinkButtonJSONObject(String buttonName) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("name", buttonName);

		JSONObject cfgJSONObject = JSONFactoryUtil.createJSONObject();

		cfgJSONObject.put("appendProtocol", false);

		jsonObject.put("cfg", cfgJSONObject);

		return jsonObject;
	}

	protected JSONArray updateAppendProtocolConfiguration(
		JSONArray buttonsJSONArray) {

		JSONArray newButtonsJSONArray = JSONFactoryUtil.createJSONArray();

		for (int j = 0; j < buttonsJSONArray.length(); j++) {
			JSONObject buttonJSONObject = buttonsJSONArray.getJSONObject(j);

			if (buttonJSONObject == null) {
				String buttonName = buttonsJSONArray.getString(j);

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

					JSONObject config = buttonJSONObject.getJSONObject("cfg");

					if (config == null) {
						config = JSONFactoryUtil.createJSONObject();

						buttonJSONObject.put("cfg", config);
					}

					config.put("appendProtocol", false);
				}

				newButtonsJSONArray.put(buttonJSONObject);
			}
		}

		return newButtonsJSONArray;
	}

}