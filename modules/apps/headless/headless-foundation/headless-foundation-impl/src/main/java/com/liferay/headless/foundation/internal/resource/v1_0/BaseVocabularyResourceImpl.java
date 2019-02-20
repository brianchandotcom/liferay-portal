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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseVocabularyResourceImpl implements VocabularyResource {

	@Override
	@GET
	@Path("/content-spaces/{content-space-id}/vocabularies")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Vocabulary> getContentSpaceVocabulariesPage(
	@PathParam("content-space-id") Long contentSpaceId,@Context Filter filter,@Context Pagination pagination,@Context Sort[] sorts)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/vocabularies")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Vocabulary postContentSpaceVocabulary(
	@PathParam("content-space-id") Long contentSpaceId,Vocabulary vocabulary)
			throws Exception {

				return new VocabularyImpl();
	}
	@Override
	@DELETE
	@Path("/vocabularies/{vocabulary-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteVocabulary(
	@PathParam("vocabulary-id") Long vocabularyId)
			throws Exception {

				return false;
	}
	@Override
	@GET
	@Path("/vocabularies/{vocabulary-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Vocabulary getVocabulary(
	@PathParam("vocabulary-id") Long vocabularyId)
			throws Exception {

				return new VocabularyImpl();
	}
	@Override
	@Consumes("application/json")
	@PUT
	@Path("/vocabularies/{vocabulary-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Vocabulary putVocabulary(
	@PathParam("vocabulary-id") Long vocabularyId,Vocabulary vocabulary)
			throws Exception {

				return new VocabularyImpl();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

}