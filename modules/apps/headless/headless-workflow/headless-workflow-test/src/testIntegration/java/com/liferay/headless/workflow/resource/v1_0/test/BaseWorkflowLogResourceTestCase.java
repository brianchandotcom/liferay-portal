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

package com.liferay.headless.workflow.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public abstract class BaseWorkflowLogResourceTestCase {

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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-workflow/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetWorkflowLog() throws Exception {
		WorkflowLog postWorkflowLog = testGetWorkflowLog_addWorkflowLog();

		WorkflowLog getWorkflowLog = invokeGetWorkflowLog(
			postWorkflowLog.getId());

		assertEquals(postWorkflowLog, getWorkflowLog);
		assertValid(getWorkflowLog);
	}

	@Test
	public void testGetWorkflowTaskWorkflowLogsPage() throws Exception {
		Long workflowTaskId =
			testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId();

		WorkflowLog workflowLog1 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());
		WorkflowLog workflowLog2 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		Page<WorkflowLog> page = invokeGetWorkflowTaskWorkflowLogsPage(
			workflowTaskId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowLog1, workflowLog2),
			(List<WorkflowLog>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTaskWorkflowLogsPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId();

		WorkflowLog workflowLog1 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());
		WorkflowLog workflowLog2 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());
		WorkflowLog workflowLog3 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		Page<WorkflowLog> page1 = invokeGetWorkflowTaskWorkflowLogsPage(
			workflowTaskId, Pagination.of(2, 1));

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 2, workflowLogs1.size());

		Page<WorkflowLog> page2 = invokeGetWorkflowTaskWorkflowLogsPage(
			workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page2.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowLog1, workflowLog2, workflowLog3),
			new ArrayList<WorkflowLog>() {
				{
					addAll(workflowLogs1);
					addAll(workflowLogs2);
				}
			});
	}

	protected void assertEquals(
		List<WorkflowLog> workflowLogs1, List<WorkflowLog> workflowLogs2) {

		Assert.assertEquals(workflowLogs1.size(), workflowLogs2.size());

		for (int i = 0; i < workflowLogs1.size(); i++) {
			WorkflowLog workflowLog1 = workflowLogs1.get(i);
			WorkflowLog workflowLog2 = workflowLogs2.get(i);

			assertEquals(workflowLog1, workflowLog2);
		}
	}

	protected void assertEquals(
		WorkflowLog workflowLog1, WorkflowLog workflowLog2) {

		Assert.assertTrue(
			workflowLog1 + " does not equal " + workflowLog2,
			equals(workflowLog1, workflowLog2));
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowLog> workflowLogs1, List<WorkflowLog> workflowLogs2) {

		Assert.assertEquals(workflowLogs1.size(), workflowLogs2.size());

		for (WorkflowLog workflowLog1 : workflowLogs1) {
			boolean contains = false;

			for (WorkflowLog workflowLog2 : workflowLogs2) {
				if (equals(workflowLog1, workflowLog2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowLogs2 + " does not contain " + workflowLog1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<WorkflowLog> page) {
		boolean valid = false;

		Collection<WorkflowLog> workflowLogs = page.getItems();

		int size = workflowLogs.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(WorkflowLog workflowLog) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		WorkflowLog workflowLog1, WorkflowLog workflowLog2) {

		if (workflowLog1 == workflowLog2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_workflowLogResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowLogResource;

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
		EntityField entityField, String operator, WorkflowLog workflowLog) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("auditPerson")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getAuditPerson()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("commentLog")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getCommentLog()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(workflowLog.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("person")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getPerson()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("previousPerson")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getPreviousPerson()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("previousState")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getPreviousState()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("state")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getState()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("task")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected WorkflowLog invokeGetWorkflowLog(Long workflowLogId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/workflow-logs/{workflow-log-id}", workflowLogId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowLog.class);
	}

	protected Http.Response invokeGetWorkflowLogResponse(Long workflowLogId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/workflow-logs/{workflow-log-id}", workflowLogId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<WorkflowLog> invokeGetWorkflowTaskWorkflowLogsPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/workflow-logs",
					workflowTaskId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<WorkflowLog>>() {
			});
	}

	protected Http.Response invokeGetWorkflowTaskWorkflowLogsPageResponse(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/workflow-logs",
					workflowTaskId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowLog randomWorkflowLog() {
		return new WorkflowLog() {
			{
				auditPerson = RandomTestUtil.randomString();
				commentLog = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				person = RandomTestUtil.randomString();
				previousPerson = RandomTestUtil.randomString();
				previousState = RandomTestUtil.randomString();
				state = RandomTestUtil.randomString();
				taskId = RandomTestUtil.randomLong();
				type = RandomTestUtil.randomString();
			}
		};
	}

	protected WorkflowLog testGetWorkflowLog_addWorkflowLog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowLog testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
			Long workflowTaskId, WorkflowLog workflowLog)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

	@Inject
	private WorkflowLogResource _workflowLogResource;

}