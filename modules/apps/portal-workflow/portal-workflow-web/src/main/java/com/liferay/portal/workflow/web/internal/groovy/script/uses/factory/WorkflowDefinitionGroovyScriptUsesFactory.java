/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.groovy.script.uses.factory;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.security.script.management.groovy.script.use.GroovyScriptUse;
import com.liferay.portal.security.script.management.groovy.script.uses.factory.GroovyScriptUsesFactory;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTabServiceTracker;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletMode;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = GroovyScriptUsesFactory.class)
public class WorkflowDefinitionGroovyScriptUsesFactory
	implements GroovyScriptUsesFactory {

	@Override
	public List<GroovyScriptUse> create(ResourceRequest resourceRequest)
		throws Exception {

		return TransformUtil.transform(
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			workflowDefinition -> {
				String content = workflowDefinition.getContentAsXML();

				if (!content.contains(
						"<script-language>groovy</script-language>")) {

					return null;
				}

				Company company = _companyLocalService.getCompany(
					workflowDefinition.getCompanyId());

				Locale locale = resourceRequest.getLocale();

				return new GroovyScriptUse(
					company.getWebId(),
					workflowDefinition.getTitle(locale.getLanguage()),
					_getSourceURL(company, workflowDefinition));
			});
	}

	private String _getBaseURL(Company company, String portletId)
		throws Exception {

		String url = StringBundler.concat(
			company.getPortalURL(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
			GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		url = HttpComponentsUtil.addParameter(url, "p_p_id", portletId);
		url = HttpComponentsUtil.addParameter(url, "p_p_lifecycle", "0");
		url = HttpComponentsUtil.addParameter(
			url, "p_p_state", WindowState.MAXIMIZED.toString());

		return HttpComponentsUtil.addParameter(
			url, "p_p_mode", PortletMode.VIEW.toString());
	}

	private String _getSourceURL(
			Company company, WorkflowDefinition workflowDefinition)
		throws Exception {

		if (_workflowPortletTabServiceTracker.contains(
				WorkflowWebKeys.WORKFLOW_TAB_DEFINITION)) {

			String url = _getBaseURL(
				company, WorkflowPortletKeys.KALEO_DESIGNER);

			String namespace = _portal.getPortletNamespace(
				WorkflowPortletKeys.KALEO_DESIGNER);

			url = HttpComponentsUtil.addParameter(
				url, namespace + "clearSessionMessage", true);
			url = HttpComponentsUtil.addParameter(
				url, namespace + "draftVersion",
				workflowDefinition.getVersion() + ".0");
			url = HttpComponentsUtil.addParameter(
				url, namespace + "mvcPath",
				"/designer/edit_workflow_definition.jsp");
			url = HttpComponentsUtil.addParameter(
				url, namespace + "name", workflowDefinition.getName());

			return HttpComponentsUtil.addParameter(
				url, namespace + "redirect",
				_getBaseURL(
					company, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW));
		}

		String url = _getBaseURL(
			company, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW);

		String namespace = _portal.getPortletNamespace(
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW);

		url = HttpComponentsUtil.addParameter(
			url, namespace + "mvcPath",
			"/definition/edit_workflow_definition.jsp");

		return HttpComponentsUtil.addParameter(
			url, namespace + "redirect",
			HttpComponentsUtil.addParameter(
				_getBaseURL(
					company, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW),
				namespace + "mvcPath", "/view.jsp"));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowPortletTabServiceTracker _workflowPortletTabServiceTracker;

}