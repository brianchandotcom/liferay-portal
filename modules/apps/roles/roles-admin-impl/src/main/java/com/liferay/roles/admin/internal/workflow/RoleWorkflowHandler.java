/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Role",
	service = WorkflowHandler.class
)
public class RoleWorkflowHandler extends BaseWorkflowHandler<Role> {

	@Override
	public String getClassName() {
		return Role.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return _language.get(locale, "role");
	}

	@Override
	public boolean isScopeable() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public Role updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		return _roleLocalService.updateStatus(
			userId, classPK, status, serviceContext, workflowContext);
	}

	@Reference
	private Language _language;

	@Reference
	private RoleLocalService _roleLocalService;

}