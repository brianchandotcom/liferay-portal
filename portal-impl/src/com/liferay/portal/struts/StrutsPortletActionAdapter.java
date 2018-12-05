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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Mika Koivisto
 */
public class StrutsPortletActionAdapter extends BaseStrutsPortletAction {

	public StrutsPortletActionAdapter(
		PortletAction portletAction, ActionMapping actionMapping) {

		_portletAction = portletAction;
		_actionMapping = actionMapping;
	}

	@Override
	public boolean isCheckMethodOnProcessAction() {
		return _portletAction.isCheckMethodOnProcessAction();
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			PortalClassLoaderUtil.getClassLoader());

		try {
			_portletAction.processAction(
				_actionMapping, portletConfig, actionRequest, actionResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			PortalClassLoaderUtil.getClassLoader());

		try {
			ActionForward actionForward = _portletAction.render(
				_actionMapping, portletConfig, renderRequest, renderResponse);

			if (actionForward != null) {
				return actionForward.getPath();
			}

			return null;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void serveResource(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			PortalClassLoaderUtil.getClassLoader());

		try {
			_portletAction.serveResource(
				_actionMapping, portletConfig, resourceRequest,
				resourceResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ActionMapping _actionMapping;
	private final PortletAction _portletAction;

}