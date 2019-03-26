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

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.collaboration.dto.v1_0.DiscussionThread;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionThreadResource;
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
public abstract class BaseDiscussionThreadResourceTestCase {

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
			"http://localhost:8080/o/headless-collaboration/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			DiscussionThread irrelevantDiscussionThread =
				testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
					irrelevantContentSpaceId,
					randomIrrelevantDiscussionThread());

			Page<DiscussionThread> page =
				invokeGetContentSpaceDiscussionThreadsPage(
					irrelevantContentSpaceId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionThread),
				(List<DiscussionThread>)page.getItems());
			assertValid(page);
		}

		DiscussionThread discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		DiscussionThread discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		Page<DiscussionThread> page =
			invokeGetContentSpaceDiscussionThreadsPage(
				contentSpaceId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionThread1, discussionThread2),
			(List<DiscussionThread>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread1);

		Thread.sleep(1000);

		discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> page =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", discussionThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionThread1),
				(List<DiscussionThread>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();

		DiscussionThread discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionThread discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> page =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", discussionThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionThread1),
				(List<DiscussionThread>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();

		DiscussionThread discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		DiscussionThread discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		DiscussionThread discussionThread3 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, randomDiscussionThread());

		Page<DiscussionThread> page1 =
			invokeGetContentSpaceDiscussionThreadsPage(
				contentSpaceId, null, Pagination.of(1, 2), null);

		List<DiscussionThread> discussionThreads1 =
			(List<DiscussionThread>)page1.getItems();

		Assert.assertEquals(
			discussionThreads1.toString(), 2, discussionThreads1.size());

		Page<DiscussionThread> page2 =
			invokeGetContentSpaceDiscussionThreadsPage(
				contentSpaceId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionThread> discussionThreads2 =
			(List<DiscussionThread>)page2.getItems();

		Assert.assertEquals(
			discussionThreads2.toString(), 1, discussionThreads2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionThread1, discussionThread2, discussionThread3),
			new ArrayList<DiscussionThread>() {
				{
					addAll(discussionThreads1);
					addAll(discussionThreads2);
				}
			});
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread1);

		Thread.sleep(1000);

		discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> ascPage =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionThread1, discussionThread2),
				(List<DiscussionThread>)ascPage.getItems());

			Page<DiscussionThread> descPage =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionThread2, discussionThread1),
				(List<DiscussionThread>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionThreadsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionThreadsPage_getContentSpaceId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionThread2, entityField.getName(), "Bbb");
		}

		discussionThread1 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread1);

		discussionThread2 =
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				contentSpaceId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> ascPage =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionThread1, discussionThread2),
				(List<DiscussionThread>)ascPage.getItems());

			Page<DiscussionThread> descPage =
				invokeGetContentSpaceDiscussionThreadsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionThread2, discussionThread1),
				(List<DiscussionThread>)descPage.getItems());
		}
	}

	protected DiscussionThread
			testGetContentSpaceDiscussionThreadsPage_addDiscussionThread(
				Long contentSpaceId, DiscussionThread discussionThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceDiscussionThreadsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceDiscussionThreadsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DiscussionThread> invokeGetContentSpaceDiscussionThreadsPage(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-threads",
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

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionThread>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDiscussionThreadsPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-threads",
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
	public void testPostContentSpaceDiscussionThread() throws Exception {
		DiscussionThread randomDiscussionThread = randomDiscussionThread();

		DiscussionThread postDiscussionThread =
			testPostContentSpaceDiscussionThread_addDiscussionThread(
				randomDiscussionThread);

		assertEquals(randomDiscussionThread, postDiscussionThread);
		assertValid(postDiscussionThread);
	}

	protected DiscussionThread
			testPostContentSpaceDiscussionThread_addDiscussionThread(
				DiscussionThread discussionThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionThread invokePostContentSpaceDiscussionThread(
			Long contentSpaceId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-threads",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceDiscussionThreadResponse(
			Long contentSpaceId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-threads",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPage()
		throws Exception {

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();
		Long irrelevantDiscussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getIrrelevantDiscussionSectionId();

		if ((irrelevantDiscussionSectionId != null)) {
			DiscussionThread irrelevantDiscussionThread =
				testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
					irrelevantDiscussionSectionId,
					randomIrrelevantDiscussionThread());

			Page<DiscussionThread> page =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					irrelevantDiscussionSectionId, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionThread),
				(List<DiscussionThread>)page.getItems());
			assertValid(page);
		}

		DiscussionThread discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		DiscussionThread discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		Page<DiscussionThread> page =
			invokeGetDiscussionSectionDiscussionThreadsPage(
				discussionSectionId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionThread1, discussionThread2),
			(List<DiscussionThread>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread1);

		Thread.sleep(1000);

		discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> page =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId,
					getFilterString(entityField, "eq", discussionThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionThread1),
				(List<DiscussionThread>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();

		DiscussionThread discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionThread discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> page =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId,
					getFilterString(entityField, "eq", discussionThread1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionThread1),
				(List<DiscussionThread>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPageWithPagination()
		throws Exception {

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();

		DiscussionThread discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		DiscussionThread discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		DiscussionThread discussionThread3 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, randomDiscussionThread());

		Page<DiscussionThread> page1 =
			invokeGetDiscussionSectionDiscussionThreadsPage(
				discussionSectionId, null, Pagination.of(1, 2), null);

		List<DiscussionThread> discussionThreads1 =
			(List<DiscussionThread>)page1.getItems();

		Assert.assertEquals(
			discussionThreads1.toString(), 2, discussionThreads1.size());

		Page<DiscussionThread> page2 =
			invokeGetDiscussionSectionDiscussionThreadsPage(
				discussionSectionId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionThread> discussionThreads2 =
			(List<DiscussionThread>)page2.getItems();

		Assert.assertEquals(
			discussionThreads2.toString(), 1, discussionThreads2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionThread1, discussionThread2, discussionThread3),
			new ArrayList<DiscussionThread>() {
				{
					addAll(discussionThreads1);
					addAll(discussionThreads2);
				}
			});
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread1);

		Thread.sleep(1000);

		discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> ascPage =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionThread1, discussionThread2),
				(List<DiscussionThread>)ascPage.getItems());

			Page<DiscussionThread> descPage =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionThread2, discussionThread1),
				(List<DiscussionThread>)descPage.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionThreadsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId();

		DiscussionThread discussionThread1 = randomDiscussionThread();
		DiscussionThread discussionThread2 = randomDiscussionThread();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionThread1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionThread2, entityField.getName(), "Bbb");
		}

		discussionThread1 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread1);

		discussionThread2 =
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				discussionSectionId, discussionThread2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionThread> ascPage =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionThread1, discussionThread2),
				(List<DiscussionThread>)ascPage.getItems());

			Page<DiscussionThread> descPage =
				invokeGetDiscussionSectionDiscussionThreadsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionThread2, discussionThread1),
				(List<DiscussionThread>)descPage.getItems());
		}
	}

	protected DiscussionThread
			testGetDiscussionSectionDiscussionThreadsPage_addDiscussionThread(
				Long discussionSectionId, DiscussionThread discussionThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionSectionDiscussionThreadsPage_getDiscussionSectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionSectionDiscussionThreadsPage_getIrrelevantDiscussionSectionId()
		throws Exception {

		return null;
	}

	protected Page<DiscussionThread>
			invokeGetDiscussionSectionDiscussionThreadsPage(
				Long discussionSectionId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-threads",
					discussionSectionId);

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

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionThread>>() {
			});
	}

	protected Http.Response
			invokeGetDiscussionSectionDiscussionThreadsPageResponse(
				Long discussionSectionId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-threads",
					discussionSectionId);

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
	public void testPostDiscussionSectionDiscussionThread() throws Exception {
		DiscussionThread randomDiscussionThread = randomDiscussionThread();

		DiscussionThread postDiscussionThread =
			testPostDiscussionSectionDiscussionThread_addDiscussionThread(
				randomDiscussionThread);

		assertEquals(randomDiscussionThread, postDiscussionThread);
		assertValid(postDiscussionThread);
	}

	protected DiscussionThread
			testPostDiscussionSectionDiscussionThread_addDiscussionThread(
				DiscussionThread discussionThread)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionThread invokePostDiscussionSectionDiscussionThread(
			Long discussionSectionId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-threads",
					discussionSectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostDiscussionSectionDiscussionThreadResponse(
			Long discussionSectionId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-threads",
					discussionSectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDiscussionThread() throws Exception {
		DiscussionThread discussionThread =
			testDeleteDiscussionThread_addDiscussionThread();

		assertResponseCode(
			204,
			invokeDeleteDiscussionThreadResponse(discussionThread.getId()));

		assertResponseCode(
			404, invokeGetDiscussionThreadResponse(discussionThread.getId()));
	}

	protected DiscussionThread testDeleteDiscussionThread_addDiscussionThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDiscussionThread(Long discussionThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDiscussionThreadResponse(
			Long discussionThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionThread() throws Exception {
		DiscussionThread postDiscussionThread =
			testGetDiscussionThread_addDiscussionThread();

		DiscussionThread getDiscussionThread = invokeGetDiscussionThread(
			postDiscussionThread.getId());

		assertEquals(postDiscussionThread, getDiscussionThread);
		assertValid(getDiscussionThread);
	}

	protected DiscussionThread testGetDiscussionThread_addDiscussionThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionThread invokeGetDiscussionThread(
			Long discussionThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDiscussionThreadResponse(
			Long discussionThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchDiscussionThread() throws Exception {
		DiscussionThread postDiscussionThread =
			testPatchDiscussionThread_addDiscussionThread();

		DiscussionThread randomPatchDiscussionThread =
			randomPatchDiscussionThread();

		DiscussionThread patchDiscussionThread = invokePatchDiscussionThread(
			postDiscussionThread.getId(), randomPatchDiscussionThread);

		DiscussionThread expectedPatchDiscussionThread =
			(DiscussionThread)BeanUtils.cloneBean(postDiscussionThread);

		_beanUtilsBean.copyProperties(
			expectedPatchDiscussionThread, randomPatchDiscussionThread);

		DiscussionThread getDiscussionThread = invokeGetDiscussionThread(
			patchDiscussionThread.getId());

		assertEquals(expectedPatchDiscussionThread, getDiscussionThread);
		assertValid(getDiscussionThread);
	}

	protected DiscussionThread testPatchDiscussionThread_addDiscussionThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionThread invokePatchDiscussionThread(
			Long discussionThreadId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchDiscussionThreadResponse(
			Long discussionThreadId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDiscussionThread() throws Exception {
		DiscussionThread postDiscussionThread =
			testPutDiscussionThread_addDiscussionThread();

		DiscussionThread randomDiscussionThread = randomDiscussionThread();

		DiscussionThread putDiscussionThread = invokePutDiscussionThread(
			postDiscussionThread.getId(), randomDiscussionThread);

		assertEquals(randomDiscussionThread, putDiscussionThread);
		assertValid(putDiscussionThread);

		DiscussionThread getDiscussionThread = invokeGetDiscussionThread(
			putDiscussionThread.getId());

		assertEquals(randomDiscussionThread, getDiscussionThread);
		assertValid(getDiscussionThread);
	}

	protected DiscussionThread testPutDiscussionThread_addDiscussionThread()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionThread invokePutDiscussionThread(
			Long discussionThreadId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionThread.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDiscussionThreadResponse(
			Long discussionThreadId, DiscussionThread discussionThread)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionThread),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}",
					discussionThreadId);

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
		DiscussionThread discussionThread1,
		DiscussionThread discussionThread2) {

		Assert.assertTrue(
			discussionThread1 + " does not equal " + discussionThread2,
			equals(discussionThread1, discussionThread2));
	}

	protected void assertEquals(
		List<DiscussionThread> discussionThreads1,
		List<DiscussionThread> discussionThreads2) {

		Assert.assertEquals(
			discussionThreads1.size(), discussionThreads2.size());

		for (int i = 0; i < discussionThreads1.size(); i++) {
			DiscussionThread discussionThread1 = discussionThreads1.get(i);
			DiscussionThread discussionThread2 = discussionThreads2.get(i);

			assertEquals(discussionThread1, discussionThread2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscussionThread> discussionThreads1,
		List<DiscussionThread> discussionThreads2) {

		Assert.assertEquals(
			discussionThreads1.size(), discussionThreads2.size());

		for (DiscussionThread discussionThread1 : discussionThreads1) {
			boolean contains = false;

			for (DiscussionThread discussionThread2 : discussionThreads2) {
				if (equals(discussionThread1, discussionThread2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discussionThreads2 + " does not contain " + discussionThread1,
				contains);
		}
	}

	protected void assertValid(DiscussionThread discussionThread) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<DiscussionThread> page) {
		boolean valid = false;

		Collection<DiscussionThread> discussionThreads = page.getItems();

		int size = discussionThreads.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		DiscussionThread discussionThread1,
		DiscussionThread discussionThread2) {

		if (discussionThread1 == discussionThread2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_discussionThreadResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discussionThreadResource;

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
		DiscussionThread discussionThread) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(discussionThread.getArticleBody()));
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
			sb.append(_dateFormat.format(discussionThread.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(discussionThread.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(discussionThread.getHeadline()));
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

		if (entityFieldName.equals("numberOfDiscussionAttachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfDiscussionForumPostings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("showAsQuestion")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("threadType")) {
			sb.append("'");
			sb.append(String.valueOf(discussionThread.getThreadType()));
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

	protected DiscussionThread randomDiscussionThread() {
		return new DiscussionThread() {
			{
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				showAsQuestion = RandomTestUtil.randomBoolean();
				threadType = RandomTestUtil.randomString();
			}
		};
	}

	protected DiscussionThread randomIrrelevantDiscussionThread() {
		return randomDiscussionThread();
	}

	protected DiscussionThread randomPatchDiscussionThread() {
		return randomDiscussionThread();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;

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

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDiscussionThreadResourceTestCase.class);

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
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
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
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

	@Inject
	private DiscussionThreadResource _discussionThreadResource;

	private URL _resourceURL;

}