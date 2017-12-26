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

package com.liferay.portal.kernel.workflow;

import java.util.List;

import com.liferay.portal.kernel.model.WorkflowDefinitionLink;

/**
 * @author Marcellus Tavares
 */
public class RequiredWorkflowDefinitionException extends WorkflowException {

	public RequiredWorkflowDefinitionException() {
	}
	
	public RequiredWorkflowDefinitionException(String msg) {
		super(msg);
	}

	public RequiredWorkflowDefinitionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredWorkflowDefinitionException(Throwable cause) {
		super(cause);
	}
	
	public RequiredWorkflowDefinitionException(List<WorkflowDefinitionLink> workflowDefinitionLinks) {
		this._workflowDefinitionLinks = workflowDefinitionLinks;
	}
	
	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks() {
		return _workflowDefinitionLinks;
	}
	
	private List<WorkflowDefinitionLink> _workflowDefinitionLinks;

}