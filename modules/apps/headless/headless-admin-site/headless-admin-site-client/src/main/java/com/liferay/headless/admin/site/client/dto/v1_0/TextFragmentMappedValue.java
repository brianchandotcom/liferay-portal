/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TextFragmentMappedValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TextFragmentMappedValue
	extends TextFragmentValue implements Cloneable, Serializable {

	public static TextFragmentMappedValue toDTO(String json) {
		return TextFragmentMappedValueSerDes.toDTO(json);
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
	public TextFragmentMappedValue clone() throws CloneNotSupportedException {
		return (TextFragmentMappedValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TextFragmentMappedValue)) {
			return false;
		}

		TextFragmentMappedValue textFragmentMappedValue =
			(TextFragmentMappedValue)object;

		return Objects.equals(toString(), textFragmentMappedValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TextFragmentMappedValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:29714219