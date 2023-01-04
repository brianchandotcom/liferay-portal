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
 * @author José Abelenda
 */
public class DSRecipientViewDefinition {

	public String getAuthenticationMethod() {
		return authenticationMethod;
	}

	public String getDSClientUserId() {
		return dsClientUserId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setAuthenticationMethod(String authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public void setDSClientUserId(String dsClientUserId) {
		this.dsClientUserId = dsClientUserId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"authenticationMethod", getAuthenticationMethod()
		).put(
			"clientUserId", getDSClientUserId()
		).put(
			"email", getEmailAddress()
		).put(
			"returnUrl", getReturnUrl()
		).put(
			"userName", getUserName()
		);
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}

	protected String authenticationMethod;
	protected String dsClientUserId;
	protected String emailAddress;
	protected String returnUrl;
	protected String userName;

}