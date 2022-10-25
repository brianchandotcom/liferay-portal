/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.configuration.icon;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.configuration.WorkflowDefinitionConfiguration;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

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
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"path=/designer/edit_kaleo_definition_version.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DuplicateDefinitionPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/designer/configuration/icon/duplicate_definition.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "duplicate");
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		if (!_canPublishWorkflowDefinition()) {
			return false;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(portletRequest);

		if (kaleoDefinitionVersion == null) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return KaleoDesignerPermission.contains(
			themeDisplay.getPermissionChecker(),
			themeDisplay.getCompanyGroupId(),
			KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
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

	protected KaleoDefinitionVersion getKaleoDefinitionVersion(
		PortletRequest portletRequest) {

		return (KaleoDefinitionVersion)portletRequest.getAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	private boolean _canPublishWorkflowDefinition() {
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
		target = "(osgi.web.symbolicname=com.liferay.portal.workflow.kaleo.designer.web)"
	)
	private ServletContext _servletContext;

}