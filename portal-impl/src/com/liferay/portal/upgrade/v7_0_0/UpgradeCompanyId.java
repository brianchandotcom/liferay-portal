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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.AnnouncementsFlagUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.AssetEntriesAssetCategoriesUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.AssetEntriesAssetTagsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.AssetTagStatsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.BrowserTrackerUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.DLFileEntryMetadataUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.DLFileEntryTypesDLFoldersUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.DLSyncEventUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.GroupOrgsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.GroupRolesUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.GroupUserGroupsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.ImageUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.MBStatsUserUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.OrgGroupRoleUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.OrgLaborUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.PasswordPasswordTrackerUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.PasswordPolicyRelUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.PortletPreferencesUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.RatingsStatsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.ResourceBlockPermissionUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.SCFrameworkVersiSCProductVersUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.SCLicenseUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.SCLicensesSCProductEntriesUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.TrashVersionUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UserGroupGroupRoleUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UserGroupRoleUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UserGroupsTeamsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UserIdMapperUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UserTrackerPathUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UsersGroupsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UsersOrgsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UsersRolesUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UsersTeamsUpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.util.companyId.UsersUserGroupsUpgradeCompanyId;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeCompanyId
	extends com.liferay.portal.upgrade.util.UpgradeCompanyId {

	@Override
	protected UpgradeCompanyIdInTable[] getUpgradeCompanyIdInTable() {
		return new UpgradeCompanyIdInTable[] {
			new AnnouncementsFlagUpgradeCompanyId(),
			new AssetEntriesAssetCategoriesUpgradeCompanyId(),
			new AssetEntriesAssetTagsUpgradeCompanyId(),
			new AssetTagStatsUpgradeCompanyId(),
			new BrowserTrackerUpgradeCompanyId(),
			new DLFileEntryMetadataUpgradeCompanyId(),
			new DLFileEntryTypesDLFoldersUpgradeCompanyId(),
			new DLSyncEventUpgradeCompanyId(), new GroupOrgsUpgradeCompanyId(),
			new GroupRolesUpgradeCompanyId(),
			new GroupUserGroupsUpgradeCompanyId(), new ImageUpgradeCompanyId(),
			new MBStatsUserUpgradeCompanyId(),
			new OrgGroupRoleUpgradeCompanyId(), new OrgLaborUpgradeCompanyId(),
			new PasswordPolicyRelUpgradeCompanyId(),
			new PasswordPasswordTrackerUpgradeCompanyId(),
			new PortletPreferencesUpgradeCompanyId(),
			new RatingsStatsUpgradeCompanyId(),
			new ResourceBlockPermissionUpgradeCompanyId(),
			new SCFrameworkVersiSCProductVersUpgradeCompanyId(),
			new SCLicenseUpgradeCompanyId(),
			new SCLicensesSCProductEntriesUpgradeCompanyId(),
			new TrashVersionUpgradeCompanyId(),
			new UserGroupGroupRoleUpgradeCompanyId(),
			new UserGroupRoleUpgradeCompanyId(),
			new UserGroupsTeamsUpgradeCompanyId(),
			new UserIdMapperUpgradeCompanyId(),
			new UsersGroupsUpgradeCompanyId(), new UsersOrgsUpgradeCompanyId(),
			new UsersRolesUpgradeCompanyId(), new UsersTeamsUpgradeCompanyId(),
			new UsersUserGroupsUpgradeCompanyId(),
			new UserTrackerPathUpgradeCompanyId()
		};
	}

}