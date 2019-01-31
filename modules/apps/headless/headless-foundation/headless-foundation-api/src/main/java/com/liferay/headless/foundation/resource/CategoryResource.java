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

package com.liferay.headless.foundation.resource;

import com.liferay.headless.foundation.dto.Category;
import com.liferay.headless.foundation.dto.Email;
import com.liferay.headless.foundation.dto.Keyword;
import com.liferay.headless.foundation.dto.Organization;
import com.liferay.headless.foundation.dto.Phone;
import com.liferay.headless.foundation.dto.PostalAddress;
import com.liferay.headless.foundation.dto.Role;
import com.liferay.headless.foundation.dto.UserAccount;
import com.liferay.headless.foundation.dto.Vocabulary;
import com.liferay.headless.foundation.dto.WebSite;
import com.liferay.headless.foundation.dto.WebUrl;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface CategoryResource {

	@GET
	@Path("/addresses")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<PostalAddress> getAddressesPage(
			@PathParam("genericparentid") Integer genericparentid,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/categories/{parent-id}/categories")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Category> getCategoriesCategoriesPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/content-space/{parent-id}/keywords")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Keyword> getContentSpaceKeywordsPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/content-space/{parent-id}/vocabularies")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Vocabulary> getContentSpaceVocabulariesPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/emails")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Email> getEmailsPage(
			@PathParam("genericparentid") Object genericparentid,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/my-user-account/{parent-id}/organization")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Organization> getMyUserAccountOrganizationPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/my-user-account")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getMyUserAccountPage(
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/my-user-account/{parent-id}/roles")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Role> getMyUserAccountRolesPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/my-user-account/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getMyUserAccountWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/organization/{parent-id}/organization")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Organization> getOrganizationOrganizationPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/organization")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Organization> getOrganizationPage(
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/organization/{parent-id}/user-account")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getOrganizationUserAccountPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/phones")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Phone> getPhonesPage(
			@PathParam("genericparentid") Object genericparentid,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/roles")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Role> getRolesPage(@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/user-account/{parent-id}/organization")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Organization> getUserAccountOrganizationPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/user-account")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getUserAccountPage(
			@QueryParam("fullnamequery") String fullnamequery,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/user-account/{parent-id}/roles")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Role> getUserAccountRolesPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/user-account/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getUserAccountWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/vocabularies/{parent-id}/categories")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<Category> getVocabulariesCategoriesPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/web-site/{parent-id}/user-account")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getWebSiteUserAccountPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/web-site/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getWebSiteWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/web-urls")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebUrl> getWebUrlsPage(
			@PathParam("genericparentid") Object genericparentid,
			@Context Pagination pagination)
		throws Exception;

	@Consumes({"*/*"})
	@Path("/categories/{parent-id}/categories/batch-create")
	@POST
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.write")
	public Category postCategoriesCategoriesBatchCreate(
			@PathParam("parent-id") Integer parentId)
		throws Exception;

	@Consumes({"*/*"})
	@Path("/vocabularies/{parent-id}/categories/batch-create")
	@POST
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.write")
	public Category postVocabulariesCategoriesBatchCreate(
			@PathParam("parent-id") Integer parentId)
		throws Exception;

}