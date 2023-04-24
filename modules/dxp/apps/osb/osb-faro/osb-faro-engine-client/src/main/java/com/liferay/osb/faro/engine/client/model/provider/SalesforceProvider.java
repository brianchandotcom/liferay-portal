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