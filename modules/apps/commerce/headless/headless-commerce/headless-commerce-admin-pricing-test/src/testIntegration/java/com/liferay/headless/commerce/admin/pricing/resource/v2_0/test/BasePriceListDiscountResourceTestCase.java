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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListDiscountResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceListDiscountSerDes;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BasePriceListDiscountResourceTestCase {

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

		_priceListDiscountResource.setContextCompany(testCompany);

		PriceListDiscountResource.Builder builder =
			PriceListDiscountResource.builder();

		priceListDiscountResource = builder.authentication(
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

		PriceListDiscount priceListDiscount1 = randomPriceListDiscount();

		String json = objectMapper.writeValueAsString(priceListDiscount1);

		PriceListDiscount priceListDiscount2 = PriceListDiscountSerDes.toDTO(
			json);

		Assert.assertTrue(equals(priceListDiscount1, priceListDiscount2));
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

		PriceListDiscount priceListDiscount = randomPriceListDiscount();

		String json1 = objectMapper.writeValueAsString(priceListDiscount);
		String json2 = PriceListDiscountSerDes.toJSON(priceListDiscount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceListDiscount priceListDiscount = randomPriceListDiscount();

		priceListDiscount.setDiscountExternalReferenceCode(regex);
		priceListDiscount.setDiscountName(regex);
		priceListDiscount.setPriceListExternalReferenceCode(regex);

		String json = PriceListDiscountSerDes.toJSON(priceListDiscount);

		Assert.assertFalse(json.contains(regex));

		priceListDiscount = PriceListDiscountSerDes.toDTO(json);

		Assert.assertEquals(
			regex, priceListDiscount.getDiscountExternalReferenceCode());
		Assert.assertEquals(regex, priceListDiscount.getDiscountName());
		Assert.assertEquals(
			regex, priceListDiscount.getPriceListExternalReferenceCode());
	}

	@Test
	public void testDeletePriceListDiscount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListDiscount priceListDiscount =
			testDeletePriceListDiscount_addPriceListDiscount();

		assertHttpResponseStatusCode(
			204,
			priceListDiscountResource.deletePriceListDiscountHttpResponse(
				priceListDiscount.getId()));
	}

	protected PriceListDiscount
			testDeletePriceListDiscount_addPriceListDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceListDiscount() throws Exception {
		PriceListDiscount priceListDiscount =
			testGraphQLPriceListDiscount_addPriceListDiscount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceListDiscount",
						new HashMap<String, Object>() {
							{
								put("id", priceListDiscount.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceListDiscount"));
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListDiscountsPage()
		throws Exception {

		Page<PriceListDiscount> page =
			priceListDiscountResource.
				getPriceListByExternalReferenceCodePriceListDiscountsPage(
					testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceListDiscount irrelevantPriceListDiscount =
				testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceListDiscount());

			page =
				priceListDiscountResource.
					getPriceListByExternalReferenceCodePriceListDiscountsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListDiscount),
				(List<PriceListDiscount>)page.getItems());
			assertValid(page);
		}

		PriceListDiscount priceListDiscount1 =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				externalReferenceCode, randomPriceListDiscount());

		PriceListDiscount priceListDiscount2 =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				externalReferenceCode, randomPriceListDiscount());

		page =
			priceListDiscountResource.
				getPriceListByExternalReferenceCodePriceListDiscountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListDiscount1, priceListDiscount2),
			(List<PriceListDiscount>)page.getItems());
		assertValid(page);

		priceListDiscountResource.deletePriceListDiscount(
			priceListDiscount1.getId());

		priceListDiscountResource.deletePriceListDiscount(
			priceListDiscount2.getId());
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListDiscountsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getExternalReferenceCode();

		PriceListDiscount priceListDiscount1 =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				externalReferenceCode, randomPriceListDiscount());

		PriceListDiscount priceListDiscount2 =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				externalReferenceCode, randomPriceListDiscount());

		PriceListDiscount priceListDiscount3 =
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				externalReferenceCode, randomPriceListDiscount());

		Page<PriceListDiscount> page1 =
			priceListDiscountResource.
				getPriceListByExternalReferenceCodePriceListDiscountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceListDiscount> priceListDiscounts1 =
			(List<PriceListDiscount>)page1.getItems();

		Assert.assertEquals(
			priceListDiscounts1.toString(), 2, priceListDiscounts1.size());

		Page<PriceListDiscount> page2 =
			priceListDiscountResource.
				getPriceListByExternalReferenceCodePriceListDiscountsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListDiscount> priceListDiscounts2 =
			(List<PriceListDiscount>)page2.getItems();

		Assert.assertEquals(
			priceListDiscounts2.toString(), 1, priceListDiscounts2.size());

		Page<PriceListDiscount> page3 =
			priceListDiscountResource.
				getPriceListByExternalReferenceCodePriceListDiscountsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListDiscount1, priceListDiscount2, priceListDiscount3),
			(List<PriceListDiscount>)page3.getItems());
	}

	protected PriceListDiscount
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_addPriceListDiscount(
				String externalReferenceCode,
				PriceListDiscount priceListDiscount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListDiscountsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceListDiscount()
		throws Exception {

		PriceListDiscount randomPriceListDiscount = randomPriceListDiscount();

		PriceListDiscount postPriceListDiscount =
			testPostPriceListByExternalReferenceCodePriceListDiscount_addPriceListDiscount(
				randomPriceListDiscount);

		assertEquals(randomPriceListDiscount, postPriceListDiscount);
		assertValid(postPriceListDiscount);
	}

	protected PriceListDiscount
			testPostPriceListByExternalReferenceCodePriceListDiscount_addPriceListDiscount(
				PriceListDiscount priceListDiscount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceListDiscountsPage() throws Exception {
		Page<PriceListDiscount> page =
			priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
				testGetPriceListIdPriceListDiscountsPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceListDiscountsPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceListDiscountsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceListDiscount irrelevantPriceListDiscount =
				testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
					irrelevantId, randomIrrelevantPriceListDiscount());

			page =
				priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
					irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListDiscount),
				(List<PriceListDiscount>)page.getItems());
			assertValid(page);
		}

		PriceListDiscount priceListDiscount1 =
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				id, randomPriceListDiscount());

		PriceListDiscount priceListDiscount2 =
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				id, randomPriceListDiscount());

		page = priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListDiscount1, priceListDiscount2),
			(List<PriceListDiscount>)page.getItems());
		assertValid(page);

		priceListDiscountResource.deletePriceListDiscount(
			priceListDiscount1.getId());

		priceListDiscountResource.deletePriceListDiscount(
			priceListDiscount2.getId());
	}

	@Test
	public void testGetPriceListIdPriceListDiscountsPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceListDiscountsPage_getId();

		PriceListDiscount priceListDiscount1 =
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				id, randomPriceListDiscount());

		PriceListDiscount priceListDiscount2 =
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				id, randomPriceListDiscount());

		PriceListDiscount priceListDiscount3 =
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				id, randomPriceListDiscount());

		Page<PriceListDiscount> page1 =
			priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
				id, Pagination.of(1, 2));

		List<PriceListDiscount> priceListDiscounts1 =
			(List<PriceListDiscount>)page1.getItems();

		Assert.assertEquals(
			priceListDiscounts1.toString(), 2, priceListDiscounts1.size());

		Page<PriceListDiscount> page2 =
			priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListDiscount> priceListDiscounts2 =
			(List<PriceListDiscount>)page2.getItems();

		Assert.assertEquals(
			priceListDiscounts2.toString(), 1, priceListDiscounts2.size());

		Page<PriceListDiscount> page3 =
			priceListDiscountResource.getPriceListIdPriceListDiscountsPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListDiscount1, priceListDiscount2, priceListDiscount3),
			(List<PriceListDiscount>)page3.getItems());
	}

	protected PriceListDiscount
			testGetPriceListIdPriceListDiscountsPage_addPriceListDiscount(
				Long id, PriceListDiscount priceListDiscount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListDiscountsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListDiscountsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceListDiscount() throws Exception {
		PriceListDiscount randomPriceListDiscount = randomPriceListDiscount();

		PriceListDiscount postPriceListDiscount =
			testPostPriceListIdPriceListDiscount_addPriceListDiscount(
				randomPriceListDiscount);

		assertEquals(randomPriceListDiscount, postPriceListDiscount);
		assertValid(postPriceListDiscount);
	}

	protected PriceListDiscount
			testPostPriceListIdPriceListDiscount_addPriceListDiscount(
				PriceListDiscount priceListDiscount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected PriceListDiscount
			testGraphQLPriceListDiscount_addPriceListDiscount()
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
		PriceListDiscount priceListDiscount1,
		PriceListDiscount priceListDiscount2) {

		Assert.assertTrue(
			priceListDiscount1 + " does not equal " + priceListDiscount2,
			equals(priceListDiscount1, priceListDiscount2));
	}

	protected void assertEquals(
		List<PriceListDiscount> priceListDiscounts1,
		List<PriceListDiscount> priceListDiscounts2) {

		Assert.assertEquals(
			priceListDiscounts1.size(), priceListDiscounts2.size());

		for (int i = 0; i < priceListDiscounts1.size(); i++) {
			PriceListDiscount priceListDiscount1 = priceListDiscounts1.get(i);
			PriceListDiscount priceListDiscount2 = priceListDiscounts2.get(i);

			assertEquals(priceListDiscount1, priceListDiscount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceListDiscount> priceListDiscounts1,
		List<PriceListDiscount> priceListDiscounts2) {

		Assert.assertEquals(
			priceListDiscounts1.size(), priceListDiscounts2.size());

		for (PriceListDiscount priceListDiscount1 : priceListDiscounts1) {
			boolean contains = false;

			for (PriceListDiscount priceListDiscount2 : priceListDiscounts2) {
				if (equals(priceListDiscount1, priceListDiscount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceListDiscounts2 + " does not contain " + priceListDiscount1,
				contains);
		}
	}

	protected void assertValid(PriceListDiscount priceListDiscount)
		throws Exception {

		boolean valid = true;

		if (priceListDiscount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListDiscount.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (priceListDiscount.getDiscountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountName", additionalAssertFieldName)) {
				if (priceListDiscount.getDiscountName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (priceListDiscount.getOrder() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListDiscount.getPriceListExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceListDiscount.getPriceListId() == null) {
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

	protected void assertValid(Page<PriceListDiscount> page) {
		boolean valid = false;

		java.util.Collection<PriceListDiscount> priceListDiscounts =
			page.getItems();

		int size = priceListDiscounts.size();

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
						PriceListDiscount.class)) {

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
		PriceListDiscount priceListDiscount1,
		PriceListDiscount priceListDiscount2) {

		if (priceListDiscount1 == priceListDiscount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListDiscount1.getDiscountExternalReferenceCode(),
						priceListDiscount2.
							getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListDiscount1.getDiscountId(),
						priceListDiscount2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListDiscount1.getDiscountName(),
						priceListDiscount2.getDiscountName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListDiscount1.getId(),
						priceListDiscount2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListDiscount1.getOrder(),
						priceListDiscount2.getOrder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListDiscount1.getPriceListExternalReferenceCode(),
						priceListDiscount2.
							getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListDiscount1.getPriceListId(),
						priceListDiscount2.getPriceListId())) {

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

		if (!(_priceListDiscountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceListDiscountResource;

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
		PriceListDiscount priceListDiscount) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceListDiscount.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountName")) {
			sb.append("'");
			sb.append(String.valueOf(priceListDiscount.getDiscountName()));
			sb.append("'");

			return sb.toString();
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
					priceListDiscount.getPriceListExternalReferenceCode()));
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

	protected PriceListDiscount randomPriceListDiscount() throws Exception {
		return new PriceListDiscount() {
			{
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				discountName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				order = RandomTestUtil.randomInt();
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceListDiscount randomIrrelevantPriceListDiscount()
		throws Exception {

		PriceListDiscount randomIrrelevantPriceListDiscount =
			randomPriceListDiscount();

		return randomIrrelevantPriceListDiscount;
	}

	protected PriceListDiscount randomPatchPriceListDiscount()
		throws Exception {

		return randomPriceListDiscount();
	}

	protected PriceListDiscountResource priceListDiscountResource;
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
		BasePriceListDiscountResourceTestCase.class);

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
		PriceListDiscountResource _priceListDiscountResource;

}