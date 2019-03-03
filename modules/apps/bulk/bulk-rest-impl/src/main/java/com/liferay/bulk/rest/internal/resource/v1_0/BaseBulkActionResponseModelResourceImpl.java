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

package com.liferay.bulk.rest.internal.resource.v1_0;

import com.liferay.bulk.rest.dto.v1_0.BulkActionResponseModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseModelResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
public abstract class BaseBulkActionResponseModelResourceImpl
	implements BulkActionResponseModelResource {

	@Consumes("application/json")
	@Override
	@Path("/categories/{category-class-name-id}")
	@POST
	@Produces("application/json")
	public BulkActionResponseModel postClassNameId(
			@PathParam("classNameId") Long classNameId,
			BulkAssetEntryUpdateCategoriesActionModel
				bulkAssetEntryUpdateCategoriesActionModel)
		throws Exception {

		return new BulkActionResponseModel();
	}

	@Consumes("application/json")
	@Override
	@Path("/tags/{tag-class-name-id}")
	@POST
	@Produces("application/json")
	public BulkActionResponseModel postClassNameId(
			@PathParam("classNameId") Long classNameId,
			BulkAssetEntryUpdateTagsActionModel
				bulkAssetEntryUpdateTagsActionModel)
		throws Exception {

		return new BulkActionResponseModel();
	}

	@Consumes("application/json")
	@Override
	@Path("/categories/{category-group-id}/{class-name-id}/common")
	@POST
	@Produces("application/json")
	public BulkAssetEntryCommonCategoriesModel postGroupIdClassNameId(
			@PathParam("groupId") Long groupId,
			@PathParam("classNameId") Long classNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		return new BulkAssetEntryCommonCategoriesModel();
	}

	@Consumes("application/json")
	@Override
	@Path("/tags/{tag-group-id}/{class-name-id}/common")
	@POST
	@Produces("application/json")
	public BulkAssetEntryCommonTagsModel postGroupIdClassNameId(
			@PathParam("groupId") Long groupId,
			@PathParam("classNameId") Long classNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		return new BulkAssetEntryCommonTagsModel();
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
			BaseBulkActionResponseModelResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseBulkActionResponseModelResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(
		BulkActionResponseModel bulkActionResponseModel) {
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