/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.function.UnsafeSupplier;
import com.example.sample.client.serdes.v1_0_0.ExternalReferenceElement2SerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class ExternalReferenceElement2 implements Cloneable, Serializable {

	public static ExternalReferenceElement2 toDTO(String json) {
		return ExternalReferenceElement2SerDes.toDTO(json);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

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

	@Override
	public ExternalReferenceElement2 clone() throws CloneNotSupportedException {
		return (ExternalReferenceElement2)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExternalReferenceElement2)) {
			return false;
		}

		ExternalReferenceElement2 externalReferenceElement2 =
			(ExternalReferenceElement2)object;

		return Objects.equals(toString(), externalReferenceElement2.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ExternalReferenceElement2SerDes.toJSON(this);
	}

}