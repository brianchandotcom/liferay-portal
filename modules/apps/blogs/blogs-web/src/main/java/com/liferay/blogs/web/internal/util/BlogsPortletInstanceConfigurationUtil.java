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

package com.liferay.blogs.web.internal.util;

import com.liferay.blogs.web.internal.configuration.BlogsPortletInstanceConfiguration;
import com.liferay.blogs.web.internal.constants.BlogsWebConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class BlogsPortletInstanceConfigurationUtil {

	public static BlogsPortletInstanceConfiguration
			getBlogsPortletInstanceConfiguration(ThemeDisplay themeDisplay)
		throws ConfigurationException {

		HttpServletRequest httpServletRequest = themeDisplay.getRequest();

		BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration =
			(BlogsPortletInstanceConfiguration)httpServletRequest.getAttribute(
				BlogsWebConstants.BLOGS_PORTLET_INSTANCE_CONFIGURATION);

		if (blogsPortletInstanceConfiguration == null) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			blogsPortletInstanceConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					BlogsPortletInstanceConfiguration.class,
					new PortletInstanceSettingsLocator(
						themeDisplay.getLayout(), portletDisplay.getId()));

			httpServletRequest.setAttribute(
				BlogsWebConstants.BLOGS_PORTLET_INSTANCE_CONFIGURATION,
				blogsPortletInstanceConfiguration);
		}

		return blogsPortletInstanceConfiguration;
	}

}