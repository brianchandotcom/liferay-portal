/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.constants;

/**
 * Provides the portlet IDs for the available Kaleo Designer portlets.
 *
 * @author Marcellus Tavares
 */
public class KaleoDesignerPortletKeys {

	/**
	 * {@value #CONTROL_PANEL_WORKFLOW} is the portlet ID for the Control Panel
	 * Workflow portlet.
	 */
	public static final String CONTROL_PANEL_WORKFLOW =
		"com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet";

	public static final String CONTROL_PANEL_WORKFLOW_INSTANCE =
		"com_liferay_portal_workflow_web_internal_portlet_" +
			"ControlPanelWorkflowInstancePortlet";

	/**
	 * {@value #KALEO_DESIGNER} is the portlet ID for the Kaleo Designer
	 * portlet.
	 */
	public static final String KALEO_DESIGNER =
		"com_liferay_portal_workflow_kaleo_designer_web_portlet_" +
			"KaleoDesignerPortlet";

}