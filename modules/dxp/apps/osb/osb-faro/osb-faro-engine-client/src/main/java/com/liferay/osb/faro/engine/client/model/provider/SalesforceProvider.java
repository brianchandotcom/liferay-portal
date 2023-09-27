/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.provider;

import com.liferay.osb.faro.engine.client.model.Provider;

/**
 * @author Matthew Kong
 */
public class SalesforceProvider implements Provider {

	public static final String TYPE = "SALESFORCE";

	public AccountsConfiguration getAccountsConfiguration() {
		return _accountsConfiguration;
	}

	public ContactsConfiguration getContactsConfiguration() {
		return _contactsConfiguration;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public void setAccountsConfiguration(
		AccountsConfiguration accountsConfiguration) {

		_accountsConfiguration = accountsConfiguration;
	}

	public void setContactsConfiguration(
		ContactsConfiguration contactsConfiguration) {

		_contactsConfiguration = contactsConfiguration;
	}

	public static class AccountsConfiguration {

		public boolean isEnableAllAccounts() {
			return _enableAllAccounts;
		}

		public void setEnableAllAccounts(boolean enableAllAccounts) {
			_enableAllAccounts = enableAllAccounts;
		}

		private boolean _enableAllAccounts;

	}

	public static class ContactsConfiguration {

		public boolean isEnableAllContacts() {
			return _enableAllContacts;
		}

		public boolean isEnableAllLeads() {
			return _enableAllLeads;
		}

		public void setEnableAllContacts(boolean enableAllContacts) {
			_enableAllContacts = enableAllContacts;
		}

		public void setEnableAllLeads(boolean enableAllLeads) {
			_enableAllLeads = enableAllLeads;
		}

		private boolean _enableAllContacts;
		private boolean _enableAllLeads;

	}

	private AccountsConfiguration _accountsConfiguration;
	private ContactsConfiguration _contactsConfiguration;

}