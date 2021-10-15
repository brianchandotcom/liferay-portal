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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.MappedProductResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.MappedProductSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
public abstract class BaseMappedProductResourceTestCase {

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

		_mappedProductResource.setContextCompany(testCompany);

		MappedProductResource.Builder builder = MappedProductResource.builder();

		mappedProductResource = builder.authentication(
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

		MappedProduct mappedProduct1 = randomMappedProduct();

		String json = objectMapper.writeValueAsString(mappedProduct1);

		MappedProduct mappedProduct2 = MappedProductSerDes.toDTO(json);

		Assert.assertTrue(equals(mappedProduct1, mappedProduct2));
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

		MappedProduct mappedProduct = randomMappedProduct();

		String json1 = objectMapper.writeValueAsString(mappedProduct);
		String json2 = MappedProductSerDes.toJSON(mappedProduct);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MappedProduct mappedProduct = randomMappedProduct();

		mappedProduct.setProductExternalReferenceCode(regex);
		mappedProduct.setSequence(regex);
		mappedProduct.setSku(regex);
		mappedProduct.setSkuExternalReferenceCode(regex);

		String json = MappedProductSerDes.toJSON(mappedProduct);

		Assert.assertFalse(json.contains(regex));

		mappedProduct = MappedProductSerDes.toDTO(json);

		Assert.assertEquals(
			regex, mappedProduct.getProductExternalReferenceCode());
		Assert.assertEquals(regex, mappedProduct.getSequence());
		Assert.assertEquals(regex, mappedProduct.getSku());
		Assert.assertEquals(regex, mappedProduct.getSkuExternalReferenceCode());
	}

	@Test
	public void testDeleteMappedProduct() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MappedProduct mappedProduct =
			testDeleteMappedProduct_addMappedProduct();

