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

package com.liferay.portal.cache.multiple.internal;

import com.liferay.portal.cache.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEvent implements Serializable {

	public PortalCacheClusterEvent(
		String portalCacheManagerName, String portalCacheName,
		Serializable elementKey,
		PortalCacheClusterEventType portalCacheClusterEventType) {

		this(
			portalCacheManagerName, portalCacheName, elementKey, null,
			PortalCache.DEFAULT_TIME_TO_LIVE, portalCacheClusterEventType);
	}

	public PortalCacheClusterEvent(
		String portalCacheManagerName, String portalCacheName,
		Serializable elementKey, Serializable elementValue, int timeToLive,
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

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		_portalCacheManagerName = portalCacheManagerName;
		_portalCacheName = portalCacheName;
		_elementKey = new SerializableObjectWrapper(elementKey);
		_elementValue = new SerializableObjectWrapper(elementValue);
		_timeToLive = timeToLive;
		_portalCacheClusterEventType = portalCacheClusterEventType;
	}

	public Serializable getElementKey() {
		return SerializableObjectWrapper.unwrap(_elementKey);
	}

	public Serializable getElementValue() {
		return SerializableObjectWrapper.unwrap(_elementValue);
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

	public void setElementValue(Serializable elementValue) {
		_elementValue = new SerializableObjectWrapper(elementValue);
	}

	public void setTimeToLive(int timeToLive) {
		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		_timeToLive = timeToLive;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append(_portalCacheManagerName);
		sb.append(StringPool.COLON);
		sb.append(_portalCacheName);
		sb.append(StringPool.COLON);
		sb.append(_elementKey);
		sb.append(StringPool.COLON);

		if (_elementValue != null) {
			sb.append(_elementValue.toString());
			sb.append(StringPool.COLON);
		}

		sb.append(_portalCacheClusterEventType.toString());

		return sb.toString();
	}

	private final SerializableObjectWrapper _elementKey;
	private SerializableObjectWrapper _elementValue;
	private final PortalCacheClusterEventType _portalCacheClusterEventType;
	private final String _portalCacheManagerName;
	private final String _portalCacheName;
	private int _timeToLive;

}