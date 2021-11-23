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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.upgrade.PortalMVCCVersionUpgradeProcess;

/**
 * @author Preston Crary
 */
public class UpgradeSocialMVCCVersion extends PortalMVCCVersionUpgradeProcess {

	@Override
	protected String[] getExcludedTableNames() {
		return new String[] {
			"CompanyInfo", "CountryLocalization", "PortalPreferenceValue",
			"PortletPreferenceValue", "RegionLocalization"
		};
	}

	@Override
	protected String[] getModuleTableNames() {
		return new String[] {
			"SocialActivity", "SocialActivityAchievement",
			"SocialActivityCounter", "SocialActivityLimit", "SocialActivitySet",
			"SocialActivitySetting", "SocialRelation", "SocialRequest"
		};
	}

}