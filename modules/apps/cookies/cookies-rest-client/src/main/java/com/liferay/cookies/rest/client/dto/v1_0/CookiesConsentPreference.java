/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.client.dto.v1_0;

import com.liferay.cookies.rest.client.function.UnsafeSupplier;
import com.liferay.cookies.rest.client.serdes.v1_0.CookiesConsentPreferenceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Christopher Kian
 * @generated
 */
@Generated("")
public class CookiesConsentPreference implements Cloneable, Serializable {

	public static CookiesConsentPreference toDTO(String json) {
		return CookiesConsentPreferenceSerDes.toDTO(json);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setDomain(
		UnsafeSupplier<String, Exception> domainUnsafeSupplier) {

		try {
			domain = domainUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String domain;

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		try {
			expirationDate = expirationDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date expirationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long userId;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String value;

	@Override
	public CookiesConsentPreference clone() throws CloneNotSupportedException {
		return (CookiesConsentPreference)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CookiesConsentPreference)) {
			return false;
		}

		CookiesConsentPreference cookiesConsentPreference =
			(CookiesConsentPreference)object;

		return Objects.equals(toString(), cookiesConsentPreference.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CookiesConsentPreferenceSerDes.toJSON(this);
	}

}