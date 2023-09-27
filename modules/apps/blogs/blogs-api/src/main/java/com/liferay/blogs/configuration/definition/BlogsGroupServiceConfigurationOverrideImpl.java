/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.configuration.definition;

import com.liferay.blogs.configuration.BlogsGroupServiceConfigurationOverride;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Sergio González
 */
public class BlogsGroupServiceConfigurationOverrideImpl
	implements BlogsGroupServiceConfigurationOverride {

	public BlogsGroupServiceConfigurationOverrideImpl(
		TypedSettings typedSettings) {

		_typedSettings = typedSettings;
	}

	@Override
	public boolean enableRss() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	private final TypedSettings _typedSettings;

}