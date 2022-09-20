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

package com.liferay.saml.admin.rest.client.dto.v1_0;

import com.liferay.saml.admin.rest.client.function.UnsafeSupplier;
import com.liferay.saml.admin.rest.client.serdes.v1_0.IdpSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Stian Sigvartsen
 * @generated
 */
@Generated("")
public class Idp implements Cloneable, Serializable {

	public static Idp toDTO(String json) {
		return IdpSerDes.toDTO(json);
	}

	public Boolean getAuthnRequestSignatureRequired() {
		return authnRequestSignatureRequired;
	}

	public void setAuthnRequestSignatureRequired(
		Boolean authnRequestSignatureRequired) {

		this.authnRequestSignatureRequired = authnRequestSignatureRequired;
	}

	public void setAuthnRequestSignatureRequired(
		UnsafeSupplier<Boolean, Exception>
			authnRequestSignatureRequiredUnsafeSupplier) {

		try {
			authnRequestSignatureRequired =
				authnRequestSignatureRequiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean authnRequestSignatureRequired;

	public Integer getDefaultAssertionLifetime() {
		return defaultAssertionLifetime;
	}

	public void setDefaultAssertionLifetime(Integer defaultAssertionLifetime) {
		this.defaultAssertionLifetime = defaultAssertionLifetime;
	}

	public void setDefaultAssertionLifetime(
		UnsafeSupplier<Integer, Exception>
			defaultAssertionLifetimeUnsafeSupplier) {

		try {
			defaultAssertionLifetime =
				defaultAssertionLifetimeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer defaultAssertionLifetime;

	public Long getSessionMaximumAge() {
		return sessionMaximumAge;
	}

	public void setSessionMaximumAge(Long sessionMaximumAge) {
		this.sessionMaximumAge = sessionMaximumAge;
	}

	public void setSessionMaximumAge(
		UnsafeSupplier<Long, Exception> sessionMaximumAgeUnsafeSupplier) {

		try {
			sessionMaximumAge = sessionMaximumAgeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long sessionMaximumAge;

	public Long getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(Long sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public void setSessionTimeout(
		UnsafeSupplier<Long, Exception> sessionTimeoutUnsafeSupplier) {

		try {
			sessionTimeout = sessionTimeoutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long sessionTimeout;

	@Override
	public Idp clone() throws CloneNotSupportedException {
		return (Idp)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Idp)) {
			return false;
		}

		Idp idp = (Idp)object;

		return Objects.equals(toString(), idp.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return IdpSerDes.toJSON(this);
	}

}