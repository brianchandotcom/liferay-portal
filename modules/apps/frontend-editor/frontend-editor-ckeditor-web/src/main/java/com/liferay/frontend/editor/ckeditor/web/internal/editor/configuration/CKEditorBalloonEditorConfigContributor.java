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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

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

		jsonObject.put(
			"extraAllowedContent", Boolean.TRUE
		);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins = extraPlugins + ",balloontoolbar,floatingspace";
		}
		else {
			extraPlugins = "balloontoolbar,floatingspace";
		}

		jsonObject.put("extraPlugins", extraPlugins);


		JSONArray toolbars = JSONUtil.putAll(
			getToolbarImage(), getToolbarLink(), getToolbarText()
		);

		jsonObject.put("toolbars", toolbars);
	}

	protected JSONObject getToolbarImage() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", "JustifyLeft,JustifyCenter,JustifyRight,Link,Unlink");
		jsonObject.put("priority", Boolean.TRUE);
		jsonObject.put("widgets", "image,image2");

		return jsonObject;
	}

	protected JSONObject getToolbarLink() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", "Link,Unlink");
		jsonObject.put("priority", Boolean.TRUE);
		jsonObject.put("cssSelector", "a");

		return jsonObject;
	}

	protected JSONObject getToolbarText() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", "Bold,Italic,Underline,RemoveFormat,Link,NumberedList,BulletedList,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,Anchor");
		jsonObject.put("cssSelector", "*");

		return jsonObject;
	}
}
