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

package com.liferay.workflow.instance.web.portlet;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.util.PortletKeys;
import com.liferay.workflow.instance.web.configuration.WorkflowInstanceWebConfiguration;

import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Leonardo Barros
 */
@Component(
	configurationPid = "com.liferay.workflow.instance.web.configuration.WorkflowInstanceWebConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-my-workflow-instance",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.friendly-url-mapping=my_workflow_instance",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/my_workflow_instance.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Submissions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_INSTANCE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MyWorkflowInstancePortlet extends WorkflowInstancePortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		this.workflowInstanceWebConfiguration = Configurable.createConfigurable(
			WorkflowInstanceWebConfiguration.class, properties);
	}

}