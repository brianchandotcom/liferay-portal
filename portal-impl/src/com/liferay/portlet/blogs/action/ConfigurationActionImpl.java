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

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.util.BlogsUtil;
import com.liferay.util.ContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Jorge Ferrer
 * @author Thiago Moreira
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void postProcessPortletPreferences(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromAddress",
			BlogsUtil.getEmailFromAddress(portletPreferences, companyId));
		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromName",
			BlogsUtil.getEmailFromName(portletPreferences, companyId));

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailEntryAddedBody_" + languageId,
			ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailEntryAddedSubject_" + languageId,
			ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailEntryUpdatedBody_" + languageId,
			ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailEntryUpdatedSubject_" + languageId,
			ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT));
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