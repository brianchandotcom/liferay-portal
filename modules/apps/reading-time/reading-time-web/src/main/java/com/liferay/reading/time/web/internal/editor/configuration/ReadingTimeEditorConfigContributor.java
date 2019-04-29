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

package com.liferay.reading.time.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.reading.time.web.internal.constants.ReadingTimePortletKeys;

import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "editor.config.key=reading-time-editor-config-key",
	service = EditorConfigContributor.class
)
public class ReadingTimeEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		PortletURL calculateReadingTimeURL =
			requestBackedPortletURLFactory.createResourceURL(
				ReadingTimePortletKeys.READING_TIME);

		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)calculateReadingTimeURL;

		liferayPortletURL.setResourceID("/reading_time/calculate");

		JSONObject readingTimeJSONObject = JSONUtil.put(
			"url", calculateReadingTimeURL.toString());

		jsonObject.put("readingTime", readingTimeJSONObject);
	}

}