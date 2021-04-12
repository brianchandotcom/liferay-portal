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

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ballooneditor",
	service = EditorConfigContributor.class
)
public class CKEditorBalloonEditorConfigContributor
	extends BaseCKEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put("extraAllowedContent", Boolean.TRUE);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins = extraPlugins + ",balloontoolbar,floatingspace";
		}
		else {
			extraPlugins = "balloontoolbar,floatingspace";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		JSONArray toolbarsJSONArray = JSONUtil.putAll(
			getToolbarImageJSONObject(), getToolbarLinkJSONObject(),
			getToolbarTextJSONObject());

		jsonObject.put("toolbars", toolbarsJSONArray);
	}

	protected JSONObject getToolbarImageJSONObject() {
		return JSONUtil.put(
			"buttons", "JustifyLeft,JustifyCenter,JustifyRight,Link,Unlink"
		).put(
			"priority", Boolean.TRUE
		).put(
			"widgets", "image,image2"
		);
	}

	protected JSONObject getToolbarLinkJSONObject() {
		return JSONUtil.put(
			"buttons", "Link,Unlink"
		).put(
			"cssSelector", "a"
		).put(
			"priority", Boolean.TRUE
		);
	}

	protected JSONObject getToolbarTextJSONObject() {
		return JSONUtil.put(
			"buttons",
			"Bold,Italic,Underline,BulletedList,NumberedList,Link," +
				"JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock," +
				"RemoveFormat"
		).put(
			"cssSelector", "*"
		);
	}

}