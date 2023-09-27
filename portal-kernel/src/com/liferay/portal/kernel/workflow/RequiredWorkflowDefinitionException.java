/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.model.WorkflowDefinitionLink;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class RequiredWorkflowDefinitionException extends WorkflowException {

	public RequiredWorkflowDefinitionException() {
	}

	public RequiredWorkflowDefinitionException(
		List<WorkflowDefinitionLink> workflowDefinitionLinks) {

		_workflowDefinitionLinks = workflowDefinitionLinks;
	}

	public RequiredWorkflowDefinitionException(String msg) {
		super(msg);
	}

	public RequiredWorkflowDefinitionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public RequiredWorkflowDefinitionException(Throwable throwable) {
		super(throwable);
	}

	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks() {
		return _workflowDefinitionLinks;
	}

	private List<WorkflowDefinitionLink> _workflowDefinitionLinks;

}