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

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;

/**
 * @author Drew Brokke
 */
public class EditAccountEntryAccountUserDisplayContext {

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public long getAccountUserId() {
		return _accountUserId;
	}

	public String getBackURL() {
		return _backURL;
	}

	public String getEditAccountUserActionURL() {
		return _editAccountUserActionURL;
	}

	public User getSelectedAccountUser() {
		return _selectedAccountUser;
	}

	public Contact getSelectedAccountUserContact() {
		return _selectedAccountUserContact;
	}

	public String getTitle() {
		return _title;
	}

	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	public void setAccountUserId(long accountUserId) {
		_accountUserId = accountUserId;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setEditAccountUserActionURL(String editAccountUserActionURL) {
		_editAccountUserActionURL = editAccountUserActionURL;
	}

	public void setSelectedAccountUser(User selectedAccountUser) {
		_selectedAccountUser = selectedAccountUser;
	}

	public void setSelectedAccountUserContact(
		Contact selectedAccountUserContact) {

		_selectedAccountUserContact = selectedAccountUserContact;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private long _accountEntryId;
	private long _accountUserId;
	private String _backURL;
	private String _editAccountUserActionURL;
	private User _selectedAccountUser;
	private Contact _selectedAccountUserContact;
	private String _title;

}