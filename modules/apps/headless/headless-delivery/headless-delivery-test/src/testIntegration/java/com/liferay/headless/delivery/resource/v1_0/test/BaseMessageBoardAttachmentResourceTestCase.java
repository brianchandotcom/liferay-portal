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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardAttachmentSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
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

import java.io.File;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
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
public abstract class BaseMessageBoardAttachmentResourceTestCase {

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

		_messageBoardAttachmentResource.setContextCompany(testCompany);

		MessageBoardAttachmentResource.Builder builder =
			MessageBoardAttachmentResource.builder();

		messageBoardAttachmentResource = builder.authentication(
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

		MessageBoardAttachment messageBoardAttachment1 =
			randomMessageBoardAttachment();

		String json = objectMapper.writeValueAsString(messageBoardAttachment1);

		MessageBoardAttachment messageBoardAttachment2 =
			MessageBoardAttachmentSerDes.toDTO(json);

		Assert.assertTrue(
			equals(messageBoardAttachment1, messageBoardAttachment2));
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

		MessageBoardAttachment messageBoardAttachment =
			randomMessageBoardAttachment();

		String json1 = objectMapper.writeValueAsString(messageBoardAttachment);
		String json2 = MessageBoardAttachmentSerDes.toJSON(
			messageBoardAttachment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MessageBoardAttachment messageBoardAttachment =
			randomMessageBoardAttachment();

		messageBoardAttachment.setContentUrl(regex);
		messageBoardAttachment.setContentValue(regex);
		messageBoardAttachment.setEncodingFormat(regex);
		messageBoardAttachment.setFileExtension(regex);
		messageBoardAttachment.setTitle(regex);

		String json = MessageBoardAttachmentSerDes.toJSON(
			messageBoardAttachment);

		Assert.assertFalse(json.contains(regex));

		messageBoardAttachment = MessageBoardAttachmentSerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardAttachment.getContentUrl());
		Assert.assertEquals(regex, messageBoardAttachment.getContentValue());
		Assert.assertEquals(regex, messageBoardAttachment.getEncodingFormat());
		Assert.assertEquals(regex, messageBoardAttachment.getFileExtension());
		Assert.assertEquals(regex, messageBoardAttachment.getTitle());
	}

	@Test
	public void testDeleteMessageBoardAttachment() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardAttachment messageBoardAttachment =
			testDeleteMessageBoardAttachment_addMessageBoardAttachment();

