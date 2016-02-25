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

import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Preston Crary
 */
public class UpgradeKernelPackage extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws UpgradeException {
		try {
			upgradeTable(
				"Counter", "name", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"ClassName_", "value", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"ResourceAction", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeTable(
				"ResourceBlock", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeTable(
				"ResourcePermission", "name", getClassNames(),
				WildcardMode.SURROUND);

			upgradeTable(
				"ResourceAction", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeTable(
				"ResourceBlock", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeTable(
				"ResourcePermission", "name", getResourceNames(),
				WildcardMode.LEADING);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	protected void upgradeTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		StringBundler tableSQLSB = new StringBundler(7);

		tableSQLSB.append("update ");
		tableSQLSB.append(tableName);
		tableSQLSB.append(" set ");
		tableSQLSB.append(columnName);
		tableSQLSB.append(" = replace(");
		tableSQLSB.append(columnName);
		tableSQLSB.append(", '");

		String tableSQL = tableSQLSB.toString();

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			StringBundler sb = new StringBundler(9);

			for (String[] name : names) {
				sb.append(tableSQL);
				sb.append(name[0]);
				sb.append("', '");
				sb.append(name[1]);
				sb.append("') where ");
				sb.append(columnName);

				if (wildcardMode.equals(WildcardMode.LEADING) ||
					wildcardMode.equals(WildcardMode.SURROUND)) {

					sb.append(" like '%");
				}
				else {
					sb.append(" like '");
				}

				sb.append(name[0]);

				if (wildcardMode.equals(WildcardMode.SURROUND) ||
					wildcardMode.equals(WildcardMode.TRAILING)) {

					sb.append("%'");
				}
				else {
					sb.append("'");
				}

				runSQL(sb.toString());

				sb.setIndex(0);
			}
		}
	}

	private static final String[][] _CLASS_NAMES = new String[][] {
		{
			"com.liferay.counter.model.Counter",
			"com.liferay.counter.kernel.model.Counter"
		},
		{
			"com.liferay.portal.kernel.mail.Account",
			"com.liferay.mail.kernel.model.Account"
		},
		{
			"com.liferay.portal.model.BackgroundTask",
			"com.liferay.portal.background.task.model.BackgroundTask"
		},
		{
			"com.liferay.portal.model.Lock",
			"com.liferay.portal.lock.model.Lock"
		},
		{
			"com.liferay.portal.model.", "com.liferay.portal.kernel.model."
		},
		{
			"com.liferay.portlet.announcements.model.",
			"com.liferay.announcements.kernel.model."
		},
		{
			"com.liferay.portlet.asset.model.",
			"com.liferay.asset.kernel.model."
		},
		{
			"com.liferay.portlet.blogs.model.",
			"com.liferay.blogs.kernel.model."
		},
		{
			"com.liferay.portlet.documentlibrary.model.",
			"com.liferay.document.library.kernel.model."
		},
		{
			"com.liferay.portlet.expando.model.",
			"com.liferay.expando.kernel.model."
		},
		{
			"com.liferay.portlet.messageboards.model.",
			"com.liferay.message.boards.kernel.model."
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.",
			"com.liferay.mobile.device.rules.model."
		},
		{
			"com.liferay.portlet.ratings.model.",
			"com.liferay.ratings.kernel.model."
		},
		{
			"com.liferay.portlet.social.model.",
			"com.liferay.social.kernel.model."
		},
		{
			"com.liferay.portlet.trash.model.",
			"com.liferay.trash.kernel.model."
		},
		{
			"com.liferay.socialnetworking.model.",
			"com.liferay.social.networking.model."
		}
	};

	private static final String[][] _RESOURCE_NAMES = new String[][] {
		{
			"com.liferay.portlet.asset", "com.liferay.asset"
		},
		{
			"com.liferay.portlet.blogs", "com.liferay.blogs"
		},
		{
			"com.liferay.portlet.documentlibrary",
			"com.liferay.document.library"
		},
		{
			"com.liferay.portlet.messageboards", "com.liferay.message.boards"
		}
	};

}