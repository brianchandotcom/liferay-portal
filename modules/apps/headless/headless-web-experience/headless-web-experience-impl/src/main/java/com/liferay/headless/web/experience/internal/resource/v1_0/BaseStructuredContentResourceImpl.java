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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseStructuredContentResourceImpl
	implements StructuredContentResource {

	public static final String ODATA_ENTITY_MODEL_NAME =
		"com_liferay_headless_web_experience_dto_v1_0_StructuredContentEntityModel";

	@Override
	public Response deleteStructuredContent(Long structuredContentsId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<StructuredContent> getContentSpaceContentStructureStructuredContentsPage(
			Long contentSpaceId, Long contentStructureId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public StructuredContent getStructuredContent(Long structuredContentsId)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public StructuredContent postContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public StructuredContent postContentSpaceStructuredContentsBatchCreate(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public StructuredContent putStructuredContent(
			Long structuredContentsId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	protected <T, R> List<R> transform(
		List<T> list, Function<T, R> transformFunction) {

		return TransformUtil.transform(list, transformFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}