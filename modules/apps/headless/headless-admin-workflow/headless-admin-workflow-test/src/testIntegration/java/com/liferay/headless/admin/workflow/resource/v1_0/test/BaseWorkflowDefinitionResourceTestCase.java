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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowDefinitionSerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWorkflowDefinitionResourceTestCase {

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

		_workflowDefinitionResource.setContextCompany(testCompany);

		WorkflowDefinitionResource.Builder builder =
			WorkflowDefinitionResource.builder();

		workflowDefinitionResource = builder.authentication(
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

		WorkflowDefinition workflowDefinition1 = randomWorkflowDefinition();

		String json = objectMapper.writeValueAsString(workflowDefinition1);

		WorkflowDefinition workflowDefinition2 = WorkflowDefinitionSerDes.toDTO(
			json);

		Assert.assertTrue(equals(workflowDefinition1, workflowDefinition2));
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

		WorkflowDefinition workflowDefinition = randomWorkflowDefinition();

		String json1 = objectMapper.writeValueAsString(workflowDefinition);
		String json2 = WorkflowDefinitionSerDes.toJSON(workflowDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowDefinition workflowDefinition = randomWorkflowDefinition();

		workflowDefinition.setContent(regex);
		workflowDefinition.setDescription(regex);
		workflowDefinition.setName(regex);
		workflowDefinition.setTitle(regex);
		workflowDefinition.setVersion(regex);

		String json = WorkflowDefinitionSerDes.toJSON(workflowDefinition);

		Assert.assertFalse(json.contains(regex));

		workflowDefinition = WorkflowDefinitionSerDes.toDTO(json);

		Assert.assertEquals(regex, workflowDefinition.getContent());
		Assert.assertEquals(regex, workflowDefinition.getDescription());
		Assert.assertEquals(regex, workflowDefinition.getName());
		Assert.assertEquals(regex, workflowDefinition.getTitle());
		Assert.assertEquals(regex, workflowDefinition.getVersion());
	}

	@Test
	public void testGetWorkflowDefinitionsPage() throws Exception {
		Page<WorkflowDefinition> page =
			workflowDefinitionResource.getWorkflowDefinitionsPage(
				null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		WorkflowDefinition workflowDefinition1 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		WorkflowDefinition workflowDefinition2 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		page = workflowDefinitionResource.getWorkflowDefinitionsPage(
			null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			workflowDefinition1, (List<WorkflowDefinition>)page.getItems());
		assertContains(
			workflowDefinition2, (List<WorkflowDefinition>)page.getItems());
		assertValid(page);

		workflowDefinitionResource.deleteWorkflowDefinition(
			workflowDefinition1.getId());

		workflowDefinitionResource.deleteWorkflowDefinition(
			workflowDefinition2.getId());
	}

	@Test
	public void testGetWorkflowDefinitionsPageWithPagination()
		throws Exception {

		Page<WorkflowDefinition> totalPage =
			workflowDefinitionResource.getWorkflowDefinitionsPage(
				null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		WorkflowDefinition workflowDefinition1 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		WorkflowDefinition workflowDefinition2 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		WorkflowDefinition workflowDefinition3 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		Page<WorkflowDefinition> page1 =
			workflowDefinitionResource.getWorkflowDefinitionsPage(
				null, Pagination.of(1, totalCount + 2), null);

		List<WorkflowDefinition> workflowDefinitions1 =
			(List<WorkflowDefinition>)page1.getItems();

		Assert.assertEquals(
			workflowDefinitions1.toString(), totalCount + 2,
			workflowDefinitions1.size());

		Page<WorkflowDefinition> page2 =
			workflowDefinitionResource.getWorkflowDefinitionsPage(
				null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<WorkflowDefinition> workflowDefinitions2 =
			(List<WorkflowDefinition>)page2.getItems();

		Assert.assertEquals(
			workflowDefinitions2.toString(), 1, workflowDefinitions2.size());

		Page<WorkflowDefinition> page3 =
			workflowDefinitionResource.getWorkflowDefinitionsPage(
				null, Pagination.of(1, totalCount + 3), null);

		assertContains(
			workflowDefinition1, (List<WorkflowDefinition>)page3.getItems());
		assertContains(
			workflowDefinition2, (List<WorkflowDefinition>)page3.getItems());
		assertContains(
			workflowDefinition3, (List<WorkflowDefinition>)page3.getItems());
	}

	@Test
	public void testGetWorkflowDefinitionsPageWithSortDateTime()
		throws Exception {

		testGetWorkflowDefinitionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, workflowDefinition1, workflowDefinition2) -> {
				BeanTestUtil.setProperty(
					workflowDefinition1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetWorkflowDefinitionsPageWithSortDouble()
		throws Exception {

		testGetWorkflowDefinitionsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, workflowDefinition1, workflowDefinition2) -> {
				BeanTestUtil.setProperty(
					workflowDefinition1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					workflowDefinition2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetWorkflowDefinitionsPageWithSortInteger()
		throws Exception {

		testGetWorkflowDefinitionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, workflowDefinition1, workflowDefinition2) -> {
				BeanTestUtil.setProperty(
					workflowDefinition1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					workflowDefinition2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetWorkflowDefinitionsPageWithSortString()
		throws Exception {

		testGetWorkflowDefinitionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, workflowDefinition1, workflowDefinition2) -> {
				Class<?> clazz = workflowDefinition1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						workflowDefinition1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						workflowDefinition2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						workflowDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						workflowDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						workflowDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						workflowDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetWorkflowDefinitionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, WorkflowDefinition, WorkflowDefinition, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		WorkflowDefinition workflowDefinition1 = randomWorkflowDefinition();
		WorkflowDefinition workflowDefinition2 = randomWorkflowDefinition();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, workflowDefinition1, workflowDefinition2);
		}

		workflowDefinition1 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				workflowDefinition1);

		workflowDefinition2 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				workflowDefinition2);

		for (EntityField entityField : entityFields) {
			Page<WorkflowDefinition> ascPage =
				workflowDefinitionResource.getWorkflowDefinitionsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(workflowDefinition1, workflowDefinition2),
				(List<WorkflowDefinition>)ascPage.getItems());

			Page<WorkflowDefinition> descPage =
				workflowDefinitionResource.getWorkflowDefinitionsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(workflowDefinition2, workflowDefinition1),
				(List<WorkflowDefinition>)descPage.getItems());
		}
	}

	protected WorkflowDefinition
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"workflowDefinitions",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject workflowDefinitionsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/workflowDefinitions");

		long totalCount = workflowDefinitionsJSONObject.getLong("totalCount");

		WorkflowDefinition workflowDefinition1 =
			testGraphQLGetWorkflowDefinitionsPage_addWorkflowDefinition();
		WorkflowDefinition workflowDefinition2 =
			testGraphQLGetWorkflowDefinitionsPage_addWorkflowDefinition();

		workflowDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/workflowDefinitions");

		Assert.assertEquals(
			totalCount + 2,
			workflowDefinitionsJSONObject.getLong("totalCount"));

		assertContains(
			workflowDefinition1,
			Arrays.asList(
				WorkflowDefinitionSerDes.toDTOs(
					workflowDefinitionsJSONObject.getString("items"))));
		assertContains(
			workflowDefinition2,
			Arrays.asList(
				WorkflowDefinitionSerDes.toDTOs(
					workflowDefinitionsJSONObject.getString("items"))));
	}

	protected WorkflowDefinition
			testGraphQLGetWorkflowDefinitionsPage_addWorkflowDefinition()
		throws Exception {

		return testGraphQLWorkflowDefinition_addWorkflowDefinition();
	}

	@Test
	public void testPostWorkflowDefinition() throws Exception {
		WorkflowDefinition randomWorkflowDefinition =
			randomWorkflowDefinition();

		WorkflowDefinition postWorkflowDefinition =
			testPostWorkflowDefinition_addWorkflowDefinition(
				randomWorkflowDefinition);

		assertEquals(randomWorkflowDefinition, postWorkflowDefinition);
		assertValid(postWorkflowDefinition);
	}

	protected WorkflowDefinition
			testPostWorkflowDefinition_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowDefinitionByName() throws Exception {
		WorkflowDefinition postWorkflowDefinition =
			testGetWorkflowDefinitionByName_addWorkflowDefinition();

		WorkflowDefinition getWorkflowDefinition =
			workflowDefinitionResource.getWorkflowDefinitionByName(
				postWorkflowDefinition.getName(), null);

		assertEquals(postWorkflowDefinition, getWorkflowDefinition);
		assertValid(getWorkflowDefinition);
	}

	protected WorkflowDefinition
			testGetWorkflowDefinitionByName_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowDefinitionByName() throws Exception {
		WorkflowDefinition workflowDefinition =
			testGraphQLGetWorkflowDefinitionByName_addWorkflowDefinition();

		Assert.assertTrue(
			equals(
				workflowDefinition,
				WorkflowDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"workflowDefinitionByName",
								new HashMap<String, Object>() {
									{
										put(
											"name",
											"\"" +
												workflowDefinition.getName() +
													"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/workflowDefinitionByName"))));
	}

	@Test
	public void testGraphQLGetWorkflowDefinitionByNameNotFound()
		throws Exception {

		String irrelevantName = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"workflowDefinitionByName",
						new HashMap<String, Object>() {
							{
								put("name", irrelevantName);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected WorkflowDefinition
			testGraphQLGetWorkflowDefinitionByName_addWorkflowDefinition()
		throws Exception {

		return testGraphQLWorkflowDefinition_addWorkflowDefinition();
	}

	@Test
	public void testPostWorkflowDefinitionDeploy() throws Exception {
		WorkflowDefinition randomWorkflowDefinition =
			randomWorkflowDefinition();

		WorkflowDefinition postWorkflowDefinition =
			testPostWorkflowDefinitionDeploy_addWorkflowDefinition(
				randomWorkflowDefinition);

		assertEquals(randomWorkflowDefinition, postWorkflowDefinition);
		assertValid(postWorkflowDefinition);
	}

	protected WorkflowDefinition
			testPostWorkflowDefinitionDeploy_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostWorkflowDefinitionSave() throws Exception {
		WorkflowDefinition randomWorkflowDefinition =
			randomWorkflowDefinition();

		WorkflowDefinition postWorkflowDefinition =
			testPostWorkflowDefinitionSave_addWorkflowDefinition(
				randomWorkflowDefinition);

		assertEquals(randomWorkflowDefinition, postWorkflowDefinition);
		assertValid(postWorkflowDefinition);
	}

	protected WorkflowDefinition
			testPostWorkflowDefinitionSave_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteWorkflowDefinitionUndeploy() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WorkflowDefinition workflowDefinition =
			testDeleteWorkflowDefinitionUndeploy_addWorkflowDefinition();

		assertHttpResponseStatusCode(
			204,
			workflowDefinitionResource.
				deleteWorkflowDefinitionUndeployHttpResponse(null, null));
	}

	protected WorkflowDefinition
			testDeleteWorkflowDefinitionUndeploy_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostWorkflowDefinitionUpdateActive() throws Exception {
		WorkflowDefinition randomWorkflowDefinition =
			randomWorkflowDefinition();

		WorkflowDefinition postWorkflowDefinition =
			testPostWorkflowDefinitionUpdateActive_addWorkflowDefinition(
				randomWorkflowDefinition);

		assertEquals(randomWorkflowDefinition, postWorkflowDefinition);
		assertValid(postWorkflowDefinition);
	}

	protected WorkflowDefinition
			testPostWorkflowDefinitionUpdateActive_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteWorkflowDefinition() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WorkflowDefinition workflowDefinition =
			testDeleteWorkflowDefinition_addWorkflowDefinition();

		assertHttpResponseStatusCode(
			204,
			workflowDefinitionResource.deleteWorkflowDefinitionHttpResponse(
				workflowDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			workflowDefinitionResource.getWorkflowDefinitionHttpResponse(
				workflowDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			workflowDefinitionResource.getWorkflowDefinitionHttpResponse(0L));
	}

	protected WorkflowDefinition
			testDeleteWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition =
			testGraphQLDeleteWorkflowDefinition_addWorkflowDefinition();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteWorkflowDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"workflowDefinitionId",
									workflowDefinition.getId());
							}
						})),
				"JSONObject/data", "Object/deleteWorkflowDefinition"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"workflowDefinition",
					new HashMap<String, Object>() {
						{
							put(
								"workflowDefinitionId",
								workflowDefinition.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected WorkflowDefinition
			testGraphQLDeleteWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		return testGraphQLWorkflowDefinition_addWorkflowDefinition();
	}

	@Test
	public void testGetWorkflowDefinition() throws Exception {
		WorkflowDefinition postWorkflowDefinition =
			testGetWorkflowDefinition_addWorkflowDefinition();

		WorkflowDefinition getWorkflowDefinition =
			workflowDefinitionResource.getWorkflowDefinition(
				postWorkflowDefinition.getId());

		assertEquals(postWorkflowDefinition, getWorkflowDefinition);
		assertValid(getWorkflowDefinition);
	}

	protected WorkflowDefinition
			testGetWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition =
			testGraphQLGetWorkflowDefinition_addWorkflowDefinition();

		Assert.assertTrue(
			equals(
				workflowDefinition,
				WorkflowDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"workflowDefinition",
								new HashMap<String, Object>() {
									{
										put(
											"workflowDefinitionId",
											workflowDefinition.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/workflowDefinition"))));
	}

	@Test
	public void testGraphQLGetWorkflowDefinitionNotFound() throws Exception {
		Long irrelevantWorkflowDefinitionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"workflowDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"workflowDefinitionId",
									irrelevantWorkflowDefinitionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected WorkflowDefinition
			testGraphQLGetWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		return testGraphQLWorkflowDefinition_addWorkflowDefinition();
	}

	@Test
	public void testPutWorkflowDefinition() throws Exception {
		WorkflowDefinition postWorkflowDefinition =
			testPutWorkflowDefinition_addWorkflowDefinition();

		WorkflowDefinition randomWorkflowDefinition =
			randomWorkflowDefinition();

		WorkflowDefinition putWorkflowDefinition =
			workflowDefinitionResource.putWorkflowDefinition(
				postWorkflowDefinition.getId(), randomWorkflowDefinition);

		assertEquals(randomWorkflowDefinition, putWorkflowDefinition);
		assertValid(putWorkflowDefinition);

		WorkflowDefinition getWorkflowDefinition =
			workflowDefinitionResource.getWorkflowDefinition(
				putWorkflowDefinition.getId());

		assertEquals(randomWorkflowDefinition, getWorkflowDefinition);
		assertValid(getWorkflowDefinition);
	}

	protected WorkflowDefinition
			testPutWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowDefinition
			testGraphQLWorkflowDefinition_addWorkflowDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		WorkflowDefinition workflowDefinition,
		List<WorkflowDefinition> workflowDefinitions) {

		boolean contains = false;

		for (WorkflowDefinition item : workflowDefinitions) {
			if (equals(workflowDefinition, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			workflowDefinitions + " does not contain " + workflowDefinition,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		Assert.assertTrue(
			workflowDefinition1 + " does not equal " + workflowDefinition2,
			equals(workflowDefinition1, workflowDefinition2));
	}

	protected void assertEquals(
		List<WorkflowDefinition> workflowDefinitions1,
		List<WorkflowDefinition> workflowDefinitions2) {

		Assert.assertEquals(
			workflowDefinitions1.size(), workflowDefinitions2.size());

		for (int i = 0; i < workflowDefinitions1.size(); i++) {
			WorkflowDefinition workflowDefinition1 = workflowDefinitions1.get(
				i);
			WorkflowDefinition workflowDefinition2 = workflowDefinitions2.get(
				i);

			assertEquals(workflowDefinition1, workflowDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowDefinition> workflowDefinitions1,
		List<WorkflowDefinition> workflowDefinitions2) {

		Assert.assertEquals(
			workflowDefinitions1.size(), workflowDefinitions2.size());

		for (WorkflowDefinition workflowDefinition1 : workflowDefinitions1) {
			boolean contains = false;

			for (WorkflowDefinition workflowDefinition2 :
					workflowDefinitions2) {

				if (equals(workflowDefinition1, workflowDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowDefinitions2 + " does not contain " +
					workflowDefinition1,
				contains);
		}
	}

	protected void assertValid(WorkflowDefinition workflowDefinition)
		throws Exception {

		boolean valid = true;

		if (workflowDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (workflowDefinition.getDateModified() == null) {
			valid = false;
		}

		if (workflowDefinition.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (workflowDefinition.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (workflowDefinition.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (workflowDefinition.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (workflowDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("nodes", additionalAssertFieldName)) {
				if (workflowDefinition.getNodes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (workflowDefinition.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (workflowDefinition.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (workflowDefinition.getTransitions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (workflowDefinition.getVersion() == null) {
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

	protected void assertValid(Page<WorkflowDefinition> page) {
		boolean valid = false;

		java.util.Collection<WorkflowDefinition> workflowDefinitions =
			page.getItems();

		int size = workflowDefinitions.size();

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
					com.liferay.headless.admin.workflow.dto.v1_0.
						WorkflowDefinition.class)) {

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
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		if (workflowDefinition1 == workflowDefinition2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getActive(),
						workflowDefinition2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getContent(),
						workflowDefinition2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getDateCreated(),
						workflowDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getDateModified(),
						workflowDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getDescription(),
						workflowDefinition2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getId(),
						workflowDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getName(),
						workflowDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("nodes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getNodes(),
						workflowDefinition2.getNodes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getTitle(),
						workflowDefinition2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)workflowDefinition1.getTitle_i18n(),
						(Map)workflowDefinition2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getTransitions(),
						workflowDefinition2.getTransitions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowDefinition1.getVersion(),
						workflowDefinition2.getVersion())) {

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

		if (!(_workflowDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowDefinitionResource;

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
		WorkflowDefinition workflowDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(workflowDefinition.getContent()));
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
							workflowDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(workflowDefinition.getDateCreated()));
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
							workflowDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(workflowDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(workflowDefinition.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(workflowDefinition.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("nodes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(workflowDefinition.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("transitions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("version")) {
			sb.append("'");
			sb.append(String.valueOf(workflowDefinition.getVersion()));
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

	protected WorkflowDefinition randomWorkflowDefinition() throws Exception {
		return new WorkflowDefinition() {
			{
				active = RandomTestUtil.randomBoolean();
				content = StringUtil.toLowerCase(RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				version = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected WorkflowDefinition randomIrrelevantWorkflowDefinition()
		throws Exception {

		WorkflowDefinition randomIrrelevantWorkflowDefinition =
			randomWorkflowDefinition();

		return randomIrrelevantWorkflowDefinition;
	}

	protected WorkflowDefinition randomPatchWorkflowDefinition()
		throws Exception {

		return randomWorkflowDefinition();
	}

	protected WorkflowDefinitionResource workflowDefinitionResource;
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
		LogFactoryUtil.getLog(BaseWorkflowDefinitionResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.admin.workflow.resource.v1_0.
			WorkflowDefinitionResource _workflowDefinitionResource;

}