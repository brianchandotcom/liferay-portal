/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.function.UnsafeSupplier;
import com.example.sample.client.serdes.v1_0_0.UnreferencedSchemaComponentSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class UnreferencedSchemaComponent implements Cloneable, Serializable {

	public static UnreferencedSchemaComponent toDTO(String json) {
		return UnreferencedSchemaComponentSerDes.toDTO(json);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public ExternalReferenceElement2[] getExternalReferenceElement2s() {
		return externalReferenceElement2s;
	}

	public void setExternalReferenceElement2s(
		ExternalReferenceElement2[] externalReferenceElement2s) {

		this.externalReferenceElement2s = externalReferenceElement2s;
	}

	public void setExternalReferenceElement2s(
		UnsafeSupplier<ExternalReferenceElement2[], Exception>
			externalReferenceElement2sUnsafeSupplier) {

		try {
			externalReferenceElement2s =
				externalReferenceElement2sUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ExternalReferenceElement2[] externalReferenceElement2s;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	@Override
	public UnreferencedSchemaComponent clone()
		throws CloneNotSupportedException {

		return (UnreferencedSchemaComponent)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UnreferencedSchemaComponent)) {
			return false;
		}

		UnreferencedSchemaComponent unreferencedSchemaComponent =
			(UnreferencedSchemaComponent)object;

		return Objects.equals(
			toString(), unreferencedSchemaComponent.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UnreferencedSchemaComponentSerDes.toJSON(this);
	}

}