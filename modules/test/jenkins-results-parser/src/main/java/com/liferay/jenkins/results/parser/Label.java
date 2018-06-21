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

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Label {

	public Label(JSONObject jsonObject, GitHubRemoteRepository repository) {
		_jsonObject = jsonObject;
		_repository = repository;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Label)) {
			return false;
		}

		Label label = (Label)o;

		String color = getColor();
		String name = getName();

		if (color.equals(label.getColor()) && name.equals(label.getName())) {
			return true;
		}

		return false;
	}

	public String getColor() {
		return _jsonObject.getString("color");
	}

	public String getDescription() {
		return _jsonObject.optString("description");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public GitHubRemoteRepository getRepository() {
		return _repository;
	}

	@Override
	public int hashCode() {
		String name = getName();

		return name.hashCode();
	}

	@Override
	public String toString() {
		return _jsonObject.toString(4);
	}

	private final JSONObject _jsonObject;
	private final GitHubRemoteRepository _repository;

}