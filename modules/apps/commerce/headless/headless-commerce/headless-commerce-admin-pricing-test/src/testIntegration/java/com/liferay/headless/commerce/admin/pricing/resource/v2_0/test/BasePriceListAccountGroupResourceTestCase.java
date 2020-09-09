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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceListAccountGroupSerDes;
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
public abstract class BasePriceListAccountGroupResourceTestCase {

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

		_priceListAccountGroupResource.setContextCompany(testCompany);

		PriceListAccountGroupResource.Builder builder =
			PriceListAccountGroupResource.builder();

		priceListAccountGroupResource = builder.authentication(
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

		PriceListAccountGroup priceListAccountGroup1 =
			randomPriceListAccountGroup();

		String json = objectMapper.writeValueAsString(priceListAccountGroup1);

		PriceListAccountGroup priceListAccountGroup2 =
			PriceListAccountGroupSerDes.toDTO(json);

		Assert.assertTrue(
			equals(priceListAccountGroup1, priceListAccountGroup2));
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

		PriceListAccountGroup priceListAccountGroup =
			randomPriceListAccountGroup();

		String json1 = objectMapper.writeValueAsString(priceListAccountGroup);
		String json2 = PriceListAccountGroupSerDes.toJSON(
			priceListAccountGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceListAccountGroup priceListAccountGroup =
			randomPriceListAccountGroup();

		priceListAccountGroup.setAccountGroupExternalReferenceCode(regex);
		priceListAccountGroup.setPriceListExternalReferenceCode(regex);

		String json = PriceListAccountGroupSerDes.toJSON(priceListAccountGroup);

		Assert.assertFalse(json.contains(regex));

		priceListAccountGroup = PriceListAccountGroupSerDes.toDTO(json);

		Assert.assertEquals(
			regex,
			priceListAccountGroup.getAccountGroupExternalReferenceCode());
		Assert.assertEquals(
			regex, priceListAccountGroup.getPriceListExternalReferenceCode());
	}

	@Test
	public void testDeletePriceListAccountGroup() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListAccountGroup priceListAccountGroup =
			testDeletePriceListAccountGroup_addPriceListAccountGroup();

		assertHttpResponseStatusCode(
			204,
			priceListAccountGroupResource.
				deletePriceListAccountGroupHttpResponse(
					priceListAccountGroup.getId()));
	}

	protected PriceListAccountGroup
			testDeletePriceListAccountGroup_addPriceListAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceListAccountGroup() throws Exception {
		PriceListAccountGroup priceListAccountGroup =
			testGraphQLPriceListAccountGroup_addPriceListAccountGroup();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceListAccountGroup",
						new HashMap<String, Object>() {
							{
								put("id", priceListAccountGroup.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceListAccountGroup"));
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage()
		throws Exception {

		Page<PriceListAccountGroup> page =
			priceListAccountGroupResource.
				getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
					testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceListAccountGroup irrelevantPriceListAccountGroup =
				testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceListAccountGroup());

			page =
				priceListAccountGroupResource.
					getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListAccountGroup),
				(List<PriceListAccountGroup>)page.getItems());
			assertValid(page);
		}

		PriceListAccountGroup priceListAccountGroup1 =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				externalReferenceCode, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup2 =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				externalReferenceCode, randomPriceListAccountGroup());

