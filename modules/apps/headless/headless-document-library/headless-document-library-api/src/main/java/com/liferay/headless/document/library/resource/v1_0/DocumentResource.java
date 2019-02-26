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

import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public interface DocumentResource {

	public boolean deleteDocument(Long documentId) throws Exception;

	public Page<Document> getContentSpaceDocumentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception;

	public Document getDocument(Long documentId) throws Exception;

	public Page<Document> getFolderDocumentsPage(
			Long folderId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Document patchDocument(Long documentId, MultipartBody multipartBody)
		throws Exception;

	public Document postContentSpaceDocument(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception;

	public Document postFolderDocument(
			Long folderId, MultipartBody multipartBody)
		throws Exception;

	public Document putDocument(Long documentId, MultipartBody multipartBody)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}