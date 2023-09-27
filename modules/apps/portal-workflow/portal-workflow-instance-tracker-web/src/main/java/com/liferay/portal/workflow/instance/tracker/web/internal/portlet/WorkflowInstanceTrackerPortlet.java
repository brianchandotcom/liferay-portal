/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.instance.tracker.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.workflow.instance.tracker.web.internal.constants.WorkflowInstanceTrackerPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"javax.portlet.display-name=Workflow Instance Tracker",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + WorkflowInstanceTrackerPortletKeys.WORKFLOW_INSTANCE_TRACKER,
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class WorkflowInstanceTrackerPortlet extends MVCPortlet {
}