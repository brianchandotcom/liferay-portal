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

package com.liferay.sync.engine.documentlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncUser;

import java.util.List;
import java.util.Map;

/**
 * @author Dennis Ju
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncContext {

	public static final String PREFERENCE_KEY_MAX_CONNECTIONS =
		"sync.client.max.connections";

	public static final String PREFERENCE_KEY_POLL_INTERVAL =
		"sync.client.poll.interval";

	public String getPluginVersion() {
		return pluginVersion;
	}

	public int getPortalBuildNumber() {
		return portalBuildNumber;
	}

	public Map<String, String> getPortletPreferencesMap() {
		return portletPreferencesMap;
	}

	public List<SyncSite> getSyncSites() {
		return syncSites;
	}

	public SyncUser getSyncUser() {
		return syncUser;
	}

	public boolean isSocialOfficeInstalled() {
		return socialOfficeInstalled;
	}

	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	public void setPortalBuildNumber(int portalBuildNumber) {
		this.portalBuildNumber = portalBuildNumber;
	}

	public void setPortletPreferencesMap(
		Map<String, String> portletPreferencesMap) {

		this.portletPreferencesMap = portletPreferencesMap;
	}

	public void setSocialOfficeInstalled(boolean socialOfficeInstalled) {
		this.socialOfficeInstalled = socialOfficeInstalled;
	}

	public void setSyncSites(List<SyncSite> syncSites) {
		this.syncSites = syncSites;
	}

	public void setSyncUser(SyncUser syncUser) {
		this.syncUser = syncUser;
	}

	protected String pluginVersion;
	protected int portalBuildNumber;
	protected Map<String, String> portletPreferencesMap;
	protected boolean socialOfficeInstalled;

	@JsonProperty("userSitesGroups")
	protected List<SyncSite> syncSites;

	@JsonProperty("user")
	protected SyncUser syncUser;

}