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
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.headless.foundation.resource.v1_0.EmailResource;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
import com.liferay.headless.foundation.resource.v1_0.PhoneResource;
import com.liferay.headless.foundation.resource.v1_0.PostalAddressResource;
import com.liferay.headless.foundation.resource.v1_0.RoleResource;
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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Category getCategory(
	@GraphQLName("category-id") Long categoryId)
			throws Exception {

				CategoryResource categoryResource = _getCategoryResource();

				categoryResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return categoryResource.getCategory(
					categoryId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Category> getCategoryCategoriesPage(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				CategoryResource categoryResource = _getCategoryResource();

				categoryResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = categoryResource.getCategoryCategoriesPage(
					categoryId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Category> getVocabularyCategoriesPage(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				CategoryResource categoryResource = _getCategoryResource();

				categoryResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = categoryResource.getVocabularyCategoriesPage(
					vocabularyId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Email> getGenericParentEmailsPage(
	@GraphQLName("generic-parent-id") Object genericParentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				EmailResource emailResource = _getEmailResource();

				emailResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = emailResource.getGenericParentEmailsPage(
					genericParentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Email getEmail(
	@GraphQLName("email-id") Long emailId)
			throws Exception {

				EmailResource emailResource = _getEmailResource();

				emailResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return emailResource.getEmail(
					emailId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> getContentSpaceKeywordsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				KeywordResource keywordResource = _getKeywordResource();

				keywordResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = keywordResource.getContentSpaceKeywordsPage(
					contentSpaceId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword getKeyword(
	@GraphQLName("keyword-id") Long keywordId)
			throws Exception {

				KeywordResource keywordResource = _getKeywordResource();

				keywordResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return keywordResource.getKeyword(
					keywordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getMyUserAccountOrganizationsPage(
	@GraphQLName("my-user-account-id") Long myUserAccountId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				OrganizationResource organizationResource = _getOrganizationResource();

				organizationResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = organizationResource.getMyUserAccountOrganizationsPage(
					myUserAccountId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationsPage(
	@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				OrganizationResource organizationResource = _getOrganizationResource();

				organizationResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = organizationResource.getOrganizationsPage(
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Organization getOrganization(
	@GraphQLName("organization-id") Long organizationId)
			throws Exception {

				OrganizationResource organizationResource = _getOrganizationResource();

				organizationResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return organizationResource.getOrganization(
					organizationId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getOrganizationOrganizationsPage(
	@GraphQLName("organization-id") Long organizationId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				OrganizationResource organizationResource = _getOrganizationResource();

				organizationResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = organizationResource.getOrganizationOrganizationsPage(
					organizationId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Organization> getUserAccountOrganizationsPage(
	@GraphQLName("user-account-id") Long userAccountId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				OrganizationResource organizationResource = _getOrganizationResource();

				organizationResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = organizationResource.getUserAccountOrganizationsPage(
					userAccountId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Phone> getGenericParentPhonesPage(
	@GraphQLName("generic-parent-id") Object genericParentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				PhoneResource phoneResource = _getPhoneResource();

				phoneResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = phoneResource.getGenericParentPhonesPage(
					genericParentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Phone getPhone(
	@GraphQLName("phone-id") Long phoneId)
			throws Exception {

				PhoneResource phoneResource = _getPhoneResource();

				phoneResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return phoneResource.getPhone(
					phoneId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<PostalAddress> getGenericParentPostalAddressesPage(
	@GraphQLName("generic-parent-id") Object genericParentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				PostalAddressResource postalAddressResource = _getPostalAddressResource();

				postalAddressResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = postalAddressResource.getGenericParentPostalAddressesPage(
					genericParentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public PostalAddress getAddress(
	@GraphQLName("address-id") Long addressId)
			throws Exception {

				PostalAddressResource postalAddressResource = _getPostalAddressResource();

				postalAddressResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return postalAddressResource.getAddress(
					addressId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getMyUserAccountRolesPage(
	@GraphQLName("my-user-account-id") Long myUserAccountId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				RoleResource roleResource = _getRoleResource();

				roleResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = roleResource.getMyUserAccountRolesPage(
					myUserAccountId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getRolesPage(
	@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				RoleResource roleResource = _getRoleResource();

				roleResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = roleResource.getRolesPage(
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Role getRole(
	@GraphQLName("role-id") Long roleId)
			throws Exception {

				RoleResource roleResource = _getRoleResource();

				roleResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return roleResource.getRole(
					roleId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Role> getUserAccountRolesPage(
	@GraphQLName("user-account-id") Long userAccountId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				RoleResource roleResource = _getRoleResource();

				roleResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = roleResource.getUserAccountRolesPage(
					userAccountId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getMyUserAccount(
	@GraphQLName("my-user-account-id") Long myUserAccountId)
			throws Exception {

				UserAccountResource userAccountResource = _getUserAccountResource();

				userAccountResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return userAccountResource.getMyUserAccount(
					myUserAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getOrganizationUserAccountsPage(
	@GraphQLName("organization-id") Long organizationId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				UserAccountResource userAccountResource = _getUserAccountResource();

				userAccountResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = userAccountResource.getOrganizationUserAccountsPage(
					organizationId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getUserAccountsPage(
	@GraphQLName("fullnamequery") String fullnamequery,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				UserAccountResource userAccountResource = _getUserAccountResource();

				userAccountResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = userAccountResource.getUserAccountsPage(
					fullnamequery,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount getUserAccount(
	@GraphQLName("user-account-id") Long userAccountId)
			throws Exception {

				UserAccountResource userAccountResource = _getUserAccountResource();

				userAccountResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return userAccountResource.getUserAccount(
					userAccountId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<UserAccount> getWebSiteUserAccountsPage(
	@GraphQLName("web-site-id") Long webSiteId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				UserAccountResource userAccountResource = _getUserAccountResource();

				userAccountResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = userAccountResource.getWebSiteUserAccountsPage(
					webSiteId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Vocabulary> getContentSpaceVocabulariesPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				VocabularyResource vocabularyResource = _getVocabularyResource();

				vocabularyResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = vocabularyResource.getContentSpaceVocabulariesPage(
					contentSpaceId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Vocabulary getVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId)
			throws Exception {

				VocabularyResource vocabularyResource = _getVocabularyResource();

				vocabularyResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return vocabularyResource.getVocabulary(
					vocabularyId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WebUrl> getGenericParentWebUrlsPage(
	@GraphQLName("generic-parent-id") Object genericParentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				WebUrlResource webUrlResource = _getWebUrlResource();

				webUrlResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = webUrlResource.getGenericParentWebUrlsPage(
					genericParentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WebUrl getWebUrl(
	@GraphQLName("web-url-id") Long webUrlId)
			throws Exception {

				WebUrlResource webUrlResource = _getWebUrlResource();

				webUrlResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return webUrlResource.getWebUrl(
					webUrlId);
	}

	private static CategoryResource _getCategoryResource() {
			return _categoryResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CategoryResource, CategoryResource>
			_categoryResourceServiceTracker;
	private static EmailResource _getEmailResource() {
			return _emailResourceServiceTracker.getService();
	}

	private static final ServiceTracker<EmailResource, EmailResource>
			_emailResourceServiceTracker;
	private static KeywordResource _getKeywordResource() {
			return _keywordResourceServiceTracker.getService();
	}

	private static final ServiceTracker<KeywordResource, KeywordResource>
			_keywordResourceServiceTracker;
	private static OrganizationResource _getOrganizationResource() {
			return _organizationResourceServiceTracker.getService();
	}

	private static final ServiceTracker<OrganizationResource, OrganizationResource>
			_organizationResourceServiceTracker;
	private static PhoneResource _getPhoneResource() {
			return _phoneResourceServiceTracker.getService();
	}

	private static final ServiceTracker<PhoneResource, PhoneResource>
			_phoneResourceServiceTracker;
	private static PostalAddressResource _getPostalAddressResource() {
			return _postalAddressResourceServiceTracker.getService();
	}

	private static final ServiceTracker<PostalAddressResource, PostalAddressResource>
			_postalAddressResourceServiceTracker;
	private static RoleResource _getRoleResource() {
			return _roleResourceServiceTracker.getService();
	}

	private static final ServiceTracker<RoleResource, RoleResource>
			_roleResourceServiceTracker;
	private static UserAccountResource _getUserAccountResource() {
			return _userAccountResourceServiceTracker.getService();
	}

	private static final ServiceTracker<UserAccountResource, UserAccountResource>
			_userAccountResourceServiceTracker;
	private static VocabularyResource _getVocabularyResource() {
			return _vocabularyResourceServiceTracker.getService();
	}

	private static final ServiceTracker<VocabularyResource, VocabularyResource>
			_vocabularyResourceServiceTracker;
	private static WebUrlResource _getWebUrlResource() {
			return _webUrlResourceServiceTracker.getService();
	}

	private static final ServiceTracker<WebUrlResource, WebUrlResource>
			_webUrlResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Query.class);

				ServiceTracker<CategoryResource, CategoryResource>
					categoryResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							CategoryResource.class, null);

				categoryResourceServiceTracker.open();

				_categoryResourceServiceTracker =
					categoryResourceServiceTracker;
				ServiceTracker<EmailResource, EmailResource>
					emailResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							EmailResource.class, null);

				emailResourceServiceTracker.open();

				_emailResourceServiceTracker =
					emailResourceServiceTracker;
				ServiceTracker<KeywordResource, KeywordResource>
					keywordResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							KeywordResource.class, null);

				keywordResourceServiceTracker.open();

				_keywordResourceServiceTracker =
					keywordResourceServiceTracker;
				ServiceTracker<OrganizationResource, OrganizationResource>
					organizationResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							OrganizationResource.class, null);

				organizationResourceServiceTracker.open();

				_organizationResourceServiceTracker =
					organizationResourceServiceTracker;
				ServiceTracker<PhoneResource, PhoneResource>
					phoneResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							PhoneResource.class, null);

				phoneResourceServiceTracker.open();

				_phoneResourceServiceTracker =
					phoneResourceServiceTracker;
				ServiceTracker<PostalAddressResource, PostalAddressResource>
					postalAddressResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							PostalAddressResource.class, null);

				postalAddressResourceServiceTracker.open();

				_postalAddressResourceServiceTracker =
					postalAddressResourceServiceTracker;
				ServiceTracker<RoleResource, RoleResource>
					roleResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							RoleResource.class, null);

				roleResourceServiceTracker.open();

				_roleResourceServiceTracker =
					roleResourceServiceTracker;
				ServiceTracker<UserAccountResource, UserAccountResource>
					userAccountResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							UserAccountResource.class, null);

				userAccountResourceServiceTracker.open();

				_userAccountResourceServiceTracker =
					userAccountResourceServiceTracker;
				ServiceTracker<VocabularyResource, VocabularyResource>
					vocabularyResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							VocabularyResource.class, null);

				vocabularyResourceServiceTracker.open();

				_vocabularyResourceServiceTracker =
					vocabularyResourceServiceTracker;
				ServiceTracker<WebUrlResource, WebUrlResource>
					webUrlResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							WebUrlResource.class, null);

				webUrlResourceServiceTracker.open();

				_webUrlResourceServiceTracker =
					webUrlResourceServiceTracker;
	}

}