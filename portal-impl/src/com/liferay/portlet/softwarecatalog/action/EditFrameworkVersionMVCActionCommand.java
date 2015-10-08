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

package com.liferay.portlet.softwarecatalog.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;

/**
 * @author Jorge Ferrer
 * @author Philip Jones
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.SOFTWARE_CATALOG,
		"mvc.command.name=/software_catalog/edit_framework_version"
	},
	service = MVCActionCommand.class
)
public class EditFrameworkVersionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			updateFrameworkVersion(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteFrameworkVersion(actionRequest);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		sendRedirect(actionRequest, actionResponse, redirect);
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