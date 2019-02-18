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

package com.liferay.headless.document.library.resource.v1_0.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Folder")
public class FolderImpl implements Folder {

	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
	}

	public void setDateCreatedWithSupplier(UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {
	}

	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
			return dateModified;
	}

	public void setDateModified(Date dateModified) {
			this.dateModified = dateModified;
	}

	public void setDateModifiedWithSupplier(UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {
	}

	@JsonProperty
	protected Date dateModified;
	public String getDescription() {
			return description;
	}

	public void setDescription(String description) {
			this.description = description;
	}

	public void setDescriptionWithSupplier(UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {
	}

	@JsonProperty
	protected String description;
	public Document[] getDocuments() {
			return documents;
	}

	public void setDocuments(Document[] documents) {
			this.documents = documents;
	}

	public void setDocumentsWithSupplier(UnsafeSupplier<Document[], Throwable> documentsUnsafeSupplier) {
	}

	@JsonProperty
	protected Document[] documents;
	public Long[] getDocumentsIds() {
			return documentsIds;
	}

	public void setDocumentsIds(Long[] documentsIds) {
			this.documentsIds = documentsIds;
	}

	public void setDocumentsIdsWithSupplier(UnsafeSupplier<Long[], Throwable> documentsIdsUnsafeSupplier) {
	}

	@JsonProperty
	protected Long[] documentsIds;
	public Folder getDocumentsRepository() {
			return documentsRepository;
	}

	public void setDocumentsRepository(Folder documentsRepository) {
			this.documentsRepository = documentsRepository;
	}

	public void setDocumentsRepositoryWithSupplier(UnsafeSupplier<Folder, Throwable> documentsRepositoryUnsafeSupplier) {
	}

	@JsonProperty
	protected Folder documentsRepository;
	public Long getDocumentsRepositoryId() {
			return documentsRepositoryId;
	}

	public void setDocumentsRepositoryId(Long documentsRepositoryId) {
			this.documentsRepositoryId = documentsRepositoryId;
	}

	public void setDocumentsRepositoryIdWithSupplier(UnsafeSupplier<Long, Throwable> documentsRepositoryIdUnsafeSupplier) {
	}

	@JsonProperty
	protected Long documentsRepositoryId;
	public Long getId() {
			return id;
	}

	public void setId(Long id) {
			this.id = id;
	}

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	@JsonProperty
	protected Long id;
	public String getName() {
			return name;
	}

	public void setName(String name) {
			this.name = name;
	}

	public void setNameWithSupplier(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
	}

	@JsonProperty
	protected String name;
	public Folder[] getSubFolders() {
			return subFolders;
	}

	@JsonProperty
	public void setSubFolders(Folder[] subFolders) {
			this.subFolders = subFolders;
	}

	public void setSubFoldersWithSupplier(UnsafeSupplier<Folder[], Throwable> subFoldersUnsafeSupplier) {
	}

	@JsonProperty
	protected Folder[] subFolders;

}