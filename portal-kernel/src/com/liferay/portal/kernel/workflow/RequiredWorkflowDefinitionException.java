/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class RequiredWorkflowDefinitionException extends WorkflowException {

	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks() {
		return _workflowDefinitionLinks;
	}

	public static class MustNotDeactivateSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeactivateSystemWorkflowDefinition(
			long kaleoDefinitionId) {

			super(
				StringBundler.concat(
					"Workflow definition ", kaleoDefinitionId, " cannot be ",
					"deactivated because it is a system workflow definition"));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class
		MustNotDeactivateWorkflowDefinitionWithWorkflowDefinitionLinks
			extends RequiredWorkflowDefinitionException {

		public MustNotDeactivateWorkflowDefinitionWithWorkflowDefinitionLinks(
			List<WorkflowDefinitionLink> workflowDefinitionLinks) {

			super(workflowDefinitionLinks);
		}

	}

	public static class MustNotDeleteActiveWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeleteActiveWorkflowDefinition(long kaleoDefinitionId) {
			super(
				StringBundler.concat(
					"Workflow definition ", kaleoDefinitionId, " cannot be ",
					"deleted because it is active"));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class MustNotDeleteSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeleteSystemWorkflowDefinition(long kaleoDefinitionId) {
			super(
				StringBundler.concat(
					"Workflow definition ", kaleoDefinitionId, " cannot be ",
					"deleted because it is a system workflow definition"));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class
		MustNotDeleteWorkflowDefinitionWithWorkflowDefinitionLinks
			extends RequiredWorkflowDefinitionException {

		public MustNotDeleteWorkflowDefinitionWithWorkflowDefinitionLinks(
			List<WorkflowDefinitionLink> workflowDefinitionLinks) {

			super(workflowDefinitionLinks);
		}

	}

	public static class MustNotUpdateSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotUpdateSystemWorkflowDefinition(long kaleoDefinitionId) {
			super(
				StringBundler.concat(
					"Workflow definition ", kaleoDefinitionId, " cannot be ",
					"updated because it is a system workflow definition"));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	private RequiredWorkflowDefinitionException(
		List<WorkflowDefinitionLink> workflowDefinitionLinks) {

		_workflowDefinitionLinks = workflowDefinitionLinks;
	}

	private RequiredWorkflowDefinitionException(String msg) {
		super(msg);
	}

	private List<WorkflowDefinitionLink> _workflowDefinitionLinks;

}