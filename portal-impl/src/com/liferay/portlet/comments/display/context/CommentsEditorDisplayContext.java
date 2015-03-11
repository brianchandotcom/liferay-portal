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
		_populateEditorData();
	}

	public Map<String, Object> getEditorData() {
		return _editorData;
	}

	private void _populateEditorData() {
		JSONObject editorConfigJSONObject = JSONFactoryUtil.createJSONObject();

		editorConfigJSONObject.put(
			"allowedContent", PropsValues.DISCUSSION_COMMENTS_ALLOWED_CONTENT);
		editorConfigJSONObject.put(
			"toolbars", JSONFactoryUtil.createJSONObject());

		_editorData.put("editorConfig", editorConfigJSONObject);

		JSONObject editorOptionsJSONObject = JSONFactoryUtil.createJSONObject();

		editorOptionsJSONObject.put("showSource", Boolean.FALSE);
		editorOptionsJSONObject.put("textMode", Boolean.FALSE);

		_editorData.put("editorOptions", editorOptionsJSONObject);
	}

	private final Map<String, Object> _editorData = new HashMap<>();

}