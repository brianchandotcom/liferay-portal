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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	@JsonProperty
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {
	}

	@Override
	public Date getDateModified() {
		return dateModified;
	}

	@Override
	@JsonProperty
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	@JsonProperty
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {
	}

	@Override
	public Boolean getHasDocuments() {
		return hasDocuments;
	}

	@Override
	@JsonProperty
	public void setHasDocuments(Boolean hasDocuments) {
	}

	@Override
	@JsonIgnore
	public void setHasDocuments(
		UnsafeSupplier<Boolean, Throwable> hasDocumentsUnsafeSupplier) {
	}

	@Override
	public Boolean getHasFolders() {
		return hasFolders;
	}

	@Override
	@JsonProperty
	public void setHasFolders(Boolean hasFolders) {
	}

	@Override
	@JsonIgnore
	public void setHasFolders(
		UnsafeSupplier<Boolean, Throwable> hasFoldersUnsafeSupplier) {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	@JsonProperty
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@JsonIgnore
	public void setId(
		UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@JsonIgnore
	public void setName(
		UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
	}

	@Override
	public Long getRepositoryId() {
		return repositoryId;
	}

	@Override
	@JsonProperty
	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	@Override
	@JsonIgnore
	public void setRepositoryId(
		UnsafeSupplier<Long, Throwable> repositoryIdUnsafeSupplier) {
	}

	public void setSubFolders(Folder[] subFolders) {
		this.subFolders = subFolders;
	}

	@JsonIgnore
	public void setSubFolders(UnsafeSupplier<Folder[], Throwable> subFoldersUnsafeSupplier) {
	}

	protected Date dateCreated;
	protected Date dateModified;
	protected String description;
	protected Boolean hasDocuments;
	protected Boolean hasFolders;
	protected Long id;
	protected Long repositoryId;
	protected String name;
	protected Folder[] subFolders;
}