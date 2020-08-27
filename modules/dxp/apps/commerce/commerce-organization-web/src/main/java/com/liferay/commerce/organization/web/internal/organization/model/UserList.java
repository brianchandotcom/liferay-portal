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

package com.liferay.commerce.organization.web.internal.organization.model;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class UserList {

	public UserList(List<User> users, int total) {
		_users = users;
		_total = total;
	}

	public UserList(String[] errorMessages) {
		_errorMessages = errorMessages;
		_success = false;
	}

	public int getTotal() {
		return _total;
	}

	public List<User> getUsers() {
		return _users;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private String[] _errorMessages;
	private boolean _success;
	private int _total;
	private List<User> _users;

}