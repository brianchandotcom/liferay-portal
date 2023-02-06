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

package com.liferay.digital.signature.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DSRecipient {

	public String getDSClientUserId() {
		return dsClientUserId;
	}

	public String getDSRecipientId() {
		return dsRecipientId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public JSONObject getTabsJSONObject() {
		return tabsJSONObject;
	}

	public void setDSClientUserId(String dsClientUserId) {
		this.dsClientUserId = dsClientUserId;
	}

	public void setDSRecipientId(String dsRecipientId) {
		this.dsRecipientId = dsRecipientId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTabsJSONObject(JSONObject tabsJSONObject) {
		this.tabsJSONObject = tabsJSONObject;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"clientUserId", dsClientUserId
		).put(
			"email", emailAddress
		).put(
			"name", name
		).put(
			"recipientId", dsRecipientId
		).put(
			"status", status
		).put(
			"tabs", tabsJSONObject
		);
	}

	protected String dsClientUserId;
	protected String dsRecipientId;
	protected String emailAddress;
	protected String name;
	protected String status;
	protected JSONObject tabsJSONObject;

}