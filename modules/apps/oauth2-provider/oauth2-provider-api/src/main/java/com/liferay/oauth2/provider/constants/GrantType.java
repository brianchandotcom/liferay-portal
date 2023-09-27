/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	CLIENT_CREDENTIALS(false, true, false), JWT_BEARER(false, true, true),
	REFRESH_TOKEN(false, true, true),
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