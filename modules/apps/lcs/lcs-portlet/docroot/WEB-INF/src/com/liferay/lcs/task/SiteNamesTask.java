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
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class SiteNamesTask implements ScheduledTask {

	@Override
	public Type getType() {
		return Type.MEMORY_CLUSTERED;
	}

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public void setPauseInterval(long pauseInterval) {
		_pauseInterval = pauseInterval;
	}

	protected void doRun() throws Exception {
		if (!_lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Waiting for LCS connection manager");
			}

			return;
		}

		int start = 0;
		int end = _pageSize;
		long queryStartTime = System.currentTimeMillis();

		long groupCount = GroupLocalServiceUtil.dynamicQueryCount(
			getGroupDynamicQuery());

		while (start < groupCount) {
			List<Group> groups = GroupLocalServiceUtil.dynamicQuery(
				getGroupDynamicQuery(), start, end);

			ScheduledTaskMessage scheduledTaskMessage =
				new ScheduledTaskMessage();

			scheduledTaskMessage.setCreateTime(System.currentTimeMillis());
			scheduledTaskMessage.setKey(_keyGenerator.getKey());
			scheduledTaskMessage.setPageEnd(end);
			scheduledTaskMessage.setPageStart(start);
			scheduledTaskMessage.setPayload(getPayload(groups));
			scheduledTaskMessage.setQueryStartTime(queryStartTime);
			scheduledTaskMessage.setResultCount(groupCount);

			if (_lcsConnectionManager.isReady()) {
				_lcsConnectionManager.sendMessage(scheduledTaskMessage);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Waiting for LCS connection manager");
				}

				return;
			}

			start = end;

			end = end + _pageSize;

			if (groupCount >= start) {
				pause();
			}
		}
	}

	protected DynamicQuery getGroupDynamicQuery() {
		DynamicQuery dynamicQuery = GroupLocalServiceUtil.dynamicQuery();

		Criterion siteCriterion = RestrictionsFactoryUtil.eq("site", true);

		dynamicQuery.add(siteCriterion);

		Criterion activeCriterion = RestrictionsFactoryUtil.eq("active", true);

		dynamicQuery.add(activeCriterion);

		return dynamicQuery;
	}

	protected Map<String, Object> getGroupMap(Group group) {
		Map<String, Object> groupMap = new HashMap<>();

		groupMap.put("companyId", group.getCompanyId());
		groupMap.put("friendlyURL", group.getFriendlyURL());
		groupMap.put("groupId", group.getGroupId());
		groupMap.put("groupKey", group.getGroupKey());
		groupMap.put("name", group.getName());
		groupMap.put("uuid", group.getUuid());

		return groupMap;
	}

	protected List<Map<String, Object>> getPayload(List<Group> groups) {
		List<Map<String, Object>> groupMaps = new ArrayList<>();

		for (Group group : groups) {
			groupMaps.add(getGroupMap(group));
		}

		return groupMaps;
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

	private static Log _log = LogFactoryUtil.getLog(SiteNamesTask.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;
	private int _pageSize;
	private long _pauseInterval;

}