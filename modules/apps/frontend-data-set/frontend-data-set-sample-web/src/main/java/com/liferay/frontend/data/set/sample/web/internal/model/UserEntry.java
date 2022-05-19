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

package com.liferay.frontend.data.set.sample.web.internal.model;

/**
 * @author Marko Cikos
 */
public class UserEntry {

	public UserEntry(String emailAddress, String firstName, String lastName) {
		_emailAddress = emailAddress;
		_firstName = firstName;
		_lastName = lastName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	private final String _emailAddress;
	private final String _firstName;
	private final String _lastName;

}