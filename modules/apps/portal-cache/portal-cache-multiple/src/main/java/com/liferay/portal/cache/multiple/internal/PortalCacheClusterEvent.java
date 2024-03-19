/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEvent {

	public PortalCacheClusterEvent(
		String portalCacheManagerName, String portalCacheName,
		Serializable elementKey,
		PortalCacheClusterEventType portalCacheClusterEventType) {

		if (portalCacheManagerName == null) {
			throw new NullPointerException("Portal cache manager name is null");
		}

		if (portalCacheName == null) {
			throw new NullPointerException("Portal cache name is null");
		}

		if (portalCacheClusterEventType == null) {
			throw new NullPointerException(
				"Portal cache cluster event type is null");
		}

		if ((elementKey == null) &&
			!portalCacheClusterEventType.equals(
				PortalCacheClusterEventType.REMOVE_ALL)) {

			throw new NullPointerException("Element key is null");
		}

		_portalCacheManagerName = portalCacheManagerName;
		_portalCacheName = portalCacheName;
		_elementKey = elementKey;
		_portalCacheClusterEventType = portalCacheClusterEventType;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Serializable getElementKey() {
		return _elementKey;
	}

	public Serializable getElementValue() {
		return _elementValue;
	}

	public PortalCacheClusterEventType getEventType() {
		return _portalCacheClusterEventType;
	}

	public String getPortalCacheManagerName() {
		return _portalCacheManagerName;
	}

	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public int getTimeToLive() {
		return _timeToLive;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setElementValue(Serializable elementValue) {
		_elementValue = elementValue;
	}

	public void setTimeToLive(int timeToLive) {
		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		_timeToLive = timeToLive;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{companyId=");
		sb.append(_companyId);
		sb.append(", elementKey=");
		sb.append(_elementKey);

		if (_elementValue != null) {
			sb.append(", elementValue=");
			sb.append(_elementValue);
		}

		sb.append(", timeToLive=");
		sb.append(_timeToLive);
		sb.append(", portalCacheClusterEventType=");
		sb.append(_portalCacheClusterEventType);
		sb.append(", portalCacheManagerName=");
		sb.append(_portalCacheManagerName);
		sb.append(", portalCacheName=");
		sb.append(_portalCacheName);
		sb.append("}");

		return sb.toString();
	}

	private long _companyId = Long.MIN_VALUE;
	private final Serializable _elementKey;
	private Serializable _elementValue;
	private final PortalCacheClusterEventType _portalCacheClusterEventType;
	private final String _portalCacheManagerName;
	private final String _portalCacheName;
	private int _timeToLive = PortalCache.DEFAULT_TIME_TO_LIVE;

}