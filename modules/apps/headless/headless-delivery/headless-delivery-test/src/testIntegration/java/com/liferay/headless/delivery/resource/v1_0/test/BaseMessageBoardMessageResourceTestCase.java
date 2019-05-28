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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardMessageSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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
public abstract class BaseMessageBoardMessageResourceTestCase {

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

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();

		String json = objectMapper.writeValueAsString(messageBoardMessage1);

		MessageBoardMessage messageBoardMessage2 =
			MessageBoardMessageSerDes.toDTO(json);

		Assert.assertTrue(equals(messageBoardMessage1, messageBoardMessage2));
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

		MessageBoardMessage messageBoardMessage = randomMessageBoardMessage();

		String json1 = objectMapper.writeValueAsString(messageBoardMessage);
		String json2 = MessageBoardMessageSerDes.toJSON(messageBoardMessage);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testDeleteMessageBoardMessage() throws Exception {
		MessageBoardMessage messageBoardMessage =
			testDeleteMessageBoardMessage_addMessageBoardMessage();

		assertHttpResponseStatusCode(
			204,
			MessageBoardMessageResource.deleteMessageBoardMessageHttpResponse(
				messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			MessageBoardMessageResource.getMessageBoardMessageHttpResponse(
				messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			MessageBoardMessageResource.getMessageBoardMessageHttpResponse(0L));
	}

	protected MessageBoardMessage
			testDeleteMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMessageBoardMessage() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testGetMessageBoardMessage_addMessageBoardMessage();

		MessageBoardMessage getMessageBoardMessage =
			MessageBoardMessageResource.getMessageBoardMessage(
				postMessageBoardMessage.getId());

		assertEquals(postMessageBoardMessage, getMessageBoardMessage);
		assertValid(getMessageBoardMessage);
	}

	protected MessageBoardMessage
			testGetMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchMessageBoardMessage() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testPatchMessageBoardMessage_addMessageBoardMessage();

		MessageBoardMessage randomPatchMessageBoardMessage =
			randomPatchMessageBoardMessage();

		MessageBoardMessage patchMessageBoardMessage =
			MessageBoardMessageResource.patchMessageBoardMessage(
				postMessageBoardMessage.getId(),
				randomPatchMessageBoardMessage);

		MessageBoardMessage expectedPatchMessageBoardMessage =
			(MessageBoardMessage)BeanUtils.cloneBean(postMessageBoardMessage);

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardMessage, randomPatchMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			MessageBoardMessageResource.getMessageBoardMessage(
				patchMessageBoardMessage.getId());

		assertEquals(expectedPatchMessageBoardMessage, getMessageBoardMessage);
		assertValid(getMessageBoardMessage);
	}

	protected MessageBoardMessage
			testPatchMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutMessageBoardMessage() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testPutMessageBoardMessage_addMessageBoardMessage();

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		MessageBoardMessage putMessageBoardMessage =
			MessageBoardMessageResource.putMessageBoardMessage(
				postMessageBoardMessage.getId(), randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, putMessageBoardMessage);
		assertValid(putMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			MessageBoardMessageResource.getMessageBoardMessage(
				putMessageBoardMessage.getId());

		assertEquals(randomMessageBoardMessage, getMessageBoardMessage);
		assertValid(getMessageBoardMessage);
	}

	protected MessageBoardMessage
			testPutMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteMessageBoardMessageMyRating() throws Exception {
		MessageBoardMessage messageBoardMessage =
			testDeleteMessageBoardMessageMyRating_addMessageBoardMessage();

		assertHttpResponseStatusCode(
			204,
			MessageBoardMessageResource.
				deleteMessageBoardMessageMyRatingHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			MessageBoardMessageResource.
				getMessageBoardMessageMyRatingHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			MessageBoardMessageResource.
				getMessageBoardMessageMyRatingHttpResponse(0L));
	}

