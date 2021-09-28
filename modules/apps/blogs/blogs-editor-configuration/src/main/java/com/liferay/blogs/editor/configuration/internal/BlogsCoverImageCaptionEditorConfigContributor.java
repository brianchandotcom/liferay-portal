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

package com.liferay.blogs.editor.configuration.internal;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=coverImageCaptionEditor",
		"editor.name=ballooneditor",
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN
	},
	service = EditorConfigContributor.class
)
public class BlogsCoverImageCaptionEditorConfigContributor
	extends BaseEditorConfigContributor {

	public static final String DEFAULT_REMOVE_PLUGINS =
		"magicline,stylescombo,videoembed,video,image,contextmenu," +
			"tabletools,liststyle,insertbutton";

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put(
			"allowedContent", "a[*](*)"
		).put(
			"disallowedContent", "br"
		);

		String removePlugins = jsonObject.getString("removePlugins");

		if (Validator.isNotNull(removePlugins)) {
			removePlugins = removePlugins + "," + DEFAULT_REMOVE_PLUGINS;
		}
		else {
			removePlugins = DEFAULT_REMOVE_PLUGINS;
		}

		jsonObject.put(
			"removePlugins", removePlugins
		).put(
			"toolbarImage", ""
		).put(
			"toolbarTable", ""
		).put(
			"toolbarText", "TextLink"
		).put(
			"toolbarVideo", ""
		);
	}

}