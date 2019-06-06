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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.function.Function;

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
	@GraphQLInvokeDetached
	public EmailAddress getEmailAddress(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("emailAddressId") Long emailAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			emailAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, emailAddressResource),
			emailAddressResource -> emailAddressResource.getEmailAddress(
				emailAddressId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<EmailAddress> getOrganizationEmailAddressesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			emailAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, emailAddressResource),
			emailAddressResource -> {
				Page paginationPage =
					emailAddressResource.getOrganizationEmailAddressesPage(
						organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<EmailAddress> getUserAccountEmailAddressesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			emailAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, emailAddressResource),
			emailAddressResource -> {
				Page paginationPage =
					emailAddressResource.getUserAccountEmailAddressesPage(
						userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			organizationResource -> _populateResourceContext(
				dataFetchingEnvironment, organizationResource),
			organizationResource -> {
				Page paginationPage = organizationResource.getOrganizationsPage(
					search, filter, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Organization getOrganization(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			organizationResource -> _populateResourceContext(
				dataFetchingEnvironment, organizationResource),
			organizationResource -> organizationResource.getOrganization(
				organizationId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationOrganizationsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentOrganizationId") Long parentOrganizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			organizationResource -> _populateResourceContext(
				dataFetchingEnvironment, organizationResource),
			organizationResource -> {
				Page paginationPage =
					organizationResource.getOrganizationOrganizationsPage(
						parentOrganizationId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Phone> getOrganizationPhonesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			phoneResource -> _populateResourceContext(
				dataFetchingEnvironment, phoneResource),
			phoneResource -> {
				Page paginationPage = phoneResource.getOrganizationPhonesPage(
					organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Phone getPhone(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("phoneId") Long phoneId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			phoneResource -> _populateResourceContext(
				dataFetchingEnvironment, phoneResource),
			phoneResource -> phoneResource.getPhone(phoneId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Phone> getUserAccountPhonesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			phoneResource -> _populateResourceContext(
				dataFetchingEnvironment, phoneResource),
			phoneResource -> {
				Page paginationPage = phoneResource.getUserAccountPhonesPage(
					userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PostalAddress> getOrganizationPostalAddressesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			postalAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, postalAddressResource),
			postalAddressResource -> {
				Page paginationPage =
					postalAddressResource.getOrganizationPostalAddressesPage(
						organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public PostalAddress getPostalAddress(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("postalAddressId") Long postalAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			postalAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, postalAddressResource),
			postalAddressResource -> postalAddressResource.getPostalAddress(
				postalAddressId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PostalAddress> getUserAccountPostalAddressesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			postalAddressResource -> _populateResourceContext(
				dataFetchingEnvironment, postalAddressResource),
			postalAddressResource -> {
				Page paginationPage =
					postalAddressResource.getUserAccountPostalAddressesPage(
						userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getRolesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			roleResource -> _populateResourceContext(
				dataFetchingEnvironment, roleResource),
			roleResource -> {
				Page paginationPage = roleResource.getRolesPage(
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Role getRole(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("roleId") Long roleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			roleResource -> _populateResourceContext(
				dataFetchingEnvironment, roleResource),
			roleResource -> roleResource.getRole(roleId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Segment> getSiteSegmentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			segmentResource -> _populateResourceContext(
				dataFetchingEnvironment, segmentResource),
			segmentResource -> {
				Page paginationPage = segmentResource.getSiteSegmentsPage(
					siteId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Segment> getSiteUserAccountSegmentsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			segmentResource -> _populateResourceContext(
				dataFetchingEnvironment, segmentResource),
			segmentResource -> {
				Page paginationPage =
					segmentResource.getSiteUserAccountSegmentsPage(
						siteId, userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<SegmentUser> getSegmentUserAccountsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("segmentId") Long segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects,
			segmentUserResource -> _populateResourceContext(
				dataFetchingEnvironment, segmentUserResource),
			segmentUserResource -> {
				Page paginationPage =
					segmentUserResource.getSegmentUserAccountsPage(
						segmentId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getMyUserAccount(
			DataFetchingEnvironment dataFetchingEnvironment)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			userAccountResource -> _populateResourceContext(
				dataFetchingEnvironment, userAccountResource),
			userAccountResource -> userAccountResource.getMyUserAccount());
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getOrganizationUserAccountsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			userAccountResource -> _populateResourceContext(
				dataFetchingEnvironment, userAccountResource),
			userAccountResource -> {
				Page paginationPage =
					userAccountResource.getOrganizationUserAccountsPage(
						organizationId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getUserAccountsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			userAccountResource -> _populateResourceContext(
				dataFetchingEnvironment, userAccountResource),
			userAccountResource -> {
				Page paginationPage = userAccountResource.getUserAccountsPage(
					search, filter, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getUserAccount(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			userAccountResource -> _populateResourceContext(
				dataFetchingEnvironment, userAccountResource),
			userAccountResource -> userAccountResource.getUserAccount(
				userAccountId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getWebSiteUserAccountsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("webSiteId") Long webSiteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			userAccountResource -> _populateResourceContext(
				dataFetchingEnvironment, userAccountResource),
			userAccountResource -> {
				Page paginationPage =
					userAccountResource.getWebSiteUserAccountsPage(
						webSiteId, search, filter,
						Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WebUrl> getOrganizationWebUrlsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			webUrlResource -> _populateResourceContext(
				dataFetchingEnvironment, webUrlResource),
			webUrlResource -> {
				Page paginationPage = webUrlResource.getOrganizationWebUrlsPage(
					organizationId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WebUrl> getUserAccountWebUrlsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			webUrlResource -> _populateResourceContext(
				dataFetchingEnvironment, webUrlResource),
			webUrlResource -> {
				Page paginationPage = webUrlResource.getUserAccountWebUrlsPage(
					userAccountId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WebUrl getWebUrl(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("webUrlId") Long webUrlId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			webUrlResource -> _populateResourceContext(
				dataFetchingEnvironment, webUrlResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			EmailAddressResource emailAddressResource)
		throws Exception {

		emailAddressResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		emailAddressResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			OrganizationResource organizationResource)
		throws Exception {

		organizationResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			PhoneResource phoneResource)
		throws Exception {

		phoneResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		phoneResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			PostalAddressResource postalAddressResource)
		throws Exception {

		postalAddressResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		postalAddressResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			RoleResource roleResource)
		throws Exception {

		roleResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		roleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			SegmentResource segmentResource)
		throws Exception {

		segmentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		segmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			SegmentUserResource segmentUserResource)
		throws Exception {

		segmentUserResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		segmentUserResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			UserAccountResource userAccountResource)
		throws Exception {

		userAccountResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			WebUrlResource webUrlResource)
		throws Exception {

		webUrlResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		webUrlResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {

		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;
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

}