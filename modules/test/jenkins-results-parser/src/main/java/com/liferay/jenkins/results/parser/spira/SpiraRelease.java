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

import java.io.IOException;

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

		String indentLevel = getIndentLevel();

		int parentSpiraReleaseCount = (indentLevel.length() / 3) - 1;

		_parentSpiraReleases = new SpiraRelease[parentSpiraReleaseCount];

		for (int i = 1; i <= parentSpiraReleaseCount; i++) {
			String parentIndentLevel = indentLevel.substring(0, i * 3);

			try {
				SpiraRelease parentSpiraRelease =
					SpiraReleaseUtil.getSpiraReleaseByIndentLevel(
						_spiraProject.getID(), parentIndentLevel);

				_parentSpiraReleases[i - 1] = parentSpiraRelease;
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	protected String getIndentLevel() {
		return _jsonObject.getString("IndentLevel");
	}

	private final JSONObject _jsonObject;
	private final SpiraRelease[] _parentSpiraReleases;
	private final SpiraProject _spiraProject;

}