/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.dto.v1_0;

import com.liferay.ai.hub.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.rest.client.serdes.v1_0.CredentialSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class Credential implements Cloneable, Serializable {

	public static Credential toDTO(String json) {
		return CredentialSerDes.toDTO(json);
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientId(
		UnsafeSupplier<String, Exception> clientIdUnsafeSupplier) {

		try {
			clientId = clientIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String clientId;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setClientSecret(
		UnsafeSupplier<String, Exception> clientSecretUnsafeSupplier) {

		try {
			clientSecret = clientSecretUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String clientSecret;

	@Override
	public Credential clone() throws CloneNotSupportedException {
		return (Credential)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Credential)) {
			return false;
		}

		Credential credential = (Credential)object;

		return Objects.equals(toString(), credential.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CredentialSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1644151370