		page =
			priceListAccountGroupResource.
				getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListAccountGroup1, priceListAccountGroup2),
			(List<PriceListAccountGroup>)page.getItems());
		assertValid(page);

		priceListAccountGroupResource.deletePriceListAccountGroup(
			priceListAccountGroup1.getId());

		priceListAccountGroupResource.deletePriceListAccountGroup(
			priceListAccountGroup2.getId());
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListAccountGroupsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getExternalReferenceCode();

		PriceListAccountGroup priceListAccountGroup1 =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				externalReferenceCode, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup2 =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				externalReferenceCode, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup3 =
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				externalReferenceCode, randomPriceListAccountGroup());

		Page<PriceListAccountGroup> page1 =
			priceListAccountGroupResource.
				getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceListAccountGroup> priceListAccountGroups1 =
			(List<PriceListAccountGroup>)page1.getItems();

		Assert.assertEquals(
			priceListAccountGroups1.toString(), 2,
			priceListAccountGroups1.size());

		Page<PriceListAccountGroup> page2 =
			priceListAccountGroupResource.
				getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListAccountGroup> priceListAccountGroups2 =
			(List<PriceListAccountGroup>)page2.getItems();

		Assert.assertEquals(
			priceListAccountGroups2.toString(), 1,
			priceListAccountGroups2.size());

		Page<PriceListAccountGroup> page3 =
			priceListAccountGroupResource.
				getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListAccountGroup1, priceListAccountGroup2,
				priceListAccountGroup3),
			(List<PriceListAccountGroup>)page3.getItems());
	}

	protected PriceListAccountGroup
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_addPriceListAccountGroup(
				String externalReferenceCode,
				PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListAccountGroupsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceListAccountGroup()
		throws Exception {

		PriceListAccountGroup randomPriceListAccountGroup =
			randomPriceListAccountGroup();

		PriceListAccountGroup postPriceListAccountGroup =
			testPostPriceListByExternalReferenceCodePriceListAccountGroup_addPriceListAccountGroup(
				randomPriceListAccountGroup);

		assertEquals(randomPriceListAccountGroup, postPriceListAccountGroup);
		assertValid(postPriceListAccountGroup);
	}

	protected PriceListAccountGroup
			testPostPriceListByExternalReferenceCodePriceListAccountGroup_addPriceListAccountGroup(
				PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPage()
		throws Exception {

		Page<PriceListAccountGroup> page =
			priceListAccountGroupResource.
				getPriceListIdPriceListAccountGroupsPage(
					testGetPriceListIdPriceListAccountGroupsPage_getId(),
					RandomTestUtil.randomString(), null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceListAccountGroupsPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceListAccountGroupsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceListAccountGroup irrelevantPriceListAccountGroup =
				testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
					irrelevantId, randomIrrelevantPriceListAccountGroup());

			page =
				priceListAccountGroupResource.
					getPriceListIdPriceListAccountGroupsPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListAccountGroup),
				(List<PriceListAccountGroup>)page.getItems());
			assertValid(page);
		}

		PriceListAccountGroup priceListAccountGroup1 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup2 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		page =
			priceListAccountGroupResource.
				getPriceListIdPriceListAccountGroupsPage(
					id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListAccountGroup1, priceListAccountGroup2),
			(List<PriceListAccountGroup>)page.getItems());
		assertValid(page);

		priceListAccountGroupResource.deletePriceListAccountGroup(
			priceListAccountGroup1.getId());

		priceListAccountGroupResource.deletePriceListAccountGroup(
			priceListAccountGroup2.getId());
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountGroupsPage_getId();

		PriceListAccountGroup priceListAccountGroup1 =
			randomPriceListAccountGroup();

		priceListAccountGroup1 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, priceListAccountGroup1);

		for (EntityField entityField : entityFields) {
			Page<PriceListAccountGroup> page =
				priceListAccountGroupResource.
					getPriceListIdPriceListAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "between", priceListAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListAccountGroup1),
				(List<PriceListAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountGroupsPage_getId();

		PriceListAccountGroup priceListAccountGroup1 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListAccountGroup priceListAccountGroup2 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		for (EntityField entityField : entityFields) {
			Page<PriceListAccountGroup> page =
				priceListAccountGroupResource.
					getPriceListIdPriceListAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "eq", priceListAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListAccountGroup1),
				(List<PriceListAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceListAccountGroupsPage_getId();

		PriceListAccountGroup priceListAccountGroup1 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup2 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		PriceListAccountGroup priceListAccountGroup3 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, randomPriceListAccountGroup());

		Page<PriceListAccountGroup> page1 =
			priceListAccountGroupResource.
				getPriceListIdPriceListAccountGroupsPage(
					id, null, null, Pagination.of(1, 2), null);

		List<PriceListAccountGroup> priceListAccountGroups1 =
			(List<PriceListAccountGroup>)page1.getItems();

		Assert.assertEquals(
			priceListAccountGroups1.toString(), 2,
			priceListAccountGroups1.size());

		Page<PriceListAccountGroup> page2 =
			priceListAccountGroupResource.
				getPriceListIdPriceListAccountGroupsPage(
					id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListAccountGroup> priceListAccountGroups2 =
			(List<PriceListAccountGroup>)page2.getItems();

		Assert.assertEquals(
			priceListAccountGroups2.toString(), 1,
			priceListAccountGroups2.size());

		Page<PriceListAccountGroup> page3 =
			priceListAccountGroupResource.
				getPriceListIdPriceListAccountGroupsPage(
					id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListAccountGroup1, priceListAccountGroup2,
				priceListAccountGroup3),
			(List<PriceListAccountGroup>)page3.getItems());
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithSortDateTime()
		throws Exception {

		testGetPriceListIdPriceListAccountGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceListAccountGroup1, priceListAccountGroup2) -> {
				BeanUtils.setProperty(
					priceListAccountGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithSortInteger()
		throws Exception {

		testGetPriceListIdPriceListAccountGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceListAccountGroup1, priceListAccountGroup2) -> {
				BeanUtils.setProperty(
					priceListAccountGroup1, entityField.getName(), 0);
				BeanUtils.setProperty(
					priceListAccountGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListIdPriceListAccountGroupsPageWithSortString()
		throws Exception {

		testGetPriceListIdPriceListAccountGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceListAccountGroup1, priceListAccountGroup2) -> {
				Class<?> clazz = priceListAccountGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceListAccountGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceListAccountGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceListAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceListAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceListAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceListAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListIdPriceListAccountGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceListAccountGroup, PriceListAccountGroup,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountGroupsPage_getId();

		PriceListAccountGroup priceListAccountGroup1 =
			randomPriceListAccountGroup();
		PriceListAccountGroup priceListAccountGroup2 =
			randomPriceListAccountGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceListAccountGroup1, priceListAccountGroup2);
		}

		priceListAccountGroup1 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, priceListAccountGroup1);

		priceListAccountGroup2 =
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				id, priceListAccountGroup2);

		for (EntityField entityField : entityFields) {
			Page<PriceListAccountGroup> ascPage =
				priceListAccountGroupResource.
					getPriceListIdPriceListAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceListAccountGroup1, priceListAccountGroup2),
				(List<PriceListAccountGroup>)ascPage.getItems());

			Page<PriceListAccountGroup> descPage =
				priceListAccountGroupResource.
					getPriceListIdPriceListAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceListAccountGroup2, priceListAccountGroup1),
				(List<PriceListAccountGroup>)descPage.getItems());
		}
	}

	protected PriceListAccountGroup
			testGetPriceListIdPriceListAccountGroupsPage_addPriceListAccountGroup(
				Long id, PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListAccountGroupsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetPriceListIdPriceListAccountGroupsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceListAccountGroup() throws Exception {
		PriceListAccountGroup randomPriceListAccountGroup =
			randomPriceListAccountGroup();

		PriceListAccountGroup postPriceListAccountGroup =
			testPostPriceListIdPriceListAccountGroup_addPriceListAccountGroup(
				randomPriceListAccountGroup);

		assertEquals(randomPriceListAccountGroup, postPriceListAccountGroup);
		assertValid(postPriceListAccountGroup);
	}

	protected PriceListAccountGroup
			testPostPriceListIdPriceListAccountGroup_addPriceListAccountGroup(
				PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceListAccountGroup
			testGraphQLPriceListAccountGroup_addPriceListAccountGroup()
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
		PriceListAccountGroup priceListAccountGroup1,
		PriceListAccountGroup priceListAccountGroup2) {

		Assert.assertTrue(
			priceListAccountGroup1 + " does not equal " +
				priceListAccountGroup2,
			equals(priceListAccountGroup1, priceListAccountGroup2));
	}

	protected void assertEquals(
		List<PriceListAccountGroup> priceListAccountGroups1,
		List<PriceListAccountGroup> priceListAccountGroups2) {

		Assert.assertEquals(
			priceListAccountGroups1.size(), priceListAccountGroups2.size());

		for (int i = 0; i < priceListAccountGroups1.size(); i++) {
			PriceListAccountGroup priceListAccountGroup1 =
				priceListAccountGroups1.get(i);
			PriceListAccountGroup priceListAccountGroup2 =
				priceListAccountGroups2.get(i);

			assertEquals(priceListAccountGroup1, priceListAccountGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceListAccountGroup> priceListAccountGroups1,
		List<PriceListAccountGroup> priceListAccountGroups2) {

		Assert.assertEquals(
			priceListAccountGroups1.size(), priceListAccountGroups2.size());

		for (PriceListAccountGroup priceListAccountGroup1 :
				priceListAccountGroups1) {

			boolean contains = false;

			for (PriceListAccountGroup priceListAccountGroup2 :
					priceListAccountGroups2) {

				if (equals(priceListAccountGroup1, priceListAccountGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceListAccountGroups2 + " does not contain " +
					priceListAccountGroup1,
				contains);
		}
	}

	protected void assertValid(PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		boolean valid = true;

		if (priceListAccountGroup.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (priceListAccountGroup.getAccountGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListAccountGroup.
						getAccountGroupExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (priceListAccountGroup.getAccountGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceListAccountGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (priceListAccountGroup.getOrder() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListAccountGroup.getPriceListExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceListAccountGroup.getPriceListId() == null) {
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

	protected void assertValid(Page<PriceListAccountGroup> page) {
		boolean valid = false;

		java.util.Collection<PriceListAccountGroup> priceListAccountGroups =
			page.getItems();

		int size = priceListAccountGroups.size();

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
						PriceListAccountGroup.class)) {

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
		PriceListAccountGroup priceListAccountGroup1,
		PriceListAccountGroup priceListAccountGroup2) {

		if (priceListAccountGroup1 == priceListAccountGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccountGroup1.getAccountGroup(),
						priceListAccountGroup2.getAccountGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListAccountGroup1.
							getAccountGroupExternalReferenceCode(),
						priceListAccountGroup2.
							getAccountGroupExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccountGroup1.getAccountGroupId(),
						priceListAccountGroup2.getAccountGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceListAccountGroup1.getActions(),
						(Map)priceListAccountGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccountGroup1.getId(),
						priceListAccountGroup2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccountGroup1.getOrder(),
						priceListAccountGroup2.getOrder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListAccountGroup1.
							getPriceListExternalReferenceCode(),
						priceListAccountGroup2.
							getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccountGroup1.getPriceListId(),
						priceListAccountGroup2.getPriceListId())) {

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

		if (!(_priceListAccountGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceListAccountGroupResource;

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
		PriceListAccountGroup priceListAccountGroup) {

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
					priceListAccountGroup.
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

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("order")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceListAccountGroup.getPriceListExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListId")) {
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

	protected PriceListAccountGroup randomPriceListAccountGroup()
		throws Exception {

		return new PriceListAccountGroup() {
			{
				accountGroupExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountGroupId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				order = RandomTestUtil.randomInt();
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceListAccountGroup randomIrrelevantPriceListAccountGroup()
		throws Exception {

		PriceListAccountGroup randomIrrelevantPriceListAccountGroup =
			randomPriceListAccountGroup();

		return randomIrrelevantPriceListAccountGroup;
	}

	protected PriceListAccountGroup randomPatchPriceListAccountGroup()
		throws Exception {

		return randomPriceListAccountGroup();
	}

	protected PriceListAccountGroupResource priceListAccountGroupResource;
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
		BasePriceListAccountGroupResourceTestCase.class);

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
		PriceListAccountGroupResource _priceListAccountGroupResource;

}