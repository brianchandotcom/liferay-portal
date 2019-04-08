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

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
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
public abstract class BaseStructuredContentResourceTestCase {

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
	public void testGetContentSpaceStructuredContentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceStructuredContentsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetContentSpaceStructuredContentsPage_addStructuredContent(
					irrelevantContentSpaceId,
					randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				invokeGetContentSpaceStructuredContentsPage(
					irrelevantContentSpaceId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page1 =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent1);

		structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceStructuredContentsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceStructuredContentsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<StructuredContent>
			invokeGetContentSpaceStructuredContentsPage(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents",
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
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceStructuredContentsPageResponse(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents",
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
	public void testPostContentSpaceStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostContentSpaceStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostContentSpaceStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokePostContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceStructuredContentResponse(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentSpaceStructuredContentByKey() throws Exception {
		StructuredContent postStructuredContent =
			testGetContentSpaceStructuredContentByKey_addStructuredContent();

		StructuredContent getStructuredContent =
			invokeGetContentSpaceStructuredContentByKey(
				postStructuredContent.getContentSpaceId(),
				postStructuredContent.getKey());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetContentSpaceStructuredContentByKey_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokeGetContentSpaceStructuredContentByKey(
			Long contentSpaceId, String key)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents/by-key/{key}",
					contentSpaceId, key);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetContentSpaceStructuredContentByKeyResponse(
			Long contentSpaceId, String key)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents/by-key/{key}",
					contentSpaceId, key);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentSpaceStructuredContentByUuid() throws Exception {
		StructuredContent postStructuredContent =
			testGetContentSpaceStructuredContentByUuid_addStructuredContent();

		StructuredContent getStructuredContent =
			invokeGetContentSpaceStructuredContentByUuid(
				postStructuredContent.getContentSpaceId(),
				postStructuredContent.getUuid());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetContentSpaceStructuredContentByUuid_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokeGetContentSpaceStructuredContentByUuid(
			Long contentSpaceId, String uuid)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents/by-uuid/{uuid}",
					contentSpaceId, uuid);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokeGetContentSpaceStructuredContentByUuidResponse(
				Long contentSpaceId, String uuid)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/structured-contents/by-uuid/{uuid}",
					contentSpaceId, uuid);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentStructureStructuredContentsPage()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();
		Long irrelevantContentStructureId =
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId();

		if ((irrelevantContentStructureId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetContentStructureStructuredContentsPage_addStructuredContent(
					irrelevantContentStructureId,
					randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				invokeGetContentStructureStructuredContentsPage(
					irrelevantContentStructureId, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithPagination()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		Page<StructuredContent> page1 =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				Long contentStructureId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentStructureStructuredContentsPage_getContentStructureId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId()
		throws Exception {

		return null;
	}

	protected Page<StructuredContent>
			invokeGetContentStructureStructuredContentsPage(
				Long contentStructureId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-structures/{contentStructureId}/structured-contents",
					contentStructureId);

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
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response
			invokeGetContentStructureStructuredContentsPageResponse(
				Long contentStructureId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-structures/{contentStructureId}/structured-contents",
					contentStructureId);

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
	public void testGetStructuredContentFolderStructuredContentsPage()
		throws Exception {

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();
		Long irrelevantStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId();

		if ((irrelevantStructuredContentFolderId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
					irrelevantStructuredContentFolderId,
					randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				invokeGetStructuredContentFolderStructuredContentsPage(
					irrelevantStructuredContentFolderId, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetStructuredContentFolderStructuredContentsPage(
				structuredContentFolderId, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithPagination()
		throws Exception {

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		Page<StructuredContent> page1 =
			invokeGetStructuredContentFolderStructuredContentsPage(
				structuredContentFolderId, null, null, Pagination.of(1, 2),
				null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetStructuredContentFolderStructuredContentsPage(
				structuredContentFolderId, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		Thread.sleep(1000);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId()
		throws Exception {

		return null;
	}

	protected Page<StructuredContent>
			invokeGetStructuredContentFolderStructuredContentsPage(
				Long structuredContentFolderId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-content-folders/{structuredContentFolderId}/structured-contents",
					structuredContentFolderId);

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
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response
			invokeGetStructuredContentFolderStructuredContentsPageResponse(
				Long structuredContentFolderId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-content-folders/{structuredContentFolderId}/structured-contents",
					structuredContentFolderId);

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
	public void testPostStructuredContentFolderStructuredContent()
		throws Exception {

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent
			invokePostStructuredContentFolderStructuredContent(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-content-folders/{structuredContentFolderId}/structured-contents",
					structuredContentFolderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostStructuredContentFolderStructuredContentResponse(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-content-folders/{structuredContentFolderId}/structured-contents",
					structuredContentFolderId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContent_addStructuredContent();

		assertResponseCode(
			204,
			invokeDeleteStructuredContentResponse(structuredContent.getId()));

		assertResponseCode(
			404, invokeGetStructuredContentResponse(structuredContent.getId()));
	}

	protected StructuredContent
			testDeleteStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteStructuredContent(Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteStructuredContentResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokeGetStructuredContent(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetStructuredContentResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPatchStructuredContent_addStructuredContent();

		StructuredContent randomPatchStructuredContent =
			randomPatchStructuredContent();

		StructuredContent patchStructuredContent = invokePatchStructuredContent(
			postStructuredContent.getId(), randomPatchStructuredContent);

		StructuredContent expectedPatchStructuredContent =
			(StructuredContent)BeanUtils.cloneBean(postStructuredContent);

		_beanUtilsBean.copyProperties(
			expectedPatchStructuredContent, randomPatchStructuredContent);

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			patchStructuredContent.getId());

		assertEquals(expectedPatchStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testPatchStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokePatchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchStructuredContentResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent = invokePutStructuredContent(
			postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testPutStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent invokePutStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, StructuredContent.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutStructuredContentResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}",
					structuredContentId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteStructuredContentMyRating() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContentMyRating_addStructuredContent();

		assertResponseCode(
			204,
			invokeDeleteStructuredContentMyRatingResponse(
				structuredContent.getId()));

		assertResponseCode(
			404,
			invokeGetStructuredContentMyRatingResponse(
				structuredContent.getId()));
	}

	protected StructuredContent
			testDeleteStructuredContentMyRating_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteStructuredContentMyRating(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteStructuredContentMyRatingResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokeGetStructuredContentMyRating(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

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

	protected Http.Response invokeGetStructuredContentMyRatingResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePostStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

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

	protected Http.Response invokePostStructuredContentMyRatingResponse(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePutStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

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

	protected Http.Response invokePutStructuredContentMyRatingResponse(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/my-rating",
					structuredContentId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetStructuredContentRenderedContentTemplate()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected String invokeGetStructuredContentRenderedContentTemplate(
			Long structuredContentId, Long templateId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/rendered-content/{templateId}",
					structuredContentId, templateId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return string;
	}

	protected Http.Response
			invokeGetStructuredContentRenderedContentTemplateResponse(
				Long structuredContentId, Long templateId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structuredContentId}/rendered-content/{templateId}",
					structuredContentId, templateId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertTrue(
			structuredContent1 + " does not equal " + structuredContent2,
			equals(structuredContent1, structuredContent2));
	}

	protected void assertEquals(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (int i = 0; i < structuredContents1.size(); i++) {
			StructuredContent structuredContent1 = structuredContents1.get(i);
			StructuredContent structuredContent2 = structuredContents2.get(i);

			assertEquals(structuredContent1, structuredContent2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (StructuredContent structuredContent1 : structuredContents1) {
			boolean contains = false;

			for (StructuredContent structuredContent2 : structuredContents2) {
				if (equals(structuredContent1, structuredContent2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContents2 + " does not contain " + structuredContent1,
				contains);
		}
	}

	protected void assertValid(StructuredContent structuredContent) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		Collection<StructuredContent> structuredContents = page.getItems();

		int size = structuredContents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_structuredContentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentResource;

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
		StructuredContent structuredContent) {

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

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentStructureId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(structuredContent.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(structuredContent.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			sb.append(_dateFormat.format(structuredContent.getDatePublished()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("lastReviewed")) {
			sb.append(_dateFormat.format(structuredContent.getLastReviewed()));

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("renderedContents")) {
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

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getUuid()));
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

	protected StructuredContent randomStructuredContent() {
		return new StructuredContent() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				key = RandomTestUtil.randomString();
				lastReviewed = RandomTestUtil.nextDate();
				title = RandomTestUtil.randomString();
				uuid = RandomTestUtil.randomString();
			}
		};
	}

	protected StructuredContent randomIrrelevantStructuredContent() {
		return randomStructuredContent();
	}

	protected StructuredContent randomPatchStructuredContent() {
		return randomStructuredContent();
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
		BaseStructuredContentResourceTestCase.class);

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
	private StructuredContentResource _structuredContentResource;

	private URL _resourceURL;

}