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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.ProductSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
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
public abstract class BaseProductResourceTestCase {

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

		_productResource.setContextCompany(testCompany);

		ProductResource.Builder builder = ProductResource.builder();

		productResource = builder.authentication(
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

		Product product1 = randomProduct();

		String json = objectMapper.writeValueAsString(product1);

		Product product2 = ProductSerDes.toDTO(json);

		Assert.assertTrue(equals(product1, product2));
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

		Product product = randomProduct();

		String json1 = objectMapper.writeValueAsString(product);
		String json2 = ProductSerDes.toJSON(product);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Product product = randomProduct();

		product.setDefaultSku(regex);
		product.setExternalReferenceCode(regex);
		product.setProductType(regex);
		product.setProductTypeI18n(regex);
		product.setSkuFormatted(regex);
		product.setThumbnail(regex);

		String json = ProductSerDes.toJSON(product);

		Assert.assertFalse(json.contains(regex));

		product = ProductSerDes.toDTO(json);

		Assert.assertEquals(regex, product.getDefaultSku());
		Assert.assertEquals(regex, product.getExternalReferenceCode());
		Assert.assertEquals(regex, product.getProductType());
		Assert.assertEquals(regex, product.getProductTypeI18n());
		Assert.assertEquals(regex, product.getSkuFormatted());
		Assert.assertEquals(regex, product.getThumbnail());
	}

	@Test
	public void testGetProductsPage() throws Exception {
		Page<Product> page = productResource.getProductsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Product product1 = testGetProductsPage_addProduct(randomProduct());

		Product product2 = testGetProductsPage_addProduct(randomProduct());

		page = productResource.getProductsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(product1, (List<Product>)page.getItems());
		assertContains(product2, (List<Product>)page.getItems());
		assertValid(page);

		productResource.deleteProduct(product1.getId());

		productResource.deleteProduct(product2.getId());
	}

	@Test
	public void testGetProductsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Product product1 = randomProduct();

		product1 = testGetProductsPage_addProduct(product1);

		for (EntityField entityField : entityFields) {
			Page<Product> page = productResource.getProductsPage(
				null, getFilterString(entityField, "between", product1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(product1),
				(List<Product>)page.getItems());
		}
	}

	@Test
	public void testGetProductsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Product product1 = testGetProductsPage_addProduct(randomProduct());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Product product2 = testGetProductsPage_addProduct(randomProduct());

		for (EntityField entityField : entityFields) {
			Page<Product> page = productResource.getProductsPage(
				null, getFilterString(entityField, "eq", product1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(product1),
				(List<Product>)page.getItems());
		}
	}

	@Test
	public void testGetProductsPageWithPagination() throws Exception {
		Page<Product> totalPage = productResource.getProductsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Product product1 = testGetProductsPage_addProduct(randomProduct());

		Product product2 = testGetProductsPage_addProduct(randomProduct());

		Product product3 = testGetProductsPage_addProduct(randomProduct());

		Page<Product> page1 = productResource.getProductsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Product> products1 = (List<Product>)page1.getItems();

		Assert.assertEquals(
			products1.toString(), totalCount + 2, products1.size());

		Page<Product> page2 = productResource.getProductsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Product> products2 = (List<Product>)page2.getItems();

		Assert.assertEquals(products2.toString(), 1, products2.size());

		Page<Product> page3 = productResource.getProductsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(product1, (List<Product>)page3.getItems());
		assertContains(product2, (List<Product>)page3.getItems());
		assertContains(product3, (List<Product>)page3.getItems());
	}

