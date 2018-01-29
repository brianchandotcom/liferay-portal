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

package com.liferay.lcs.task;

import com.liferay.lcs.messaging.ScheduledTaskMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class UserGroupNamesTask extends BaseScheduledTask {

	@Override
	public Type getType() {
		return Type.MEMORY_CLUSTERED;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public void setPauseInterval(long pauseInterval) {
		_pauseInterval = pauseInterval;
	}

	@Override
	protected void doRun() throws Exception {
		if (_log.isTraceEnabled()) {
			_log.trace("Running user group names task");
		}

		int start = 0;
		int end = _pageSize;
		long queryStartTime = System.currentTimeMillis();

		long userGroupCount = UserGroupLocalServiceUtil.getUserGroupsCount();

		while (start < userGroupCount) {
			List<UserGroup> userGroups =
				UserGroupLocalServiceUtil.getUserGroups(start, end);

			ScheduledTaskMessage scheduledTaskMessage =
				new ScheduledTaskMessage();

			scheduledTaskMessage.setCreateTime(System.currentTimeMillis());
			scheduledTaskMessage.setKey(getKey());
			scheduledTaskMessage.setPageEnd(end);
			scheduledTaskMessage.setPageStart(start);
			scheduledTaskMessage.setPayload(getPayload(userGroups));
			scheduledTaskMessage.setQueryStartTime(queryStartTime);
			scheduledTaskMessage.setResultCount(userGroupCount);

			sendMessage(scheduledTaskMessage);

			start = end;

			end = end + _pageSize;

			if (userGroupCount >= start) {
				pause();
			}
		}
	}

	protected List<Map<String, Object>> getPayload(List<UserGroup> userGroups) {
		List<Map<String, Object>> userGroupMaps = new ArrayList<>();

		for (UserGroup userGroup : userGroups) {
			userGroupMaps.add(getUserGroupMap(userGroup));
		}

		return userGroupMaps;
	}

	protected Map<String, Object> getUserGroupMap(UserGroup userGroup) {
		Map<String, Object> userGroupMap = new HashMap<>();

		userGroupMap.put("companyId", userGroup.getCompanyId());
		userGroupMap.put("name", userGroup.getName());
		userGroupMap.put("userGroupId", userGroup.getUserGroupId());
		userGroupMap.put("uuid", userGroup.getUuid());

		return userGroupMap;
	}

	protected void pause() {
		if (_pauseInterval == 0) {
			return;
		}

		try {
			Thread.sleep(_pauseInterval);
		}
		catch (InterruptedException ie) {
			_log.error(ie, ie);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupNamesTask.class);

	private int _pageSize;
	private long _pauseInterval;

}