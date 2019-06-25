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

package com.liferay.headless.admin.user.internal.graphql.query.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.user.resource.v1_0.WebUrlResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setEmailAddressResourceComponentServiceObjects(
		ComponentServiceObjects<EmailAddressResource>
			emailAddressResourceComponentServiceObjects) {

		_emailAddressResourceComponentServiceObjects =
			emailAddressResourceComponentServiceObjects;
	}

	public static void setOrganizationResourceComponentServiceObjects(
		ComponentServiceObjects<OrganizationResource>
			organizationResourceComponentServiceObjects) {

		_organizationResourceComponentServiceObjects =
			organizationResourceComponentServiceObjects;
	}

	public static void setPhoneResourceComponentServiceObjects(
		ComponentServiceObjects<PhoneResource>
			phoneResourceComponentServiceObjects) {

		_phoneResourceComponentServiceObjects =
			phoneResourceComponentServiceObjects;
	}

	public static void setPostalAddressResourceComponentServiceObjects(
		ComponentServiceObjects<PostalAddressResource>
			postalAddressResourceComponentServiceObjects) {

		_postalAddressResourceComponentServiceObjects =
			postalAddressResourceComponentServiceObjects;
	}

	public static void setRoleResourceComponentServiceObjects(
		ComponentServiceObjects<RoleResource>
			roleResourceComponentServiceObjects) {

		_roleResourceComponentServiceObjects =
			roleResourceComponentServiceObjects;
	}

	public static void setSegmentResourceComponentServiceObjects(
		ComponentServiceObjects<SegmentResource>
			segmentResourceComponentServiceObjects) {

		_segmentResourceComponentServiceObjects =
			segmentResourceComponentServiceObjects;
	}

	public static void setSegmentUserResourceComponentServiceObjects(
		ComponentServiceObjects<SegmentUserResource>
			segmentUserResourceComponentServiceObjects) {

		_segmentUserResourceComponentServiceObjects =
			segmentUserResourceComponentServiceObjects;
	}

	public static void setUserAccountResourceComponentServiceObjects(
		ComponentServiceObjects<UserAccountResource>
			userAccountResourceComponentServiceObjects) {

		_userAccountResourceComponentServiceObjects =
			userAccountResourceComponentServiceObjects;
	}

	public static void setWebUrlResourceComponentServiceObjects(
		ComponentServiceObjects<WebUrlResource>
			webUrlResourceComponentServiceObjects) {

		_webUrlResourceComponentServiceObjects =
			webUrlResourceComponentServiceObjects;
	}

	@GraphQLField
	public EmailAddress getEmailAddress(
			@GraphQLName("emailAddressId") Long emailAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> emailAddressResource.getEmailAddress(
				emailAddressId));
	}

	@GraphQLField
	public java.util.Collection<EmailAddress> getOrganizationEmailAddressesPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> {
				Page paginationPage =
					emailAddressResource.getOrganizationEmailAddressesPage(
						organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<EmailAddress> getUserAccountEmailAddressesPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> {
				Page paginationPage =
					emailAddressResource.getUserAccountEmailAddressesPage(
						userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Organization> getOrganizationsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> {
				Page paginationPage = organizationResource.getOrganizationsPage(
					search, filter, Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Organization getOrganization(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.getOrganization(
				organizationId));
	}

	@GraphQLField
	public java.util.Collection<Organization> getOrganizationOrganizationsPage(
			@GraphQLName("parentOrganizationId") Long parentOrganizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> {
				Page paginationPage =
					organizationResource.getOrganizationOrganizationsPage(
						parentOrganizationId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Phone> getOrganizationPhonesPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> {
				Page paginationPage = phoneResource.getOrganizationPhonesPage(
					organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Phone getPhone(@GraphQLName("phoneId") Long phoneId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> phoneResource.getPhone(phoneId));
	}

	@GraphQLField
	public java.util.Collection<Phone> getUserAccountPhonesPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> {
				Page paginationPage = phoneResource.getUserAccountPhonesPage(
					userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<PostalAddress>
			getOrganizationPostalAddressesPage(
				@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> {
				Page paginationPage =
					postalAddressResource.getOrganizationPostalAddressesPage(
						organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public PostalAddress getPostalAddress(
			@GraphQLName("postalAddressId") Long postalAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> postalAddressResource.getPostalAddress(
				postalAddressId));
	}

	@GraphQLField
	public java.util.Collection<PostalAddress>
			getUserAccountPostalAddressesPage(
				@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> {
				Page paginationPage =
					postalAddressResource.getUserAccountPostalAddressesPage(
						userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Role> getRolesPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> {
				Page paginationPage = roleResource.getRolesPage(
					Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Role getRole(@GraphQLName("roleId") Long roleId) throws Exception {
		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.getRole(roleId));
	}

	@GraphQLField
	public java.util.Collection<Segment> getSiteSegmentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> {
				Page paginationPage = segmentResource.getSiteSegmentsPage(
					siteId, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Segment> getSiteUserAccountSegmentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> {
				Page paginationPage =
					segmentResource.getSiteUserAccountSegmentsPage(
						siteId, userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<SegmentUser> getSegmentUserAccountsPage(
			@GraphQLName("segmentId") Long segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentUserResource -> {
				Page paginationPage =
					segmentUserResource.getSegmentUserAccountsPage(
						segmentId, Pagination.of(page, pageSize));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public UserAccount getMyUserAccount() throws Exception {
		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.getMyUserAccount());
	}

	@GraphQLField
	public java.util.Collection<UserAccount> getOrganizationUserAccountsPage(
			@GraphQLName("organizationId") Long organizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> {
				Page paginationPage =
					userAccountResource.getOrganizationUserAccountsPage(
						organizationId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<UserAccount> getUserAccountsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> {
				Page paginationPage = userAccountResource.getUserAccountsPage(
					search, filter, Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public UserAccount getUserAccount(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.getUserAccount(
				userAccountId));
	}

	@GraphQLField
	public java.util.Collection<UserAccount> getWebSiteUserAccountsPage(
			@GraphQLName("webSiteId") Long webSiteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> {
				Page paginationPage =
					userAccountResource.getWebSiteUserAccountsPage(
						webSiteId, search, filter,
						Pagination.of(page, pageSize), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<WebUrl> getOrganizationWebUrlsPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> {
				Page paginationPage = webUrlResource.getOrganizationWebUrlsPage(
					organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<WebUrl> getUserAccountWebUrlsPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> {
				Page paginationPage = webUrlResource.getUserAccountWebUrlsPage(
					userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public WebUrl getWebUrl(@GraphQLName("webUrlId") Long webUrlId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> webUrlResource.getWebUrl(webUrlId));
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

	private void _populateResourceContext(
			EmailAddressResource emailAddressResource)
		throws Exception {

		emailAddressResource.setContextAcceptLanguage(_acceptLanguage);
		emailAddressResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			OrganizationResource organizationResource)
		throws Exception {

		organizationResource.setContextAcceptLanguage(_acceptLanguage);
		organizationResource.setContextCompany(_company);
	}

	private void _populateResourceContext(PhoneResource phoneResource)
		throws Exception {

		phoneResource.setContextAcceptLanguage(_acceptLanguage);
		phoneResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			PostalAddressResource postalAddressResource)
		throws Exception {

		postalAddressResource.setContextAcceptLanguage(_acceptLanguage);
		postalAddressResource.setContextCompany(_company);
	}

	private void _populateResourceContext(RoleResource roleResource)
		throws Exception {

		roleResource.setContextAcceptLanguage(_acceptLanguage);
		roleResource.setContextCompany(_company);
	}

	private void _populateResourceContext(SegmentResource segmentResource)
		throws Exception {

		segmentResource.setContextAcceptLanguage(_acceptLanguage);
		segmentResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			SegmentUserResource segmentUserResource)
		throws Exception {

		segmentUserResource.setContextAcceptLanguage(_acceptLanguage);
		segmentUserResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			UserAccountResource userAccountResource)
		throws Exception {

		userAccountResource.setContextAcceptLanguage(_acceptLanguage);
		userAccountResource.setContextCompany(_company);
	}

	private void _populateResourceContext(WebUrlResource webUrlResource)
		throws Exception {

		webUrlResource.setContextAcceptLanguage(_acceptLanguage);
		webUrlResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<EmailAddressResource>
		_emailAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrganizationResource>
		_organizationResourceComponentServiceObjects;
	private static ComponentServiceObjects<PhoneResource>
		_phoneResourceComponentServiceObjects;
	private static ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;
	private static ComponentServiceObjects<SegmentResource>
		_segmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<SegmentUserResource>
		_segmentUserResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;
	private static ComponentServiceObjects<WebUrlResource>
		_webUrlResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;

}