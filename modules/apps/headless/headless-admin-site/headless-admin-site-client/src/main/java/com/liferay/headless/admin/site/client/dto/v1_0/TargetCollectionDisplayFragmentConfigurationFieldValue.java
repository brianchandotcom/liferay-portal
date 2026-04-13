/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TargetCollectionDisplayFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TargetCollectionDisplayFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static TargetCollectionDisplayFragmentConfigurationFieldValue toDTO(
		String json) {

		return TargetCollectionDisplayFragmentConfigurationFieldValueSerDes.
			toDTO(json);
	}

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<String[], Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] value;

	public Object getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Object value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Object, Exception> value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object value_i18n;

	@Override
	public TargetCollectionDisplayFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (TargetCollectionDisplayFragmentConfigurationFieldValue)
			super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				TargetCollectionDisplayFragmentConfigurationFieldValue)) {

			return false;
		}

		TargetCollectionDisplayFragmentConfigurationFieldValue
			targetCollectionDisplayFragmentConfigurationFieldValue =
				(TargetCollectionDisplayFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(),
			targetCollectionDisplayFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TargetCollectionDisplayFragmentConfigurationFieldValueSerDes.
			toJSON(this);
	}

}