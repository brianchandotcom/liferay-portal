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

package com.liferay.mail.model;

import java.io.Serializable;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusUser implements Serializable {

	public static final Object[][] TABLE_COLUMNS = {
		{"userId", Integer.valueOf(Types.VARCHAR)},
		{"password_", Integer.valueOf(Types.VARCHAR)}
	};

	public static final String TABLE_NAME = "CyrusUser";

	public static final String TABLE_SQL_CREATE =
		"create table CyrusUser (userId VARCHAR(75) not null primary key, " +
			"password_ VARCHAR(75) not null)";

	public CyrusUser() {
	}

	public CyrusUser(long userId, String password) {
		this(String.valueOf(userId), password);
	}

	public CyrusUser(String userId, String password) {
		_userId = userId;
		_password = password;
	}

	public String getPassword() {
		return _password;
	}

	public String getUserId() {
		return _userId;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	private String _password;
	private String _userId;

}