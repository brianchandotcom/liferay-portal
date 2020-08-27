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

package com.liferay.commerce.account.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Organization {

	public Organization(
		long organizationId, long accountId, String name, String path) {

		_organizationId = organizationId;
		_accountId = accountId;
		_name = name;
		_path = path;
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getName() {
		return _name;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public String getPath() {
		return _path;
	}

	private final long _accountId;
	private final String _name;
	private final long _organizationId;
	private final String _path;

}