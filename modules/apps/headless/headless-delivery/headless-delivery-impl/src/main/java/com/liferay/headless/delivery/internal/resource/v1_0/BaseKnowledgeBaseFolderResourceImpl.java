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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
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
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public void deleteKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolder existingKnowledgeBaseFolder =
			getKnowledgeBaseFolder(knowledgeBaseFolderId);

		if (knowledgeBaseFolder.getDateCreated() != null) {
			existingKnowledgeBaseFolder.setDateCreated(
				knowledgeBaseFolder.getDateCreated());
		}

		if (knowledgeBaseFolder.getDateModified() != null) {
			existingKnowledgeBaseFolder.setDateModified(
				knowledgeBaseFolder.getDateModified());
		}

		if (knowledgeBaseFolder.getDescription() != null) {
			existingKnowledgeBaseFolder.setDescription(
				knowledgeBaseFolder.getDescription());
		}

		if (knowledgeBaseFolder.getName() != null) {
			existingKnowledgeBaseFolder.setName(knowledgeBaseFolder.getName());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() != null) {
			existingKnowledgeBaseFolder.setParentKnowledgeBaseFolderId(
				knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseFolder.getSiteId() != null) {
			existingKnowledgeBaseFolder.setSiteId(
				knowledgeBaseFolder.getSiteId());
		}

		if (knowledgeBaseFolder.getViewableBy() != null) {
			existingKnowledgeBaseFolder.setViewableBy(
				knowledgeBaseFolder.getViewableBy());
		}

		preparePatch(knowledgeBaseFolder, existingKnowledgeBaseFolder);

		return putKnowledgeBaseFolder(
			knowledgeBaseFolderId, existingKnowledgeBaseFolder);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseFolderId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentKnowledgeBaseFolderId") Long
					parentKnowledgeBaseFolderId,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseFolderId"
			)
		}
	)
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentKnowledgeBaseFolderId") Long
				parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		KnowledgeBaseFolder knowledgeBaseFolder,
		KnowledgeBaseFolder existingKnowledgeBaseFolder) {
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