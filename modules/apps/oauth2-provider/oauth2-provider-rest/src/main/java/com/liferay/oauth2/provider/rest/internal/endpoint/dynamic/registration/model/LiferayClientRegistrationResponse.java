/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.model;

import java.util.List;

import org.apache.cxf.rs.security.oauth2.services.ClientRegistrationResponse;

/**
 * @author Jorge García Jiménez
 */
public class LiferayClientRegistrationResponse
	extends ClientRegistrationResponse {

	public String getClientName() {
		return clientName;
	}

	public String getJwks() {
		return jwks;
	}

	public String getJwksUri() {
		return jwksUri;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public List<String> getRedirectUris() {
		return redirectUris;
	}

	public String getResponseTypes() {
		return responseTypes;
	}

	public List<String> getScope() {
		return scope;
	}

	public String getSoftwareId() {
		return softwareId;
	}

	public String getTosUri() {
		return tosUri;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setJwks(String jwks) {
		this.jwks = jwks;
	}

	public void setJwksUri(String jwksUri) {
		this.jwksUri = jwksUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	public void setRedirectUris(List<String> redirectUris) {
		this.redirectUris = redirectUris;
	}

	public void setResponseTypes(String responseTypes) {
		this.responseTypes = responseTypes;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public void setTosUri(String tosUri) {
		this.tosUri = tosUri;
	}

	protected String clientName;
	protected String jwks;
	protected String jwksUri;
	protected String logoUri;
	protected List<String> redirectUris;
	protected String responseTypes;
	protected List<String> scope;
	protected String softwareId;
	protected String tosUri;

}