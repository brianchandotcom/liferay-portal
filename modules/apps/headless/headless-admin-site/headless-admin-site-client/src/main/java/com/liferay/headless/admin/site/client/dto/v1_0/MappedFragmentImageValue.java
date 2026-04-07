/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.MappedFragmentImageValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class MappedFragmentImageValue
	extends FragmentImageValue implements Cloneable, Serializable {

	public static MappedFragmentImageValue toDTO(String json) {
		return MappedFragmentImageValueSerDes.toDTO(json);
	}

	public FragmentMappedValue getFragmentMappedValue() {
		return fragmentMappedValue;
	}

	public void setFragmentMappedValue(
		FragmentMappedValue fragmentMappedValue) {

		this.fragmentMappedValue = fragmentMappedValue;
	}

	public void setFragmentMappedValue(
		UnsafeSupplier<FragmentMappedValue, Exception>
			fragmentMappedValueUnsafeSupplier) {

		try {
			fragmentMappedValue = fragmentMappedValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentMappedValue fragmentMappedValue;

	@Override
	public MappedFragmentImageValue clone() throws CloneNotSupportedException {
		return (MappedFragmentImageValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MappedFragmentImageValue)) {
			return false;
		}

		MappedFragmentImageValue mappedFragmentImageValue =
			(MappedFragmentImageValue)object;

		return Objects.equals(toString(), mappedFragmentImageValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MappedFragmentImageValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-584602853