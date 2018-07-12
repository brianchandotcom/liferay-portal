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

package com.liferay.portal.workflow.portlet.tab;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseWorkflowPortletTab
	extends BaseJSPDynamicInclude implements WorkflowPortletTab {

	@Override
	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletURL searchURL = renderResponse.createRenderURL();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		searchURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		searchURL.setParameter("mvcPath", "/view.jsp");
		searchURL.setParameter("tab", getName());

		return searchURL;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void prepareDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {
	}

	@Override
	public void prepareProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {
	}

	@Override
	public void prepareRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {
	}

	@Override
	protected Log getLog() {
		Class<? extends BaseWorkflowPortletTab> clazz = getClass();

		if (!_logs.containsKey(clazz)) {
			_logs.put(clazz, LogFactoryUtil.getLog(clazz));
		}

		return _logs.get(clazz);
	}

	@Override
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
		super.setServletContext(servletContext);
	}

	private static final Map<Class<? extends BaseWorkflowPortletTab>, Log>
		_logs = new HashMap<>();

	private ServletContext _servletContext;

}