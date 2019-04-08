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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			MessageBoardThread irrelevantMessageBoardThread =
				testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
					irrelevantContentSpaceId,
					randomIrrelevantMessageBoardThread());

			Page<MessageBoardThread> page =
				invokeGetContentSpaceMessageBoardThreadsPage(
					irrelevantContentSpaceId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardThread),
				(List<MessageBoardThread>)page.getItems());
			assertValid(page);
		}

		MessageBoardThread messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		Page<MessageBoardThread> page =
			invokeGetContentSpaceMessageBoardThreadsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread1);

		Thread.sleep(1000);

		messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", messageBoardThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();

		MessageBoardThread messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardThread messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", messageBoardThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardThread1),
				(List<MessageBoardThread>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();

		MessageBoardThread messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		MessageBoardThread messageBoardThread3 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, randomMessageBoardThread());

		Page<MessageBoardThread> page1 =
			invokeGetContentSpaceMessageBoardThreadsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardThread> messageBoardThreads1 =
			(List<MessageBoardThread>)page1.getItems();

		Assert.assertEquals(
			messageBoardThreads1.toString(), 2, messageBoardThreads1.size());

		Page<MessageBoardThread> page2 =
			invokeGetContentSpaceMessageBoardThreadsPage(
				contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardThread> messageBoardThreads2 =
			(List<MessageBoardThread>)page2.getItems();

		Assert.assertEquals(
			messageBoardThreads2.toString(), 1, messageBoardThreads2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardThread1, messageBoardThread2, messageBoardThread3),
			new ArrayList<MessageBoardThread>() {
				{
					addAll(messageBoardThreads1);
					addAll(messageBoardThreads2);
				}
			});
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread1);

		Thread.sleep(1000);

		messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardThreadsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardThread2, entityField.getName(), "Bbb");
		}

		messageBoardThread1 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread1);

		messageBoardThread2 =
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				contentSpaceId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				invokeGetContentSpaceMessageBoardThreadsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	protected MessageBoardThread
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				Long contentSpaceId, MessageBoardThread messageBoardThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceMessageBoardThreadsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceMessageBoardThreadsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<MessageBoardThread>
			invokeGetContentSpaceMessageBoardThreadsPage(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-threads",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<MessageBoardThread>>() {
			});
	}

	protected Http.Response
			invokeGetContentSpaceMessageBoardThreadsPageResponse(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-threads",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostContentSpaceMessageBoardThread() throws Exception {
		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread postMessageBoardThread =
			testPostContentSpaceMessageBoardThread_addMessageBoardThread(
				randomMessageBoardThread);

		assertEquals(randomMessageBoardThread, postMessageBoardThread);
		assertValid(postMessageBoardThread);
	}

	protected MessageBoardThread
			testPostContentSpaceMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardThread invokePostContentSpaceMessageBoardThread(
			Long contentSpaceId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-threads",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceMessageBoardThreadResponse(
			Long contentSpaceId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-threads",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPage()
		throws Exception {

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();
		Long irrelevantMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getIrrelevantMessageBoardSectionId();

		if ((irrelevantMessageBoardSectionId != null)) {
			MessageBoardThread irrelevantMessageBoardThread =
				testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
					irrelevantMessageBoardSectionId,
					randomIrrelevantMessageBoardThread());

			Page<MessageBoardThread> page =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					irrelevantMessageBoardSectionId, null, null,
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

		Page<MessageBoardThread> page =
			invokeGetMessageBoardSectionMessageBoardThreadsPage(
				messageBoardSectionId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)page.getItems());
		assertValid(page);
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
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread1);

		Thread.sleep(1000);

		messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> page =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null,
					getFilterString(entityField, "eq", messageBoardThread1),
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
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null,
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
			invokeGetMessageBoardSectionMessageBoardThreadsPage(
				messageBoardSectionId, null, null, Pagination.of(1, 2), null);

		List<MessageBoardThread> messageBoardThreads1 =
			(List<MessageBoardThread>)page1.getItems();

		Assert.assertEquals(
			messageBoardThreads1.toString(), 2, messageBoardThreads1.size());

		Page<MessageBoardThread> page2 =
			invokeGetMessageBoardSectionMessageBoardThreadsPage(
				messageBoardSectionId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardThread> messageBoardThreads2 =
			(List<MessageBoardThread>)page2.getItems();

		Assert.assertEquals(
			messageBoardThreads2.toString(), 1, messageBoardThreads2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardThread1, messageBoardThread2, messageBoardThread3),
			new ArrayList<MessageBoardThread>() {
				{
					addAll(messageBoardThreads1);
					addAll(messageBoardThreads2);
				}
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread1);

		Thread.sleep(1000);

		messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardThread2, messageBoardThread1),
				(List<MessageBoardThread>)descPage.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardThreadsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardSectionId =
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId();

		MessageBoardThread messageBoardThread1 = randomMessageBoardThread();
		MessageBoardThread messageBoardThread2 = randomMessageBoardThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardThread1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardThread2, entityField.getName(), "Bbb");
		}

		messageBoardThread1 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread1);

		messageBoardThread2 =
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				messageBoardSectionId, messageBoardThread2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardThread> ascPage =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardThread1, messageBoardThread2),
				(List<MessageBoardThread>)ascPage.getItems());

			Page<MessageBoardThread> descPage =
				invokeGetMessageBoardSectionMessageBoardThreadsPage(
					messageBoardSectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

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

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected Page<MessageBoardThread>
			invokeGetMessageBoardSectionMessageBoardThreadsPage(
				Long messageBoardSectionId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}/message-board-threads",
					messageBoardSectionId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<MessageBoardThread>>() {
			});
	}

	protected Http.Response
			invokeGetMessageBoardSectionMessageBoardThreadsPageResponse(
				Long messageBoardSectionId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}/message-board-threads",
					messageBoardSectionId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardThread
			invokePostMessageBoardSectionMessageBoardThread(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}/message-board-threads",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostMessageBoardSectionMessageBoardThreadResponse(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}/message-board-threads",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteMessageBoardThread() throws Exception {
		MessageBoardThread messageBoardThread =
			testDeleteMessageBoardThread_addMessageBoardThread();

		assertResponseCode(
			204,
			invokeDeleteMessageBoardThreadResponse(messageBoardThread.getId()));

		assertResponseCode(
			404,
			invokeGetMessageBoardThreadResponse(messageBoardThread.getId()));
	}

	protected MessageBoardThread
			testDeleteMessageBoardThread_addMessageBoardThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteMessageBoardThread(Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteMessageBoardThreadResponse(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testGetMessageBoardThread_addMessageBoardThread();

		MessageBoardThread getMessageBoardThread = invokeGetMessageBoardThread(
			postMessageBoardThread.getId());

		assertEquals(postMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testGetMessageBoardThread_addMessageBoardThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardThread invokeGetMessageBoardThread(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetMessageBoardThreadResponse(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testPatchMessageBoardThread_addMessageBoardThread();

		MessageBoardThread randomPatchMessageBoardThread =
			randomPatchMessageBoardThread();

		MessageBoardThread patchMessageBoardThread =
			invokePatchMessageBoardThread(
				postMessageBoardThread.getId(), randomPatchMessageBoardThread);

		MessageBoardThread expectedPatchMessageBoardThread =
			(MessageBoardThread)BeanUtils.cloneBean(postMessageBoardThread);

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardThread, randomPatchMessageBoardThread);

		MessageBoardThread getMessageBoardThread = invokeGetMessageBoardThread(
			patchMessageBoardThread.getId());

		assertEquals(expectedPatchMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testPatchMessageBoardThread_addMessageBoardThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardThread invokePatchMessageBoardThread(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchMessageBoardThreadResponse(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutMessageBoardThread() throws Exception {
		MessageBoardThread postMessageBoardThread =
			testPutMessageBoardThread_addMessageBoardThread();

		MessageBoardThread randomMessageBoardThread =
			randomMessageBoardThread();

		MessageBoardThread putMessageBoardThread = invokePutMessageBoardThread(
			postMessageBoardThread.getId(), randomMessageBoardThread);

		assertEquals(randomMessageBoardThread, putMessageBoardThread);
		assertValid(putMessageBoardThread);

		MessageBoardThread getMessageBoardThread = invokeGetMessageBoardThread(
			putMessageBoardThread.getId());

		assertEquals(randomMessageBoardThread, getMessageBoardThread);
		assertValid(getMessageBoardThread);
	}

	protected MessageBoardThread
			testPutMessageBoardThread_addMessageBoardThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardThread invokePutMessageBoardThread(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutMessageBoardThreadResponse(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteMessageBoardThreadMyRating() throws Exception {
		MessageBoardThread messageBoardThread =
			testDeleteMessageBoardThreadMyRating_addMessageBoardThread();

		assertResponseCode(
			204,
			invokeDeleteMessageBoardThreadMyRatingResponse(
				messageBoardThread.getId()));

		assertResponseCode(
			404,
			invokeGetMessageBoardThreadMyRatingResponse(
				messageBoardThread.getId()));
	}

	protected MessageBoardThread
			testDeleteMessageBoardThreadMyRating_addMessageBoardThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteMessageBoardThreadMyRating(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteMessageBoardThreadMyRatingResponse(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardThreadMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokeGetMessageBoardThreadMyRating(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetMessageBoardThreadMyRatingResponse(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostMessageBoardThreadMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePostMessageBoardThreadMyRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostMessageBoardThreadMyRatingResponse(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutMessageBoardThreadMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePutMessageBoardThreadMyRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutMessageBoardThreadMyRatingResponse(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{messageBoardThreadId}/my-rating",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

	protected void assertValid(MessageBoardThread messageBoardThread) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<MessageBoardThread> page) {
		boolean valid = false;

		Collection<MessageBoardThread> messageBoardThreads = page.getItems();

		int size = messageBoardThreads.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		MessageBoardThread messageBoardThread1,
		MessageBoardThread messageBoardThread2) {

		if (messageBoardThread1 == messageBoardThread2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
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

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(messageBoardThread.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(messageBoardThread.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
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

		if (entityFieldName.equals("numberOfMessageBoardAttachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfMessageBoardMessages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("showAsQuestion")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("threadType")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardThread.getThreadType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected MessageBoardThread randomMessageBoardThread() {
		return new MessageBoardThread() {
			{
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				encodingFormat = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				showAsQuestion = RandomTestUtil.randomBoolean();
				threadType = RandomTestUtil.randomString();
			}
		};
	}

	protected MessageBoardThread randomIrrelevantMessageBoardThread() {
		return randomMessageBoardThread();
	}

	protected MessageBoardThread randomPatchMessageBoardThread() {
		return randomMessageBoardThread();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
			{
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
			}
		};

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

		return options;
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
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
	private MessageBoardThreadResource _messageBoardThreadResource;

	private URL _resourceURL;

}