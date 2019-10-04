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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.OrganizationSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

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
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_organizationResource.setContextCompany(testCompany);

		OrganizationResource.Builder builder = OrganizationResource.builder();

		organizationResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Organization organization1 = randomOrganization();

		String json = objectMapper.writeValueAsString(organization1);

		Organization organization2 = OrganizationSerDes.toDTO(json);

		Assert.assertTrue(equals(organization1, organization2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Organization organization = randomOrganization();

		String json1 = objectMapper.writeValueAsString(organization);
		String json2 = OrganizationSerDes.toJSON(organization);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Organization organization = randomOrganization();

		organization.setComment(regex);
		organization.setImage(regex);
		organization.setName(regex);

		String json = OrganizationSerDes.toJSON(organization);

		Assert.assertFalse(json.contains(regex));

		organization = OrganizationSerDes.toDTO(json);

		Assert.assertEquals(regex, organization.getComment());
		Assert.assertEquals(regex, organization.getImage());
		Assert.assertEquals(regex, organization.getName());
	}

	@Test
	public void testGetOrganizationsPage() throws Exception {
		Page<Organization> page = organizationResource.getOrganizationsPage(
			null, RandomTestUtil.randomString(), null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		page = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = randomOrganization();

		organization1 = testGetOrganizationsPage_addOrganization(organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page = organizationResource.getOrganizationsPage(
				null, null,
				getFilterString(entityField, "between", organization1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page = organizationResource.getOrganizationsPage(
				null, null, getFilterString(entityField, "eq", organization1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationsPageWithPagination() throws Exception {
		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization3 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Page<Organization> page1 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 2), null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetOrganizationsPageWithSortDateTime() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanUtils.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationsPageWithSortInteger() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanUtils.setProperty(organization1, entityField.getName(), 0);
				BeanUtils.setProperty(organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationsPageWithSortString() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						organization1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						organization2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						organization1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						organization2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 = testGetOrganizationsPage_addOrganization(organization1);

		organization2 = testGetOrganizationsPage_addOrganization(organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getOrganizationsPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getOrganizationsPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization testGetOrganizationsPage_addOrganization(
			Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrganizationsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"organizations",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject organizationsJSONObject = dataJSONObject.getJSONObject(
			"organizations");

		Assert.assertEquals(0, organizationsJSONObject.get("totalCount"));

		Organization organization1 = testGraphQLOrganization_addOrganization();
		Organization organization2 = testGraphQLOrganization_addOrganization();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		organizationsJSONObject = dataJSONObject.getJSONObject("organizations");

		Assert.assertEquals(2, organizationsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(organization1, organization2),
			organizationsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testGetOrganization() throws Exception {
		Organization postOrganization = testGetOrganization_addOrganization();

		Organization getOrganization = organizationResource.getOrganization(
			postOrganization.getId());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrganization() throws Exception {
		Organization organization = testGraphQLOrganization_addOrganization();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"organization",
				new HashMap<String, Object>() {
					{
						put("organizationId", organization.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				organization, dataJSONObject.getJSONObject("organization")));
	}

	@Test
	public void testGetOrganizationOrganizationsPage() throws Exception {
		Page<Organization> page =
			organizationResource.getOrganizationOrganizationsPage(
				testGetOrganizationOrganizationsPage_getParentOrganizationId(),
				null, RandomTestUtil.randomString(), null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();
		Long irrelevantParentOrganizationId =
			testGetOrganizationOrganizationsPage_getIrrelevantParentOrganizationId();

		if ((irrelevantParentOrganizationId != null)) {
			Organization irrelevantOrganization =
				testGetOrganizationOrganizationsPage_addOrganization(
					irrelevantParentOrganizationId,
					randomIrrelevantOrganization());

			page = organizationResource.getOrganizationOrganizationsPage(
				irrelevantParentOrganizationId, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrganization),
				(List<Organization>)page.getItems());
			assertValid(page);
		}

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		page = organizationResource.getOrganizationOrganizationsPage(
			parentOrganizationId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 = randomOrganization();

		organization1 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null,
					getFilterString(entityField, "between", organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null,
					getFilterString(entityField, "eq", organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithPagination()
		throws Exception {

		Long parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization3 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Page<Organization> page1 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(1, 2),
				null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortDateTime()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanUtils.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortInteger()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanUtils.setProperty(organization1, entityField.getName(), 0);
				BeanUtils.setProperty(organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortString()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						organization1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						organization2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						organization1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						organization2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization1);

		organization2 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			Long parentOrganizationId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrganizationOrganizationsPage_getParentOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrganizationOrganizationsPage_getIrrelevantParentOrganizationId()
		throws Exception {

		return null;
	}

	protected Organization testGraphQLOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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

	protected void assertEqualsJSONArray(
		List<Organization> organizations, JSONArray jsonArray) {

		for (Organization organization : organizations) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(organization, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + organization, contains);
		}
	}

	protected void assertValid(Organization organization) {
		boolean valid = true;

		if (organization.getDateCreated() == null) {
			valid = false;
		}

		if (organization.getDateModified() == null) {
			valid = false;
		}

		if (organization.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("comment", additionalAssertFieldName)) {
				if (organization.getComment() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contactInformation", additionalAssertFieldName)) {

				if (organization.getContactInformation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (organization.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (organization.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (organization.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (organization.getLocation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (organization.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfOrganizations", additionalAssertFieldName)) {

				if (organization.getNumberOfOrganizations() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrganization", additionalAssertFieldName)) {

				if (organization.getParentOrganization() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("services", additionalAssertFieldName)) {
				if (organization.getServices() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Organization> page) {
		boolean valid = false;

		java.util.Collection<Organization> organizations = page.getItems();

		int size = organizations.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		Organization organization1, Organization organization2) {

		if (organization1 == organization2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("comment", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getComment(),
						organization2.getComment())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contactInformation", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getContactInformation(),
						organization2.getContactInformation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getCustomFields(),
						organization2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getDateCreated(),
						organization2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getDateModified(),
						organization2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getId(), organization2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getImage(), organization2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getKeywords(),
						organization2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getLocation(),
						organization2.getLocation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getName(), organization2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfOrganizations", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getNumberOfOrganizations(),
						organization2.getNumberOfOrganizations())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrganization", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getParentOrganization(),
						organization2.getParentOrganization())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("services", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getServices(),
						organization2.getServices())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equalsJSONObject(
		Organization organization, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("comment", fieldName)) {
				if (!Objects.deepEquals(
						organization.getComment(),
						jsonObject.getString("comment"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						organization.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", fieldName)) {
				if (!Objects.deepEquals(
						organization.getImage(),
						jsonObject.getString("image"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						organization.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfOrganizations", fieldName)) {
				if (!Objects.deepEquals(
						organization.getNumberOfOrganizations(),
						jsonObject.getInt("numberOfOrganizations"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
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

		if (entityFieldName.equals("contactInformation")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(organization.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(organization.getDateModified()));
			}

			return sb.toString();
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

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

		if (entityFieldName.equals("numberOfOrganizations")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentOrganization")) {
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

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Organization randomOrganization() throws Exception {
		return new Organization() {
			{
				comment = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				numberOfOrganizations = RandomTestUtil.randomInt();
			}
		};
	}

	protected Organization randomIrrelevantOrganization() throws Exception {
		Organization randomIrrelevantOrganization = randomOrganization();

		return randomIrrelevantOrganization;
	}

	protected Organization randomPatchOrganization() throws Exception {
		return randomOrganization();
	}

	protected OrganizationResource organizationResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseOrganizationResourceTestCase.class);

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

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.OrganizationResource
		_organizationResource;

}