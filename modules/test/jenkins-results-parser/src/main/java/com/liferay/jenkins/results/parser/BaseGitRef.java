/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitRef implements GitRef {

	@Override
	public String getName() {
		return _name;
	}

	@Override
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