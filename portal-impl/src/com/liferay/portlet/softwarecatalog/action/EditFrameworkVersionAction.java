/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.softwarecatalog.FrameworkVersionNameException;
import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class EditFrameworkVersionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFrameworkVersion(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFrameworkVersion(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFrameworkVersionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.software_catalog.error");
			}
			else if (e instanceof FrameworkVersionNameException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFrameworkVersion(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFrameworkVersionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.software_catalog.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.software_catalog.edit_framework_version"));
	}

	protected void deleteFrameworkVersion(ActionRequest actionRequest)
		throws Exception {

		long frameworkVersionId = ParamUtil.getLong(
			actionRequest, "frameworkVersionId");

		SCFrameworkVersionServiceUtil.deleteFrameworkVersion(
			frameworkVersionId);
	}

	protected void updateFrameworkVersion(ActionRequest actionRequest)
		throws Exception {

		long frameworkVersionId = ParamUtil.getLong(
			actionRequest, "frameworkVersionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		int priority = ParamUtil.getInteger(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SCFrameworkVersion.class.getName(), actionRequest);

		if (frameworkVersionId <= 0) {

			// Add framework version

			SCFrameworkVersionServiceUtil.addFrameworkVersion(
				name, url, active, priority, serviceContext);
		}
		else {

			// Update framework version

			SCFrameworkVersionServiceUtil.updateFrameworkVersion(
				frameworkVersionId, name, url, active, priority);
		}
	}

}