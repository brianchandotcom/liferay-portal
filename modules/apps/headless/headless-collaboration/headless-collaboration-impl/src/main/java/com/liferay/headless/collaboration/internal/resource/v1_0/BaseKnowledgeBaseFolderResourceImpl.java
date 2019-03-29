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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

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
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseKnowledgeBaseFolderResourceImpl
	implements KnowledgeBaseFolderResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/content-spaces/{content-space-id}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder> getContentSpaceKnowledgeBaseFoldersPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postContentSpaceKnowledgeBaseFolder(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@DELETE
	@Path("/knowledge-base-folders/{knowledge-base-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public void deleteKnowledgeBaseFolder(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/knowledge-base-folders/{knowledge-base-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/knowledge-base-folders/{knowledge-base-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		preparePatch(knowledgeBaseFolder);

		KnowledgeBaseFolder existingKnowledgeBaseFolder =
			getKnowledgeBaseFolder(knowledgeBaseFolderId);

		if (Validator.isNotNull(knowledgeBaseFolder.getDateCreated())) {
			existingKnowledgeBaseFolder.setDateCreated(
				knowledgeBaseFolder.getDateCreated());
		}

		if (Validator.isNotNull(knowledgeBaseFolder.getDateModified())) {
			existingKnowledgeBaseFolder.setDateModified(
				knowledgeBaseFolder.getDateModified());
		}

		if (Validator.isNotNull(knowledgeBaseFolder.getDescription())) {
			existingKnowledgeBaseFolder.setDescription(
				knowledgeBaseFolder.getDescription());
		}

		if (Validator.isNotNull(knowledgeBaseFolder.getName())) {
			existingKnowledgeBaseFolder.setName(knowledgeBaseFolder.getName());
		}

		if (Validator.isNotNull(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles())) {

			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		if (Validator.isNotNull(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders())) {

			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		if (Validator.isNotNull(
				knowledgeBaseFolder.getParentKnowledgeBaseFolderId())) {

			existingKnowledgeBaseFolder.setParentKnowledgeBaseFolderId(
				knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		return putKnowledgeBaseFolder(
			knowledgeBaseFolderId, existingKnowledgeBaseFolder);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/knowledge-base-folders/{knowledge-base-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledge-base-folder-id}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@NotNull @PathParam("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-folders/{knowledge-base-folder-id}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(KnowledgeBaseFolder knowledgeBaseFolder) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
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