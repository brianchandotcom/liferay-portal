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

package com.liferay.change.tracking.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Gergely Mathe
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("drop table CTCollection");
		runSQL("drop table CTCollection_CTEntryAggregate");
		runSQL("drop table CTCollections_CTEntries");
		runSQL("drop table CTEntry");
		runSQL("drop table CTEntryAggregate");
		runSQL("drop table CTEntryAggregates_CTEntries");
		runSQL("drop table CTProcess");

		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream(
				"/META-INF/sql/tables.sql"));

		runSQLTemplateString(template, true);
	}

}