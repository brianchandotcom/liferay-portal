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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigurationImpl implements EditorConfiguration {

	public EditorConfigurationImpl(
		JSONObject configJSONObject, EditorOptions editorOptions) {

		_configJSONObject = configJSONObject;
		_editorOptions = editorOptions;
	}

	@Override
	public JSONObject getConfigJSONObject() {
		return _configJSONObject;
	}

	@Override
	public Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"editorConfig", _configJSONObject
		).put(
			"editorOptions", _editorOptions
		).build();
	}

	@Override
	public EditorOptions getEditorOptions() {
		return _editorOptions;
	}

	private final JSONObject _configJSONObject;
	private final EditorOptions _editorOptions;

}