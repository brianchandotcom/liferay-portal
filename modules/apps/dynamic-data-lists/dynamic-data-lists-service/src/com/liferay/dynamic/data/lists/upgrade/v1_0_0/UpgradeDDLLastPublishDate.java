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

package com.liferay.dynamic.data.lists.upgrade.v1_0_0;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate;

/**
 * @author Levente Hudak
 */
public class UpgradeDDLLastPublishDate extends UpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		super.updateLastPublishDates(
			DDLPortletKeys.DYNAMIC_DATA_LISTS, "DDLRecord");
		super.updateLastPublishDates(
			DDLPortletKeys.DYNAMIC_DATA_LISTS, "DDLRecordSet");
	}

}