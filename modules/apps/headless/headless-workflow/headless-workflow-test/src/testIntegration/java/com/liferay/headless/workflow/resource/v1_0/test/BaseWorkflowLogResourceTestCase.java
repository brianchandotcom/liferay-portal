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

import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWorkflowLogResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
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
			Assert.assertTrue(true);
	}
	@Test
	public void testGetWorkflowTaskWorkflowLogsPage() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetWorkflowLog(
				Long workflowLogId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/workflow-logs/{workflow-log-id}",
					workflowLogId
				);
	}
	protected Response invokeGetWorkflowTaskWorkflowLogsPage(
				Long workflowTaskId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/workflow-tasks/{workflow-task-id}/workflow-logs",
					workflowTaskId
				);
	}

	protected WorkflowLog randomWorkflowLog() {
		return new WorkflowLogImpl() {
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

	protected Group testGroup;

	protected class WorkflowLogImpl implements WorkflowLog {

	public String getAuditPerson() {
				return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
				this.auditPerson = auditPerson;
	}

	@JsonIgnore
	public void setAuditPerson(
				UnsafeSupplier<String, Throwable> auditPersonUnsafeSupplier) {

				try {
					auditPerson = auditPersonUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String auditPerson;
	public String getCommentLog() {
				return commentLog;
	}

	public void setCommentLog(String commentLog) {
				this.commentLog = commentLog;
	}

	@JsonIgnore
	public void setCommentLog(
				UnsafeSupplier<String, Throwable> commentLogUnsafeSupplier) {

				try {
					commentLog = commentLogUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String commentLog;
	public Date getDateCreated() {
				return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
				this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
				UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

				try {
					dateCreated = dateCreatedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date dateCreated;
	public Long getId() {
				return id;
	}

	public void setId(Long id) {
				this.id = id;
	}

	@JsonIgnore
	public void setId(
				UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {

				try {
					id = idUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long id;
	public String getPerson() {
				return person;
	}

	public void setPerson(String person) {
				this.person = person;
	}

	@JsonIgnore
	public void setPerson(
				UnsafeSupplier<String, Throwable> personUnsafeSupplier) {

				try {
					person = personUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String person;
	public String getPreviousPerson() {
				return previousPerson;
	}

	public void setPreviousPerson(String previousPerson) {
				this.previousPerson = previousPerson;
	}

	@JsonIgnore
	public void setPreviousPerson(
				UnsafeSupplier<String, Throwable> previousPersonUnsafeSupplier) {

				try {
					previousPerson = previousPersonUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String previousPerson;
	public String getPreviousState() {
				return previousState;
	}

	public void setPreviousState(String previousState) {
				this.previousState = previousState;
	}

	@JsonIgnore
	public void setPreviousState(
				UnsafeSupplier<String, Throwable> previousStateUnsafeSupplier) {

				try {
					previousState = previousStateUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String previousState;
	public String getState() {
				return state;
	}

	public void setState(String state) {
				this.state = state;
	}

	@JsonIgnore
	public void setState(
				UnsafeSupplier<String, Throwable> stateUnsafeSupplier) {

				try {
					state = stateUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String state;
	public WorkflowTask getTask() {
				return task;
	}

	public void setTask(WorkflowTask task) {
				this.task = task;
	}

	@JsonIgnore
	public void setTask(
				UnsafeSupplier<WorkflowTask, Throwable> taskUnsafeSupplier) {

				try {
					task = taskUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected WorkflowTask task;
	public Long getTaskId() {
				return taskId;
	}

	public void setTaskId(Long taskId) {
				this.taskId = taskId;
	}

	@JsonIgnore
	public void setTaskId(
				UnsafeSupplier<Long, Throwable> taskIdUnsafeSupplier) {

				try {
					taskId = taskIdUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long taskId;
	public String getType() {
				return type;
	}

	public void setType(String type) {
				this.type = type;
	}

	@JsonIgnore
	public void setType(
				UnsafeSupplier<String, Throwable> typeUnsafeSupplier) {

				try {
					type = typeUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String type;

	}

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}