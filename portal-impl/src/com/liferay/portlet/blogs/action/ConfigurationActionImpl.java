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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.Settings;
import com.liferay.portlet.blogs.BlogsSettings;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

/**
 * @author Jorge Ferrer
 * @author Thiago Moreira
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws SystemException {

		BlogsSettings blogsSettings = new BlogsSettings(settings);

		String emailFromAddress = blogsSettings.getEmailFromAddress();

		removeDefaultValue(
			portletRequest, settings, "emailFromAddress", emailFromAddress);

		String emailFromName = blogsSettings.getEmailFromName();

		removeDefaultValue(
			portletRequest, settings, "emailFromName", emailFromName);

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, settings, "emailEntryAddedBody_" + languageId,
			blogsSettings.getEmailEntryAddedBody().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailEntryAddedSubject_" + languageId,
			blogsSettings.getEmailEntryAddedSubject().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailEntryUpdatedBody_" + languageId,
			blogsSettings.getEmailEntryUpdatedBody().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailEntryUpdatedSubject_" + languageId,
			blogsSettings.getEmailEntryUpdatedSubject().getDefaultValue());
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNotNull(cmd)) {
			validateEmail(actionRequest, "emailEntryAdded", true);
			validateEmail(actionRequest, "emailEntryUpdated", true);
			validateEmailFrom(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}