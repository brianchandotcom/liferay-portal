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

package com.liferay.portal.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfig;
import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigImpl implements EditorConfig {

	public EditorConfigImpl(
		List<EditorConfigContributor> editorConfigContributors,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		_editorConfigContributors = editorConfigContributors;

		_populateConfigJSONObject(
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);
		_populateOptionsJSONObject(
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);
	}

	@Override
	public Map<String, Object> getData() {
		Map<String, Object> coverImageCaptionEditorData = new HashMap<>();

		coverImageCaptionEditorData.put("editorConfig", _configJSONObject);
		coverImageCaptionEditorData.put("editorOptions", _optionsJSONObject);

		return coverImageCaptionEditorData;
	}

	private JSONObject _populateConfigJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		Iterator<EditorConfigContributor> iterator = ListUtil.reverseIterator(
			_editorConfigContributors);

		while (iterator.hasNext()) {
			EditorConfigContributor editorConfigContributor = iterator.next();

			editorConfigContributor.populateConfigJSONObject(
				_configJSONObject, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse);
		}

		return _configJSONObject;
	}

	private JSONObject _populateOptionsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		Iterator<EditorConfigContributor> iterator = ListUtil.reverseIterator(
			_editorConfigContributors);

		while (iterator.hasNext()) {
			EditorConfigContributor editorConfigContributor = iterator.next();

			editorConfigContributor.populateOptionsJSONObject(
				_optionsJSONObject, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse);
		}

		return _optionsJSONObject;
	}

	private final JSONObject _configJSONObject =
		JSONFactoryUtil.createJSONObject();
	private final List<EditorConfigContributor> _editorConfigContributors;
	private final JSONObject _optionsJSONObject =
		JSONFactoryUtil.createJSONObject();

}