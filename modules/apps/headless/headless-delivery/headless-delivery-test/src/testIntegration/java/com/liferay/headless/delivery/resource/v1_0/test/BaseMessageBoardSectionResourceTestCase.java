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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
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
public abstract class BaseMessageBoardSectionResourceTestCase {

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
	public void testGetContentSpaceMessageBoardSectionsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			MessageBoardSection irrelevantMessageBoardSection =
				testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
					irrelevantContentSpaceId,
					randomIrrelevantMessageBoardSection());

			Page<MessageBoardSection> page =
				invokeGetContentSpaceMessageBoardSectionsPage(
					irrelevantContentSpaceId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSection),
				(List<MessageBoardSection>)page.getItems());
			assertValid(page);
		}

		MessageBoardSection messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		Page<MessageBoardSection> page =
			invokeGetContentSpaceMessageBoardSectionsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			(List<MessageBoardSection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceMessageBoardSectionsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection1);

		Thread.sleep(1000);

		messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardSectionsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();

		MessageBoardSection messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardSectionsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();

		MessageBoardSection messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection3 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, randomMessageBoardSection());

		Page<MessageBoardSection> page1 =
			invokeGetContentSpaceMessageBoardSectionsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardSection> messageBoardSections1 =
			(List<MessageBoardSection>)page1.getItems();

		Assert.assertEquals(
			messageBoardSections1.toString(), 2, messageBoardSections1.size());

		Page<MessageBoardSection> page2 =
			invokeGetContentSpaceMessageBoardSectionsPage(
				contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSection> messageBoardSections2 =
			(List<MessageBoardSection>)page2.getItems();

		Assert.assertEquals(
			messageBoardSections2.toString(), 1, messageBoardSections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSection1, messageBoardSection2,
				messageBoardSection3),
			new ArrayList<MessageBoardSection>() {
				{
					addAll(messageBoardSections1);
					addAll(messageBoardSections2);
				}
			});
	}

	@Test
	public void testGetContentSpaceMessageBoardSectionsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection1);

		Thread.sleep(1000);

		messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceMessageBoardSectionsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardSection2, entityField.getName(), "Bbb");
		}

		messageBoardSection1 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection1);

		messageBoardSection2 =
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				contentSpaceId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetContentSpaceMessageBoardSectionsPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	protected MessageBoardSection
			testGetContentSpaceMessageBoardSectionsPage_addMessageBoardSection(
				Long contentSpaceId, MessageBoardSection messageBoardSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceMessageBoardSectionsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceMessageBoardSectionsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<MessageBoardSection>
			invokeGetContentSpaceMessageBoardSectionsPage(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-sections",
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
			new TypeReference<Page<MessageBoardSection>>() {
			});
	}

	protected Http.Response
			invokeGetContentSpaceMessageBoardSectionsPageResponse(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-sections",
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
	public void testPostContentSpaceMessageBoardSection() throws Exception {
		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection postMessageBoardSection =
			testPostContentSpaceMessageBoardSection_addMessageBoardSection(
				randomMessageBoardSection);

		assertEquals(randomMessageBoardSection, postMessageBoardSection);
		assertValid(postMessageBoardSection);
	}

	protected MessageBoardSection
			testPostContentSpaceMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardSection invokePostContentSpaceMessageBoardSection(
			Long contentSpaceId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-sections",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceMessageBoardSectionResponse(
			Long contentSpaceId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/message-board-sections",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteMessageBoardSection() throws Exception {
		MessageBoardSection messageBoardSection =
			testDeleteMessageBoardSection_addMessageBoardSection();

		assertResponseCode(
			204,
			invokeDeleteMessageBoardSectionResponse(
				messageBoardSection.getId()));

		assertResponseCode(
			404,
			invokeGetMessageBoardSectionResponse(messageBoardSection.getId()));
	}

	protected MessageBoardSection
			testDeleteMessageBoardSection_addMessageBoardSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteMessageBoardSection(Long messageBoardSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteMessageBoardSectionResponse(
			Long messageBoardSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testGetMessageBoardSection_addMessageBoardSection();

		MessageBoardSection getMessageBoardSection =
			invokeGetMessageBoardSection(postMessageBoardSection.getId());

		assertEquals(postMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testGetMessageBoardSection_addMessageBoardSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardSection invokeGetMessageBoardSection(
			Long messageBoardSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetMessageBoardSectionResponse(
			Long messageBoardSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testPatchMessageBoardSection_addMessageBoardSection();

		MessageBoardSection randomPatchMessageBoardSection =
			randomPatchMessageBoardSection();

		MessageBoardSection patchMessageBoardSection =
			invokePatchMessageBoardSection(
				postMessageBoardSection.getId(),
				randomPatchMessageBoardSection);

		MessageBoardSection expectedPatchMessageBoardSection =
			(MessageBoardSection)BeanUtils.cloneBean(postMessageBoardSection);

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardSection, randomPatchMessageBoardSection);

		MessageBoardSection getMessageBoardSection =
			invokeGetMessageBoardSection(patchMessageBoardSection.getId());

		assertEquals(expectedPatchMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testPatchMessageBoardSection_addMessageBoardSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardSection invokePatchMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchMessageBoardSectionResponse(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testPutMessageBoardSection_addMessageBoardSection();

		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection putMessageBoardSection =
			invokePutMessageBoardSection(
				postMessageBoardSection.getId(), randomMessageBoardSection);

		assertEquals(randomMessageBoardSection, putMessageBoardSection);
		assertValid(putMessageBoardSection);

		MessageBoardSection getMessageBoardSection =
			invokeGetMessageBoardSection(putMessageBoardSection.getId());

		assertEquals(randomMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testPutMessageBoardSection_addMessageBoardSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardSection invokePutMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutMessageBoardSectionResponse(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{messageBoardSectionId}",
					messageBoardSectionId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPage()
		throws Exception {

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();
		Long irrelevantParentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getIrrelevantParentMessageBoardSectionId();

		if ((irrelevantParentMessageBoardSectionId != null)) {
			MessageBoardSection irrelevantMessageBoardSection =
				testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
					irrelevantParentMessageBoardSectionId,
					randomIrrelevantMessageBoardSection());

			Page<MessageBoardSection> page =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					irrelevantParentMessageBoardSectionId, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSection),
				(List<MessageBoardSection>)page.getItems());
			assertValid(page);
		}

		MessageBoardSection messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		Page<MessageBoardSection> page =
			invokeGetMessageBoardSectionMessageBoardSectionsPage(
				parentMessageBoardSectionId, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			(List<MessageBoardSection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection1);

		Thread.sleep(1000);

		messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null,
					getFilterString(entityField, "eq", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null,
					getFilterString(entityField, "eq", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithPagination()
		throws Exception {

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection3 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, randomMessageBoardSection());

		Page<MessageBoardSection> page1 =
			invokeGetMessageBoardSectionMessageBoardSectionsPage(
				parentMessageBoardSectionId, null, null, Pagination.of(1, 2),
				null);

		List<MessageBoardSection> messageBoardSections1 =
			(List<MessageBoardSection>)page1.getItems();

		Assert.assertEquals(
			messageBoardSections1.toString(), 2, messageBoardSections1.size());

		Page<MessageBoardSection> page2 =
			invokeGetMessageBoardSectionMessageBoardSectionsPage(
				parentMessageBoardSectionId, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSection> messageBoardSections2 =
			(List<MessageBoardSection>)page2.getItems();

		Assert.assertEquals(
			messageBoardSections2.toString(), 1, messageBoardSections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSection1, messageBoardSection2,
				messageBoardSection3),
			new ArrayList<MessageBoardSection>() {
				{
					addAll(messageBoardSections1);
					addAll(messageBoardSections2);
				}
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection1);

		Thread.sleep(1000);

		messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardSection2, entityField.getName(), "Bbb");
		}

		messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection1);

		messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	protected MessageBoardSection
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				Long parentMessageBoardSectionId,
				MessageBoardSection messageBoardSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardSectionMessageBoardSectionsPage_getIrrelevantParentMessageBoardSectionId()
		throws Exception {

		return null;
	}

	protected Page<MessageBoardSection>
			invokeGetMessageBoardSectionMessageBoardSectionsPage(
				Long parentMessageBoardSectionId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections",
					parentMessageBoardSectionId);

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
			new TypeReference<Page<MessageBoardSection>>() {
			});
	}

	protected Http.Response
			invokeGetMessageBoardSectionMessageBoardSectionsPageResponse(
				Long parentMessageBoardSectionId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections",
					parentMessageBoardSectionId);

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
	public void testPostMessageBoardSectionMessageBoardSection()
		throws Exception {

		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection postMessageBoardSection =
			testPostMessageBoardSectionMessageBoardSection_addMessageBoardSection(
				randomMessageBoardSection);

		assertEquals(randomMessageBoardSection, postMessageBoardSection);
		assertValid(postMessageBoardSection);
	}

	protected MessageBoardSection
			testPostMessageBoardSectionMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardSection
			invokePostMessageBoardSectionMessageBoardSection(
				Long parentMessageBoardSectionId,
				MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections",
					parentMessageBoardSectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, MessageBoardSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostMessageBoardSectionMessageBoardSectionResponse(
				Long parentMessageBoardSectionId,
				MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections",
					parentMessageBoardSectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		MessageBoardSection messageBoardSection1,
		MessageBoardSection messageBoardSection2) {

		Assert.assertTrue(
			messageBoardSection1 + " does not equal " + messageBoardSection2,
			equals(messageBoardSection1, messageBoardSection2));
	}

	protected void assertEquals(
		List<MessageBoardSection> messageBoardSections1,
		List<MessageBoardSection> messageBoardSections2) {

		Assert.assertEquals(
			messageBoardSections1.size(), messageBoardSections2.size());

		for (int i = 0; i < messageBoardSections1.size(); i++) {
			MessageBoardSection messageBoardSection1 =
				messageBoardSections1.get(i);
			MessageBoardSection messageBoardSection2 =
				messageBoardSections2.get(i);

			assertEquals(messageBoardSection1, messageBoardSection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardSection> messageBoardSections1,
		List<MessageBoardSection> messageBoardSections2) {

		Assert.assertEquals(
			messageBoardSections1.size(), messageBoardSections2.size());

		for (MessageBoardSection messageBoardSection1 : messageBoardSections1) {
			boolean contains = false;

			for (MessageBoardSection messageBoardSection2 :
					messageBoardSections2) {

				if (equals(messageBoardSection1, messageBoardSection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				messageBoardSections2 + " does not contain " +
					messageBoardSection1,
				contains);
		}
	}

	protected void assertValid(MessageBoardSection messageBoardSection) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<MessageBoardSection> page) {
		boolean valid = false;

		Collection<MessageBoardSection> messageBoardSections = page.getItems();

		int size = messageBoardSections.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		MessageBoardSection messageBoardSection1,
		MessageBoardSection messageBoardSection2) {

		if (messageBoardSection1 == messageBoardSection2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_messageBoardSectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_messageBoardSectionResource;

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
		MessageBoardSection messageBoardSection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(messageBoardSection.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(
				_dateFormat.format(messageBoardSection.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardSection.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfMessageBoardSections")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfMessageBoardThreads")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardSection.getTitle()));
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

	protected MessageBoardSection randomMessageBoardSection() {
		return new MessageBoardSection() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected MessageBoardSection randomIrrelevantMessageBoardSection() {
		return randomMessageBoardSection();
	}

	protected MessageBoardSection randomPatchMessageBoardSection() {
		return randomMessageBoardSection();
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
		BaseMessageBoardSectionResourceTestCase.class);

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
	private MessageBoardSectionResource _messageBoardSectionResource;

	private URL _resourceURL;

}