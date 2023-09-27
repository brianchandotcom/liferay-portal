/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.workflow.definition.link.update.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.definition.link.update.handler.WorkflowDefinitionLinkUpdateHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.LayoutRevision",
	service = WorkflowDefinitionLinkUpdateHandler.class
)
public class LayoutRevisionWorkflowDefinitionLinkUpdateHandler
	implements WorkflowDefinitionLinkUpdateHandler {

	@Override
	public void updatedWorkflowDefinitionLink(String workflowDefinition) {
		if (Validator.isNotNull(workflowDefinition)) {
			return;
		}

		// Workflow definition link was deleted

		for (LayoutRevision layoutRevision :
				_layoutRevisionLocalService.getLayoutRevisionsByStatus(
					WorkflowConstants.STATUS_PENDING)) {

			layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);

			layoutRevision = _layoutRevisionLocalService.updateLayoutRevision(
				layoutRevision);

			try {
				_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
					layoutRevision.getCompanyId(), layoutRevision.getGroupId(),
					layoutRevision.getModelClassName(),
					layoutRevision.getLayoutRevisionId());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to delete workflow instance links for layout " +
							"revision " + layoutRevision.getLayoutRevisionId(),
						portalException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionWorkflowDefinitionLinkUpdateHandler.class);

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}