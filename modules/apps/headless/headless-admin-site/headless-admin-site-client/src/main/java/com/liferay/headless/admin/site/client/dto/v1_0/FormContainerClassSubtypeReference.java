/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormContainerClassSubtypeReferenceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormContainerClassSubtypeReference
	extends FormContainerReference implements Cloneable, Serializable {

	public static FormContainerClassSubtypeReference toDTO(String json) {
		return FormContainerClassSubtypeReferenceSerDes.toDTO(json);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassName(
		UnsafeSupplier<String, Exception> classNameUnsafeSupplier) {

		try {
			className = classNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String className;

	public ItemExternalReference getSubTypeExternalReference() {
		return subTypeExternalReference;
	}

	public void setSubTypeExternalReference(
		ItemExternalReference subTypeExternalReference) {

		this.subTypeExternalReference = subTypeExternalReference;
	}

	public void setSubTypeExternalReference(
		UnsafeSupplier<ItemExternalReference, Exception>
			subTypeExternalReferenceUnsafeSupplier) {

		try {
			subTypeExternalReference =
				subTypeExternalReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference subTypeExternalReference;

	@Override
	public FormContainerClassSubtypeReference clone()
		throws CloneNotSupportedException {

		return (FormContainerClassSubtypeReference)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormContainerClassSubtypeReference)) {
			return false;
		}

		FormContainerClassSubtypeReference formContainerClassSubtypeReference =
			(FormContainerClassSubtypeReference)object;

		return Objects.equals(
			toString(), formContainerClassSubtypeReference.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormContainerClassSubtypeReferenceSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1685726861