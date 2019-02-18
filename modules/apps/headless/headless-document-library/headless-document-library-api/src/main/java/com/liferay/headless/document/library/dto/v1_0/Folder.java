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

	public void setDateCreated(Date dateCreated);

	public void setDateCreatedWithSupplier(UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);
	public Date getDateModified();

	public void setDateModified(Date dateModified);

	public void setDateModifiedWithSupplier(UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);
	public String getDescription();

	public void setDescription(String description);

	public void setDescriptionWithSupplier(UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);
	public Document[] getDocuments();

	public void setDocuments(Document[] documents);

	public void setDocumentsWithSupplier(UnsafeSupplier<Document[], Throwable> documentsUnsafeSupplier);
	public Long[] getDocumentsIds();

	public void setDocumentsIds(Long[] documentsIds);

	public void setDocumentsIdsWithSupplier(UnsafeSupplier<Long[], Throwable> documentsIdsUnsafeSupplier);
	public Folder getDocumentsRepository();

	public void setDocumentsRepository(Folder documentsRepository);

	public void setDocumentsRepositoryWithSupplier(UnsafeSupplier<Folder, Throwable> documentsRepositoryUnsafeSupplier);
	public Long getDocumentsRepositoryId();

	public void setDocumentsRepositoryId(Long documentsRepositoryId);

	public void setDocumentsRepositoryIdWithSupplier(UnsafeSupplier<Long, Throwable> documentsRepositoryIdUnsafeSupplier);
	public Long getId();

	public void setId(Long id);

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);
	public String getName();

	public void setName(String name);

	public void setNameWithSupplier(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);
	public Folder[] getSubFolders();

	public void setSubFolders(Folder[] subFolders);

	public void setSubFoldersWithSupplier(UnsafeSupplier<Folder[], Throwable> subFoldersUnsafeSupplier);

}