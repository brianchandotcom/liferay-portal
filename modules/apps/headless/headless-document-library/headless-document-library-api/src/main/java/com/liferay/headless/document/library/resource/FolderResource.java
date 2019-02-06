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

package com.liferay.headless.document.library.resource;

import com.liferay.headless.document.library.dto.Comment;
import com.liferay.headless.document.library.dto.Creator;
import com.liferay.headless.document.library.dto.Document;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-document-library/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface FolderResource {

	@GET
	@Path("/documents-repository/{id}")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Folder getDocumentsRepository( @PathParam("id") Long id ) throws Exception;

	@GET
	@Path("/documents-repository/{documents-repository-id}/folder")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Page<Folder> getDocumentsRepositoryFolderPage( @PathParam("documents-repository-id") Long documentsRepositoryId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents-repository/{documents-repository-id}/folder")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Folder postDocumentsRepositoryFolder( @PathParam("documents-repository-id") Long documentsRepositoryId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents-repository/{documents-repository-id}/folder/batch-create")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.write")
	public Folder postDocumentsRepositoryFolderBatchCreate( @PathParam("documents-repository-id") Long documentsRepositoryId ) throws Exception;

	@DELETE
	@Path("/folder/{folder-id}")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Response deleteFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@GET
	@Path("/folder/{folder-id}")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Folder getFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/folder/{folder-id}")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Folder putFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@GET
	@Path("/folder/{folder-id}/folder")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Page<Folder> getFolderFolderPage( @PathParam("folder-id") Long folderId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/folder/{folder-id}/folder")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.read")
	public Folder postFolderFolder( @PathParam("folder-id") Long folderId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/folder/{folder-id}/folder/batch-create")
	@Produces("application/json")
	@RequiresScope("headless-document-library-application.write")
	public Folder postFolderFolderBatchCreate( @PathParam("folder-id") Long folderId ) throws Exception;
}