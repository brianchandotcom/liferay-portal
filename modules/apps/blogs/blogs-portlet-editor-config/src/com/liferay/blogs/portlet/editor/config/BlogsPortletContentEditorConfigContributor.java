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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.editor.config.PortletEditorConfigContributor;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=33", "javax.portlet.name=161",
		"editor.config.key=contentEditor"
	}
)
public class BlogsPortletContentEditorConfigContributor
	implements PortletEditorConfigContributor {

	public JSONObject getConfigJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		JSONObject editorConfigJSONObject = JSONFactoryUtil.createJSONObject();

		return editorConfigJSONObject;
	}

	public JSONObject getOptionsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		JSONObject editorOptionsJSONObject = JSONFactoryUtil.createJSONObject();

		PortletURL uploadEditorImageURL =
			liferayPortletResponse.createActionURL();

		uploadEditorImageURL.setParameter(
			"struts_action", "/blogs/upload_editor_image");

		editorOptionsJSONObject.put("uploadURL", uploadEditorImageURL);

		return editorOptionsJSONObject;
	}

}