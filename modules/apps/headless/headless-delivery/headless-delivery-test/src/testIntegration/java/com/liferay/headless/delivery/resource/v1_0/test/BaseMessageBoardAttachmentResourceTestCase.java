/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.Field;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardAttachmentSerDes;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
		messageBoardAttachment.setExternalReferenceCode(regex);
		messageBoardAttachment.setFileExtension(regex);
		messageBoardAttachment.setTitle(regex);

		String json = MessageBoardAttachmentSerDes.toJSON(
			messageBoardAttachment);

		Assert.assertFalse(json.contains(regex));

		messageBoardAttachment = MessageBoardAttachmentSerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardAttachment.getContentUrl());
		Assert.assertEquals(regex, messageBoardAttachment.getContentValue());
		Assert.assertEquals(regex, messageBoardAttachment.getEncodingFormat());
		Assert.assertEquals(
			regex, messageBoardAttachment.getExternalReferenceCode());
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
			testGraphQLDeleteMessageBoardAttachment_addMessageBoardAttachment();

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

	protected MessageBoardAttachment
			testGraphQLDeleteMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return testGraphQLMessageBoardAttachment_addMessageBoardAttachment();
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
			testGraphQLGetMessageBoardAttachment_addMessageBoardAttachment();

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

	protected MessageBoardAttachment
			testGraphQLGetMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return testGraphQLMessageBoardAttachment_addMessageBoardAttachment();
	}

	@Test
	public void testGetMessageBoardMessageMessageBoardAttachmentsPage()
		throws Exception {

		Long messageBoardMessageId =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId();
		Long irrelevantMessageBoardMessageId =
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getIrrelevantMessageBoardMessageId();

		Page<MessageBoardAttachment> page =
			messageBoardAttachmentResource.
				getMessageBoardMessageMessageBoardAttachmentsPage(
					messageBoardMessageId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantMessageBoardMessageId != null) {
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
			assertValid(
				page,
				testGetMessageBoardMessageMessageBoardAttachmentsPage_getExpectedActions(
					irrelevantMessageBoardMessageId));
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
		assertValid(
			page,
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getExpectedActions(
				messageBoardMessageId));

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment1.getId());

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getExpectedActions(
				Long messageBoardMessageId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments/batch".
				replace(
					"{messageBoardMessageId}",
					String.valueOf(messageBoardMessageId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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

		Long messageBoardThreadId =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getIrrelevantMessageBoardThreadId();

		Page<MessageBoardAttachment> page =
			messageBoardAttachmentResource.
				getMessageBoardThreadMessageBoardAttachmentsPage(
					messageBoardThreadId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantMessageBoardThreadId != null) {
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
			assertValid(
				page,
				testGetMessageBoardThreadMessageBoardAttachmentsPage_getExpectedActions(
					irrelevantMessageBoardThreadId));
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
		assertValid(
			page,
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getExpectedActions(
				messageBoardThreadId));

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment1.getId());

		messageBoardAttachmentResource.deleteMessageBoardAttachment(
			messageBoardAttachment2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getExpectedActions(
				Long messageBoardThreadId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments/batch".
				replace(
					"{messageBoardThreadId}",
					String.valueOf(messageBoardThreadId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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

	@Test
	public void testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardAttachment messageBoardAttachment =
			testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment();

		assertHttpResponseStatusCode(
			204,
			messageBoardAttachmentResource.
				deleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCodeHttpResponse(
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId(),
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode(),
					messageBoardAttachment.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			messageBoardAttachmentResource.
				getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCodeHttpResponse(
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId(),
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode(),
					messageBoardAttachment.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			messageBoardAttachmentResource.
				getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCodeHttpResponse(
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId(),
					testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode(),
					messageBoardAttachment.getExternalReferenceCode()));
	}

	protected Long
			testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardAttachment
			testDeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode()
		throws Exception {

		MessageBoardAttachment postMessageBoardAttachment =
			testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment();

		MessageBoardAttachment getMessageBoardAttachment =
			messageBoardAttachmentResource.
				getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(
					testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId(),
					testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode(),
					postMessageBoardAttachment.getExternalReferenceCode());

		assertEquals(postMessageBoardAttachment, getMessageBoardAttachment);
		assertValid(getMessageBoardAttachment);
	}

	protected Long
			testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MessageBoardAttachment
			testGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode()
		throws Exception {

		MessageBoardAttachment messageBoardAttachment =
			testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment();

		Assert.assertTrue(
			equals(
				messageBoardAttachment,
				MessageBoardAttachmentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"messageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId() +
													"\"");

										put(
											"messageBoardMessageExternalReferenceCode",
											"\"" +
												testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode() +
													"\"");

										put(
											"externalReferenceCode",
											"\"" +
												messageBoardAttachment.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/messageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode"))));
	}

	protected Long
			testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getSiteId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_getMessageBoardMessageExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantMessageBoardMessageExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"messageBoardMessageExternalReferenceCode",
									irrelevantMessageBoardMessageExternalReferenceCode);
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected MessageBoardAttachment
			testGraphQLGetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode_addMessageBoardAttachment()
		throws Exception {

		return testGraphQLMessageBoardAttachment_addMessageBoardAttachment();
	}

	protected MessageBoardAttachment
			testGraphQLMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		MessageBoardAttachment messageBoardAttachment,
		List<MessageBoardAttachment> messageBoardAttachments) {

		boolean contains = false;

		for (MessageBoardAttachment item : messageBoardAttachments) {
			if (equals(messageBoardAttachment, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			messageBoardAttachments + " does not contain " +
				messageBoardAttachment,
			contains);
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (messageBoardAttachment.getActions() == null) {
					valid = false;
				}

				continue;
			}

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

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (messageBoardAttachment.getExternalReferenceCode() == null) {
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
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<MessageBoardAttachment> page,
		Map<String, Map<String, String>> expectedActions) {

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

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
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

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
		MessageBoardAttachment messageBoardAttachment1,
		MessageBoardAttachment messageBoardAttachment2) {

		if (messageBoardAttachment1 == messageBoardAttachment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)messageBoardAttachment1.getActions(),
						(Map)messageBoardAttachment2.getActions())) {

					return false;
				}

				continue;
			}

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

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardAttachment1.getExternalReferenceCode(),
						messageBoardAttachment2.getExternalReferenceCode())) {

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

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
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

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
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

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentUrl")) {
			Object object = messageBoardAttachment.getContentUrl();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("contentValue")) {
			Object object = messageBoardAttachment.getContentValue();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			Object object = messageBoardAttachment.getEncodingFormat();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = messageBoardAttachment.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			Object object = messageBoardAttachment.getFileExtension();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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
			Object object = messageBoardAttachment.getTitle();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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
				externalReferenceCode = StringUtil.toLowerCase(
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

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

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

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseMessageBoardAttachmentResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.delivery.resource.v1_0.
			MessageBoardAttachmentResource _messageBoardAttachmentResource;

}