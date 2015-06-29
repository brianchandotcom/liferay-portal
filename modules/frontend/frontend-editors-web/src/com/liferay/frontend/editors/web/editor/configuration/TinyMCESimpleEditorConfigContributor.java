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

package com.liferay.frontend.editors.web.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLBuilder;

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
		ThemeDisplay themeDisplay, PortletURLBuilder portletURLBuilder) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			portletURLBuilder);

		String plugins = "contextmenu preview print";

		if (isShowSource(inputEditorTaglibAttributes)) {
			plugins+= " code";
		}

		jsonObject.put("plugins", plugins);

		String toolbar =
			"bold italic underline | alignleft aligncenter alignright " +
				"alignjustify | ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			toolbar += "code ";
		}

		toolbar += "preview print";

		jsonObject.put("toolbar", toolbar);
	}

}