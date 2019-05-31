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

package com.liferay.change.tracking.headless.internal.resource.v1_0;

import com.liferay.change.tracking.headless.dto.v1_0.Settings;
import com.liferay.change.tracking.headless.dto.v1_0.SettingsUpdate;
import com.liferay.change.tracking.headless.resource.v1_0.SettingsResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
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
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseSettingsResourceImpl implements SettingsResource {

	@Override
	@GET
	@Operation(
		description = "Retrieves a change tracking setting for a company or a specific user."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "companyId"),
			@Parameter(in = ParameterIn.QUERY, name = "userId")
		}
	)
	@Path("/settings")
	@Produces({"application/json", "application/xml", "text/plain"})
	@Tags(value = {@Tag(name = "Settings")})
	public Page<Settings> getSettingsPage(
			@NotNull @Parameter(hidden = true) @QueryParam("companyId") Long
				companyId,
			@Parameter(hidden = true) @QueryParam("userId") Long userId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates a change tracking setting for a company or a specific user."
	)
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "companyId"),
			@Parameter(in = ParameterIn.QUERY, name = "userId")
		}
	)
	@Path("/settings")
	@Produces({"application/json", "application/xml", "text/plain"})
	@Tags(value = {@Tag(name = "Settings")})
	public Settings putSettings(
			@NotNull @Parameter(hidden = true) @QueryParam("companyId") Long
				companyId,
			@Parameter(hidden = true) @QueryParam("userId") Long userId,
			SettingsUpdate settingsUpdate)
		throws Exception {

		return new Settings();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(Settings settings, Settings existingSettings) {
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