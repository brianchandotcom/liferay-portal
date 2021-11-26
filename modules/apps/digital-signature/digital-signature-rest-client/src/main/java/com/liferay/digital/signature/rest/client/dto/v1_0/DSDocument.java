/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.digital.signature.rest.client.dto.v1_0;

import com.liferay.digital.signature.rest.client.function.UnsafeSupplier;
import com.liferay.digital.signature.rest.client.serdes.v1_0.DSDocumentSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class DSDocument implements Cloneable, Serializable {

	public static DSDocument toDTO(String json) {
		return DSDocumentSerDes.toDTO(json);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setData(UnsafeSupplier<String, Exception> dataUnsafeSupplier) {
		try {
			data = dataUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String data;

	public String getFileEntryExternalReferenceCode() {
		return fileEntryExternalReferenceCode;
	}

	public void setFileEntryExternalReferenceCode(
		String fileEntryExternalReferenceCode) {

		this.fileEntryExternalReferenceCode = fileEntryExternalReferenceCode;
	}

	public void setFileEntryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			fileEntryExternalReferenceCodeUnsafeSupplier) {

		try {
			fileEntryExternalReferenceCode =
				fileEntryExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fileEntryExternalReferenceCode;

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setFileExtension(
		UnsafeSupplier<String, Exception> fileExtensionUnsafeSupplier) {

		try {
			fileExtension = fileExtensionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fileExtension;

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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUri(UnsafeSupplier<String, Exception> uriUnsafeSupplier) {
		try {
			uri = uriUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String uri;

	@Override
	public DSDocument clone() throws CloneNotSupportedException {
		return (DSDocument)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DSDocument)) {
			return false;
		}

		DSDocument dsDocument = (DSDocument)object;

		return Objects.equals(toString(), dsDocument.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DSDocumentSerDes.toJSON(this);
	}

}