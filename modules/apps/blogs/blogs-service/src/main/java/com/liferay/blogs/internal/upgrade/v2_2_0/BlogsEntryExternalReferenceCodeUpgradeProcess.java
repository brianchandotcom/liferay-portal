/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.v2_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Miguel Barcos
 */
public class BlogsEntryExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("BlogsEntry", "externalReferenceCode")) {
			alterTableAddColumn(
				"BlogsEntry", "externalReferenceCode", "VARCHAR(75)");

			runSQL(
				StringBundler.concat(
					"update BlogsEntry set externalReferenceCode = ",
					"CAST_TEXT(entryId) where externalReferenceCode is null ",
					"or externalReferenceCode = ''"));
		}
	}

}