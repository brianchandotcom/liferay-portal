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
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardMessageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardMessageSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
import org.apache.log4j.Level;

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_messageBoardMessageResource.setContextCompany(testCompany);

		MessageBoardMessageResource.Builder builder =
			MessageBoardMessageResource.builder();

		messageBoardMessageResource = builder.locale(
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
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MessageBoardMessage messageBoardMessage = randomMessageBoardMessage();

		messageBoardMessage.setArticleBody(regex);
		messageBoardMessage.setEncodingFormat(regex);
		messageBoardMessage.setHeadline(regex);

		String json = MessageBoardMessageSerDes.toJSON(messageBoardMessage);

		Assert.assertFalse(json.contains(regex));

		messageBoardMessage = MessageBoardMessageSerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardMessage.getArticleBody());
		Assert.assertEquals(regex, messageBoardMessage.getEncodingFormat());
		Assert.assertEquals(regex, messageBoardMessage.getHeadline());
	}

	@Test
	public void testDeleteMessageBoardMessage() throws Exception {
		MessageBoardMessage messageBoardMessage =
			testDeleteMessageBoardMessage_addMessageBoardMessage();

		assertHttpResponseStatusCode(
			204,
			messageBoardMessageResource.deleteMessageBoardMessageHttpResponse(
				messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.getMessageBoardMessageHttpResponse(
				messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.getMessageBoardMessageHttpResponse(0L));
	}

	protected MessageBoardMessage
			testDeleteMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteMessageBoardMessage() throws Exception {
		MessageBoardMessage messageBoardMessage =
			testGraphQLMessageBoardMessage_addMessageBoardMessage();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteMessageBoardMessage",
				new HashMap<String, Object>() {
					{
						put(
							"messageBoardMessageId",
							messageBoardMessage.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			dataJSONObject.getBoolean("deleteMessageBoardMessage"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"messageBoardMessage",
					new HashMap<String, Object>() {
						{
							put(
								"messageBoardMessageId",
								messageBoardMessage.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetMessageBoardMessage() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testGetMessageBoardMessage_addMessageBoardMessage();

		MessageBoardMessage getMessageBoardMessage =
			messageBoardMessageResource.getMessageBoardMessage(
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
	public void testGraphQLGetMessageBoardMessage() throws Exception {
		MessageBoardMessage messageBoardMessage =
			testGraphQLMessageBoardMessage_addMessageBoardMessage();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardMessage",
				new HashMap<String, Object>() {
					{
						put(
							"messageBoardMessageId",
							messageBoardMessage.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				messageBoardMessage,
				dataJSONObject.getJSONObject("messageBoardMessage")));
	}

	@Test
	public void testPatchMessageBoardMessage() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testPatchMessageBoardMessage_addMessageBoardMessage();

		MessageBoardMessage randomPatchMessageBoardMessage =
			randomPatchMessageBoardMessage();

		MessageBoardMessage patchMessageBoardMessage =
			messageBoardMessageResource.patchMessageBoardMessage(
				postMessageBoardMessage.getId(),
				randomPatchMessageBoardMessage);

		MessageBoardMessage expectedPatchMessageBoardMessage =
			postMessageBoardMessage.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardMessage, randomPatchMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			messageBoardMessageResource.getMessageBoardMessage(
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
			messageBoardMessageResource.putMessageBoardMessage(
				postMessageBoardMessage.getId(), randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, putMessageBoardMessage);
		assertValid(putMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			messageBoardMessageResource.getMessageBoardMessage(
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
			messageBoardMessageResource.
				deleteMessageBoardMessageMyRatingHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.
				getMessageBoardMessageMyRatingHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.
				getMessageBoardMessageMyRatingHttpResponse(0L));
	}

	protected MessageBoardMessage
			testDeleteMessageBoardMessageMyRating_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutMessageBoardMessageSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardMessage messageBoardMessage =
			testPutMessageBoardMessageSubscribe_addMessageBoardMessage();

		assertHttpResponseStatusCode(
			204,
			messageBoardMessageResource.
				putMessageBoardMessageSubscribeHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.
				putMessageBoardMessageSubscribeHttpResponse(0L));
	}

	protected MessageBoardMessage
			testPutMessageBoardMessageSubscribe_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutMessageBoardMessageUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardMessage messageBoardMessage =
			testPutMessageBoardMessageUnsubscribe_addMessageBoardMessage();

		assertHttpResponseStatusCode(
			204,
			messageBoardMessageResource.
				putMessageBoardMessageUnsubscribeHttpResponse(
					messageBoardMessage.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardMessageResource.
				putMessageBoardMessageUnsubscribeHttpResponse(0L));
	}

	protected MessageBoardMessage
			testPutMessageBoardMessageUnsubscribe_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPage()
		throws Exception {

		Page<MessageBoardMessage> page =
			messageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId(),
					null, RandomTestUtil.randomString(), null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();
		Long irrelevantParentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getIrrelevantParentMessageBoardMessageId();

		if ((irrelevantParentMessageBoardMessageId != null)) {
			MessageBoardMessage irrelevantMessageBoardMessage =
				testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
					irrelevantParentMessageBoardMessageId,
					randomIrrelevantMessageBoardMessage());

			page =
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						irrelevantParentMessageBoardMessageId, null, null, null,
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

		page =
			messageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			(List<MessageBoardMessage>)page.getItems());
		assertValid(page);

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage1.getId());

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage2.getId());
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
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
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
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null,
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
			messageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null, null,
					Pagination.of(1, 2), null);

		List<MessageBoardMessage> messageBoardMessages1 =
			(List<MessageBoardMessage>)page1.getItems();

		Assert.assertEquals(
			messageBoardMessages1.toString(), 2, messageBoardMessages1.size());

		Page<MessageBoardMessage> page2 =
			messageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardMessage> messageBoardMessages2 =
			(List<MessageBoardMessage>)page2.getItems();

		Assert.assertEquals(
			messageBoardMessages2.toString(), 1, messageBoardMessages2.size());

		Page<MessageBoardMessage> page3 =
			messageBoardMessageResource.
				getMessageBoardMessageMessageBoardMessagesPage(
					parentMessageBoardMessageId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardMessage1, messageBoardMessage2,
				messageBoardMessage3),
			(List<MessageBoardMessage>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardMessageMessageBoardMessagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithSortInteger()
		throws Exception {

		testGetMessageBoardMessageMessageBoardMessagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardMessage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardMessagesPageWithSortString()
		throws Exception {

		testGetMessageBoardMessageMessageBoardMessagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				Class<?> clazz = messageBoardMessage1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetMessageBoardMessageMessageBoardMessagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardMessage, MessageBoardMessage,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardMessage1, messageBoardMessage2);
		}

		messageBoardMessage1 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				parentMessageBoardMessageId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				messageBoardMessageResource.
					getMessageBoardMessageMessageBoardMessagesPage(
						parentMessageBoardMessageId, null, null, null,
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

		return messageBoardMessageResource.
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

		return messageBoardMessageResource.
			postMessageBoardMessageMessageBoardMessage(
				testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId(),
				messageBoardMessage);
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPage()
		throws Exception {

		Page<MessageBoardMessage> page =
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId(),
					RandomTestUtil.randomString(), null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getIrrelevantMessageBoardThreadId();

		if ((irrelevantMessageBoardThreadId != null)) {
			MessageBoardMessage irrelevantMessageBoardMessage =
				testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
					irrelevantMessageBoardThreadId,
					randomIrrelevantMessageBoardMessage());

			page =
				messageBoardMessageResource.
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

		page =
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			(List<MessageBoardMessage>)page.getItems());
		assertValid(page);

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage1.getId());

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage2.getId());
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
				messageBoardMessageResource.
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
				messageBoardMessageResource.
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
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(1, 2),
					null);

		List<MessageBoardMessage> messageBoardMessages1 =
			(List<MessageBoardMessage>)page1.getItems();

		Assert.assertEquals(
			messageBoardMessages1.toString(), 2, messageBoardMessages1.size());

		Page<MessageBoardMessage> page2 =
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardMessage> messageBoardMessages2 =
			(List<MessageBoardMessage>)page2.getItems();

		Assert.assertEquals(
			messageBoardMessages2.toString(), 1, messageBoardMessages2.size());

		Page<MessageBoardMessage> page3 =
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessagesPage(
					messageBoardThreadId, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardMessage1, messageBoardMessage2,
				messageBoardMessage3),
			(List<MessageBoardMessage>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardThreadMessageBoardMessagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithSortInteger()
		throws Exception {

		testGetMessageBoardThreadMessageBoardMessagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardMessage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardMessagesPageWithSortString()
		throws Exception {

		testGetMessageBoardThreadMessageBoardMessagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				Class<?> clazz = messageBoardMessage1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetMessageBoardThreadMessageBoardMessagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardMessage, MessageBoardMessage,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardMessage1, messageBoardMessage2);
		}

		messageBoardMessage1 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				messageBoardThreadId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				messageBoardMessageResource.
					getMessageBoardThreadMessageBoardMessagesPage(
						messageBoardThreadId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				messageBoardMessageResource.
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

		return messageBoardMessageResource.
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

		return messageBoardMessageResource.
			postMessageBoardThreadMessageBoardMessage(
				testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId(),
				messageBoardMessage);
	}

	@Test
	public void testGetSiteMessageBoardMessagesPage() throws Exception {
		Page<MessageBoardMessage> page =
			messageBoardMessageResource.getSiteMessageBoardMessagesPage(
				testGetSiteMessageBoardMessagesPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteMessageBoardMessagesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteMessageBoardMessagesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			MessageBoardMessage irrelevantMessageBoardMessage =
				testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
					irrelevantSiteId, randomIrrelevantMessageBoardMessage());

			page = messageBoardMessageResource.getSiteMessageBoardMessagesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardMessage),
				(List<MessageBoardMessage>)page.getItems());
			assertValid(page);
		}

		MessageBoardMessage messageBoardMessage1 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		page = messageBoardMessageResource.getSiteMessageBoardMessagesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			(List<MessageBoardMessage>)page.getItems());
		assertValid(page);

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage1.getId());

		messageBoardMessageResource.deleteMessageBoardMessage(
			messageBoardMessage2.getId());
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardMessagesPage_getSiteId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();

		messageBoardMessage1 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, messageBoardMessage1);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				messageBoardMessageResource.getSiteMessageBoardMessagesPage(
					siteId, null, null,
					getFilterString(
						entityField, "between", messageBoardMessage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardMessagesPage_getSiteId();

		MessageBoardMessage messageBoardMessage1 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardMessage messageBoardMessage2 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> page =
				messageBoardMessageResource.getSiteMessageBoardMessagesPage(
					siteId, null, null,
					getFilterString(entityField, "eq", messageBoardMessage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(messageBoardMessage1),
				(List<MessageBoardMessage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteMessageBoardMessagesPage_getSiteId();

		MessageBoardMessage messageBoardMessage1 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage2 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		MessageBoardMessage messageBoardMessage3 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, randomMessageBoardMessage());

		Page<MessageBoardMessage> page1 =
			messageBoardMessageResource.getSiteMessageBoardMessagesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardMessage> messageBoardMessages1 =
			(List<MessageBoardMessage>)page1.getItems();

		Assert.assertEquals(
			messageBoardMessages1.toString(), 2, messageBoardMessages1.size());

		Page<MessageBoardMessage> page2 =
			messageBoardMessageResource.getSiteMessageBoardMessagesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardMessage> messageBoardMessages2 =
			(List<MessageBoardMessage>)page2.getItems();

		Assert.assertEquals(
			messageBoardMessages2.toString(), 1, messageBoardMessages2.size());

		Page<MessageBoardMessage> page3 =
			messageBoardMessageResource.getSiteMessageBoardMessagesPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardMessage1, messageBoardMessage2,
				messageBoardMessage3),
			(List<MessageBoardMessage>)page3.getItems());
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithSortDateTime()
		throws Exception {

		testGetSiteMessageBoardMessagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithSortInteger()
		throws Exception {

		testGetSiteMessageBoardMessagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				BeanUtils.setProperty(
					messageBoardMessage1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardMessage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteMessageBoardMessagesPageWithSortString()
		throws Exception {

		testGetSiteMessageBoardMessagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardMessage1, messageBoardMessage2) -> {
				Class<?> clazz = messageBoardMessage1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						messageBoardMessage1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						messageBoardMessage2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetSiteMessageBoardMessagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardMessage, MessageBoardMessage,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardMessagesPage_getSiteId();

		MessageBoardMessage messageBoardMessage1 = randomMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 = randomMessageBoardMessage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardMessage1, messageBoardMessage2);
		}

		messageBoardMessage1 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, messageBoardMessage1);

		messageBoardMessage2 =
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				siteId, messageBoardMessage2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardMessage> ascPage =
				messageBoardMessageResource.getSiteMessageBoardMessagesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardMessage1, messageBoardMessage2),
				(List<MessageBoardMessage>)ascPage.getItems());

			Page<MessageBoardMessage> descPage =
				messageBoardMessageResource.getSiteMessageBoardMessagesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(messageBoardMessage2, messageBoardMessage1),
				(List<MessageBoardMessage>)descPage.getItems());
		}
	}

	protected MessageBoardMessage
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				Long siteId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteMessageBoardMessagesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteMessageBoardMessagesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteMessageBoardMessagesPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardMessages",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject messageBoardMessagesJSONObject =
			dataJSONObject.getJSONObject("messageBoardMessages");

		Assert.assertEquals(
			0, messageBoardMessagesJSONObject.get("totalCount"));

		MessageBoardMessage messageBoardMessage1 =
			testGraphQLMessageBoardMessage_addMessageBoardMessage();
		MessageBoardMessage messageBoardMessage2 =
			testGraphQLMessageBoardMessage_addMessageBoardMessage();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		messageBoardMessagesJSONObject = dataJSONObject.getJSONObject(
			"messageBoardMessages");

		Assert.assertEquals(
			2, messageBoardMessagesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(messageBoardMessage1, messageBoardMessage2),
			messageBoardMessagesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testGetMessageBoardMessageMyRating() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testGetMessageBoardMessage_addMessageBoardMessage();

		Rating postRating = testGetMessageBoardMessageMyRating_addRating(
			postMessageBoardMessage.getId(), randomRating());

		Rating getRating =
			messageBoardMessageResource.getMessageBoardMessageMyRating(
				postMessageBoardMessage.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetMessageBoardMessageMyRating_addRating(
			long messageBoardMessageId, Rating rating)
		throws Exception {

		return messageBoardMessageResource.postMessageBoardMessageMyRating(
			messageBoardMessageId, rating);
	}

	@Test
	public void testPostMessageBoardMessageMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutMessageBoardMessageMyRating() throws Exception {
		MessageBoardMessage postMessageBoardMessage =
			testPutMessageBoardMessage_addMessageBoardMessage();

		testPutMessageBoardMessageMyRating_addRating(
			postMessageBoardMessage.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating =
			messageBoardMessageResource.putMessageBoardMessageMyRating(
				postMessageBoardMessage.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutMessageBoardMessageMyRating_addRating(
			long messageBoardMessageId, Rating rating)
		throws Exception {

		return messageBoardMessageResource.postMessageBoardMessageMyRating(
			messageBoardMessageId, rating);
	}

	protected MessageBoardMessage
			testGraphQLMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
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

	protected void assertEqualsJSONArray(
		List<MessageBoardMessage> messageBoardMessages, JSONArray jsonArray) {

		for (MessageBoardMessage messageBoardMessage : messageBoardMessages) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(messageBoardMessage, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + messageBoardMessage,
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (messageBoardMessage.getActions() == null) {
					valid = false;
				}

				continue;
			}

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
					"messageBoardThreadId", additionalAssertFieldName)) {

				if (messageBoardMessage.getMessageBoardThreadId() == null) {
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

			if (Objects.equals(
					"parentMessageBoardMessageId", additionalAssertFieldName)) {

				if (messageBoardMessage.getParentMessageBoardMessageId() ==
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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (messageBoardMessage.getSubscribed() == null) {
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

		java.util.Collection<MessageBoardMessage> messageBoardMessages =
			page.getItems();

		int size = messageBoardMessages.size();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getActions(),
						messageBoardMessage2.getActions())) {

					return false;
				}

				continue;
			}

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
					"messageBoardThreadId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardMessage1.getMessageBoardThreadId(),
						messageBoardMessage2.getMessageBoardThreadId())) {

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

			if (Objects.equals(
					"parentMessageBoardMessageId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardMessage1.getParentMessageBoardMessageId(),
						messageBoardMessage2.
							getParentMessageBoardMessageId())) {

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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage1.getSubscribed(),
						messageBoardMessage2.getSubscribed())) {

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

	protected boolean equalsJSONObject(
		MessageBoardMessage messageBoardMessage, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("anonymous", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getAnonymous(),
						jsonObject.getBoolean("anonymous"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getArticleBody(),
						jsonObject.getString("articleBody"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getEncodingFormat(),
						jsonObject.getString("encodingFormat"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getHeadline(),
						jsonObject.getString("headline"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getId(),
						jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("messageBoardThreadId", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getMessageBoardThreadId(),
						jsonObject.getLong("messageBoardThreadId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfMessageBoardAttachments", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.
							getNumberOfMessageBoardAttachments(),
						jsonObject.getInt("numberOfMessageBoardAttachments"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfMessageBoardMessages", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getNumberOfMessageBoardMessages(),
						jsonObject.getInt("numberOfMessageBoardMessages"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentMessageBoardMessageId", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getParentMessageBoardMessageId(),
						jsonObject.getLong("parentMessageBoardMessageId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("showAsAnswer", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getShowAsAnswer(),
						jsonObject.getBoolean("showAsAnswer"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", fieldName)) {
				if (!Objects.deepEquals(
						messageBoardMessage.getSubscribed(),
						jsonObject.getBoolean("subscribed"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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
		MessageBoardMessage messageBoardMessage) {

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

		if (entityFieldName.equals("messageBoardThreadId")) {
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

		if (entityFieldName.equals("parentMessageBoardMessageId")) {
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

		if (entityFieldName.equals("subscribed")) {
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
				messageBoardThreadId = RandomTestUtil.randomLong();
				numberOfMessageBoardAttachments = RandomTestUtil.randomInt();
				numberOfMessageBoardMessages = RandomTestUtil.randomInt();
				parentMessageBoardMessageId = RandomTestUtil.randomLong();
				showAsAnswer = RandomTestUtil.randomBoolean();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
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

	protected MessageBoardMessageResource messageBoardMessageResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

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