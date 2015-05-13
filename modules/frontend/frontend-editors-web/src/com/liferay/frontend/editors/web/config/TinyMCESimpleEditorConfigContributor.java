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

package com.liferay.frontend.editors.web.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=tinymce_simple"},
	service = EditorConfigContributor.class
)
public class TinyMCESimpleEditorConfigContributor
	extends BaseTinyMCEEditorConfigConfigurator {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			liferayPortletResponse);

		boolean showSource = GetterUtil.getBoolean(
			(String)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:showSource"));

		String plugins = "contextmenu preview print";

		if (showSource) {
			plugins+= " code";
		}

		jsonObject.put("plugins", plugins);

		String rowButtons =
			"bold italic underline | " +
				"alignleft aligncenter alignright alignjustify | ";

		if (showSource) {
			rowButtons += "code ";
		}

		rowButtons += "preview print";

		jsonObject.put("toolbar", rowButtons);
	}

}