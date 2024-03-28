/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.portlet.tab;

/**
 * @author Feliphe Marinho
 */
public interface WorkflowPortletTabRegistry {

	public boolean contains(String portalWorkflowTabsName);

	public WorkflowPortletTab getWorkflowPortletTab(
		String portalWorkflowTabsName);

}