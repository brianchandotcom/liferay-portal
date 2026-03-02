/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;

import jakarta.portlet.PortletMode;
import jakarta.portlet.WindowState;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Davyson Melo
 */
public class EditAgentDefinitionDisplayContext {

	public EditAgentDefinitionDisplayContext(
		AccountEntryLocalService accountEntryLocalService,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Portal portal) {

		_accountEntryLocalService = accountEntryLocalService;
		_groupLocalService = groupLocalService;
		_httpServletRequest = httpServletRequest;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() throws Exception {
		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			_themeDisplay.getUserId());

		Company company = _themeDisplay.getCompany();

		Group group = _groupLocalService.getGroup(
			_themeDisplay.getScopeGroupId());

		String aiHubURL = StringBundler.concat(
			company.getPortalURL(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			"/web", group.getFriendlyURL());

		return HashMapBuilder.<String, Object>put(
			"accountEntryExternalReferenceCode",
			() -> {
				if (accountEntry == null) {
					return null;
				}

				return accountEntry.getExternalReferenceCode();
			}
		).put(
			"backURL", aiHubURL + "/agents"
		).put(
			"externalReferenceCode",
			_httpServletRequest.getParameter("externalReferenceCode")
		).put(
			"readonly",
			() -> {
				String workflowDefinitionName =
					_httpServletRequest.getParameter("workflowDefinitionName");

				if ((workflowDefinitionName != null) &&
					ArrayUtil.contains(
						WorkflowDefinitionConstants.
							SYSTEM_WORKFLOW_DEFINITION_NAMES,
						workflowDefinitionName)) {

					return true;
				}

				return false;
			}
		).put(
			"workflowDefinitionURL",
			() -> {
				String namespace = _portal.getPortletNamespace(
					WorkflowPortletKeys.KALEO_DESIGNER);

				String url = _addGroupExternalReferenceCodeParameter(
					accountEntry, namespace, aiHubURL + "/workflow-definition");

				return HttpComponentsUtil.addParameters(
					_addNameParameter(namespace, url), "p_p_id",
					WorkflowPortletKeys.KALEO_DESIGNER, "p_p_lifecycle", "0",
					"p_p_state", WindowState.MAXIMIZED.toString(), "p_p_mode",
					PortletMode.VIEW.toString(), namespace + "mvcPath",
					"/designer/edit_workflow_definition.jsp",
					namespace + "redirect",
					_portal.getPortalURL(_httpServletRequest) +
						_portal.getCurrentURL(_httpServletRequest),
					namespace + "clearSessionMessage", true,
					namespace + "scope", WorkflowDefinitionConstants.SCOPE_AI);
			}
		).build();
	}

	private String _addGroupExternalReferenceCodeParameter(
			AccountEntry accountEntry, String namespace, String url)
		throws PortalException {

		if (accountEntry == null) {
			return url;
		}

		Group group = _groupLocalService.getGroup(
			accountEntry.getAccountEntryGroupId());

		return HttpComponentsUtil.addParameter(
			url, namespace + "groupExternalReferenceCode",
			group.getExternalReferenceCode());
	}

	private String _addNameParameter(String namespace, String url) {
		String workflowDefinitionName = _httpServletRequest.getParameter(
			"workflowDefinitionName");

		if (workflowDefinitionName == null) {
			return url;
		}

		return HttpComponentsUtil.addParameter(
			url, namespace + "name", workflowDefinitionName);
	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;

}