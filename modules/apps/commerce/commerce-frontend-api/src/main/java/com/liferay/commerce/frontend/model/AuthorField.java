/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class AuthorField {

	public AuthorField(String avatarUrl, String email, String name) {
		_avatarUrl = avatarUrl;
		_email = email;
		_name = name;
	}

	public String getAvatarUrl() {
		return _avatarUrl;
	}

	public String getEmail() {
		return _email;
	}

	public String getName() {
		return _name;
	}

	private final String _avatarUrl;
	private final String _email;
	private final String _name;

}