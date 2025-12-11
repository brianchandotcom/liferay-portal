/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.provider;

import com.liferay.osb.faro.engine.client.model.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Matthew Kong
 */
public class SalesforceProvider implements Provider {

	public static final String TYPE = "SALESFORCE";

	public AccountsConfiguration getAccountsConfiguration() {
		return _accountsConfiguration;
	}

	public ChannelsConfiguration getChannelsConfiguration() {
		return _channelsConfiguration;
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

	public void setChannelsConfiguration(
		ChannelsConfiguration channelsConfiguration) {

		_channelsConfiguration = channelsConfiguration;
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

	public static class Channel {

		public String getChannelId() {
			return _channelId;
		}

		public Set<Long> getCommerceChannelIds() {
			return _commerceChannelIds;
		}

		public Set<Long> getGroupIds() {
			return _groupIds;
		}

		public Boolean isEnabled() {
			return _enabled;
		}

		public void setChannelId(String channelId) {
			_channelId = channelId;
		}

		public void setCommerceChannelIds(Set<Long> commerceChannelIds) {
			_commerceChannelIds = commerceChannelIds;
		}

		public void setEnabled(Boolean enabled) {
			_enabled = enabled;
		}

		public void setGroupIds(Set<Long> groupIds) {
			_groupIds = groupIds;
		}

		private String _channelId;
		private Set<Long> _commerceChannelIds;
		private Boolean _enabled;
		private Set<Long> _groupIds;

	}

	public static class ChannelsConfiguration {

		public List<Channel> getChannels() {
			return _channels;
		}

		public boolean isEnableAllChannels() {
			return _enableAllChannels;
		}

		public void setChannels(List<Channel> channels) {
			_channels = channels;
		}

		public void setEnableAllChannels(boolean enableAllChannels) {
			_enableAllChannels = enableAllChannels;
		}

		private List<Channel> _channels = new ArrayList<>();
		private boolean _enableAllChannels;

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
	private ChannelsConfiguration _channelsConfiguration;
	private ContactsConfiguration _contactsConfiguration;

}