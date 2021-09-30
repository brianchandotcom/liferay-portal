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

package com.liferay.remote.app.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.internal.upgrade.v1_0_1.util.RemoteAppEntryTable;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RemoteAppEntry", "customElementCSSURLs")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("customElementCSSURLs", "TEXT"));
		}

		if (!hasColumn("RemoteAppEntry", "customElementHTMLElementName")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn(
					"customElementHTMLElementName", "VARCHAR(255)"));
		}

		if (!hasColumn("RemoteAppEntry", "customElementURLs")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("customElementURLs", "TEXT"));
		}

		alter(
			RemoteAppEntryTable.class,
			new AlterColumnName("url", "iFrameURL STRING null"));

		if (!hasColumn("RemoteAppEntry", "instanceable")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("instanceable", "BOOLEAN"));

			runSQL("update RemoteAppEntry set instanceable = [$TRUE$]");
		}

		if (!hasColumn("RemoteAppEntry", "portletCategoryName")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("portletCategoryName", "VARCHAR(75)"));

			runSQL(
				"update RemoteAppEntry set portletCategoryName = " +
					"'category.remote-apps'");
		}

		if (!hasColumn("RemoteAppEntry", "properties")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("properties", "TEXT"));
		}

		if (!hasColumn("RemoteAppEntry", "type_")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("type_", "VARCHAR(75)"));

			runSQL(
				StringBundler.concat(
					"update RemoteAppEntry set type_ = '",
					RemoteAppConstants.TYPE_IFRAME, "'"));
		}
	}

}