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

package com.liferay.site.navigation.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.site.navigation.constants.SiteNavigationConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteNavigationMenuImpl extends SiteNavigationMenuBaseImpl {

	@Override
	public String getTypeKey() {
		String navigationTypeKey = StringPool.BLANK;

		if (getType() == SiteNavigationConstants.TYPE_PRIMARY) {
			navigationTypeKey = "primary-navigation";
		}
		else if (getType() == SiteNavigationConstants.TYPE_SECONDARY) {
			navigationTypeKey = "secondary-navigation";
		}
		else if (getType() == SiteNavigationConstants.TYPE_SOCIAL) {
			navigationTypeKey = "social-navigation";
		}

		return navigationTypeKey;
	}

	@Override
	public boolean isPrimary() {
		if (getType() == SiteNavigationConstants.TYPE_PRIMARY) {
			return true;
		}

		return false;
	}

}