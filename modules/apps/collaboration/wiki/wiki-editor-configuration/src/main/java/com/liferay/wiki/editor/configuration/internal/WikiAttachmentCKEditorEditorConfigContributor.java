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
		"editor.name=ckeditor", "editor.name=ckeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentCKEditorEditorConfigContributor
	extends BaseWikiAttachmentEditorConfigContributor {

	@Override
	protected void removeImageButton(JSONObject jsonObject) {
		Iterator<String> jsonObjectKeysIterator = jsonObject.keys();

		while (jsonObjectKeysIterator.hasNext()) {
			String key = jsonObjectKeysIterator.next();

			if (key.startsWith("toolbar_")) {
				JSONArray toolbarJSONArray = JSONFactoryUtil.createJSONArray();

				JSONArray jsonArray = jsonObject.getJSONArray(key);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONArray itemJSONArray = jsonArray.getJSONArray(i);

					if (itemJSONArray != null) {
						if ((itemJSONArray.length() == 1) &&
							itemJSONArray.get(0).equals("ImageSelector")) {

							continue;
						}

						JSONArray toolbarItemJSONArray =
							JSONFactoryUtil.createJSONArray();

						for (int j = 0; j < itemJSONArray.length(); j++) {
							Object item = itemJSONArray.get(j);

							if (!item.equals("ImageSelector")) {
								toolbarItemJSONArray.put(item);
							}
						}

						toolbarJSONArray.put(toolbarItemJSONArray);
					}
					else {
						toolbarJSONArray.put(jsonArray.get(i));
					}
				}

				jsonObject.put(key, toolbarJSONArray);
			}
		}
	}

}