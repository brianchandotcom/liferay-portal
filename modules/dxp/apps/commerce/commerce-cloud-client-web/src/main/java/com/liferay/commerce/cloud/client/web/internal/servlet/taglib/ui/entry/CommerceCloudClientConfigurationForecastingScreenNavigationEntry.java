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

package com.liferay.commerce.cloud.client.web.internal.servlet.taglib.ui.entry;

import com.liferay.commerce.cloud.client.web.internal.constants.CommerceCloudClientScreenNavigationConstants;
import com.liferay.commerce.cloud.client.web.internal.display.context.EditConfigurationDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class
	CommerceCloudClientConfigurationForecastingScreenNavigationEntry
		extends BaseCommerceCloudClientConfigurationScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return
			CommerceCloudClientScreenNavigationConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return
			CommerceCloudClientScreenNavigationConstants.ENTRY_KEY_FORECASTING;
	}

	@Override
	public boolean isVisible(
		User user,
		EditConfigurationDisplayContext editConfigurationDisplayContext) {

		if (editConfigurationDisplayContext.
				getForecastingConfiguration() != null) {

			return true;
		}

		return false;
	}

	@Override
	protected String getJspPath() {
		return "/configuration/forecasting.jsp";
	}

}