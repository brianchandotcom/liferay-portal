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

package com.liferay.server.admin.web.util;

import com.liferay.portal.kernel.util.GetterUtil;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Preston Crary
 */
public class CacheStatistics {

	public CacheStatistics(MBeanServer mBeanServer, ObjectName objectName)
		throws JMException {

		_name = objectName.getKeyProperty("name");
		_objectCount = GetterUtil.getInteger(
			mBeanServer.getAttribute(objectName, "ObjectCount"));
		_cacheHits = GetterUtil.getInteger(
			mBeanServer.getAttribute(objectName, "CacheHits"));
		_cacheHitPercentage = GetterUtil.getDouble(
			mBeanServer.getAttribute(objectName, "CacheHitPercentage"));
		_cacheMisses = GetterUtil.getInteger(
			mBeanServer.getAttribute(objectName, "CacheMisses"));
		_cacheMissPercentage = GetterUtil.getDouble(
			mBeanServer.getAttribute(objectName, "CacheMissPercentage"));
	}

	public double getCacheHitPercentage() {
		return _cacheHitPercentage;
	}

	public int getCacheHits() {
		return _cacheHits;
	}

	public int getCacheMisses() {
		return _cacheMisses;
	}

	public double getCacheMissPercentage() {
		return _cacheMissPercentage;
	}

	public String getName() {
		return _name;
	}

	public int getObjectCount() {
		return _objectCount;
	}

	private final double _cacheHitPercentage;
	private final int _cacheHits;
	private final int _cacheMisses;
	private final double _cacheMissPercentage;
	private final String _name;
	private final int _objectCount;

}