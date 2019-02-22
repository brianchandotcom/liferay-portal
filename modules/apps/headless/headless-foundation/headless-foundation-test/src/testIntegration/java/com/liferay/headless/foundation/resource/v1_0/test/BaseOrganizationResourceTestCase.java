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
import com.liferay.headless.foundation.dto.v1_0.Location;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Services;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.internal.dto.v1_0.ContactInformationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.LocationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.OrganizationImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ServicesImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountImpl;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseOrganizationResourceTestCase {

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
	public void testGetMyUserAccountOrganizationsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetOrganization() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetOrganizationOrganizationsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetOrganizationsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetUserAccountOrganizationsPage() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<Organization> invokeGetMyUserAccountOrganizationsPage(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/my-user-accounts/{my-user-account-id}/organizations",
					myUserAccountId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetMyUserAccountOrganizationsPageResponse(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/my-user-accounts/{my-user-account-id}/organizations",
					myUserAccountId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Organization invokeGetOrganization(Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/organizations/{organization-id}", organizationId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), OrganizationImpl.class);
	}

	protected Page<Organization> invokeGetOrganizationOrganizationsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/organizations/{organization-id}/organizations",
					organizationId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetOrganizationOrganizationsPageResponse(
			Long organizationId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/organizations/{organization-id}/organizations",
					organizationId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Http.Response invokeGetOrganizationResponse(Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/organizations/{organization-id}", organizationId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Organization> invokeGetOrganizationsPage(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/organizations", pagination));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetOrganizationsPageResponse(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/organizations", pagination));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Organization> invokeGetUserAccountOrganizationsPage(
			Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/user-accounts/{user-account-id}/organizations",
					userAccountId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetUserAccountOrganizationsPageResponse(
			Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/user-accounts/{user-account-id}/organizations",
					userAccountId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Organization randomOrganization() {
		return new OrganizationImpl() {
			{

				comment = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				logo = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				parentOrganizationId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Group testGroup;

	protected static class OrganizationImpl implements Organization {

		public String getComment() {
			return comment;
		}

		public ContactInformation getContactInformation() {
			return contactInformation;
		}

		public Long getId() {
			return id;
		}

		public Location getLocation() {
			return location;
		}

		public String getLogo() {
			return logo;
		}

		public UserAccount[] getMembers() {
			return members;
		}

		public Long[] getMembersIds() {
			return membersIds;
		}

		public String getName() {
			return name;
		}

		public Organization getParentOrganization() {
			return parentOrganization;
		}

		public Long getParentOrganizationId() {
			return parentOrganizationId;
		}

		public Services[] getServices() {
			return services;
		}

		public Organization[] getSubOrganization() {
			return subOrganization;
		}

		public Long[] getSubOrganizationIds() {
			return subOrganizationIds;
		}

		public void setComment(String comment) {
			this.comment = (String)comment;
		}

		@JsonIgnore
		public void setComment(
			UnsafeSupplier<String, Throwable> commentUnsafeSupplier) {

			try {
				comment = (String)commentUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setContactInformation(
			ContactInformation contactInformation) {

			this.contactInformation =
				(ContactInformationImpl)contactInformation;
		}

		@JsonIgnore
		public void setContactInformation(
			UnsafeSupplier<ContactInformation, Throwable>
				contactInformationUnsafeSupplier) {

			try {
				contactInformation =
					(ContactInformationImpl)
						contactInformationUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setId(Long id) {
			this.id = (Long)id;
		}

		@JsonIgnore
		public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
			try {
				id = (Long)idUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setLocation(Location location) {
			this.location = (LocationImpl)location;
		}

		@JsonIgnore
		public void setLocation(
			UnsafeSupplier<Location, Throwable> locationUnsafeSupplier) {

			try {
				location = (LocationImpl)locationUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setLogo(String logo) {
			this.logo = (String)logo;
		}

		@JsonIgnore
		public void setLogo(
			UnsafeSupplier<String, Throwable> logoUnsafeSupplier) {

			try {
				logo = (String)logoUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonIgnore
		public void setMembers(
			UnsafeSupplier<UserAccount[], Throwable> membersUnsafeSupplier) {

			try {
				members = (UserAccountImpl[])membersUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setMembers(UserAccount[] members) {
			this.members = (UserAccountImpl[])members;
		}

		public void setMembersIds(Long[] membersIds) {
			this.membersIds = (Long[])membersIds;
		}

		@JsonIgnore
		public void setMembersIds(
			UnsafeSupplier<Long[], Throwable> membersIdsUnsafeSupplier) {

			try {
				membersIds = (Long[])membersIdsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setName(String name) {
			this.name = (String)name;
		}

		@JsonIgnore
		public void setName(
			UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

			try {
				name = (String)nameUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setParentOrganization(Organization parentOrganization) {
			this.parentOrganization = (OrganizationImpl)parentOrganization;
		}

		@JsonIgnore
		public void setParentOrganization(
			UnsafeSupplier<Organization, Throwable>
				parentOrganizationUnsafeSupplier) {

			try {
				parentOrganization =
					(OrganizationImpl)parentOrganizationUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setParentOrganizationId(Long parentOrganizationId) {
			this.parentOrganizationId = (Long)parentOrganizationId;
		}

		@JsonIgnore
		public void setParentOrganizationId(
			UnsafeSupplier<Long, Throwable>
				parentOrganizationIdUnsafeSupplier) {

			try {
				parentOrganizationId =
					(Long)parentOrganizationIdUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setServices(Services[] services) {
			this.services = (ServicesImpl[])services;
		}

		@JsonIgnore
		public void setServices(
			UnsafeSupplier<Services[], Throwable> servicesUnsafeSupplier) {

			try {
				services = (ServicesImpl[])servicesUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setSubOrganization(Organization[] subOrganization) {
			this.subOrganization = (OrganizationImpl[])subOrganization;
		}

		@JsonIgnore
		public void setSubOrganization(
			UnsafeSupplier<Organization[], Throwable>
				subOrganizationUnsafeSupplier) {

			try {
				subOrganization =
					(OrganizationImpl[])subOrganizationUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setSubOrganizationIds(Long[] subOrganizationIds) {
			this.subOrganizationIds = (Long[])subOrganizationIds;
		}

		@JsonIgnore
		public void setSubOrganizationIds(
			UnsafeSupplier<Long[], Throwable>
				subOrganizationIdsUnsafeSupplier) {

			try {
				subOrganizationIds =
					(Long[])subOrganizationIdsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonProperty
		protected String comment;

		@JsonProperty
		protected ContactInformationImpl contactInformation;

		@JsonProperty
		protected Long id;

		@JsonProperty
		protected LocationImpl location;

		@JsonProperty
		protected String logo;

		@JsonProperty
		protected UserAccountImpl[] members;

		@JsonProperty
		protected Long[] membersIds;

		@JsonProperty
		protected String name;

		@JsonProperty
		protected OrganizationImpl parentOrganization;

		@JsonProperty
		protected Long parentOrganizationId;

		@JsonProperty
		protected ServicesImpl[] services;

		@JsonProperty
		protected OrganizationImpl[] subOrganization;

		@JsonProperty
		protected Long[] subOrganizationIds;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}