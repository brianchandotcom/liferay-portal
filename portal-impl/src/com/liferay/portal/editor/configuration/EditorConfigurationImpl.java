/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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