		assertHttpResponseStatusCode(
			204,
			mappedProductResource.deleteMappedProductHttpResponse(
				mappedProduct.getId()));
	}

	protected MappedProduct testDeleteMappedProduct_addMappedProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteMappedProduct() throws Exception {
		MappedProduct mappedProduct =
			testGraphQLMappedProduct_addMappedProduct();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteMappedProduct",
						new HashMap<String, Object>() {
							{
								put("mappedProductId", mappedProduct.getId());
							}
						})),
				"JSONObject/data", "Object/deleteMappedProduct"));
	}

	@Test
	public void testPatchMappedProduct() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductsPage()
		throws Exception {

		String externalReferenceCode =
			testGetProductByExternalReferenceCodeMappedProductsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetProductByExternalReferenceCodeMappedProductsPage_getIrrelevantExternalReferenceCode();

		Page<MappedProduct> page =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductsPage(
					externalReferenceCode, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			MappedProduct irrelevantMappedProduct =
				testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
					irrelevantExternalReferenceCode,
					randomIrrelevantMappedProduct());

			page =
				mappedProductResource.
					getProductByExternalReferenceCodeMappedProductsPage(
						irrelevantExternalReferenceCode, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMappedProduct),
				(List<MappedProduct>)page.getItems());
			assertValid(page);
		}

		MappedProduct mappedProduct1 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, randomMappedProduct());

		page =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductsPage(
					externalReferenceCode, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2),
			(List<MappedProduct>)page.getItems());
		assertValid(page);

		mappedProductResource.deleteMappedProduct(mappedProduct1.getId());

		mappedProductResource.deleteMappedProduct(mappedProduct2.getId());
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetProductByExternalReferenceCodeMappedProductsPage_getExternalReferenceCode();

		MappedProduct mappedProduct1 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, randomMappedProduct());

		MappedProduct mappedProduct3 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, randomMappedProduct());

		Page<MappedProduct> page1 =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductsPage(
					externalReferenceCode, null, Pagination.of(1, 2), null);

		List<MappedProduct> mappedProducts1 =
			(List<MappedProduct>)page1.getItems();

		Assert.assertEquals(
			mappedProducts1.toString(), 2, mappedProducts1.size());

		Page<MappedProduct> page2 =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductsPage(
					externalReferenceCode, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MappedProduct> mappedProducts2 =
			(List<MappedProduct>)page2.getItems();

		Assert.assertEquals(
			mappedProducts2.toString(), 1, mappedProducts2.size());

		Page<MappedProduct> page3 =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductsPage(
					externalReferenceCode, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2, mappedProduct3),
			(List<MappedProduct>)page3.getItems());
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductsPageWithSortDateTime()
		throws Exception {

		testGetProductByExternalReferenceCodeMappedProductsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(
					mappedProduct1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductsPageWithSortInteger()
		throws Exception {

		testGetProductByExternalReferenceCodeMappedProductsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(mappedProduct1, entityField.getName(), 0);
				BeanUtils.setProperty(mappedProduct2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductsPageWithSortString()
		throws Exception {

		testGetProductByExternalReferenceCodeMappedProductsPageWithSort(
			EntityField.Type.STRING,
			(entityField, mappedProduct1, mappedProduct2) -> {
				Class<?> clazz = mappedProduct1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetProductByExternalReferenceCodeMappedProductsPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, MappedProduct, MappedProduct, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetProductByExternalReferenceCodeMappedProductsPage_getExternalReferenceCode();

		MappedProduct mappedProduct1 = randomMappedProduct();
		MappedProduct mappedProduct2 = randomMappedProduct();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, mappedProduct1, mappedProduct2);
		}

		mappedProduct1 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, mappedProduct1);

		mappedProduct2 =
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				externalReferenceCode, mappedProduct2);

		for (EntityField entityField : entityFields) {
			Page<MappedProduct> ascPage =
				mappedProductResource.
					getProductByExternalReferenceCodeMappedProductsPage(
						externalReferenceCode, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(mappedProduct1, mappedProduct2),
				(List<MappedProduct>)ascPage.getItems());

			Page<MappedProduct> descPage =
				mappedProductResource.
					getProductByExternalReferenceCodeMappedProductsPage(
						externalReferenceCode, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(mappedProduct2, mappedProduct1),
				(List<MappedProduct>)descPage.getItems());
		}
	}

	protected MappedProduct
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				String externalReferenceCode, MappedProduct mappedProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductByExternalReferenceCodeMappedProductsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductByExternalReferenceCodeMappedProductsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProductByExternalReferenceCodeMappedProduct()
		throws Exception {

		MappedProduct randomMappedProduct = randomMappedProduct();

		MappedProduct postMappedProduct =
			testPostProductByExternalReferenceCodeMappedProduct_addMappedProduct(
				randomMappedProduct);

		assertEquals(randomMappedProduct, postMappedProduct);
		assertValid(postMappedProduct);
	}

	protected MappedProduct
			testPostProductByExternalReferenceCodeMappedProduct_addMappedProduct(
				MappedProduct mappedProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductByExternalReferenceCodeMappedProductBySequence()
		throws Exception {

		MappedProduct postMappedProduct =
			testGetProductByExternalReferenceCodeMappedProductBySequence_addMappedProduct();

		MappedProduct getMappedProduct =
			mappedProductResource.
				getProductByExternalReferenceCodeMappedProductBySequence(
					null, postMappedProduct.getSequence());

		assertEquals(postMappedProduct, getMappedProduct);
		assertValid(getMappedProduct);
	}

	protected MappedProduct
			testGetProductByExternalReferenceCodeMappedProductBySequence_addMappedProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeMappedProductBySequence()
		throws Exception {

		MappedProduct mappedProduct =
			testGraphQLMappedProduct_addMappedProduct();

		Assert.assertTrue(
			equals(
				mappedProduct,
				MappedProductSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCodeMappedProductBySequence",
								new HashMap<String, Object>() {
									{
										put("externalReferenceCode", null);
										put(
											"sequence",
											"\"" + mappedProduct.getSequence() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCodeMappedProductBySequence"))));
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeMappedProductBySequenceNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantSequence = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCodeMappedProductBySequence",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
								put("sequence", irrelevantSequence);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetProductIdMappedProductsPage() throws Exception {
		Long productId = testGetProductIdMappedProductsPage_getProductId();
		Long irrelevantProductId =
			testGetProductIdMappedProductsPage_getIrrelevantProductId();

		Page<MappedProduct> page =
			mappedProductResource.getProductIdMappedProductsPage(
				productId, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantProductId != null) {
			MappedProduct irrelevantMappedProduct =
				testGetProductIdMappedProductsPage_addMappedProduct(
					irrelevantProductId, randomIrrelevantMappedProduct());

			page = mappedProductResource.getProductIdMappedProductsPage(
				irrelevantProductId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMappedProduct),
				(List<MappedProduct>)page.getItems());
			assertValid(page);
		}

		MappedProduct mappedProduct1 =
			testGetProductIdMappedProductsPage_addMappedProduct(
				productId, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetProductIdMappedProductsPage_addMappedProduct(
				productId, randomMappedProduct());

		page = mappedProductResource.getProductIdMappedProductsPage(
			productId, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2),
			(List<MappedProduct>)page.getItems());
		assertValid(page);

		mappedProductResource.deleteMappedProduct(mappedProduct1.getId());

		mappedProductResource.deleteMappedProduct(mappedProduct2.getId());
	}

	@Test
	public void testGetProductIdMappedProductsPageWithPagination()
		throws Exception {

		Long productId = testGetProductIdMappedProductsPage_getProductId();

		MappedProduct mappedProduct1 =
			testGetProductIdMappedProductsPage_addMappedProduct(
				productId, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetProductIdMappedProductsPage_addMappedProduct(
				productId, randomMappedProduct());

		MappedProduct mappedProduct3 =
			testGetProductIdMappedProductsPage_addMappedProduct(
				productId, randomMappedProduct());

		Page<MappedProduct> page1 =
			mappedProductResource.getProductIdMappedProductsPage(
				productId, null, Pagination.of(1, 2), null);

		List<MappedProduct> mappedProducts1 =
			(List<MappedProduct>)page1.getItems();

		Assert.assertEquals(
			mappedProducts1.toString(), 2, mappedProducts1.size());

		Page<MappedProduct> page2 =
			mappedProductResource.getProductIdMappedProductsPage(
				productId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MappedProduct> mappedProducts2 =
			(List<MappedProduct>)page2.getItems();

		Assert.assertEquals(
			mappedProducts2.toString(), 1, mappedProducts2.size());

		Page<MappedProduct> page3 =
			mappedProductResource.getProductIdMappedProductsPage(
				productId, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2, mappedProduct3),
			(List<MappedProduct>)page3.getItems());
	}

	@Test
	public void testGetProductIdMappedProductsPageWithSortDateTime()
		throws Exception {

		testGetProductIdMappedProductsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(
					mappedProduct1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProductIdMappedProductsPageWithSortInteger()
		throws Exception {

		testGetProductIdMappedProductsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(mappedProduct1, entityField.getName(), 0);
				BeanUtils.setProperty(mappedProduct2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProductIdMappedProductsPageWithSortString()
		throws Exception {

		testGetProductIdMappedProductsPageWithSort(
			EntityField.Type.STRING,
			(entityField, mappedProduct1, mappedProduct2) -> {
				Class<?> clazz = mappedProduct1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						mappedProduct1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						mappedProduct2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetProductIdMappedProductsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MappedProduct, MappedProduct, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long productId = testGetProductIdMappedProductsPage_getProductId();

		MappedProduct mappedProduct1 = randomMappedProduct();
		MappedProduct mappedProduct2 = randomMappedProduct();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, mappedProduct1, mappedProduct2);
		}

		mappedProduct1 = testGetProductIdMappedProductsPage_addMappedProduct(
			productId, mappedProduct1);

		mappedProduct2 = testGetProductIdMappedProductsPage_addMappedProduct(
			productId, mappedProduct2);

		for (EntityField entityField : entityFields) {
			Page<MappedProduct> ascPage =
				mappedProductResource.getProductIdMappedProductsPage(
					productId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(mappedProduct1, mappedProduct2),
				(List<MappedProduct>)ascPage.getItems());

			Page<MappedProduct> descPage =
				mappedProductResource.getProductIdMappedProductsPage(
					productId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(mappedProduct2, mappedProduct1),
				(List<MappedProduct>)descPage.getItems());
		}
	}

	protected MappedProduct testGetProductIdMappedProductsPage_addMappedProduct(
			Long productId, MappedProduct mappedProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProductIdMappedProductsPage_getProductId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProductIdMappedProductsPage_getIrrelevantProductId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProductIdMappedProduct() throws Exception {
		MappedProduct randomMappedProduct = randomMappedProduct();

		MappedProduct postMappedProduct =
			testPostProductIdMappedProduct_addMappedProduct(
				randomMappedProduct);

		assertEquals(randomMappedProduct, postMappedProduct);
		assertValid(postMappedProduct);
	}

	protected MappedProduct testPostProductIdMappedProduct_addMappedProduct(
			MappedProduct mappedProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductMappedProductBySequence() throws Exception {
		MappedProduct postMappedProduct =
			testGetProductMappedProductBySequence_addMappedProduct();

		MappedProduct getMappedProduct =
			mappedProductResource.getProductMappedProductBySequence(
				postMappedProduct.getProductId(),
				postMappedProduct.getSequence());

		assertEquals(postMappedProduct, getMappedProduct);
		assertValid(getMappedProduct);
	}

	protected MappedProduct
			testGetProductMappedProductBySequence_addMappedProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductMappedProductBySequence()
		throws Exception {

		MappedProduct mappedProduct =
			testGraphQLMappedProduct_addMappedProduct();

		Assert.assertTrue(
			equals(
				mappedProduct,
				MappedProductSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productMappedProductBySequence",
								new HashMap<String, Object>() {
									{
										put(
											"productId",
											mappedProduct.getProductId());
										put(
											"sequence",
											"\"" + mappedProduct.getSequence() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productMappedProductBySequence"))));
	}

	@Test
	public void testGraphQLGetProductMappedProductBySequenceNotFound()
		throws Exception {

		Long irrelevantProductId = RandomTestUtil.randomLong();
		String irrelevantSequence = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productMappedProductBySequence",
						new HashMap<String, Object>() {
							{
								put("productId", irrelevantProductId);
								put("sequence", irrelevantSequence);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected MappedProduct testGraphQLMappedProduct_addMappedProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		MappedProduct mappedProduct, List<MappedProduct> mappedProducts) {

		boolean contains = false;

		for (MappedProduct item : mappedProducts) {
			if (equals(mappedProduct, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			mappedProducts + " does not contain " + mappedProduct, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MappedProduct mappedProduct1, MappedProduct mappedProduct2) {

		Assert.assertTrue(
			mappedProduct1 + " does not equal " + mappedProduct2,
			equals(mappedProduct1, mappedProduct2));
	}

	protected void assertEquals(
		List<MappedProduct> mappedProducts1,
		List<MappedProduct> mappedProducts2) {

		Assert.assertEquals(mappedProducts1.size(), mappedProducts2.size());

		for (int i = 0; i < mappedProducts1.size(); i++) {
			MappedProduct mappedProduct1 = mappedProducts1.get(i);
			MappedProduct mappedProduct2 = mappedProducts2.get(i);

			assertEquals(mappedProduct1, mappedProduct2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MappedProduct> mappedProducts1,
		List<MappedProduct> mappedProducts2) {

		Assert.assertEquals(mappedProducts1.size(), mappedProducts2.size());

		for (MappedProduct mappedProduct1 : mappedProducts1) {
			boolean contains = false;

			for (MappedProduct mappedProduct2 : mappedProducts2) {
				if (equals(mappedProduct1, mappedProduct2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				mappedProducts2 + " does not contain " + mappedProduct1,
				contains);
		}
	}

	protected void assertValid(MappedProduct mappedProduct) throws Exception {
		boolean valid = true;

		if (mappedProduct.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (mappedProduct.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (mappedProduct.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productExternalReferenceCode",
					additionalAssertFieldName)) {

				if (mappedProduct.getProductExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (mappedProduct.getProductId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productName", additionalAssertFieldName)) {
				if (mappedProduct.getProductName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (mappedProduct.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sequence", additionalAssertFieldName)) {
				if (mappedProduct.getSequence() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (mappedProduct.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (mappedProduct.getSkuExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (mappedProduct.getSkuId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (mappedProduct.getType() == null) {
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

	protected void assertValid(Page<MappedProduct> page) {
		boolean valid = false;

		java.util.Collection<MappedProduct> mappedProducts = page.getItems();

		int size = mappedProducts.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.catalog.dto.v1_0.
						MappedProduct.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
					getDeclaredFields(clazz));

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
		MappedProduct mappedProduct1, MappedProduct mappedProduct2) {

		if (mappedProduct1 == mappedProduct2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)mappedProduct1.getActions(),
						(Map)mappedProduct2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getCustomFields(),
						mappedProduct2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getId(), mappedProduct2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.getProductExternalReferenceCode(),
						mappedProduct2.getProductExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getProductId(),
						mappedProduct2.getProductId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productName", additionalAssertFieldName)) {
				if (!equals(
						(Map)mappedProduct1.getProductName(),
						(Map)mappedProduct2.getProductName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getQuantity(),
						mappedProduct2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sequence", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getSequence(),
						mappedProduct2.getSequence())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getSku(), mappedProduct2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.getSkuExternalReferenceCode(),
						mappedProduct2.getSkuExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getSkuId(), mappedProduct2.getSkuId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getType(), mappedProduct2.getType())) {

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

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_mappedProductResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_mappedProductResource;

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
		EntityField entityField, String operator, MappedProduct mappedProduct) {

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

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					mappedProduct.getProductExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("productId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productName")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sequence")) {
			sb.append("'");
			sb.append(String.valueOf(mappedProduct.getSequence()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("sku")) {
			sb.append("'");
			sb.append(String.valueOf(mappedProduct.getSku()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skuExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(mappedProduct.getSkuExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skuId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
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

	protected MappedProduct randomMappedProduct() throws Exception {
		return new MappedProduct() {
			{
				id = RandomTestUtil.randomLong();
				productExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				productId = RandomTestUtil.randomLong();
				quantity = RandomTestUtil.randomInt();
				sequence = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
			}
		};
	}

	protected MappedProduct randomIrrelevantMappedProduct() throws Exception {
		MappedProduct randomIrrelevantMappedProduct = randomMappedProduct();

		return randomIrrelevantMappedProduct;
	}

	protected MappedProduct randomPatchMappedProduct() throws Exception {
		return randomMappedProduct();
	}

	protected MappedProductResource mappedProductResource;
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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseMappedProductResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.catalog.resource.v1_0.
		MappedProductResource _mappedProductResource;

}