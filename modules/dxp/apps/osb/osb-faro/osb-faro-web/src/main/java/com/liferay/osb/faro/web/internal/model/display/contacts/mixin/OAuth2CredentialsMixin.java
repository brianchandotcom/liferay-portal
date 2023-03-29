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

package com.liferay.osb.faro.web.internal.model.display.contacts.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Matthew Kong
 */
public abstract class OAuth2CredentialsMixin {

	@JsonIgnore
	public abstract String getOAuthAuthorizationURL();

	@JsonIgnore
	public abstract String getOAuthCallbackURL();

	@JsonIgnore
	public abstract String getOAuthClientId();

	@JsonIgnore
	public abstract String getOAuthClientSecret();

	@JsonIgnore
	public abstract String getOAuthCode();

	@JsonIgnore
	public abstract String getOAuthOwner();

	@JsonIgnore
	public abstract String getOAuthRefreshToken();

}