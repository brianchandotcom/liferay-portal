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

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.content.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.content.client.http.HttpInvoker;
import com.liferay.headless.admin.content.client.pagination.Page;
import com.liferay.headless.admin.content.client.pagination.Pagination;
import com.liferay.headless.admin.content.client.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.headless.admin.content.client.serdes.v1_0.DisplayPageTemplateSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
public abstract class BaseDisplayPageTemplateResourceTestCase {

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

		_displayPageTemplateResource.setContextCompany(testCompany);

		DisplayPageTemplateResource.Builder builder =
			DisplayPageTemplateResource.builder();

		displayPageTemplateResource = builder.authentication(
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

		DisplayPageTemplate displayPageTemplate1 = randomDisplayPageTemplate();

		String json = objectMapper.writeValueAsString(displayPageTemplate1);

		DisplayPageTemplate displayPageTemplate2 =
			DisplayPageTemplateSerDes.toDTO(json);

		Assert.assertTrue(equals(displayPageTemplate1, displayPageTemplate2));
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

		DisplayPageTemplate displayPageTemplate = randomDisplayPageTemplate();

		String json1 = objectMapper.writeValueAsString(displayPageTemplate);
		String json2 = DisplayPageTemplateSerDes.toJSON(displayPageTemplate);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DisplayPageTemplate displayPageTemplate = randomDisplayPageTemplate();

		displayPageTemplate.setDisplayPageTemplateKey(regex);
		displayPageTemplate.setTitle(regex);
		displayPageTemplate.setUuid(regex);

		String json = DisplayPageTemplateSerDes.toJSON(displayPageTemplate);

		Assert.assertFalse(json.contains(regex));

		displayPageTemplate = DisplayPageTemplateSerDes.toDTO(json);

		Assert.assertEquals(
			regex, displayPageTemplate.getDisplayPageTemplateKey());
		Assert.assertEquals(regex, displayPageTemplate.getTitle());
		Assert.assertEquals(regex, displayPageTemplate.getUuid());
	}

	@Test
	public void testGetSiteDisplayPageTemplatesPage() throws Exception {
		Page<DisplayPageTemplate> page =
			displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				testGetSiteDisplayPageTemplatesPage_getSiteId(),
				Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteDisplayPageTemplatesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDisplayPageTemplatesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DisplayPageTemplate irrelevantDisplayPageTemplate =
				testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
					irrelevantSiteId, randomIrrelevantDisplayPageTemplate());

