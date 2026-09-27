/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.ItemFragmentConfigurationFieldDefaultValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemFragmentConfigurationFieldDefaultValue
	implements Cloneable, Serializable {

	public static ItemFragmentConfigurationFieldDefaultValue toDTO(
		String json) {

		return ItemFragmentConfigurationFieldDefaultValueSerDes.toDTO(json);
	}

	public ItemValue getValue() {
		return value;
	}

	public void setValue(ItemValue value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<ItemValue, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemValue value;

	@Override
	public ItemFragmentConfigurationFieldDefaultValue clone()
		throws CloneNotSupportedException {

		return (ItemFragmentConfigurationFieldDefaultValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemFragmentConfigurationFieldDefaultValue)) {
			return false;
		}

		ItemFragmentConfigurationFieldDefaultValue
			itemFragmentConfigurationFieldDefaultValue =
				(ItemFragmentConfigurationFieldDefaultValue)object;

		return Objects.equals(
			toString(), itemFragmentConfigurationFieldDefaultValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ItemFragmentConfigurationFieldDefaultValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1721103413