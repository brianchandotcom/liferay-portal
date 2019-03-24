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

import com.liferay.headless.collaboration.dto.v1_0.DiscussionForumPosting;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionForumPostingResource;
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
public abstract class BaseDiscussionForumPostingResourceTestCase {

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
	public void testDeleteDiscussionForumPosting() throws Exception {
		DiscussionForumPosting discussionForumPosting =
			testDeleteDiscussionForumPosting_addDiscussionForumPosting();

		assertResponseCode(
			200,
			invokeDeleteDiscussionForumPostingResponse(
				discussionForumPosting.getId()));

		assertResponseCode(
			404,
			invokeGetDiscussionForumPostingResponse(
				discussionForumPosting.getId()));
	}

	protected DiscussionForumPosting
			testDeleteDiscussionForumPosting_addDiscussionForumPosting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDiscussionForumPosting(
			Long discussionForumPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);
	}

	protected Http.Response invokeDeleteDiscussionForumPostingResponse(
			Long discussionForumPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionForumPosting() throws Exception {
		DiscussionForumPosting postDiscussionForumPosting =
			testGetDiscussionForumPosting_addDiscussionForumPosting();

		DiscussionForumPosting getDiscussionForumPosting =
			invokeGetDiscussionForumPosting(postDiscussionForumPosting.getId());

		assertEquals(postDiscussionForumPosting, getDiscussionForumPosting);
		assertValid(getDiscussionForumPosting);
	}

	protected DiscussionForumPosting
			testGetDiscussionForumPosting_addDiscussionForumPosting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionForumPosting invokeGetDiscussionForumPosting(
			Long discussionForumPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionForumPosting.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDiscussionForumPostingResponse(
			Long discussionForumPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPatchDiscussionForumPosting() throws Exception {
		DiscussionForumPosting postDiscussionForumPosting =
			testPatchDiscussionForumPosting_addDiscussionForumPosting(
				randomDiscussionForumPosting());

		DiscussionForumPosting randomPatchDiscussionForumPosting =
			randomDiscussionForumPosting();

		DiscussionForumPosting patchDiscussionForumPosting =
			testPatchDiscussionForumPosting_addDiscussionForumPosting(
				randomPatchDiscussionForumPosting);

		DiscussionForumPosting expectedPatchDiscussionForumPosting =
			(DiscussionForumPosting)BeanUtils.cloneBean(
				postDiscussionForumPosting);

		_beanUtilsBean.copyProperties(
			expectedPatchDiscussionForumPosting,
			randomPatchDiscussionForumPosting);

		DiscussionForumPosting getDiscussionForumPosting =
			invokeGetDiscussionForumPosting(
				patchDiscussionForumPosting.getId());

		assertEquals(
			expectedPatchDiscussionForumPosting, getDiscussionForumPosting);
		assertValid(getDiscussionForumPosting);
	}

	protected DiscussionForumPosting
			testPatchDiscussionForumPosting_addDiscussionForumPosting(
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionForumPosting invokePatchDiscussionForumPosting(
			Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionForumPosting.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchDiscussionForumPostingResponse(
			Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPutDiscussionForumPosting() throws Exception {
		DiscussionForumPosting postDiscussionForumPosting =
			testPutDiscussionForumPosting_addDiscussionForumPosting();

		DiscussionForumPosting randomDiscussionForumPosting =
			randomDiscussionForumPosting();

		DiscussionForumPosting putDiscussionForumPosting =
			invokePutDiscussionForumPosting(
				postDiscussionForumPosting.getId(),
				randomDiscussionForumPosting);

		assertEquals(randomDiscussionForumPosting, putDiscussionForumPosting);
		assertValid(putDiscussionForumPosting);

		DiscussionForumPosting getDiscussionForumPosting =
			invokeGetDiscussionForumPosting(putDiscussionForumPosting.getId());

		assertEquals(randomDiscussionForumPosting, getDiscussionForumPosting);
		assertValid(getDiscussionForumPosting);
	}

	protected DiscussionForumPosting
			testPutDiscussionForumPosting_addDiscussionForumPosting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionForumPosting invokePutDiscussionForumPosting(
			Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionForumPosting.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDiscussionForumPostingResponse(
			Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPage()
		throws Exception {

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();
		Long irrelevantDiscussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getIrrelevantDiscussionForumPostingId();

		if ((irrelevantDiscussionForumPostingId != null)) {
			DiscussionForumPosting irrelevantDiscussionForumPosting =
				testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
					irrelevantDiscussionForumPostingId,
					randomIrrelevantDiscussionForumPosting());

			Page<DiscussionForumPosting> page =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					irrelevantDiscussionForumPostingId, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionForumPosting),
				(List<DiscussionForumPosting>)page.getItems());
			assertValid(page);
		}

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		Page<DiscussionForumPosting> page =
			invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
				discussionForumPostingId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionForumPosting1, discussionForumPosting2),
			(List<DiscussionForumPosting>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting1);

		Thread.sleep(1000);

		discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> page =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId,
					getFilterString(entityField, "eq", discussionForumPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionForumPosting1),
				(List<DiscussionForumPosting>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> page =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId,
					getFilterString(entityField, "eq", discussionForumPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionForumPosting1),
				(List<DiscussionForumPosting>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPageWithPagination()
		throws Exception {

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting3 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, randomDiscussionForumPosting());

		Page<DiscussionForumPosting> page1 =
			invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
				discussionForumPostingId, null, Pagination.of(1, 2), null);

		List<DiscussionForumPosting> discussionForumPostings1 =
			(List<DiscussionForumPosting>)page1.getItems();

		Assert.assertEquals(
			discussionForumPostings1.toString(), 2,
			discussionForumPostings1.size());

		Page<DiscussionForumPosting> page2 =
			invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
				discussionForumPostingId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionForumPosting> discussionForumPostings2 =
			(List<DiscussionForumPosting>)page2.getItems();

		Assert.assertEquals(
			discussionForumPostings2.toString(), 1,
			discussionForumPostings2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionForumPosting1, discussionForumPosting2,
				discussionForumPosting3),
			new ArrayList<DiscussionForumPosting>() {
				{
					addAll(discussionForumPostings1);
					addAll(discussionForumPostings2);
				}
			});
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting1);

		Thread.sleep(1000);

		discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> ascPage =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionForumPosting1, discussionForumPosting2),
				(List<DiscussionForumPosting>)ascPage.getItems());

			Page<DiscussionForumPosting> descPage =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionForumPosting2, discussionForumPosting1),
				(List<DiscussionForumPosting>)descPage.getItems());
		}
	}

	@Test
	public void testGetDiscussionForumPostingDiscussionForumPostingsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionForumPostingId =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionForumPosting2, entityField.getName(), "Bbb");
		}

		discussionForumPosting1 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting1);

		discussionForumPosting2 =
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionForumPostingId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> ascPage =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionForumPosting1, discussionForumPosting2),
				(List<DiscussionForumPosting>)ascPage.getItems());

			Page<DiscussionForumPosting> descPage =
				invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
					discussionForumPostingId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionForumPosting2, discussionForumPosting1),
				(List<DiscussionForumPosting>)descPage.getItems());
		}
	}

	protected DiscussionForumPosting
			testGetDiscussionForumPostingDiscussionForumPostingsPage_addDiscussionForumPosting(
				Long discussionForumPostingId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getDiscussionForumPostingId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionForumPostingDiscussionForumPostingsPage_getIrrelevantDiscussionForumPostingId()
		throws Exception {

		return null;
	}

	protected Page<DiscussionForumPosting>
			invokeGetDiscussionForumPostingDiscussionForumPostingsPage(
				Long discussionForumPostingId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings",
					discussionForumPostingId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionForumPosting>>() {
			});
	}

	protected Http.Response
			invokeGetDiscussionForumPostingDiscussionForumPostingsPageResponse(
				Long discussionForumPostingId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings",
					discussionForumPostingId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostDiscussionForumPostingDiscussionForumPosting()
		throws Exception {

		DiscussionForumPosting randomDiscussionForumPosting =
			randomDiscussionForumPosting();

		DiscussionForumPosting postDiscussionForumPosting =
			testPostDiscussionForumPostingDiscussionForumPosting_addDiscussionForumPosting(
				randomDiscussionForumPosting);

		assertEquals(randomDiscussionForumPosting, postDiscussionForumPosting);
		assertValid(postDiscussionForumPosting);
	}

	protected DiscussionForumPosting
			testPostDiscussionForumPostingDiscussionForumPosting_addDiscussionForumPosting(
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionForumPosting
			invokePostDiscussionForumPostingDiscussionForumPosting(
				Long discussionForumPostingId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionForumPosting.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostDiscussionForumPostingDiscussionForumPostingResponse(
				Long discussionForumPostingId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings",
					discussionForumPostingId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPage()
		throws Exception {

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();
		Long irrelevantDiscussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getIrrelevantDiscussionThreadId();

		if ((irrelevantDiscussionThreadId != null)) {
			DiscussionForumPosting irrelevantDiscussionForumPosting =
				testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
					irrelevantDiscussionThreadId,
					randomIrrelevantDiscussionForumPosting());

			Page<DiscussionForumPosting> page =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					irrelevantDiscussionThreadId, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionForumPosting),
				(List<DiscussionForumPosting>)page.getItems());
			assertValid(page);
		}

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		Page<DiscussionForumPosting> page =
			invokeGetDiscussionThreadDiscussionForumPostingsPage(
				discussionThreadId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionForumPosting1, discussionForumPosting2),
			(List<DiscussionForumPosting>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting1);

		Thread.sleep(1000);

		discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> page =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId,
					getFilterString(entityField, "eq", discussionForumPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionForumPosting1),
				(List<DiscussionForumPosting>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> page =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId,
					getFilterString(entityField, "eq", discussionForumPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionForumPosting1),
				(List<DiscussionForumPosting>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPageWithPagination()
		throws Exception {

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();

		DiscussionForumPosting discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		DiscussionForumPosting discussionForumPosting3 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, randomDiscussionForumPosting());

		Page<DiscussionForumPosting> page1 =
			invokeGetDiscussionThreadDiscussionForumPostingsPage(
				discussionThreadId, null, Pagination.of(1, 2), null);

		List<DiscussionForumPosting> discussionForumPostings1 =
			(List<DiscussionForumPosting>)page1.getItems();

		Assert.assertEquals(
			discussionForumPostings1.toString(), 2,
			discussionForumPostings1.size());

		Page<DiscussionForumPosting> page2 =
			invokeGetDiscussionThreadDiscussionForumPostingsPage(
				discussionThreadId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionForumPosting> discussionForumPostings2 =
			(List<DiscussionForumPosting>)page2.getItems();

		Assert.assertEquals(
			discussionForumPostings2.toString(), 1,
			discussionForumPostings2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionForumPosting1, discussionForumPosting2,
				discussionForumPosting3),
			new ArrayList<DiscussionForumPosting>() {
				{
					addAll(discussionForumPostings1);
					addAll(discussionForumPostings2);
				}
			});
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting1);

		Thread.sleep(1000);

		discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> ascPage =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionForumPosting1, discussionForumPosting2),
				(List<DiscussionForumPosting>)ascPage.getItems());

			Page<DiscussionForumPosting> descPage =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionForumPosting2, discussionForumPosting1),
				(List<DiscussionForumPosting>)descPage.getItems());
		}
	}

	@Test
	public void testGetDiscussionThreadDiscussionForumPostingsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionThreadId =
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId();

		DiscussionForumPosting discussionForumPosting1 =
			randomDiscussionForumPosting();
		DiscussionForumPosting discussionForumPosting2 =
			randomDiscussionForumPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionForumPosting1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionForumPosting2, entityField.getName(), "Bbb");
		}

		discussionForumPosting1 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting1);

		discussionForumPosting2 =
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				discussionThreadId, discussionForumPosting2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionForumPosting> ascPage =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionForumPosting1, discussionForumPosting2),
				(List<DiscussionForumPosting>)ascPage.getItems());

			Page<DiscussionForumPosting> descPage =
				invokeGetDiscussionThreadDiscussionForumPostingsPage(
					discussionThreadId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionForumPosting2, discussionForumPosting1),
				(List<DiscussionForumPosting>)descPage.getItems());
		}
	}

	protected DiscussionForumPosting
			testGetDiscussionThreadDiscussionForumPostingsPage_addDiscussionForumPosting(
				Long discussionThreadId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionThreadDiscussionForumPostingsPage_getDiscussionThreadId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionThreadDiscussionForumPostingsPage_getIrrelevantDiscussionThreadId()
		throws Exception {

		return null;
	}

	protected Page<DiscussionForumPosting>
			invokeGetDiscussionThreadDiscussionForumPostingsPage(
				Long discussionThreadId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}/discussion-forum-postings",
					discussionThreadId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionForumPosting>>() {
			});
	}

	protected Http.Response
			invokeGetDiscussionThreadDiscussionForumPostingsPageResponse(
				Long discussionThreadId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}/discussion-forum-postings",
					discussionThreadId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostDiscussionThreadDiscussionForumPosting()
		throws Exception {

		DiscussionForumPosting randomDiscussionForumPosting =
			randomDiscussionForumPosting();

		DiscussionForumPosting postDiscussionForumPosting =
			testPostDiscussionThreadDiscussionForumPosting_addDiscussionForumPosting(
				randomDiscussionForumPosting);

		assertEquals(randomDiscussionForumPosting, postDiscussionForumPosting);
		assertValid(postDiscussionForumPosting);
	}

	protected DiscussionForumPosting
			testPostDiscussionThreadDiscussionForumPosting_addDiscussionForumPosting(
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionForumPosting
			invokePostDiscussionThreadDiscussionForumPosting(
				Long discussionThreadId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}/discussion-forum-postings",
					discussionThreadId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionForumPosting.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostDiscussionThreadDiscussionForumPostingResponse(
				Long discussionThreadId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionForumPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-threads/{discussion-thread-id}/discussion-forum-postings",
					discussionThreadId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		DiscussionForumPosting discussionForumPosting1,
		DiscussionForumPosting discussionForumPosting2) {

		Assert.assertTrue(
			discussionForumPosting1 + " does not equal " +
				discussionForumPosting2,
			equals(discussionForumPosting1, discussionForumPosting2));
	}

	protected void assertEquals(
		List<DiscussionForumPosting> discussionForumPostings1,
		List<DiscussionForumPosting> discussionForumPostings2) {

		Assert.assertEquals(
			discussionForumPostings1.size(), discussionForumPostings2.size());

		for (int i = 0; i < discussionForumPostings1.size(); i++) {
			DiscussionForumPosting discussionForumPosting1 =
				discussionForumPostings1.get(i);
			DiscussionForumPosting discussionForumPosting2 =
				discussionForumPostings2.get(i);

			assertEquals(discussionForumPosting1, discussionForumPosting2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscussionForumPosting> discussionForumPostings1,
		List<DiscussionForumPosting> discussionForumPostings2) {

		Assert.assertEquals(
			discussionForumPostings1.size(), discussionForumPostings2.size());

		for (DiscussionForumPosting discussionForumPosting1 :
				discussionForumPostings1) {

			boolean contains = false;

			for (DiscussionForumPosting discussionForumPosting2 :
					discussionForumPostings2) {

				if (equals(discussionForumPosting1, discussionForumPosting2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discussionForumPostings2 + " does not contain " +
					discussionForumPosting1,
				contains);
		}
	}

	protected void assertValid(DiscussionForumPosting discussionForumPosting) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<DiscussionForumPosting> page) {
		boolean valid = false;

		Collection<DiscussionForumPosting> discussionForumPostings =
			page.getItems();

		int size = discussionForumPostings.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		DiscussionForumPosting discussionForumPosting1,
		DiscussionForumPosting discussionForumPosting2) {

		if (discussionForumPosting1 == discussionForumPosting2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_discussionForumPostingResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discussionForumPostingResource;

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
		DiscussionForumPosting discussionForumPosting) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(discussionForumPosting.getArticleBody()));
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
			sb.append(
				_dateFormat.format(discussionForumPosting.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(
				_dateFormat.format(discussionForumPosting.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(discussionForumPosting.getHeadline()));
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

		if (entityFieldName.equals("showAsAnswer")) {
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

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected DiscussionForumPosting randomDiscussionForumPosting() {
		return new DiscussionForumPosting() {
			{
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				showAsAnswer = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected DiscussionForumPosting randomIrrelevantDiscussionForumPosting() {
		return randomDiscussionForumPosting();
	}

	protected DiscussionForumPosting randomPatchDiscussionForumPosting() {
		return randomDiscussionForumPosting();
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
		BaseDiscussionForumPostingResourceTestCase.class);

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
	private DiscussionForumPostingResource _discussionForumPostingResource;

	private URL _resourceURL;

}