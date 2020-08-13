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

package com.liferay.headless.admin.user.internal.graphql.mutation.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.headless.admin.user.resource.v1_0.SubscriptionResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setOrganizationResourceComponentServiceObjects(
		ComponentServiceObjects<OrganizationResource>
			organizationResourceComponentServiceObjects) {

		_organizationResourceComponentServiceObjects =
			organizationResourceComponentServiceObjects;
	}

	public static void setRoleResourceComponentServiceObjects(
		ComponentServiceObjects<RoleResource>
			roleResourceComponentServiceObjects) {

		_roleResourceComponentServiceObjects =
			roleResourceComponentServiceObjects;
	}

	public static void setSubscriptionResourceComponentServiceObjects(
		ComponentServiceObjects<SubscriptionResource>
			subscriptionResourceComponentServiceObjects) {

		_subscriptionResourceComponentServiceObjects =
			subscriptionResourceComponentServiceObjects;
	}

	public static void setUserAccountResourceComponentServiceObjects(
		ComponentServiceObjects<UserAccountResource>
			userAccountResourceComponentServiceObjects) {

		_userAccountResourceComponentServiceObjects =
			userAccountResourceComponentServiceObjects;
	}

	@GraphQLField(description = "Creates a new organization")
	public Organization createOrganization(
			@GraphQLName("organization") Organization organization)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.postOrganization(
				organization));
	}

	@GraphQLField
	public Response createOrganizationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.postOrganizationBatch(
				callbackURL, object));
	}

	@GraphQLField(description = "Deletes an organization.")
	public boolean deleteOrganization(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.deleteOrganization(
				organizationId));

		return true;
	}

	@GraphQLField
	public Response deleteOrganizationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource ->
				organizationResource.deleteOrganizationBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Updates the organization with the information sent in the request body. Fields not present in the request body are left unchanged."
	)
	public Organization patchOrganization(
			@GraphQLName("organizationId") String organizationId,
			@GraphQLName("organization") Organization organization)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.patchOrganization(
				organizationId, organization));
	}

	@GraphQLField(
		description = "Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	public Organization updateOrganization(
			@GraphQLName("organizationId") String organizationId,
			@GraphQLName("organization") Organization organization)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.putOrganization(
				organizationId, organization));
	}

	@GraphQLField
	public Response updateOrganizationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.putOrganizationBatch(
				callbackURL, object));
	}

	@GraphQLField(description = "Unassociates a role with a user account")
	public boolean deleteRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.deleteRoleUserAccountAssociation(
				roleId, userAccountId));

		return true;
	}

	@GraphQLField(description = "Associates a role with a user account")
	public boolean createRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.postRoleUserAccountAssociation(
				roleId, userAccountId));

		return true;
	}

	@GraphQLField(
		description = "Unassociates an organization role with a user account"
	)
	public boolean deleteOrganizationRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource ->
				roleResource.deleteOrganizationRoleUserAccountAssociation(
					roleId, userAccountId, organizationId));

		return true;
	}

	@GraphQLField(
		description = "Associates a organization role with a user account"
	)
	public boolean createOrganizationRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource ->
				roleResource.postOrganizationRoleUserAccountAssociation(
					roleId, userAccountId, organizationId));

		return true;
	}

	@GraphQLField(description = "Unassociates a site role with a user account")
	public boolean deleteSiteRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.deleteSiteRoleUserAccountAssociation(
				roleId, userAccountId, Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField(description = "Associates a site role with a user account")
	public boolean createSiteRoleUserAccountAssociation(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.postSiteRoleUserAccountAssociation(
				roleId, userAccountId, Long.valueOf(siteKey)));

		return true;
	}

	@GraphQLField
	public boolean deleteMyUserAccountSubscription(
			@GraphQLName("subscriptionId") Long subscriptionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_subscriptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			subscriptionResource ->
				subscriptionResource.deleteMyUserAccountSubscription(
					subscriptionId));

		return true;
	}

	@GraphQLField(description = "Creates a new user account")
	public UserAccount createUserAccount(
			@GraphQLName("userAccount") UserAccount userAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.postUserAccount(
				userAccount));
	}

	@GraphQLField
	public Response createUserAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.postUserAccountBatch(
				callbackURL, object));
	}

	@GraphQLField(description = "Deletes the user account")
	public boolean deleteUserAccount(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.deleteUserAccount(
				userAccountId));

		return true;
	}

	@GraphQLField
	public Response deleteUserAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.deleteUserAccountBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Updates the user account with information sent in the request body. Only the provided fields are updated."
	)
	public UserAccount patchUserAccount(
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("userAccount") UserAccount userAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.patchUserAccount(
				userAccountId, userAccount));
	}

	@GraphQLField(
		description = "Replaces the user account with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	public UserAccount updateUserAccount(
			@GraphQLName("userAccountId") Long userAccountId,
			@GraphQLName("userAccount") UserAccount userAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.putUserAccount(
				userAccountId, userAccount));
	}

	@GraphQLField
	public Response updateUserAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.putUserAccountBatch(
				callbackURL, object));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			OrganizationResource organizationResource)
		throws Exception {

		organizationResource.setContextAcceptLanguage(_acceptLanguage);
		organizationResource.setContextCompany(_company);
		organizationResource.setContextHttpServletRequest(_httpServletRequest);
		organizationResource.setContextHttpServletResponse(
			_httpServletResponse);
		organizationResource.setContextUriInfo(_uriInfo);
		organizationResource.setContextUser(_user);
		organizationResource.setGroupLocalService(_groupLocalService);
		organizationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(RoleResource roleResource)
		throws Exception {

		roleResource.setContextAcceptLanguage(_acceptLanguage);
		roleResource.setContextCompany(_company);
		roleResource.setContextHttpServletRequest(_httpServletRequest);
		roleResource.setContextHttpServletResponse(_httpServletResponse);
		roleResource.setContextUriInfo(_uriInfo);
		roleResource.setContextUser(_user);
		roleResource.setGroupLocalService(_groupLocalService);
		roleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SubscriptionResource subscriptionResource)
		throws Exception {

		subscriptionResource.setContextAcceptLanguage(_acceptLanguage);
		subscriptionResource.setContextCompany(_company);
		subscriptionResource.setContextHttpServletRequest(_httpServletRequest);
		subscriptionResource.setContextHttpServletResponse(
			_httpServletResponse);
		subscriptionResource.setContextUriInfo(_uriInfo);
		subscriptionResource.setContextUser(_user);
		subscriptionResource.setGroupLocalService(_groupLocalService);
		subscriptionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			UserAccountResource userAccountResource)
		throws Exception {

		userAccountResource.setContextAcceptLanguage(_acceptLanguage);
		userAccountResource.setContextCompany(_company);
		userAccountResource.setContextHttpServletRequest(_httpServletRequest);
		userAccountResource.setContextHttpServletResponse(_httpServletResponse);
		userAccountResource.setContextUriInfo(_uriInfo);
		userAccountResource.setContextUser(_user);
		userAccountResource.setGroupLocalService(_groupLocalService);
		userAccountResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<OrganizationResource>
		_organizationResourceComponentServiceObjects;
	private static ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;
	private static ComponentServiceObjects<SubscriptionResource>
		_subscriptionResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}