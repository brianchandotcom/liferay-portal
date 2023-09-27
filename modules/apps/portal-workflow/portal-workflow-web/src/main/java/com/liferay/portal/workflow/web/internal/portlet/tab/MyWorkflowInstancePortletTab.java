/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.portlet.tab;

import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	property = "portal.workflow.tabs.name=" + WorkflowWebKeys.WORKFLOW_TAB_MY_SUBMISSIONS,
	service = WorkflowPortletTab.class
)
public class MyWorkflowInstancePortletTab extends WorkflowInstancePortletTab {

	@Override
	public String getName() {
		return WorkflowWebKeys.WORKFLOW_TAB_MY_SUBMISSIONS;
	}

}