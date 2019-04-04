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

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class BaseGitRepository implements GitRepository {

	public static void setRepositoryProperties(
		Properties repositoryProperties) {

		_repositoryProperties = repositoryProperties;
	}

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public String getName() {
		return getString("name");
	}

	protected BaseGitRepository(JSONObject jsonObject) {
		_jsonObject = jsonObject;

		validateKeys(_KEYS_REQUIRED);
	}

	protected BaseGitRepository(String name) {
		_jsonObject = new JSONObject();

		_setName(name);

		validateKeys(_KEYS_REQUIRED);
	}

	protected File getFile(String key) {
		return new File(getString(key));
	}

	protected JSONArray getJSONArray(String key) {
		return _jsonObject.getJSONArray(key);
	}

	protected Properties getRepositoryProperties() {
		if (_repositoryProperties != null) {
			return _repositoryProperties;
		}

		_repositoryProperties = new Properties();

		try {
			_repositoryProperties.load(
				new StringReader(
					JenkinsResultsParserUtil.toString(
						_URL_PROPERTIES_REPOSITORY, false)));
		}
		catch (IOException ioe) {
			System.out.println(
				"Skipped downloading " + _URL_PROPERTIES_REPOSITORY);
		}

		_repositoryProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File("repository.properties")));

		return _repositoryProperties;
	}

	protected String getString(String key) {
		return _jsonObject.getString(key);
	}

	protected boolean has(String key) {
		return _jsonObject.has(key);
	}

	protected String optString(String key) {
		return _jsonObject.optString(key);
	}

	protected void put(String key, Object value) {
		_jsonObject.put(key, value);
	}

	protected void validateKeys(String[] requiredKeys) {
		for (String requiredKey : requiredKeys) {
			if (!has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private void _setName(String name) {
		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("Name is null");
		}

		put("name", name);
	}

	private static final String[] _KEYS_REQUIRED = {"name"};

	private static final String _URL_PROPERTIES_REPOSITORY =
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/repository.properties";

	private static Properties _repositoryProperties;

	private final JSONObject _jsonObject;

}