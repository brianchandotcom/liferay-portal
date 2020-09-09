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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceModifierCategory;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceModifierCategoryResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceModifierCategorySerDes;
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
public abstract class BasePriceModifierCategoryResourceTestCase {

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

		_priceModifierCategoryResource.setContextCompany(testCompany);

		PriceModifierCategoryResource.Builder builder =
			PriceModifierCategoryResource.builder();

		priceModifierCategoryResource = builder.authentication(
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

		PriceModifierCategory priceModifierCategory1 =
			randomPriceModifierCategory();

		String json = objectMapper.writeValueAsString(priceModifierCategory1);

		PriceModifierCategory priceModifierCategory2 =
			PriceModifierCategorySerDes.toDTO(json);

		Assert.assertTrue(
			equals(priceModifierCategory1, priceModifierCategory2));
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

		PriceModifierCategory priceModifierCategory =
			randomPriceModifierCategory();

		String json1 = objectMapper.writeValueAsString(priceModifierCategory);
		String json2 = PriceModifierCategorySerDes.toJSON(
			priceModifierCategory);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceModifierCategory priceModifierCategory =
			randomPriceModifierCategory();

		priceModifierCategory.setCategoryExternalReferenceCode(regex);
		priceModifierCategory.setPriceModifierExternalReferenceCode(regex);

		String json = PriceModifierCategorySerDes.toJSON(priceModifierCategory);

		Assert.assertFalse(json.contains(regex));

		priceModifierCategory = PriceModifierCategorySerDes.toDTO(json);

		Assert.assertEquals(
			regex, priceModifierCategory.getCategoryExternalReferenceCode());
		Assert.assertEquals(
			regex,
			priceModifierCategory.getPriceModifierExternalReferenceCode());
	}

	@Test
	public void testDeletePriceModifierCategory() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifierCategory priceModifierCategory =
			testDeletePriceModifierCategory_addPriceModifierCategory();

