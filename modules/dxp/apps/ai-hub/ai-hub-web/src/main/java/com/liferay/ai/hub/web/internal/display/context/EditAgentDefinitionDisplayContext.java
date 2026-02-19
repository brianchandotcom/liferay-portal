/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;
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
		HttpServletRequest httpServletRequest, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() throws Exception {
		Company company = _themeDisplay.getCompany();

		String portalURL = company.getPortalURL(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		return HashMapBuilder.<String, Object>put(
			"backURL", portalURL + "/web/ai-hub/agents"
		).put(
			"externalReferenceCode",
			_httpServletRequest.getParameter("externalReferenceCode")
		).put(
			"workflowDefinitionURL",
			() -> {
				String namespace = _portal.getPortletNamespace(
					WorkflowPortletKeys.KALEO_DESIGNER);

				String url = StringBundler.concat(
					portalURL,
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
					GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
					PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

				String workflowDefinitionName =
					_httpServletRequest.getParameter("workflowDefinitionName");

				if (workflowDefinitionName != null) {
					url = HttpComponentsUtil.addParameter(
						url, namespace + "name", workflowDefinitionName);
				}

				return HttpComponentsUtil.addParameters(
					url, "p_p_id", WorkflowPortletKeys.KALEO_DESIGNER,
					"p_p_lifecycle", "0", "p_p_state",
					WindowState.MAXIMIZED.toString(), "p_p_mode",
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

	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;

}