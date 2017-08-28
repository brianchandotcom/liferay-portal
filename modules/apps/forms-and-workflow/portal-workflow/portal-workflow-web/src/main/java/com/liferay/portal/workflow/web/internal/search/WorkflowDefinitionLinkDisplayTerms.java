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

package com.liferay.portal.workflow.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkDisplayTerms extends DisplayTerms {

	public static final String RESOURCE = "resource";

	public static final String WORKFLOW = "workflow";

	public WorkflowDefinitionLinkDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		resource = ParamUtil.getString(portletRequest, RESOURCE);
		workflow = ParamUtil.getString(portletRequest, WORKFLOW);
	}

	public String getResource() {
		return resource;
	}

	public String getWorkflow() {
		return workflow;
	}

	protected String resource;
	protected String workflow;

}