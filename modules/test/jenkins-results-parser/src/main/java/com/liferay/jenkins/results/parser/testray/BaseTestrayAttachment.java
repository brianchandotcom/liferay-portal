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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayAttachment implements TestrayAttachment {

	@Override
	public boolean exists() {
		if (_exists != null) {
			return _exists;
		}

		_exists = JenkinsResultsParserUtil.exists(getURL());

		return _exists;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		if (!exists()) {
			return null;
		}

		try {
			return JenkinsResultsParserUtil.toString(String.valueOf(getURL()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected BaseTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		_testrayCaseResult = testrayCaseResult;
		_name = name;
		_key = key;
	}

	protected TestrayCaseResult getTestrayCaseResult() {
		return _testrayCaseResult;
	}

	private Boolean _exists;
	private final String _key;
	private final String _name;
	private final TestrayCaseResult _testrayCaseResult;

}