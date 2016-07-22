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

package com.liferay.sync.engine.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BasePersistenceImpl;

/**
 * @author Shinn Lok
 */
@DatabaseTable(daoClass = BasePersistenceImpl.class, tableName = "SyncAccount")
public class SyncAccount extends StateAwareModel {

	public static final int STATE_CONNECTED = 2;

	public static final int STATE_CONNECTING = 1;

	public static final int STATE_DISCONNECTED = 0;

	public static final int UI_EVENT_AUTHENTICATION_EXCEPTION = 1;

	public static final int UI_EVENT_CONNECTION_EXCEPTION = 2;

	public static final int UI_EVENT_MIN_BUILD_REQUIREMENT_FAILED = 7;

	public static final int UI_EVENT_SYNC_ACCOUNT_FOLDER_MISSING = 3;

	public static final int UI_EVENT_SYNC_ACCOUNT_NOT_ACTIVE = 8;

	public static final int UI_EVENT_SYNC_SERVICES_NOT_ACTIVE = 6;

	public static final int UI_EVENT_SYNC_WEB_MISSING = 4;

	public static final int UI_EVENT_SYNC_WEB_OUT_OF_DATE = 5;

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof SyncAccount)) {
			return false;
		}

		SyncAccount syncAccount = (SyncAccount)object;

		if (syncAccount.getSyncAccountId() == syncAccountId) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getActive() {
		return active;
	}

	public int getAuthenticationRetryInterval() {
		return authenticationRetryInterval;
	}

	public int getBatchFileMaxSize() {
		return batchFileMaxSize;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public String getLogin() {
		return login;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public int getMaxDownloadRate() {
		return maxDownloadRate;
	}

	public int getMaxUploadRate() {
		return maxUploadRate;
	}

	public String getOAuthConsumerKey() {
		return oAuthConsumerKey;
	}

	public String getOAuthConsumerSecret() {
		return oAuthConsumerSecret;
	}

	public boolean getOAuthEnabled() {
		return oAuthEnabled;
	}

	public String getOAuthToken() {
		return oAuthToken;
	}

	public String getOAuthTokenSecret() {
		return oAuthTokenSecret;
	}

	public String getPassword() {
		return password;
	}

	public String getPluginVersion() {
		return pluginVersion;
	}

	public int getPollInterval() {
		return pollInterval;
	}

	public boolean getSocialOfficeInstalled() {
		return socialOfficeInstalled;
	}

	public long getSyncAccountId() {
		return syncAccountId;
	}

	public long getSyncContextModifiedTime() {
		return syncContextModifiedTime;
	}

	public boolean getTrustSelfSigned() {
		return trustSelfSigned;
	}

	public String getUrl() {
		return url;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		return (int)(syncAccountId ^ (syncAccountId >>> 32));
	}

	public boolean isActive() {
		return getActive();
	}

	public boolean isOAuthEnabled() {
		return getOAuthEnabled();
	}

	public boolean isSocialOfficeInstalled() {
		return getSocialOfficeInstalled();
	}

	public boolean isTrustSelfSigned() {
		return getTrustSelfSigned();
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAuthenticationRetryInterval(
		int authenticationRetryInterval) {

		this.authenticationRetryInterval = authenticationRetryInterval;
	}

	public void setBatchFileMaxSize(int batchFileMaxSize) {
		this.batchFileMaxSize = batchFileMaxSize;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public void setMaxDownloadRate(int maxDownloadRate) {
		this.maxDownloadRate = maxDownloadRate;
	}

	public void setMaxUploadRate(int maxUploadRate) {
		this.maxUploadRate = maxUploadRate;
	}

	public void setOAuthConsumerKey(String oAuthConsumerKey) {
		this.oAuthConsumerKey = oAuthConsumerKey;
	}

	public void setOAuthConsumerSecret(String oAuthConsumerSecret) {
		this.oAuthConsumerSecret = oAuthConsumerSecret;
	}

	public void setOAuthEnabled(boolean oAuthEnabled) {
		this.oAuthEnabled = oAuthEnabled;
	}

	public void setOAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public void setOAuthTokenSecret(String oAuthTokenSecret) {
		this.oAuthTokenSecret = oAuthTokenSecret;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}

	public void setSocialOfficeInstalled(boolean socialOfficeInstalled) {
		this.socialOfficeInstalled = socialOfficeInstalled;
	}

	public void setSyncAccountId(long syncAccountId) {
		this.syncAccountId = syncAccountId;
	}

	public void setSyncContextModifiedTime(long syncContextModifiedTime) {
		this.syncContextModifiedTime = syncContextModifiedTime;
	}

	public void setTrustSelfSigned(boolean trustSelfSigned) {
		this.trustSelfSigned = trustSelfSigned;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@DatabaseField(useGetSet = true)
	protected boolean active;

	@DatabaseField(useGetSet = true)
	protected int authenticationRetryInterval;

	@DatabaseField(useGetSet = true)
	protected int batchFileMaxSize;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String filePathName;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String login;

	@DatabaseField(useGetSet = true)
	protected int maxConnections;

	@DatabaseField(useGetSet = true)
	protected int maxDownloadRate;

	@DatabaseField(useGetSet = true)
	protected int maxUploadRate;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String oAuthConsumerKey;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String oAuthConsumerSecret;

	@DatabaseField(useGetSet = true)
	protected boolean oAuthEnabled;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String oAuthToken;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String oAuthTokenSecret;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String password;

	@DatabaseField(useGetSet = true)
	protected String pluginVersion;

	@DatabaseField(useGetSet = true)
	protected int pollInterval;

	@DatabaseField(useGetSet = true)
	protected boolean socialOfficeInstalled;

	@DatabaseField(generatedId = true, useGetSet = true)
	protected long syncAccountId;

	@DatabaseField(useGetSet = true)
	protected long syncContextModifiedTime;

	@DatabaseField(useGetSet = true)
	protected boolean trustSelfSigned;

	@DatabaseField(useGetSet = true, width = 16777216)
	protected String url;

	@DatabaseField(useGetSet = true)
	protected String uuid;

}