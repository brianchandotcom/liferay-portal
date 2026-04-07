/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CategoryFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CategoryFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static CategoryFragmentConfigurationFieldValue toDTO(String json) {
		return CategoryFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

	public ItemExternalReference getValue() {
		return value;
	}

	public void setValue(ItemExternalReference value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<ItemExternalReference, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference value;

	public Map<String, ItemExternalReference> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, ItemExternalReference> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, ItemExternalReference>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ItemExternalReference> value_i18n;

	@Override
	public CategoryFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (CategoryFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CategoryFragmentConfigurationFieldValue)) {
			return false;
		}

		CategoryFragmentConfigurationFieldValue
			categoryFragmentConfigurationFieldValue =
				(CategoryFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(), categoryFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CategoryFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2006690186