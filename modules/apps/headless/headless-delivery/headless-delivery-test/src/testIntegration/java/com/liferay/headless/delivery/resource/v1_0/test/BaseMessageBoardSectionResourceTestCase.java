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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardSectionSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
import java.util.Locale;
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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
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

		return invokePostSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
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

		return invokePostSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
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
			return MessageBoardSectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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

		return invokePostSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	protected MessageBoardSection invokePatchMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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
			return MessageBoardSectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePatchMessageBoardSectionResponse(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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

		return invokePostSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	protected MessageBoardSection invokePutMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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
			return MessageBoardSectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutMessageBoardSectionResponse(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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

		messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null,
					getFilterString(
						entityField, "between", messageBoardSection1),
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

		return invokePostMessageBoardSectionMessageBoardSection(
			parentMessageBoardSectionId, messageBoardSection);
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

		return Page.of(string, MessageBoardSectionSerDes::toDTO);
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

		return invokePostMessageBoardSectionMessageBoardSection(
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId(),
			messageBoardSection);
	}

	protected MessageBoardSection
			invokePostMessageBoardSectionMessageBoardSection(
				Long parentMessageBoardSectionId,
				MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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
			return MessageBoardSectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
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

	@Test
	public void testGetSiteMessageBoardSectionsPage() throws Exception {
		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteMessageBoardSectionsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			MessageBoardSection irrelevantMessageBoardSection =
				testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
					irrelevantSiteId, randomIrrelevantMessageBoardSection());

			Page<MessageBoardSection> page =
				invokeGetSiteMessageBoardSectionsPage(
					irrelevantSiteId, null, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardSection),
				(List<MessageBoardSection>)page.getItems());
			assertValid(page);
		}

		MessageBoardSection messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		Page<MessageBoardSection> page = invokeGetSiteMessageBoardSectionsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			(List<MessageBoardSection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();

		messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null,
					getFilterString(
						entityField, "between", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> page =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null,
					getFilterString(entityField, "eq", messageBoardSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardSection1),
				(List<MessageBoardSection>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		MessageBoardSection messageBoardSection3 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, randomMessageBoardSection());

		Page<MessageBoardSection> page1 = invokeGetSiteMessageBoardSectionsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardSection> messageBoardSections1 =
			(List<MessageBoardSection>)page1.getItems();

		Assert.assertEquals(
			messageBoardSections1.toString(), 2, messageBoardSections1.size());

		Page<MessageBoardSection> page2 = invokeGetSiteMessageBoardSectionsPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

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
	public void testGetSiteMessageBoardSectionsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection1);

		messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardSection1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardSection2, entityField.getName(), "Bbb");
		}

		messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection1);

		messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				invokeGetSiteMessageBoardSectionsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardSection2, messageBoardSection1),
				(List<MessageBoardSection>)descPage.getItems());
		}
	}

	protected MessageBoardSection
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				Long siteId, MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostSiteMessageBoardSection(siteId, messageBoardSection);
	}

	protected Long testGetSiteMessageBoardSectionsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteMessageBoardSectionsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<MessageBoardSection> invokeGetSiteMessageBoardSectionsPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/message-board-sections", siteId);

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

		return Page.of(string, MessageBoardSectionSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteMessageBoardSectionsPageResponse(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/message-board-sections", siteId);

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
	public void testPostSiteMessageBoardSection() throws Exception {
		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection postMessageBoardSection =
			testPostSiteMessageBoardSection_addMessageBoardSection(
				randomMessageBoardSection);

		assertEquals(randomMessageBoardSection, postMessageBoardSection);
		assertValid(postMessageBoardSection);
	}

	protected MessageBoardSection
			testPostSiteMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		return invokePostSiteMessageBoardSection(
			testGetSiteMessageBoardSectionsPage_getSiteId(),
			messageBoardSection);
	}

	protected MessageBoardSection invokePostSiteMessageBoardSection(
			Long siteId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/message-board-sections", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return MessageBoardSectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteMessageBoardSectionResponse(
			Long siteId, MessageBoardSection messageBoardSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			MessageBoardSectionSerDes.toJSON(messageBoardSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/message-board-sections", siteId);

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
		boolean valid = true;

		if (messageBoardSection.getDateCreated() == null) {
			valid = false;
		}

		if (messageBoardSection.getDateModified() == null) {
			valid = false;
		}

		if (messageBoardSection.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				messageBoardSection.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (messageBoardSection.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (messageBoardSection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardSections",
					additionalAssertFieldName)) {

				if (messageBoardSection.getNumberOfMessageBoardSections() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardThreads", additionalAssertFieldName)) {

				if (messageBoardSection.getNumberOfMessageBoardThreads() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (messageBoardSection.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (messageBoardSection.getViewableBy() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		MessageBoardSection messageBoardSection1,
		MessageBoardSection messageBoardSection2) {

		if (messageBoardSection1 == messageBoardSection2) {
			return true;
		}

		if (!Objects.equals(
				messageBoardSection1.getSiteId(),
				messageBoardSection2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getCreator(),
						messageBoardSection2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getDateCreated(),
						messageBoardSection2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getDateModified(),
						messageBoardSection2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getDescription(),
						messageBoardSection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getId(),
						messageBoardSection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardSections",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardSection1.getNumberOfMessageBoardSections(),
						messageBoardSection2.
							getNumberOfMessageBoardSections())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardThreads", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardSection1.getNumberOfMessageBoardThreads(),
						messageBoardSection2.
							getNumberOfMessageBoardThreads())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getTitle(),
						messageBoardSection2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getViewableBy(),
						messageBoardSection2.getViewableBy())) {

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

		if (entityFieldName.equals("creator")) {
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
							messageBoardSection.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardSection.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardSection.getDateCreated()));
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
							messageBoardSection.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardSection.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardSection.getDateModified()));
			}

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

		if (entityFieldName.equals("siteId")) {
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
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected MessageBoardSection randomIrrelevantMessageBoardSection() {
		MessageBoardSection randomIrrelevantMessageBoardSection =
			randomMessageBoardSection();

		randomIrrelevantMessageBoardSection.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantMessageBoardSection;
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

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

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