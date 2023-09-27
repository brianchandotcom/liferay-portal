/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.upgrade.v7_2_x.util.AccountTable;
import com.liferay.portal.upgrade.v7_2_x.util.AddressTable;
import com.liferay.portal.upgrade.v7_2_x.util.AnnouncementsEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.AnnouncementsFlagTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetCategoryTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetLinkTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetTagTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetVocabularyTable;
import com.liferay.portal.upgrade.v7_2_x.util.ContactTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileEntryTypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileShortcutTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileVersionTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFolderTable;
import com.liferay.portal.upgrade.v7_2_x.util.EmailAddressTable;
import com.liferay.portal.upgrade.v7_2_x.util.ExpandoRowTable;
import com.liferay.portal.upgrade.v7_2_x.util.ExportImportConfigurationTable;
import com.liferay.portal.upgrade.v7_2_x.util.GroupTable;
import com.liferay.portal.upgrade.v7_2_x.util.ImageTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutFriendlyURLTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutPrototypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutRevisionTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutSetBranchTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutSetPrototypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutSetTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutTable;
import com.liferay.portal.upgrade.v7_2_x.util.MembershipRequestTable;
import com.liferay.portal.upgrade.v7_2_x.util.OrganizationTable;
import com.liferay.portal.upgrade.v7_2_x.util.PasswordPolicyTable;
import com.liferay.portal.upgrade.v7_2_x.util.PasswordTrackerTable;
import com.liferay.portal.upgrade.v7_2_x.util.PhoneTable;
import com.liferay.portal.upgrade.v7_2_x.util.PortletItemTable;
import com.liferay.portal.upgrade.v7_2_x.util.RatingsEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.ReleaseTable;
import com.liferay.portal.upgrade.v7_2_x.util.RepositoryEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.RepositoryTable;
import com.liferay.portal.upgrade.v7_2_x.util.RoleTable;
import com.liferay.portal.upgrade.v7_2_x.util.SystemEventTable;
import com.liferay.portal.upgrade.v7_2_x.util.TeamTable;
import com.liferay.portal.upgrade.v7_2_x.util.TicketTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserGroupTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserTrackerPathTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserTrackerTable;
import com.liferay.portal.upgrade.v7_2_x.util.WebDAVPropsTable;
import com.liferay.portal.upgrade.v7_2_x.util.WebsiteTable;
import com.liferay.portal.upgrade.v7_2_x.util.WorkflowDefinitionLinkTable;
import com.liferay.portal.upgrade.v7_2_x.util.WorkflowInstanceLinkTable;

/**
 * @author José Ángel Jiménez
 */
public class UpgradeSQLServerDatetime
	extends BaseSQLServerDatetimeUpgradeProcess {

	public UpgradeSQLServerDatetime() {
		super(_TABLE_CLASSES);
	}

	private static final Class<?>[] _TABLE_CLASSES = {
		AccountTable.class, AddressTable.class, AnnouncementsEntryTable.class,
		AnnouncementsFlagTable.class, AssetCategoryTable.class,
		AssetEntryTable.class, AssetLinkTable.class, AssetTagTable.class,
		AssetVocabularyTable.class, ContactTable.class, DLFileEntryTable.class,
		DLFileEntryTypeTable.class, DLFileShortcutTable.class,
		DLFileVersionTable.class, DLFolderTable.class, EmailAddressTable.class,
		ExpandoRowTable.class, ExportImportConfigurationTable.class,
		GroupTable.class, ImageTable.class, LayoutFriendlyURLTable.class,
		LayoutPrototypeTable.class, LayoutRevisionTable.class,
		LayoutSetBranchTable.class, LayoutSetPrototypeTable.class,
		LayoutSetTable.class, LayoutTable.class, MembershipRequestTable.class,
		OrganizationTable.class, PasswordPolicyTable.class,
		PasswordTrackerTable.class, PhoneTable.class, PortletItemTable.class,
		RatingsEntryTable.class, ReleaseTable.class, RepositoryEntryTable.class,
		RepositoryTable.class, RoleTable.class, SystemEventTable.class,
		TeamTable.class, TicketTable.class, UserGroupTable.class,
		UserTable.class, UserTrackerPathTable.class, UserTrackerTable.class,
		WebDAVPropsTable.class, WebsiteTable.class,
		WorkflowDefinitionLinkTable.class, WorkflowInstanceLinkTable.class
	};

}