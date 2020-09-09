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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceModifierProductResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceModifierProductSerDes;
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
public abstract class BasePriceModifierProductResourceTestCase {

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

		_priceModifierProductResource.setContextCompany(testCompany);

		PriceModifierProductResource.Builder builder =
			PriceModifierProductResource.builder();

		priceModifierProductResource = builder.authentication(
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

		PriceModifierProduct priceModifierProduct1 =
			randomPriceModifierProduct();

		String json = objectMapper.writeValueAsString(priceModifierProduct1);

		PriceModifierProduct priceModifierProduct2 =
			PriceModifierProductSerDes.toDTO(json);

		Assert.assertTrue(equals(priceModifierProduct1, priceModifierProduct2));
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

		PriceModifierProduct priceModifierProduct =
			randomPriceModifierProduct();

		String json1 = objectMapper.writeValueAsString(priceModifierProduct);
		String json2 = PriceModifierProductSerDes.toJSON(priceModifierProduct);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceModifierProduct priceModifierProduct =
			randomPriceModifierProduct();

		priceModifierProduct.setPriceModifierExternalReferenceCode(regex);
		priceModifierProduct.setProductExternalReferenceCode(regex);

		String json = PriceModifierProductSerDes.toJSON(priceModifierProduct);

		Assert.assertFalse(json.contains(regex));

		priceModifierProduct = PriceModifierProductSerDes.toDTO(json);

		Assert.assertEquals(
			regex,
			priceModifierProduct.getPriceModifierExternalReferenceCode());
		Assert.assertEquals(
			regex, priceModifierProduct.getProductExternalReferenceCode());
	}

	@Test
	public void testDeletePriceModifierProduct() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifierProduct priceModifierProduct =
			testDeletePriceModifierProduct_addPriceModifierProduct();

