/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class User {

	public User(
		long userId, long organizationId, String name, String email,
		String roles, String href) {

		_userId = userId;
		_organizationId = organizationId;
		_name = name;
		_email = email;
		_roles = roles;
		_href = href;
	}

	public String getEmail() {
		return _email;
	}

	public String getHref() {
		return _href;
	}

	public String getName() {
		return _name;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public String getRoles() {
		return _roles;
	}

	public long getUserId() {
		return _userId;
	}

	private final String _email;
	private final String _href;
	private final String _name;
	private final long _organizationId;
	private final String _roles;
	private final long _userId;

}