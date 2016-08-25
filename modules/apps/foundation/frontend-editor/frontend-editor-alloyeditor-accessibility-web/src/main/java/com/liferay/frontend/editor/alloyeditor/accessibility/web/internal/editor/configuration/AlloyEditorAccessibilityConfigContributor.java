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

package com.liferay.frontend.editor.alloyeditor.accessibility.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Antonio Pol
 */
@Component(
	property = {"editor.name=alloyeditor", "service.ranking:Integer=1000"},
	service = EditorConfigContributor.class
)
public class AlloyEditorAccessibilityConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject == null) {
			toolbarsJSONObject = JSONFactoryUtil.createJSONObject();
		}

		JSONObject stylesJSONObject = toolbarsJSONObject.getJSONObject(
			"styles");

		if (stylesJSONObject == null) {
			stylesJSONObject = JSONFactoryUtil.createJSONObject();
		}

		JSONArray selectionsJSONArray = stylesJSONObject.getJSONArray(
			"selections");

		for (int i = 0; i < selectionsJSONArray.length(); i++) {
			JSONObject selection = selectionsJSONArray.getJSONObject(i);

			if (Objects.equals(selection.get("name"), "image")) {
				JSONArray buttons = selection.getJSONArray("buttons");

				buttons.put("imageAlt");
			}
		}

		stylesJSONObject.put("selections", selectionsJSONArray);
		toolbarsJSONObject.put("styles", stylesJSONObject);
		jsonObject.put("toolbars", toolbarsJSONObject);
	}

}