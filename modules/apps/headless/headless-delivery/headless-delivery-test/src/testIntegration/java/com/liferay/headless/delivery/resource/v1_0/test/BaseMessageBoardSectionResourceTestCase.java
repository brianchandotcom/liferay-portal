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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardSectionResource;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardSectionSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_messageBoardSectionResource.setContextCompany(testCompany);

		MessageBoardSectionResource.Builder builder =
			MessageBoardSectionResource.builder();

		messageBoardSectionResource = builder.authentication(
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

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();

		String json = objectMapper.writeValueAsString(messageBoardSection1);

		MessageBoardSection messageBoardSection2 =
			MessageBoardSectionSerDes.toDTO(json);

		Assert.assertTrue(equals(messageBoardSection1, messageBoardSection2));
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

		MessageBoardSection messageBoardSection = randomMessageBoardSection();

		String json1 = objectMapper.writeValueAsString(messageBoardSection);
		String json2 = MessageBoardSectionSerDes.toJSON(messageBoardSection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MessageBoardSection messageBoardSection = randomMessageBoardSection();

		messageBoardSection.setDescription(regex);
		messageBoardSection.setTitle(regex);

		String json = MessageBoardSectionSerDes.toJSON(messageBoardSection);

		Assert.assertFalse(json.contains(regex));

		messageBoardSection = MessageBoardSectionSerDes.toDTO(json);

		Assert.assertEquals(regex, messageBoardSection.getDescription());
		Assert.assertEquals(regex, messageBoardSection.getTitle());
	}

	@Test
	public void testDeleteMessageBoardSection() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection =
			testDeleteMessageBoardSection_addMessageBoardSection();

		assertHttpResponseStatusCode(
			204,
			messageBoardSectionResource.deleteMessageBoardSectionHttpResponse(
				messageBoardSection.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.getMessageBoardSectionHttpResponse(
				messageBoardSection.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.getMessageBoardSectionHttpResponse(0L));
	}

	protected MessageBoardSection
			testDeleteMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testGraphQLDeleteMessageBoardSection() throws Exception {
		MessageBoardSection messageBoardSection =
			testGraphQLMessageBoardSection_addMessageBoardSection();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteMessageBoardSection",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardSectionId",
									messageBoardSection.getId());
							}
						})),
				"JSONObject/data", "Object/deleteMessageBoardSection"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"messageBoardSection",
					new HashMap<String, Object>() {
						{
							put(
								"messageBoardSectionId",
								messageBoardSection.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testGetMessageBoardSection_addMessageBoardSection();

		MessageBoardSection getMessageBoardSection =
			messageBoardSectionResource.getMessageBoardSection(
				postMessageBoardSection.getId());

		assertEquals(postMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testGetMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testGraphQLGetMessageBoardSection() throws Exception {
		MessageBoardSection messageBoardSection =
			testGraphQLMessageBoardSection_addMessageBoardSection();

		Assert.assertTrue(
			equals(
				messageBoardSection,
				MessageBoardSectionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"messageBoardSection",
								new HashMap<String, Object>() {
									{
										put(
											"messageBoardSectionId",
											messageBoardSection.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/messageBoardSection"))));
	}

	@Test
	public void testGraphQLGetMessageBoardSectionNotFound() throws Exception {
		Long irrelevantMessageBoardSectionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardSection",
						new HashMap<String, Object>() {
							{
								put(
									"messageBoardSectionId",
									irrelevantMessageBoardSectionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testPatchMessageBoardSection_addMessageBoardSection();

		MessageBoardSection randomPatchMessageBoardSection =
			randomPatchMessageBoardSection();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection patchMessageBoardSection =
			messageBoardSectionResource.patchMessageBoardSection(
				postMessageBoardSection.getId(),
				randomPatchMessageBoardSection);

		MessageBoardSection expectedPatchMessageBoardSection =
			postMessageBoardSection.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchMessageBoardSection, randomPatchMessageBoardSection);

		MessageBoardSection getMessageBoardSection =
			messageBoardSectionResource.getMessageBoardSection(
				patchMessageBoardSection.getId());

		assertEquals(expectedPatchMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testPatchMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testPutMessageBoardSection() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testPutMessageBoardSection_addMessageBoardSection();

		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection putMessageBoardSection =
			messageBoardSectionResource.putMessageBoardSection(
				postMessageBoardSection.getId(), randomMessageBoardSection);

		assertEquals(randomMessageBoardSection, putMessageBoardSection);
		assertValid(putMessageBoardSection);

		MessageBoardSection getMessageBoardSection =
			messageBoardSectionResource.getMessageBoardSection(
				putMessageBoardSection.getId());

		assertEquals(randomMessageBoardSection, getMessageBoardSection);
		assertValid(getMessageBoardSection);
	}

	protected MessageBoardSection
			testPutMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testGetMessageBoardSectionPermissionsPage() throws Exception {
		MessageBoardSection postMessageBoardSection =
			testGetMessageBoardSectionPermissionsPage_addMessageBoardSection();

		Page<Permission> page =
			messageBoardSectionResource.getMessageBoardSectionPermissionsPage(
				postMessageBoardSection.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected MessageBoardSection
			testGetMessageBoardSectionPermissionsPage_addMessageBoardSection()
		throws Exception {

		return testPostMessageBoardSectionMessageBoardSection_addMessageBoardSection(
			randomMessageBoardSection());
	}

	@Test
	public void testPutMessageBoardSectionPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection =
			testPutMessageBoardSectionPermission_addMessageBoardSection();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			messageBoardSectionResource.
				putMessageBoardSectionPermissionHttpResponse(
					messageBoardSection.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.
				putMessageBoardSectionPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected MessageBoardSection
			testPutMessageBoardSectionPermission_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testPutMessageBoardSectionSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection =
			testPutMessageBoardSectionSubscribe_addMessageBoardSection();

		assertHttpResponseStatusCode(
			204,
			messageBoardSectionResource.
				putMessageBoardSectionSubscribeHttpResponse(
					messageBoardSection.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.
				putMessageBoardSectionSubscribeHttpResponse(0L));
	}

	protected MessageBoardSection
			testPutMessageBoardSectionSubscribe_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testPutMessageBoardSectionUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection =
			testPutMessageBoardSectionUnsubscribe_addMessageBoardSection();

		assertHttpResponseStatusCode(
			204,
			messageBoardSectionResource.
				putMessageBoardSectionUnsubscribeHttpResponse(
					messageBoardSection.getId()));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.
				putMessageBoardSectionUnsubscribeHttpResponse(0L));
	}

	protected MessageBoardSection
			testPutMessageBoardSectionUnsubscribe_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPage()
		throws Exception {

		Page<MessageBoardSection> page =
			messageBoardSectionResource.
				getMessageBoardSectionMessageBoardSectionsPage(
					testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId(),
					RandomTestUtil.randomString(), null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();
		Long irrelevantParentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getIrrelevantParentMessageBoardSectionId();

		if (irrelevantParentMessageBoardSectionId != null) {
			MessageBoardSection irrelevantMessageBoardSection =
				testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
					irrelevantParentMessageBoardSectionId,
					randomIrrelevantMessageBoardSection());

			page =
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						irrelevantParentMessageBoardSectionId, null, null, null,
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

		page =
			messageBoardSectionResource.
				getMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			(List<MessageBoardSection>)page.getItems());
		assertValid(page);

		messageBoardSectionResource.deleteMessageBoardSection(
			messageBoardSection1.getId());

		messageBoardSectionResource.deleteMessageBoardSection(
			messageBoardSection2.getId());
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
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						parentMessageBoardSectionId, null, null,
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
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						parentMessageBoardSectionId, null, null,
						getFilterString(
							entityField, "eq", messageBoardSection1),
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
			messageBoardSectionResource.
				getMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null, null,
					Pagination.of(1, 2), null);

		List<MessageBoardSection> messageBoardSections1 =
			(List<MessageBoardSection>)page1.getItems();

		Assert.assertEquals(
			messageBoardSections1.toString(), 2, messageBoardSections1.size());

		Page<MessageBoardSection> page2 =
			messageBoardSectionResource.
				getMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSection> messageBoardSections2 =
			(List<MessageBoardSection>)page2.getItems();

		Assert.assertEquals(
			messageBoardSections2.toString(), 1, messageBoardSections2.size());

		Page<MessageBoardSection> page3 =
			messageBoardSectionResource.
				getMessageBoardSectionMessageBoardSectionsPage(
					parentMessageBoardSectionId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSection1, messageBoardSection2,
				messageBoardSection3),
			(List<MessageBoardSection>)page3.getItems());
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithSortDateTime()
		throws Exception {

		testGetMessageBoardSectionMessageBoardSectionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				BeanUtils.setProperty(
					messageBoardSection1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithSortInteger()
		throws Exception {

		testGetMessageBoardSectionMessageBoardSectionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				BeanUtils.setProperty(
					messageBoardSection1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardSection2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMessageBoardSectionMessageBoardSectionsPageWithSortString()
		throws Exception {

		testGetMessageBoardSectionMessageBoardSectionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				Class<?> clazz = messageBoardSection1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetMessageBoardSectionMessageBoardSectionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardSection, MessageBoardSection,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentMessageBoardSectionId =
			testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardSection1, messageBoardSection2);
		}

		messageBoardSection1 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection1);

		messageBoardSection2 =
			testGetMessageBoardSectionMessageBoardSectionsPage_addMessageBoardSection(
				parentMessageBoardSectionId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						parentMessageBoardSectionId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				messageBoardSectionResource.
					getMessageBoardSectionMessageBoardSectionsPage(
						parentMessageBoardSectionId, null, null, null,
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

		return messageBoardSectionResource.
			postMessageBoardSectionMessageBoardSection(
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

		return messageBoardSectionResource.
			postMessageBoardSectionMessageBoardSection(
				testGetMessageBoardSectionMessageBoardSectionsPage_getParentMessageBoardSectionId(),
				messageBoardSection);
	}

	@Test
	public void testGetSiteMessageBoardSectionsPage() throws Exception {
		Page<MessageBoardSection> page =
			messageBoardSectionResource.getSiteMessageBoardSectionsPage(
				testGetSiteMessageBoardSectionsPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteMessageBoardSectionsPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			MessageBoardSection irrelevantMessageBoardSection =
				testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
					irrelevantSiteId, randomIrrelevantMessageBoardSection());

			page = messageBoardSectionResource.getSiteMessageBoardSectionsPage(
				irrelevantSiteId, null, null, null, null, Pagination.of(1, 2),
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

		page = messageBoardSectionResource.getSiteMessageBoardSectionsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			(List<MessageBoardSection>)page.getItems());
		assertValid(page);

		messageBoardSectionResource.deleteMessageBoardSection(
			messageBoardSection1.getId());

		messageBoardSectionResource.deleteMessageBoardSection(
			messageBoardSection2.getId());
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
				messageBoardSectionResource.getSiteMessageBoardSectionsPage(
					siteId, null, null, null,
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
				messageBoardSectionResource.getSiteMessageBoardSectionsPage(
					siteId, null, null, null,
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

		Page<MessageBoardSection> page1 =
			messageBoardSectionResource.getSiteMessageBoardSectionsPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<MessageBoardSection> messageBoardSections1 =
			(List<MessageBoardSection>)page1.getItems();

		Assert.assertEquals(
			messageBoardSections1.toString(), 2, messageBoardSections1.size());

		Page<MessageBoardSection> page2 =
			messageBoardSectionResource.getSiteMessageBoardSectionsPage(
				siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MessageBoardSection> messageBoardSections2 =
			(List<MessageBoardSection>)page2.getItems();

		Assert.assertEquals(
			messageBoardSections2.toString(), 1, messageBoardSections2.size());

		Page<MessageBoardSection> page3 =
			messageBoardSectionResource.getSiteMessageBoardSectionsPage(
				siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				messageBoardSection1, messageBoardSection2,
				messageBoardSection3),
			(List<MessageBoardSection>)page3.getItems());
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithSortDateTime()
		throws Exception {

		testGetSiteMessageBoardSectionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				BeanUtils.setProperty(
					messageBoardSection1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithSortInteger()
		throws Exception {

		testGetSiteMessageBoardSectionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				BeanUtils.setProperty(
					messageBoardSection1, entityField.getName(), 0);
				BeanUtils.setProperty(
					messageBoardSection2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteMessageBoardSectionsPageWithSortString()
		throws Exception {

		testGetSiteMessageBoardSectionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, messageBoardSection1, messageBoardSection2) -> {
				Class<?> clazz = messageBoardSection1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						messageBoardSection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						messageBoardSection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteMessageBoardSectionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MessageBoardSection, MessageBoardSection,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		MessageBoardSection messageBoardSection1 = randomMessageBoardSection();
		MessageBoardSection messageBoardSection2 = randomMessageBoardSection();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, messageBoardSection1, messageBoardSection2);
		}

		messageBoardSection1 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection1);

		messageBoardSection2 =
			testGetSiteMessageBoardSectionsPage_addMessageBoardSection(
				siteId, messageBoardSection2);

		for (EntityField entityField : entityFields) {
			Page<MessageBoardSection> ascPage =
				messageBoardSectionResource.getSiteMessageBoardSectionsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(messageBoardSection1, messageBoardSection2),
				(List<MessageBoardSection>)ascPage.getItems());

			Page<MessageBoardSection> descPage =
				messageBoardSectionResource.getSiteMessageBoardSectionsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
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

		return messageBoardSectionResource.postSiteMessageBoardSection(
			siteId, messageBoardSection);
	}

	protected Long testGetSiteMessageBoardSectionsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteMessageBoardSectionsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteMessageBoardSectionsPage() throws Exception {
		Long siteId = testGetSiteMessageBoardSectionsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"messageBoardSections",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject messageBoardSectionsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/messageBoardSections");

		Assert.assertEquals(
			0, messageBoardSectionsJSONObject.get("totalCount"));

		MessageBoardSection messageBoardSection1 =
			testGraphQLMessageBoardSection_addMessageBoardSection();
		MessageBoardSection messageBoardSection2 =
			testGraphQLMessageBoardSection_addMessageBoardSection();

		messageBoardSectionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/messageBoardSections");

		Assert.assertEquals(
			2, messageBoardSectionsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardSection1, messageBoardSection2),
			Arrays.asList(
				MessageBoardSectionSerDes.toDTOs(
					messageBoardSectionsJSONObject.getString("items"))));
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

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGetSiteMessageBoardSectionsPage_getSiteId(),
			messageBoardSection);
	}

	@Test
	public void testGraphQLPostSiteMessageBoardSection() throws Exception {
		MessageBoardSection randomMessageBoardSection =
			randomMessageBoardSection();

		MessageBoardSection messageBoardSection =
			testGraphQLMessageBoardSection_addMessageBoardSection(
				randomMessageBoardSection);

		Assert.assertTrue(
			equals(randomMessageBoardSection, messageBoardSection));
	}

	@Test
	public void testGetSiteMessageBoardSectionPermissionsPage()
		throws Exception {

		Page<Permission> page =
			messageBoardSectionResource.
				getSiteMessageBoardSectionPermissionsPage(
					testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected MessageBoardSection
			testGetSiteMessageBoardSectionPermissionsPage_addMessageBoardSection()
		throws Exception {

		return testPostSiteMessageBoardSection_addMessageBoardSection(
			randomMessageBoardSection());
	}

	@Test
	public void testPutSiteMessageBoardSectionPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MessageBoardSection messageBoardSection =
			testPutSiteMessageBoardSectionPermission_addMessageBoardSection();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			messageBoardSectionResource.
				putSiteMessageBoardSectionPermissionHttpResponse(
					messageBoardSection.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			messageBoardSectionResource.
				putSiteMessageBoardSectionPermissionHttpResponse(
					messageBoardSection.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected MessageBoardSection
			testPutSiteMessageBoardSectionPermission_addMessageBoardSection()
		throws Exception {

		return messageBoardSectionResource.postSiteMessageBoardSection(
			testGroup.getGroupId(), randomMessageBoardSection());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (Field field : getDeclaredFields(clazz.getSuperclass())) {
					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected MessageBoardSection
			testGraphQLMessageBoardSection_addMessageBoardSection()
		throws Exception {

		return testGraphQLMessageBoardSection_addMessageBoardSection(
			randomMessageBoardSection());
	}

	protected MessageBoardSection
			testGraphQLMessageBoardSection_addMessageBoardSection(
				MessageBoardSection messageBoardSection)
		throws Exception {

		JSONDeserializer<MessageBoardSection> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(MessageBoardSection.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(messageBoardSection));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteMessageBoardSection",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("messageBoardSection", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteMessageBoardSection"),
			MessageBoardSection.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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

	protected void assertValid(MessageBoardSection messageBoardSection)
		throws Exception {

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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (messageBoardSection.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (messageBoardSection.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (messageBoardSection.getCustomFields() == null) {
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

			if (Objects.equals(
					"parentMessageBoardSectionId", additionalAssertFieldName)) {

				if (messageBoardSection.getParentMessageBoardSectionId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (messageBoardSection.getSubscribed() == null) {
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

		java.util.Collection<MessageBoardSection> messageBoardSections =
			page.getItems();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.MessageBoardSection.
						class)) {

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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)messageBoardSection1.getActions(),
						(Map)messageBoardSection2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getCreator(),
						messageBoardSection2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getCustomFields(),
						messageBoardSection2.getCustomFields())) {

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

			if (Objects.equals(
					"parentMessageBoardSectionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						messageBoardSection1.getParentMessageBoardSectionId(),
						messageBoardSection2.
							getParentMessageBoardSectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						messageBoardSection1.getSubscribed(),
						messageBoardSection2.getSubscribed())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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
		MessageBoardSection messageBoardSection) {

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

		if (entityFieldName.equals("parentMessageBoardSectionId")) {
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

	protected MessageBoardSection randomMessageBoardSection() throws Exception {
		return new MessageBoardSection() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				numberOfMessageBoardSections = RandomTestUtil.randomInt();
				numberOfMessageBoardThreads = RandomTestUtil.randomInt();
				parentMessageBoardSectionId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected MessageBoardSection randomIrrelevantMessageBoardSection()
		throws Exception {

		MessageBoardSection randomIrrelevantMessageBoardSection =
			randomMessageBoardSection();

		randomIrrelevantMessageBoardSection.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantMessageBoardSection;
	}

	protected MessageBoardSection randomPatchMessageBoardSection()
		throws Exception {

		return randomMessageBoardSection();
	}

	protected MessageBoardSectionResource messageBoardSectionResource;
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
	private
		com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource
			_messageBoardSectionResource;

}