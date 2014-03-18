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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.ContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void postProcessPortletPreferences(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromAddress",
			DLUtil.getEmailFromAddress(portletPreferences, companyId));
		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromName",
			DLUtil.getEmailFromName(portletPreferences, companyId));

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailFileEntryAddedBody_" + languageId,
			ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailFileEntryAddedSubject_" + languageId,
			ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailFileEntryUpdatedBody_" + languageId,
			ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailFileEntryUpdatedSubject_" + languageId,
			ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT));
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNotNull(cmd)) {
			validateDisplayStyleViews(actionRequest);
			validateEmail(actionRequest, "emailFileEntryAdded", true);
			validateEmail(actionRequest, "emailFileEntryUpdated", true);
			validateEmailFrom(actionRequest);
			validateRootFolder(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
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