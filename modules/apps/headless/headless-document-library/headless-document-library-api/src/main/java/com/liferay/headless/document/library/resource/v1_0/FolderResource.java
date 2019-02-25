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

package com.liferay.headless.document.library.resource.v1_0;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-document-library/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface FolderResource {

	public Page<Folder> getContentSpaceFoldersPage(
				Long contentSpaceId,Pagination pagination)
			throws Exception;
	public Folder postContentSpaceFolder(
				Long contentSpaceId,Folder folder)
			throws Exception;
	public boolean deleteFolder(
				Long folderId)
			throws Exception;
	public Folder getFolder(
				Long folderId)
			throws Exception;
	public Folder patchFolder(
				Long folderId,Folder folder)
			throws Exception;
	public Folder putFolder(
				Long folderId,Folder folder)
			throws Exception;
	public Page<Folder> getFolderFoldersPage(
				Long folderId,Pagination pagination)
			throws Exception;
	public Folder postFolderFolder(
				Long folderId,Folder folder)
			throws Exception;

	public void setContextCompany(Company contextCompany);

}