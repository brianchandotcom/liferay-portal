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
public abstract class IndentLevelSpiraArtifact extends PathSpiraArtifact {

	protected IndentLevelSpiraArtifact(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		if (_parentSpiraArtifact != null) {
			return _parentSpiraArtifact;
		}

		String indentLevel = _getIndentLevel();

		if (indentLevel.length() <= 3) {
			return null;
		}

		String parentIndentLevel = indentLevel.substring(
			0, indentLevel.length() - 3);

		_parentSpiraArtifact = getSpiraArtifactByIndentLevel(parentIndentLevel);

		return _parentSpiraArtifact;
	}

	protected abstract PathSpiraArtifact getSpiraArtifactByIndentLevel(
		String indentLevel);

	private String _getIndentLevel() {
		return jsonObject.getString("IndentLevel");
	}

	private PathSpiraArtifact _parentSpiraArtifact;

}