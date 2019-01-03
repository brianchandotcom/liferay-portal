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

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
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

		List<LayoutRevision> pendingLayoutRevisions =
			_layoutRevisionLocalService.getLayoutRevisionsByStatus(
				WorkflowConstants.STATUS_PENDING);

		Stream<LayoutRevision> stream = pendingLayoutRevisions.stream();

		stream.forEach(
			layoutRevision -> {
				layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);

				_layoutRevisionLocalService.updateLayoutRevision(
					layoutRevision);

				try {
					_workflowInstanceLinkLocalService.
						deleteWorkflowInstanceLinks(
							layoutRevision.getCompanyId(),
							layoutRevision.getGroupId(),
							layoutRevision.getModelClassName(),
							layoutRevision.getLayoutRevisionId());
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to delete workflow instance links for " +
								"layout revision " +
									layoutRevision.getLayoutRevisionId(),
							pe);
					}
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionWorkflowDefinitionLinkUpdateHandler.class);

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}