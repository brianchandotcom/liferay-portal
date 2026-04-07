/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.URLFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class URLFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static URLFragmentConfigurationFieldValue toDTO(String json) {
		return URLFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

	public URLValue getValue() {
		return value;
	}

	public void setValue(URLValue value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<URLValue, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected URLValue value;

	public Map<String, URLValue> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, URLValue> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, URLValue>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, URLValue> value_i18n;

	@Override
	public URLFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (URLFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof URLFragmentConfigurationFieldValue)) {
			return false;
		}

		URLFragmentConfigurationFieldValue urlFragmentConfigurationFieldValue =
			(URLFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(), urlFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return URLFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:930717226