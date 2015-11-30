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

package com.liferay.portal.instances.web.portlet.action;

import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.RequiredCompanyException;
import com.liferay.portal.instances.web.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.util.PortalInstances;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/portal_instances/edit_instance"
	},
	service = MVCActionCommand.class
)
public class EditInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteInstance(ActionRequest actionRequest)
		throws Exception {

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		_companyService.deleteCompany(companyId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteInstance(actionRequest);
			}
			else {
				updateInstance(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			String mvcPath = "/error.jsp";

			if (e instanceof NoSuchCompanyException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof CompanyMxException ||
					 e instanceof CompanyVirtualHostException ||
					 e instanceof CompanyWebIdException) {

				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/edit_instance.jsp";
			}
			else if (e instanceof RequiredCompanyException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyService(CompanyService companyService) {
		_companyService = companyService;
	}

	protected void updateInstance(ActionRequest actionRequest)
		throws Exception {

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		String webId = ParamUtil.getString(actionRequest, "webId");
		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname");
		String mx = ParamUtil.getString(actionRequest, "mx");
		boolean system = false;
		int maxUsers = ParamUtil.getInteger(actionRequest, "maxUsers", 0);
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (companyId <= 0) {

			// Add instance

			Company company = _companyService.addCompany(
				webId, virtualHostname, mx, system, maxUsers, active);

			ServletContext servletContext =
				(ServletContext)actionRequest.getAttribute(WebKeys.CTX);

			PortalInstances.initCompany(servletContext, company.getWebId());
		}
		else {

			// Update instance

			_companyService.updateCompany(
				companyId, virtualHostname, mx, maxUsers, active);
		}
	}

	private volatile CompanyService _companyService;

}