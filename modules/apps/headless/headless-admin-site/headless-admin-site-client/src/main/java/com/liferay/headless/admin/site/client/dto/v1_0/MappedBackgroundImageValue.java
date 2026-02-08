/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.MappedBackgroundImageValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class MappedBackgroundImageValue
	extends BackgroundImageValue implements Cloneable, Serializable {

	public static MappedBackgroundImageValue toDTO(String json) {
		return MappedBackgroundImageValueSerDes.toDTO(json);
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
	public MappedBackgroundImageValue clone()
		throws CloneNotSupportedException {

		return (MappedBackgroundImageValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MappedBackgroundImageValue)) {
			return false;
		}

		MappedBackgroundImageValue mappedBackgroundImageValue =
			(MappedBackgroundImageValue)object;

		return Objects.equals(
			toString(), mappedBackgroundImageValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MappedBackgroundImageValueSerDes.toJSON(this);
	}

}