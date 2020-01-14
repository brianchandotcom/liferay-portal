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

package com.liferay.layout.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;

/**
 * @author Pavel Savinov
 */
public class UpgradeDraftLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql =
			"update Layout set status = ? where classNameId = ? and classPK " +
				"> 0 and system_ = ? and type_ = ?";

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, WorkflowConstants.STATUS_DRAFT);
			ps.setLong(2, PortalUtil.getClassNameId(Layout.class));
			ps.setBoolean(3, true);
			ps.setString(4, LayoutConstants.TYPE_CONTENT);

			ps.execute();
		}
	}

}