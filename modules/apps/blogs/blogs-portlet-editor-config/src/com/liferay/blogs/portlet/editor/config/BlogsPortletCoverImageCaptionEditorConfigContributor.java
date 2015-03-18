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

package com.liferay.blogs.portlet.editor.config;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.editor.config.PortletEditorConfigContributor;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=33", "javax.portlet.name=161",
		"editor.config.key=coverImageCaptionEditor"
	}
)
public class BlogsPortletCoverImageCaptionEditorConfigContributor
	implements PortletEditorConfigContributor {

	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		jsonObject.put("allowedContent", "a");
		jsonObject.put("disallowedContent", "br");

		JSONObject toolbarJSONObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONArray stylesJSONObject = JSONFactoryUtil.createJSONArray(
				"['a']");

			toolbarJSONObject.put("styles", stylesJSONObject);
		}
		catch (JSONException jsone) {
		}

		jsonObject.put("toolbars", toolbarJSONObject);
	}

	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		jsonObject.put("textMode", Boolean.TRUE);
	}

}