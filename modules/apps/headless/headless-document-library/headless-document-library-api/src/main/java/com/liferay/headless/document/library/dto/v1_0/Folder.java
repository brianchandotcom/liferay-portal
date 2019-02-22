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

package com.liferay.headless.document.library.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Folder {

	public Date getDateCreated();

	public Date getDateModified();

	public String getDescription();

	public Boolean getHasDocuments();

	public Boolean getHasFolders();

	public Long getId();

	public String getName();

	public Long getRepositoryId();

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDateModified(Date dateModified);

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);

	public void setDescription(String description);

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);

	public void setHasDocuments(Boolean hasDocuments);

	public void setHasDocuments(
		UnsafeSupplier<Boolean, Throwable> hasDocumentsUnsafeSupplier);

	public void setHasFolders(Boolean hasFolders);

	public void setHasFolders(
		UnsafeSupplier<Boolean, Throwable> hasFoldersUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setRepositoryId(Long repositoryId);

	public void setRepositoryId(
		UnsafeSupplier<Long, Throwable> repositoryIdUnsafeSupplier);

}