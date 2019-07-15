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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

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

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getEmailAddress(emailAddressId:___, ){emailAddress, id, primary, type, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getEmailAddress(emailAddressId:___, ){emailAddress, id, primary, type, }}"}'
	 */
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

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationEmailAddressesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationEmailAddressesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public EmailAddressPage getOrganizationEmailAddressesPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> new EmailAddressPage(
				emailAddressResource.getOrganizationEmailAddressesPage(
					organizationId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccountEmailAddressesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccountEmailAddressesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public EmailAddressPage getUserAccountEmailAddressesPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> new EmailAddressPage(
				emailAddressResource.getUserAccountEmailAddressesPage(
					userAccountId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationsPage(search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationsPage(search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public OrganizationPage getOrganizationsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getOrganizationsPage(
					search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganization(organizationId:___, ){comment, contactInformation, customFields, dateCreated, dateModified, id, image, keywords, location, name, numberOfOrganizations, parentOrganization, services, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganization(organizationId:___, ){comment, contactInformation, customFields, dateCreated, dateModified, id, image, keywords, location, name, numberOfOrganizations, parentOrganization, services, }}"}'
	 */
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

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationOrganizationsPage(parentOrganizationId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationOrganizationsPage(parentOrganizationId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public OrganizationPage getOrganizationOrganizationsPage(
			@GraphQLName("parentOrganizationId") Long parentOrganizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationPhonesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationPhonesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public PhonePage getOrganizationPhonesPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> new PhonePage(
				phoneResource.getOrganizationPhonesPage(organizationId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getPhone(phoneId:___, ){extension, id, phoneNumber, phoneType, primary, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getPhone(phoneId:___, ){extension, id, phoneNumber, phoneType, primary, }}"}'
	 */
	@GraphQLField
	public Phone getPhone(@GraphQLName("phoneId") Long phoneId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> phoneResource.getPhone(phoneId));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccountPhonesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccountPhonesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public PhonePage getUserAccountPhonesPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> new PhonePage(
				phoneResource.getUserAccountPhonesPage(userAccountId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationPostalAddressesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationPostalAddressesPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public PostalAddressPage getOrganizationPostalAddressesPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> new PostalAddressPage(
				postalAddressResource.getOrganizationPostalAddressesPage(
					organizationId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getPostalAddress(postalAddressId:___, ){addressCountry, addressLocality, addressRegion, addressType, id, postalCode, primary, streetAddressLine1, streetAddressLine2, streetAddressLine3, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getPostalAddress(postalAddressId:___, ){addressCountry, addressLocality, addressRegion, addressType, id, postalCode, primary, streetAddressLine1, streetAddressLine2, streetAddressLine3, }}"}'
	 */
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

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccountPostalAddressesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccountPostalAddressesPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public PostalAddressPage getUserAccountPostalAddressesPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> new PostalAddressPage(
				postalAddressResource.getUserAccountPostalAddressesPage(
					userAccountId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getRolesPage(pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getRolesPage(pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public RolePage getRolesPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> new RolePage(
				roleResource.getRolesPage(Pagination.of(page, pageSize))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getRole(roleId:___, ){availableLanguages, creator, dateCreated, dateModified, description, id, name, roleType, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getRole(roleId:___, ){availableLanguages, creator, dateCreated, dateModified, description, id, name, roleType, }}"}'
	 */
	@GraphQLField
	public Role getRole(@GraphQLName("roleId") Long roleId) throws Exception {
		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.getRole(roleId));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getSiteSegmentsPage(siteId:___, pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getSiteSegmentsPage(siteId:___, pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public SegmentPage getSiteSegmentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> new SegmentPage(
				segmentResource.getSiteSegmentsPage(
					siteId, Pagination.of(page, pageSize))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getSiteUserAccountSegmentsPage(siteId:___, userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getSiteUserAccountSegmentsPage(siteId:___, userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public SegmentPage getSiteUserAccountSegmentsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> new SegmentPage(
				segmentResource.getSiteUserAccountSegmentsPage(
					siteId, userAccountId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getSegmentUserAccountsPage(segmentId:___, pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getSegmentUserAccountsPage(segmentId:___, pageSize:___, page:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public SegmentUserPage getSegmentUserAccountsPage(
			@GraphQLName("segmentId") Long segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentUserResource -> new SegmentUserPage(
				segmentUserResource.getSegmentUserAccountsPage(
					segmentId, Pagination.of(page, pageSize))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getMyUserAccount{additionalName, alternateName, birthDate, contactInformation, customFields, dashboardURL, dateCreated, dateModified, emailAddress, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, name, organizationBriefs, profileURL, roleBriefs, siteBriefs, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getMyUserAccount{additionalName, alternateName, birthDate, contactInformation, customFields, dashboardURL, dateCreated, dateModified, emailAddress, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, name, organizationBriefs, profileURL, roleBriefs, siteBriefs, }}"}'
	 */
	@GraphQLField
	public UserAccount getMyUserAccount() throws Exception {
		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.getMyUserAccount());
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationUserAccountsPage(organizationId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationUserAccountsPage(organizationId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public UserAccountPage getOrganizationUserAccountsPage(
			@GraphQLName("organizationId") Long organizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getOrganizationUserAccountsPage(
					organizationId, search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccountsPage(search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccountsPage(search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public UserAccountPage getUserAccountsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getUserAccountsPage(
					search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccount(userAccountId:___, ){additionalName, alternateName, birthDate, contactInformation, customFields, dashboardURL, dateCreated, dateModified, emailAddress, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, name, organizationBriefs, profileURL, roleBriefs, siteBriefs, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccount(userAccountId:___, ){additionalName, alternateName, birthDate, contactInformation, customFields, dashboardURL, dateCreated, dateModified, emailAddress, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, name, organizationBriefs, profileURL, roleBriefs, siteBriefs, }}"}'
	 */
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

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getWebSiteUserAccountsPage(webSiteId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getWebSiteUserAccountsPage(webSiteId:___, search:___, filter:___, pageSize:___, page:___, sorts:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public UserAccountPage getWebSiteUserAccountsPage(
			@GraphQLName("webSiteId") Long webSiteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getWebSiteUserAccountsPage(
					webSiteId, search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getOrganizationWebUrlsPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getOrganizationWebUrlsPage(organizationId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public WebUrlPage getOrganizationWebUrlsPage(
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> new WebUrlPage(
				webUrlResource.getOrganizationWebUrlsPage(organizationId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getUserAccountWebUrlsPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getUserAccountWebUrlsPage(userAccountId:___, ){page, pageSize, totalCount, items {__}}}"}'
	 */
	@GraphQLField
	public WebUrlPage getUserAccountWebUrlsPage(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> new WebUrlPage(
				webUrlResource.getUserAccountWebUrlsPage(userAccountId)));
	}

	/**
	 * Can be queried by doing a POST request to http://localhost:8080/o/graphql
	 * with a basic Authentication header and this body:
	 * {"query":"query {getWebUrl(webUrlId:___, ){id, url, urlType, }}"}
	 *
	 * or a CURL request like:
	 * curl -X 'POST' -H 'Content-Type: text/plain; charset=utf-8' 'http://localhost:8080/o/graphql' -u 'test@liferay.com:test' -d $'{"query":"query {getWebUrl(webUrlId:___, ){id, url, urlType, }}"}'
	 */
	@GraphQLField
	public WebUrl getWebUrl(@GraphQLName("webUrlId") Long webUrlId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> webUrlResource.getWebUrl(webUrlId));
	}

	@GraphQLName("EmailAddressPage")
	public class EmailAddressPage {

		public EmailAddressPage(Page emailAddressPage) {
			items = emailAddressPage.getItems();
			page = emailAddressPage.getPage();
			pageSize = emailAddressPage.getPageSize();
			totalCount = emailAddressPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<EmailAddress> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrganizationPage")
	public class OrganizationPage {

		public OrganizationPage(Page organizationPage) {
			items = organizationPage.getItems();
			page = organizationPage.getPage();
			pageSize = organizationPage.getPageSize();
			totalCount = organizationPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Organization> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PhonePage")
	public class PhonePage {

		public PhonePage(Page phonePage) {
			items = phonePage.getItems();
			page = phonePage.getPage();
			pageSize = phonePage.getPageSize();
			totalCount = phonePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Phone> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PostalAddressPage")
	public class PostalAddressPage {

		public PostalAddressPage(Page postalAddressPage) {
			items = postalAddressPage.getItems();
			page = postalAddressPage.getPage();
			pageSize = postalAddressPage.getPageSize();
			totalCount = postalAddressPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<PostalAddress> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("RolePage")
	public class RolePage {

		public RolePage(Page rolePage) {
			items = rolePage.getItems();
			page = rolePage.getPage();
			pageSize = rolePage.getPageSize();
			totalCount = rolePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Role> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SegmentPage")
	public class SegmentPage {

		public SegmentPage(Page segmentPage) {
			items = segmentPage.getItems();
			page = segmentPage.getPage();
			pageSize = segmentPage.getPageSize();
			totalCount = segmentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Segment> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SegmentUserPage")
	public class SegmentUserPage {

		public SegmentUserPage(Page segmentUserPage) {
			items = segmentUserPage.getItems();
			page = segmentUserPage.getPage();
			pageSize = segmentUserPage.getPageSize();
			totalCount = segmentUserPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<SegmentUser> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UserAccountPage")
	public class UserAccountPage {

		public UserAccountPage(Page userAccountPage) {
			items = userAccountPage.getItems();
			page = userAccountPage.getPage();
			pageSize = userAccountPage.getPageSize();
			totalCount = userAccountPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<UserAccount> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WebUrlPage")
	public class WebUrlPage {

		public WebUrlPage(Page webUrlPage) {
			items = webUrlPage.getItems();
			page = webUrlPage.getPage();
			pageSize = webUrlPage.getPageSize();
			totalCount = webUrlPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<WebUrl> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		emailAddressResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			OrganizationResource organizationResource)
		throws Exception {

		organizationResource.setContextAcceptLanguage(_acceptLanguage);
		organizationResource.setContextCompany(_company);
		organizationResource.setContextUser(_user);
	}

	private void _populateResourceContext(PhoneResource phoneResource)
		throws Exception {

		phoneResource.setContextAcceptLanguage(_acceptLanguage);
		phoneResource.setContextCompany(_company);
		phoneResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			PostalAddressResource postalAddressResource)
		throws Exception {

		postalAddressResource.setContextAcceptLanguage(_acceptLanguage);
		postalAddressResource.setContextCompany(_company);
		postalAddressResource.setContextUser(_user);
	}

	private void _populateResourceContext(RoleResource roleResource)
		throws Exception {

		roleResource.setContextAcceptLanguage(_acceptLanguage);
		roleResource.setContextCompany(_company);
		roleResource.setContextUser(_user);
	}

	private void _populateResourceContext(SegmentResource segmentResource)
		throws Exception {

		segmentResource.setContextAcceptLanguage(_acceptLanguage);
		segmentResource.setContextCompany(_company);
		segmentResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			SegmentUserResource segmentUserResource)
		throws Exception {

		segmentUserResource.setContextAcceptLanguage(_acceptLanguage);
		segmentUserResource.setContextCompany(_company);
		segmentUserResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			UserAccountResource userAccountResource)
		throws Exception {

		userAccountResource.setContextAcceptLanguage(_acceptLanguage);
		userAccountResource.setContextCompany(_company);
		userAccountResource.setContextUser(_user);
	}

	private void _populateResourceContext(WebUrlResource webUrlResource)
		throws Exception {

		webUrlResource.setContextAcceptLanguage(_acceptLanguage);
		webUrlResource.setContextCompany(_company);
		webUrlResource.setContextUser(_user);
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
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private User _user;

}