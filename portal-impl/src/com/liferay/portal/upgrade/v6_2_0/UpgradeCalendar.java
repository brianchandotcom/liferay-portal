/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Mate Thurzo
 */
public class UpgradeCalendar extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update CalEvent set endDate = null ");
		sb.append("where recurrence like '%\"until\":null%' ");
		sb.append("and enddate is not null");

		runSQL(sb.toString());
	}

}