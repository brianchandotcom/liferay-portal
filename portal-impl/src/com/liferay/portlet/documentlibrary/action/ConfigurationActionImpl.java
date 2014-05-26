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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	public ConfigurationActionImpl() {
		addMultiValuedKeys(DLPortletInstanceSettings.MULTI_VALUED_KEYS);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNotNull(cmd)) {
			validate(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	protected Settings getSettings(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return new DLSettings(super.getSettings(actionRequest));
	}

	protected void validate(ActionRequest actionRequest) throws Exception {
		validateDisplayStyleViews(actionRequest);
		validateRootFolder(actionRequest);
	}

	protected void validateDisplayStyleViews(ActionRequest actionRequest) {
		String displayViews = GetterUtil.getString(
			getParameter(actionRequest, "displayViews"));

		if (Validator.isNull(displayViews)) {
			SessionErrors.add(actionRequest, "displayViewsInvalid");
		}
	}

	protected void validateRootFolder(ActionRequest actionRequest)
		throws Exception {

		long rootFolderId = GetterUtil.getLong(
			getParameter(actionRequest, "rootFolderId"));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				DLAppLocalServiceUtil.getFolder(rootFolderId);
			}
			catch (Exception e) {
				if (e instanceof NoSuchFolderException ||
					e instanceof NoSuchRepositoryEntryException) {

					SessionErrors.add(actionRequest, "rootFolderIdInvalid");
				}
				else {
					throw e;
				}
			}
		}
	}

}