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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataLayoutSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataLayoutResourceTestCase {

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

		_dataLayoutResource.setContextCompany(testCompany);

		DataLayoutResource.Builder builder = DataLayoutResource.builder();

		dataLayoutResource = builder.authentication(
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

		DataLayout dataLayout1 = randomDataLayout();

		String json = objectMapper.writeValueAsString(dataLayout1);

		DataLayout dataLayout2 = DataLayoutSerDes.toDTO(json);

		Assert.assertTrue(equals(dataLayout1, dataLayout2));
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

		DataLayout dataLayout = randomDataLayout();

		String json1 = objectMapper.writeValueAsString(dataLayout);
		String json2 = DataLayoutSerDes.toJSON(dataLayout);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataLayout dataLayout = randomDataLayout();

		dataLayout.setContentType(regex);
		dataLayout.setDataLayoutKey(regex);
		dataLayout.setPaginationMode(regex);

		String json = DataLayoutSerDes.toJSON(dataLayout);

		Assert.assertFalse(json.contains(regex));

		dataLayout = DataLayoutSerDes.toDTO(json);

		Assert.assertEquals(regex, dataLayout.getContentType());
		Assert.assertEquals(regex, dataLayout.getDataLayoutKey());
		Assert.assertEquals(regex, dataLayout.getPaginationMode());
	}

	@Test
	public void testDeleteDataLayoutsDataDefinition() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataLayout dataLayout =
			testDeleteDataLayoutsDataDefinition_addDataLayout();

		assertHttpResponseStatusCode(
			204,
			dataLayoutResource.deleteDataLayoutsDataDefinitionHttpResponse(
				dataLayout.getDataDefinitionId()));
	}

	protected DataLayout testDeleteDataLayoutsDataDefinition_addDataLayout()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage() throws Exception {
		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
				RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataLayout irrelevantDataLayout =
				testGetDataDefinitionDataLayoutsPage_addDataLayout(
					irrelevantDataDefinitionId, randomIrrelevantDataLayout());

			page = dataLayoutResource.getDataDefinitionDataLayoutsPage(
				irrelevantDataDefinitionId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataLayout),
				(List<DataLayout>)page.getItems());
			assertValid(page);
		}

		DataLayout dataLayout1 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout2 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		page = dataLayoutResource.getDataDefinitionDataLayoutsPage(
			dataDefinitionId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2),
			(List<DataLayout>)page.getItems());
		assertValid(page);

		dataLayoutResource.deleteDataLayout(dataLayout1.getId());

		dataLayoutResource.deleteDataLayout(dataLayout2.getId());
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPageWithPagination()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();

		DataLayout dataLayout1 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout2 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout3 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		Page<DataLayout> page1 =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(1, 2), null);

		List<DataLayout> dataLayouts1 = (List<DataLayout>)page1.getItems();

		Assert.assertEquals(dataLayouts1.toString(), 2, dataLayouts1.size());

		Page<DataLayout> page2 =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataLayout> dataLayouts2 = (List<DataLayout>)page2.getItems();

		Assert.assertEquals(dataLayouts2.toString(), 1, dataLayouts2.size());

		Page<DataLayout> page3 =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2, dataLayout3),
			(List<DataLayout>)page3.getItems());
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPageWithSortDateTime()
		throws Exception {

		testGetDataDefinitionDataLayoutsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataLayout1, dataLayout2) -> {
				BeanUtils.setProperty(
					dataLayout1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPageWithSortInteger()
		throws Exception {

		testGetDataDefinitionDataLayoutsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataLayout1, dataLayout2) -> {
				BeanUtils.setProperty(dataLayout1, entityField.getName(), 0);
				BeanUtils.setProperty(dataLayout2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPageWithSortString()
		throws Exception {

		testGetDataDefinitionDataLayoutsPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataLayout1, dataLayout2) -> {
				Class<?> clazz = dataLayout1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						dataLayout1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						dataLayout2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						dataLayout1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						dataLayout2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						dataLayout1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						dataLayout2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDataDefinitionDataLayoutsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, DataLayout, DataLayout, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();

		DataLayout dataLayout1 = randomDataLayout();
		DataLayout dataLayout2 = randomDataLayout();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, dataLayout1, dataLayout2);
		}

		dataLayout1 = testGetDataDefinitionDataLayoutsPage_addDataLayout(
			dataDefinitionId, dataLayout1);

		dataLayout2 = testGetDataDefinitionDataLayoutsPage_addDataLayout(
			dataDefinitionId, dataLayout2);

		for (EntityField entityField : entityFields) {
			Page<DataLayout> ascPage =
				dataLayoutResource.getDataDefinitionDataLayoutsPage(
					dataDefinitionId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataLayout1, dataLayout2),
				(List<DataLayout>)ascPage.getItems());

			Page<DataLayout> descPage =
				dataLayoutResource.getDataDefinitionDataLayoutsPage(
					dataDefinitionId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataLayout2, dataLayout1),
				(List<DataLayout>)descPage.getItems());
		}
	}

	protected DataLayout testGetDataDefinitionDataLayoutsPage_addDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);
	}

	protected Long testGetDataDefinitionDataLayoutsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataLayoutsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionDataLayout() throws Exception {
		DataLayout randomDataLayout = randomDataLayout();

		DataLayout postDataLayout =
			testPostDataDefinitionDataLayout_addDataLayout(randomDataLayout);

		assertEquals(randomDataLayout, postDataLayout);
		assertValid(postDataLayout);
	}

	protected DataLayout testPostDataDefinitionDataLayout_addDataLayout(
			DataLayout dataLayout)
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
			dataLayout);
	}

	@Test
	public void testDeleteDataLayout() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataLayout dataLayout = testDeleteDataLayout_addDataLayout();

		assertHttpResponseStatusCode(
			204,
			dataLayoutResource.deleteDataLayoutHttpResponse(
				dataLayout.getId()));

		assertHttpResponseStatusCode(
			404,
			dataLayoutResource.getDataLayoutHttpResponse(dataLayout.getId()));

		assertHttpResponseStatusCode(
			404, dataLayoutResource.getDataLayoutHttpResponse(0L));
	}

	protected DataLayout testDeleteDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDataLayout() throws Exception {
		DataLayout dataLayout = testGraphQLDataLayout_addDataLayout();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDataLayout",
						new HashMap<String, Object>() {
							{
								put("dataLayoutId", dataLayout.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDataLayout"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataLayout",
						new HashMap<String, Object>() {
							{
								put("dataLayoutId", dataLayout.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetDataLayout() throws Exception {
		DataLayout postDataLayout = testGetDataLayout_addDataLayout();

		DataLayout getDataLayout = dataLayoutResource.getDataLayout(
			postDataLayout.getId());

		assertEquals(postDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout testGetDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataLayout() throws Exception {
		DataLayout dataLayout = testGraphQLDataLayout_addDataLayout();

		Assert.assertTrue(
			equals(
				dataLayout,
				DataLayoutSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"dataLayout",
								new HashMap<String, Object>() {
									{
										put("dataLayoutId", dataLayout.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/dataLayout"))));
	}

	@Test
	public void testGraphQLGetDataLayoutNotFound() throws Exception {
		Long irrelevantDataLayoutId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataLayout",
						new HashMap<String, Object>() {
							{
								put("dataLayoutId", irrelevantDataLayoutId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutDataLayout() throws Exception {
		DataLayout postDataLayout = testPutDataLayout_addDataLayout();

		DataLayout randomDataLayout = randomDataLayout();

		DataLayout putDataLayout = dataLayoutResource.putDataLayout(
			postDataLayout.getId(), randomDataLayout);

		assertEquals(randomDataLayout, putDataLayout);
		assertValid(putDataLayout);

		DataLayout getDataLayout = dataLayoutResource.getDataLayout(
			putDataLayout.getId());

		assertEquals(randomDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout testPutDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataLayoutByContentTypeByDataLayoutKey()
		throws Exception {

		DataLayout postDataLayout =
			testGetSiteDataLayoutByContentTypeByDataLayoutKey_addDataLayout();

		DataLayout getDataLayout =
			dataLayoutResource.getSiteDataLayoutByContentTypeByDataLayoutKey(
				postDataLayout.getSiteId(), postDataLayout.getContentType(),
				postDataLayout.getDataLayoutKey());

		assertEquals(postDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout
			testGetSiteDataLayoutByContentTypeByDataLayoutKey_addDataLayout()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteDataLayoutByContentTypeByDataLayoutKey()
		throws Exception {

		DataLayout dataLayout = testGraphQLDataLayout_addDataLayout();

		Assert.assertTrue(
			equals(
				dataLayout,
				DataLayoutSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"dataLayoutByContentTypeByDataLayoutKey",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" + dataLayout.getSiteId() +
												"\"");
										put(
											"contentType",
											"\"" + dataLayout.getContentType() +
												"\"");
										put(
											"dataLayoutKey",
											"\"" +
												dataLayout.getDataLayoutKey() +
													"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/dataLayoutByContentTypeByDataLayoutKey"))));
	}

	@Test
	public void testGraphQLGetSiteDataLayoutByContentTypeByDataLayoutKeyNotFound()
		throws Exception {

		String irrelevantContentType =
			"\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantDataLayoutKey =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataLayoutByContentTypeByDataLayoutKey",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put("contentType", irrelevantContentType);
								put("dataLayoutKey", irrelevantDataLayoutKey);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected DataLayout testGraphQLDataLayout_addDataLayout()
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
		DataLayout dataLayout1, DataLayout dataLayout2) {

		Assert.assertTrue(
			dataLayout1 + " does not equal " + dataLayout2,
			equals(dataLayout1, dataLayout2));
	}

	protected void assertEquals(
		List<DataLayout> dataLayouts1, List<DataLayout> dataLayouts2) {

		Assert.assertEquals(dataLayouts1.size(), dataLayouts2.size());

		for (int i = 0; i < dataLayouts1.size(); i++) {
			DataLayout dataLayout1 = dataLayouts1.get(i);
			DataLayout dataLayout2 = dataLayouts2.get(i);

			assertEquals(dataLayout1, dataLayout2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataLayout> dataLayouts1, List<DataLayout> dataLayouts2) {

		Assert.assertEquals(dataLayouts1.size(), dataLayouts2.size());

		for (DataLayout dataLayout1 : dataLayouts1) {
			boolean contains = false;

			for (DataLayout dataLayout2 : dataLayouts2) {
				if (equals(dataLayout1, dataLayout2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataLayouts2 + " does not contain " + dataLayout1, contains);
		}
	}

	protected void assertValid(DataLayout dataLayout) throws Exception {
		boolean valid = true;

		if (dataLayout.getDateCreated() == null) {
			valid = false;
		}

		if (dataLayout.getDateModified() == null) {
			valid = false;
		}

		if (dataLayout.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(dataLayout.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (dataLayout.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (dataLayout.getDataDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutKey", additionalAssertFieldName)) {
				if (dataLayout.getDataLayoutKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutPages", additionalAssertFieldName)) {
				if (dataLayout.getDataLayoutPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataRules", additionalAssertFieldName)) {
				if (dataLayout.getDataRules() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataLayout.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataLayout.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paginationMode", additionalAssertFieldName)) {
				if (dataLayout.getPaginationMode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (dataLayout.getUserId() == null) {
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

	protected void assertValid(Page<DataLayout> page) {
		boolean valid = false;

		java.util.Collection<DataLayout> dataLayouts = page.getItems();

		int size = dataLayouts.size();

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
					com.liferay.data.engine.rest.dto.v2_0.DataLayout.class)) {

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

	protected boolean equals(DataLayout dataLayout1, DataLayout dataLayout2) {
		if (dataLayout1 == dataLayout2) {
			return true;
		}

		if (!Objects.equals(dataLayout1.getSiteId(), dataLayout2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getContentType(),
						dataLayout2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataDefinitionId(),
						dataLayout2.getDataDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataLayoutKey(),
						dataLayout2.getDataLayoutKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutPages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataLayoutPages(),
						dataLayout2.getDataLayoutPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataRules", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataRules(),
						dataLayout2.getDataRules())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDateCreated(),
						dataLayout2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDateModified(),
						dataLayout2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!equals(
						(Map)dataLayout1.getDescription(),
						(Map)dataLayout2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getId(), dataLayout2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)dataLayout1.getName(),
						(Map)dataLayout2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paginationMode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getPaginationMode(),
						dataLayout2.getPaginationMode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getUserId(), dataLayout2.getUserId())) {

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

		if (!(_dataLayoutResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataLayoutResource;

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
		EntityField entityField, String operator, DataLayout dataLayout) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dataDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataLayoutKey")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getDataLayoutKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dataLayoutPages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataRules")) {
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
						DateUtils.addSeconds(dataLayout.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(dataLayout.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataLayout.getDateCreated()));
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
							dataLayout.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(dataLayout.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataLayout.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paginationMode")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getPaginationMode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userId")) {
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

	protected DataLayout randomDataLayout() throws Exception {
		return new DataLayout() {
			{
				contentType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dataDefinitionId = RandomTestUtil.randomLong();
				dataLayoutKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				paginationMode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataLayout randomIrrelevantDataLayout() throws Exception {
		DataLayout randomIrrelevantDataLayout = randomDataLayout();

		randomIrrelevantDataLayout.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDataLayout;
	}

	protected DataLayout randomPatchDataLayout() throws Exception {
		return randomDataLayout();
	}

	protected DataLayoutResource dataLayoutResource;
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
		BaseDataLayoutResourceTestCase.class);

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
	private com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource
		_dataLayoutResource;

}