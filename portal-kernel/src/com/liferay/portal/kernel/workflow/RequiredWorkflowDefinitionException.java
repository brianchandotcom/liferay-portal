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

	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks() {
		return _workflowDefinitionLinks;
	}

	public static class MustNotDeactivateSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeactivateSystemWorkflowDefinition(
			long kaleoDefinitionId) {

			super(
				String.format(
					"Cannot deactivate system workflow definition %s",
					kaleoDefinitionId));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class
		MustNotDeactivateWorkflowDefinitionWithWorkflowDefinitionLinks
			extends RequiredWorkflowDefinitionException {

		public MustNotDeactivateWorkflowDefinitionWithWorkflowDefinitionLinks(
			String kaleoDefinitionName,
			List<WorkflowDefinitionLink> workflowDefinitionLinks) {

			super(
				String.format(
					"Workflow definition %s cannot be deactivated because it " +
						"is referenced by one or more workflow definition " +
							"links",
					kaleoDefinitionName));

			_workflowDefinitionLinks = workflowDefinitionLinks;
		}

	}

	public static class MustNotDeleteActiveWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeleteActiveWorkflowDefinition(long kaleoDefinitionId) {
			super(
				String.format(
					"Cannot delete active workflow definition %s",
					kaleoDefinitionId));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class MustNotDeleteSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotDeleteSystemWorkflowDefinition(long kaleoDefinitionId) {
			super(
				String.format(
					"Cannot delete system workflow definition %s",
					kaleoDefinitionId));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	public static class
		MustNotDeleteWorkflowDefinitionWithWorkflowDefinitionLinks
			extends RequiredWorkflowDefinitionException {

		public MustNotDeleteWorkflowDefinitionWithWorkflowDefinitionLinks(
			long kaleoDefinitionId,
			List<WorkflowDefinitionLink> workflowDefinitionLinks) {

			super(
				String.format(
					"Workflow definition %s cannot be deleted because it is " +
						"referenced by one or more workflow definition links",
					kaleoDefinitionId));

			_workflowDefinitionLinks = workflowDefinitionLinks;
		}

	}

	public static class MustNotUpdateSystemWorkflowDefinition
		extends RequiredWorkflowDefinitionException {

		public MustNotUpdateSystemWorkflowDefinition(long kaleoDefinitionId) {
			super(
				String.format(
					"Cannot update system workflow definition %s",
					kaleoDefinitionId));

			this.kaleoDefinitionId = kaleoDefinitionId;
		}

		public long kaleoDefinitionId;

	}

	private RequiredWorkflowDefinitionException(String msg) {
		super(msg);
	}

	private static List<WorkflowDefinitionLink> _workflowDefinitionLinks;

}