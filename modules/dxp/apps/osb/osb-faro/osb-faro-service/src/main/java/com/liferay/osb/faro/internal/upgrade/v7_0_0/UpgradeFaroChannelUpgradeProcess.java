/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author André Miranda
 */
public class UpgradeFaroChannelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"create table OSBFaro_FaroChannel (",
				"faroChannelId LONG not null primary key, groupId LONG, ",
				"userId LONG, userName VARCHAR(75) null, createTime LONG, ",
				"modifiedTime LONG, channelId VARCHAR(75), name VARCHAR(75) ",
				"null, permissionType INTEGER, workspaceGroupId LONG)"));
	}

}