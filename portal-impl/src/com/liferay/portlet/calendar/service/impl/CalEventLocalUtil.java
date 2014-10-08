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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.calendar.model.CalEvent;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class CalEventLocalUtil {

	protected static void clearEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		_portalCache.remove(key);
	}

	protected static Map<String, List<CalEvent>> getEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		Map<String, List<CalEvent>> eventsPool =
			(ConcurrentHashMap<String, List<CalEvent>>)_portalCache.get(key);

		if (eventsPool == null) {
			eventsPool = new ConcurrentHashMap<String, List<CalEvent>>();

			_portalCache.put(key, (Serializable)eventsPool);
		}

		return eventsPool;
	}

	private static String _encodeKey(long groupId) {
		return _CACHE_NAME.concat(StringPool.POUND).concat(
			StringUtil.toHexString(groupId));
	}

	private static final String _CACHE_NAME = CalEventLocalUtil.class.getName();

	private static final PortalCache<String, Serializable> _portalCache =
		MultiVMPoolUtil.getCache(_CACHE_NAME);

}