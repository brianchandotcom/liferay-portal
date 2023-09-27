/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMVCCVersion
	extends com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion {

	@Override
	protected String[] getExcludedTableNames() {
		return new String[] {
			"CompanyInfo", "CountryLocalization", "PortalPreferenceValue",
			"PortletPreferenceValue", "RegionLocalization", "SocialActivity",
			"SocialActivityAchievement", "SocialActivityCounter",
			"SocialActivityLimit", "SocialActivitySet", "SocialActivitySetting",
			"SocialRelation", "SocialRequest"
		};
	}

	@Override
	protected String[] getModuleTableNames() {
		return new String[] {
			"AnnouncementsDelivery", "AnnouncementsEntry", "AnnouncementsFlag",
			"AssetCategory", "AssetEntry", "AssetLink", "AssetTag",
			"AssetVocabulary"
		};
	}

}