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
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil;

/**
 * @author Jorge Ferrer
 * @author Philip Jones
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.SOFTWARE_CATALOG,
		"mvc.command.name=/software_catalog/edit_product_version"
	},
	service = MVCActionCommand.class
)
public class EditProductVersionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			updateProductVersion(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteProductVersion(actionRequest);
		}
		
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		sendRedirect(actionRequest, actionResponse, redirect);

	}

	protected void deleteProductVersion(ActionRequest actionRequest)
		throws Exception {

		long productVersionId = ParamUtil.getLong(
			actionRequest, "productVersionId");

		SCProductVersionServiceUtil.deleteProductVersion(productVersionId);
	}

	protected void updateProductVersion(ActionRequest actionRequest)
		throws Exception {

		long productVersionId = ParamUtil.getLong(
			actionRequest, "productVersionId");

		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");
		String version = ParamUtil.getString(actionRequest, "version");
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");
		String downloadPageURL = ParamUtil.getString(
			actionRequest, "downloadPageURL");
		String directDownloadURL = ParamUtil.getString(
			actionRequest, "directDownloadURL");
		boolean testDirectDownloadURL = ParamUtil.getBoolean(
			actionRequest, "testDirectDownloadURL");
		boolean repoStoreArtifact = ParamUtil.getBoolean(
			actionRequest, "repoStoreArtifact");

		long[] frameworkVersionIds = ParamUtil.getLongValues(
			actionRequest, "frameworkVersions");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SCProductVersion.class.getName(), actionRequest);

		if (productVersionId <= 0) {

			// Add product version

			SCProductVersionServiceUtil.addProductVersion(
				productEntryId, version, changeLog, downloadPageURL,
				directDownloadURL, testDirectDownloadURL, repoStoreArtifact,
				frameworkVersionIds, serviceContext);
		}
		else {

			// Update product version

			SCProductVersionServiceUtil.updateProductVersion(
				productVersionId, version, changeLog, downloadPageURL,
				directDownloadURL, testDirectDownloadURL, repoStoreArtifact,
				frameworkVersionIds);
		}
	}

}