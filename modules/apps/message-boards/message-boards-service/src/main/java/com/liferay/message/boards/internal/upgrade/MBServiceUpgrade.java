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

package com.liferay.message.boards.internal.upgrade;

import com.liferay.message.boards.internal.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.message.boards.internal.upgrade.v1_0_1.UpgradeUnsupportedGuestPermissions;
import com.liferay.message.boards.internal.upgrade.v1_1_0.MBThreadUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBBanTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBCategoryTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBDiscussionTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBMailingListTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBMessageTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBStatsUserTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBThreadFlagTable;
import com.liferay.message.boards.internal.upgrade.v2_0_0.util.MBThreadTable;
import com.liferay.message.boards.internal.upgrade.v3_0_0.MBMessageTreePathUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v3_1_0.UrlSubjectUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v4_0_0.MBCategoryLastPostDateUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v4_0_0.MBCategoryMessageCountUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v4_0_0.MBCategoryThreadCountUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v5_0_0.MBThreadMessageCountUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v5_2_0.MBMessageExternalReferenceCodeUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v6_0_0.MBStatsUserUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v6_1_0.MBThreadTableUpgradeProcess;
import com.liferay.message.boards.internal.upgrade.v6_1_1.MBMessageTableUpgradeProcess;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.ViewCountUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class MBServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new UpgradeClassNames());

		registry.register(
			"1.0.0", "1.0.1",
			new UpgradeUnsupportedGuestPermissions(
				_resourceActionLocalService, _resourcePermissionLocalService,
				_roleLocalService));

		registry.register("1.0.1", "1.1.0", new MBThreadUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					MBBanTable.class, MBCategoryTable.class,
					MBDiscussionTable.class, MBMailingListTable.class,
					MBMessageTable.class, MBStatsUserTable.class,
					MBThreadFlagTable.class, MBThreadTable.class
				}));

		registry.register(
			"2.0.0", "3.0.0",
			new ViewCountUpgradeProcess(
				"MBThread", MBThread.class, "threadId", "viewCount"),
			new MBMessageTreePathUpgradeProcess());

		registry.register("3.0.0", "3.1.0", new UrlSubjectUpgradeProcess());

		registry.register(
			"3.1.0", "4.0.0", new MBCategoryLastPostDateUpgradeProcess(),
			new MBCategoryMessageCountUpgradeProcess(),
			new MBCategoryThreadCountUpgradeProcess());

		registry.register(
			"4.0.0", "5.0.0", new MBThreadMessageCountUpgradeProcess(),
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"MBBan", "MBCategory", "MBDiscussion", "MBMailingList",
						"MBMessage", "MBStatsUser", "MBThread", "MBThreadFlag"
					};
				}

			});

		registry.register(
			"5.0.0", "5.1.0",
			new CTModelUpgradeProcess(
				"MBBan", "MBCategory", "MBDiscussion", "MBMailingList",
				"MBMessage", "MBStatsUser", "MBThread", "MBThreadFlag"));

		registry.register(
			"5.1.0", "5.2.0",
			new MBMessageExternalReferenceCodeUpgradeProcess());

		registry.register("5.2.0", "6.0.0", new MBStatsUserUpgradeProcess());

		registry.register("6.0.0", "6.1.0", new MBThreadTableUpgradeProcess());

		registry.register("6.1.0", "6.1.1", new MBMessageTableUpgradeProcess());
	}

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	/**
	 * See LPS-101086. The ViewCount table needs to exist.
	 */
	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}