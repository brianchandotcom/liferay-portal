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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseOrganizationResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
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
		Long myUserAccountId =
			testGetMyUserAccountOrganizationsPage_getMyUserAccountId();

		Organization organization1 =
			testGetMyUserAccountOrganizationsPage_addOrganization(
				myUserAccountId, randomOrganization());
		Organization organization2 =
			testGetMyUserAccountOrganizationsPage_addOrganization(
				myUserAccountId, randomOrganization());

		Page<Organization> page = invokeGetMyUserAccountOrganizationsPage(
			myUserAccountId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMyUserAccountOrganizationsPageWithPagination()
		throws Exception {

		Long myUserAccountId =
			testGetMyUserAccountOrganizationsPage_getMyUserAccountId();

		Organization organization1 =
			testGetMyUserAccountOrganizationsPage_addOrganization(
				myUserAccountId, randomOrganization());
		Organization organization2 =
			testGetMyUserAccountOrganizationsPage_addOrganization(
				myUserAccountId, randomOrganization());
		Organization organization3 =
			testGetMyUserAccountOrganizationsPage_addOrganization(
				myUserAccountId, randomOrganization());

		Page<Organization> page1 = invokeGetMyUserAccountOrganizationsPage(
			myUserAccountId, Pagination.of(1, 2));

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 = invokeGetMyUserAccountOrganizationsPage(
			myUserAccountId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			new ArrayList<Organization>() {
				{
					addAll(organizations1);
					addAll(organizations2);
				}
			});
	}

	protected Organization
			testGetMyUserAccountOrganizationsPage_addOrganization(
				Long myUserAccountId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetMyUserAccountOrganizationsPage_getMyUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Organization> invokeGetMyUserAccountOrganizationsPage(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/my-user-accounts/{my-user-account-id}/organizations",
					myUserAccountId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Organization>>() {
			});
	}

	protected Http.Response invokeGetMyUserAccountOrganizationsPageResponse(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/my-user-accounts/{my-user-account-id}/organizations",
					myUserAccountId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganizationsPage() throws Exception {
		Assert.assertTrue(true);
	}

	protected Page<Organization> invokeGetOrganizationsPage(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/organizations", pagination);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Organization>>() {
			});
	}

	protected Http.Response invokeGetOrganizationsPageResponse(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/organizations", pagination);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganization() throws Exception {
		Organization postOrganization = testGetOrganization_addOrganization();

		Organization getOrganization = invokeGetOrganization(
			postOrganization.getId());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Organization invokeGetOrganization(Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/organizations/{organization-id}", organizationId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Organization.class);
	}

	protected Http.Response invokeGetOrganizationResponse(Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/organizations/{organization-id}", organizationId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganizationOrganizationsPage() throws Exception {
		Long organizationId =
			testGetOrganizationOrganizationsPage_getOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				organizationId, randomOrganization());
		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Page<Organization> page = invokeGetOrganizationOrganizationsPage(
			organizationId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithPagination()
		throws Exception {

		Long organizationId =
			testGetOrganizationOrganizationsPage_getOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				organizationId, randomOrganization());
		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				organizationId, randomOrganization());
		Organization organization3 =
			testGetOrganizationOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Page<Organization> page1 = invokeGetOrganizationOrganizationsPage(
			organizationId, Pagination.of(1, 2));

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 = invokeGetOrganizationOrganizationsPage(
			organizationId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			new ArrayList<Organization>() {
				{
					addAll(organizations1);
					addAll(organizations2);
				}
			});
	}

	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			Long organizationId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationOrganizationsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Organization> invokeGetOrganizationOrganizationsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organization-id}/organizations",
					organizationId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Organization>>() {
			});
	}

	protected Http.Response invokeGetOrganizationOrganizationsPageResponse(
			Long organizationId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organization-id}/organizations",
					organizationId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccountOrganizationsPage() throws Exception {
		Long userAccountId =
			testGetUserAccountOrganizationsPage_getUserAccountId();

		Organization organization1 =
			testGetUserAccountOrganizationsPage_addOrganization(
				userAccountId, randomOrganization());
		Organization organization2 =
			testGetUserAccountOrganizationsPage_addOrganization(
				userAccountId, randomOrganization());

		Page<Organization> page = invokeGetUserAccountOrganizationsPage(
			userAccountId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetUserAccountOrganizationsPageWithPagination()
		throws Exception {

		Long userAccountId =
			testGetUserAccountOrganizationsPage_getUserAccountId();

		Organization organization1 =
			testGetUserAccountOrganizationsPage_addOrganization(
				userAccountId, randomOrganization());
		Organization organization2 =
			testGetUserAccountOrganizationsPage_addOrganization(
				userAccountId, randomOrganization());
		Organization organization3 =
			testGetUserAccountOrganizationsPage_addOrganization(
				userAccountId, randomOrganization());

		Page<Organization> page1 = invokeGetUserAccountOrganizationsPage(
			userAccountId, Pagination.of(1, 2));

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 = invokeGetUserAccountOrganizationsPage(
			userAccountId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			new ArrayList<Organization>() {
				{
					addAll(organizations1);
					addAll(organizations2);
				}
			});
	}

	protected Organization testGetUserAccountOrganizationsPage_addOrganization(
			Long userAccountId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountOrganizationsPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Organization> invokeGetUserAccountOrganizationsPage(
			Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{user-account-id}/organizations",
					userAccountId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Organization>>() {
			});
	}

	protected Http.Response invokeGetUserAccountOrganizationsPageResponse(
			Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{user-account-id}/organizations",
					userAccountId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		Organization organization1, Organization organization2) {

		Assert.assertTrue(
			organization1 + " does not equal " + organization2,
			equals(organization1, organization2));
	}

	protected void assertEquals(
		List<Organization> organizations1, List<Organization> organizations2) {

		Assert.assertEquals(organizations1.size(), organizations2.size());

		for (int i = 0; i < organizations1.size(); i++) {
			Organization organization1 = organizations1.get(i);
			Organization organization2 = organizations2.get(i);

			assertEquals(organization1, organization2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Organization> organizations1, List<Organization> organizations2) {

		Assert.assertEquals(organizations1.size(), organizations2.size());

		for (Organization organization1 : organizations1) {
			boolean contains = false;

			for (Organization organization2 : organizations2) {
				if (equals(organization1, organization2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				organizations2 + " does not contain " + organization1,
				contains);
		}
	}

	protected void assertValid(Organization organization) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Organization> page) {
		boolean valid = false;

		Collection<Organization> organizations = page.getItems();

		int size = organizations.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		Organization organization1, Organization organization2) {

		if (organization1 == organization2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_organizationResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_organizationResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Organization organization) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("comment")) {
			sb.append("'");
			sb.append(String.valueOf(organization.getComment()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hasOrganizations")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			sb.append("'");
			sb.append(String.valueOf(organization.getImage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("location")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(organization.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("parentOrganization")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentOrganizationId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("services")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Organization randomOrganization() {
		return new Organization() {
			{
				comment = RandomTestUtil.randomString();
				hasOrganizations = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				parentOrganizationId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Organization randomPatchOrganization() {
		return randomOrganization();
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

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

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private OrganizationResource _organizationResource;

	private URL _resourceURL;

}