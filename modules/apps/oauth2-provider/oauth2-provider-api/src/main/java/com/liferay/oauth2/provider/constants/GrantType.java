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

package com.liferay.oauth2.provider.constants;

/**
 * This enum represents all supported OAuth2 Grant flows that are used and
 * validated by the framework.
 *
 * @author Tomas Polesovsky
 * @review
 */
public enum GrantType {

	AUTHORIZATION_CODE(true, true, false),
	AUTHORIZATION_CODE_PKCE(true, false, true),
	CLIENT_CREDENTIALS(false, true, false), REFRESH_TOKEN(false, true, true),
	RESOURCE_OWNER_PASSWORD(false, true, true);

	public boolean isRequiresRedirectURI() {
		return _requiresRedirectURI;
	}

	public boolean isSupportsConfidentialClients() {
		return _supportsConfidentialClients;
	}

	public boolean isSupportsPublicClients() {
		return _supportsPublicClients;
	}

	private GrantType(
		boolean requiresRedirectURI, boolean supportsConfidentialClients,
		boolean supportsPublicClients) {

		_requiresRedirectURI = requiresRedirectURI;
		_supportsConfidentialClients = supportsConfidentialClients;
		_supportsPublicClients = supportsPublicClients;
	}

	private final boolean _requiresRedirectURI;
	private final boolean _supportsConfidentialClients;
	private final boolean _supportsPublicClients;

}