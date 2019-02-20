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
public abstract class BaseOrganizationResourceTestCase {

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
	public void testGetMyUserAccountOrganizationsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetOrganizationsPage() throws Exception {
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
	public void testGetUserAccountOrganizationsPage() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetMyUserAccountOrganizationsPage(
				Long myUserAccountId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/my-user-accounts/{my-user-account-id}/organizations",
					myUserAccountId
				);
	}
	protected Response invokeGetOrganizationsPage(
				Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/organizations",
					pagination
				);
	}
	protected Response invokeGetOrganization(
				Long organizationId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/organizations/{organization-id}",
					organizationId
				);
	}
	protected Response invokeGetOrganizationOrganizationsPage(
				Long organizationId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/organizations/{organization-id}/organizations",
					organizationId
				);
	}
	protected Response invokeGetUserAccountOrganizationsPage(
				Long userAccountId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/user-accounts/{user-account-id}/organizations",
					userAccountId
				);
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

	protected class OrganizationImpl implements Organization {

	public String getComment() {
				return comment;
	}

	public void setComment(String comment) {
				this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
				UnsafeSupplier<String, Throwable> commentUnsafeSupplier) {

				try {
					comment = commentUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String comment;
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
	public Location getLocation() {
				return location;
	}

	public void setLocation(Location location) {
				this.location = location;
	}

	@JsonIgnore
	public void setLocation(
				UnsafeSupplier<Location, Throwable> locationUnsafeSupplier) {

				try {
					location = locationUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Location location;
	public String getLogo() {
				return logo;
	}

	public void setLogo(String logo) {
				this.logo = logo;
	}

	@JsonIgnore
	public void setLogo(
				UnsafeSupplier<String, Throwable> logoUnsafeSupplier) {

				try {
					logo = logoUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String logo;
	public UserAccount[] getMembers() {
				return members;
	}

	public void setMembers(UserAccount[] members) {
				this.members = members;
	}

	@JsonIgnore
	public void setMembers(
				UnsafeSupplier<UserAccount[], Throwable> membersUnsafeSupplier) {

				try {
					members = membersUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected UserAccount[] members;
	public Long[] getMembersIds() {
				return membersIds;
	}

	public void setMembersIds(Long[] membersIds) {
				this.membersIds = membersIds;
	}

	@JsonIgnore
	public void setMembersIds(
				UnsafeSupplier<Long[], Throwable> membersIdsUnsafeSupplier) {

				try {
					membersIds = membersIdsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long[] membersIds;
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
	public Organization getParentOrganization() {
				return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
				this.parentOrganization = parentOrganization;
	}

	@JsonIgnore
	public void setParentOrganization(
				UnsafeSupplier<Organization, Throwable> parentOrganizationUnsafeSupplier) {

				try {
					parentOrganization = parentOrganizationUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Organization parentOrganization;
	public Long getParentOrganizationId() {
				return parentOrganizationId;
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
				this.parentOrganizationId = parentOrganizationId;
	}

	@JsonIgnore
	public void setParentOrganizationId(
				UnsafeSupplier<Long, Throwable> parentOrganizationIdUnsafeSupplier) {

				try {
					parentOrganizationId = parentOrganizationIdUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long parentOrganizationId;
	public Services[] getServices() {
				return services;
	}

	public void setServices(Services[] services) {
				this.services = services;
	}

	@JsonIgnore
	public void setServices(
				UnsafeSupplier<Services[], Throwable> servicesUnsafeSupplier) {

				try {
					services = servicesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Services[] services;
	public Organization[] getSubOrganization() {
				return subOrganization;
	}

	public void setSubOrganization(Organization[] subOrganization) {
				this.subOrganization = subOrganization;
	}

	@JsonIgnore
	public void setSubOrganization(
				UnsafeSupplier<Organization[], Throwable> subOrganizationUnsafeSupplier) {

				try {
					subOrganization = subOrganizationUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Organization[] subOrganization;
	public Long[] getSubOrganizationIds() {
				return subOrganizationIds;
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
				this.subOrganizationIds = subOrganizationIds;
	}

	@JsonIgnore
	public void setSubOrganizationIds(
				UnsafeSupplier<Long[], Throwable> subOrganizationIdsUnsafeSupplier) {

				try {
					subOrganizationIds = subOrganizationIdsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long[] subOrganizationIds;

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