			page = displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				irrelevantSiteId, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDisplayPageTemplate),
				(List<DisplayPageTemplate>)page.getItems());
			assertValid(page);
		}

		DisplayPageTemplate displayPageTemplate1 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, randomDisplayPageTemplate());

		DisplayPageTemplate displayPageTemplate2 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, randomDisplayPageTemplate());

		page = displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
			siteId, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(displayPageTemplate1, displayPageTemplate2),
			(List<DisplayPageTemplate>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDisplayPageTemplatesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteDisplayPageTemplatesPage_getSiteId();

		DisplayPageTemplate displayPageTemplate1 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, randomDisplayPageTemplate());

		DisplayPageTemplate displayPageTemplate2 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, randomDisplayPageTemplate());

		DisplayPageTemplate displayPageTemplate3 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, randomDisplayPageTemplate());

		Page<DisplayPageTemplate> page1 =
			displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				siteId, Pagination.of(1, 2), null);

		List<DisplayPageTemplate> displayPageTemplates1 =
			(List<DisplayPageTemplate>)page1.getItems();

		Assert.assertEquals(
			displayPageTemplates1.toString(), 2, displayPageTemplates1.size());

		Page<DisplayPageTemplate> page2 =
			displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				siteId, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DisplayPageTemplate> displayPageTemplates2 =
			(List<DisplayPageTemplate>)page2.getItems();

		Assert.assertEquals(
			displayPageTemplates2.toString(), 1, displayPageTemplates2.size());

		Page<DisplayPageTemplate> page3 =
			displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				siteId, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				displayPageTemplate1, displayPageTemplate2,
				displayPageTemplate3),
			(List<DisplayPageTemplate>)page3.getItems());
	}

	@Test
	public void testGetSiteDisplayPageTemplatesPageWithSortDateTime()
		throws Exception {

		testGetSiteDisplayPageTemplatesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, displayPageTemplate1, displayPageTemplate2) -> {
				BeanUtils.setProperty(
					displayPageTemplate1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteDisplayPageTemplatesPageWithSortInteger()
		throws Exception {

		testGetSiteDisplayPageTemplatesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, displayPageTemplate1, displayPageTemplate2) -> {
				BeanUtils.setProperty(
					displayPageTemplate1, entityField.getName(), 0);
				BeanUtils.setProperty(
					displayPageTemplate2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteDisplayPageTemplatesPageWithSortString()
		throws Exception {

		testGetSiteDisplayPageTemplatesPageWithSort(
			EntityField.Type.STRING,
			(entityField, displayPageTemplate1, displayPageTemplate2) -> {
				Class<?> clazz = displayPageTemplate1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						displayPageTemplate1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						displayPageTemplate2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						displayPageTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						displayPageTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						displayPageTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						displayPageTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteDisplayPageTemplatesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DisplayPageTemplate, DisplayPageTemplate,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDisplayPageTemplatesPage_getSiteId();

		DisplayPageTemplate displayPageTemplate1 = randomDisplayPageTemplate();
		DisplayPageTemplate displayPageTemplate2 = randomDisplayPageTemplate();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, displayPageTemplate1, displayPageTemplate2);
		}

		displayPageTemplate1 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, displayPageTemplate1);

		displayPageTemplate2 =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				siteId, displayPageTemplate2);

		for (EntityField entityField : entityFields) {
			Page<DisplayPageTemplate> ascPage =
				displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
					siteId, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(displayPageTemplate1, displayPageTemplate2),
				(List<DisplayPageTemplate>)ascPage.getItems());

			Page<DisplayPageTemplate> descPage =
				displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
					siteId, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(displayPageTemplate2, displayPageTemplate1),
				(List<DisplayPageTemplate>)descPage.getItems());
		}
	}

	protected DisplayPageTemplate
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				Long siteId, DisplayPageTemplate displayPageTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteDisplayPageTemplatesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteDisplayPageTemplatesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteDisplayPageTemplatesPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteDisplayPageTemplate() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetSiteDisplayPageTemplate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetSiteDisplayPageTemplateNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DisplayPageTemplate displayPageTemplate1,
		DisplayPageTemplate displayPageTemplate2) {

		Assert.assertTrue(
			displayPageTemplate1 + " does not equal " + displayPageTemplate2,
			equals(displayPageTemplate1, displayPageTemplate2));
	}

	protected void assertEquals(
		List<DisplayPageTemplate> displayPageTemplates1,
		List<DisplayPageTemplate> displayPageTemplates2) {

		Assert.assertEquals(
			displayPageTemplates1.size(), displayPageTemplates2.size());

		for (int i = 0; i < displayPageTemplates1.size(); i++) {
			DisplayPageTemplate displayPageTemplate1 =
				displayPageTemplates1.get(i);
			DisplayPageTemplate displayPageTemplate2 =
				displayPageTemplates2.get(i);

			assertEquals(displayPageTemplate1, displayPageTemplate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DisplayPageTemplate> displayPageTemplates1,
		List<DisplayPageTemplate> displayPageTemplates2) {

		Assert.assertEquals(
			displayPageTemplates1.size(), displayPageTemplates2.size());

		for (DisplayPageTemplate displayPageTemplate1 : displayPageTemplates1) {
			boolean contains = false;

			for (DisplayPageTemplate displayPageTemplate2 :
					displayPageTemplates2) {

				if (equals(displayPageTemplate1, displayPageTemplate2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				displayPageTemplates2 + " does not contain " +
					displayPageTemplate1,
				contains);
		}
	}

	protected void assertValid(DisplayPageTemplate displayPageTemplate)
		throws Exception {

		boolean valid = true;

		if (displayPageTemplate.getDateCreated() == null) {
			valid = false;
		}

		if (displayPageTemplate.getDateModified() == null) {
			valid = false;
		}

		if (!Objects.equals(
				displayPageTemplate.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (displayPageTemplate.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (displayPageTemplate.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (displayPageTemplate.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (displayPageTemplate.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"displayPageTemplateKey", additionalAssertFieldName)) {

				if (displayPageTemplate.getDisplayPageTemplateKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"displayPageTemplateSettings", additionalAssertFieldName)) {

				if (displayPageTemplate.getDisplayPageTemplateSettings() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("markedAsDefault", additionalAssertFieldName)) {
				if (displayPageTemplate.getMarkedAsDefault() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (displayPageTemplate.getPageDefinition() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (displayPageTemplate.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (displayPageTemplate.getUuid() == null) {
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

	protected void assertValid(Page<DisplayPageTemplate> page) {
		boolean valid = false;

		java.util.Collection<DisplayPageTemplate> displayPageTemplates =
			page.getItems();

		int size = displayPageTemplates.size();

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
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.admin.content.dto.v1_0.
						DisplayPageTemplate.class)) {

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
		DisplayPageTemplate displayPageTemplate1,
		DisplayPageTemplate displayPageTemplate2) {

		if (displayPageTemplate1 == displayPageTemplate2) {
			return true;
		}

		if (!Objects.equals(
				displayPageTemplate1.getSiteId(),
				displayPageTemplate2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)displayPageTemplate1.getActions(),
						(Map)displayPageTemplate2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						displayPageTemplate1.getAvailableLanguages(),
						displayPageTemplate2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getCreator(),
						displayPageTemplate2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getCustomFields(),
						displayPageTemplate2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getDateCreated(),
						displayPageTemplate2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getDateModified(),
						displayPageTemplate2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"displayPageTemplateKey", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						displayPageTemplate1.getDisplayPageTemplateKey(),
						displayPageTemplate2.getDisplayPageTemplateKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"displayPageTemplateSettings", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						displayPageTemplate1.getDisplayPageTemplateSettings(),
						displayPageTemplate2.
							getDisplayPageTemplateSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("markedAsDefault", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getMarkedAsDefault(),
						displayPageTemplate2.getMarkedAsDefault())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getPageDefinition(),
						displayPageTemplate2.getPageDefinition())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getTitle(),
						displayPageTemplate2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						displayPageTemplate1.getUuid(),
						displayPageTemplate2.getUuid())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_displayPageTemplateResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_displayPageTemplateResource;

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
		DisplayPageTemplate displayPageTemplate) {

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

		if (entityFieldName.equals("availableLanguages")) {
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
							displayPageTemplate.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							displayPageTemplate.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(displayPageTemplate.getDateCreated()));
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
							displayPageTemplate.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							displayPageTemplate.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(displayPageTemplate.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("displayPageTemplateKey")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					displayPageTemplate.getDisplayPageTemplateKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("displayPageTemplateSettings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("markedAsDefault")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("pageDefinition")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(displayPageTemplate.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(displayPageTemplate.getUuid()));
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

	protected DisplayPageTemplate randomDisplayPageTemplate() throws Exception {
		return new DisplayPageTemplate() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				displayPageTemplateKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				markedAsDefault = RandomTestUtil.randomBoolean();
				siteId = testGroup.getGroupId();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected DisplayPageTemplate randomIrrelevantDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate randomIrrelevantDisplayPageTemplate =
			randomDisplayPageTemplate();

		randomIrrelevantDisplayPageTemplate.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantDisplayPageTemplate;
	}

	protected DisplayPageTemplate randomPatchDisplayPageTemplate()
		throws Exception {

		return randomDisplayPageTemplate();
	}

	protected DisplayPageTemplateResource displayPageTemplateResource;
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
		BaseDisplayPageTemplateResourceTestCase.class);

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
		com.liferay.headless.admin.content.resource.v1_0.
			DisplayPageTemplateResource _displayPageTemplateResource;

}