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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountAccountGroupSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountAccountGroupResourceTestCase {

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

		_discountAccountGroupResource.setContextCompany(testCompany);

		DiscountAccountGroupResource.Builder builder =
			DiscountAccountGroupResource.builder();

		discountAccountGroupResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		DiscountAccountGroup discountAccountGroup1 =
			randomDiscountAccountGroup();

		String json = objectMapper.writeValueAsString(discountAccountGroup1);

		DiscountAccountGroup discountAccountGroup2 =
			DiscountAccountGroupSerDes.toDTO(json);

		Assert.assertTrue(equals(discountAccountGroup1, discountAccountGroup2));
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

		DiscountAccountGroup discountAccountGroup =
			randomDiscountAccountGroup();

		String json1 = objectMapper.writeValueAsString(discountAccountGroup);
		String json2 = DiscountAccountGroupSerDes.toJSON(discountAccountGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountAccountGroup discountAccountGroup =
			randomDiscountAccountGroup();

		discountAccountGroup.setAccountGroupExternalReferenceCode(regex);
		discountAccountGroup.setDiscountExternalReferenceCode(regex);

		String json = DiscountAccountGroupSerDes.toJSON(discountAccountGroup);

		Assert.assertFalse(json.contains(regex));

		discountAccountGroup = DiscountAccountGroupSerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountAccountGroup.getAccountGroupExternalReferenceCode());
		Assert.assertEquals(
			regex, discountAccountGroup.getDiscountExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountAccountGroup() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountAccountGroup discountAccountGroup =
			testDeleteDiscountAccountGroup_addDiscountAccountGroup();

		assertHttpResponseStatusCode(
			204,
			discountAccountGroupResource.deleteDiscountAccountGroupHttpResponse(
				discountAccountGroup.getId()));
	}

	protected DiscountAccountGroup
			testDeleteDiscountAccountGroup_addDiscountAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscountAccountGroup() throws Exception {
		DiscountAccountGroup discountAccountGroup =
			testGraphQLDiscountAccountGroup_addDiscountAccountGroup();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscountAccountGroup",
						new HashMap<String, Object>() {
							{
								put("id", discountAccountGroup.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscountAccountGroup"));
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage()
		throws Exception {

		Page<DiscountAccountGroup> page =
			discountAccountGroupResource.
				getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
					testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			DiscountAccountGroup irrelevantDiscountAccountGroup =
				testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountAccountGroup());

			page =
				discountAccountGroupResource.
					getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountAccountGroup),
				(List<DiscountAccountGroup>)page.getItems());
			assertValid(page);
		}

		DiscountAccountGroup discountAccountGroup1 =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				externalReferenceCode, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup2 =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				externalReferenceCode, randomDiscountAccountGroup());

		page =
			discountAccountGroupResource.
				getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccountGroup1, discountAccountGroup2),
			(List<DiscountAccountGroup>)page.getItems());
		assertValid(page);

		discountAccountGroupResource.deleteDiscountAccountGroup(
			discountAccountGroup1.getId());

		discountAccountGroupResource.deleteDiscountAccountGroup(
			discountAccountGroup2.getId());
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getExternalReferenceCode();

		DiscountAccountGroup discountAccountGroup1 =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				externalReferenceCode, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup2 =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				externalReferenceCode, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup3 =
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				externalReferenceCode, randomDiscountAccountGroup());

		Page<DiscountAccountGroup> page1 =
			discountAccountGroupResource.
				getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountAccountGroup> discountAccountGroups1 =
			(List<DiscountAccountGroup>)page1.getItems();

		Assert.assertEquals(
			discountAccountGroups1.toString(), 2,
			discountAccountGroups1.size());

		Page<DiscountAccountGroup> page2 =
			discountAccountGroupResource.
				getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountAccountGroup> discountAccountGroups2 =
			(List<DiscountAccountGroup>)page2.getItems();

		Assert.assertEquals(
			discountAccountGroups2.toString(), 1,
			discountAccountGroups2.size());

		Page<DiscountAccountGroup> page3 =
			discountAccountGroupResource.
				getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountAccountGroup1, discountAccountGroup2,
				discountAccountGroup3),
			(List<DiscountAccountGroup>)page3.getItems());
	}

	protected DiscountAccountGroup
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_addDiscountAccountGroup(
				String externalReferenceCode,
				DiscountAccountGroup discountAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountAccountGroupsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountAccountGroup()
		throws Exception {

		DiscountAccountGroup randomDiscountAccountGroup =
			randomDiscountAccountGroup();

		DiscountAccountGroup postDiscountAccountGroup =
			testPostDiscountByExternalReferenceCodeDiscountAccountGroup_addDiscountAccountGroup(
				randomDiscountAccountGroup);

		assertEquals(randomDiscountAccountGroup, postDiscountAccountGroup);
		assertValid(postDiscountAccountGroup);
	}

	protected DiscountAccountGroup
			testPostDiscountByExternalReferenceCodeDiscountAccountGroup_addDiscountAccountGroup(
				DiscountAccountGroup discountAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPage() throws Exception {
		Page<DiscountAccountGroup> page =
			discountAccountGroupResource.getDiscountIdDiscountAccountGroupsPage(
				testGetDiscountIdDiscountAccountGroupsPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetDiscountIdDiscountAccountGroupsPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountAccountGroupsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			DiscountAccountGroup irrelevantDiscountAccountGroup =
				testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
					irrelevantId, randomIrrelevantDiscountAccountGroup());

			page =
				discountAccountGroupResource.
					getDiscountIdDiscountAccountGroupsPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountAccountGroup),
				(List<DiscountAccountGroup>)page.getItems());
			assertValid(page);
		}

		DiscountAccountGroup discountAccountGroup1 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup2 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		page =
			discountAccountGroupResource.getDiscountIdDiscountAccountGroupsPage(
				id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccountGroup1, discountAccountGroup2),
			(List<DiscountAccountGroup>)page.getItems());
		assertValid(page);

		discountAccountGroupResource.deleteDiscountAccountGroup(
			discountAccountGroup1.getId());

		discountAccountGroupResource.deleteDiscountAccountGroup(
			discountAccountGroup2.getId());
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountGroupsPage_getId();

		DiscountAccountGroup discountAccountGroup1 =
			randomDiscountAccountGroup();

		discountAccountGroup1 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, discountAccountGroup1);

		for (EntityField entityField : entityFields) {
			Page<DiscountAccountGroup> page =
				discountAccountGroupResource.
					getDiscountIdDiscountAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "between", discountAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountAccountGroup1),
				(List<DiscountAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountGroupsPage_getId();

		DiscountAccountGroup discountAccountGroup1 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountAccountGroup discountAccountGroup2 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		for (EntityField entityField : entityFields) {
			Page<DiscountAccountGroup> page =
				discountAccountGroupResource.
					getDiscountIdDiscountAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "eq", discountAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountAccountGroup1),
				(List<DiscountAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountAccountGroupsPage_getId();

		DiscountAccountGroup discountAccountGroup1 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup2 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		DiscountAccountGroup discountAccountGroup3 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, randomDiscountAccountGroup());

		Page<DiscountAccountGroup> page1 =
			discountAccountGroupResource.getDiscountIdDiscountAccountGroupsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountAccountGroup> discountAccountGroups1 =
			(List<DiscountAccountGroup>)page1.getItems();

		Assert.assertEquals(
			discountAccountGroups1.toString(), 2,
			discountAccountGroups1.size());

		Page<DiscountAccountGroup> page2 =
			discountAccountGroupResource.getDiscountIdDiscountAccountGroupsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountAccountGroup> discountAccountGroups2 =
			(List<DiscountAccountGroup>)page2.getItems();

		Assert.assertEquals(
			discountAccountGroups2.toString(), 1,
			discountAccountGroups2.size());

		Page<DiscountAccountGroup> page3 =
			discountAccountGroupResource.getDiscountIdDiscountAccountGroupsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountAccountGroup1, discountAccountGroup2,
				discountAccountGroup3),
			(List<DiscountAccountGroup>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountAccountGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountAccountGroup1, discountAccountGroup2) -> {
				BeanUtils.setProperty(
					discountAccountGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountAccountGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountAccountGroup1, discountAccountGroup2) -> {
				BeanUtils.setProperty(
					discountAccountGroup1, entityField.getName(), 0);
				BeanUtils.setProperty(
					discountAccountGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountAccountGroupsPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountAccountGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountAccountGroup1, discountAccountGroup2) -> {
				Class<?> clazz = discountAccountGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discountAccountGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discountAccountGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discountAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discountAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discountAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discountAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountAccountGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountAccountGroup, DiscountAccountGroup,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountGroupsPage_getId();

		DiscountAccountGroup discountAccountGroup1 =
			randomDiscountAccountGroup();
		DiscountAccountGroup discountAccountGroup2 =
			randomDiscountAccountGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, discountAccountGroup1, discountAccountGroup2);
		}

		discountAccountGroup1 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, discountAccountGroup1);

		discountAccountGroup2 =
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				id, discountAccountGroup2);

		for (EntityField entityField : entityFields) {
			Page<DiscountAccountGroup> ascPage =
				discountAccountGroupResource.
					getDiscountIdDiscountAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountAccountGroup1, discountAccountGroup2),
				(List<DiscountAccountGroup>)ascPage.getItems());

			Page<DiscountAccountGroup> descPage =
				discountAccountGroupResource.
					getDiscountIdDiscountAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountAccountGroup2, discountAccountGroup1),
				(List<DiscountAccountGroup>)descPage.getItems());
		}
	}

	protected DiscountAccountGroup
			testGetDiscountIdDiscountAccountGroupsPage_addDiscountAccountGroup(
				Long id, DiscountAccountGroup discountAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountAccountGroupsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountAccountGroupsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountAccountGroup() throws Exception {
		DiscountAccountGroup randomDiscountAccountGroup =
			randomDiscountAccountGroup();

		DiscountAccountGroup postDiscountAccountGroup =
			testPostDiscountIdDiscountAccountGroup_addDiscountAccountGroup(
				randomDiscountAccountGroup);

		assertEquals(randomDiscountAccountGroup, postDiscountAccountGroup);
		assertValid(postDiscountAccountGroup);
	}

	protected DiscountAccountGroup
			testPostDiscountIdDiscountAccountGroup_addDiscountAccountGroup(
				DiscountAccountGroup discountAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected DiscountAccountGroup
			testGraphQLDiscountAccountGroup_addDiscountAccountGroup()
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
		DiscountAccountGroup discountAccountGroup1,
		DiscountAccountGroup discountAccountGroup2) {

		Assert.assertTrue(
			discountAccountGroup1 + " does not equal " + discountAccountGroup2,
			equals(discountAccountGroup1, discountAccountGroup2));
	}

	protected void assertEquals(
		List<DiscountAccountGroup> discountAccountGroups1,
		List<DiscountAccountGroup> discountAccountGroups2) {

		Assert.assertEquals(
			discountAccountGroups1.size(), discountAccountGroups2.size());

		for (int i = 0; i < discountAccountGroups1.size(); i++) {
			DiscountAccountGroup discountAccountGroup1 =
				discountAccountGroups1.get(i);
			DiscountAccountGroup discountAccountGroup2 =
				discountAccountGroups2.get(i);

			assertEquals(discountAccountGroup1, discountAccountGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountAccountGroup> discountAccountGroups1,
		List<DiscountAccountGroup> discountAccountGroups2) {

		Assert.assertEquals(
			discountAccountGroups1.size(), discountAccountGroups2.size());

		for (DiscountAccountGroup discountAccountGroup1 :
				discountAccountGroups1) {

			boolean contains = false;

			for (DiscountAccountGroup discountAccountGroup2 :
					discountAccountGroups2) {

				if (equals(discountAccountGroup1, discountAccountGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountAccountGroups2 + " does not contain " +
					discountAccountGroup1,
				contains);
		}
	}

	protected void assertValid(DiscountAccountGroup discountAccountGroup)
		throws Exception {

		boolean valid = true;

		if (discountAccountGroup.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (discountAccountGroup.getAccountGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountAccountGroup.
						getAccountGroupExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (discountAccountGroup.getAccountGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountAccountGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountAccountGroup.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountAccountGroup.getDiscountId() == null) {
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

	protected void assertValid(Page<DiscountAccountGroup> page) {
		boolean valid = false;

		java.util.Collection<DiscountAccountGroup> discountAccountGroups =
			page.getItems();

		int size = discountAccountGroups.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountAccountGroup.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DiscountAccountGroup discountAccountGroup1,
		DiscountAccountGroup discountAccountGroup2) {

		if (discountAccountGroup1 == discountAccountGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccountGroup1.getAccountGroup(),
						discountAccountGroup2.getAccountGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountAccountGroup1.
							getAccountGroupExternalReferenceCode(),
						discountAccountGroup2.
							getAccountGroupExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccountGroup1.getAccountGroupId(),
						discountAccountGroup2.getAccountGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountAccountGroup1.getActions(),
						(Map)discountAccountGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountAccountGroup1.
							getDiscountExternalReferenceCode(),
						discountAccountGroup2.
							getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccountGroup1.getDiscountId(),
						discountAccountGroup2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccountGroup1.getId(),
						discountAccountGroup2.getId())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountAccountGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountAccountGroupResource;

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
		EntityField entityField, String operator,
		DiscountAccountGroup discountAccountGroup) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountGroup")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("accountGroupExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountAccountGroup.
						getAccountGroupExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("accountGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountAccountGroup.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DiscountAccountGroup randomDiscountAccountGroup()
		throws Exception {

		return new DiscountAccountGroup() {
			{
				accountGroupExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountGroupId = RandomTestUtil.randomLong();
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DiscountAccountGroup randomIrrelevantDiscountAccountGroup()
		throws Exception {

		DiscountAccountGroup randomIrrelevantDiscountAccountGroup =
			randomDiscountAccountGroup();

		return randomIrrelevantDiscountAccountGroup;
	}

	protected DiscountAccountGroup randomPatchDiscountAccountGroup()
		throws Exception {

		return randomDiscountAccountGroup();
	}

	protected DiscountAccountGroupResource discountAccountGroupResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDiscountAccountGroupResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountAccountGroupResource _discountAccountGroupResource;

}