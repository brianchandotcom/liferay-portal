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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerHeader implements Serializable {

	public PollerHeader(
		long companyId, long userId, long browserKey,
		Map<String, Boolean> portletIdsMap, boolean startPolling) {

		_companyId = companyId;
		_userId = userId;
		_browserKey = browserKey;
		_portletIdsMap = portletIdsMap;
		_startPolling = startPolling;
	}

	public long getBrowserKey() {
		return _browserKey;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Map<String, Boolean> getPortletIdsMap() {
		return _portletIdsMap;
	}

	public long getTimestamp() {
		return _TIMESTAMP;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isStartPolling() {
		return _startPolling;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{_browserKey=");
		sb.append(_browserKey);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", portletIdsMap=");
		sb.append(_portletIdsMap);
		sb.append(", startPolling=");
		sb.append(_startPolling);
		sb.append(", timestamp=");
		sb.append(_TIMESTAMP);
		sb.append(", userId=");
		sb.append(_userId);
		sb.append("}");

		return sb.toString();
	}

	private static final long _TIMESTAMP = System.currentTimeMillis();

	private static final long serialVersionUID = 1L;

	private final long _browserKey;
	private final long _companyId;
	private final Map<String, Boolean> _portletIdsMap;
	private final boolean _startPolling;
	private final long _userId;

}