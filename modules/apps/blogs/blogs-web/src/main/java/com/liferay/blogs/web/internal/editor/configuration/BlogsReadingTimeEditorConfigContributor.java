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

package com.liferay.blogs.web.internal.editor.configuration;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.web.internal.configuration.BlogsPortletInstanceConfiguration;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"editor.config.key=contentEditor", "editor.name=alloyeditor",
		"editor.name=ckeditor", "javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"service.ranking:Integer=101"
	},
	service = EditorConfigContributor.class
)
public class BlogsReadingTimeEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			BlogsPortletInstanceConfiguration
				blogsPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						BlogsPortletInstanceConfiguration.class);

			if (!blogsPortletInstanceConfiguration.enableReadingTime()) {
				return;
			}
		}
		catch (ConfigurationException ce) {
			_log.error(
				"Unable to get blogs portlet instance configuration", ce);
		}

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins = extraPlugins + ",readingtime";
		}
		else {
			extraPlugins = "readingtime";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		_readingTimeConfigContributor.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		JSONObject readingTimeJSONObject = jsonObject.getJSONObject(
			"readingTime");

		if (readingTimeJSONObject != null) {
			String namespace = GetterUtil.getString(
				inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:namespace"));

			readingTimeJSONObject.put("elementId", namespace + "readingTime");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsReadingTimeEditorConfigContributor.class);

	@Reference(target = "(editor.config.key=reading-time-editor-config-key)")
	private EditorConfigContributor _readingTimeConfigContributor;

}