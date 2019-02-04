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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Jeyvison Nascimento
 */
public class UpgradeDDMStructureLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DDMStructureLayout", "description") &&
			!hasColumn("DDMStructureLayout", "name")) {

			runSQL("drop index IX_B7158C0A on DDMStructureLayout");

			String template = StringUtil.read(
				UpgradeDDMStructureLayout.class.getResourceAsStream(
					"dependencies/update.sql"));

			runSQLTemplateString(template, false, true);
		}
	}

}