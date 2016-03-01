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

package com.liferay.portal.upgrade.v5_2_5_to_6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v5_2_5_to_6_0_0.util.WikiPageResourceTable;
import com.liferay.portal.upgrade.v5_2_5_to_6_0_0.util.WikiPageTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeWiki extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			WikiPageTable.class,
			new AlterColumnType("parentTitle", "VARCHAR(255) null"),
			new AlterColumnType("redirectTitle", "VARCHAR(255) null"));

		alter(
			WikiPageResourceTable.class,
			new AlterColumnType("title", "VARCHAR(255) null"));
	}

}