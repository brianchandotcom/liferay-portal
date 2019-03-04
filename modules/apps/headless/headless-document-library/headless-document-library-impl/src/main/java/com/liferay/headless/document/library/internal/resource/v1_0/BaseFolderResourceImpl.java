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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseFolderResourceImpl implements FolderResource {

	@DELETE
	@Override
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	public boolean deleteFolder(@PathParam("folder-id") Long folderId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/content-spaces/{content-space-id}/folders")
	@Produces("application/json")
	public Page<Folder> getContentSpaceFoldersPage(
			@PathParam("content-space-id") Long contentSpaceId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	public Folder getFolder(@PathParam("folder-id") Long folderId)
		throws Exception {

		return new Folder();
	}

	@GET
	@Override
	@Path("/folders/{folder-id}/folders")
	@Produces("application/json")
	public Page<Folder> getFolderFoldersPage(
			@PathParam("folder-id") Long folderId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@PATCH
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	public Folder patchFolder(
			@PathParam("folder-id") Long folderId, Folder folder)
		throws Exception {

		preparePatch(folder);

		Folder existingFolder = getFolder(folderId);

		if (Validator.isNotNull(folder.getContentSpaceId())) {
			existingFolder.setContentSpaceId(folder.getContentSpaceId());
		}

		if (Validator.isNotNull(folder.getDateCreated())) {
			existingFolder.setDateCreated(folder.getDateCreated());
		}

		if (Validator.isNotNull(folder.getDateModified())) {
			existingFolder.setDateModified(folder.getDateModified());
		}

		if (Validator.isNotNull(folder.getDescription())) {
			existingFolder.setDescription(folder.getDescription());
		}

		if (Validator.isNotNull(folder.getHasDocuments())) {
			existingFolder.setHasDocuments(folder.getHasDocuments());
		}

		if (Validator.isNotNull(folder.getHasFolders())) {
			existingFolder.setHasFolders(folder.getHasFolders());
		}

		if (Validator.isNotNull(folder.getName())) {
			existingFolder.setName(folder.getName());
		}

		if (Validator.isNotNull(folder.getViewableBy())) {
			existingFolder.setViewableBy(folder.getViewableBy());
		}

		return putFolder(folderId, existingFolder);
	}

	@Consumes("application/json")
	@Override
	@Path("/content-spaces/{content-space-id}/folders")
	@POST
	@Produces("application/json")
	public Folder postContentSpaceFolder(
			@PathParam("content-space-id") Long contentSpaceId, Folder folder)
		throws Exception {

		return new Folder();
	}

	@Consumes("application/json")
	@Override
	@Path("/folders/{folder-id}/folders")
	@POST
	@Produces("application/json")
	public Folder postFolderFolder(
			@PathParam("folder-id") Long folderId, Folder folder)
		throws Exception {

		return new Folder();
	}

	@Consumes("application/json")
	@Override
	@Path("/folders/{folder-id}")
	@Produces("application/json")
	@PUT
	public Folder putFolder(
			@PathParam("folder-id") Long folderId, Folder folder)
		throws Exception {

		return new Folder();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			BaseFolderResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseFolderResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(Folder folder) {
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transformToArray(list, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}