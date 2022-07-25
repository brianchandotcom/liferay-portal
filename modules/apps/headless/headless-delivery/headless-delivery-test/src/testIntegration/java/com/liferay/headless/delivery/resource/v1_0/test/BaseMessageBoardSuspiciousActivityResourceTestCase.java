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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.Field;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardSuspiciousActivityResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardSuspiciousActivitySerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseMessageBoardSuspiciousActivityResourceTestCase {

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

		_messageBoardSuspiciousActivityResource.setContextCompany(testCompany);

		MessageBoardSuspiciousActivityResource.Builder builder =
			MessageBoardSuspiciousActivityResource.builder();

		messageBoardSuspiciousActivityResource = builder.authentication(
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

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();

		String json = objectMapper.writeValueAsString(
			messageBoardSuspiciousActivity1);

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			MessageBoardSuspiciousActivitySerDes.toDTO(json);

		Assert.assertTrue(
			equals(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2));
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

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity =
			randomMessageBoardSuspiciousActivity();

		String json1 = objectMapper.writeValueAsString(
			messageBoardSuspiciousActivity);
		String json2 = MessageBoardSuspiciousActivitySerDes.toJSON(
			messageBoardSuspiciousActivity);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity =
			randomMessageBoardSuspiciousActivity();

		messageBoardSuspiciousActivity.setReason(regex);

		String json = MessageBoardSuspiciousActivitySerDes.toJSON(
			messageBoardSuspiciousActivity);

		Assert.assertFalse(json.contains(regex));

		messageBoardSuspiciousActivity =
			MessageBoardSuspiciousActivitySerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardSuspiciousActivity.getReason());
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPage()
		throws Exception {

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();
		Long irrelevantMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getIrrelevantMessageBoardMessageId();

		Page<MessageBoardSuspiciousActivity> page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					messageBoardMessageId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantMessageBoardMessageId != null) {
			MessageBoardSuspiciousActivity
				irrelevantMessageBoardSuspiciousActivity =
					testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
						irrelevantMessageBoardMessageId,
						randomIrrelevantMessageBoardSuspiciousActivity());

			page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						irrelevantMessageBoardMessageId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSuspiciousActivity),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
			assertValid(page);
		}

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					messageBoardMessageId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2),
			(List<MessageBoardSuspiciousActivity>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, messageBoardSuspiciousActivity1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						messageBoardMessageId, null, null,
						getFilterString(
							entityField, "between",
							messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						messageBoardMessageId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						messageBoardMessageId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithPagination()
		throws Exception {

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity3 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, randomMessageBoardSuspiciousActivity());

		Page<MessageBoardSuspiciousActivity> page1 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					messageBoardMessageId, null, null, null,
					Pagination.of(1, 2), null);

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities1 =
			(List<MessageBoardSuspiciousActivity>)page1.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities1.toString(), 2,
			messageBoardSuspiciousActivities1.size());

		Page<MessageBoardSuspiciousActivity> page2 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					messageBoardMessageId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities2 =
			(List<MessageBoardSuspiciousActivity>)page2.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities2.toString(), 1,
			messageBoardSuspiciousActivities2.size());

		Page<MessageBoardSuspiciousActivity> page3 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardMessageMessageBoardSuspiciousActivityPage(
					messageBoardMessageId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2,
				messageBoardSuspiciousActivity3),
			(List<MessageBoardSuspiciousActivity>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSortDouble()
		throws Exception {

		testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					0.1);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(),
					0.5);
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSortInteger()
		throws Exception {

		testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSortString()
		throws Exception {

		testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				Class<?> clazz = messageBoardSuspiciousActivity1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, MessageBoardSuspiciousActivity,
					 MessageBoardSuspiciousActivity, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			randomMessageBoardSuspiciousActivity();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2);
		}

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, messageBoardSuspiciousActivity1);

		messageBoardSuspiciousActivity2 =
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardMessageId, messageBoardSuspiciousActivity2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> ascPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						messageBoardMessageId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity1,
					messageBoardSuspiciousActivity2),
				(List<MessageBoardSuspiciousActivity>)ascPage.getItems());

			Page<MessageBoardSuspiciousActivity> descPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardMessageMessageBoardSuspiciousActivityPage(
						messageBoardMessageId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity2,
					messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)descPage.getItems());
		}
	}

	protected MessageBoardSuspiciousActivity
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				Long messageBoardMessageId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getMessageBoardMessageId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageMessageBoardSuspiciousActivityPage_getIrrelevantMessageBoardMessageId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardMessageMessageBoardSuspiciousActivityPage()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivity()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage()
		throws Exception {

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();
		Long irrelevantSuspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getIrrelevantSuspiciousActivityId();

		Page<MessageBoardSuspiciousActivity> page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					suspiciousActivityId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSuspiciousActivityId != null) {
			MessageBoardSuspiciousActivity
				irrelevantMessageBoardSuspiciousActivity =
					testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
						irrelevantSuspiciousActivityId,
						randomIrrelevantMessageBoardSuspiciousActivity());

			page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						irrelevantSuspiciousActivityId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSuspiciousActivity),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
			assertValid(page);
		}

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					suspiciousActivityId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2),
			(List<MessageBoardSuspiciousActivity>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, messageBoardSuspiciousActivity1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						suspiciousActivityId, null, null,
						getFilterString(
							entityField, "between",
							messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						suspiciousActivityId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						suspiciousActivityId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithPagination()
		throws Exception {

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity3 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		Page<MessageBoardSuspiciousActivity> page1 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					suspiciousActivityId, null, null, null, Pagination.of(1, 2),
					null);

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities1 =
			(List<MessageBoardSuspiciousActivity>)page1.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities1.toString(), 2,
			messageBoardSuspiciousActivities1.size());

		Page<MessageBoardSuspiciousActivity> page2 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					suspiciousActivityId, null, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities2 =
			(List<MessageBoardSuspiciousActivity>)page2.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities2.toString(), 1,
			messageBoardSuspiciousActivities2.size());

		Page<MessageBoardSuspiciousActivity> page3 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
					suspiciousActivityId, null, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2,
				messageBoardSuspiciousActivity3),
			(List<MessageBoardSuspiciousActivity>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSortDouble()
		throws Exception {

		testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					0.1);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(),
					0.5);
			});
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSortInteger()
		throws Exception {

		testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSortString()
		throws Exception {

		testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				Class<?> clazz = messageBoardSuspiciousActivity1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, MessageBoardSuspiciousActivity,
					 MessageBoardSuspiciousActivity, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			randomMessageBoardSuspiciousActivity();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2);
		}

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, messageBoardSuspiciousActivity1);

		messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, messageBoardSuspiciousActivity2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> ascPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						suspiciousActivityId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity1,
					messageBoardSuspiciousActivity2),
				(List<MessageBoardSuspiciousActivity>)ascPage.getItems());

			Page<MessageBoardSuspiciousActivity> descPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
						suspiciousActivityId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity2,
					messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)descPage.getItems());
		}
	}

	protected MessageBoardSuspiciousActivity
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				Long suspiciousActivityId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getSuspiciousActivityId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage_getIrrelevantSuspiciousActivityId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage()
		throws Exception {

		Long suspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_getSuspiciousActivityId();
		Long irrelevantSuspiciousActivityId =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_getIrrelevantSuspiciousActivityId();

		Page<MessageBoardSuspiciousActivity> page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
					suspiciousActivityId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSuspiciousActivityId != null) {
			MessageBoardSuspiciousActivity
				irrelevantMessageBoardSuspiciousActivity =
					testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_addMessageBoardSuspiciousActivity(
						irrelevantSuspiciousActivityId,
						randomIrrelevantMessageBoardSuspiciousActivity());

			page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
						irrelevantSuspiciousActivityId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSuspiciousActivity),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
			assertValid(page);
		}

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_addMessageBoardSuspiciousActivity(
				suspiciousActivityId, randomMessageBoardSuspiciousActivity());

		page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
					suspiciousActivityId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2),
			(List<MessageBoardSuspiciousActivity>)page.getItems());
		assertValid(page);
	}

	protected MessageBoardSuspiciousActivity
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_addMessageBoardSuspiciousActivity(
				Long suspiciousActivityId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_getSuspiciousActivityId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage_getIrrelevantSuspiciousActivityId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPage()
		throws Exception {

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getIrrelevantMessageBoardThreadId();

		Page<MessageBoardSuspiciousActivity> page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					messageBoardThreadId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantMessageBoardThreadId != null) {
			MessageBoardSuspiciousActivity
				irrelevantMessageBoardSuspiciousActivity =
					testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
						irrelevantMessageBoardThreadId,
						randomIrrelevantMessageBoardSuspiciousActivity());

			page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						irrelevantMessageBoardThreadId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSuspiciousActivity),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
			assertValid(page);
		}

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		page =
			messageBoardSuspiciousActivityResource.
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					messageBoardThreadId, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2),
			(List<MessageBoardSuspiciousActivity>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, messageBoardSuspiciousActivity1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						messageBoardThreadId, null, null,
						getFilterString(
							entityField, "between",
							messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						messageBoardThreadId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> page =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						messageBoardThreadId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSuspiciousActivity1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithPagination()
		throws Exception {

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity3 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, randomMessageBoardSuspiciousActivity());

		Page<MessageBoardSuspiciousActivity> page1 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					messageBoardThreadId, null, null, null, Pagination.of(1, 2),
					null);

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities1 =
			(List<MessageBoardSuspiciousActivity>)page1.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities1.toString(), 2,
			messageBoardSuspiciousActivities1.size());

		Page<MessageBoardSuspiciousActivity> page2 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					messageBoardThreadId, null, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities2 =
			(List<MessageBoardSuspiciousActivity>)page2.getItems();

		Assert.assertEquals(
			messageBoardSuspiciousActivities2.toString(), 1,
			messageBoardSuspiciousActivities2.size());

		Page<MessageBoardSuspiciousActivity> page3 =
			messageBoardSuspiciousActivityResource.
				getMessageBoardThreadMessageBoardSuspiciousActivityPage(
					messageBoardThreadId, null, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2,
				messageBoardSuspiciousActivity3),
			(List<MessageBoardSuspiciousActivity>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSortDouble()
		throws Exception {

		testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(),
					0.1);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(),
					0.5);
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSortInteger()
		throws Exception {

		testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					messageBoardSuspiciousActivity2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSortString()
		throws Exception {

		testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardSuspiciousActivity1,
			 messageBoardSuspiciousActivity2) -> {

				Class<?> clazz = messageBoardSuspiciousActivity1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						messageBoardSuspiciousActivity2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, MessageBoardSuspiciousActivity,
					 MessageBoardSuspiciousActivity, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId();

		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
			randomMessageBoardSuspiciousActivity();
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
			randomMessageBoardSuspiciousActivity();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2);
		}

		messageBoardSuspiciousActivity1 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, messageBoardSuspiciousActivity1);

		messageBoardSuspiciousActivity2 =
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				messageBoardThreadId, messageBoardSuspiciousActivity2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSuspiciousActivity> ascPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						messageBoardThreadId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity1,
					messageBoardSuspiciousActivity2),
				(List<MessageBoardSuspiciousActivity>)ascPage.getItems());

			Page<MessageBoardSuspiciousActivity> descPage =
				messageBoardSuspiciousActivityResource.
					getMessageBoardThreadMessageBoardSuspiciousActivityPage(
						messageBoardThreadId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					messageBoardSuspiciousActivity2,
					messageBoardSuspiciousActivity1),
				(List<MessageBoardSuspiciousActivity>)descPage.getItems());
		}
	}

	protected MessageBoardSuspiciousActivity
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_addMessageBoardSuspiciousActivity(
				Long messageBoardThreadId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		return messageBoardSuspiciousActivityResource.
			postMessageBoardThreadMessageBoardSuspiciousActivity(
				messageBoardThreadId, messageBoardSuspiciousActivity);
	}

	protected Long
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getMessageBoardThreadId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardThreadMessageBoardSuspiciousActivityPage_getIrrelevantMessageBoardThreadId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardThreadMessageBoardSuspiciousActivity()
		throws Exception {

		MessageBoardSuspiciousActivity randomMessageBoardSuspiciousActivity =
			randomMessageBoardSuspiciousActivity();

		MessageBoardSuspiciousActivity postMessageBoardSuspiciousActivity =
			testPostMessageBoardThreadMessageBoardSuspiciousActivity_addMessageBoardSuspiciousActivity(
				randomMessageBoardSuspiciousActivity);

		assertEquals(
			randomMessageBoardSuspiciousActivity,
			postMessageBoardSuspiciousActivity);
		assertValid(postMessageBoardSuspiciousActivity);
	}

	protected MessageBoardSuspiciousActivity
			testPostMessageBoardThreadMessageBoardSuspiciousActivity_addMessageBoardSuspiciousActivity(
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		return messageBoardSuspiciousActivityResource.
			postMessageBoardThreadMessageBoardSuspiciousActivity(
				testGetMessageBoardThreadMessageBoardSuspiciousActivitiesPage_getMessageBoardThreadId(),
				messageBoardSuspiciousActivity);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity,
		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities) {

		boolean contains = false;

		for (MessageBoardSuspiciousActivity item :
				messageBoardSuspiciousActivities) {

			if (equals(messageBoardSuspiciousActivity, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			messageBoardSuspiciousActivities + " does not contain " +
				messageBoardSuspiciousActivity,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1,
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2) {

		Assert.assertTrue(
			messageBoardSuspiciousActivity1 + " does not equal " +
				messageBoardSuspiciousActivity2,
			equals(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2));
	}

	protected void assertEquals(
		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities1,
		List<MessageBoardSuspiciousActivity>
			messageBoardSuspiciousActivities2) {

		Assert.assertEquals(
			messageBoardSuspiciousActivities1.size(),
			messageBoardSuspiciousActivities2.size());

		for (int i = 0; i < messageBoardSuspiciousActivities1.size(); i++) {
			MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 =
				messageBoardSuspiciousActivities1.get(i);
			MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2 =
				messageBoardSuspiciousActivities2.get(i);

			assertEquals(
				messageBoardSuspiciousActivity1,
				messageBoardSuspiciousActivity2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities1,
		List<MessageBoardSuspiciousActivity>
			messageBoardSuspiciousActivities2) {

		Assert.assertEquals(
			messageBoardSuspiciousActivities1.size(),
			messageBoardSuspiciousActivities2.size());

		for (MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1 :
				messageBoardSuspiciousActivities1) {

			boolean contains = false;

			for (MessageBoardSuspiciousActivity
					messageBoardSuspiciousActivity2 :
						messageBoardSuspiciousActivities2) {

				if (equals(
						messageBoardSuspiciousActivity1,
						messageBoardSuspiciousActivity2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				messageBoardSuspiciousActivities2 + " does not contain " +
					messageBoardSuspiciousActivity1,
				contains);
		}
	}

	protected void assertValid(
			MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("messageId", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getMessageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("reason", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getReason() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"suspiciousActivityId", additionalAssertFieldName)) {

				if (messageBoardSuspiciousActivity.getSuspiciousActivityId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("threadId", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getThreadId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("validated", additionalAssertFieldName)) {
				if (messageBoardSuspiciousActivity.getValidated() == null) {
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

	protected void assertValid(Page<MessageBoardSuspiciousActivity> page) {
		boolean valid = false;

		java.util.Collection<MessageBoardSuspiciousActivity>
			messageBoardSuspiciousActivities = page.getItems();

		int size = messageBoardSuspiciousActivities.size();

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
					com.liferay.headless.delivery.dto.v1_0.
						MessageBoardSuspiciousActivity.class)) {

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
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity1,
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity2) {

		if (messageBoardSuspiciousActivity1 ==
				messageBoardSuspiciousActivity2) {

			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)messageBoardSuspiciousActivity1.getActions(),
						(Map)messageBoardSuspiciousActivity2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getCreateDate(),
						messageBoardSuspiciousActivity2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("messageId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getMessageId(),
						messageBoardSuspiciousActivity2.getMessageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getModifiedDate(),
						messageBoardSuspiciousActivity2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("reason", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getReason(),
						messageBoardSuspiciousActivity2.getReason())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"suspiciousActivityId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.
							getSuspiciousActivityId(),
						messageBoardSuspiciousActivity2.
							getSuspiciousActivityId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("threadId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getThreadId(),
						messageBoardSuspiciousActivity2.getThreadId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getUserId(),
						messageBoardSuspiciousActivity2.getUserId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("validated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSuspiciousActivity1.getValidated(),
						messageBoardSuspiciousActivity2.getValidated())) {

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

		if (!(_messageBoardSuspiciousActivityResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_messageBoardSuspiciousActivityResource;

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
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity) {

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

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardSuspiciousActivity.getCreateDate(),
							-2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardSuspiciousActivity.getCreateDate(),
							2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(
						messageBoardSuspiciousActivity.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("messageId")) {
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
						DateUtils.addSeconds(
							messageBoardSuspiciousActivity.getModifiedDate(),
							-2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardSuspiciousActivity.getModifiedDate(),
							2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(
						messageBoardSuspiciousActivity.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("reason")) {
			sb.append("'");
			sb.append(
				String.valueOf(messageBoardSuspiciousActivity.getReason()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("suspiciousActivityId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("threadId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("validated")) {
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

	protected MessageBoardSuspiciousActivity
			randomMessageBoardSuspiciousActivity()
		throws Exception {

		return new MessageBoardSuspiciousActivity() {
			{
				createDate = RandomTestUtil.nextDate();
				messageId = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				reason = StringUtil.toLowerCase(RandomTestUtil.randomString());
				suspiciousActivityId = RandomTestUtil.randomLong();
				threadId = RandomTestUtil.randomLong();
				userId = RandomTestUtil.randomLong();
				validated = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected MessageBoardSuspiciousActivity
			randomIrrelevantMessageBoardSuspiciousActivity()
		throws Exception {

		MessageBoardSuspiciousActivity
			randomIrrelevantMessageBoardSuspiciousActivity =
				randomMessageBoardSuspiciousActivity();

		return randomIrrelevantMessageBoardSuspiciousActivity;
	}

	protected MessageBoardSuspiciousActivity
			randomPatchMessageBoardSuspiciousActivity()
		throws Exception {

		return randomMessageBoardSuspiciousActivity();
	}

	protected MessageBoardSuspiciousActivityResource
		messageBoardSuspiciousActivityResource;
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
		LogFactoryUtil.getLog(
			BaseMessageBoardSuspiciousActivityResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.delivery.resource.v1_0.
		MessageBoardSuspiciousActivityResource
			_messageBoardSuspiciousActivityResource;

}