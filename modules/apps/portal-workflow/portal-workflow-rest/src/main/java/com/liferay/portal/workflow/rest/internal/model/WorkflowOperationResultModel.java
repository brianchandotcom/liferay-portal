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

package com.liferay.portal.workflow.rest.internal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowOperationResultModel {

	public static final String STATUS_ERROR = "error";

	public static final String STATUS_SUCCESS = "success";

	public WorkflowOperationResultModel() {
		_status = null;
		_message = null;
		_workflowTaskModel = null;
	}

	public WorkflowOperationResultModel(String status) {
		this(status, null, null);
	}

	public WorkflowOperationResultModel(String status, String message) {
		this(status, message, null);
	}

	public WorkflowOperationResultModel(
		String status, String message, WorkflowTaskModel workflowTaskModel) {

		_status = status;
		_message = message;
		_workflowTaskModel = workflowTaskModel;
	}

	public WorkflowOperationResultModel(
		String status, WorkflowTaskModel workflowTaskModel) {

		this(status, null, workflowTaskModel);
	}

	@XmlElement
	public String getMessage() {
		return _message;
	}

	@XmlElement
	public String getStatus() {
		return _status;
	}

	@XmlElement(name = "task")
	public WorkflowTaskModel getWorkflowTaskModel() {
		return _workflowTaskModel;
	}

	private final String _message;
	private final String _status;
	private final WorkflowTaskModel _workflowTaskModel;

}