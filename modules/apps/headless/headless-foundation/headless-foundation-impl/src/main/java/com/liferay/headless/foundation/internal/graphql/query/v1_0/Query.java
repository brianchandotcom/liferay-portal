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

import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.EmailImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.KeywordImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.OrganizationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.PhoneImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.PostalAddressImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.RoleImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.WebUrlImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.EmailResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.OrganizationResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.PhoneResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.PostalAddressResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.RoleResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.UserAccountResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.VocabularyResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.WebUrlResourceImpl;
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
	public PostalAddressImpl getAddress(
			@GraphQLName("address-id") Long addressId)
		throws Exception {

		PostalAddressResourceImpl postalAddressResourceImpl =
			_getPostalAddressResourceImpl();

		postalAddressResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (PostalAddressImpl)postalAddressResourceImpl.getAddress(
			addressId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CategoryImpl getCategory(@GraphQLName("category-id") Long categoryId)
		throws Exception {

		CategoryResourceImpl categoryResourceImpl = _getCategoryResourceImpl();

		categoryResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (CategoryImpl)categoryResourceImpl.getCategory(categoryId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CategoryImpl> getCategoryCategoriesPage(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CategoryResourceImpl categoryResourceImpl = _getCategoryResourceImpl();

		categoryResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = categoryResourceImpl.getCategoryCategoriesPage(
			categoryId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KeywordImpl> getContentSpaceKeywordsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		KeywordResourceImpl keywordResourceImpl = _getKeywordResourceImpl();

		keywordResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = keywordResourceImpl.getContentSpaceKeywordsPage(
			contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<VocabularyImpl> getContentSpaceVocabulariesPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		VocabularyResourceImpl vocabularyResourceImpl =
			_getVocabularyResourceImpl();

		vocabularyResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			vocabularyResourceImpl.getContentSpaceVocabulariesPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public EmailImpl getEmail(@GraphQLName("email-id") Long emailId)
		throws Exception {

		EmailResourceImpl emailResourceImpl = _getEmailResourceImpl();

		emailResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (EmailImpl)emailResourceImpl.getEmail(emailId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<EmailImpl> getGenericParentEmailsPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		EmailResourceImpl emailResourceImpl = _getEmailResourceImpl();

		emailResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = emailResourceImpl.getGenericParentEmailsPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PhoneImpl> getGenericParentPhonesPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		PhoneResourceImpl phoneResourceImpl = _getPhoneResourceImpl();

		phoneResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = phoneResourceImpl.getGenericParentPhonesPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PostalAddressImpl> getGenericParentPostalAddressesPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		PostalAddressResourceImpl postalAddressResourceImpl =
			_getPostalAddressResourceImpl();

		postalAddressResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			postalAddressResourceImpl.getGenericParentPostalAddressesPage(
				genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WebUrlImpl> getGenericParentWebUrlsPage(
			@GraphQLName("generic-parent-id") Object genericParentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		WebUrlResourceImpl webUrlResourceImpl = _getWebUrlResourceImpl();

		webUrlResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = webUrlResourceImpl.getGenericParentWebUrlsPage(
			genericParentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KeywordImpl getKeyword(@GraphQLName("keyword-id") Long keywordId)
		throws Exception {

		KeywordResourceImpl keywordResourceImpl = _getKeywordResourceImpl();

		keywordResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (KeywordImpl)keywordResourceImpl.getKeyword(keywordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccountImpl getMyUserAccount(
			@GraphQLName("my-user-account-id") Long myUserAccountId)
		throws Exception {

		UserAccountResourceImpl userAccountResourceImpl =
			_getUserAccountResourceImpl();

		userAccountResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (UserAccountImpl)userAccountResourceImpl.getMyUserAccount(
			myUserAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<OrganizationImpl> getMyUserAccountOrganizationsPage(
			@GraphQLName("my-user-account-id") Long myUserAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResourceImpl organizationResourceImpl =
			_getOrganizationResourceImpl();

		organizationResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResourceImpl.getMyUserAccountOrganizationsPage(
				myUserAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<RoleImpl> getMyUserAccountRolesPage(
			@GraphQLName("my-user-account-id") Long myUserAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResourceImpl roleResourceImpl = _getRoleResourceImpl();

		roleResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResourceImpl.getMyUserAccountRolesPage(
			myUserAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public OrganizationImpl getOrganization(
			@GraphQLName("organization-id") Long organizationId)
		throws Exception {

		OrganizationResourceImpl organizationResourceImpl =
			_getOrganizationResourceImpl();

		organizationResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (OrganizationImpl)organizationResourceImpl.getOrganization(
			organizationId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<OrganizationImpl> getOrganizationOrganizationsPage(
			@GraphQLName("organization-id") Long organizationId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResourceImpl organizationResourceImpl =
			_getOrganizationResourceImpl();

		organizationResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResourceImpl.getOrganizationOrganizationsPage(
				organizationId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<OrganizationImpl> getOrganizationsPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResourceImpl organizationResourceImpl =
			_getOrganizationResourceImpl();

		organizationResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = organizationResourceImpl.getOrganizationsPage(
			Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccountImpl> getOrganizationUserAccountsPage(
			@GraphQLName("organization-id") Long organizationId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResourceImpl userAccountResourceImpl =
			_getUserAccountResourceImpl();

		userAccountResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			userAccountResourceImpl.getOrganizationUserAccountsPage(
				organizationId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public PhoneImpl getPhone(@GraphQLName("phone-id") Long phoneId)
		throws Exception {

		PhoneResourceImpl phoneResourceImpl = _getPhoneResourceImpl();

		phoneResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (PhoneImpl)phoneResourceImpl.getPhone(phoneId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public RoleImpl getRole(@GraphQLName("role-id") Long roleId)
		throws Exception {

		RoleResourceImpl roleResourceImpl = _getRoleResourceImpl();

		roleResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (RoleImpl)roleResourceImpl.getRole(roleId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<RoleImpl> getRolesPage(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResourceImpl roleResourceImpl = _getRoleResourceImpl();

		roleResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResourceImpl.getRolesPage(
			Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccountImpl getUserAccount(
			@GraphQLName("user-account-id") Long userAccountId)
		throws Exception {

		UserAccountResourceImpl userAccountResourceImpl =
			_getUserAccountResourceImpl();

		userAccountResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (UserAccountImpl)userAccountResourceImpl.getUserAccount(
			userAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<OrganizationImpl> getUserAccountOrganizationsPage(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		OrganizationResourceImpl organizationResourceImpl =
			_getOrganizationResourceImpl();

		organizationResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			organizationResourceImpl.getUserAccountOrganizationsPage(
				userAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<RoleImpl> getUserAccountRolesPage(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		RoleResourceImpl roleResourceImpl = _getRoleResourceImpl();

		roleResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = roleResourceImpl.getUserAccountRolesPage(
			userAccountId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccountImpl> getUserAccountsPage(
			@GraphQLName("fullnamequery") String fullnamequery,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResourceImpl userAccountResourceImpl =
			_getUserAccountResourceImpl();

		userAccountResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = userAccountResourceImpl.getUserAccountsPage(
			fullnamequery, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public VocabularyImpl getVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId)
		throws Exception {

		VocabularyResourceImpl vocabularyResourceImpl =
			_getVocabularyResourceImpl();

		vocabularyResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (VocabularyImpl)vocabularyResourceImpl.getVocabulary(
			vocabularyId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CategoryImpl> getVocabularyCategoriesPage(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CategoryResourceImpl categoryResourceImpl = _getCategoryResourceImpl();

		categoryResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = categoryResourceImpl.getVocabularyCategoriesPage(
			vocabularyId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccountImpl> getWebSiteUserAccountsPage(
			@GraphQLName("web-site-id") Long webSiteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		UserAccountResourceImpl userAccountResourceImpl =
			_getUserAccountResourceImpl();

		userAccountResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			userAccountResourceImpl.getWebSiteUserAccountsPage(
				webSiteId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WebUrlImpl getWebUrl(@GraphQLName("web-url-id") Long webUrlId)
		throws Exception {

		WebUrlResourceImpl webUrlResourceImpl = _getWebUrlResourceImpl();

		webUrlResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (WebUrlImpl)webUrlResourceImpl.getWebUrl(webUrlId);
	}

	private static CategoryResourceImpl _getCategoryResourceImpl() {
		return new CategoryResourceImpl();
	}

	private static EmailResourceImpl _getEmailResourceImpl() {
		return new EmailResourceImpl();
	}

	private static KeywordResourceImpl _getKeywordResourceImpl() {
		return new KeywordResourceImpl();
	}

	private static OrganizationResourceImpl _getOrganizationResourceImpl() {
		return new OrganizationResourceImpl();
	}

	private static PhoneResourceImpl _getPhoneResourceImpl() {
		return new PhoneResourceImpl();
	}

	private static PostalAddressResourceImpl _getPostalAddressResourceImpl() {
		return new PostalAddressResourceImpl();
	}

	private static RoleResourceImpl _getRoleResourceImpl() {
		return new RoleResourceImpl();
	}

	private static UserAccountResourceImpl _getUserAccountResourceImpl() {
		return new UserAccountResourceImpl();
	}

	private static VocabularyResourceImpl _getVocabularyResourceImpl() {
		return new VocabularyResourceImpl();
	}

	private static WebUrlResourceImpl _getWebUrlResourceImpl() {
		return new WebUrlResourceImpl();
	}

}