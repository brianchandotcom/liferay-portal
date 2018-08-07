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

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitRef {

	public String getName() {
		return _name;
	}

	public String getSHA() {
		return _sha;
	}

	protected BaseGitRef(String name, String sha) {
		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("Name is null");
		}

		if ((sha == null) || sha.isEmpty()) {
			throw new IllegalArgumentException("SHA is null");
		}

		if (!sha.matches("[0-9a-f]{7,40}")) {
			throw new IllegalArgumentException("SHA is invalid");
		}

		_name = name;
		_sha = sha;
	}

	private final String _name;
	private final String _sha;

}