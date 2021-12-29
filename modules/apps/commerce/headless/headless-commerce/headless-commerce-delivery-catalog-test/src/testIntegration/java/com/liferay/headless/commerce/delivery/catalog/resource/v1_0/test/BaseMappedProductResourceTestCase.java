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

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Page;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.catalog.client.resource.v1_0.MappedProductResource;
import com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0.MappedProductSerDes;
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
 * @author Andrea Sbarra
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
		mappedProduct.setReplacementMessage(regex);
		mappedProduct.setSequence(regex);
		mappedProduct.setSku(regex);
		mappedProduct.setSkuExternalReferenceCode(regex);
		mappedProduct.setThumbnail(regex);

		String json = MappedProductSerDes.toJSON(mappedProduct);

		Assert.assertFalse(json.contains(regex));

		mappedProduct = MappedProductSerDes.toDTO(json);

		Assert.assertEquals(
			regex, mappedProduct.getProductExternalReferenceCode());
		Assert.assertEquals(regex, mappedProduct.getReplacementMessage());
		Assert.assertEquals(regex, mappedProduct.getSequence());
		Assert.assertEquals(regex, mappedProduct.getSku());
		Assert.assertEquals(regex, mappedProduct.getSkuExternalReferenceCode());
		Assert.assertEquals(regex, mappedProduct.getThumbnail());
	}

	@Test
	public void testGetChannelProductMappedProductsPage() throws Exception {
		Long channelId = testGetChannelProductMappedProductsPage_getChannelId();
		Long irrelevantChannelId =
			testGetChannelProductMappedProductsPage_getIrrelevantChannelId();
		Long productId = testGetChannelProductMappedProductsPage_getProductId();
		Long irrelevantProductId =
			testGetChannelProductMappedProductsPage_getIrrelevantProductId();

		Page<MappedProduct> page =
			mappedProductResource.getChannelProductMappedProductsPage(
				channelId, productId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantChannelId != null) && (irrelevantProductId != null)) {
			MappedProduct irrelevantMappedProduct =
				testGetChannelProductMappedProductsPage_addMappedProduct(
					irrelevantChannelId, irrelevantProductId,
					randomIrrelevantMappedProduct());

			page = mappedProductResource.getChannelProductMappedProductsPage(
				irrelevantChannelId, irrelevantProductId, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMappedProduct),
				(List<MappedProduct>)page.getItems());
			assertValid(page);
		}

		MappedProduct mappedProduct1 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, randomMappedProduct());

		page = mappedProductResource.getChannelProductMappedProductsPage(
			channelId, productId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2),
			(List<MappedProduct>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetChannelProductMappedProductsPageWithPagination()
		throws Exception {

		Long channelId = testGetChannelProductMappedProductsPage_getChannelId();
		Long productId = testGetChannelProductMappedProductsPage_getProductId();

		MappedProduct mappedProduct1 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, randomMappedProduct());

		MappedProduct mappedProduct2 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, randomMappedProduct());

		MappedProduct mappedProduct3 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, randomMappedProduct());

		Page<MappedProduct> page1 =
			mappedProductResource.getChannelProductMappedProductsPage(
				channelId, productId, null, null, Pagination.of(1, 2), null);

		List<MappedProduct> mappedProducts1 =
			(List<MappedProduct>)page1.getItems();

		Assert.assertEquals(
			mappedProducts1.toString(), 2, mappedProducts1.size());

		Page<MappedProduct> page2 =
			mappedProductResource.getChannelProductMappedProductsPage(
				channelId, productId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MappedProduct> mappedProducts2 =
			(List<MappedProduct>)page2.getItems();

		Assert.assertEquals(
			mappedProducts2.toString(), 1, mappedProducts2.size());

		Page<MappedProduct> page3 =
			mappedProductResource.getChannelProductMappedProductsPage(
				channelId, productId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(mappedProduct1, mappedProduct2, mappedProduct3),
			(List<MappedProduct>)page3.getItems());
	}

	@Test
	public void testGetChannelProductMappedProductsPageWithSortDateTime()
		throws Exception {

		testGetChannelProductMappedProductsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(
					mappedProduct1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetChannelProductMappedProductsPageWithSortInteger()
		throws Exception {

		testGetChannelProductMappedProductsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, mappedProduct1, mappedProduct2) -> {
				BeanUtils.setProperty(mappedProduct1, entityField.getName(), 0);
				BeanUtils.setProperty(mappedProduct2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetChannelProductMappedProductsPageWithSortString()
		throws Exception {

		testGetChannelProductMappedProductsPageWithSort(
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

	protected void testGetChannelProductMappedProductsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MappedProduct, MappedProduct, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long channelId = testGetChannelProductMappedProductsPage_getChannelId();
		Long productId = testGetChannelProductMappedProductsPage_getProductId();

		MappedProduct mappedProduct1 = randomMappedProduct();
		MappedProduct mappedProduct2 = randomMappedProduct();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, mappedProduct1, mappedProduct2);
		}

		mappedProduct1 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, mappedProduct1);

		mappedProduct2 =
			testGetChannelProductMappedProductsPage_addMappedProduct(
				channelId, productId, mappedProduct2);

		for (EntityField entityField : entityFields) {
			Page<MappedProduct> ascPage =
				mappedProductResource.getChannelProductMappedProductsPage(
					channelId, productId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(mappedProduct1, mappedProduct2),
				(List<MappedProduct>)ascPage.getItems());

			Page<MappedProduct> descPage =
				mappedProductResource.getChannelProductMappedProductsPage(
					channelId, productId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(mappedProduct2, mappedProduct1),
				(List<MappedProduct>)descPage.getItems());
		}
	}

	protected MappedProduct
			testGetChannelProductMappedProductsPage_addMappedProduct(
				Long channelId, Long productId, MappedProduct mappedProduct)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetChannelProductMappedProductsPage_getChannelId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetChannelProductMappedProductsPage_getIrrelevantChannelId()
		throws Exception {

		return null;
	}

	protected Long testGetChannelProductMappedProductsPage_getProductId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetChannelProductMappedProductsPage_getIrrelevantProductId()
		throws Exception {

		return null;
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

			if (Objects.equals("availability", additionalAssertFieldName)) {
				if (mappedProduct.getAvailability() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"firstAvailableReplacementMappedProduct",
					additionalAssertFieldName)) {

				if (mappedProduct.getFirstAvailableReplacementMappedProduct() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (mappedProduct.getOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (mappedProduct.getPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productConfiguration", additionalAssertFieldName)) {

				if (mappedProduct.getProductConfiguration() == null) {
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

			if (Objects.equals("productOptions", additionalAssertFieldName)) {
				if (mappedProduct.getProductOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("purchasable", additionalAssertFieldName)) {
				if (mappedProduct.getPurchasable() == null) {
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

			if (Objects.equals(
					"replacementMappedProduct", additionalAssertFieldName)) {

				if (mappedProduct.getReplacementMappedProduct() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"replacementMessage", additionalAssertFieldName)) {

				if (mappedProduct.getReplacementMessage() == null) {
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

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (mappedProduct.getThumbnail() == null) {
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

			if (Objects.equals("urls", additionalAssertFieldName)) {
				if (mappedProduct.getUrls() == null) {
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
					com.liferay.headless.commerce.delivery.catalog.dto.v1_0.
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

			if (Objects.equals("availability", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getAvailability(),
						mappedProduct2.getAvailability())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"firstAvailableReplacementMappedProduct",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.
							getFirstAvailableReplacementMappedProduct(),
						mappedProduct2.
							getFirstAvailableReplacementMappedProduct())) {

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

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (!equals(
						(Map)mappedProduct1.getOptions(),
						(Map)mappedProduct2.getOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getPrice(), mappedProduct2.getPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productConfiguration", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.getProductConfiguration(),
						mappedProduct2.getProductConfiguration())) {

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

			if (Objects.equals("productOptions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getProductOptions(),
						mappedProduct2.getProductOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("purchasable", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getPurchasable(),
						mappedProduct2.getPurchasable())) {

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

			if (Objects.equals(
					"replacementMappedProduct", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.getReplacementMappedProduct(),
						mappedProduct2.getReplacementMappedProduct())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"replacementMessage", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						mappedProduct1.getReplacementMessage(),
						mappedProduct2.getReplacementMessage())) {

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

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						mappedProduct1.getThumbnail(),
						mappedProduct2.getThumbnail())) {

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

			if (Objects.equals("urls", additionalAssertFieldName)) {
				if (!equals(
						(Map)mappedProduct1.getUrls(),
						(Map)mappedProduct2.getUrls())) {

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

		if (entityFieldName.equals("availability")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("firstAvailableReplacementMappedProduct")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("options")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("price")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productConfiguration")) {
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

		if (entityFieldName.equals("productOptions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("purchasable")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("replacementMappedProduct")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("replacementMessage")) {
			sb.append("'");
			sb.append(String.valueOf(mappedProduct.getReplacementMessage()));
			sb.append("'");

			return sb.toString();
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

		if (entityFieldName.equals("thumbnail")) {
			sb.append("'");
			sb.append(String.valueOf(mappedProduct.getThumbnail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("urls")) {
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
				purchasable = RandomTestUtil.randomBoolean();
				quantity = RandomTestUtil.randomInt();
				replacementMessage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sequence = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
				thumbnail = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
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
	private com.liferay.headless.commerce.delivery.catalog.resource.v1_0.
		MappedProductResource _mappedProductResource;

}