/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.introspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Field names correspond to RFC.
 *
 * @author Tomas Polesovsky
 */
public class TokenIntrospection {

	public TokenIntrospection() {
	}

	public TokenIntrospection(boolean active) {
		_active = active;
	}

	public List<String> getAud() {
		return _aud;
	}

	public String getClientId() {
		return _clientId;
	}

	public Long getExp() {
		return _exp;
	}

	public Map<String, String> getExtensions() {
		return _extensions;
	}

	public Long getIat() {
		return _iat;
	}

	public String getIss() {
		return _iss;
	}

	public String getJti() {
		return _jti;
	}

	public Long getNbf() {
		return _nbf;
	}

	public String getScope() {
		return _scope;
	}

	public String getSub() {
		return _sub;
	}

	public String getTokenType() {
		return _tokenType;
	}

	public String getUsername() {
		return _username;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setAud(List<String> aud) {
		_aud = aud;
	}

	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	public void setExp(Long exp) {
		_exp = exp;
	}

	public void setExtensions(Map<String, String> extensions) {
		_extensions = extensions;
	}

	public void setIat(Long iat) {
		_iat = iat;
	}

	public void setIss(String iss) {
		_iss = iss;
	}

	public void setJti(String jti) {
		_jti = jti;
	}

	public void setNbf(Long nbf) {
		_nbf = nbf;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setSub(String sub) {
		_sub = sub;
	}

	public void setTokenType(String tokenType) {
		_tokenType = tokenType;
	}

	public void setUsername(String username) {
		_username = username;
	}

	private boolean _active;
	private List<String> _aud;
	private String _clientId;
	private Long _exp;
	private Map<String, String> _extensions = new HashMap<>();
	private Long _iat;
	private String _iss;
	private String _jti;
	private Long _nbf;
	private String _scope;
	private String _sub;
	private String _tokenType;
	private String _username;

}