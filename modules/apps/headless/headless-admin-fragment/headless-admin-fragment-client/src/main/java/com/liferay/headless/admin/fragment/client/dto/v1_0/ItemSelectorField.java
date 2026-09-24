/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.ItemSelectorFieldSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemSelectorField
	extends Field implements Cloneable, Serializable {

	public static ItemSelectorField toDTO(String json) {
		return ItemSelectorFieldSerDes.toDTO(json);
	}

	public ItemFragmentConfigurationFieldDefaultValue getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(
		ItemFragmentConfigurationFieldDefaultValue defaultValue) {

		this.defaultValue = defaultValue;
	}

	public void setDefaultValue(
		UnsafeSupplier<ItemFragmentConfigurationFieldDefaultValue, Exception>
			defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemFragmentConfigurationFieldDefaultValue defaultValue;

	public ItemSelectorTypeOptions getTypeOptions() {
		return typeOptions;
	}

	public void setTypeOptions(ItemSelectorTypeOptions typeOptions) {
		this.typeOptions = typeOptions;
	}

	public void setTypeOptions(
		UnsafeSupplier<ItemSelectorTypeOptions, Exception>
			typeOptionsUnsafeSupplier) {

		try {
			typeOptions = typeOptionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemSelectorTypeOptions typeOptions;

	@Override
	public ItemSelectorField clone() throws CloneNotSupportedException {
		return (ItemSelectorField)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemSelectorField)) {
			return false;
		}

		ItemSelectorField itemSelectorField = (ItemSelectorField)object;

		return Objects.equals(toString(), itemSelectorField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ItemSelectorFieldSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1639397758