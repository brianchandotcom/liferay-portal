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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
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
import javax.ws.rs.POST;
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
public abstract class BaseKnowledgeBaseAttachmentResourceImpl
	implements KnowledgeBaseAttachmentResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path(
		"/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseAttachment")})
	public Page<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("multipart/form-data")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path(
		"/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseAttachment")})
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId,
				MultipartBody multipartBody)
		throws Exception {

		return new KnowledgeBaseAttachment();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "knowledgeBaseAttachmentId"
			)
		}
	)
	@Path("/knowledge-base-attachments/{knowledgeBaseAttachmentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseAttachment")})
	public void deleteKnowledgeBaseAttachment(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseAttachmentId") Long
				knowledgeBaseAttachmentId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "knowledgeBaseAttachmentId"
			)
		}
	)
	@Path("/knowledge-base-attachments/{knowledgeBaseAttachmentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseAttachment")})
	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseAttachmentId") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		return new KnowledgeBaseAttachment();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		KnowledgeBaseAttachment knowledgeBaseAttachment,
		KnowledgeBaseAttachment existingKnowledgeBaseAttachment) {
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