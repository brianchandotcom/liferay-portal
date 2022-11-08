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

package com.liferay.cookies.internal.configuration.helper;

import com.liferay.cookies.configuration.CookiesConfigurationHelper;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
@Component(immediate = true, service = CookiesConfigurationHelper.class)
public class CookiesConfigurationHelperImpl
	implements CookiesConfigurationHelper {

  @Override
	public CookiesPreferenceHandlingConfiguration
			getCookiesPreferenceHandlingConfiguration(
				HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

    LayoutSet layoutSet = _LayoutSetLocalService.fetchLayoutSet(
      themeDisplay.getServerName());

    if (layoutSet != null) {
			Group group = layoutSet.getGroup();

			return _configurationProvider.getGroupConfiguration(
				CookiesPreferenceHandlingConfiguration.class,
				group.getGroupId());
		}

		return _configurationProvider.getCompanyConfiguration(
			CookiesPreferenceHandlingConfiguration.class,
			themeDisplay.getCompanyId());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

  @Reference
  private LayoutSetLocalService _LayoutSetLocalService;

}
