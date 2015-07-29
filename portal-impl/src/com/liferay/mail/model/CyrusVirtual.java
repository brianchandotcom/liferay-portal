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
public class CyrusVirtual implements Serializable {

	public static final Object[][] TABLE_COLUMNS = {
		{"emailAddress", Integer.valueOf(Types.VARCHAR)},
		{"userId", Integer.valueOf(Types.VARCHAR)}
	};

	public static final String TABLE_NAME = "CyrusVirtual";

	public static final String TABLE_SQL_CREATE =
		"create table CyrusVirtual (emailAddress VARCHAR(75) not null " +
			"primary key, userId VARCHAR(75) not null)";

	public CyrusVirtual() {
	}

	public CyrusVirtual(String emailAddress, long userId) {
		this(emailAddress, String.valueOf(userId));
	}

	public CyrusVirtual(String emailAddress, String userId) {
		_emailAddress = emailAddress;
		_userId = userId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getUserId() {
		return _userId;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	private String _emailAddress;
	private String _userId;

}