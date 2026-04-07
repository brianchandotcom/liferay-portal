/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentEditableElementSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentEditableElement implements Cloneable, Serializable {

	public static FragmentEditableElement toDTO(String json) {
		return FragmentEditableElementSerDes.toDTO(json);
	}

	public FragmentEditableElementValue getFragmentEditableElementValue() {
		return fragmentEditableElementValue;
	}

	public void setFragmentEditableElementValue(
		FragmentEditableElementValue fragmentEditableElementValue) {

		this.fragmentEditableElementValue = fragmentEditableElementValue;
	}

	public void setFragmentEditableElementValue(
		UnsafeSupplier<FragmentEditableElementValue, Exception>
			fragmentEditableElementValueUnsafeSupplier) {

		try {
			fragmentEditableElementValue =
				fragmentEditableElementValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentEditableElementValue fragmentEditableElementValue;

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
	public FragmentEditableElement clone() throws CloneNotSupportedException {
		return (FragmentEditableElement)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentEditableElement)) {
			return false;
		}

		FragmentEditableElement fragmentEditableElement =
			(FragmentEditableElement)object;

		return Objects.equals(toString(), fragmentEditableElement.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentEditableElementSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:876485283