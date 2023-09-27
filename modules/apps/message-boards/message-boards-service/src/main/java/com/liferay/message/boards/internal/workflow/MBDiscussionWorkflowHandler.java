/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.workflow;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = WorkflowHandler.class
)
public class MBDiscussionWorkflowHandler extends MBMessageWorkflowHandler {

	@Override
	@SuppressWarnings("rawtypes")
	public AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	@Override
	public String getClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public boolean isCommentable() {
		return false;
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK,
			MBMessage model, Map<String, Serializable> workflowContext)
		throws PortalException {

		if (_isWorkflowReviewComment(workflowContext)) {
			updateStatus(WorkflowConstants.STATUS_APPROVED, workflowContext);

			return;
		}

		_workflowInstanceLinkLocalService.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK,
			workflowContext);
	}

	private boolean _isWorkflowReviewComment(
		Map<String, Serializable> workflowContext) {

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		String namespace = GetterUtil.getString(
			serviceContext.getAttribute("namespace"));

		for (String portletId : _PORTLET_IDS) {
			if (namespace.contains(portletId)) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _PORTLET_IDS = {
		"com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet",
		"com_liferay_portal_workflow_web_internal_portlet_UserWorkflowPortlet"
	};

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}