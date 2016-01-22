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

package com.liferay.journal.editor.configuration;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Antonio Pol
 */
@Component(
	property = {
		"editor.name=alloyeditor",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = EditorConfigContributor.class
)
public class JournalMediaConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		if (!XugglerUtil.isEnabled()) {
			return;
		}

		JSONObject toolbars = jsonObject.getJSONObject("toolbars");

		if (Validator.isNull(toolbars)) {
			toolbars = JSONFactoryUtil.createJSONObject();
		}

		JSONObject toolbarAdd = toolbars.getJSONObject("add");

		if (Validator.isNull(toolbarAdd)) {
			toolbarAdd = JSONFactoryUtil.createJSONObject();
		}

		JSONArray buttons = toolbarAdd.getJSONArray("buttons");

		if (Validator.isNull(buttons)) {
			buttons = JSONFactoryUtil.createJSONArray();
		}

		buttons.put("video");
		buttons.put("audio");

		toolbarAdd.put("add", buttons);

		toolbars.put("add", toolbarAdd);

		jsonObject.put("toolbars", toolbars);
	}

}