/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.NavigationMenuFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class NavigationMenuFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static NavigationMenuFragmentConfigurationFieldValue toDTO(
		String json) {

		return NavigationMenuFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

	public NavigationMenuValue getValue() {
		return value;
	}

	public void setValue(NavigationMenuValue value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<NavigationMenuValue, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected NavigationMenuValue value;

	public Map<String, NavigationMenuValue> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, NavigationMenuValue> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, NavigationMenuValue>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, NavigationMenuValue> value_i18n;

	@Override
	public NavigationMenuFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (NavigationMenuFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				NavigationMenuFragmentConfigurationFieldValue)) {

			return false;
		}

		NavigationMenuFragmentConfigurationFieldValue
			navigationMenuFragmentConfigurationFieldValue =
				(NavigationMenuFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(),
			navigationMenuFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NavigationMenuFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1107430268