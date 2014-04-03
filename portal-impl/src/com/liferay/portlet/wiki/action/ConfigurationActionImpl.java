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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.Settings;
import com.liferay.portlet.wiki.WikiSettings;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

/**
 * @author Bruno Farache
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings) {

		WikiSettings wikiSettings = new WikiSettings(settings);

		String emailFromAddress = wikiSettings.getEmailFromAddress();

		removeDefaultValue(
			portletRequest, settings, "emailFromAddress", emailFromAddress);

		String emailFromName = wikiSettings.getEmailFromName();

		removeDefaultValue(
			portletRequest, settings, "emailFromName", emailFromName);

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, settings, "emailPageAddedBody_" + languageId,
			wikiSettings.getEmailPageAddedBody().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailPageAddedSubject_" + languageId,
			wikiSettings.getEmailPageAddedSubject().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailPageUpdatedBody_" + languageId,
			wikiSettings.getEmailPageUpdatedBody().getDefaultValue());
		removeDefaultValue(
			portletRequest, settings, "emailPageUpdatedSubject_" + languageId,
			wikiSettings.getEmailPageUpdatedSubject());
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateDisplaySettings(actionRequest);
		validateEmail(actionRequest, "emailPageAdded", true);
		validateEmail(actionRequest, "emailPageUpdated", true);
		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void validateDisplaySettings(ActionRequest actionRequest) {
		String visibleNodes = getParameter(actionRequest, "visibleNodes");

		if (Validator.isNull(visibleNodes)) {
			SessionErrors.add(actionRequest, "visibleNodesCount");
		}
	}

}