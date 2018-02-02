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

package com.liferay.social.privatemessaging.internal.upgrade.v1_0_0;

import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.social.privatemessaging.model.PrivateMessagingConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Scott Lee
 */
public class UpgradePrivateMessaging extends UpgradeProcess {

	public UpgradePrivateMessaging(MBThreadLocalService mBThreadLocalService) {
		_mBThreadLocalService = mBThreadLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = connection.prepareStatement(
			"select mbThreadId from PM_UserThread");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			long mbThreadId = rs.getLong(1);

			_mBThreadLocalService.moveThread(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				PrivateMessagingConstants.PRIVATE_MESSAGING_CATEGORY_ID,
				mbThreadId);
		}
	}

	private final MBThreadLocalService _mBThreadLocalService;

}