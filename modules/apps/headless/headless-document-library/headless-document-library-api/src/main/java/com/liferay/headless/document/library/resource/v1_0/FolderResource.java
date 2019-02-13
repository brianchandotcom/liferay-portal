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

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Creator;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-document-library/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface FolderResource {

	@GET
	@Path("/documents-repositories/{documents-repository-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Folder getDocumentsRepository( @PathParam("documents-repository-id") Long documentsRepositoryId ) throws Exception;

	@GET
	@Path("/documents-repositories/{documents-repository-id}/folders")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Folder> getDocumentsRepositoryFoldersPage( @PathParam("documents-repository-id") Long documentsRepositoryId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents-repositories/{documents-repository-id}/folders")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Folder postDocumentsRepositoryFolder( @PathParam("documents-repository-id") Long documentsRepositoryId , Folder folder ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents-repositories/{documents-repository-id}/folders/batch-create")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Folder postDocumentsRepositoryFolderBatchCreate( @PathParam("documents-repository-id") Long documentsRepositoryId , Folder folder ) throws Exception;

	@DELETE
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Response deleteFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@GET
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Folder getFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Folder putFolder( @PathParam("folder-id") Long folderId , Folder folder ) throws Exception;

	@GET
	@Path("/folders/{folder-id}/folders")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Folder> getFolderFoldersPage( @PathParam("folder-id") Long folderId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/folders/{folder-id}/folders")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Folder postFolderFolder( @PathParam("folder-id") Long folderId , Folder folder ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/folders/{folder-id}/folders/batch-create")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Folder postFolderFolderBatchCreate( @PathParam("folder-id") Long folderId , Folder folder ) throws Exception;

}