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

package com.liferay.client.extension.internal.upgrade.v2_0_0;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("RemoteAppEntry", "customElementCSSURLs", "TEXT");
		alterTableAddColumn(
			"RemoteAppEntry", "customElementHTMLElementName", "VARCHAR(255)");
		alterTableAddColumn("RemoteAppEntry", "customElementURLs", "TEXT");

		alterColumnName(
			"RemoteAppEntry", "url", "iFrameURL VARCHAR(1024) null");

		if (!hasColumn("RemoteAppEntry", "instanceable")) {
			alterTableAddColumn("RemoteAppEntry", "instanceable", "BOOLEAN");

			runSQL("update RemoteAppEntry set instanceable = [$TRUE$]");
		}

		if (!hasColumn("RemoteAppEntry", "portletCategoryName")) {
			alterTableAddColumn(
				"RemoteAppEntry", "portletCategoryName", "VARCHAR(75)");

			runSQL(
				"update RemoteAppEntry set portletCategoryName = " +
					"'category.remote-apps'");
		}

		alterTableAddColumn("RemoteAppEntry", "properties", "TEXT");

		if (!hasColumn("RemoteAppEntry", "type_")) {
			alterTableAddColumn("RemoteAppEntry", "type_", "VARCHAR(75)");

			runSQL(
				StringBundler.concat(
					"update RemoteAppEntry set type_ = '",
					ClientExtensionEntryConstants.TYPE_IFRAME, "'"));
		}
	}

}