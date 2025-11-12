/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentElementSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentElement implements Cloneable, Serializable {

	public static FragmentElement toDTO(String json) {
		return FragmentElementSerDes.toDTO(json);
	}

	public FragmentElementValue getFragmentElementValue() {
		return fragmentElementValue;
	}

	public void setFragmentElementValue(
		FragmentElementValue fragmentElementValue) {

		this.fragmentElementValue = fragmentElementValue;
	}

	public void setFragmentElementValue(
		UnsafeSupplier<FragmentElementValue, Exception>
			fragmentElementValueUnsafeSupplier) {

		try {
			fragmentElementValue = fragmentElementValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentElementValue fragmentElementValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	@Override
	public FragmentElement clone() throws CloneNotSupportedException {
		return (FragmentElement)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentElement)) {
			return false;
		}

		FragmentElement fragmentElement = (FragmentElement)object;

		return Objects.equals(toString(), fragmentElement.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentElementSerDes.toJSON(this);
	}

}