		assertHttpResponseStatusCode(
			204,
			priceModifierCategoryResource.
				deletePriceModifierCategoryHttpResponse(
					priceModifierCategory.getId()));
	}

	protected PriceModifierCategory
			testDeletePriceModifierCategory_addPriceModifierCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceModifierCategory() throws Exception {
		PriceModifierCategory priceModifierCategory =
			testGraphQLPriceModifierCategory_addPriceModifierCategory();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceModifierCategory",
						new HashMap<String, Object>() {
							{
								put("id", priceModifierCategory.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceModifierCategory"));
	}

	@Test
	public void testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage()
		throws Exception {

		Page<PriceModifierCategory> page =
			priceModifierCategoryResource.
				getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
					testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceModifierCategory irrelevantPriceModifierCategory =
				testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceModifierCategory());

			page =
				priceModifierCategoryResource.
					getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifierCategory),
				(List<PriceModifierCategory>)page.getItems());
			assertValid(page);
		}

		PriceModifierCategory priceModifierCategory1 =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				externalReferenceCode, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory2 =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				externalReferenceCode, randomPriceModifierCategory());

		page =
			priceModifierCategoryResource.
				getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifierCategory1, priceModifierCategory2),
			(List<PriceModifierCategory>)page.getItems());
		assertValid(page);

		priceModifierCategoryResource.deletePriceModifierCategory(
			priceModifierCategory1.getId());

		priceModifierCategoryResource.deletePriceModifierCategory(
			priceModifierCategory2.getId());
	}

	@Test
	public void testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getExternalReferenceCode();

		PriceModifierCategory priceModifierCategory1 =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				externalReferenceCode, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory2 =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				externalReferenceCode, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory3 =
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				externalReferenceCode, randomPriceModifierCategory());

		Page<PriceModifierCategory> page1 =
			priceModifierCategoryResource.
				getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceModifierCategory> priceModifierCategories1 =
			(List<PriceModifierCategory>)page1.getItems();

		Assert.assertEquals(
			priceModifierCategories1.toString(), 2,
			priceModifierCategories1.size());

		Page<PriceModifierCategory> page2 =
			priceModifierCategoryResource.
				getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifierCategory> priceModifierCategories2 =
			(List<PriceModifierCategory>)page2.getItems();

		Assert.assertEquals(
			priceModifierCategories2.toString(), 1,
			priceModifierCategories2.size());

		Page<PriceModifierCategory> page3 =
			priceModifierCategoryResource.
				getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceModifierCategory1, priceModifierCategory2,
				priceModifierCategory3),
			(List<PriceModifierCategory>)page3.getItems());
	}

	protected PriceModifierCategory
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_addPriceModifierCategory(
				String externalReferenceCode,
				PriceModifierCategory priceModifierCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceModifierByExternalReferenceCodePriceModifierCategory()
		throws Exception {

		PriceModifierCategory randomPriceModifierCategory =
			randomPriceModifierCategory();

		PriceModifierCategory postPriceModifierCategory =
			testPostPriceModifierByExternalReferenceCodePriceModifierCategory_addPriceModifierCategory(
				randomPriceModifierCategory);

		assertEquals(randomPriceModifierCategory, postPriceModifierCategory);
		assertValid(postPriceModifierCategory);
	}

	protected PriceModifierCategory
			testPostPriceModifierByExternalReferenceCodePriceModifierCategory_addPriceModifierCategory(
				PriceModifierCategory priceModifierCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPage()
		throws Exception {

		Page<PriceModifierCategory> page =
			priceModifierCategoryResource.
				getPriceModifierIdPriceModifierCategoriesPage(
					testGetPriceModifierIdPriceModifierCategoriesPage_getId(),
					RandomTestUtil.randomString(), null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceModifierIdPriceModifierCategoriesPage_getId();
		Long irrelevantId =
			testGetPriceModifierIdPriceModifierCategoriesPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceModifierCategory irrelevantPriceModifierCategory =
				testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
					irrelevantId, randomIrrelevantPriceModifierCategory());

			page =
				priceModifierCategoryResource.
					getPriceModifierIdPriceModifierCategoriesPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifierCategory),
				(List<PriceModifierCategory>)page.getItems());
			assertValid(page);
		}

		PriceModifierCategory priceModifierCategory1 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory2 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		page =
			priceModifierCategoryResource.
				getPriceModifierIdPriceModifierCategoriesPage(
					id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifierCategory1, priceModifierCategory2),
			(List<PriceModifierCategory>)page.getItems());
		assertValid(page);

		priceModifierCategoryResource.deletePriceModifierCategory(
			priceModifierCategory1.getId());

		priceModifierCategoryResource.deletePriceModifierCategory(
			priceModifierCategory2.getId());
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierCategoriesPage_getId();

		PriceModifierCategory priceModifierCategory1 =
			randomPriceModifierCategory();

		priceModifierCategory1 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, priceModifierCategory1);

		for (EntityField entityField : entityFields) {
			Page<PriceModifierCategory> page =
				priceModifierCategoryResource.
					getPriceModifierIdPriceModifierCategoriesPage(
						id, null,
						getFilterString(
							entityField, "between", priceModifierCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifierCategory1),
				(List<PriceModifierCategory>)page.getItems());
		}
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierCategoriesPage_getId();

		PriceModifierCategory priceModifierCategory1 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifierCategory priceModifierCategory2 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		for (EntityField entityField : entityFields) {
			Page<PriceModifierCategory> page =
				priceModifierCategoryResource.
					getPriceModifierIdPriceModifierCategoriesPage(
						id, null,
						getFilterString(
							entityField, "eq", priceModifierCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifierCategory1),
				(List<PriceModifierCategory>)page.getItems());
		}
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithPagination()
		throws Exception {

		Long id = testGetPriceModifierIdPriceModifierCategoriesPage_getId();

		PriceModifierCategory priceModifierCategory1 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory2 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		PriceModifierCategory priceModifierCategory3 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, randomPriceModifierCategory());

		Page<PriceModifierCategory> page1 =
			priceModifierCategoryResource.
				getPriceModifierIdPriceModifierCategoriesPage(
					id, null, null, Pagination.of(1, 2), null);

		List<PriceModifierCategory> priceModifierCategories1 =
			(List<PriceModifierCategory>)page1.getItems();

		Assert.assertEquals(
			priceModifierCategories1.toString(), 2,
			priceModifierCategories1.size());

		Page<PriceModifierCategory> page2 =
			priceModifierCategoryResource.
				getPriceModifierIdPriceModifierCategoriesPage(
					id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifierCategory> priceModifierCategories2 =
			(List<PriceModifierCategory>)page2.getItems();

		Assert.assertEquals(
			priceModifierCategories2.toString(), 1,
			priceModifierCategories2.size());

		Page<PriceModifierCategory> page3 =
			priceModifierCategoryResource.
				getPriceModifierIdPriceModifierCategoriesPage(
					id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceModifierCategory1, priceModifierCategory2,
				priceModifierCategory3),
			(List<PriceModifierCategory>)page3.getItems());
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithSortDateTime()
		throws Exception {

		testGetPriceModifierIdPriceModifierCategoriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceModifierCategory1, priceModifierCategory2) -> {
				BeanUtils.setProperty(
					priceModifierCategory1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithSortInteger()
		throws Exception {

		testGetPriceModifierIdPriceModifierCategoriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceModifierCategory1, priceModifierCategory2) -> {
				BeanUtils.setProperty(
					priceModifierCategory1, entityField.getName(), 0);
				BeanUtils.setProperty(
					priceModifierCategory2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceModifierIdPriceModifierCategoriesPageWithSortString()
		throws Exception {

		testGetPriceModifierIdPriceModifierCategoriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceModifierCategory1, priceModifierCategory2) -> {
				Class<?> clazz = priceModifierCategory1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceModifierCategory1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceModifierCategory2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceModifierCategory1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceModifierCategory2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceModifierCategory1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceModifierCategory2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceModifierIdPriceModifierCategoriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceModifierCategory, PriceModifierCategory,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierCategoriesPage_getId();

		PriceModifierCategory priceModifierCategory1 =
			randomPriceModifierCategory();
		PriceModifierCategory priceModifierCategory2 =
			randomPriceModifierCategory();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceModifierCategory1, priceModifierCategory2);
		}

		priceModifierCategory1 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, priceModifierCategory1);

		priceModifierCategory2 =
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				id, priceModifierCategory2);

		for (EntityField entityField : entityFields) {
			Page<PriceModifierCategory> ascPage =
				priceModifierCategoryResource.
					getPriceModifierIdPriceModifierCategoriesPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceModifierCategory1, priceModifierCategory2),
				(List<PriceModifierCategory>)ascPage.getItems());

			Page<PriceModifierCategory> descPage =
				priceModifierCategoryResource.
					getPriceModifierIdPriceModifierCategoriesPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceModifierCategory2, priceModifierCategory1),
				(List<PriceModifierCategory>)descPage.getItems());
		}
	}

	protected PriceModifierCategory
			testGetPriceModifierIdPriceModifierCategoriesPage_addPriceModifierCategory(
				Long id, PriceModifierCategory priceModifierCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceModifierIdPriceModifierCategoriesPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetPriceModifierIdPriceModifierCategoriesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceModifierIdPriceModifierCategory()
		throws Exception {

		PriceModifierCategory randomPriceModifierCategory =
			randomPriceModifierCategory();

		PriceModifierCategory postPriceModifierCategory =
			testPostPriceModifierIdPriceModifierCategory_addPriceModifierCategory(
				randomPriceModifierCategory);

		assertEquals(randomPriceModifierCategory, postPriceModifierCategory);
		assertValid(postPriceModifierCategory);
	}

	protected PriceModifierCategory
			testPostPriceModifierIdPriceModifierCategory_addPriceModifierCategory(
				PriceModifierCategory priceModifierCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceModifierCategory
			testGraphQLPriceModifierCategory_addPriceModifierCategory()
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
		PriceModifierCategory priceModifierCategory1,
		PriceModifierCategory priceModifierCategory2) {

		Assert.assertTrue(
			priceModifierCategory1 + " does not equal " +
				priceModifierCategory2,
			equals(priceModifierCategory1, priceModifierCategory2));
	}

	protected void assertEquals(
		List<PriceModifierCategory> priceModifierCategories1,
		List<PriceModifierCategory> priceModifierCategories2) {

		Assert.assertEquals(
			priceModifierCategories1.size(), priceModifierCategories2.size());

		for (int i = 0; i < priceModifierCategories1.size(); i++) {
			PriceModifierCategory priceModifierCategory1 =
				priceModifierCategories1.get(i);
			PriceModifierCategory priceModifierCategory2 =
				priceModifierCategories2.get(i);

			assertEquals(priceModifierCategory1, priceModifierCategory2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceModifierCategory> priceModifierCategories1,
		List<PriceModifierCategory> priceModifierCategories2) {

		Assert.assertEquals(
			priceModifierCategories1.size(), priceModifierCategories2.size());

		for (PriceModifierCategory priceModifierCategory1 :
				priceModifierCategories1) {

			boolean contains = false;

			for (PriceModifierCategory priceModifierCategory2 :
					priceModifierCategories2) {

				if (equals(priceModifierCategory1, priceModifierCategory2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceModifierCategories2 + " does not contain " +
					priceModifierCategory1,
				contains);
		}
	}

	protected void assertValid(PriceModifierCategory priceModifierCategory)
		throws Exception {

		boolean valid = true;

		if (priceModifierCategory.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceModifierCategory.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (priceModifierCategory.getCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"categoryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceModifierCategory.getCategoryExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("categoryId", additionalAssertFieldName)) {
				if (priceModifierCategory.getCategoryId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceModifierCategory.
						getPriceModifierExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceModifierId", additionalAssertFieldName)) {
				if (priceModifierCategory.getPriceModifierId() == null) {
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

	protected void assertValid(Page<PriceModifierCategory> page) {
		boolean valid = false;

		java.util.Collection<PriceModifierCategory> priceModifierCategories =
			page.getItems();

		int size = priceModifierCategories.size();

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
						PriceModifierCategory.class)) {

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
		PriceModifierCategory priceModifierCategory1,
		PriceModifierCategory priceModifierCategory2) {

		if (priceModifierCategory1 == priceModifierCategory2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceModifierCategory1.getActions(),
						(Map)priceModifierCategory2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierCategory1.getCategory(),
						priceModifierCategory2.getCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"categoryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifierCategory1.
							getCategoryExternalReferenceCode(),
						priceModifierCategory2.
							getCategoryExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("categoryId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierCategory1.getCategoryId(),
						priceModifierCategory2.getCategoryId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierCategory1.getId(),
						priceModifierCategory2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifierCategory1.
							getPriceModifierExternalReferenceCode(),
						priceModifierCategory2.
							getPriceModifierExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceModifierId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierCategory1.getPriceModifierId(),
						priceModifierCategory2.getPriceModifierId())) {

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

		if (!(_priceModifierCategoryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceModifierCategoryResource;

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
		PriceModifierCategory priceModifierCategory) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("category")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categoryExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceModifierCategory.getCategoryExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("categoryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifierExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceModifierCategory.
						getPriceModifierExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceModifierId")) {
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

	protected PriceModifierCategory randomPriceModifierCategory()
		throws Exception {

		return new PriceModifierCategory() {
			{
				categoryExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				categoryId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				priceModifierExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceModifierId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceModifierCategory randomIrrelevantPriceModifierCategory()
		throws Exception {

		PriceModifierCategory randomIrrelevantPriceModifierCategory =
			randomPriceModifierCategory();

		return randomIrrelevantPriceModifierCategory;
	}

	protected PriceModifierCategory randomPatchPriceModifierCategory()
		throws Exception {

		return randomPriceModifierCategory();
	}

	protected PriceModifierCategoryResource priceModifierCategoryResource;
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
		BasePriceModifierCategoryResourceTestCase.class);

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
		PriceModifierCategoryResource _priceModifierCategoryResource;

}