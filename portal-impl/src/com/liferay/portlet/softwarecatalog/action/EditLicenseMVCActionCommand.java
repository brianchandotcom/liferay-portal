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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Jorge Ferrer
 * @autorh Philip Jones
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.SOFTWARE_CATALOG,
		"mvc.command.name=/software_catalog/edit_license"
	},
	service = MVCActionCommand.class
)
public class EditLicenseMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			updateLicense(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteLicense(actionRequest);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected void deleteLicense(ActionRequest actionRequest) throws Exception {
		long licenseId = ParamUtil.getLong(actionRequest, "licenseId");

		SCLicenseServiceUtil.deleteLicense(licenseId);
	}

	protected void updateLicense(ActionRequest actionRequest) throws Exception {
		long licenseId = ParamUtil.getLong(actionRequest, "licenseId");

		String name = ParamUtil.getString(actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");
		boolean openSource = ParamUtil.getBoolean(actionRequest, "openSource");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		boolean recommended = ParamUtil.getBoolean(
			actionRequest, "recommended");

		if (licenseId <= 0) {

			// Add license

			SCLicenseServiceUtil.addLicense(
				name, url, openSource, active, recommended);
		}
		else {

			// Update license

			SCLicenseServiceUtil.updateLicense(
				licenseId, name, url, openSource, active, recommended);
		}
	}

}