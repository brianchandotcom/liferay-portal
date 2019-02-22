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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.internal.dto.v1_0.ObjectReviewedImpl;
import com.liferay.headless.workflow.internal.dto.v1_0.WorkflowLogImpl;
import com.liferay.headless.workflow.internal.dto.v1_0.WorkflowTaskImpl;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWorkflowTaskResourceTestCase {

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
	public void testGetRoleWorkflowTasksPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetWorkflowTask() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetWorkflowTasksPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostWorkflowTaskAssignToMe() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostWorkflowTaskAssignToUser() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostWorkflowTaskChangeTransition() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostWorkflowTaskUpdateDueDate() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<WorkflowTask> invokeGetRoleWorkflowTasksPage(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/roles/{role-id}/workflow-tasks", roleId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetRoleWorkflowTasksPageResponse(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/roles/{role-id}/workflow-tasks", roleId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask invokeGetWorkflowTask(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/workflow-tasks/{workflow-task-id}", workflowTaskId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTaskImpl.class);
	}

	protected Http.Response invokeGetWorkflowTaskResponse(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/workflow-tasks/{workflow-task-id}", workflowTaskId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<WorkflowTask> invokeGetWorkflowTasksPage(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/workflow-tasks", pagination));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetWorkflowTasksPageResponse(
			Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/workflow-tasks", pagination));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask invokePostWorkflowTaskAssignToMe(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-me",
					workflowTaskId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTaskImpl.class);
	}

	protected Http.Response invokePostWorkflowTaskAssignToMeResponse(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-me",
					workflowTaskId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask invokePostWorkflowTaskAssignToUser(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-user",
					workflowTaskId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTaskImpl.class);
	}

	protected Http.Response invokePostWorkflowTaskAssignToUserResponse(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-user",
					workflowTaskId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask invokePostWorkflowTaskChangeTransition(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/change-transition",
					workflowTaskId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTaskImpl.class);
	}

	protected Http.Response invokePostWorkflowTaskChangeTransitionResponse(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/change-transition",
					workflowTaskId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask invokePostWorkflowTaskUpdateDueDate(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/update-due-date",
					workflowTaskId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTaskImpl.class);
	}

	protected Http.Response invokePostWorkflowTaskUpdateDueDateResponse(
			Long workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(workflowTask),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/update-due-date",
					workflowTaskId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected WorkflowTask randomWorkflowTask() {
		return new WorkflowTaskImpl() {
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

	protected Group testGroup;

	protected static class WorkflowTaskImpl implements WorkflowTask {

		public Boolean getCompleted() {
			return completed;
		}

		public Date getDateCompleted() {
			return dateCompleted;
		}

		public Date getDateCreated() {
			return dateCreated;
		}

		public String getDefinitionName() {
			return definitionName;
		}

		public String getDescription() {
			return description;
		}

		public Date getDueDate() {
			return dueDate;
		}

		public Long getId() {
			return id;
		}

		public WorkflowLog[] getLogs() {
			return logs;
		}

		public Long[] getLogsIds() {
			return logsIds;
		}

		public String getName() {
			return name;
		}

		public ObjectReviewed getObjectReviewed() {
			return objectReviewed;
		}

		public String[] getTransitions() {
			return transitions;
		}

		public void setCompleted(Boolean completed) {
			this.completed = (Boolean)completed;
		}

		@JsonIgnore
		public void setCompleted(
			UnsafeSupplier<Boolean, Throwable> completedUnsafeSupplier) {

			try {
				completed = (Boolean)completedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateCompleted(Date dateCompleted) {
			this.dateCompleted = (Date)dateCompleted;
		}

		@JsonIgnore
		public void setDateCompleted(
			UnsafeSupplier<Date, Throwable> dateCompletedUnsafeSupplier) {

			try {
				dateCompleted = (Date)dateCompletedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateCreated(Date dateCreated) {
			this.dateCreated = (Date)dateCreated;
		}

		@JsonIgnore
		public void setDateCreated(
			UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

			try {
				dateCreated = (Date)dateCreatedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDefinitionName(String definitionName) {
			this.definitionName = (String)definitionName;
		}

		@JsonIgnore
		public void setDefinitionName(
			UnsafeSupplier<String, Throwable> definitionNameUnsafeSupplier) {

			try {
				definitionName = (String)definitionNameUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDescription(String description) {
			this.description = (String)description;
		}

		@JsonIgnore
		public void setDescription(
			UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

			try {
				description = (String)descriptionUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDueDate(Date dueDate) {
			this.dueDate = (Date)dueDate;
		}

		@JsonIgnore
		public void setDueDate(
			UnsafeSupplier<Date, Throwable> dueDateUnsafeSupplier) {

			try {
				dueDate = (Date)dueDateUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setId(Long id) {
			this.id = (Long)id;
		}

		@JsonIgnore
		public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
			try {
				id = (Long)idUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonIgnore
		public void setLogs(
			UnsafeSupplier<WorkflowLog[], Throwable> logsUnsafeSupplier) {

			try {
				logs = (WorkflowLogImpl[])logsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setLogs(WorkflowLog[] logs) {
			this.logs = (WorkflowLogImpl[])logs;
		}

		public void setLogsIds(Long[] logsIds) {
			this.logsIds = (Long[])logsIds;
		}

		@JsonIgnore
		public void setLogsIds(
			UnsafeSupplier<Long[], Throwable> logsIdsUnsafeSupplier) {

			try {
				logsIds = (Long[])logsIdsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setName(String name) {
			this.name = (String)name;
		}

		@JsonIgnore
		public void setName(
			UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

			try {
				name = (String)nameUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setObjectReviewed(ObjectReviewed objectReviewed) {
			this.objectReviewed = (ObjectReviewedImpl)objectReviewed;
		}

		@JsonIgnore
		public void setObjectReviewed(
			UnsafeSupplier<ObjectReviewed, Throwable>
				objectReviewedUnsafeSupplier) {

			try {
				objectReviewed =
					(ObjectReviewedImpl)objectReviewedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setTransitions(String[] transitions) {
			this.transitions = (String[])transitions;
		}

		@JsonIgnore
		public void setTransitions(
			UnsafeSupplier<String[], Throwable> transitionsUnsafeSupplier) {

			try {
				transitions = (String[])transitionsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonProperty
		protected Boolean completed;

		@JsonProperty
		protected Date dateCompleted;

		@JsonProperty
		protected Date dateCreated;

		@JsonProperty
		protected String definitionName;

		@JsonProperty
		protected String description;

		@JsonProperty
		protected Date dueDate;

		@JsonProperty
		protected Long id;

		@JsonProperty
		protected WorkflowLogImpl[] logs;

		@JsonProperty
		protected Long[] logsIds;

		@JsonProperty
		protected String name;

		@JsonProperty
		protected ObjectReviewedImpl objectReviewed;

		@JsonProperty
		protected String[] transitions;

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

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}