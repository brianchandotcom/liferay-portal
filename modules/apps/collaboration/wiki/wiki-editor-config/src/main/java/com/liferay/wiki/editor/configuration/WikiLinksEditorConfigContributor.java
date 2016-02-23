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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
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

						String buttonsString = StringUtil.replace(
							buttonsJSONArray.toString(),
							new String[] {"\"link\"", "\"linkEdit\""},
							new String[] {
								getWikiLinkConfig("link"),
								getWikiLinkConfig("linkEdit")
							});

						selectionJSONObject.put(
							"buttons", toJSONArray(buttonsString));
					}
				}
			}
		}
	}

	protected String getWikiLinkConfig(String buttonName) {
		return "{name: \"" + buttonName + "\", cfg: {appendProtocol: false}}";
	}

}