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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register.model;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Polesovsky
 */
public class DynamicClientRegistrationRequest {

	@JSON(name = "application_type")
	public String getApplicationType() {
		return _applicationType;
	}

	@JSON(name = "client_name")
	public String getClientName() {
		return _clientName;
	}

	@JSON(name = "client_uri")
	public String getClientUri() {
		return _clientUri;
	}

	@JSON(name = "contacts")
	public List<String> getContacts() {
		return _contacts;
	}

	@JSON(name = "extensions")
	public List<String> getExtensions() {
		return _extensions;
	}

	@JSON(name = "grant_types")
	public List<String> getGrantTypes() {
		return _grantTypes;
	}

	@JSON(name = "logo_uri")
	public String getLogoUri() {
		return _logoUri;
	}

	@JSON(name = "policy_uri")
	public String getPolicyUri() {
		return _policyUri;
	}

	@JSON(name = "redirect_uris")
	public List<String> getRedirectURIs() {
		return _redirectURIs;
	}

	@JSON(name = "response_types")
	public List<String> getResponseTypes() {
		return _responseTypes;
	}

	@JSON(name = "scope")
	public String getScope() {
		return _scope;
	}

	@JSON(name = "software_id")
	public String getSoftwareId() {
		return _softwareId;
	}

	@JSON(name = "software_statement")
	public String getSoftwareStatement() {
		return _softwareStatement;
	}

	@JSON(name = "software_version")
	public String getSoftwareVersion() {
		return _softwareVersion;
	}

	@JSON(name = "token_endpoint_auth_method")
	public String getTokenEndpointAuthMethod() {
		return _tokenEndpointAuthMethod;
	}

	@JSON(name = "tos_uri")
	public String getTosUri() {
		return _tosUri;
	}

	public void setApplicationType(String applicationType) {
		_applicationType = applicationType;
	}

	public void setClientName(String clientName) {
		_clientName = clientName;
	}

	public void setClientUri(String clientUri) {
		_clientUri = clientUri;
	}

	public void setContacts(List<String> contacts) {
		_contacts = contacts;
	}

	public void setExtensions(List<String> extensions) {
		_extensions = extensions;
	}

	public void setGrantTypes(List<String> grantTypes) {
		_grantTypes = grantTypes;
	}

	public void setLogoUri(String logoUri) {
		_logoUri = logoUri;
	}

	public void setPolicyUri(String policyUri) {
		_policyUri = policyUri;
	}

	public void setRedirectURIs(List<String> redirectURIs) {
		_redirectURIs = redirectURIs;
	}

	public void setResponseTypes(List<String> responseTypes) {
		_responseTypes = responseTypes;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setSoftwareId(String softwareId) {
		_softwareId = softwareId;
	}

	public void setSoftwareStatement(String softwareStatement) {
		_softwareStatement = softwareStatement;
	}

	public void setSoftwareVersion(String softwareVersion) {
		_softwareVersion = softwareVersion;
	}

	public void setTokenEndpointAuthMethod(String tokenEndpointAuthMethod) {
		_tokenEndpointAuthMethod = tokenEndpointAuthMethod;
	}

	public void setTosUri(String tosUri) {
		_tosUri = tosUri;
	}

	private String _applicationType;
	private String _clientName;
	private String _clientUri;
	private List<String> _contacts = new ArrayList<>();
	private List<String> _extensions = new ArrayList<>();
	private List<String> _grantTypes = new ArrayList<>();
	private String _logoUri;
	private String _policyUri;
	private List<String> _redirectURIs = new ArrayList<>();
	private List<String> _responseTypes = new ArrayList<>();
	private String _scope;
	private String _softwareId;
	private String _softwareStatement;
	private String _softwareVersion;
	private String _tokenEndpointAuthMethod;
	private String _tosUri;

}