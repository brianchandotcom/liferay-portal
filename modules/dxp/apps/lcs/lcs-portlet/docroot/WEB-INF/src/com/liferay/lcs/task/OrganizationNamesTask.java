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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class OrganizationNamesTask extends BaseScheduledTask {

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
			_log.trace("Running organization names task");
		}

		int start = 0;
		int end = _pageSize;
		long queryStartTime = System.currentTimeMillis();

		long organizationCount =
			OrganizationLocalServiceUtil.getOrganizationsCount();

		while (start < organizationCount) {
			List<Organization> organizations =
				OrganizationLocalServiceUtil.getOrganizations(start, end);

			ScheduledTaskMessage scheduledTaskMessage =
				new ScheduledTaskMessage();

			scheduledTaskMessage.setCreateTime(System.currentTimeMillis());
			scheduledTaskMessage.setKey(getKey());
			scheduledTaskMessage.setPageEnd(end);
			scheduledTaskMessage.setPageStart(start);
			scheduledTaskMessage.setPayload(getPayload(organizations));
			scheduledTaskMessage.setQueryStartTime(queryStartTime);
			scheduledTaskMessage.setResultCount(organizationCount);

			sendMessage(scheduledTaskMessage);

			start = end;

			end = end + _pageSize;

			if (organizationCount >= start) {
				pause();
			}
		}
	}

	protected Map<String, Object> getOrganizationMap(
		Organization organization) {

		Map<String, Object> organizationMap = new HashMap<>();

		organizationMap.put("companyId", organization.getCompanyId());
		organizationMap.put("name", organization.getName());
		organizationMap.put("organizationId", organization.getOrganizationId());
		organizationMap.put(
			"parentOrganizationId", organization.getParentOrganizationId());
		organizationMap.put("uuid", organization.getUuid());

		return organizationMap;
	}

	protected List<Map<String, Object>> getPayload(
		List<Organization> organizations) {

		List<Map<String, Object>> organizationMaps = new ArrayList<>();

		for (Organization organization : organizations) {
			organizationMaps.add(getOrganizationMap(organization));
		}

		return organizationMaps;
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
		OrganizationNamesTask.class);

	private int _pageSize;
	private long _pauseInterval;

}