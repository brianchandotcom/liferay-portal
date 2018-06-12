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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay;
import com.liferay.configuration.admin.web.internal.display.ConfigurationEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Drew Brokke
 */
public class ConfigurationCategoryUtil {

	public static String getHref(
		ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay,
		LiferayPortletResponse liferayPortletResponse,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		if (!configurationCategoryMenuDisplay.isEmpty()) {
			ConfigurationEntry configurationEntry =
				configurationCategoryMenuDisplay.getFirstConfigurationEntry();

			return configurationEntry.getEditURL(renderRequest, renderResponse);
		}

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return portletURL.toString();
	}

}