/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.client.web.internal.servlet.taglib.ui.entry;

import com.liferay.commerce.cloud.client.web.internal.constants.CommerceCloudClientScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class CommerceCloudClientConfigurationConnectionScreenNavigationEntry
	extends BaseCommerceCloudClientConfigurationScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return
			CommerceCloudClientScreenNavigationConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return
			CommerceCloudClientScreenNavigationConstants.ENTRY_KEY_CONNECTION;
	}

	@Override
	protected String getJspPath() {
		return "/configuration/connection.jsp";
	}

}