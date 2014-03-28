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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Bruno Farache
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	public void postProcessPortletPreferences(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		String emailFromAddress = PortalUtil.getEmailFromAddress(
			portletPreferences, companyId,
			PropsValues.WIKI_EMAIL_FROM_ADDRESS);

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromAddress",
			emailFromAddress);

		String emailFromName = PortalUtil.getEmailFromName(
			portletPreferences, companyId, PropsValues.WIKI_EMAIL_FROM_NAME);

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromName", emailFromName);

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPageAddedBody_" + languageId,
			ContentUtil.get(PropsValues.WIKI_EMAIL_PAGE_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPageAddedSubject_" + languageId,
			ContentUtil.get(PropsValues.WIKI_EMAIL_PAGE_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPageUpdatedBody_" + languageId,
			ContentUtil.get(PropsValues.WIKI_EMAIL_PAGE_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPageUpdatedSubject_" + languageId,
			ContentUtil.get(PropsValues.WIKI_EMAIL_PAGE_UPDATED_SUBJECT));
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