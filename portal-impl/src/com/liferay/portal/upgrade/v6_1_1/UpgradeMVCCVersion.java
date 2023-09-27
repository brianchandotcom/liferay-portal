/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_1_1;

/**
 * @author Tina Tian
 */
public class UpgradeMVCCVersion
	extends com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion {

	@Override
	protected String[] getExcludedTableNames() {
		return new String[] {
			"BackgroundTask", "CompanyInfo", "CountryLocalization",
			"ExportImportConfiguration", "LayoutFriendlyURL",
			"PortalPreferenceValue", "PortletPreferenceValue",
			"RecentLayoutBranch", "RecentLayoutRevision",
			"RecentLayoutSetBranch", "RegionLocalization", "SocialActivity",
			"SocialActivityAchievement", "SocialActivityCounter",
			"SocialActivityLimit", "SocialActivitySet", "SocialActivitySetting",
			"SocialRelation", "SocialRequest", "SystemEvent",
			"UserNotificationDelivery"
		};
	}

}