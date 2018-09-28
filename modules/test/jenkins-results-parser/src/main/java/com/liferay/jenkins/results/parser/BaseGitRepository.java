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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class BaseGitRepository
	extends JSONObject implements GitRepository {

	@Override
	public String getName() {
		return getString("name");
	}

	@Override
	public JSONObject put(String key, Object value) throws JSONException {
		if (has(key)) {
			throw new RuntimeException("Already contains " + key);
		}

		super.put(key, value);

		return this;
	}

	protected BaseGitRepository(String name) {
		super("{}");

		_setName(name);

		validateKeys(_REQUIRED_KEYS);
	}

	protected File getFile(String key) {
		return new File(getString(key));
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
						_REPOSITORY_PROPERTIES_URL, false)));
		}
		catch (IOException ioe) {
			System.out.println(
				"Skipped downloading " + _REPOSITORY_PROPERTIES_URL);
		}

		_repositoryProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File("repository.properties")));

		return _repositoryProperties;
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

	private static final String _REPOSITORY_PROPERTIES_URL =
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/repository.properties";

	private static final String[] _REQUIRED_KEYS = {"name"};

	private static Properties _repositoryProperties;

}