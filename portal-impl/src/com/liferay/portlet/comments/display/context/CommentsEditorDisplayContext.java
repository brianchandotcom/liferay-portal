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

package com.liferay.portlet.comments.display.context;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class CommentsEditorDisplayContext {

	public CommentsEditorDisplayContext() {
		_populateDataTextEditor();
	}

	public Map<String, Object> getDataTextEditor() {
		return _dataTextEditor;
	}

	private void _populateDataTextEditor() {
		JSONObject editorConfig = JSONFactoryUtil.createJSONObject();
		editorConfig.put(
			"allowedContent", PropsValues.DISCUSSION_COMMENTS_ALLOWED_CONTENT);
		editorConfig.put("toolbars", JSONFactoryUtil.createJSONObject());

		JSONObject editorOptions = JSONFactoryUtil.createJSONObject();
		editorOptions.put("textMode", Boolean.FALSE);
		editorOptions.put("showSource", Boolean.FALSE);

		_dataTextEditor.put("editorConfig", editorConfig);
		_dataTextEditor.put("editorOptions", editorOptions);
	}

	private final Map<String, Object> _dataTextEditor = new HashMap<>();

}
