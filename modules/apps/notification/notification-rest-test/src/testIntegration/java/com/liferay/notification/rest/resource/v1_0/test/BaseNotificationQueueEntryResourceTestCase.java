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

package com.liferay.notification.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.notification.rest.client.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.client.http.HttpInvoker;
import com.liferay.notification.rest.client.pagination.Page;
import com.liferay.notification.rest.client.pagination.Pagination;
import com.liferay.notification.rest.client.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.notification.rest.client.serdes.v1_0.NotificationQueueEntrySerDes;
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

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseNotificationQueueEntryResourceTestCase {

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

		_notificationQueueEntryResource.setContextCompany(testCompany);

		NotificationQueueEntryResource.Builder builder =
			NotificationQueueEntryResource.builder();

		notificationQueueEntryResource = builder.authentication(
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

		NotificationQueueEntry notificationQueueEntry1 =
			randomNotificationQueueEntry();

		String json = objectMapper.writeValueAsString(notificationQueueEntry1);

		NotificationQueueEntry notificationQueueEntry2 =
			NotificationQueueEntrySerDes.toDTO(json);

		Assert.assertTrue(
			equals(notificationQueueEntry1, notificationQueueEntry2));
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

		NotificationQueueEntry notificationQueueEntry =
			randomNotificationQueueEntry();

		String json1 = objectMapper.writeValueAsString(notificationQueueEntry);
		String json2 = NotificationQueueEntrySerDes.toJSON(
			notificationQueueEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		NotificationQueueEntry notificationQueueEntry =
			randomNotificationQueueEntry();

		notificationQueueEntry.setBcc(regex);
		notificationQueueEntry.setBody(regex);
		notificationQueueEntry.setCc(regex);
		notificationQueueEntry.setFrom(regex);
		notificationQueueEntry.setFromName(regex);
		notificationQueueEntry.setSubject(regex);
		notificationQueueEntry.setTo(regex);
		notificationQueueEntry.setToName(regex);
		notificationQueueEntry.setTriggerBy(regex);
		notificationQueueEntry.setType(regex);

		String json = NotificationQueueEntrySerDes.toJSON(
			notificationQueueEntry);

		Assert.assertFalse(json.contains(regex));

		notificationQueueEntry = NotificationQueueEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, notificationQueueEntry.getBcc());
		Assert.assertEquals(regex, notificationQueueEntry.getBody());
		Assert.assertEquals(regex, notificationQueueEntry.getCc());
		Assert.assertEquals(regex, notificationQueueEntry.getFrom());
		Assert.assertEquals(regex, notificationQueueEntry.getFromName());
		Assert.assertEquals(regex, notificationQueueEntry.getSubject());
		Assert.assertEquals(regex, notificationQueueEntry.getTo());
		Assert.assertEquals(regex, notificationQueueEntry.getToName());
		Assert.assertEquals(regex, notificationQueueEntry.getTriggerBy());
		Assert.assertEquals(regex, notificationQueueEntry.getType());
	}

	@Test
	public void testGetNotificationQueueEntriesPage() throws Exception {
		Page<NotificationQueueEntry> page =
			notificationQueueEntryResource.getNotificationQueueEntriesPage(
				null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		NotificationQueueEntry notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		NotificationQueueEntry notificationQueueEntry2 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		page = notificationQueueEntryResource.getNotificationQueueEntriesPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			notificationQueueEntry1,
			(List<NotificationQueueEntry>)page.getItems());
		assertContains(
			notificationQueueEntry2,
			(List<NotificationQueueEntry>)page.getItems());
		assertValid(page);

		notificationQueueEntryResource.deleteNotificationQueueEntry(
			notificationQueueEntry1.getId());

		notificationQueueEntryResource.deleteNotificationQueueEntry(
			notificationQueueEntry2.getId());
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationQueueEntry notificationQueueEntry1 =
			randomNotificationQueueEntry();

		notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				notificationQueueEntry1);

		for (EntityField entityField : entityFields) {
			Page<NotificationQueueEntry> page =
				notificationQueueEntryResource.getNotificationQueueEntriesPage(
					null,
					getFilterString(
						entityField, "between", notificationQueueEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationQueueEntry1),
				(List<NotificationQueueEntry>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationQueueEntry notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationQueueEntry notificationQueueEntry2 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		for (EntityField entityField : entityFields) {
			Page<NotificationQueueEntry> page =
				notificationQueueEntryResource.getNotificationQueueEntriesPage(
					null,
					getFilterString(entityField, "eq", notificationQueueEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationQueueEntry1),
				(List<NotificationQueueEntry>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationQueueEntry notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationQueueEntry notificationQueueEntry2 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		for (EntityField entityField : entityFields) {
			Page<NotificationQueueEntry> page =
				notificationQueueEntryResource.getNotificationQueueEntriesPage(
					null,
					getFilterString(entityField, "eq", notificationQueueEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationQueueEntry1),
				(List<NotificationQueueEntry>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithPagination()
		throws Exception {

		Page<NotificationQueueEntry> totalPage =
			notificationQueueEntryResource.getNotificationQueueEntriesPage(
				null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		NotificationQueueEntry notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		NotificationQueueEntry notificationQueueEntry2 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		NotificationQueueEntry notificationQueueEntry3 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				randomNotificationQueueEntry());

		Page<NotificationQueueEntry> page1 =
			notificationQueueEntryResource.getNotificationQueueEntriesPage(
				null, null, Pagination.of(1, totalCount + 2), null);

		List<NotificationQueueEntry> notificationQueueEntries1 =
			(List<NotificationQueueEntry>)page1.getItems();

		Assert.assertEquals(
			notificationQueueEntries1.toString(), totalCount + 2,
			notificationQueueEntries1.size());

		Page<NotificationQueueEntry> page2 =
			notificationQueueEntryResource.getNotificationQueueEntriesPage(
				null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<NotificationQueueEntry> notificationQueueEntries2 =
			(List<NotificationQueueEntry>)page2.getItems();

		Assert.assertEquals(
			notificationQueueEntries2.toString(), 1,
			notificationQueueEntries2.size());

		Page<NotificationQueueEntry> page3 =
			notificationQueueEntryResource.getNotificationQueueEntriesPage(
				null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(
			notificationQueueEntry1,
			(List<NotificationQueueEntry>)page3.getItems());
		assertContains(
			notificationQueueEntry2,
			(List<NotificationQueueEntry>)page3.getItems());
		assertContains(
			notificationQueueEntry3,
			(List<NotificationQueueEntry>)page3.getItems());
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithSortDateTime()
		throws Exception {

		testGetNotificationQueueEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, notificationQueueEntry1, notificationQueueEntry2) -> {
				BeanTestUtil.setProperty(
					notificationQueueEntry1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithSortDouble()
		throws Exception {

		testGetNotificationQueueEntriesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, notificationQueueEntry1, notificationQueueEntry2) -> {
				BeanTestUtil.setProperty(
					notificationQueueEntry1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					notificationQueueEntry2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithSortInteger()
		throws Exception {

		testGetNotificationQueueEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, notificationQueueEntry1, notificationQueueEntry2) -> {
				BeanTestUtil.setProperty(
					notificationQueueEntry1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					notificationQueueEntry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetNotificationQueueEntriesPageWithSortString()
		throws Exception {

		testGetNotificationQueueEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, notificationQueueEntry1, notificationQueueEntry2) -> {
				Class<?> clazz = notificationQueueEntry1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						notificationQueueEntry1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						notificationQueueEntry2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						notificationQueueEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						notificationQueueEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						notificationQueueEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						notificationQueueEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetNotificationQueueEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, NotificationQueueEntry, NotificationQueueEntry,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationQueueEntry notificationQueueEntry1 =
			randomNotificationQueueEntry();
		NotificationQueueEntry notificationQueueEntry2 =
			randomNotificationQueueEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, notificationQueueEntry1, notificationQueueEntry2);
		}

		notificationQueueEntry1 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				notificationQueueEntry1);

		notificationQueueEntry2 =
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				notificationQueueEntry2);

		for (EntityField entityField : entityFields) {
			Page<NotificationQueueEntry> ascPage =
				notificationQueueEntryResource.getNotificationQueueEntriesPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(notificationQueueEntry1, notificationQueueEntry2),
				(List<NotificationQueueEntry>)ascPage.getItems());

			Page<NotificationQueueEntry> descPage =
				notificationQueueEntryResource.getNotificationQueueEntriesPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(notificationQueueEntry2, notificationQueueEntry1),
				(List<NotificationQueueEntry>)descPage.getItems());
		}
	}

	protected NotificationQueueEntry
			testGetNotificationQueueEntriesPage_addNotificationQueueEntry(
				NotificationQueueEntry notificationQueueEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetNotificationQueueEntriesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"notificationQueueEntries",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject notificationQueueEntriesJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/notificationQueueEntries");

		long totalCount = notificationQueueEntriesJSONObject.getLong(
			"totalCount");

		NotificationQueueEntry notificationQueueEntry1 =
			testGraphQLGetNotificationQueueEntriesPage_addNotificationQueueEntry();
		NotificationQueueEntry notificationQueueEntry2 =
			testGraphQLGetNotificationQueueEntriesPage_addNotificationQueueEntry();

		notificationQueueEntriesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/notificationQueueEntries");

		Assert.assertEquals(
			totalCount + 2,
			notificationQueueEntriesJSONObject.getLong("totalCount"));

		assertContains(
			notificationQueueEntry1,
			Arrays.asList(
				NotificationQueueEntrySerDes.toDTOs(
					notificationQueueEntriesJSONObject.getString("items"))));
		assertContains(
			notificationQueueEntry2,
			Arrays.asList(
				NotificationQueueEntrySerDes.toDTOs(
					notificationQueueEntriesJSONObject.getString("items"))));
	}

	protected NotificationQueueEntry
			testGraphQLGetNotificationQueueEntriesPage_addNotificationQueueEntry()
		throws Exception {

		return testGraphQLNotificationQueueEntry_addNotificationQueueEntry();
	}

	@Test
	public void testDeleteNotificationQueueEntry() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationQueueEntry notificationQueueEntry =
			testDeleteNotificationQueueEntry_addNotificationQueueEntry();

		assertHttpResponseStatusCode(
			204,
			notificationQueueEntryResource.
				deleteNotificationQueueEntryHttpResponse(
					notificationQueueEntry.getId()));

		assertHttpResponseStatusCode(
			404,
			notificationQueueEntryResource.
				getNotificationQueueEntryHttpResponse(
					notificationQueueEntry.getId()));

		assertHttpResponseStatusCode(
			404,
			notificationQueueEntryResource.
				getNotificationQueueEntryHttpResponse(0L));
	}

	protected NotificationQueueEntry
			testDeleteNotificationQueueEntry_addNotificationQueueEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteNotificationQueueEntry() throws Exception {
		NotificationQueueEntry notificationQueueEntry =
			testGraphQLDeleteNotificationQueueEntry_addNotificationQueueEntry();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteNotificationQueueEntry",
						new HashMap<String, Object>() {
							{
								put(
									"notificationQueueEntryId",
									notificationQueueEntry.getId());
							}
						})),
				"JSONObject/data", "Object/deleteNotificationQueueEntry"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"notificationQueueEntry",
					new HashMap<String, Object>() {
						{
							put(
								"notificationQueueEntryId",
								notificationQueueEntry.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected NotificationQueueEntry
			testGraphQLDeleteNotificationQueueEntry_addNotificationQueueEntry()
		throws Exception {

		return testGraphQLNotificationQueueEntry_addNotificationQueueEntry();
	}

	@Test
	public void testGetNotificationQueueEntry() throws Exception {
		NotificationQueueEntry postNotificationQueueEntry =
			testGetNotificationQueueEntry_addNotificationQueueEntry();

		NotificationQueueEntry getNotificationQueueEntry =
			notificationQueueEntryResource.getNotificationQueueEntry(
				postNotificationQueueEntry.getId());

		assertEquals(postNotificationQueueEntry, getNotificationQueueEntry);
		assertValid(getNotificationQueueEntry);
	}

	protected NotificationQueueEntry
			testGetNotificationQueueEntry_addNotificationQueueEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetNotificationQueueEntry() throws Exception {
		NotificationQueueEntry notificationQueueEntry =
			testGraphQLGetNotificationQueueEntry_addNotificationQueueEntry();

		Assert.assertTrue(
			equals(
				notificationQueueEntry,
				NotificationQueueEntrySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"notificationQueueEntry",
								new HashMap<String, Object>() {
									{
										put(
											"notificationQueueEntryId",
											notificationQueueEntry.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/notificationQueueEntry"))));
	}

	@Test
	public void testGraphQLGetNotificationQueueEntryNotFound()
		throws Exception {

		Long irrelevantNotificationQueueEntryId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"notificationQueueEntry",
						new HashMap<String, Object>() {
							{
								put(
									"notificationQueueEntryId",
									irrelevantNotificationQueueEntryId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected NotificationQueueEntry
			testGraphQLGetNotificationQueueEntry_addNotificationQueueEntry()
		throws Exception {

		return testGraphQLNotificationQueueEntry_addNotificationQueueEntry();
	}

	@Test
	public void testPutNotificationQueueEntryResend() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationQueueEntry notificationQueueEntry =
			testPutNotificationQueueEntryResend_addNotificationQueueEntry();

		assertHttpResponseStatusCode(
			204,
			notificationQueueEntryResource.
				putNotificationQueueEntryResendHttpResponse(
					notificationQueueEntry.getId()));

		assertHttpResponseStatusCode(
			404,
			notificationQueueEntryResource.
				putNotificationQueueEntryResendHttpResponse(0L));
	}

	protected NotificationQueueEntry
			testPutNotificationQueueEntryResend_addNotificationQueueEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected NotificationQueueEntry
			testGraphQLNotificationQueueEntry_addNotificationQueueEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		NotificationQueueEntry notificationQueueEntry,
		List<NotificationQueueEntry> notificationQueueEntries) {

		boolean contains = false;

		for (NotificationQueueEntry item : notificationQueueEntries) {
			if (equals(notificationQueueEntry, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			notificationQueueEntries + " does not contain " +
				notificationQueueEntry,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		NotificationQueueEntry notificationQueueEntry1,
		NotificationQueueEntry notificationQueueEntry2) {

		Assert.assertTrue(
			notificationQueueEntry1 + " does not equal " +
				notificationQueueEntry2,
			equals(notificationQueueEntry1, notificationQueueEntry2));
	}

	protected void assertEquals(
		List<NotificationQueueEntry> notificationQueueEntries1,
		List<NotificationQueueEntry> notificationQueueEntries2) {

		Assert.assertEquals(
			notificationQueueEntries1.size(), notificationQueueEntries2.size());

		for (int i = 0; i < notificationQueueEntries1.size(); i++) {
			NotificationQueueEntry notificationQueueEntry1 =
				notificationQueueEntries1.get(i);
			NotificationQueueEntry notificationQueueEntry2 =
				notificationQueueEntries2.get(i);

			assertEquals(notificationQueueEntry1, notificationQueueEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<NotificationQueueEntry> notificationQueueEntries1,
		List<NotificationQueueEntry> notificationQueueEntries2) {

		Assert.assertEquals(
			notificationQueueEntries1.size(), notificationQueueEntries2.size());

		for (NotificationQueueEntry notificationQueueEntry1 :
				notificationQueueEntries1) {

			boolean contains = false;

			for (NotificationQueueEntry notificationQueueEntry2 :
					notificationQueueEntries2) {

				if (equals(notificationQueueEntry1, notificationQueueEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				notificationQueueEntries2 + " does not contain " +
					notificationQueueEntry1,
				contains);
		}
	}

	protected void assertValid(NotificationQueueEntry notificationQueueEntry)
		throws Exception {

		boolean valid = true;

		if (notificationQueueEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (notificationQueueEntry.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bcc", additionalAssertFieldName)) {
				if (notificationQueueEntry.getBcc() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("body", additionalAssertFieldName)) {
				if (notificationQueueEntry.getBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("cc", additionalAssertFieldName)) {
				if (notificationQueueEntry.getCc() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("from", additionalAssertFieldName)) {
				if (notificationQueueEntry.getFrom() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fromName", additionalAssertFieldName)) {
				if (notificationQueueEntry.getFromName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (notificationQueueEntry.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sentDate", additionalAssertFieldName)) {
				if (notificationQueueEntry.getSentDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (notificationQueueEntry.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subject", additionalAssertFieldName)) {
				if (notificationQueueEntry.getSubject() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("to", additionalAssertFieldName)) {
				if (notificationQueueEntry.getTo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("toName", additionalAssertFieldName)) {
				if (notificationQueueEntry.getToName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("triggerBy", additionalAssertFieldName)) {
				if (notificationQueueEntry.getTriggerBy() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (notificationQueueEntry.getType() == null) {
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

	protected void assertValid(Page<NotificationQueueEntry> page) {
		boolean valid = false;

		java.util.Collection<NotificationQueueEntry> notificationQueueEntries =
			page.getItems();

		int size = notificationQueueEntries.size();

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
					com.liferay.notification.rest.dto.v1_0.
						NotificationQueueEntry.class)) {

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
		NotificationQueueEntry notificationQueueEntry1,
		NotificationQueueEntry notificationQueueEntry2) {

		if (notificationQueueEntry1 == notificationQueueEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationQueueEntry1.getActions(),
						(Map)notificationQueueEntry2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bcc", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getBcc(),
						notificationQueueEntry2.getBcc())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("body", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getBody(),
						notificationQueueEntry2.getBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("cc", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getCc(),
						notificationQueueEntry2.getCc())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("from", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getFrom(),
						notificationQueueEntry2.getFrom())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fromName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getFromName(),
						notificationQueueEntry2.getFromName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getId(),
						notificationQueueEntry2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getPriority(),
						notificationQueueEntry2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sentDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getSentDate(),
						notificationQueueEntry2.getSentDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getStatus(),
						notificationQueueEntry2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subject", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getSubject(),
						notificationQueueEntry2.getSubject())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("to", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getTo(),
						notificationQueueEntry2.getTo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("toName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getToName(),
						notificationQueueEntry2.getToName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("triggerBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getTriggerBy(),
						notificationQueueEntry2.getTriggerBy())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationQueueEntry1.getType(),
						notificationQueueEntry2.getType())) {

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

		if (!(_notificationQueueEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_notificationQueueEntryResource;

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
		NotificationQueueEntry notificationQueueEntry) {

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

		if (entityFieldName.equals("bcc")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getBcc()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("body")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getBody()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("cc")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getCc()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("from")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getFrom()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fromName")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getFromName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(notificationQueueEntry.getPriority()));

			return sb.toString();
		}

		if (entityFieldName.equals("sentDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							notificationQueueEntry.getSentDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							notificationQueueEntry.getSentDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(notificationQueueEntry.getSentDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			sb.append(String.valueOf(notificationQueueEntry.getStatus()));

			return sb.toString();
		}

		if (entityFieldName.equals("subject")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getSubject()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("to")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getTo()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("toName")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getToName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("triggerBy")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getTriggerBy()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(notificationQueueEntry.getType()));
			sb.append("'");

			return sb.toString();
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

	protected NotificationQueueEntry randomNotificationQueueEntry()
		throws Exception {

		return new NotificationQueueEntry() {
			{
				bcc = StringUtil.toLowerCase(RandomTestUtil.randomString());
				body = StringUtil.toLowerCase(RandomTestUtil.randomString());
				cc = StringUtil.toLowerCase(RandomTestUtil.randomString());
				from = StringUtil.toLowerCase(RandomTestUtil.randomString());
				fromName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
				sentDate = RandomTestUtil.nextDate();
				status = RandomTestUtil.randomInt();
				subject = StringUtil.toLowerCase(RandomTestUtil.randomString());
				to = StringUtil.toLowerCase(RandomTestUtil.randomString());
				toName = StringUtil.toLowerCase(RandomTestUtil.randomString());
				triggerBy = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected NotificationQueueEntry randomIrrelevantNotificationQueueEntry()
		throws Exception {

		NotificationQueueEntry randomIrrelevantNotificationQueueEntry =
			randomNotificationQueueEntry();

		return randomIrrelevantNotificationQueueEntry;
	}

	protected NotificationQueueEntry randomPatchNotificationQueueEntry()
		throws Exception {

		return randomNotificationQueueEntry();
	}

	protected NotificationQueueEntryResource notificationQueueEntryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

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
		LogFactoryUtil.getLog(BaseNotificationQueueEntryResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.notification.rest.resource.v1_0.
			NotificationQueueEntryResource _notificationQueueEntryResource;

}