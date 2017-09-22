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

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;

/**
 * @author Shinn Lok
 */
public class GroupThreadLocal {

	public static Long getGroupId() {
		Long groupId = _groupId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getGroupId " + groupId);
		}

		return groupId;
	}

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	public static void setGroupId(Long groupId) {
		if (_log.isDebugEnabled()) {
			_log.debug("setGroupId " + groupId);
		}

		if (groupId > 0) {
			_groupId.set(groupId);
		}
		else {
			_groupId.set(GroupConstants.DEFAULT_LIVE_GROUP_ID);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupThreadLocal.class);

	private static final ThreadLocal<Boolean> _deleteInProcess =
		new CentralizedThreadLocal<>(
			GroupThreadLocal.class + "._deleteInProcess", () -> Boolean.FALSE);
	private static final ThreadLocal<Long> _groupId =
		new CentralizedThreadLocal<>(
			GroupThreadLocal.class + "._groupId",
			() -> GroupConstants.DEFAULT_LIVE_GROUP_ID);

}