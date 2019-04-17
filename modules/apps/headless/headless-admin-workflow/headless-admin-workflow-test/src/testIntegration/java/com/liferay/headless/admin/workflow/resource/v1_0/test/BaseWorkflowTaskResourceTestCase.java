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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskSerDes;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-admin-workflow/v1.0");
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
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		WorkflowTask workflowTask = randomWorkflowTask();

		String json1 = objectMapper.writeValueAsString(workflowTask);
		String json2 = WorkflowTaskSerDes.toJSON(workflowTask);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetRoleWorkflowTasksPage() throws Exception {
		Long roleId = testGetRoleWorkflowTasksPage_getRoleId();
		Long irrelevantRoleId =
			testGetRoleWorkflowTasksPage_getIrrelevantRoleId();

		if ((irrelevantRoleId != null)) {
			WorkflowTask irrelevantWorkflowTask =
				testGetRoleWorkflowTasksPage_addWorkflowTask(
					irrelevantRoleId, randomIrrelevantWorkflowTask());

			Page<WorkflowTask> page = invokeGetRoleWorkflowTasksPage(
				irrelevantRoleId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWorkflowTask),
				(List<WorkflowTask>)page.getItems());
			assertValid(page);
		}

		WorkflowTask workflowTask1 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		Page<WorkflowTask> page = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetRoleWorkflowTasksPageWithPagination() throws Exception {
		Long roleId = testGetRoleWorkflowTasksPage_getRoleId();

		WorkflowTask workflowTask1 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		Page<WorkflowTask> page1 = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			new ArrayList<WorkflowTask>() {
				{
					addAll(workflowTasks1);
					addAll(workflowTasks2);
				}
			});
	}

	protected WorkflowTask testGetRoleWorkflowTasksPage_addWorkflowTask(
			Long roleId, WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetRoleWorkflowTasksPage_getRoleId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetRoleWorkflowTasksPage_getIrrelevantRoleId()
		throws Exception {

		return null;
	}

	protected Page<WorkflowTask> invokeGetRoleWorkflowTasksPage(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/roles/{roleId}/workflow-tasks", roleId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, WorkflowTaskSerDes::toDTO);
	}

	protected Http.Response invokeGetRoleWorkflowTasksPageResponse(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/roles/{roleId}/workflow-tasks", roleId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetWorkflowTasksAssignedToMePage() throws Exception {
		Assert.assertTrue(true);
	}

	protected Page<WorkflowTask> invokeGetWorkflowTasksAssignedToMePage(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/workflow-tasks/assigned-to-me");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, WorkflowTaskSerDes::toDTO);
	}

	protected Http.Response invokeGetWorkflowTasksAssignedToMePageResponse(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/workflow-tasks/assigned-to-me");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetWorkflowTasksAssignedToMyRolesPage() throws Exception {
		Assert.assertTrue(true);
	}

	protected Page<WorkflowTask> invokeGetWorkflowTasksAssignedToMyRolesPage(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/workflow-tasks/assigned-to-my-roles");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, WorkflowTaskSerDes::toDTO);
	}

	protected Http.Response invokeGetWorkflowTasksAssignedToMyRolesPageResponse(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/workflow-tasks/assigned-to-my-roles");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetWorkflowTask() throws Exception {
		WorkflowTask postWorkflowTask = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask getWorkflowTask = invokeGetWorkflowTask(
			postWorkflowTask.getId());

		assertEquals(postWorkflowTask, getWorkflowTask);
		assertValid(getWorkflowTask);
	}

	protected WorkflowTask testGetWorkflowTask_addWorkflowTask()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokeGetWorkflowTask(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/workflow-tasks/{workflowTaskId}", workflowTaskId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return WorkflowTaskSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetWorkflowTaskResponse(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/workflow-tasks/{workflowTaskId}", workflowTaskId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

	protected WorkflowTask invokePostWorkflowTaskAssignToMe(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/assign-to-me",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return WorkflowTaskSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostWorkflowTaskAssignToMeResponse(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/assign-to-me",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

	protected WorkflowTask invokePostWorkflowTaskAssignToUser(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/assign-to-user",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return WorkflowTaskSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostWorkflowTaskAssignToUserResponse(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/assign-to-user",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

	protected WorkflowTask invokePostWorkflowTaskChangeTransition(
			Long workflowTaskId, ChangeTransition changeTransition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/change-transition",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return WorkflowTaskSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostWorkflowTaskChangeTransitionResponse(
			Long workflowTaskId, ChangeTransition changeTransition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/change-transition",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

	protected WorkflowTask invokePostWorkflowTaskUpdateDueDate(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/update-due-date",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return WorkflowTaskSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostWorkflowTaskUpdateDueDateResponse(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflowTaskId}/update-due-date",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (workflowTask.getCompleted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateCompleted", additionalAssertFieldName)) {
				if (workflowTask.getDateCompleted() == null) {
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

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (workflowTask.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dueDate", additionalAssertFieldName)) {
				if (workflowTask.getDueDate() == null) {
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

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (workflowTask.getTransitions() == null) {
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

		Collection<WorkflowTask> workflowTasks = page.getItems();

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

	protected boolean equals(
		WorkflowTask workflowTask1, WorkflowTask workflowTask2) {

		if (workflowTask1 == workflowTask2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getCompleted(),
						workflowTask2.getCompleted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCompleted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDateCompleted(),
						workflowTask2.getDateCompleted())) {

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

			if (Objects.equals("definitionName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDefinitionName(),
						workflowTask2.getDefinitionName())) {

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

			if (Objects.equals("dueDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getDueDate(),
						workflowTask2.getDueDate())) {

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

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowTask1.getTransitions(),
						workflowTask2.getTransitions())) {

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

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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

		if (entityFieldName.equals("completed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompleted")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowTask.getDateCompleted(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowTask.getDateCompleted(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowTask.getDateCompleted()));
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

		if (entityFieldName.equals("definitionName")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDefinitionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dueDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(workflowTask.getDueDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(workflowTask.getDueDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowTask.getDueDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
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

		if (entityFieldName.equals("transitions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected WorkflowTask randomWorkflowTask() {
		return new WorkflowTask() {
			{
				completed = RandomTestUtil.randomBoolean();
				dateCompleted = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				definitionName = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				dueDate = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected WorkflowTask randomIrrelevantWorkflowTask() {
		WorkflowTask randomIrrelevantWorkflowTask = randomWorkflowTask();

		return randomIrrelevantWorkflowTask;
	}

	protected WorkflowTask randomPatchWorkflowTask() {
		return randomWorkflowTask();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
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
	private WorkflowTaskResource _workflowTaskResource;

	private URL _resourceURL;

}