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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetMyUserAccount() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetOrganizationUserAccountsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetUserAccountsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostUserAccount() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteUserAccount() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetUserAccount() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutUserAccount() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetWebSiteUserAccountsPage() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetMyUserAccount(
				Long myUserAccountId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/my-user-accounts/{my-user-account-id}",
					myUserAccountId
				);
	}
	protected Response invokeGetOrganizationUserAccountsPage(
				Long organizationId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/organizations/{organization-id}/user-accounts",
					organizationId
				);
	}
	protected Response invokeGetUserAccountsPage(
				String fullnamequery,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/user-accounts",
					fullnamequery
				);
	}
	protected Response invokePostUserAccount(
				UserAccount userAccount)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).post(
					_resourceURL + "/user-accounts",
					userAccount
				);
	}
	protected Response invokeDeleteUserAccount(
				Long userAccountId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).delete(
					_resourceURL + "/user-accounts/{user-account-id}",
					userAccountId
				);
	}
	protected Response invokeGetUserAccount(
				Long userAccountId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/user-accounts/{user-account-id}",
					userAccountId
				);
	}
	protected Response invokePutUserAccount(
				Long userAccountId,UserAccount userAccount)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.body(
					userAccount
				).when(
				).put(
					_resourceURL + "/user-accounts/{user-account-id}",
					userAccountId
				);
	}
	protected Response invokeGetWebSiteUserAccountsPage(
				Long webSiteId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/web-sites/{web-site-id}/user-accounts",
					webSiteId
				);
	}

	protected UserAccount randomUserAccount() {
		return new UserAccountImpl() {
			{

						additionalName = RandomTestUtil.randomString();
						alternateName = RandomTestUtil.randomString();
						birthDate = RandomTestUtil.nextDate();
						dashboardURL = RandomTestUtil.randomString();
						email = RandomTestUtil.randomString();
						familyName = RandomTestUtil.randomString();
						givenName = RandomTestUtil.randomString();
						honorificPrefix = RandomTestUtil.randomString();
						honorificSuffix = RandomTestUtil.randomString();
						id = RandomTestUtil.randomLong();
						image = RandomTestUtil.randomString();
						jobTitle = RandomTestUtil.randomString();
						name = RandomTestUtil.randomString();
						profileURL = RandomTestUtil.randomString();
	}
		};
	}

	protected Group testGroup;

	protected class UserAccountImpl implements UserAccount {

	public String getAdditionalName() {
				return additionalName;
	}

	public void setAdditionalName(String additionalName) {
				this.additionalName = additionalName;
	}

	@JsonIgnore
	public void setAdditionalName(
				UnsafeSupplier<String, Throwable> additionalNameUnsafeSupplier) {

				try {
					additionalName = additionalNameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String additionalName;
	public String getAlternateName() {
				return alternateName;
	}

	public void setAlternateName(String alternateName) {
				this.alternateName = alternateName;
	}

	@JsonIgnore
	public void setAlternateName(
				UnsafeSupplier<String, Throwable> alternateNameUnsafeSupplier) {

				try {
					alternateName = alternateNameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String alternateName;
	public Date getBirthDate() {
				return birthDate;
	}

	public void setBirthDate(Date birthDate) {
				this.birthDate = birthDate;
	}

	@JsonIgnore
	public void setBirthDate(
				UnsafeSupplier<Date, Throwable> birthDateUnsafeSupplier) {

				try {
					birthDate = birthDateUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date birthDate;
	public ContactInformation getContactInformation() {
				return contactInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
				this.contactInformation = contactInformation;
	}

	@JsonIgnore
	public void setContactInformation(
				UnsafeSupplier<ContactInformation, Throwable> contactInformationUnsafeSupplier) {

				try {
					contactInformation = contactInformationUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected ContactInformation contactInformation;
	public String getDashboardURL() {
				return dashboardURL;
	}

	public void setDashboardURL(String dashboardURL) {
				this.dashboardURL = dashboardURL;
	}

	@JsonIgnore
	public void setDashboardURL(
				UnsafeSupplier<String, Throwable> dashboardURLUnsafeSupplier) {

				try {
					dashboardURL = dashboardURLUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String dashboardURL;
	public String getEmail() {
				return email;
	}

	public void setEmail(String email) {
				this.email = email;
	}

	@JsonIgnore
	public void setEmail(
				UnsafeSupplier<String, Throwable> emailUnsafeSupplier) {

				try {
					email = emailUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String email;
	public String getFamilyName() {
				return familyName;
	}

	public void setFamilyName(String familyName) {
				this.familyName = familyName;
	}

	@JsonIgnore
	public void setFamilyName(
				UnsafeSupplier<String, Throwable> familyNameUnsafeSupplier) {

				try {
					familyName = familyNameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String familyName;
	public String getGivenName() {
				return givenName;
	}

	public void setGivenName(String givenName) {
				this.givenName = givenName;
	}

	@JsonIgnore
	public void setGivenName(
				UnsafeSupplier<String, Throwable> givenNameUnsafeSupplier) {

				try {
					givenName = givenNameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String givenName;
	public String getHonorificPrefix() {
				return honorificPrefix;
	}

	public void setHonorificPrefix(String honorificPrefix) {
				this.honorificPrefix = honorificPrefix;
	}

	@JsonIgnore
	public void setHonorificPrefix(
				UnsafeSupplier<String, Throwable> honorificPrefixUnsafeSupplier) {

				try {
					honorificPrefix = honorificPrefixUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String honorificPrefix;
	public String getHonorificSuffix() {
				return honorificSuffix;
	}

	public void setHonorificSuffix(String honorificSuffix) {
				this.honorificSuffix = honorificSuffix;
	}

	@JsonIgnore
	public void setHonorificSuffix(
				UnsafeSupplier<String, Throwable> honorificSuffixUnsafeSupplier) {

				try {
					honorificSuffix = honorificSuffixUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String honorificSuffix;
	public Long getId() {
				return id;
	}

	public void setId(Long id) {
				this.id = id;
	}

	@JsonIgnore
	public void setId(
				UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {

				try {
					id = idUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long id;
	public String getImage() {
				return image;
	}

	public void setImage(String image) {
				this.image = image;
	}

	@JsonIgnore
	public void setImage(
				UnsafeSupplier<String, Throwable> imageUnsafeSupplier) {

				try {
					image = imageUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String image;
	public String getJobTitle() {
				return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
				this.jobTitle = jobTitle;
	}

	@JsonIgnore
	public void setJobTitle(
				UnsafeSupplier<String, Throwable> jobTitleUnsafeSupplier) {

				try {
					jobTitle = jobTitleUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String jobTitle;
	public Organization[] getMyOrganizations() {
				return myOrganizations;
	}

	public void setMyOrganizations(Organization[] myOrganizations) {
				this.myOrganizations = myOrganizations;
	}

	@JsonIgnore
	public void setMyOrganizations(
				UnsafeSupplier<Organization[], Throwable> myOrganizationsUnsafeSupplier) {

				try {
					myOrganizations = myOrganizationsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Organization[] myOrganizations;
	public Long[] getMyOrganizationsIds() {
				return myOrganizationsIds;
	}

	public void setMyOrganizationsIds(Long[] myOrganizationsIds) {
				this.myOrganizationsIds = myOrganizationsIds;
	}

	@JsonIgnore
	public void setMyOrganizationsIds(
				UnsafeSupplier<Long[], Throwable> myOrganizationsIdsUnsafeSupplier) {

				try {
					myOrganizationsIds = myOrganizationsIdsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long[] myOrganizationsIds;
	public String getName() {
				return name;
	}

	public void setName(String name) {
				this.name = name;
	}

	@JsonIgnore
	public void setName(
				UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

				try {
					name = nameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String name;
	public String getProfileURL() {
				return profileURL;
	}

	public void setProfileURL(String profileURL) {
				this.profileURL = profileURL;
	}

	@JsonIgnore
	public void setProfileURL(
				UnsafeSupplier<String, Throwable> profileURLUnsafeSupplier) {

				try {
					profileURL = profileURLUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String profileURL;
	public Role[] getRoles() {
				return roles;
	}

	public void setRoles(Role[] roles) {
				this.roles = roles;
	}

	@JsonIgnore
	public void setRoles(
				UnsafeSupplier<Role[], Throwable> rolesUnsafeSupplier) {

				try {
					roles = rolesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Role[] roles;
	public Long[] getRolesIds() {
				return rolesIds;
	}

	public void setRolesIds(Long[] rolesIds) {
				this.rolesIds = rolesIds;
	}

	@JsonIgnore
	public void setRolesIds(
				UnsafeSupplier<Long[], Throwable> rolesIdsUnsafeSupplier) {

				try {
					rolesIds = rolesIdsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long[] rolesIds;
	public String[] getTasksAssignedToMe() {
				return tasksAssignedToMe;
	}

	public void setTasksAssignedToMe(String[] tasksAssignedToMe) {
				this.tasksAssignedToMe = tasksAssignedToMe;
	}

	@JsonIgnore
	public void setTasksAssignedToMe(
				UnsafeSupplier<String[], Throwable> tasksAssignedToMeUnsafeSupplier) {

				try {
					tasksAssignedToMe = tasksAssignedToMeUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String[] tasksAssignedToMe;
	public String[] getTasksAssignedToMyRoles() {
				return tasksAssignedToMyRoles;
	}

	public void setTasksAssignedToMyRoles(String[] tasksAssignedToMyRoles) {
				this.tasksAssignedToMyRoles = tasksAssignedToMyRoles;
	}

	@JsonIgnore
	public void setTasksAssignedToMyRoles(
				UnsafeSupplier<String[], Throwable> tasksAssignedToMyRolesUnsafeSupplier) {

				try {
					tasksAssignedToMyRoles = tasksAssignedToMyRolesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String[] tasksAssignedToMyRoles;

	}

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}