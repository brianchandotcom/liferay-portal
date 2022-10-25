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

package com.liferay.portal.workflow.web.internal.portlet.configuration.icon;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.configuration.WorkflowDefinitionConfiguration;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * Defines the icon triggering duplication of the workflow definition.
 *
 * @author Jeyvison Nascimento
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.configuration.WorkflowDefinitionConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"path=/definition/edit_workflow_definition.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DuplicateDefinitionPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/duplicate_definition.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "duplicate");
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		if (!_checkPermissions()) {
			return false;
		}

		WorkflowDefinition workflowDefinition =
			(WorkflowDefinition)portletRequest.getAttribute(
				WebKeys.WORKFLOW_DEFINITION);

		if (workflowDefinition == null) {
			return false;
		}

		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		WorkflowDefinitionConfiguration workflowDefinitionConfiguration =
			ConfigurableUtil.createConfigurable(
				WorkflowDefinitionConfiguration.class, properties);

		_companyAdministratorCanPublish =
			workflowDefinitionConfiguration.companyAdministratorCanPublish();
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	private boolean _checkPermissions() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((_companyAdministratorCanPublish &&
			 permissionChecker.isCompanyAdmin()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		return false;
	}

	private volatile boolean _companyAdministratorCanPublish;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.workflow.web)"
	)
	private ServletContext _servletContext;

}