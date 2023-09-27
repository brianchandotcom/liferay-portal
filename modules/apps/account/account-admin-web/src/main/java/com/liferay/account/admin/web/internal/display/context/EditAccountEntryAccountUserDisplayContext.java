/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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