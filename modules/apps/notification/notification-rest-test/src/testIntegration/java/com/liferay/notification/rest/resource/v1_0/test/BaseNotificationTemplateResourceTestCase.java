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

package com.liferay.notification.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.notification.rest.client.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.client.http.HttpInvoker;
import com.liferay.notification.rest.client.pagination.Page;
import com.liferay.notification.rest.client.pagination.Pagination;
import com.liferay.notification.rest.client.resource.v1_0.NotificationTemplateResource;
import com.liferay.notification.rest.client.serdes.v1_0.NotificationTemplateSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseNotificationTemplateResourceTestCase {

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

		_notificationTemplateResource.setContextCompany(testCompany);

		NotificationTemplateResource.Builder builder =
			NotificationTemplateResource.builder();

		notificationTemplateResource = builder.authentication(
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

		NotificationTemplate notificationTemplate1 =
			randomNotificationTemplate();

		String json = objectMapper.writeValueAsString(notificationTemplate1);

		NotificationTemplate notificationTemplate2 =
			NotificationTemplateSerDes.toDTO(json);

		Assert.assertTrue(equals(notificationTemplate1, notificationTemplate2));
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

		NotificationTemplate notificationTemplate =
			randomNotificationTemplate();

		String json1 = objectMapper.writeValueAsString(notificationTemplate);
		String json2 = NotificationTemplateSerDes.toJSON(notificationTemplate);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		NotificationTemplate notificationTemplate =
			randomNotificationTemplate();

		notificationTemplate.setBcc(regex);
		notificationTemplate.setCc(regex);
		notificationTemplate.setDescription(regex);
		notificationTemplate.setFrom(regex);
		notificationTemplate.setName(regex);
		notificationTemplate.setType(regex);

		String json = NotificationTemplateSerDes.toJSON(notificationTemplate);

		Assert.assertFalse(json.contains(regex));

		notificationTemplate = NotificationTemplateSerDes.toDTO(json);

		Assert.assertEquals(regex, notificationTemplate.getBcc());
		Assert.assertEquals(regex, notificationTemplate.getCc());
		Assert.assertEquals(regex, notificationTemplate.getDescription());
		Assert.assertEquals(regex, notificationTemplate.getFrom());
		Assert.assertEquals(regex, notificationTemplate.getName());
		Assert.assertEquals(regex, notificationTemplate.getType());
	}

	@Test
	public void testGetNotificationTemplatesPage() throws Exception {
		Page<NotificationTemplate> page =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		NotificationTemplate notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		NotificationTemplate notificationTemplate2 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		page = notificationTemplateResource.getNotificationTemplatesPage(
			null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			notificationTemplate1, (List<NotificationTemplate>)page.getItems());
		assertContains(
			notificationTemplate2, (List<NotificationTemplate>)page.getItems());
		assertValid(page);

		notificationTemplateResource.deleteNotificationTemplate(
			notificationTemplate1.getId());

		notificationTemplateResource.deleteNotificationTemplate(
			notificationTemplate2.getId());
	}

	@Test
	public void testGetNotificationTemplatesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationTemplate notificationTemplate1 =
			randomNotificationTemplate();

		notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				notificationTemplate1);

		for (EntityField entityField : entityFields) {
			Page<NotificationTemplate> page =
				notificationTemplateResource.getNotificationTemplatesPage(
					null, null,
					getFilterString(
						entityField, "between", notificationTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationTemplate1),
				(List<NotificationTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationTemplatesPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationTemplate notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationTemplate notificationTemplate2 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		for (EntityField entityField : entityFields) {
			Page<NotificationTemplate> page =
				notificationTemplateResource.getNotificationTemplatesPage(
					null, null,
					getFilterString(entityField, "eq", notificationTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationTemplate1),
				(List<NotificationTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationTemplatesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationTemplate notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationTemplate notificationTemplate2 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		for (EntityField entityField : entityFields) {
			Page<NotificationTemplate> page =
				notificationTemplateResource.getNotificationTemplatesPage(
					null, null,
					getFilterString(entityField, "eq", notificationTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(notificationTemplate1),
				(List<NotificationTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetNotificationTemplatesPageWithPagination()
		throws Exception {

		Page<NotificationTemplate> totalPage =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		NotificationTemplate notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		NotificationTemplate notificationTemplate2 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		NotificationTemplate notificationTemplate3 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				randomNotificationTemplate());

		Page<NotificationTemplate> page1 =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null, null, Pagination.of(1, totalCount + 2), null);

		List<NotificationTemplate> notificationTemplates1 =
			(List<NotificationTemplate>)page1.getItems();

		Assert.assertEquals(
			notificationTemplates1.toString(), totalCount + 2,
			notificationTemplates1.size());

		Page<NotificationTemplate> page2 =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<NotificationTemplate> notificationTemplates2 =
			(List<NotificationTemplate>)page2.getItems();

		Assert.assertEquals(
			notificationTemplates2.toString(), 1,
			notificationTemplates2.size());

		Page<NotificationTemplate> page3 =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(
			notificationTemplate1,
			(List<NotificationTemplate>)page3.getItems());
		assertContains(
			notificationTemplate2,
			(List<NotificationTemplate>)page3.getItems());
		assertContains(
			notificationTemplate3,
			(List<NotificationTemplate>)page3.getItems());
	}

	@Test
	public void testGetNotificationTemplatesPageWithSortDateTime()
		throws Exception {

		testGetNotificationTemplatesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, notificationTemplate1, notificationTemplate2) -> {
				BeanTestUtil.setProperty(
					notificationTemplate1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetNotificationTemplatesPageWithSortDouble()
		throws Exception {

		testGetNotificationTemplatesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, notificationTemplate1, notificationTemplate2) -> {
				BeanTestUtil.setProperty(
					notificationTemplate1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					notificationTemplate2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetNotificationTemplatesPageWithSortInteger()
		throws Exception {

		testGetNotificationTemplatesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, notificationTemplate1, notificationTemplate2) -> {
				BeanTestUtil.setProperty(
					notificationTemplate1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					notificationTemplate2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetNotificationTemplatesPageWithSortString()
		throws Exception {

		testGetNotificationTemplatesPageWithSort(
			EntityField.Type.STRING,
			(entityField, notificationTemplate1, notificationTemplate2) -> {
				Class<?> clazz = notificationTemplate1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						notificationTemplate1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						notificationTemplate2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						notificationTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						notificationTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						notificationTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						notificationTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetNotificationTemplatesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, NotificationTemplate, NotificationTemplate,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		NotificationTemplate notificationTemplate1 =
			randomNotificationTemplate();
		NotificationTemplate notificationTemplate2 =
			randomNotificationTemplate();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, notificationTemplate1, notificationTemplate2);
		}

		notificationTemplate1 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				notificationTemplate1);

		notificationTemplate2 =
			testGetNotificationTemplatesPage_addNotificationTemplate(
				notificationTemplate2);

		for (EntityField entityField : entityFields) {
			Page<NotificationTemplate> ascPage =
				notificationTemplateResource.getNotificationTemplatesPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(notificationTemplate1, notificationTemplate2),
				(List<NotificationTemplate>)ascPage.getItems());

			Page<NotificationTemplate> descPage =
				notificationTemplateResource.getNotificationTemplatesPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(notificationTemplate2, notificationTemplate1),
				(List<NotificationTemplate>)descPage.getItems());
		}
	}

	protected NotificationTemplate
			testGetNotificationTemplatesPage_addNotificationTemplate(
				NotificationTemplate notificationTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetNotificationTemplatesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"notificationTemplates",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject notificationTemplatesJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/notificationTemplates");

		long totalCount = notificationTemplatesJSONObject.getLong("totalCount");

		NotificationTemplate notificationTemplate1 =
			testGraphQLGetNotificationTemplatesPage_addNotificationTemplate();
		NotificationTemplate notificationTemplate2 =
			testGraphQLGetNotificationTemplatesPage_addNotificationTemplate();

		notificationTemplatesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/notificationTemplates");

		Assert.assertEquals(
			totalCount + 2,
			notificationTemplatesJSONObject.getLong("totalCount"));

		assertContains(
			notificationTemplate1,
			Arrays.asList(
				NotificationTemplateSerDes.toDTOs(
					notificationTemplatesJSONObject.getString("items"))));
		assertContains(
			notificationTemplate2,
			Arrays.asList(
				NotificationTemplateSerDes.toDTOs(
					notificationTemplatesJSONObject.getString("items"))));
	}

	protected NotificationTemplate
			testGraphQLGetNotificationTemplatesPage_addNotificationTemplate()
		throws Exception {

		return testGraphQLNotificationTemplate_addNotificationTemplate();
	}

	@Test
	public void testPostNotificationTemplate() throws Exception {
		NotificationTemplate randomNotificationTemplate =
			randomNotificationTemplate();

		NotificationTemplate postNotificationTemplate =
			testPostNotificationTemplate_addNotificationTemplate(
				randomNotificationTemplate);

		assertEquals(randomNotificationTemplate, postNotificationTemplate);
		assertValid(postNotificationTemplate);
	}

	protected NotificationTemplate
			testPostNotificationTemplate_addNotificationTemplate(
				NotificationTemplate notificationTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteNotificationTemplate() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationTemplate notificationTemplate =
			testDeleteNotificationTemplate_addNotificationTemplate();

		assertHttpResponseStatusCode(
			204,
			notificationTemplateResource.deleteNotificationTemplateHttpResponse(
				notificationTemplate.getId()));

		assertHttpResponseStatusCode(
			404,
			notificationTemplateResource.getNotificationTemplateHttpResponse(
				notificationTemplate.getId()));

		assertHttpResponseStatusCode(
			404,
			notificationTemplateResource.getNotificationTemplateHttpResponse(
				0L));
	}

	protected NotificationTemplate
			testDeleteNotificationTemplate_addNotificationTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate =
			testGraphQLDeleteNotificationTemplate_addNotificationTemplate();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteNotificationTemplate",
						new HashMap<String, Object>() {
							{
								put(
									"notificationTemplateId",
									notificationTemplate.getId());
							}
						})),
				"JSONObject/data", "Object/deleteNotificationTemplate"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"notificationTemplate",
					new HashMap<String, Object>() {
						{
							put(
								"notificationTemplateId",
								notificationTemplate.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected NotificationTemplate
			testGraphQLDeleteNotificationTemplate_addNotificationTemplate()
		throws Exception {

		return testGraphQLNotificationTemplate_addNotificationTemplate();
	}

	@Test
	public void testGetNotificationTemplate() throws Exception {
		NotificationTemplate postNotificationTemplate =
			testGetNotificationTemplate_addNotificationTemplate();

		NotificationTemplate getNotificationTemplate =
			notificationTemplateResource.getNotificationTemplate(
				postNotificationTemplate.getId());

		assertEquals(postNotificationTemplate, getNotificationTemplate);
		assertValid(getNotificationTemplate);
	}

	protected NotificationTemplate
			testGetNotificationTemplate_addNotificationTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate =
			testGraphQLGetNotificationTemplate_addNotificationTemplate();

		Assert.assertTrue(
			equals(
				notificationTemplate,
				NotificationTemplateSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"notificationTemplate",
								new HashMap<String, Object>() {
									{
										put(
											"notificationTemplateId",
											notificationTemplate.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/notificationTemplate"))));
	}

	@Test
	public void testGraphQLGetNotificationTemplateNotFound() throws Exception {
		Long irrelevantNotificationTemplateId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"notificationTemplate",
						new HashMap<String, Object>() {
							{
								put(
									"notificationTemplateId",
									irrelevantNotificationTemplateId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected NotificationTemplate
			testGraphQLGetNotificationTemplate_addNotificationTemplate()
		throws Exception {

		return testGraphQLNotificationTemplate_addNotificationTemplate();
	}

	@Test
	public void testPatchNotificationTemplate() throws Exception {
		NotificationTemplate postNotificationTemplate =
			testPatchNotificationTemplate_addNotificationTemplate();

		NotificationTemplate randomPatchNotificationTemplate =
			randomPatchNotificationTemplate();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		NotificationTemplate patchNotificationTemplate =
			notificationTemplateResource.patchNotificationTemplate(
				postNotificationTemplate.getId(),
				randomPatchNotificationTemplate);

		NotificationTemplate expectedPatchNotificationTemplate =
			postNotificationTemplate.clone();

		BeanTestUtil.copyProperties(
			randomPatchNotificationTemplate, expectedPatchNotificationTemplate);

		NotificationTemplate getNotificationTemplate =
			notificationTemplateResource.getNotificationTemplate(
				patchNotificationTemplate.getId());

		assertEquals(
			expectedPatchNotificationTemplate, getNotificationTemplate);
		assertValid(getNotificationTemplate);
	}

	protected NotificationTemplate
			testPatchNotificationTemplate_addNotificationTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutNotificationTemplate() throws Exception {
		NotificationTemplate postNotificationTemplate =
			testPutNotificationTemplate_addNotificationTemplate();

		NotificationTemplate randomNotificationTemplate =
			randomNotificationTemplate();

		NotificationTemplate putNotificationTemplate =
			notificationTemplateResource.putNotificationTemplate(
				postNotificationTemplate.getId(), randomNotificationTemplate);

		assertEquals(randomNotificationTemplate, putNotificationTemplate);
		assertValid(putNotificationTemplate);

		NotificationTemplate getNotificationTemplate =
			notificationTemplateResource.getNotificationTemplate(
				putNotificationTemplate.getId());

		assertEquals(randomNotificationTemplate, getNotificationTemplate);
		assertValid(getNotificationTemplate);
	}

	protected NotificationTemplate
			testPutNotificationTemplate_addNotificationTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected NotificationTemplate
			testGraphQLNotificationTemplate_addNotificationTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		NotificationTemplate notificationTemplate,
		List<NotificationTemplate> notificationTemplates) {

		boolean contains = false;

		for (NotificationTemplate item : notificationTemplates) {
			if (equals(notificationTemplate, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			notificationTemplates + " does not contain " + notificationTemplate,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		NotificationTemplate notificationTemplate1,
		NotificationTemplate notificationTemplate2) {

		Assert.assertTrue(
			notificationTemplate1 + " does not equal " + notificationTemplate2,
			equals(notificationTemplate1, notificationTemplate2));
	}

	protected void assertEquals(
		List<NotificationTemplate> notificationTemplates1,
		List<NotificationTemplate> notificationTemplates2) {

		Assert.assertEquals(
			notificationTemplates1.size(), notificationTemplates2.size());

		for (int i = 0; i < notificationTemplates1.size(); i++) {
			NotificationTemplate notificationTemplate1 =
				notificationTemplates1.get(i);
			NotificationTemplate notificationTemplate2 =
				notificationTemplates2.get(i);

			assertEquals(notificationTemplate1, notificationTemplate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<NotificationTemplate> notificationTemplates1,
		List<NotificationTemplate> notificationTemplates2) {

		Assert.assertEquals(
			notificationTemplates1.size(), notificationTemplates2.size());

		for (NotificationTemplate notificationTemplate1 :
				notificationTemplates1) {

			boolean contains = false;

			for (NotificationTemplate notificationTemplate2 :
					notificationTemplates2) {

				if (equals(notificationTemplate1, notificationTemplate2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				notificationTemplates2 + " does not contain " +
					notificationTemplate1,
				contains);
		}
	}

	protected void assertValid(NotificationTemplate notificationTemplate)
		throws Exception {

		boolean valid = true;

		if (notificationTemplate.getDateCreated() == null) {
			valid = false;
		}

		if (notificationTemplate.getDateModified() == null) {
			valid = false;
		}

		if (notificationTemplate.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (notificationTemplate.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"attachmentObjectFieldIds", additionalAssertFieldName)) {

				if (notificationTemplate.getAttachmentObjectFieldIds() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("bcc", additionalAssertFieldName)) {
				if (notificationTemplate.getBcc() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("body", additionalAssertFieldName)) {
				if (notificationTemplate.getBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("cc", additionalAssertFieldName)) {
				if (notificationTemplate.getCc() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (notificationTemplate.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("from", additionalAssertFieldName)) {
				if (notificationTemplate.getFrom() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fromName", additionalAssertFieldName)) {
				if (notificationTemplate.getFromName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (notificationTemplate.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (notificationTemplate.getName_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (notificationTemplate.getObjectDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("recipientType", additionalAssertFieldName)) {
				if (notificationTemplate.getRecipientType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subject", additionalAssertFieldName)) {
				if (notificationTemplate.getSubject() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("to", additionalAssertFieldName)) {
				if (notificationTemplate.getTo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (notificationTemplate.getType() == null) {
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

	protected void assertValid(Page<NotificationTemplate> page) {
		boolean valid = false;

		java.util.Collection<NotificationTemplate> notificationTemplates =
			page.getItems();

		int size = notificationTemplates.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.notification.rest.dto.v1_0.NotificationTemplate.
						class)) {

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
		NotificationTemplate notificationTemplate1,
		NotificationTemplate notificationTemplate2) {

		if (notificationTemplate1 == notificationTemplate2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getActions(),
						(Map)notificationTemplate2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"attachmentObjectFieldIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						notificationTemplate1.getAttachmentObjectFieldIds(),
						notificationTemplate2.getAttachmentObjectFieldIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bcc", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getBcc(),
						notificationTemplate2.getBcc())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("body", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getBody(),
						(Map)notificationTemplate2.getBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("cc", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getCc(),
						notificationTemplate2.getCc())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getDateCreated(),
						notificationTemplate2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getDateModified(),
						notificationTemplate2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getDescription(),
						notificationTemplate2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("from", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getFrom(),
						notificationTemplate2.getFrom())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fromName", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getFromName(),
						(Map)notificationTemplate2.getFromName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getId(),
						notificationTemplate2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getName(),
						notificationTemplate2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getName_i18n(),
						(Map)notificationTemplate2.getName_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						notificationTemplate1.getObjectDefinitionId(),
						notificationTemplate2.getObjectDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("recipientType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getRecipientType(),
						notificationTemplate2.getRecipientType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subject", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getSubject(),
						(Map)notificationTemplate2.getSubject())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("to", additionalAssertFieldName)) {
				if (!equals(
						(Map)notificationTemplate1.getTo(),
						(Map)notificationTemplate2.getTo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						notificationTemplate1.getType(),
						notificationTemplate2.getType())) {

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

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_notificationTemplateResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_notificationTemplateResource;

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
		NotificationTemplate notificationTemplate) {

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

		if (entityFieldName.equals("attachmentObjectFieldIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("bcc")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getBcc()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("body")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("cc")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getCc()));
			sb.append("'");

			return sb.toString();
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
							notificationTemplate.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							notificationTemplate.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(notificationTemplate.getDateCreated()));
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
							notificationTemplate.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							notificationTemplate.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(notificationTemplate.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("from")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getFrom()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fromName")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("recipientType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subject")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("to")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(notificationTemplate.getType()));
			sb.append("'");

			return sb.toString();
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

	protected NotificationTemplate randomNotificationTemplate()
		throws Exception {

		return new NotificationTemplate() {
			{
				bcc = StringUtil.toLowerCase(RandomTestUtil.randomString());
				cc = StringUtil.toLowerCase(RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				from = StringUtil.toLowerCase(RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				objectDefinitionId = RandomTestUtil.randomLong();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected NotificationTemplate randomIrrelevantNotificationTemplate()
		throws Exception {

		NotificationTemplate randomIrrelevantNotificationTemplate =
			randomNotificationTemplate();

		return randomIrrelevantNotificationTemplate;
	}

	protected NotificationTemplate randomPatchNotificationTemplate()
		throws Exception {

		return randomNotificationTemplate();
	}

	protected NotificationTemplateResource notificationTemplateResource;
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
		LogFactoryUtil.getLog(BaseNotificationTemplateResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource
			_notificationTemplateResource;

}