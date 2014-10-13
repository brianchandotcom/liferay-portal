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

package com.liferay.portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextBagPool {

	public static void clear() {
		_instance._portletContextBagPool.clear();
	}

	public static PortletContextBag get(String servletContextName) {
		return _instance._get(servletContextName);
	}

	public static void put(
		String servletContextName, PortletContextBag portletContextBag) {

		_instance._put(servletContextName, portletContextBag);
	}

	public static PortletContextBag remove(String servletContextName) {
		return _instance._remove(servletContextName);
	}

	private PortletContextBagPool() {
		_portletContextBagPool =
			new ConcurrentHashMap<String, PortletContextBag>();
	}

	private PortletContextBag _get(String servletContextName) {
		return _portletContextBagPool.get(servletContextName);
	}

	private void _put(
		String servletContextName, PortletContextBag portletContextBag) {

		_portletContextBagPool.put(servletContextName, portletContextBag);
	}

	private PortletContextBag _remove(String servletContextName) {
		return _portletContextBagPool.remove(servletContextName);
	}

	private static final PortletContextBagPool _instance =
		new PortletContextBagPool();

	private final Map<String, PortletContextBag> _portletContextBagPool;

}