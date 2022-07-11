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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.dto.v1_0.Site;
import com.liferay.headless.admin.user.dto.v1_0.Subscription;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.headless.admin.user.client', and version '4.0.29'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Headless Admin User", version = "v1.0")
)
@Path("/v1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		try {
			Class<? extends OpenAPIResource> clazz =
				_openAPIResource.getClass();

			clazz.getMethod(
				"getOpenAPI", long.class, Map.class, String.class,
				UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(
			_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(AccountResourceImpl.class, Account.class);
				put(AccountRoleResourceImpl.class, AccountRole.class);
				put(EmailAddressResourceImpl.class, EmailAddress.class);
				put(OrganizationResourceImpl.class, Organization.class);
				put(PhoneResourceImpl.class, Phone.class);
				put(PostalAddressResourceImpl.class, PostalAddress.class);
				put(RoleResourceImpl.class, Role.class);
				put(SegmentResourceImpl.class, Segment.class);
				put(SegmentUserResourceImpl.class, SegmentUser.class);
				put(SiteResourceImpl.class, Site.class);
				put(SubscriptionResourceImpl.class, Subscription.class);
				put(UserAccountResourceImpl.class, UserAccount.class);
				put(UserGroupResourceImpl.class, UserGroup.class);
				put(WebUrlResourceImpl.class, WebUrl.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}