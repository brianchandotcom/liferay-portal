/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcos Martins
 */
public class IndividualSegmentRealTimeMembership {

	public String getAccountId() {
		return _accountId;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public long getFirstActivityTime() {
		return _firstSeenTime;
	}

	public String getIdentityId() {
		return _identityId;
	}

	public String getIndividualId() {
		return _individualId;
	}

	public long getLastActivityTime() {
		return _lastActivityTime;
	}

	public String getName() {
		return _name;
	}

	public String getProfileType() {
		return _profileType;
	}

	public String getType() {
		return _type;
	}

	public void setAccountId(String accountId) {
		_accountId = accountId;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setFirstActivityTime(long firstActivityTime) {
		_firstSeenTime = firstActivityTime;
	}

	public void setIdentityId(String identityId) {
		_identityId = identityId;
	}

	public void setIndividualId(String individualId) {
		_individualId = individualId;
	}

	public void setLastActivityTime(long lastActivityTime) {
		_lastActivityTime = lastActivityTime;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setProfileType(String profileType) {
		_profileType = profileType;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _accountId;
	private String _accountName;
	private String _emailAddress;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private long _firstSeenTime;
	private String _identityId;
	private String _individualId;
	private long _lastActivityTime;
	private String _name;
	private String _profileType;
	private String _type;

}