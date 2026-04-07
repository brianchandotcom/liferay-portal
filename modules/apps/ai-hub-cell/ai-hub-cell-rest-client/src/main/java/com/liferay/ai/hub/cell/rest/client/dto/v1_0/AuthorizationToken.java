/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.client.dto.v1_0;

import com.liferay.ai.hub.cell.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.cell.rest.client.serdes.v1_0.AuthorizationTokenSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class AuthorizationToken implements Cloneable, Serializable {

	public static AuthorizationToken toDTO(String json) {
		return AuthorizationTokenSerDes.toDTO(json);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAccessToken(
		UnsafeSupplier<String, Exception> accessTokenUnsafeSupplier) {

		try {
			accessToken = accessTokenUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accessToken;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setScope(
		UnsafeSupplier<String, Exception> scopeUnsafeSupplier) {

		try {
			scope = scopeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String scope;

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public void setServiceURL(
		UnsafeSupplier<String, Exception> serviceURLUnsafeSupplier) {

		try {
			serviceURL = serviceURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String serviceURL;

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public void setUserToken(
		UnsafeSupplier<String, Exception> userTokenUnsafeSupplier) {

		try {
			userToken = userTokenUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String userToken;

	@Override
	public AuthorizationToken clone() throws CloneNotSupportedException {
		return (AuthorizationToken)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AuthorizationToken)) {
			return false;
		}

		AuthorizationToken authorizationToken = (AuthorizationToken)object;

		return Objects.equals(toString(), authorizationToken.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AuthorizationTokenSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1025382711