		assertHttpResponseStatusCode(
			204,
			priceModifierProductResource.deletePriceModifierProductHttpResponse(
				priceModifierProduct.getId()));
	}

	protected PriceModifierProduct
			testDeletePriceModifierProduct_addPriceModifierProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceModifierProduct() throws Exception {
		PriceModifierProduct priceModifierProduct =
			testGraphQLPriceModifierProduct_addPriceModifierProduct();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceModifierProduct",
						new HashMap<String, Object>() {
							{
								put("id", priceModifierProduct.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceModifierProduct"));
	}

	@Test
	public void testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage()
		throws Exception {

		Page<PriceModifierProduct> page =
			priceModifierProductResource.
				getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
					testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceModifierProduct irrelevantPriceModifierProduct =
				testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceModifierProduct());

			page =
				priceModifierProductResource.
					getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifierProduct),
				(List<PriceModifierProduct>)page.getItems());
			assertValid(page);
		}

		PriceModifierProduct priceModifierProduct1 =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				externalReferenceCode, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct2 =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				externalReferenceCode, randomPriceModifierProduct());

		page =
			priceModifierProductResource.
				getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifierProduct1, priceModifierProduct2),
			(List<PriceModifierProduct>)page.getItems());
		assertValid(page);

		priceModifierProductResource.deletePriceModifierProduct(
			priceModifierProduct1.getId());

		priceModifierProductResource.deletePriceModifierProduct(
			priceModifierProduct2.getId());
	}

	@Test
	public void testGetPriceModifierByExternalReferenceCodePriceModifierProductsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getExternalReferenceCode();

		PriceModifierProduct priceModifierProduct1 =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				externalReferenceCode, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct2 =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				externalReferenceCode, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct3 =
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				externalReferenceCode, randomPriceModifierProduct());

		Page<PriceModifierProduct> page1 =
			priceModifierProductResource.
				getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceModifierProduct> priceModifierProducts1 =
			(List<PriceModifierProduct>)page1.getItems();

		Assert.assertEquals(
			priceModifierProducts1.toString(), 2,
			priceModifierProducts1.size());

		Page<PriceModifierProduct> page2 =
			priceModifierProductResource.
				getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifierProduct> priceModifierProducts2 =
			(List<PriceModifierProduct>)page2.getItems();

		Assert.assertEquals(
			priceModifierProducts2.toString(), 1,
			priceModifierProducts2.size());

		Page<PriceModifierProduct> page3 =
			priceModifierProductResource.
				getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceModifierProduct1, priceModifierProduct2,
				priceModifierProduct3),
			(List<PriceModifierProduct>)page3.getItems());
	}

	protected PriceModifierProduct
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_addPriceModifierProduct(
				String externalReferenceCode,
				PriceModifierProduct priceModifierProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceModifierByExternalReferenceCodePriceModifierProductsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceModifierByExternalReferenceCodePriceModifierProduct()
		throws Exception {

		PriceModifierProduct randomPriceModifierProduct =
			randomPriceModifierProduct();

		PriceModifierProduct postPriceModifierProduct =
			testPostPriceModifierByExternalReferenceCodePriceModifierProduct_addPriceModifierProduct(
				randomPriceModifierProduct);

		assertEquals(randomPriceModifierProduct, postPriceModifierProduct);
		assertValid(postPriceModifierProduct);
	}

	protected PriceModifierProduct
			testPostPriceModifierByExternalReferenceCodePriceModifierProduct_addPriceModifierProduct(
				PriceModifierProduct priceModifierProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPage()
		throws Exception {

		Page<PriceModifierProduct> page =
			priceModifierProductResource.
				getPriceModifierIdPriceModifierProductsPage(
					testGetPriceModifierIdPriceModifierProductsPage_getId(),
					RandomTestUtil.randomString(), null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceModifierIdPriceModifierProductsPage_getId();
		Long irrelevantId =
			testGetPriceModifierIdPriceModifierProductsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceModifierProduct irrelevantPriceModifierProduct =
				testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
					irrelevantId, randomIrrelevantPriceModifierProduct());

			page =
				priceModifierProductResource.
					getPriceModifierIdPriceModifierProductsPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifierProduct),
				(List<PriceModifierProduct>)page.getItems());
			assertValid(page);
		}

		PriceModifierProduct priceModifierProduct1 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct2 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		page =
			priceModifierProductResource.
				getPriceModifierIdPriceModifierProductsPage(
					id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifierProduct1, priceModifierProduct2),
			(List<PriceModifierProduct>)page.getItems());
		assertValid(page);

		priceModifierProductResource.deletePriceModifierProduct(
			priceModifierProduct1.getId());

		priceModifierProductResource.deletePriceModifierProduct(
			priceModifierProduct2.getId());
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierProductsPage_getId();

		PriceModifierProduct priceModifierProduct1 =
			randomPriceModifierProduct();

		priceModifierProduct1 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, priceModifierProduct1);

		for (EntityField entityField : entityFields) {
			Page<PriceModifierProduct> page =
				priceModifierProductResource.
					getPriceModifierIdPriceModifierProductsPage(
						id, null,
						getFilterString(
							entityField, "between", priceModifierProduct1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifierProduct1),
				(List<PriceModifierProduct>)page.getItems());
		}
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierProductsPage_getId();

		PriceModifierProduct priceModifierProduct1 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifierProduct priceModifierProduct2 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		for (EntityField entityField : entityFields) {
			Page<PriceModifierProduct> page =
				priceModifierProductResource.
					getPriceModifierIdPriceModifierProductsPage(
						id, null,
						getFilterString(
							entityField, "eq", priceModifierProduct1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifierProduct1),
				(List<PriceModifierProduct>)page.getItems());
		}
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithPagination()
		throws Exception {

		Long id = testGetPriceModifierIdPriceModifierProductsPage_getId();

		PriceModifierProduct priceModifierProduct1 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct2 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		PriceModifierProduct priceModifierProduct3 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, randomPriceModifierProduct());

		Page<PriceModifierProduct> page1 =
			priceModifierProductResource.
				getPriceModifierIdPriceModifierProductsPage(
					id, null, null, Pagination.of(1, 2), null);

		List<PriceModifierProduct> priceModifierProducts1 =
			(List<PriceModifierProduct>)page1.getItems();

		Assert.assertEquals(
			priceModifierProducts1.toString(), 2,
			priceModifierProducts1.size());

		Page<PriceModifierProduct> page2 =
			priceModifierProductResource.
				getPriceModifierIdPriceModifierProductsPage(
					id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifierProduct> priceModifierProducts2 =
			(List<PriceModifierProduct>)page2.getItems();

		Assert.assertEquals(
			priceModifierProducts2.toString(), 1,
			priceModifierProducts2.size());

		Page<PriceModifierProduct> page3 =
			priceModifierProductResource.
				getPriceModifierIdPriceModifierProductsPage(
					id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceModifierProduct1, priceModifierProduct2,
				priceModifierProduct3),
			(List<PriceModifierProduct>)page3.getItems());
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithSortDateTime()
		throws Exception {

		testGetPriceModifierIdPriceModifierProductsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceModifierProduct1, priceModifierProduct2) -> {
				BeanUtils.setProperty(
					priceModifierProduct1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithSortInteger()
		throws Exception {

		testGetPriceModifierIdPriceModifierProductsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceModifierProduct1, priceModifierProduct2) -> {
				BeanUtils.setProperty(
					priceModifierProduct1, entityField.getName(), 0);
				BeanUtils.setProperty(
					priceModifierProduct2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceModifierIdPriceModifierProductsPageWithSortString()
		throws Exception {

		testGetPriceModifierIdPriceModifierProductsPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceModifierProduct1, priceModifierProduct2) -> {
				Class<?> clazz = priceModifierProduct1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceModifierProduct1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceModifierProduct2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceModifierProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceModifierProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceModifierProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceModifierProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceModifierIdPriceModifierProductsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceModifierProduct, PriceModifierProduct,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceModifierIdPriceModifierProductsPage_getId();

		PriceModifierProduct priceModifierProduct1 =
			randomPriceModifierProduct();
		PriceModifierProduct priceModifierProduct2 =
			randomPriceModifierProduct();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceModifierProduct1, priceModifierProduct2);
		}

		priceModifierProduct1 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, priceModifierProduct1);

		priceModifierProduct2 =
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				id, priceModifierProduct2);

		for (EntityField entityField : entityFields) {
			Page<PriceModifierProduct> ascPage =
				priceModifierProductResource.
					getPriceModifierIdPriceModifierProductsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceModifierProduct1, priceModifierProduct2),
				(List<PriceModifierProduct>)ascPage.getItems());

			Page<PriceModifierProduct> descPage =
				priceModifierProductResource.
					getPriceModifierIdPriceModifierProductsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceModifierProduct2, priceModifierProduct1),
				(List<PriceModifierProduct>)descPage.getItems());
		}
	}

	protected PriceModifierProduct
			testGetPriceModifierIdPriceModifierProductsPage_addPriceModifierProduct(
				Long id, PriceModifierProduct priceModifierProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceModifierIdPriceModifierProductsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetPriceModifierIdPriceModifierProductsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceModifierIdPriceModifierProduct() throws Exception {
		PriceModifierProduct randomPriceModifierProduct =
			randomPriceModifierProduct();

		PriceModifierProduct postPriceModifierProduct =
			testPostPriceModifierIdPriceModifierProduct_addPriceModifierProduct(
				randomPriceModifierProduct);

		assertEquals(randomPriceModifierProduct, postPriceModifierProduct);
		assertValid(postPriceModifierProduct);
	}

	protected PriceModifierProduct
			testPostPriceModifierIdPriceModifierProduct_addPriceModifierProduct(
				PriceModifierProduct priceModifierProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceModifierProduct
			testGraphQLPriceModifierProduct_addPriceModifierProduct()
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
		PriceModifierProduct priceModifierProduct1,
		PriceModifierProduct priceModifierProduct2) {

		Assert.assertTrue(
			priceModifierProduct1 + " does not equal " + priceModifierProduct2,
			equals(priceModifierProduct1, priceModifierProduct2));
	}

	protected void assertEquals(
		List<PriceModifierProduct> priceModifierProducts1,
		List<PriceModifierProduct> priceModifierProducts2) {

		Assert.assertEquals(
			priceModifierProducts1.size(), priceModifierProducts2.size());

		for (int i = 0; i < priceModifierProducts1.size(); i++) {
			PriceModifierProduct priceModifierProduct1 =
				priceModifierProducts1.get(i);
			PriceModifierProduct priceModifierProduct2 =
				priceModifierProducts2.get(i);

			assertEquals(priceModifierProduct1, priceModifierProduct2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceModifierProduct> priceModifierProducts1,
		List<PriceModifierProduct> priceModifierProducts2) {

		Assert.assertEquals(
			priceModifierProducts1.size(), priceModifierProducts2.size());

		for (PriceModifierProduct priceModifierProduct1 :
				priceModifierProducts1) {

			boolean contains = false;

			for (PriceModifierProduct priceModifierProduct2 :
					priceModifierProducts2) {

				if (equals(priceModifierProduct1, priceModifierProduct2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceModifierProducts2 + " does not contain " +
					priceModifierProduct1,
				contains);
		}
	}

	protected void assertValid(PriceModifierProduct priceModifierProduct)
		throws Exception {

		boolean valid = true;

		if (priceModifierProduct.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceModifierProduct.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceModifierProduct.
						getPriceModifierExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceModifierId", additionalAssertFieldName)) {
				if (priceModifierProduct.getPriceModifierId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("product", additionalAssertFieldName)) {
				if (priceModifierProduct.getProduct() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceModifierProduct.getProductExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (priceModifierProduct.getProductId() == null) {
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

	protected void assertValid(Page<PriceModifierProduct> page) {
		boolean valid = false;

		java.util.Collection<PriceModifierProduct> priceModifierProducts =
			page.getItems();

		int size = priceModifierProducts.size();

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
						PriceModifierProduct.class)) {

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
		PriceModifierProduct priceModifierProduct1,
		PriceModifierProduct priceModifierProduct2) {

		if (priceModifierProduct1 == priceModifierProduct2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceModifierProduct1.getActions(),
						(Map)priceModifierProduct2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierProduct1.getId(),
						priceModifierProduct2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifierProduct1.
							getPriceModifierExternalReferenceCode(),
						priceModifierProduct2.
							getPriceModifierExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceModifierId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierProduct1.getPriceModifierId(),
						priceModifierProduct2.getPriceModifierId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("product", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierProduct1.getProduct(),
						priceModifierProduct2.getProduct())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifierProduct1.getProductExternalReferenceCode(),
						priceModifierProduct2.
							getProductExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifierProduct1.getProductId(),
						priceModifierProduct2.getProductId())) {

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

		if (!(_priceModifierProductResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceModifierProductResource;

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
		PriceModifierProduct priceModifierProduct) {

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

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifierExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceModifierProduct.
						getPriceModifierExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceModifierId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("product")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceModifierProduct.getProductExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("productId")) {
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

	protected PriceModifierProduct randomPriceModifierProduct()
		throws Exception {

		return new PriceModifierProduct() {
			{
				id = RandomTestUtil.randomLong();
				priceModifierExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceModifierId = RandomTestUtil.randomLong();
				productExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				productId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceModifierProduct randomIrrelevantPriceModifierProduct()
		throws Exception {

		PriceModifierProduct randomIrrelevantPriceModifierProduct =
			randomPriceModifierProduct();

		return randomIrrelevantPriceModifierProduct;
	}

	protected PriceModifierProduct randomPatchPriceModifierProduct()
		throws Exception {

		return randomPriceModifierProduct();
	}

	protected PriceModifierProductResource priceModifierProductResource;
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
		BasePriceModifierProductResourceTestCase.class);

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
		PriceModifierProductResource _priceModifierProductResource;

}