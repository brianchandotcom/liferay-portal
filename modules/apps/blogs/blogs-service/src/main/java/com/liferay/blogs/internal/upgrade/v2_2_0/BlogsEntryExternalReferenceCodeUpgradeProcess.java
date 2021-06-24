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

package com.liferay.blogs.internal.upgrade.v2_2_0;

import com.liferay.blogs.internal.upgrade.v2_2_0.util.BlogsEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Miguel Barcos
 */
public class BlogsEntryExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(BlogsEntryTable.TABLE_NAME, "externalReferenceCode")) {
			alter(
				BlogsEntryTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));

			runSQL(
				StringBundler.concat(
					"update ", BlogsEntryTable.TABLE_NAME,
					" set externalReferenceCode = CAST_TEXT(entryId) where ",
					"externalReferenceCode is null or externalReferenceCode =",
					"''"));
		}
	}

}