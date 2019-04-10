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

package com.liferay.headless.foundation.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface PostalAddressResource {

	@GET
	@Path("/organizations/{organizationId}/postal-addresses")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "PostalAddress")})
	public Page<PostalAddress> getOrganizationPostalAddressesPage(
			@NotNull @PathParam("organizationId") Long organizationId)
		throws Exception;

	@GET
	@Path("/postal-addresses/{postalAddressId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "PostalAddress")})
	public PostalAddress getPostalAddress(
			@NotNull @PathParam("postalAddressId") Long postalAddressId)
		throws Exception;

	@GET
	@Path("/user-accounts/{userAccountId}/postal-addresses")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "PostalAddress")})
	public Page<PostalAddress> getUserAccountPostalAddressesPage(
			@NotNull @PathParam("userAccountId") Long userAccountId)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}