	@Test
	public void testGetProductsPageWithSortDateTime() throws Exception {
		testGetProductsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, product1, product2) -> {
				BeanUtils.setProperty(
					product1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProductsPageWithSortInteger() throws Exception {
		testGetProductsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, product1, product2) -> {
				BeanUtils.setProperty(product1, entityField.getName(), 0);
				BeanUtils.setProperty(product2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProductsPageWithSortString() throws Exception {
		testGetProductsPageWithSort(
			EntityField.Type.STRING,
			(entityField, product1, product2) -> {
				Class<?> clazz = product1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						product1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						product2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						product1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						product2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						product1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						product2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetProductsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Product, Product, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Product product1 = randomProduct();
		Product product2 = randomProduct();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, product1, product2);
		}

		product1 = testGetProductsPage_addProduct(product1);

		product2 = testGetProductsPage_addProduct(product2);

		for (EntityField entityField : entityFields) {
			Page<Product> ascPage = productResource.getProductsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(product1, product2),
				(List<Product>)ascPage.getItems());

			Page<Product> descPage = productResource.getProductsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(product2, product1),
				(List<Product>)descPage.getItems());
		}
	}

	protected Product testGetProductsPage_addProduct(Product product)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"products",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject productsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/products");

		long totalCount = productsJSONObject.getLong("totalCount");

		Product product1 = testGraphQLProduct_addProduct();
		Product product2 = testGraphQLProduct_addProduct();

		productsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/products");

		Assert.assertEquals(
			totalCount + 2, productsJSONObject.getLong("totalCount"));

		assertContains(
			product1,
			Arrays.asList(
				ProductSerDes.toDTOs(productsJSONObject.getString("items"))));
		assertContains(
			product2,
			Arrays.asList(
				ProductSerDes.toDTOs(productsJSONObject.getString("items"))));
	}

	@Test
	public void testPostProduct() throws Exception {
		Product randomProduct = randomProduct();

		Product postProduct = testPostProduct_addProduct(randomProduct);

		assertEquals(randomProduct, postProduct);
		assertValid(postProduct);
	}

	protected Product testPostProduct_addProduct(Product product)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteProductByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Product product = testDeleteProductByExternalReferenceCode_addProduct();

		assertHttpResponseStatusCode(
			204,
			productResource.deleteProductByExternalReferenceCodeHttpResponse(
				product.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			productResource.getProductByExternalReferenceCodeHttpResponse(
				product.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			productResource.getProductByExternalReferenceCodeHttpResponse(
				product.getExternalReferenceCode()));
	}

	protected Product testDeleteProductByExternalReferenceCode_addProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductByExternalReferenceCode() throws Exception {
		Product postProduct =
			testGetProductByExternalReferenceCode_addProduct();

		Product getProduct = productResource.getProductByExternalReferenceCode(
			postProduct.getExternalReferenceCode());

		assertEquals(postProduct, getProduct);
		assertValid(getProduct);
	}

	protected Product testGetProductByExternalReferenceCode_addProduct()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCode()
		throws Exception {

		Product product = testGraphQLProduct_addProduct();

		Assert.assertTrue(
			equals(
				product,
				ProductSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												product.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProductByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostProductByExternalReferenceCodeClone() throws Exception {
		Product randomProduct = randomProduct();

		Product postProduct =
			testPostProductByExternalReferenceCodeClone_addProduct(
				randomProduct);

		assertEquals(randomProduct, postProduct);
		assertValid(postProduct);
	}

	protected Product testPostProductByExternalReferenceCodeClone_addProduct(
			Product product)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteProduct() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Product product = testDeleteProduct_addProduct();

		assertHttpResponseStatusCode(
			204, productResource.deleteProductHttpResponse(product.getId()));

		assertHttpResponseStatusCode(
			404, productResource.getProductHttpResponse(product.getId()));

		assertHttpResponseStatusCode(
			404, productResource.getProductHttpResponse(product.getId()));
	}

	protected Product testDeleteProduct_addProduct() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteProduct() throws Exception {
		Product product = testGraphQLProduct_addProduct();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteProduct",
						new HashMap<String, Object>() {
							{
								put("id", product.getId());
							}
						})),
				"JSONObject/data", "Object/deleteProduct"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"product",
					new HashMap<String, Object>() {
						{
							put("id", product.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetProduct() throws Exception {
		Product postProduct = testGetProduct_addProduct();

		Product getProduct = productResource.getProduct(postProduct.getId());

		assertEquals(postProduct, getProduct);
		assertValid(getProduct);
	}

	protected Product testGetProduct_addProduct() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProduct() throws Exception {
		Product product = testGraphQLProduct_addProduct();

		Assert.assertTrue(
			equals(
				product,
				ProductSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"product",
								new HashMap<String, Object>() {
									{
										put("id", product.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/product"))));
	}

	@Test
	public void testGraphQLGetProductNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"product",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProduct() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostProductClone() throws Exception {
		Product randomProduct = randomProduct();

		Product postProduct = testPostProductClone_addProduct(randomProduct);

		assertEquals(randomProduct, postProduct);
		assertValid(postProduct);
	}

	protected Product testPostProductClone_addProduct(Product product)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Product testGraphQLProduct_addProduct() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Product product, List<Product> products) {
		boolean contains = false;

		for (Product item : products) {
			if (equals(product, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(products + " does not contain " + product, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Product product1, Product product2) {
		Assert.assertTrue(
			product1 + " does not equal " + product2,
			equals(product1, product2));
	}

	protected void assertEquals(
		List<Product> products1, List<Product> products2) {

		Assert.assertEquals(products1.size(), products2.size());

		for (int i = 0; i < products1.size(); i++) {
			Product product1 = products1.get(i);
			Product product2 = products2.get(i);

			assertEquals(product1, product2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Product> products1, List<Product> products2) {

		Assert.assertEquals(products1.size(), products2.size());

		for (Product product1 : products1) {
			boolean contains = false;

			for (Product product2 : products2) {
				if (equals(product1, product2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				products2 + " does not contain " + product1, contains);
		}
	}

	protected void assertValid(Product product) throws Exception {
		boolean valid = true;

		if (product.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (product.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (product.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("attachments", additionalAssertFieldName)) {
				if (product.getAttachments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("catalog", additionalAssertFieldName)) {
				if (product.getCatalog() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("catalogId", additionalAssertFieldName)) {
				if (product.getCatalogId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("categories", additionalAssertFieldName)) {
				if (product.getCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (product.getConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (product.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (product.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultSku", additionalAssertFieldName)) {
				if (product.getDefaultSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (product.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("diagram", additionalAssertFieldName)) {
				if (product.getDiagram() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (product.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expando", additionalAssertFieldName)) {
				if (product.getExpando() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (product.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (product.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("images", additionalAssertFieldName)) {
				if (product.getImages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("mappedProducts", additionalAssertFieldName)) {
				if (product.getMappedProducts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("metaDescription", additionalAssertFieldName)) {
				if (product.getMetaDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("metaKeyword", additionalAssertFieldName)) {
				if (product.getMetaKeyword() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("metaTitle", additionalAssertFieldName)) {
				if (product.getMetaTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (product.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (product.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (product.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pins", additionalAssertFieldName)) {
				if (product.getPins() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productAccountGroupFilter", additionalAssertFieldName)) {

				if (product.getProductAccountGroupFilter() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productAccountGroups", additionalAssertFieldName)) {

				if (product.getProductAccountGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productChannelFilter", additionalAssertFieldName)) {

				if (product.getProductChannelFilter() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productChannels", additionalAssertFieldName)) {
				if (product.getProductChannels() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productConfiguration", additionalAssertFieldName)) {

				if (product.getProductConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (product.getProductId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productOptions", additionalAssertFieldName)) {
				if (product.getProductOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productSpecifications", additionalAssertFieldName)) {

				if (product.getProductSpecifications() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productStatus", additionalAssertFieldName)) {
				if (product.getProductStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productType", additionalAssertFieldName)) {
				if (product.getProductType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productTypeI18n", additionalAssertFieldName)) {
				if (product.getProductTypeI18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedProducts", additionalAssertFieldName)) {
				if (product.getRelatedProducts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingConfiguration", additionalAssertFieldName)) {

				if (product.getShippingConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shortDescription", additionalAssertFieldName)) {
				if (product.getShortDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuFormatted", additionalAssertFieldName)) {
				if (product.getSkuFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skus", additionalAssertFieldName)) {
				if (product.getSkus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subscriptionConfiguration", additionalAssertFieldName)) {

				if (product.getSubscriptionConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("tags", additionalAssertFieldName)) {
				if (product.getTags() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taxConfiguration", additionalAssertFieldName)) {
				if (product.getTaxConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (product.getThumbnail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("urls", additionalAssertFieldName)) {
				if (product.getUrls() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (product.getWorkflowStatusInfo() == null) {
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

	protected void assertValid(Page<Product> page) {
		boolean valid = false;

		java.util.Collection<Product> products = page.getItems();

		int size = products.size();

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
						Product.class)) {

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

	protected boolean equals(Product product1, Product product2) {
		if (product1 == product2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getActions(),
						(Map)product2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getActive(), product2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("attachments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getAttachments(), product2.getAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("catalog", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getCatalog(), product2.getCatalog())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("catalogId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getCatalogId(), product2.getCatalogId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("categories", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getCategories(), product2.getCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getConfiguration(),
						product2.getConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getCreateDate(), product2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getCustomFields(),
						product2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultSku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getDefaultSku(), product2.getDefaultSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getDescription(),
						(Map)product2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("diagram", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getDiagram(), product2.getDiagram())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getDisplayDate(), product2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expando", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getExpando(),
						(Map)product2.getExpando())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getExpirationDate(),
						product2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getExternalReferenceCode(),
						product2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(product1.getId(), product2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("images", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getImages(), product2.getImages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("mappedProducts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getMappedProducts(),
						product2.getMappedProducts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("metaDescription", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getMetaDescription(),
						(Map)product2.getMetaDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("metaKeyword", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getMetaKeyword(),
						(Map)product2.getMetaKeyword())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("metaTitle", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getMetaTitle(),
						(Map)product2.getMetaTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getModifiedDate(),
						product2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals((Map)product1.getName(), (Map)product2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getNeverExpire(), product2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pins", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getPins(), product2.getPins())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productAccountGroupFilter", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getProductAccountGroupFilter(),
						product2.getProductAccountGroupFilter())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productAccountGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getProductAccountGroups(),
						product2.getProductAccountGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productChannelFilter", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getProductChannelFilter(),
						product2.getProductChannelFilter())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productChannels", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductChannels(),
						product2.getProductChannels())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productConfiguration", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getProductConfiguration(),
						product2.getProductConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductId(), product2.getProductId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productOptions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductOptions(),
						product2.getProductOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productSpecifications", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getProductSpecifications(),
						product2.getProductSpecifications())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductStatus(),
						product2.getProductStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductType(), product2.getProductType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productTypeI18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getProductTypeI18n(),
						product2.getProductTypeI18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedProducts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getRelatedProducts(),
						product2.getRelatedProducts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingConfiguration", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getShippingConfiguration(),
						product2.getShippingConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shortDescription", additionalAssertFieldName)) {
				if (!equals(
						(Map)product1.getShortDescription(),
						(Map)product2.getShortDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuFormatted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getSkuFormatted(),
						product2.getSkuFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getSkus(), product2.getSkus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subscriptionConfiguration", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getSubscriptionConfiguration(),
						product2.getSubscriptionConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("tags", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getTags(), product2.getTags())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taxConfiguration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getTaxConfiguration(),
						product2.getTaxConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						product1.getThumbnail(), product2.getThumbnail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("urls", additionalAssertFieldName)) {
				if (!equals((Map)product1.getUrls(), (Map)product2.getUrls())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						product1.getWorkflowStatusInfo(),
						product2.getWorkflowStatusInfo())) {

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

		if (!(_productResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_productResource;

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
		EntityField entityField, String operator, Product product) {

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

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("attachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("catalog")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("catalogId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("configuration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(product.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("defaultSku")) {
			sb.append("'");
			sb.append(String.valueOf(product.getDefaultSku()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("diagram")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(product.getDisplayDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expando")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("expirationDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(product.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(product.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("images")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("mappedProducts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("metaDescription")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("metaKeyword")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("metaTitle")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(product.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(product.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("pins")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productAccountGroupFilter")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productAccountGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productChannelFilter")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productChannels")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productConfiguration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productOptions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productSpecifications")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productType")) {
			sb.append("'");
			sb.append(String.valueOf(product.getProductType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("productTypeI18n")) {
			sb.append("'");
			sb.append(String.valueOf(product.getProductTypeI18n()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("relatedProducts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingConfiguration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shortDescription")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("skuFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(product.getSkuFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscriptionConfiguration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("tags")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxConfiguration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("thumbnail")) {
			sb.append("'");
			sb.append(String.valueOf(product.getThumbnail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("urls")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("workflowStatusInfo")) {
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

	protected Product randomProduct() throws Exception {
		return new Product() {
			{
				active = RandomTestUtil.randomBoolean();
				catalogId = RandomTestUtil.randomLong();
				createDate = RandomTestUtil.nextDate();
				defaultSku = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				neverExpire = RandomTestUtil.randomBoolean();
				productAccountGroupFilter = RandomTestUtil.randomBoolean();
				productChannelFilter = RandomTestUtil.randomBoolean();
				productId = RandomTestUtil.randomLong();
				productStatus = RandomTestUtil.randomInt();
				productType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				productTypeI18n = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				skuFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				thumbnail = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Product randomIrrelevantProduct() throws Exception {
		Product randomIrrelevantProduct = randomProduct();

		return randomIrrelevantProduct;
	}

	protected Product randomPatchProduct() throws Exception {
		return randomProduct();
	}

	protected ProductResource productResource;
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
		LogFactoryUtil.getLog(BaseProductResourceTestCase.class);

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
	private
		com.liferay.headless.commerce.admin.catalog.resource.v1_0.
			ProductResource _productResource;

}