		assertHttpResponseStatusCode(
			204,
			messageBoardAttachmentResource.
				deleteMessageBoardAttachmentHttpResponse(
					messageBoardAttachment.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardAttachmentResource.
				getMessageBoardAttachmentHttpResponse(
					messageBoardAttachment.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardAttachmentResource.
				getMessageBoardAttachmentHttpResponse(0L));
	}

	protected MessageBoardAttachment
			testDeleteMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteMessageBoardAttachment() throws Exception {
		MessageBoardAttachment messageBoardAttachment =
			testGraphQLMessageBoardAttachment_addMessageBoardAttachment();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteMessageBoardAttachment",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardAttachmentId",
									messageBoardAttachment.getId());
							}
						})),
				"JSONObject/data", "Object/deleteMessageBoardAttachment"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardAttachment",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardAttachmentId",
									messageBoardAttachment.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetMessageBoardAttachment() throws Exception {
		MessageBoardAttachment postMessageBoardAttachment =
			testGetMessageBoardAttachment_addMessageBoardAttachment();

		MessageBoardAttachment getMessageBoardAttachment =
			messageBoardAttachmentResource.getMessageBoardAttachment(
				postMessageBoardAttachment.getId());

		assertEquals(postMessageBoardAttachment, getMessageBoardAttachment);
		assertValid(getMessageBoardAttachment);
	}

	protected MessageBoardAttachment
			testGetMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMessageBoardAttachment() throws Exception {
		MessageBoardAttachment messageBoardAttachment =
			testGraphQLMessageBoardAttachment_addMessageBoardAttachment();

		Assert.assertTrue(
			equals(
				messageBoardAttachment,
				MessageBoardAttachmentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"messageBoardAttachment",
								new HashMap<String, Object>() {
									{
										put(
											"messageBoardAttachmentId",
											messageBoardAttachment.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/messageBoardAttachment"))));
	}

	@Test
	public void testGraphQLGetMessageBoardAttachmentNotFound()
		throws Exception {

		Long irrelevantMessageBoardAttachmentId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardAttachment",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardAttachmentId",
									irrelevantMessageBoardAttachmentId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardAttachmentsPage()
		throws Exception {

		Page<MessageBoardAttachment> page =
			messageBoardAttachmentResource.
				getMessageBoardMessageMessageBoardAttachmentsPage(
					testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId());

		Assert.assertEquals(0, page.getTotalCount());

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId();
		Long irrelevantMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getIrrelevantMessageBoardMessageId();

		if ((irrelevantMessageBoardMessageId != null)) {
			MessageBoardAttachment irrelevantMessageBoardAttachment =
				testGetMessageBoardMessageMessageBoardAttachmentsPage_addMessageBoardAttachment(
					irrelevantMessageBoardMessageId,
					randomIrrelevantMessageBoardAttachment());

			page =
				messageBoardAttachmentResource.
					getMessageBoardMessageMessageBoardAttachmentsPage(
						irrelevantMessageBoardMessageId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardAttachment),
				(List<MessageBoardAttachment>)page.getItems());
			assertValid(page);
		}

		MessageBoardAttachment messageBoardAttachment1 =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_addMessageBoardAttachment(
				messageBoardMessageId, randomMessageBoardAttachment());

		MessageBoardAttachment messageBoardAttachment2 =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_addMessageBoardAttachment(
				messageBoardMessageId, randomMessageBoardAttachment());

		page =
			messageBoardAttachmentResource.
				getMessageBoardMessageMessageBoardAttachmentsPage(
					messageBoardMessageId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardAttachment1, messageBoardAttachment2),
			(List<MessageBoardAttachment>)page.getItems());
		assertValid(page);

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment1.getId());

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment2.getId());
	}

	protected MessageBoardAttachment
			testGetMessageBoardMessageMessageBoardAttachmentsPage_addMessageBoardAttachment(
				Long messageBoardMessageId,
				MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardMessageMessageBoardAttachment(
				messageBoardMessageId, messageBoardAttachment,
				getMultipartFiles());
	}

	protected Long
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getIrrelevantMessageBoardMessageId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardMessageMessageBoardAttachment()
		throws Exception {

		MessageBoardAttachment randomMessageBoardAttachment =
			randomMessageBoardAttachment();

		Map<String, File> multipartFiles = getMultipartFiles();

		MessageBoardAttachment postMessageBoardAttachment =
			testPostMessageBoardMessageMessageBoardAttachment_addMessageBoardAttachment(
				randomMessageBoardAttachment, multipartFiles);

		assertEquals(randomMessageBoardAttachment, postMessageBoardAttachment);
		assertValid(postMessageBoardAttachment);

		assertValid(postMessageBoardAttachment, multipartFiles);
	}

	protected MessageBoardAttachment
			testPostMessageBoardMessageMessageBoardAttachment_addMessageBoardAttachment(
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardMessageMessageBoardAttachment(
				testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId(),
				messageBoardAttachment, multipartFiles);
	}

	@Test
	public void testGetMessageBoardThreadMessageBoardAttachmentsPage()
		throws Exception {

		Page<MessageBoardAttachment> page =
			messageBoardAttachmentResource.
				getMessageBoardThreadMessageBoardAttachmentsPage(
					testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId());

		Assert.assertEquals(0, page.getTotalCount());

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getIrrelevantMessageBoardThreadId();

		if ((irrelevantMessageBoardThreadId != null)) {
			MessageBoardAttachment irrelevantMessageBoardAttachment =
				testGetMessageBoardThreadMessageBoardAttachmentsPage_addMessageBoardAttachment(
					irrelevantMessageBoardThreadId,
					randomIrrelevantMessageBoardAttachment());

			page =
				messageBoardAttachmentResource.
					getMessageBoardThreadMessageBoardAttachmentsPage(
						irrelevantMessageBoardThreadId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMessageBoardAttachment),
				(List<MessageBoardAttachment>)page.getItems());
			assertValid(page);
		}

		MessageBoardAttachment messageBoardAttachment1 =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_addMessageBoardAttachment(
				messageBoardThreadId, randomMessageBoardAttachment());

		MessageBoardAttachment messageBoardAttachment2 =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_addMessageBoardAttachment(
				messageBoardThreadId, randomMessageBoardAttachment());

		page =
			messageBoardAttachmentResource.
				getMessageBoardThreadMessageBoardAttachmentsPage(
					messageBoardThreadId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardAttachment1, messageBoardAttachment2),
			(List<MessageBoardAttachment>)page.getItems());
		assertValid(page);

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment1.getId());

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment2.getId());
	}

	protected MessageBoardAttachment
			testGetMessageBoardThreadMessageBoardAttachmentsPage_addMessageBoardAttachment(
				Long messageBoardThreadId,
				MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardThreadMessageBoardAttachment(
				messageBoardThreadId, messageBoardAttachment,
				getMultipartFiles());
	}

	protected Long
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getIrrelevantMessageBoardThreadId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostMessageBoardThreadMessageBoardAttachment()
		throws Exception {

		MessageBoardAttachment randomMessageBoardAttachment =
			randomMessageBoardAttachment();

		Map<String, File> multipartFiles = getMultipartFiles();

		MessageBoardAttachment postMessageBoardAttachment =
			testPostMessageBoardThreadMessageBoardAttachment_addMessageBoardAttachment(
				randomMessageBoardAttachment, multipartFiles);

		assertEquals(randomMessageBoardAttachment, postMessageBoardAttachment);
		assertValid(postMessageBoardAttachment);

		assertValid(postMessageBoardAttachment, multipartFiles);
	}

	protected MessageBoardAttachment
			testPostMessageBoardThreadMessageBoardAttachment_addMessageBoardAttachment(
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardThreadMessageBoardAttachment(
				testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId(),
				messageBoardAttachment, multipartFiles);
	}

	protected MessageBoardAttachment
			testGraphQLMessageBoardAttachment_addMessageBoardAttachment()
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
		MessageBoardAttachment messageBoardAttachment1,
		MessageBoardAttachment messageBoardAttachment2) {

		Assert.assertTrue(
			messageBoardAttachment1 + " does not equal " +
				messageBoardAttachment2,
			equals(messageBoardAttachment1, messageBoardAttachment2));
	}

	protected void assertEquals(
		List<MessageBoardAttachment> messageBoardAttachments1,
		List<MessageBoardAttachment> messageBoardAttachments2) {

		Assert.assertEquals(
			messageBoardAttachments1.size(), messageBoardAttachments2.size());

		for (int i = 0; i < messageBoardAttachments1.size(); i++) {
			MessageBoardAttachment messageBoardAttachment1 =
				messageBoardAttachments1.get(i);
			MessageBoardAttachment messageBoardAttachment2 =
				messageBoardAttachments2.get(i);

			assertEquals(messageBoardAttachment1, messageBoardAttachment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MessageBoardAttachment> messageBoardAttachments1,
		List<MessageBoardAttachment> messageBoardAttachments2) {

		Assert.assertEquals(
			messageBoardAttachments1.size(), messageBoardAttachments2.size());

		for (MessageBoardAttachment messageBoardAttachment1 :
				messageBoardAttachments1) {

			boolean contains = false;

			for (MessageBoardAttachment messageBoardAttachment2 :
					messageBoardAttachments2) {

				if (equals(messageBoardAttachment1, messageBoardAttachment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				messageBoardAttachments2 + " does not contain " +
					messageBoardAttachment1,
				contains);
		}
	}

	protected void assertValid(MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		boolean valid = true;

		if (messageBoardAttachment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (messageBoardAttachment.getContentUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (messageBoardAttachment.getContentValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (messageBoardAttachment.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (messageBoardAttachment.getFileExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (messageBoardAttachment.getSizeInBytes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (messageBoardAttachment.getTitle() == null) {
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

	protected void assertValid(
			MessageBoardAttachment messageBoardAttachment,
			Map<String, File> multipartFiles)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<MessageBoardAttachment> page) {
		boolean valid = false;

		java.util.Collection<MessageBoardAttachment> messageBoardAttachments =
			page.getItems();

		int size = messageBoardAttachments.size();

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

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.
						MessageBoardAttachment.class)) {

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
					ReflectionUtil.getDeclaredFields(clazz));

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
		MessageBoardAttachment messageBoardAttachment1,
		MessageBoardAttachment messageBoardAttachment2) {

		if (messageBoardAttachment1 == messageBoardAttachment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getContentUrl(),
						messageBoardAttachment2.getContentUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getContentValue(),
						messageBoardAttachment2.getContentValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getEncodingFormat(),
						messageBoardAttachment2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getFileExtension(),
						messageBoardAttachment2.getFileExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getId(),
						messageBoardAttachment2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getSizeInBytes(),
						messageBoardAttachment2.getSizeInBytes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardAttachment1.getTitle(),
						messageBoardAttachment2.getTitle())) {

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
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_messageBoardAttachmentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_messageBoardAttachmentResource;

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
		MessageBoardAttachment messageBoardAttachment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardAttachment.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contentValue")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardAttachment.getContentValue()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(
				String.valueOf(messageBoardAttachment.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(
				String.valueOf(messageBoardAttachment.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(messageBoardAttachment.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Map<String, File> getMultipartFiles() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected MessageBoardAttachment randomMessageBoardAttachment()
		throws Exception {

		return new MessageBoardAttachment() {
			{
				contentUrl = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentValue = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				fileExtension = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				sizeInBytes = RandomTestUtil.randomLong();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected MessageBoardAttachment randomIrrelevantMessageBoardAttachment()
		throws Exception {

		MessageBoardAttachment randomIrrelevantMessageBoardAttachment =
			randomMessageBoardAttachment();

		return randomIrrelevantMessageBoardAttachment;
	}

	protected MessageBoardAttachment randomPatchMessageBoardAttachment()
		throws Exception {

		return randomMessageBoardAttachment();
	}

	protected MessageBoardAttachmentResource messageBoardAttachmentResource;
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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessageBoardAttachmentResourceTestCase.class);

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
		com.liferay.headless.delivery.resource.v1_0.
			MessageBoardAttachmentResource _messageBoardAttachmentResource;

}