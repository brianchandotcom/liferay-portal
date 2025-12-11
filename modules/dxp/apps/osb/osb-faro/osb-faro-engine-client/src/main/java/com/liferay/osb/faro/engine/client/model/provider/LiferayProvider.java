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
public class LiferayProvider implements Provider {

	public static final String TYPE = "LIFERAY";

	public AnalyticsConfiguration getAnalyticsConfiguration() {
		return _analyticsConfiguration;
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

	public void setAnalyticsConfiguration(
		AnalyticsConfiguration analyticsConfiguration) {

		_analyticsConfiguration = analyticsConfiguration;
	}

	public void setChannelsConfiguration(
		ChannelsConfiguration channelsConfiguration) {

		_channelsConfiguration = channelsConfiguration;
	}

	public void setContactsConfiguration(
		ContactsConfiguration contactsConfiguration) {

		_contactsConfiguration = contactsConfiguration;
	}

	public static class AnalyticsConfiguration {

		public String getAnalyticsKey() {
			return _analyticsKey;
		}

		public List<Container> getSites() {
			return _sites;
		}

		public boolean isEnableAllSites() {
			return _enableAllSites;
		}

		public void setAnalyticsKey(String analyticsKey) {
			_analyticsKey = analyticsKey;
		}

		public void setEnableAllSites(boolean enableAllSites) {
			_enableAllSites = enableAllSites;
		}

		public void setSites(List<Container> sites) {
			_sites = sites;
		}

		private String _analyticsKey;
		private boolean _enableAllSites;
		private List<Container> _sites;

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

		public List<Container> getOrganizations() {
			return _organizations;
		}

		public List<Container> getUserGroups() {
			return _userGroups;
		}

		public boolean isEnableAllContacts() {
			return _enableAllContacts;
		}

		public void setEnableAllContacts(boolean enableAllContacts) {
			_enableAllContacts = enableAllContacts;
		}

		public void setOrganizations(List<Container> organizations) {
			_organizations = organizations;
		}

		public void setUserGroups(List<Container> userGroups) {
			_userGroups = userGroups;
		}

		private boolean _enableAllContacts;
		private List<Container> _organizations;
		private List<Container> _userGroups;

	}

	public static class Container {

		public String getId() {
			return _id;
		}

		public boolean isEnableAllChildren() {
			return _enableAllChildren;
		}

		public void setEnableAllChildren(boolean enableAllChildren) {
			_enableAllChildren = enableAllChildren;
		}

		public void setId(String id) {
			_id = id;
		}

		private boolean _enableAllChildren;
		private String _id;

	}

	private AnalyticsConfiguration _analyticsConfiguration;
	private ChannelsConfiguration _channelsConfiguration;
	private ContactsConfiguration _contactsConfiguration;

}