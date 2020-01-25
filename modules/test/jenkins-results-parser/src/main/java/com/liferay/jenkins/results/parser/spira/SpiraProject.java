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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraProject {

	public void addSpiraRelease(SpiraRelease spiraRelease) {
		_spiraReleaseByID.put(spiraRelease.getID(), spiraRelease);
		_spiraReleaseByIndentLevel.put(
			spiraRelease.getIndentLevel(), spiraRelease);
		_spiraReleaseByPath.put(spiraRelease.getPath(), spiraRelease);
	}

	public int getID() {
		return _jsonObject.getInt("ProjectId");
	}

	public SpiraRelease getSpiraReleaseByID(int releaseID) {
		return _spiraReleaseByID.get(releaseID);
	}

	public SpiraRelease getSpiraReleaseByIndentLevel(String indentLevel) {
		return _spiraReleaseByIndentLevel.get(indentLevel);
	}

	public SpiraRelease getSpiraReleaseByPath(String releasePath) {
		return _spiraReleaseByPath.get(releasePath);
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", getID());

		return jsonObject;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toString();
	}

	protected SpiraProject(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;
	private final Map<Integer, SpiraRelease> _spiraReleaseByID =
		new HashMap<>();
	private final Map<String, SpiraRelease> _spiraReleaseByIndentLevel =
		new HashMap<>();
	private final Map<String, SpiraRelease> _spiraReleaseByPath =
		new HashMap<>();

}