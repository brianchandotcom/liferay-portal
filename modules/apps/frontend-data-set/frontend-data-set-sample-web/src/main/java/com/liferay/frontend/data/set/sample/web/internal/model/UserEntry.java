/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.model;

/**
 * @author Marko Cikos
 */
public class UserEntry {

	public UserEntry(
		String emailAddress, String firstName, Long id, String lastName) {

		_emailAddress = emailAddress;
		_firstName = firstName;
		_id = id;
		_lastName = lastName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getFirstName() {
		return _firstName;
	}

	public Long getId() {
		return _id;
	}

	public String getLastName() {
		return _lastName;
	}

	private final String _emailAddress;
	private final String _firstName;
	private final Long _id;
	private final String _lastName;

}