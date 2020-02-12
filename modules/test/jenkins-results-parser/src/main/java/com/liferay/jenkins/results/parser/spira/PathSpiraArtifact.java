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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class PathSpiraArtifact extends BaseSpiraArtifact {

	public String getPath() {
		String name = getName();

		name = name.replace("/", "\\/");

		PathSpiraArtifact parentSpiraArtifact = getParentSpiraArtifact();

		if (parentSpiraArtifact == null) {
			return "/" + name;
		}

		return JenkinsResultsParserUtil.combine(
			parentSpiraArtifact.getPath(), "/", name);
	}

	protected PathSpiraArtifact(JSONObject jsonObject) {
		super(jsonObject);

		this.jsonObject.put("Path", getPath());
	}

	protected abstract PathSpiraArtifact getParentSpiraArtifact();

}