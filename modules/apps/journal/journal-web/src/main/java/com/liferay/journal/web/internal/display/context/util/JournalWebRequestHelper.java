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

package com.liferay.journal.web.internal.display.context.util;

import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class JournalWebRequestHelper {

	public JournalWebRequestHelper(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public JournalGroupServiceConfiguration
		getJournalGroupServiceConfiguration() {

		try {
			if (_journalGroupServiceConfiguration == null) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				if (Validator.isNotNull(portletDisplay.getPortletResource())) {
					_journalGroupServiceConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							JournalGroupServiceConfiguration.class,
							new ParameterMapSettingsLocator(
								_httpServletRequest.getParameterMap(),
								new GroupServiceSettingsLocator(
									themeDisplay.getSiteGroupId(),
									JournalConstants.SERVICE_NAME)));
				}
				else {
					_journalGroupServiceConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							JournalGroupServiceConfiguration.class,
							new GroupServiceSettingsLocator(
								themeDisplay.getSiteGroupId(),
								JournalConstants.SERVICE_NAME));
				}
			}

			return _journalGroupServiceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private final HttpServletRequest _httpServletRequest;
	private JournalGroupServiceConfiguration _journalGroupServiceConfiguration;

}