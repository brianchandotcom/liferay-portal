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

package com.liferay.login.web.internal.portlet.action;

import com.liferay.login.web.internal.constants.LoginPortletKeys;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@Component(
	property = {
		"javax.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + LoginPortletKeys.LOGIN
	},
	service = ConfigurationAction.class
)
public class LoginConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void postProcess(
		long companyId, PortletRequest portletRequest,
		PortletPreferences portletPreferences) {

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordResetBody_" + languageId,
			ContentUtil.get(
				PortalClassLoaderUtil.getClassLoader(),
				PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordResetSubject_" + languageId,
			ContentUtil.get(
				PortalClassLoaderUtil.getClassLoader(),
				PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT));

		String[] discardLegacyKeys = ParamUtil.getStringValues(
			portletRequest, "discardLegacyKey");

		Enumeration<String> names = portletPreferences.getNames();

		try {
			while (names.hasMoreElements()) {
				String name = names.nextElement();

				for (String discardLegacyKey : discardLegacyKeys) {
					if (name.startsWith(discardLegacyKey + "_")) {
						portletPreferences.reset(name);
					}
				}
			}
		}
		catch (ReadOnlyException roe) {
			throw new SystemException(roe);
		}
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}