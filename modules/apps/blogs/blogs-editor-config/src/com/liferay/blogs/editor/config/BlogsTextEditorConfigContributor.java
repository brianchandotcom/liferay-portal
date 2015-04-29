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

package com.liferay.blogs.editor.config;

import com.liferay.portal.kernel.editor.config.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=descriptionEditor",
		"editor.config.key=subtitleEditor", "editor.config.key=titleEditor",
		"javax.portlet.name=33", "javax.portlet.name=161"
	},
	service = EditorConfigContributor.class
)
public class BlogsTextEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		jsonObject.put("allowedContent", "p");
		jsonObject.put("disallowedContent", "br");
		jsonObject.put("extraPlugins", "placeholder,selectionregion,uicore");
		jsonObject.put("toolbars", JSONFactoryUtil.createJSONObject());
	}

	@Override
	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		jsonObject.put("textMode", Boolean.TRUE);
	}

}