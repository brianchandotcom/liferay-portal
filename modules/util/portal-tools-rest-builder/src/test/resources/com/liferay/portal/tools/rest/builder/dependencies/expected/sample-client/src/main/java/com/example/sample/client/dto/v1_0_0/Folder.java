/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.function.UnsafeSupplier;
import com.example.sample.client.serdes.v1_0_0.FolderSerDes;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class Folder implements Cloneable, Serializable {

	public static Folder toDTO(String json) {
		return FolderSerDes.toDTO(json);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

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

	public Folder getDocumentsRepository() {
		return documentsRepository;
	}

	public void setDocumentsRepository(Folder documentsRepository) {
		this.documentsRepository = documentsRepository;
	}

	public void setDocumentsRepository(
		UnsafeSupplier<Folder, Exception> documentsRepositoryUnsafeSupplier) {

		try {
			documentsRepository = documentsRepositoryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Folder documentsRepository;

	public ExternalReferenceElement1[] getExternalReferenceElement1s() {
		return externalReferenceElement1s;
	}

	public void setExternalReferenceElement1s(
		ExternalReferenceElement1[] externalReferenceElement1s) {

		this.externalReferenceElement1s = externalReferenceElement1s;
	}

	public void setExternalReferenceElement1s(
		UnsafeSupplier<ExternalReferenceElement1[], Exception>
			externalReferenceElement1sUnsafeSupplier) {

		try {
			externalReferenceElement1s =
				externalReferenceElement1sUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ExternalReferenceElement1[] externalReferenceElement1s;

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

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public void setSelf(UnsafeSupplier<String, Exception> selfUnsafeSupplier) {
		try {
			self = selfUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String self;

	public Folder getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(Folder subFolders) {
		this.subFolders = subFolders;
	}

	public void setSubFolders(
		UnsafeSupplier<Folder, Exception> subFoldersUnsafeSupplier) {

		try {
			subFolders = subFoldersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Folder subFolders;

	@Override
	public Folder clone() throws CloneNotSupportedException {
		return (Folder)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Folder)) {
			return false;
		}

		Folder folder = (Folder)object;

		return Objects.equals(toString(), folder.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FolderSerDes.toJSON(this);
	}

}