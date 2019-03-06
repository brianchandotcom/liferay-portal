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

package com.liferay.headless.foundation.internal.graphql.query.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.dto.v1_0.Segment;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.headless.foundation.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.EmailResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.OrganizationResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.PhoneResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.PostalAddressResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.RoleResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.SegmentResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.UserAccountResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.VocabularyResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.WebUrlResourceImpl;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.headless.foundation.resource.v1_0.EmailResource;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
import com.liferay.headless.foundation.resource.v1_0.PhoneResource;
import com.liferay.headless.foundation.resource.v1_0.PostalAddressResource;
import com.liferay.headless.foundation.resource.v1_0.RoleResource;
import com.liferay.headless.foundation.resource.v1_0.SegmentResource;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.headless.foundation.resource.v1_0.WebUrlResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Category getCategory(@GraphQLName("category-id") Long categoryId)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return categoryResource.getCategory(categoryId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Category> getCategoryCategoriesPage(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = categoryResource.getCategoryCategoriesPage(
			categoryId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> getContentSpaceKeywordsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = keywordResource.getContentSpaceKeywordsPage(
			contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Vocabulary> getContentSpaceVocabulariesPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			vocabularyResource.getContentSpaceVocabulariesPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Email getEmail(@GraphQLName("email-id") Long emailId)
		throws Exception {

		EmailResource emailResource = _createEmailResource();

		emailResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return emailResource.getEmail(emailId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Email> getGenericParentEmailsPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		EmailResource emailResource = _createEmailResource();

		emailResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = emailResource.getGenericParentEmailsPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Phone> getGenericParentPhonesPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		PhoneResource phoneResource = _createPhoneResource();

		phoneResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = phoneResource.getGenericParentPhonesPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PostalAddress> getGenericParentPostalAddressesPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		PostalAddressResource postalAddressResource =
			_createPostalAddressResource();

		postalAddressResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			postalAddressResource.getGenericParentPostalAddressesPage(
				genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WebUrl> getGenericParentWebUrlsPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		WebUrlResource webUrlResource = _createWebUrlResource();

		webUrlResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = webUrlResource.getGenericParentWebUrlsPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword getKeyword(@GraphQLName("keyword-id") Long keywordId)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return keywordResource.getKeyword(keywordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getMyUserAccount(
			@GraphQLName("my-user-account-id") Long myUserAccountId)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return userAccountResource.getMyUserAccount(myUserAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getMyUserAccountOrganizationsPage(
			@GraphQLName("my-user-account-id") Long myUserAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResource organizationResource =
			_createOrganizationResource();

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResource.getMyUserAccountOrganizationsPage(
				myUserAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getMyUserAccountRolesPage(
			@GraphQLName("my-user-account-id") Long myUserAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResource roleResource = _createRoleResource();

		roleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResource.getMyUserAccountRolesPage(
			myUserAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Organization getOrganization(
			@GraphQLName("organization-id") Long organizationId)
		throws Exception {

		OrganizationResource organizationResource =
			_createOrganizationResource();

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return organizationResource.getOrganization(organizationId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationOrganizationsPage(
			@GraphQLName("organization-id") Long organizationId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResource organizationResource =
			_createOrganizationResource();

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResource.getOrganizationOrganizationsPage(
				organizationId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationsPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResource organizationResource =
			_createOrganizationResource();

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = organizationResource.getOrganizationsPage(
			Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getOrganizationUserAccountsPage(
			@GraphQLName("organization-id") Long organizationId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			userAccountResource.getOrganizationUserAccountsPage(
				organizationId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Phone getPhone(@GraphQLName("phone-id") Long phoneId)
		throws Exception {

		PhoneResource phoneResource = _createPhoneResource();

		phoneResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return phoneResource.getPhone(phoneId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public PostalAddress getPostalAddress(
			@GraphQLName("postal-address-id") Long postalAddressId)
		throws Exception {

		PostalAddressResource postalAddressResource =
			_createPostalAddressResource();

		postalAddressResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return postalAddressResource.getPostalAddress(postalAddressId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Role getRole(@GraphQLName("role-id") Long roleId) throws Exception {
		RoleResource roleResource = _createRoleResource();

		roleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return roleResource.getRole(roleId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getRolesPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResource roleResource = _createRoleResource();

		roleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResource.getRolesPage(
			Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getUserAccount(
			@GraphQLName("user-account-id") Long userAccountId)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return userAccountResource.getUserAccount(userAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getUserAccountOrganizationsPage(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResource organizationResource =
			_createOrganizationResource();

		organizationResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResource.getUserAccountOrganizationsPage(
				userAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getUserAccountRolesPage(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResource roleResource = _createRoleResource();

		roleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResource.getUserAccountRolesPage(
			userAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getUserAccountsPage(
			@GraphQLName("fullnamequery") String fullnamequery,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = userAccountResource.getUserAccountsPage(
			fullnamequery, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Segment> getUserSegmentsPage(
			@GraphQLName("user-id") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		SegmentResource segmentResource = _createSegmentResource();

		segmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = segmentResource.getUserSegmentsPage(
			userId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Vocabulary getVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return vocabularyResource.getVocabulary(vocabularyId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Category> getVocabularyCategoriesPage(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = categoryResource.getVocabularyCategoriesPage(
			vocabularyId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getWebSiteUserAccountsPage(
			@GraphQLName("web-site-id") Long webSiteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = userAccountResource.getWebSiteUserAccountsPage(
			webSiteId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WebUrl getWebUrl(@GraphQLName("web-url-id") Long webUrlId)
		throws Exception {

		WebUrlResource webUrlResource = _createWebUrlResource();

		webUrlResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return webUrlResource.getWebUrl(webUrlId);
	}

	private static CategoryResource _createCategoryResource() {
		return new CategoryResourceImpl();
	}

	private static EmailResource _createEmailResource() {
		return new EmailResourceImpl();
	}

	private static KeywordResource _createKeywordResource() {
		return new KeywordResourceImpl();
	}

	private static OrganizationResource _createOrganizationResource() {
		return new OrganizationResourceImpl();
	}

	private static PhoneResource _createPhoneResource() {
		return new PhoneResourceImpl();
	}

	private static PostalAddressResource _createPostalAddressResource() {
		return new PostalAddressResourceImpl();
	}

	private static RoleResource _createRoleResource() {
		return new RoleResourceImpl();
	}

	private static SegmentResource _createSegmentResource() {
		return new SegmentResourceImpl();
	}

	private static UserAccountResource _createUserAccountResource() {
		return new UserAccountResourceImpl();
	}

	private static VocabularyResource _createVocabularyResource() {
		return new VocabularyResourceImpl();
	}

	private static WebUrlResource _createWebUrlResource() {
		return new WebUrlResourceImpl();
	}

}