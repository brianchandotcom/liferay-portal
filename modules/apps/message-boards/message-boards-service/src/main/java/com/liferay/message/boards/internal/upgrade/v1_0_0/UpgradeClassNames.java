/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v1_0_0;

import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.message.boards.kernel.model.MBBan",
			"com.liferay.message.boards.model.MBBan"
		},
		{
			"com.liferay.message.boards.kernel.model.MBCategory",
			"com.liferay.message.boards.model.MBCategory"
		},
		{
			"com.liferay.message.boards.kernel.model.MBDiscussion",
			"com.liferay.message.boards.model.MBDiscussion",
			"com.liferay.message.boards.model.MBDiscussion_"
		},
		{
			"com.liferay.message.boards.kernel.model.MBMailingList",
			"com.liferay.message.boards.model.MBMailingList"
		},
		{
			"com.liferay.message.boards.kernel.model.MBMessage",
			"com.liferay.message.boards.model.MBMessage"
		},
		{
			"com.liferay.message.boards.kernel.model.MBStatsUser",
			"com.liferay.message.boards.model.MBStatsUser"
		},
		{
			"com.liferay.message.boards.kernel.model.MBThread",
			"com.liferay.message.boards.model.MBThread"
		},
		{
			"com.liferay.message.boards.kernel.model.MBThreadFlag",
			"com.liferay.message.boards.model.MBThreadFlag"
		}
	};

	private static final String[][] _RESOURCE_NAMES = new String[0][0];

}