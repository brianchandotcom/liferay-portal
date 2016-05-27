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

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"editor.name=alloyeditor", "editor.name=alloyeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentAlloyEditorEditorConfigContributor
	extends BaseWikiAttachmentEditorConfigContributor {

	@Override
	protected void removeImageButton(JSONObject jsonObject) {
		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject == null) {
			return;
		}

		JSONObject addJSONObject = toolbarsJSONObject.getJSONObject("add");

		if (addJSONObject == null) {
			return;
		}

		JSONArray buttonsJSONArray = addJSONObject.getJSONArray("buttons");

		if (buttonsJSONArray == null) {
			return;
		}

		JSONArray newButtonsJSONArray = JSONFactoryUtil.createJSONArray();

		Iterator iterator = buttonsJSONArray.iterator();

		while (iterator.hasNext()) {
			Object buttonObject = iterator.next();

			if (buttonObject instanceof String) {
				String buttonString = (String)buttonObject;

				if (!buttonString.equals("image")) {
					newButtonsJSONArray.put(buttonString);
				}
			}
			else {
				newButtonsJSONArray.put(buttonObject);
			}
		}

		addJSONObject.put("buttons", newButtonsJSONArray);
	}

}