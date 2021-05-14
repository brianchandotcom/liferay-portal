/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.web.internal.organization.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.AccountList;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.Organization;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.UserList;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceOrganizationResource.class)
public class CommerceOrganizationResource {

	@GET
	@Path("/organizations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganization(
		@PathParam("id") long organizationId,
		@Context HttpServletRequest httpServletRequest) {

		Organization organization = null;

		com.liferay.portal.kernel.model.Organization curOrganization = null;

		try {
			long companyId = _portal.getCompanyId(httpServletRequest);

			if (organizationId >
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				curOrganization = _organizationService.getOrganization(
					organizationId);

				companyId = curOrganization.getCompanyId();
			}

			organization = _commerceOrganizationResourceUtil.getOrganization(
				_portal.getUserId(httpServletRequest), companyId,
				curOrganization);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			organization = new Organization(
				StringUtil.split(exception.getLocalizedMessage()));
		}

		return getResponse(organization);
	}

	@GET
	@Path("/organizations/{id}/accounts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizationAccounts(
		@PathParam("id") long organizationId,
		@Context HttpServletRequest httpServletRequest,
		@Context Pagination pagination) {

		AccountList accountList = null;

		try {
			accountList = _commerceOrganizationResourceUtil.getAccountList(
				organizationId, pagination);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			accountList = new AccountList(
				StringUtil.split(exception.getLocalizedMessage()));
		}

		return getResponse(accountList);
	}

	@GET
	@Path("/organizations/{id}/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizationUsers(
		@PathParam("id") long organizationId,
		@Context HttpServletRequest httpServletRequest,
		@Context Pagination pagination) {

		UserList userList = null;

		try {
			userList = _commerceOrganizationResourceUtil.getUserList(
				organizationId, pagination);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			userList = new UserList(
				StringUtil.split(exception.getLocalizedMessage()));
		}

		return getResponse(userList);
	}

	protected Response getResponse(Object object) {
		if (object == null) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		try {
			String json = ObjectMapperHolder._objectMapper.writeValueAsString(
				object);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (JsonProcessingException jsonProcessingException) {
			_log.error(jsonProcessingException, jsonProcessingException);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrganizationResource.class);

	@Reference
	private CommerceOrganizationResourceUtil _commerceOrganizationResourceUtil;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	private static class ObjectMapperHolder {

		private static final ObjectMapper _objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				disable(SerializationFeature.INDENT_OUTPUT);
			}
		};

	}

}