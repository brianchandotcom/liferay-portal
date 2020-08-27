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

/**
 * @author Alessio Antonio Rendina
 */
public class Account {

	public Account(long accountId, String name, String imageUrl, String email) {
		_accountId = accountId;
		_name = name;
		_imageUrl = imageUrl;
		_email = email;
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getEmail() {
		return _email;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public String getName() {
		return _name;
	}

	private final long _accountId;
	private final String _email;
	private final String _imageUrl;
	private final String _name;

}