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

package com.liferay.exportimport.web.internal.portlet.action;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=exportImport"
	},
	service = MVCResourceCommand.class
)
public class ExportImportMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		PortletRequestDispatcher portletRequestDispatcher = null;

		if (cmd.equals(Constants.EXPORT)) {
			portletRequestDispatcher = getPortletRequestDispatcher(
				resourceRequest, "/export_portlet_processes.jsp");
		}
		else if (cmd.equals(Constants.IMPORT)) {
			portletRequestDispatcher = getPortletRequestDispatcher(
				resourceRequest, "/import_portlet_processes.jsp");
		}
		else if (cmd.equals("export_import")) {
			portletRequestDispatcher = getPortletRequestDispatcher(
				resourceRequest, "/export_import_process.jsp");
		}
		else {
			portletRequestDispatcher = getPortletRequestDispatcher(
				resourceRequest, "/import_portlet_resources.jsp");
		}

		resourceRequest = ActionUtil.getWrappedResourceRequest(
			resourceRequest, null);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

}