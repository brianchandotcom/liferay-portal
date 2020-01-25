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

package com.liferay.jenkins.results.parser.spira;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRelease {

	public int getID() {
		return _jsonObject.getInt("ReleaseId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", getID());
		jsonObject.put("name", getName());
		jsonObject.put("project", _spiraProject.toJSONObject());

		return jsonObject.toString();
	}

	protected SpiraRelease(JSONObject jsonObject) {
		_jsonObject = jsonObject;
		_spiraProject = SpiraProjectUtil.getSpiraProjectById(
			jsonObject.getInt("ProjectId"));
	}

	private final JSONObject _jsonObject;
	private final SpiraProject _spiraProject;

}