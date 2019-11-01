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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowTaskResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskSerDes;
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
public abstract class BaseWorkflowTaskResourceTestCase {

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

		_workflowTaskResource.setContextCompany(testCompany);

		WorkflowTaskResource.Builder builder = WorkflowTaskResource.builder();

		workflowTaskResource = builder.locale(
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

		WorkflowTask workflowTask1 = randomWorkflowTask();

		String json = objectMapper.writeValueAsString(workflowTask1);

		WorkflowTask workflowTask2 = WorkflowTaskSerDes.toDTO(json);

		Assert.assertTrue(equals(workflowTask1, workflowTask2));
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

		WorkflowTask workflowTask = randomWorkflowTask();

		String json1 = objectMapper.writeValueAsString(workflowTask);
		String json2 = WorkflowTaskSerDes.toJSON(workflowTask);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowTask workflowTask = randomWorkflowTask();

		workflowTask.setDefinitionName(regex);
		workflowTask.setDefinitionVersion(regex);
		workflowTask.setDescription(regex);
		workflowTask.setName(regex);

		String json = WorkflowTaskSerDes.toJSON(workflowTask);

		Assert.assertFalse(json.contains(regex));

		workflowTask = WorkflowTaskSerDes.toDTO(json);

		Assert.assertEquals(regex, workflowTask.getDefinitionName());
		Assert.assertEquals(regex, workflowTask.getDefinitionVersion());
		Assert.assertEquals(regex, workflowTask.getDescription());
		Assert.assertEquals(regex, workflowTask.getName());
	}

	@Test
	public void testGetWorkflowInstanceWorkflowTasksPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowInstanceId =
			testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId();
		Long irrelevantWorkflowInstanceId =
			testGetWorkflowInstanceWorkflowTasksPage_getIrrelevantWorkflowInstanceId();

		if ((irrelevantWorkflowInstanceId != null)) {
			WorkflowTask irrelevantWorkflowTask =
				testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
					irrelevantWorkflowInstanceId,
					randomIrrelevantWorkflowTask());

