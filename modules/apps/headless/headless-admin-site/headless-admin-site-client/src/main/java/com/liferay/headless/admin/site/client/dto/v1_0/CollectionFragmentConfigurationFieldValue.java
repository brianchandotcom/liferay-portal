/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static CollectionFragmentConfigurationFieldValue toDTO(String json) {
		return CollectionFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

	public CollectionReference getValue() {
		return value;
	}

	public void setValue(CollectionReference value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<CollectionReference, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionReference value;

	public Map<String, CollectionReference> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, CollectionReference> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, CollectionReference>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, CollectionReference> value_i18n;

	@Override
	public CollectionFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (CollectionFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionFragmentConfigurationFieldValue)) {
			return false;
		}

		CollectionFragmentConfigurationFieldValue
			collectionFragmentConfigurationFieldValue =
				(CollectionFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(), collectionFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1437349002