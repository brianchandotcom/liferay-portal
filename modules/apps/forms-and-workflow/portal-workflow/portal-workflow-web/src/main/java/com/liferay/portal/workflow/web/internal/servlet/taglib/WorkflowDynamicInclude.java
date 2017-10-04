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

package com.liferay.portal.workflow.web.internal.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adam Brandizzi
 */
public interface WorkflowDynamicInclude extends DynamicInclude {

	public void prepareDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException;

	public void prepareProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException;

	public void prepareRender(
			RenderRequest actionRequest, RenderResponse actionResponse)
		throws PortletException;

}