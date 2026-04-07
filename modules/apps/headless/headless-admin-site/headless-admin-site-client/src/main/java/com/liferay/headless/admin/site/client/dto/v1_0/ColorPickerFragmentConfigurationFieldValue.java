/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ColorPickerFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ColorPickerFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static ColorPickerFragmentConfigurationFieldValue toDTO(
		String json) {

		return ColorPickerFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

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

	public Map<String, String> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, String> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> value_i18n;

	@Override
	public ColorPickerFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (ColorPickerFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ColorPickerFragmentConfigurationFieldValue)) {
			return false;
		}

		ColorPickerFragmentConfigurationFieldValue
			colorPickerFragmentConfigurationFieldValue =
				(ColorPickerFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(), colorPickerFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ColorPickerFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1406428938