			page = workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				irrelevantWorkflowInstanceId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWorkflowTask),
				(List<WorkflowTask>)page.getItems());
			assertValid(page);
		}

		WorkflowTask workflowTask1 =
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				workflowInstanceId, randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				workflowInstanceId, randomWorkflowTask());

		page = workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
			workflowInstanceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowInstanceWorkflowTasksPageWithPagination()
		throws Exception {

		Long workflowInstanceId =
			testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId();

		WorkflowTask workflowTask1 =
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				workflowInstanceId, randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				workflowInstanceId, randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				workflowInstanceId, randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				workflowInstanceId, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				workflowInstanceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				workflowInstanceId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	protected WorkflowTask
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				Long workflowInstanceId, WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowInstanceWorkflowTasksPage_getIrrelevantWorkflowInstanceId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetWorkflowTasksPage() throws Exception {
		Page<WorkflowTask> page = workflowTaskResource.getWorkflowTasksPage(
			null, null, RandomTestUtil.randomString(), null, null,
			RandomTestUtil.nextDate(), RandomTestUtil.nextDate(), null,
			RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 = testGetWorkflowTasksPage_addWorkflowTask(
			randomWorkflowTask());

		WorkflowTask workflowTask2 = testGetWorkflowTasksPage_addWorkflowTask(
			randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksPage(
			null, null, null, null, null, null, null, null, null,
			Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksPageWithPagination() throws Exception {
		WorkflowTask workflowTask1 = testGetWorkflowTasksPage_addWorkflowTask(
			randomWorkflowTask());

		WorkflowTask workflowTask2 = testGetWorkflowTasksPage_addWorkflowTask(
			randomWorkflowTask());

		WorkflowTask workflowTask3 = testGetWorkflowTasksPage_addWorkflowTask(
			randomWorkflowTask());

		Page<WorkflowTask> page1 = workflowTaskResource.getWorkflowTasksPage(
			null, null, null, null, null, null, null, null, null,
			Pagination.of(1, 2), null);

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 = workflowTaskResource.getWorkflowTasksPage(
			null, null, null, null, null, null, null, null, null,
			Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 = workflowTaskResource.getWorkflowTasksPage(
			null, null, null, null, null, null, null, null, null,
			Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Test
	public void testGetWorkflowTasksPageWithSortDateTime() throws Exception {
		testGetWorkflowTasksPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, workflowTask1, workflowTask2) -> {
				BeanUtils.setProperty(
					workflowTask1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetWorkflowTasksPageWithSortInteger() throws Exception {
		testGetWorkflowTasksPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, workflowTask1, workflowTask2) -> {
				BeanUtils.setProperty(workflowTask1, entityField.getName(), 0);
				BeanUtils.setProperty(workflowTask2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetWorkflowTasksPageWithSortString() throws Exception {
		testGetWorkflowTasksPageWithSort(
			EntityField.Type.STRING,
			(entityField, workflowTask1, workflowTask2) -> {
				Class<?> clazz = workflowTask1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						workflowTask1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						workflowTask2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						workflowTask1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						workflowTask2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetWorkflowTasksPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, WorkflowTask, WorkflowTask, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		WorkflowTask workflowTask1 = randomWorkflowTask();
		WorkflowTask workflowTask2 = randomWorkflowTask();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, workflowTask1, workflowTask2);
		}

		workflowTask1 = testGetWorkflowTasksPage_addWorkflowTask(workflowTask1);

		workflowTask2 = testGetWorkflowTasksPage_addWorkflowTask(workflowTask2);

		for (EntityField entityField : entityFields) {
			Page<WorkflowTask> ascPage =
				workflowTaskResource.getWorkflowTasksPage(
					null, null, null, null, null, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(workflowTask1, workflowTask2),
				(List<WorkflowTask>)ascPage.getItems());

			Page<WorkflowTask> descPage =
				workflowTaskResource.getWorkflowTasksPage(
					null, null, null, null, null, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(workflowTask2, workflowTask1),
				(List<WorkflowTask>)descPage.getItems());
		}
	}

	protected WorkflowTask testGetWorkflowTasksPage_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowTasksPage() throws Exception {
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
				"workflowTasks",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject workflowTasksJSONObject = dataJSONObject.getJSONObject(
			"workflowTasks");

		Assert.assertEquals(0, workflowTasksJSONObject.get("totalCount"));

		WorkflowTask workflowTask1 = testGraphQLWorkflowTask_addWorkflowTask();
		WorkflowTask workflowTask2 = testGraphQLWorkflowTask_addWorkflowTask();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		workflowTasksJSONObject = dataJSONObject.getJSONObject("workflowTasks");

		Assert.assertEquals(2, workflowTasksJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(workflowTask1, workflowTask2),
			workflowTasksJSONObject.getJSONArray("items"));
	}

	@Test
	public void testGetWorkflowTasksAssignedToMePage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetWorkflowTasksAssignedToMyRolesPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetWorkflowTasksAssignedToRolePage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksAssignedToRolePage(
			null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksAssignedToRolePageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				null, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	protected WorkflowTask
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowTasksAssignedToUserPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksAssignedToUserPage(
			null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksAssignedToUserPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				null, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	protected WorkflowTask
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowTasksAssignedToUserRolesPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
			null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksAssignedToUserRolesPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				null, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	protected WorkflowTask
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowTasksSubmittingUserPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksSubmittingUserPage(
			null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksSubmittingUserPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				null, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	protected WorkflowTask
			testGetWorkflowTasksSubmittingUserPage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowTask() throws Exception {
		WorkflowTask postWorkflowTask = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask getWorkflowTask = workflowTaskResource.getWorkflowTask(
			postWorkflowTask.getId());

		assertEquals(postWorkflowTask, getWorkflowTask);
		assertValid(getWorkflowTask);
	}

	protected WorkflowTask testGetWorkflowTask_addWorkflowTask()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowTask() throws Exception {
		WorkflowTask workflowTask = testGraphQLWorkflowTask_addWorkflowTask();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"workflowTask",
				new HashMap<String, Object>() {
					{
						put("workflowTaskId", workflowTask.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				workflowTask, dataJSONObject.getJSONObject("workflowTask")));
	}

	@Test
	public void testPostWorkflowTaskAssignToMe() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskAssignToMe_addWorkflowTask(randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskAssignToMe_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostWorkflowTaskAssignToRole() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskAssignToRole_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskAssignToRole_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostWorkflowTaskAssignToUser() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskAssignToUser_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskAssignToUser_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostWorkflowTaskChangeTransition() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskChangeTransition_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskChangeTransition_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkflowTaskHasOtherAssignableUsers() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostWorkflowTaskUpdateDueDate() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskUpdateDueDate_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskUpdateDueDate_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask testGraphQLWorkflowTask_addWorkflowTask()
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
		WorkflowTask workflowTask1, WorkflowTask workflowTask2) {

		Assert.assertTrue(
			workflowTask1 + " does not equal " + workflowTask2,
			equals(workflowTask1, workflowTask2));
	}

	protected void assertEquals(
		List<WorkflowTask> workflowTasks1, List<WorkflowTask> workflowTasks2) {

		Assert.assertEquals(workflowTasks1.size(), workflowTasks2.size());

		for (int i = 0; i < workflowTasks1.size(); i++) {
			WorkflowTask workflowTask1 = workflowTasks1.get(i);
			WorkflowTask workflowTask2 = workflowTasks2.get(i);

			assertEquals(workflowTask1, workflowTask2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowTask> workflowTasks1, List<WorkflowTask> workflowTasks2) {

		Assert.assertEquals(workflowTasks1.size(), workflowTasks2.size());

		for (WorkflowTask workflowTask1 : workflowTasks1) {
			boolean contains = false;

			for (WorkflowTask workflowTask2 : workflowTasks2) {
				if (equals(workflowTask1, workflowTask2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowTasks2 + " does not contain " + workflowTask1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<WorkflowTask> workflowTasks, JSONArray jsonArray) {

		for (WorkflowTask workflowTask : workflowTasks) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(workflowTask, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + workflowTask, contains);
		}
	}

	protected void assertValid(WorkflowTask workflowTask) {
		boolean valid = true;

		if (workflowTask.getDateCreated() == null) {
			valid = false;
		}

		if (workflowTask.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assigneePerson", additionalAssertFieldName)) {
				if (workflowTask.getAssigneePerson() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assigneeRoles", additionalAssertFieldName)) {
				if (workflowTask.getAssigneeRoles() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (workflowTask.getCompleted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (workflowTask.getDateCompletion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateDue", additionalAssertFieldName)) {
				if (workflowTask.getDateDue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("definitionId", additionalAssertFieldName)) {
				if (workflowTask.getDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("definitionName", additionalAssertFieldName)) {
				if (workflowTask.getDefinitionName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"definitionVersion", additionalAssertFieldName)) {

				if (workflowTask.getDefinitionVersion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (workflowTask.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("instanceId", additionalAssertFieldName)) {
				if (workflowTask.getInstanceId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (workflowTask.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("objectReviewed", additionalAssertFieldName)) {
				if (workflowTask.getObjectReviewed() == null) {
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

	protected void assertValid(Page<WorkflowTask> page) {
		boolean valid = false;

		java.util.Collection<WorkflowTask> workflowTasks = page.getItems();

		int size = workflowTasks.size();

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
		WorkflowTask workflowTask1, WorkflowTask workflowTask2) {

		if (workflowTask1 == workflowTask2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assigneePerson", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getAssigneePerson(),
						workflowTask2.getAssigneePerson())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assigneeRoles", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getAssigneeRoles(),
						workflowTask2.getAssigneeRoles())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getCompleted(),
						workflowTask2.getCompleted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDateCompletion(),
						workflowTask2.getDateCompletion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDateCreated(),
						workflowTask2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateDue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDateDue(),
						workflowTask2.getDateDue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("definitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDefinitionId(),
						workflowTask2.getDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("definitionName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDefinitionName(),
						workflowTask2.getDefinitionName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"definitionVersion", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						workflowTask1.getDefinitionVersion(),
						workflowTask2.getDefinitionVersion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDescription(),
						workflowTask2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getId(), workflowTask2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("instanceId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getInstanceId(),
						workflowTask2.getInstanceId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getName(), workflowTask2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("objectReviewed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getObjectReviewed(),
						workflowTask2.getObjectReviewed())) {

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
		WorkflowTask workflowTask, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("completed", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getCompleted(),
						jsonObject.getBoolean("completed"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("definitionId", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getDefinitionId(),
						jsonObject.getLong("definitionId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("definitionName", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getDefinitionName(),
						jsonObject.getString("definitionName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("definitionVersion", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getDefinitionVersion(),
						jsonObject.getString("definitionVersion"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("instanceId", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getInstanceId(),
						jsonObject.getLong("instanceId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						workflowTask.getName(), jsonObject.getString("name"))) {

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

		if (!(_workflowTaskResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowTaskResource;

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
		EntityField entityField, String operator, WorkflowTask workflowTask) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assigneePerson")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assigneeRoles")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("completed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompletion")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowTask.getDateCompletion(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowTask.getDateCompletion(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowTask.getDateCompletion()));
			}

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
							workflowTask.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowTask.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowTask.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateDue")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(workflowTask.getDateDue(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(workflowTask.getDateDue(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowTask.getDateDue()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("definitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("definitionName")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDefinitionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("definitionVersion")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDefinitionVersion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("instanceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("objectReviewed")) {
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

	protected WorkflowTask randomWorkflowTask() throws Exception {
		return new WorkflowTask() {
			{
				completed = RandomTestUtil.randomBoolean();
				dateCompletion = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				dateDue = RandomTestUtil.nextDate();
				definitionId = RandomTestUtil.randomLong();
				definitionName = RandomTestUtil.randomString();
				definitionVersion = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				instanceId = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected WorkflowTask randomIrrelevantWorkflowTask() throws Exception {
		WorkflowTask randomIrrelevantWorkflowTask = randomWorkflowTask();

		return randomIrrelevantWorkflowTask;
	}

	protected WorkflowTask randomPatchWorkflowTask() throws Exception {
		return randomWorkflowTask();
	}

	protected WorkflowTaskResource workflowTaskResource;
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
		BaseWorkflowTaskResourceTestCase.class);

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
		com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource
			_workflowTaskResource;

}