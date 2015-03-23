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

package com.liferay.portlet.editor.config;

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
public class PortletEditorConfigImpl implements PortletEditorConfig {

	public PortletEditorConfigImpl(
		List<PortletEditorConfigContributor> portletEditorConfigContributors,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		_portletEditorConfigContributors = portletEditorConfigContributors;

		_populateConfigJSONObject(
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);
		_populateOptionsJSONObject(
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);
	}

	@Override
	public JSONObject getConfigJSONObject() {
		return _configJSONObject;
	}

	@Override
	public Map<String, Object> getData() {
		Map<String, Object> coverImageCaptionEditorData = new HashMap<>();

		coverImageCaptionEditorData.put("editorConfig", _configJSONObject);
		coverImageCaptionEditorData.put("editorOptions", _optionsJSONObject);

		return coverImageCaptionEditorData;
	}

	@Override
	public JSONObject getOptionsJSONObject() {
		return _optionsJSONObject;
	}

	private JSONObject _populateConfigJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		Iterator<PortletEditorConfigContributor> iterator =
			ListUtil.reverseIterator(_portletEditorConfigContributors);

		while (iterator.hasNext()) {
			PortletEditorConfigContributor portletEditorConfigContributor =
				iterator.next();

			portletEditorConfigContributor.populateConfigJSONObject(
				_configJSONObject, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse);
		}

		return _configJSONObject;
	}

	private JSONObject _populateOptionsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		Iterator<PortletEditorConfigContributor> iterator =
			ListUtil.reverseIterator(_portletEditorConfigContributors);

		while (iterator.hasNext()) {
			PortletEditorConfigContributor portletEditorConfigContributor =
				iterator.next();

			portletEditorConfigContributor.populateOptionsJSONObject(
				_optionsJSONObject, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse);
		}

		return _optionsJSONObject;
	}

	private final JSONObject _configJSONObject =
		JSONFactoryUtil.createJSONObject();
	private final JSONObject _optionsJSONObject =
		JSONFactoryUtil.createJSONObject();
	private final List<PortletEditorConfigContributor>
		_portletEditorConfigContributors;

}