	protected MessageBoardMessage
			testDeleteMessageBoardMessageMyRating_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMessageBoardMessageMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostMessageBoardMessageMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutMessageBoardMessageMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPage()
		throws Exception {

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();
		Long irrelevantParentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getIrrelevantParentMessageBoardMessageId();

		if ((irrelevantParentMessageBoardMessageId != null)) {
			MessageBoardMessage irrelevantMessageBoardMessage =
				testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
					irrelevantParentMessageBoardMessageId,
					randomIrrelevantMessageBoardMessage());

			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						irrelevantParentMessageBoardMessageId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardMessage),
				(List<MessageBoardMessage>)page.getItems());
			assertValid(page);
		}

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		Page<MessageBoardMessage> page =
			MessageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			(List<MessageBoardMessage>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();

		messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null,
						getFilterString(
							entityField, "between", messageBoardMessage1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null,
						getFilterString(
							entityField, "eq", messageBoardMessage1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithPagination()
		throws Exception {

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage3 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, randomMessageBoardMessage());

		Page<MessageBoardMessage> page1 =
			MessageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null,
					Pagination.of(1, 2), null);

		List<MessageBoardMessage> messageBoardMessages1 =
			(List<MessageBoardMessage>)page1.getItems();

		Assert.assertEquals(
			messageBoardMessages1.toString(), 2, messageBoardMessages1.size());

		Page<MessageBoardMessage> page2 =
			MessageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardMessage> messageBoardMessages2 =
			(List<MessageBoardMessage>)page2.getItems();

		Assert.assertEquals(
			messageBoardMessages2.toString(), 1, messageBoardMessages2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardMessage1, messageBoardMessage2,
				messageBoardMessage3),
			new ArrayList<MessageBoardMessage>() {
				{
					addAll(messageBoardMessages1);
					addAll(messageBoardMessages2);
				}
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardMessage1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardMessage2, messageBoardMessage1),
				(List<MessageBoardMessage>)descPage.getItems());
		}
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardMessage1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardMessage2, entityField.getName(), "Bbb");
		}

		messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				MessageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardMessage2, messageBoardMessage1),
				(List<MessageBoardMessage>)descPage.getItems());
		}
	}

	protected MessageBoardMessage
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				Long parentMessageBoardMessageId,
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return MessageBoardMessageResource.
			postMessageBoardMessageMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage);
	}

	protected Long
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageMessageBoardMessagesPage_getIrrelevantParentMessageBoardMessageId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardMessageMessageBoardMessage()
		throws Exception {

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		MessageBoardMessage postMessageBoardMessage =
			testPostMessageBoardMessageMessageBoardMessage_addMessageBoardMessage(
				randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, postMessageBoardMessage);
		assertValid(postMessageBoardMessage);
	}

	protected MessageBoardMessage
			testPostMessageBoardMessageMessageBoardMessage_addMessageBoardMessage(
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return MessageBoardMessageResource.
			postMessageBoardMessageMessageBoardMessage(
				testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId(),
				messageBoardMessage);
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPage()
		throws Exception {

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getIrrelevantMessageBoardThreadId();

		if ((irrelevantMessageBoardThreadId != null)) {
			MessageBoardMessage irrelevantMessageBoardMessage =
				testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
					irrelevantMessageBoardThreadId,
					randomIrrelevantMessageBoardMessage());

			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						irrelevantMessageBoardThreadId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardMessage),
				(List<MessageBoardMessage>)page.getItems());
			assertValid(page);
		}

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		Page<MessageBoardMessage> page =
			MessageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			(List<MessageBoardMessage>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();

		messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null,
						getFilterString(
							entityField, "between", messageBoardMessage1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null,
						getFilterString(
							entityField, "eq", messageBoardMessage1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithPagination()
		throws Exception {

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage3 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, randomMessageBoardMessage());

		Page<MessageBoardMessage> page1 =
			MessageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(1, 2),
					null);

		List<MessageBoardMessage> messageBoardMessages1 =
			(List<MessageBoardMessage>)page1.getItems();

		Assert.assertEquals(
			messageBoardMessages1.toString(), 2, messageBoardMessages1.size());

		Page<MessageBoardMessage> page2 =
			MessageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardMessage> messageBoardMessages2 =
			(List<MessageBoardMessage>)page2.getItems();

		Assert.assertEquals(
			messageBoardMessages2.toString(), 1, messageBoardMessages2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardMessage1, messageBoardMessage2,
				messageBoardMessage3),
			new ArrayList<MessageBoardMessage>() {
				{
					addAll(messageBoardMessages1);
					addAll(messageBoardMessages2);
				}
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardMessage1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardMessage2, messageBoardMessage1),
				(List<MessageBoardMessage>)descPage.getItems());
		}
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				messageBoardMessage1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				messageBoardMessage2, entityField.getName(), "Bbb");
		}

		messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				MessageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardMessage2, messageBoardMessage1),
				(List<MessageBoardMessage>)descPage.getItems());
		}
	}

	protected MessageBoardMessage
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				Long messageBoardThreadId,
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return MessageBoardMessageResource.
			postMessageBoardThreadMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage);
	}

	protected Long
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardThreadMessageBoardMessagesPage_getIrrelevantMessageBoardThreadId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardThreadMessageBoardMessage()
		throws Exception {

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		MessageBoardMessage postMessageBoardMessage =
			testPostMessageBoardThreadMessageBoardMessage_addMessageBoardMessage(
				randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, postMessageBoardMessage);
		assertValid(postMessageBoardMessage);
	}

	protected MessageBoardMessage
			testPostMessageBoardThreadMessageBoardMessage_addMessageBoardMessage(
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return MessageBoardMessageResource.
			postMessageBoardThreadMessageBoardMessage(
				testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId(),
				messageBoardMessage);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MessageBoardMessage messageBoardMessage1,
		MessageBoardMessage messageBoardMessage2) {

		Assert.assertTrue(
			messageBoardMessage1 + " does not equal " + messageBoardMessage2,
			equals(messageBoardMessage1, messageBoardMessage2));
	}

	protected void assertEquals(
		List<MessageBoardMessage> messageBoardMessages1,
		List<MessageBoardMessage> messageBoardMessages2) {

		Assert.assertEquals(
			messageBoardMessages1.size(), messageBoardMessages2.size());

		for (int i = 0; i < messageBoardMessages1.size(); i++) {
			MessageBoardMessage messageBoardMessage1 =
				messageBoardMessages1.get(i);
			MessageBoardMessage messageBoardMessage2 =
				messageBoardMessages2.get(i);

			assertEquals(messageBoardMessage1, messageBoardMessage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardMessage> messageBoardMessages1,
		List<MessageBoardMessage> messageBoardMessages2) {

		Assert.assertEquals(
			messageBoardMessages1.size(), messageBoardMessages2.size());

		for (MessageBoardMessage messageBoardMessage1 : messageBoardMessages1) {
			boolean contains = false;

			for (MessageBoardMessage messageBoardMessage2 :
					messageBoardMessages2) {

				if (equals(messageBoardMessage1, messageBoardMessage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				messageBoardMessages2 + " does not contain " +
					messageBoardMessage1,
				contains);
		}
	}

	protected void assertValid(MessageBoardMessage messageBoardMessage) {
		boolean valid = true;

		if (messageBoardMessage.getDateCreated() == null) {
			valid = false;
		}

		if (messageBoardMessage.getDateModified() == null) {
			valid = false;
		}

		if (messageBoardMessage.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				messageBoardMessage.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (messageBoardMessage.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("anonymous", additionalAssertFieldName)) {
				if (messageBoardMessage.getAnonymous() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (messageBoardMessage.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (messageBoardMessage.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (messageBoardMessage.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (messageBoardMessage.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (messageBoardMessage.getHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (messageBoardMessage.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardAttachments",
					additionalAssertFieldName)) {

				if (messageBoardMessage.getNumberOfMessageBoardAttachments() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardMessages",
					additionalAssertFieldName)) {

				if (messageBoardMessage.getNumberOfMessageBoardMessages() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (messageBoardMessage.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("showAsAnswer", additionalAssertFieldName)) {
				if (messageBoardMessage.getShowAsAnswer() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (messageBoardMessage.getViewableBy() == null) {
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

	protected void assertValid(Page<MessageBoardMessage> page) {
		boolean valid = false;

		Collection<MessageBoardMessage> messageBoardMessages = page.getItems();

		int size = messageBoardMessages.size();

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
		MessageBoardMessage messageBoardMessage1,
		MessageBoardMessage messageBoardMessage2) {

		if (messageBoardMessage1 == messageBoardMessage2) {
			return true;
		}

		if (!Objects.equals(
				messageBoardMessage1.getSiteId(),
				messageBoardMessage2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getAggregateRating(),
						messageBoardMessage2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("anonymous", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getAnonymous(),
						messageBoardMessage2.getAnonymous())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getArticleBody(),
						messageBoardMessage2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getCreator(),
						messageBoardMessage2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getCustomFields(),
						messageBoardMessage2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getDateCreated(),
						messageBoardMessage2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getDateModified(),
						messageBoardMessage2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getEncodingFormat(),
						messageBoardMessage2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getHeadline(),
						messageBoardMessage2.getHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getId(),
						messageBoardMessage2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getKeywords(),
						messageBoardMessage2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardAttachments",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardMessage1.
							getNumberOfMessageBoardAttachments(),
						messageBoardMessage2.
							getNumberOfMessageBoardAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfMessageBoardMessages",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardMessage1.getNumberOfMessageBoardMessages(),
						messageBoardMessage2.
							getNumberOfMessageBoardMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getRelatedContents(),
						messageBoardMessage2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("showAsAnswer", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getShowAsAnswer(),
						messageBoardMessage2.getShowAsAnswer())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getViewableBy(),
						messageBoardMessage2.getViewableBy())) {

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
		if (!(_messageBoardMessageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_messageBoardMessageResource;

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
		MessageBoardMessage messageBoardMessage) {

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

		if (entityFieldName.equals("anonymous")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardMessage.getArticleBody()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
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
							messageBoardMessage.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardMessage.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardMessage.getDateCreated()));
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
							messageBoardMessage.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							messageBoardMessage.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(messageBoardMessage.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardMessage.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardMessage.getHeadline()));
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

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("showAsAnswer")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
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

	protected MessageBoardMessage randomMessageBoardMessage() throws Exception {
		return new MessageBoardMessage() {
			{
				anonymous = RandomTestUtil.randomBoolean();
				articleBody = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				encodingFormat = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				showAsAnswer = RandomTestUtil.randomBoolean();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected MessageBoardMessage randomIrrelevantMessageBoardMessage()
		throws Exception {

		MessageBoardMessage randomIrrelevantMessageBoardMessage =
			randomMessageBoardMessage();

		randomIrrelevantMessageBoardMessage.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantMessageBoardMessage;
	}

	protected MessageBoardMessage randomPatchMessageBoardMessage()
		throws Exception {

		return randomMessageBoardMessage();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessageBoardMessageResourceTestCase.class);

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
		com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource
			_messageBoardMessageResource;

}