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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceEntryResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceEntrySerDes;
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
public abstract class BasePriceEntryResourceTestCase {

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

		_priceEntryResource.setContextCompany(testCompany);

		PriceEntryResource.Builder builder = PriceEntryResource.builder();

		priceEntryResource = builder.authentication(
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

		PriceEntry priceEntry1 = randomPriceEntry();

		String json = objectMapper.writeValueAsString(priceEntry1);

		PriceEntry priceEntry2 = PriceEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(priceEntry1, priceEntry2));
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

		PriceEntry priceEntry = randomPriceEntry();

		String json1 = objectMapper.writeValueAsString(priceEntry);
		String json2 = PriceEntrySerDes.toJSON(priceEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceEntry priceEntry = randomPriceEntry();

		priceEntry.setDiscountLevelsFormatted(regex);
		priceEntry.setExternalReferenceCode(regex);
		priceEntry.setPriceFormatted(regex);
		priceEntry.setPriceListExternalReferenceCode(regex);
		priceEntry.setSkuExternalReferenceCode(regex);

		String json = PriceEntrySerDes.toJSON(priceEntry);

		Assert.assertFalse(json.contains(regex));

		priceEntry = PriceEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, priceEntry.getDiscountLevelsFormatted());
		Assert.assertEquals(regex, priceEntry.getExternalReferenceCode());
		Assert.assertEquals(regex, priceEntry.getPriceFormatted());
		Assert.assertEquals(
			regex, priceEntry.getPriceListExternalReferenceCode());
		Assert.assertEquals(regex, priceEntry.getSkuExternalReferenceCode());
	}

	@Test
	public void testDeletePriceEntryByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetPriceEntryByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetPriceEntryByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetPriceEntryByExternalReferenceCodeNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPatchPriceEntryByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeletePriceEntry() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeletePriceEntry() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetPriceEntry() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetPriceEntry() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetPriceEntryNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPatchPriceEntry() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceEntriesPage()
		throws Exception {

		Page<PriceEntry> page =
			priceEntryResource.
				getPriceListByExternalReferenceCodePriceEntriesPage(
					testGetPriceListByExternalReferenceCodePriceEntriesPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_getIrrelevantExternalReferenceCode();

		if (irrelevantExternalReferenceCode != null) {
			PriceEntry irrelevantPriceEntry =
				testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceEntry());

			page =
				priceEntryResource.
					getPriceListByExternalReferenceCodePriceEntriesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceEntry),
				(List<PriceEntry>)page.getItems());
			assertValid(page);
		}

		PriceEntry priceEntry1 =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				externalReferenceCode, randomPriceEntry());

		PriceEntry priceEntry2 =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				externalReferenceCode, randomPriceEntry());

		page =
			priceEntryResource.
				getPriceListByExternalReferenceCodePriceEntriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceEntry1, priceEntry2),
			(List<PriceEntry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceEntriesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_getExternalReferenceCode();

		PriceEntry priceEntry1 =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				externalReferenceCode, randomPriceEntry());

		PriceEntry priceEntry2 =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				externalReferenceCode, randomPriceEntry());

		PriceEntry priceEntry3 =
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				externalReferenceCode, randomPriceEntry());

		Page<PriceEntry> page1 =
			priceEntryResource.
				getPriceListByExternalReferenceCodePriceEntriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceEntry> priceEntries1 = (List<PriceEntry>)page1.getItems();

		Assert.assertEquals(priceEntries1.toString(), 2, priceEntries1.size());

		Page<PriceEntry> page2 =
			priceEntryResource.
				getPriceListByExternalReferenceCodePriceEntriesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceEntry> priceEntries2 = (List<PriceEntry>)page2.getItems();

		Assert.assertEquals(priceEntries2.toString(), 1, priceEntries2.size());

		Page<PriceEntry> page3 =
			priceEntryResource.
				getPriceListByExternalReferenceCodePriceEntriesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(priceEntry1, priceEntry2, priceEntry3),
			(List<PriceEntry>)page3.getItems());
	}

	protected PriceEntry
			testGetPriceListByExternalReferenceCodePriceEntriesPage_addPriceEntry(
				String externalReferenceCode, PriceEntry priceEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceEntriesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceEntriesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceEntry()
		throws Exception {

		PriceEntry randomPriceEntry = randomPriceEntry();

		PriceEntry postPriceEntry =
			testPostPriceListByExternalReferenceCodePriceEntry_addPriceEntry(
				randomPriceEntry);

		assertEquals(randomPriceEntry, postPriceEntry);
		assertValid(postPriceEntry);
	}

	protected PriceEntry
			testPostPriceListByExternalReferenceCodePriceEntry_addPriceEntry(
				PriceEntry priceEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceEntriesPage() throws Exception {
		Page<PriceEntry> page =
			priceEntryResource.getPriceListIdPriceEntriesPage(
				testGetPriceListIdPriceEntriesPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceEntriesPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceEntriesPage_getIrrelevantId();

		if (irrelevantId != null) {
			PriceEntry irrelevantPriceEntry =
				testGetPriceListIdPriceEntriesPage_addPriceEntry(
					irrelevantId, randomIrrelevantPriceEntry());

			page = priceEntryResource.getPriceListIdPriceEntriesPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceEntry),
				(List<PriceEntry>)page.getItems());
			assertValid(page);
		}

		PriceEntry priceEntry1 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		PriceEntry priceEntry2 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		page = priceEntryResource.getPriceListIdPriceEntriesPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceEntry1, priceEntry2),
			(List<PriceEntry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceEntriesPage_getId();

		PriceEntry priceEntry1 = randomPriceEntry();

		priceEntry1 = testGetPriceListIdPriceEntriesPage_addPriceEntry(
			id, priceEntry1);

		for (EntityField entityField : entityFields) {
			Page<PriceEntry> page =
				priceEntryResource.getPriceListIdPriceEntriesPage(
					id, null,
					getFilterString(entityField, "between", priceEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceEntry1),
				(List<PriceEntry>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceEntriesPage_getId();

		PriceEntry priceEntry1 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceEntry priceEntry2 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		for (EntityField entityField : entityFields) {
			Page<PriceEntry> page =
				priceEntryResource.getPriceListIdPriceEntriesPage(
					id, null, getFilterString(entityField, "eq", priceEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceEntry1),
				(List<PriceEntry>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceEntriesPage_getId();

		PriceEntry priceEntry1 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		PriceEntry priceEntry2 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		PriceEntry priceEntry3 =
			testGetPriceListIdPriceEntriesPage_addPriceEntry(
				id, randomPriceEntry());

		Page<PriceEntry> page1 =
			priceEntryResource.getPriceListIdPriceEntriesPage(
				id, null, null, Pagination.of(1, 2), null);

		List<PriceEntry> priceEntries1 = (List<PriceEntry>)page1.getItems();

		Assert.assertEquals(priceEntries1.toString(), 2, priceEntries1.size());

		Page<PriceEntry> page2 =
			priceEntryResource.getPriceListIdPriceEntriesPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceEntry> priceEntries2 = (List<PriceEntry>)page2.getItems();

		Assert.assertEquals(priceEntries2.toString(), 1, priceEntries2.size());

		Page<PriceEntry> page3 =
			priceEntryResource.getPriceListIdPriceEntriesPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(priceEntry1, priceEntry2, priceEntry3),
			(List<PriceEntry>)page3.getItems());
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithSortDateTime()
		throws Exception {

		testGetPriceListIdPriceEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceEntry1, priceEntry2) -> {
				BeanUtils.setProperty(
					priceEntry1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithSortInteger()
		throws Exception {

		testGetPriceListIdPriceEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceEntry1, priceEntry2) -> {
				BeanUtils.setProperty(priceEntry1, entityField.getName(), 0);
				BeanUtils.setProperty(priceEntry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListIdPriceEntriesPageWithSortString()
		throws Exception {

		testGetPriceListIdPriceEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceEntry1, priceEntry2) -> {
				Class<?> clazz = priceEntry1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceEntry1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceEntry2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListIdPriceEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, PriceEntry, PriceEntry, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceEntriesPage_getId();

		PriceEntry priceEntry1 = randomPriceEntry();
		PriceEntry priceEntry2 = randomPriceEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, priceEntry1, priceEntry2);
		}

		priceEntry1 = testGetPriceListIdPriceEntriesPage_addPriceEntry(
			id, priceEntry1);

		priceEntry2 = testGetPriceListIdPriceEntriesPage_addPriceEntry(
			id, priceEntry2);

		for (EntityField entityField : entityFields) {
			Page<PriceEntry> ascPage =
				priceEntryResource.getPriceListIdPriceEntriesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceEntry1, priceEntry2),
				(List<PriceEntry>)ascPage.getItems());

			Page<PriceEntry> descPage =
				priceEntryResource.getPriceListIdPriceEntriesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceEntry2, priceEntry1),
				(List<PriceEntry>)descPage.getItems());
		}
	}

	protected PriceEntry testGetPriceListIdPriceEntriesPage_addPriceEntry(
			Long id, PriceEntry priceEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceEntriesPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceEntriesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceEntry() throws Exception {
		PriceEntry randomPriceEntry = randomPriceEntry();

		PriceEntry postPriceEntry = testPostPriceListIdPriceEntry_addPriceEntry(
			randomPriceEntry);

		assertEquals(randomPriceEntry, postPriceEntry);
		assertValid(postPriceEntry);
	}

	protected PriceEntry testPostPriceListIdPriceEntry_addPriceEntry(
			PriceEntry priceEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		PriceEntry priceEntry1, PriceEntry priceEntry2) {

		Assert.assertTrue(
			priceEntry1 + " does not equal " + priceEntry2,
			equals(priceEntry1, priceEntry2));
	}

	protected void assertEquals(
		List<PriceEntry> priceEntries1, List<PriceEntry> priceEntries2) {

		Assert.assertEquals(priceEntries1.size(), priceEntries2.size());

		for (int i = 0; i < priceEntries1.size(); i++) {
			PriceEntry priceEntry1 = priceEntries1.get(i);
			PriceEntry priceEntry2 = priceEntries2.get(i);

			assertEquals(priceEntry1, priceEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceEntry> priceEntries1, List<PriceEntry> priceEntries2) {

		Assert.assertEquals(priceEntries1.size(), priceEntries2.size());

		for (PriceEntry priceEntry1 : priceEntries1) {
			boolean contains = false;

			for (PriceEntry priceEntry2 : priceEntries2) {
				if (equals(priceEntry1, priceEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceEntries2 + " does not contain " + priceEntry1, contains);
		}
	}

	protected void assertValid(PriceEntry priceEntry) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceEntry.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (priceEntry.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bulkPricing", additionalAssertFieldName)) {
				if (priceEntry.getBulkPricing() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (priceEntry.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountDiscovery", additionalAssertFieldName)) {

				if (priceEntry.getDiscountDiscovery() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountLevel1", additionalAssertFieldName)) {
				if (priceEntry.getDiscountLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountLevel2", additionalAssertFieldName)) {
				if (priceEntry.getDiscountLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountLevel3", additionalAssertFieldName)) {
				if (priceEntry.getDiscountLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountLevel4", additionalAssertFieldName)) {
				if (priceEntry.getDiscountLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountLevelsFormatted", additionalAssertFieldName)) {

				if (priceEntry.getDiscountLevelsFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (priceEntry.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (priceEntry.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (priceEntry.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("hasTierPrice", additionalAssertFieldName)) {
				if (priceEntry.getHasTierPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (priceEntry.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (priceEntry.getPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceEntryId", additionalAssertFieldName)) {
				if (priceEntry.getPriceEntryId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceFormatted", additionalAssertFieldName)) {
				if (priceEntry.getPriceFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceEntry.getPriceListExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceEntry.getPriceListId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("product", additionalAssertFieldName)) {
				if (priceEntry.getProduct() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (priceEntry.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (priceEntry.getSkuExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (priceEntry.getSkuId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("tierPrices", additionalAssertFieldName)) {
				if (priceEntry.getTierPrices() == null) {
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

	protected void assertValid(Page<PriceEntry> page) {
		boolean valid = false;

		java.util.Collection<PriceEntry> priceEntries = page.getItems();

		int size = priceEntries.size();

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
				getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						PriceEntry.class)) {

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

	protected boolean equals(PriceEntry priceEntry1, PriceEntry priceEntry2) {
		if (priceEntry1 == priceEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceEntry1.getActions(),
						(Map)priceEntry2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getActive(), priceEntry2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bulkPricing", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getBulkPricing(),
						priceEntry2.getBulkPricing())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceEntry1.getCustomFields(),
						(Map)priceEntry2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountDiscovery", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceEntry1.getDiscountDiscovery(),
						priceEntry2.getDiscountDiscovery())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountLevel1", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getDiscountLevel1(),
						priceEntry2.getDiscountLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountLevel2", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getDiscountLevel2(),
						priceEntry2.getDiscountLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountLevel3", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getDiscountLevel3(),
						priceEntry2.getDiscountLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountLevel4", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getDiscountLevel4(),
						priceEntry2.getDiscountLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountLevelsFormatted", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceEntry1.getDiscountLevelsFormatted(),
						priceEntry2.getDiscountLevelsFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getDisplayDate(),
						priceEntry2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getExpirationDate(),
						priceEntry2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceEntry1.getExternalReferenceCode(),
						priceEntry2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("hasTierPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getHasTierPrice(),
						priceEntry2.getHasTierPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getNeverExpire(),
						priceEntry2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getPrice(), priceEntry2.getPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceEntryId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getPriceEntryId(),
						priceEntry2.getPriceEntryId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceFormatted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getPriceFormatted(),
						priceEntry2.getPriceFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceEntry1.getPriceListExternalReferenceCode(),
						priceEntry2.getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getPriceListId(),
						priceEntry2.getPriceListId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("product", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getProduct(), priceEntry2.getProduct())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getSku(), priceEntry2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceEntry1.getSkuExternalReferenceCode(),
						priceEntry2.getSkuExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getSkuId(), priceEntry2.getSkuId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("tierPrices", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceEntry1.getTierPrices(),
						priceEntry2.getTierPrices())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_priceEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceEntryResource;

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
		EntityField entityField, String operator, PriceEntry priceEntry) {

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

		if (entityFieldName.equals("bulkPricing")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountDiscovery")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountLevelsFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(priceEntry.getDiscountLevelsFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(priceEntry.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(priceEntry.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceEntry.getDisplayDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expirationDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceEntry.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceEntry.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceEntry.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(priceEntry.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hasTierPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("price")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceEntryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(priceEntry.getPriceFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(priceEntry.getPriceListExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("product")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("skuExternalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(priceEntry.getSkuExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skuId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("tierPrices")) {
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

	protected PriceEntry randomPriceEntry() throws Exception {
		return new PriceEntry() {
			{
				active = RandomTestUtil.randomBoolean();
				bulkPricing = RandomTestUtil.randomBoolean();
				discountDiscovery = RandomTestUtil.randomBoolean();
				discountLevelsFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hasTierPrice = RandomTestUtil.randomBoolean();
				neverExpire = RandomTestUtil.randomBoolean();
				price = RandomTestUtil.randomDouble();
				priceEntryId = RandomTestUtil.randomLong();
				priceFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
				skuExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceEntry randomIrrelevantPriceEntry() throws Exception {
		PriceEntry randomIrrelevantPriceEntry = randomPriceEntry();

		return randomIrrelevantPriceEntry;
	}

	protected PriceEntry randomPatchPriceEntry() throws Exception {
		return randomPriceEntry();
	}

	protected PriceEntryResource priceEntryResource;
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

	private static final Log _log = LogFactoryUtil.getLog(
		BasePriceEntryResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.pricing.resource.v2_0.
			PriceEntryResource _priceEntryResource;

}