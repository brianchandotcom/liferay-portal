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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardThreadResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardThreadSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseMessageBoardThreadResourceTestCase {

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

		_messageBoardThreadResource.setContextCompany(testCompany);

		MessageBoardThreadResource.Builder builder =
			MessageBoardThreadResource.builder();

		messageBoardThreadResource = builder.authentication(
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

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();

		String json = objectMapper.writeValueAsString(messageBoardThread1);

		MessageBoardThread messageBoardThread2 = MessageBoardThreadSerDes.toDTO(
			json);

		Assert.assertTrue(equals(messageBoardThread1, messageBoardThread2));
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

		MessageBoardThread messageBoardThread = randomMessageBoardThread();

		String json1 = objectMapper.writeValueAsString(messageBoardThread);
		String json2 = MessageBoardThreadSerDes.toJSON(messageBoardThread);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MessageBoardThread messageBoardThread = randomMessageBoardThread();

		messageBoardThread.setArticleBody(regex);
		messageBoardThread.setEncodingFormat(regex);
		messageBoardThread.setFriendlyUrlPath(regex);
		messageBoardThread.setHeadline(regex);
		messageBoardThread.setThreadType(regex);

		String json = MessageBoardThreadSerDes.toJSON(messageBoardThread);

		Assert.assertFalse(json.contains(regex));

		messageBoardThread = MessageBoardThreadSerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardThread.getArticleBody());
		Assert.assertEquals(regex, messageBoardThread.getEncodingFormat());
		Assert.assertEquals(regex, messageBoardThread.getFriendlyUrlPath());
		Assert.assertEquals(regex, messageBoardThread.getHeadline());
		Assert.assertEquals(regex, messageBoardThread.getThreadType());
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPage()
		throws Exception {

		Page<MessageBoardThread> page =
			messageBoardThreadResource.
				getMessageBoardSectionMessageBoardThreadsPage(
					testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId(),
					RandomTestUtil.randomString(), null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();
		Long irrelevantMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getIrrelevantMessageBoardSectionId();

		if (irrelevantMessageBoardSectionId != null) {
			MessageBoardThread irrelevantMessageBoardThread =
				testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
					irrelevantMessageBoardSectionId,
					randomIrrelevantMessageBoardThread());

			page =
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						irrelevantMessageBoardSectionId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardThread),
				(List<MessageBoardThread>)page.getItems());
			assertValid(page);
		}

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		page =
			messageBoardThreadResource.
				getMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)page.getItems());
		assertValid(page);

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread1.getId());

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread2.getId());
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();

		messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						messageBoardSectionId, null, null,
						getFilterString(
							entityField, "between", messageBoardThread1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						messageBoardSectionId, null, null,
						getFilterString(entityField, "eq", messageBoardThread1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithPagination()
		throws Exception {

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread3 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, randomMessageBoardThread());

		Page<MessageBoardThread> page1 =
			messageBoardThreadResource.
				getMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, null,
					Pagination.of(1, 2), null);

		List<MessageBoardThread> messageBoardThreads1 =
			(List<MessageBoardThread>)page1.getItems();

		Assert.assertEquals(
			messageBoardThreads1.toString(), 2, messageBoardThreads1.size());

		Page<MessageBoardThread> page2 =
			messageBoardThreadResource.
				getMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardThread> messageBoardThreads2 =
			(List<MessageBoardThread>)page2.getItems();

		Assert.assertEquals(
			messageBoardThreads2.toString(), 1, messageBoardThreads2.size());

		Page<MessageBoardThread> page3 =
			messageBoardThreadResource.
				getMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardThread1, messageBoardThread2, messageBoardThread3),
			(List<MessageBoardThread>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardSectionMessageBoardThreadsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortInteger()
		throws Exception {

		testGetMessageBoardSectionMessageBoardThreadsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardThread2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortString()
		throws Exception {

		testGetMessageBoardSectionMessageBoardThreadsPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				Class<?> clazz = messageBoardThread1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetMessageBoardSectionMessageBoardThreadsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardThread, MessageBoardThread, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardThread1, messageBoardThread2);
		}

		messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread1);

		messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						messageBoardSectionId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				messageBoardThreadResource.
					getMessageBoardSectionMessageBoardThreadsPage(
						messageBoardSectionId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	protected MessageBoardThread
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
		throws Exception {

		return messageBoardThreadResource.
			postMessageBoardSectionMessageBoardThread(
				messageBoardSectionId, messageBoardThread);
	}

	protected Long
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSectionMessageBoardThreadsPage_getIrrelevantMessageBoardSectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardSectionMessageBoardThread()
		throws Exception {

		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread postMessageBoardThread =
			testPostMessageBoardSectionMessageBoardThread_addMessageBoardThread(
				randomMessageBoardThread);

		assertEquals(randomMessageBoardThread, postMessageBoardThread);
		assertValid(postMessageBoardThread);
	}

	protected MessageBoardThread
			testPostMessageBoardSectionMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		return messageBoardThreadResource.
			postMessageBoardSectionMessageBoardThread(
				testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId(),
				messageBoardThread);
	}

	@Test
	public void testGetMessageBoardThreadsRankedPage() throws Exception {
		Page<MessageBoardThread> page =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				RandomTestUtil.nextDate(), RandomTestUtil.nextDate(), null,
				Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		page = messageBoardThreadResource.getMessageBoardThreadsRankedPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)page.getItems());
		assertValid(page);

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread1.getId());

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread2.getId());
	}

	@Test
	public void testGetMessageBoardThreadsRankedPageWithPagination()
		throws Exception {

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		MessageBoardThread messageBoardThread3 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		Page<MessageBoardThread> page1 =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardThread> messageBoardThreads1 =
			(List<MessageBoardThread>)page1.getItems();

		Assert.assertEquals(
			messageBoardThreads1.toString(), 2, messageBoardThreads1.size());

		Page<MessageBoardThread> page2 =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardThread> messageBoardThreads2 =
			(List<MessageBoardThread>)page2.getItems();

		Assert.assertEquals(
			messageBoardThreads2.toString(), 1, messageBoardThreads2.size());

		Page<MessageBoardThread> page3 =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardThread1, messageBoardThread2, messageBoardThread3),
			(List<MessageBoardThread>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardThreadsRankedPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortInteger()
		throws Exception {

		testGetMessageBoardThreadsRankedPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardThread2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortString()
		throws Exception {

		testGetMessageBoardThreadsRankedPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				Class<?> clazz = messageBoardThread1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetMessageBoardThreadsRankedPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardThread, MessageBoardThread, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardThread1, messageBoardThread2);
		}

		messageBoardThread1 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				messageBoardThread1);

		messageBoardThread2 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				messageBoardThreadResource.getMessageBoardThreadsRankedPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				messageBoardThreadResource.getMessageBoardThreadsRankedPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	protected MessageBoardThread
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteMessageBoardThread() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testDeleteMessageBoardThread_addMessageBoardThread();

		assertHttpResponseStatusCode(
			204,
			messageBoardThreadResource.deleteMessageBoardThreadHttpResponse(
				messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.getMessageBoardThreadHttpResponse(
				messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.getMessageBoardThreadHttpResponse(0L));
	}

	protected MessageBoardThread
			testDeleteMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testGraphQLDeleteMessageBoardThread() throws Exception {
		MessageBoardThread messageBoardThread =
			testGraphQLMessageBoardThread_addMessageBoardThread();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteMessageBoardThread",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardThreadId",
									messageBoardThread.getId());
							}
						})),
				"JSONObject/data", "Object/deleteMessageBoardThread"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"messageBoardThread",
					new HashMap<String, Object>() {
						{
							put(
								"messageBoardThreadId",
								messageBoardThread.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testGetMessageBoardThread_addMessageBoardThread();

		MessageBoardThread getMessageBoardThread =
			messageBoardThreadResource.getMessageBoardThread(
				postMessageBoardThread.getId());

		assertEquals(postMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testGetMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testGraphQLGetMessageBoardThread() throws Exception {
		MessageBoardThread messageBoardThread =
			testGraphQLMessageBoardThread_addMessageBoardThread();

		Assert.assertTrue(
			equals(
				messageBoardThread,
				MessageBoardThreadSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"messageBoardThread",
								new HashMap<String, Object>() {
									{
										put(
											"messageBoardThreadId",
											messageBoardThread.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/messageBoardThread"))));
	}

	@Test
	public void testGraphQLGetMessageBoardThreadNotFound() throws Exception {
		Long irrelevantMessageBoardThreadId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardThread",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardThreadId",
									irrelevantMessageBoardThreadId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testPatchMessageBoardThread_addMessageBoardThread();

		MessageBoardThread randomPatchMessageBoardThread =
			randomPatchMessageBoardThread();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread patchMessageBoardThread =
			messageBoardThreadResource.patchMessageBoardThread(
				postMessageBoardThread.getId(), randomPatchMessageBoardThread);

		MessageBoardThread expectedPatchMessageBoardThread =
			postMessageBoardThread.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardThread, randomPatchMessageBoardThread);

		MessageBoardThread getMessageBoardThread =
			messageBoardThreadResource.getMessageBoardThread(
				patchMessageBoardThread.getId());

		assertEquals(expectedPatchMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testPatchMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testPutMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testPutMessageBoardThread_addMessageBoardThread();

		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread putMessageBoardThread =
			messageBoardThreadResource.putMessageBoardThread(
				postMessageBoardThread.getId(), randomMessageBoardThread);

		assertEquals(randomMessageBoardThread, putMessageBoardThread);
		assertValid(putMessageBoardThread);

		MessageBoardThread getMessageBoardThread =
			messageBoardThreadResource.getMessageBoardThread(
				putMessageBoardThread.getId());

		assertEquals(randomMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testPutMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testDeleteMessageBoardThreadMyRating() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testDeleteMessageBoardThreadMyRating_addMessageBoardThread();

		assertHttpResponseStatusCode(
			204,
			messageBoardThreadResource.
				deleteMessageBoardThreadMyRatingHttpResponse(
					messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				getMessageBoardThreadMyRatingHttpResponse(
					messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				getMessageBoardThreadMyRatingHttpResponse(0L));
	}

	protected MessageBoardThread
			testDeleteMessageBoardThreadMyRating_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testGetMessageBoardThreadPermissionsPage() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testGetMessageBoardThreadPermissionsPage_addMessageBoardThread();

		Page<Permission> page =
			messageBoardThreadResource.getMessageBoardThreadPermissionsPage(
				postMessageBoardThread.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected MessageBoardThread
			testGetMessageBoardThreadPermissionsPage_addMessageBoardThread()
		throws Exception {

		return testPostSiteMessageBoardThread_addMessageBoardThread(
			randomMessageBoardThread());
	}

	@Test
	public void testPutMessageBoardThreadPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testPutMessageBoardThreadPermission_addMessageBoardThread();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			messageBoardThreadResource.
				putMessageBoardThreadPermissionHttpResponse(
					messageBoardThread.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				putMessageBoardThreadPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected MessageBoardThread
			testPutMessageBoardThreadPermission_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testPutMessageBoardThreadSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testPutMessageBoardThreadSubscribe_addMessageBoardThread();

		assertHttpResponseStatusCode(
			204,
			messageBoardThreadResource.
				putMessageBoardThreadSubscribeHttpResponse(
					messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				putMessageBoardThreadSubscribeHttpResponse(0L));
	}

	protected MessageBoardThread
			testPutMessageBoardThreadSubscribe_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testPutMessageBoardThreadUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testPutMessageBoardThreadUnsubscribe_addMessageBoardThread();

		assertHttpResponseStatusCode(
			204,
			messageBoardThreadResource.
				putMessageBoardThreadUnsubscribeHttpResponse(
					messageBoardThread.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				putMessageBoardThreadUnsubscribeHttpResponse(0L));
	}

	protected MessageBoardThread
			testPutMessageBoardThreadUnsubscribe_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testGetSiteMessageBoardThreadsPage() throws Exception {
		Page<MessageBoardThread> page =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				testGetSiteMessageBoardThreadsPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteMessageBoardThreadsPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			MessageBoardThread irrelevantMessageBoardThread =
				testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
					irrelevantSiteId, randomIrrelevantMessageBoardThread());

			page = messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				irrelevantSiteId, null, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardThread),
				(List<MessageBoardThread>)page.getItems());
			assertValid(page);
		}

		MessageBoardThread messageBoardThread1 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		page = messageBoardThreadResource.getSiteMessageBoardThreadsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)page.getItems());
		assertValid(page);

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread1.getId());

		messageBoardThreadResource.deleteMessageBoardThread(
			messageBoardThread2.getId());
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();

		messageBoardThread1 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, messageBoardThread1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				messageBoardThreadResource.getSiteMessageBoardThreadsPage(
					siteId, null, null, null,
					getFilterString(
						entityField, "between", messageBoardThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		MessageBoardThread messageBoardThread1 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread2 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				messageBoardThreadResource.getSiteMessageBoardThreadsPage(
					siteId, null, null, null,
					getFilterString(entityField, "eq", messageBoardThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		MessageBoardThread messageBoardThread1 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread3 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, randomMessageBoardThread());

		Page<MessageBoardThread> page1 =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardThread> messageBoardThreads1 =
			(List<MessageBoardThread>)page1.getItems();

		Assert.assertEquals(
			messageBoardThreads1.toString(), 2, messageBoardThreads1.size());

		Page<MessageBoardThread> page2 =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardThread> messageBoardThreads2 =
			(List<MessageBoardThread>)page2.getItems();

		Assert.assertEquals(
			messageBoardThreads2.toString(), 1, messageBoardThreads2.size());

		Page<MessageBoardThread> page3 =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardThread1, messageBoardThread2, messageBoardThread3),
			(List<MessageBoardThread>)page3.getItems());
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithSortDateTime()
		throws Exception {

		testGetSiteMessageBoardThreadsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithSortInteger()
		throws Exception {

		testGetSiteMessageBoardThreadsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				BeanUtils.setProperty(
					messageBoardThread1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardThread2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteMessageBoardThreadsPageWithSortString()
		throws Exception {

		testGetSiteMessageBoardThreadsPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardThread1, messageBoardThread2) -> {
				Class<?> clazz = messageBoardThread1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						messageBoardThread1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						messageBoardThread2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteMessageBoardThreadsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardThread, MessageBoardThread, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardThread1, messageBoardThread2);
		}

		messageBoardThread1 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, messageBoardThread1);

		messageBoardThread2 =
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				siteId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				messageBoardThreadResource.getSiteMessageBoardThreadsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				messageBoardThreadResource.getSiteMessageBoardThreadsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	protected MessageBoardThread
			testGetSiteMessageBoardThreadsPage_addMessageBoardThread(
				Long siteId, MessageBoardThread messageBoardThread)
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			siteId, messageBoardThread);
	}

	protected Long testGetSiteMessageBoardThreadsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteMessageBoardThreadsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteMessageBoardThreadsPage() throws Exception {
		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"messageBoardThreads",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject messageBoardThreadsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/messageBoardThreads");

		Assert.assertEquals(0, messageBoardThreadsJSONObject.get("totalCount"));

		MessageBoardThread messageBoardThread1 =
			testGraphQLMessageBoardThread_addMessageBoardThread();
		MessageBoardThread messageBoardThread2 =
			testGraphQLMessageBoardThread_addMessageBoardThread();

		messageBoardThreadsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/messageBoardThreads");

		Assert.assertEquals(2, messageBoardThreadsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			Arrays.asList(
				MessageBoardThreadSerDes.toDTOs(
					messageBoardThreadsJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteMessageBoardThread() throws Exception {
		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread postMessageBoardThread =
			testPostSiteMessageBoardThread_addMessageBoardThread(
				randomMessageBoardThread);

		assertEquals(randomMessageBoardThread, postMessageBoardThread);
		assertValid(postMessageBoardThread);
	}

	protected MessageBoardThread
			testPostSiteMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGetSiteMessageBoardThreadsPage_getSiteId(), messageBoardThread);
	}

	@Test
	public void testGraphQLPostSiteMessageBoardThread() throws Exception {
		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread messageBoardThread =
			testGraphQLMessageBoardThread_addMessageBoardThread(
				randomMessageBoardThread);

		Assert.assertTrue(equals(randomMessageBoardThread, messageBoardThread));
	}

	@Test
	public void testGetSiteMessageBoardThreadByFriendlyUrlPath()
		throws Exception {

		MessageBoardThread postMessageBoardThread =
			testGetSiteMessageBoardThreadByFriendlyUrlPath_addMessageBoardThread();

		MessageBoardThread getMessageBoardThread =
			messageBoardThreadResource.
				getSiteMessageBoardThreadByFriendlyUrlPath(
					postMessageBoardThread.getSiteId(),
					postMessageBoardThread.getFriendlyUrlPath());

		assertEquals(postMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testGetSiteMessageBoardThreadByFriendlyUrlPath_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Test
	public void testGraphQLGetSiteMessageBoardThreadByFriendlyUrlPath()
		throws Exception {

		MessageBoardThread messageBoardThread =
			testGraphQLMessageBoardThread_addMessageBoardThread();

		Assert.assertTrue(
			equals(
				messageBoardThread,
				MessageBoardThreadSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"messageBoardThreadByFriendlyUrlPath",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												messageBoardThread.getSiteId() +
													"\"");
										put(
											"friendlyUrlPath",
											"\"" +
												messageBoardThread.
													getFriendlyUrlPath() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/messageBoardThreadByFriendlyUrlPath"))));
	}

	@Test
	public void testGraphQLGetSiteMessageBoardThreadByFriendlyUrlPathNotFound()
		throws Exception {

		String irrelevantFriendlyUrlPath =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardThreadByFriendlyUrlPath",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"friendlyUrlPath",
									irrelevantFriendlyUrlPath);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteMessageBoardThreadPermissionsPage()
		throws Exception {

		Page<Permission> page =
			messageBoardThreadResource.getSiteMessageBoardThreadPermissionsPage(
				testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected MessageBoardThread
			testGetSiteMessageBoardThreadPermissionsPage_addMessageBoardThread()
		throws Exception {

		return testPostSiteMessageBoardThread_addMessageBoardThread(
			randomMessageBoardThread());
	}

	@Test
	public void testPutSiteMessageBoardThreadPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread =
			testPutSiteMessageBoardThreadPermission_addMessageBoardThread();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			messageBoardThreadResource.
				putSiteMessageBoardThreadPermissionHttpResponse(
					messageBoardThread.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			messageBoardThreadResource.
				putSiteMessageBoardThreadPermissionHttpResponse(
					messageBoardThread.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected MessageBoardThread
			testPutSiteMessageBoardThreadPermission_addMessageBoardThread()
		throws Exception {

		return messageBoardThreadResource.postSiteMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Test
	public void testGetMessageBoardThreadMyRating() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testGetMessageBoardThread_addMessageBoardThread();

		Rating postRating = testGetMessageBoardThreadMyRating_addRating(
			postMessageBoardThread.getId(), randomRating());

		Rating getRating =
			messageBoardThreadResource.getMessageBoardThreadMyRating(
				postMessageBoardThread.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetMessageBoardThreadMyRating_addRating(
			long messageBoardThreadId, Rating rating)
		throws Exception {

		return messageBoardThreadResource.postMessageBoardThreadMyRating(
			messageBoardThreadId, rating);
	}

	@Test
	public void testPostMessageBoardThreadMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutMessageBoardThreadMyRating() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testPutMessageBoardThread_addMessageBoardThread();

		testPutMessageBoardThreadMyRating_addRating(
			postMessageBoardThread.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating =
			messageBoardThreadResource.putMessageBoardThreadMyRating(
				postMessageBoardThread.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutMessageBoardThreadMyRating_addRating(
			long messageBoardThreadId, Rating rating)
		throws Exception {

		return messageBoardThreadResource.postMessageBoardThreadMyRating(
			messageBoardThreadId, rating);
	}

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (Field field : getDeclaredFields(clazz.getSuperclass())) {
					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected MessageBoardThread
			testGraphQLMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return testGraphQLMessageBoardThread_addMessageBoardThread(
			randomMessageBoardThread());
	}

	protected MessageBoardThread
			testGraphQLMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		JSONDeserializer<MessageBoardThread> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(MessageBoardThread.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(messageBoardThread));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteMessageBoardThread",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("messageBoardThread", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteMessageBoardThread"),
			MessageBoardThread.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MessageBoardThread messageBoardThread1,
		MessageBoardThread messageBoardThread2) {

		Assert.assertTrue(
			messageBoardThread1 + " does not equal " + messageBoardThread2,
			equals(messageBoardThread1, messageBoardThread2));
	}

	protected void assertEquals(
		List<MessageBoardThread> messageBoardThreads1,
		List<MessageBoardThread> messageBoardThreads2) {

		Assert.assertEquals(
			messageBoardThreads1.size(), messageBoardThreads2.size());

		for (int i = 0; i < messageBoardThreads1.size(); i++) {
			MessageBoardThread messageBoardThread1 = messageBoardThreads1.get(
				i);
			MessageBoardThread messageBoardThread2 = messageBoardThreads2.get(
				i);

			assertEquals(messageBoardThread1, messageBoardThread2);
		}
	}

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardThread> messageBoardThreads1,
		List<MessageBoardThread> messageBoardThreads2) {

		Assert.assertEquals(
			messageBoardThreads1.size(), messageBoardThreads2.size());

		for (MessageBoardThread messageBoardThread1 : messageBoardThreads1) {
			boolean contains = false;

			for (MessageBoardThread messageBoardThread2 :
					messageBoardThreads2) {

				if (equals(messageBoardThread1, messageBoardThread2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				messageBoardThreads2 + " does not contain " +
					messageBoardThread1,
				contains);
		}
	}

	protected void assertValid(MessageBoardThread messageBoardThread)
		throws Exception {

		boolean valid = true;

		if (messageBoardThread.getDateCreated() == null) {
			valid = false;
		}

		if (messageBoardThread.getDateModified() == null) {
			valid = false;
		}

		if (messageBoardThread.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				messageBoardThread.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (messageBoardThread.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (messageBoardThread.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (messageBoardThread.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (messageBoardThread.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"creatorStatistics", additionalAssertFieldName)) {

				if (messageBoardThread.getCreatorStatistics() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (messageBoardThread.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (messageBoardThread.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (messageBoardThread.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("hasValidAnswer", additionalAssertFieldName)) {
				if (messageBoardThread.getHasValidAnswer() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (messageBoardThread.getHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (messageBoardThread.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("locked", additionalAssertFieldName)) {
				if (messageBoardThread.getLocked() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"messageBoardSectionId", additionalAssertFieldName)) {

				if (messageBoardThread.getMessageBoardSectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardAttachments",
					additionalAssertFieldName)) {

				if (messageBoardThread.getNumberOfMessageBoardAttachments() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardMessages",
					additionalAssertFieldName)) {

				if (messageBoardThread.getNumberOfMessageBoardMessages() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (messageBoardThread.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("seen", additionalAssertFieldName)) {
				if (messageBoardThread.getSeen() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("showAsQuestion", additionalAssertFieldName)) {
				if (messageBoardThread.getShowAsQuestion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (messageBoardThread.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (messageBoardThread.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (messageBoardThread.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("threadType", additionalAssertFieldName)) {
				if (messageBoardThread.getThreadType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewCount", additionalAssertFieldName)) {
				if (messageBoardThread.getViewCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (messageBoardThread.getViewableBy() == null) {
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

	protected void assertValid(Page<MessageBoardThread> page) {
		boolean valid = false;

		java.util.Collection<MessageBoardThread> messageBoardThreads =
			page.getItems();

		int size = messageBoardThreads.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Rating rating) {
		boolean valid = true;

		if (rating.getDateCreated() == null) {
			valid = false;
		}

		if (rating.getDateModified() == null) {
			valid = false;
		}

		if (rating.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (rating.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (rating.getBestRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (rating.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (rating.getRatingValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (rating.getWorstRating() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalRatingAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.MessageBoardThread.
						class)) {

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

	protected boolean equals(
		MessageBoardThread messageBoardThread1,
		MessageBoardThread messageBoardThread2) {

		if (messageBoardThread1 == messageBoardThread2) {
			return true;
		}

		if (!Objects.equals(
				messageBoardThread1.getSiteId(),
				messageBoardThread2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)messageBoardThread1.getActions(),
						(Map)messageBoardThread2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getAggregateRating(),
						messageBoardThread2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getArticleBody(),
						messageBoardThread2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getCreator(),
						messageBoardThread2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"creatorStatistics", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.getCreatorStatistics(),
						messageBoardThread2.getCreatorStatistics())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getCustomFields(),
						messageBoardThread2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getDateCreated(),
						messageBoardThread2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getDateModified(),
						messageBoardThread2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getEncodingFormat(),
						messageBoardThread2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getFriendlyUrlPath(),
						messageBoardThread2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("hasValidAnswer", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getHasValidAnswer(),
						messageBoardThread2.getHasValidAnswer())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getHeadline(),
						messageBoardThread2.getHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getId(),
						messageBoardThread2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getKeywords(),
						messageBoardThread2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("locked", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getLocked(),
						messageBoardThread2.getLocked())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"messageBoardSectionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.getMessageBoardSectionId(),
						messageBoardThread2.getMessageBoardSectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardAttachments",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.
							getNumberOfMessageBoardAttachments(),
						messageBoardThread2.
							getNumberOfMessageBoardAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardMessages",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.getNumberOfMessageBoardMessages(),
						messageBoardThread2.
							getNumberOfMessageBoardMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getRelatedContents(),
						messageBoardThread2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("seen", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getSeen(),
						messageBoardThread2.getSeen())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("showAsQuestion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getShowAsQuestion(),
						messageBoardThread2.getShowAsQuestion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getSubscribed(),
						messageBoardThread2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.getTaxonomyCategoryBriefs(),
						messageBoardThread2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardThread1.getTaxonomyCategoryIds(),
						messageBoardThread2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("threadType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getThreadType(),
						messageBoardThread2.getThreadType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getViewCount(),
						messageBoardThread2.getViewCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardThread1.getViewableBy(),
						messageBoardThread2.getViewableBy())) {

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

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getActions(), rating2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getBestRating(), rating2.getBestRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getCreator(), rating2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateCreated(), rating2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateModified(), rating2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(rating1.getId(), rating2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getRatingValue(), rating2.getRatingValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getWorstRating(), rating2.getWorstRating())) {

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

		if (!(_messageBoardThreadResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_messageBoardThreadResource;

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
		MessageBoardThread messageBoardThread) {

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

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getArticleBody()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creatorStatistics")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardThread.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardThread.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardThread.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardThread.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardThread.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardThread.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hasValidAnswer")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getHeadline()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("locked")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("messageBoardSectionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfMessageBoardAttachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfMessageBoardMessages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("seen")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("showAsQuestion")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscribed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("threadType")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getThreadType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
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

	protected MessageBoardThread randomMessageBoardThread() throws Exception {
		return new MessageBoardThread() {
			{
				articleBody = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hasValidAnswer = RandomTestUtil.randomBoolean();
				headline = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				locked = RandomTestUtil.randomBoolean();
				messageBoardSectionId = RandomTestUtil.randomLong();
				numberOfMessageBoardAttachments = RandomTestUtil.randomInt();
				numberOfMessageBoardMessages = RandomTestUtil.randomInt();
				seen = RandomTestUtil.randomBoolean();
				showAsQuestion = RandomTestUtil.randomBoolean();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
				threadType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				viewCount = RandomTestUtil.randomLong();
			}
		};
	}

	protected MessageBoardThread randomIrrelevantMessageBoardThread()
		throws Exception {

		MessageBoardThread randomIrrelevantMessageBoardThread =
			randomMessageBoardThread();

		randomIrrelevantMessageBoardThread.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantMessageBoardThread;
	}

	protected MessageBoardThread randomPatchMessageBoardThread()
		throws Exception {

		return randomMessageBoardThread();
	}

	protected Rating randomRating() throws Exception {
		return new Rating() {
			{
				bestRating = RandomTestUtil.randomDouble();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				ratingValue = RandomTestUtil.randomDouble();
				worstRating = RandomTestUtil.randomDouble();
			}
		};
	}

	protected MessageBoardThreadResource messageBoardThreadResource;
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
		BaseMessageBoardThreadResourceTestCase.class);

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
		com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource
			_messageBoardThreadResource;

}