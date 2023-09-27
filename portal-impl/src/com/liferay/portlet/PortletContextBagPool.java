/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextBagPool {

	public static void clear() {
		_portletContextBagPool._portletContextBagMap.clear();
	}

	public static PortletContextBag get(String servletContextName) {
		return _portletContextBagPool._get(servletContextName);
	}

	public static void put(
		String servletContextName, PortletContextBag portletContextBag) {

		_portletContextBagPool._put(servletContextName, portletContextBag);
	}

	public static PortletContextBag remove(String servletContextName) {
		return _portletContextBagPool._remove(servletContextName);
	}

	private PortletContextBagPool() {
	}

	private PortletContextBag _get(String servletContextName) {
		return _portletContextBagMap.get(servletContextName);
	}

	private void _put(
		String servletContextName, PortletContextBag portletContextBag) {

		_portletContextBagMap.put(servletContextName, portletContextBag);
	}

	private PortletContextBag _remove(String servletContextName) {
		return _portletContextBagMap.remove(servletContextName);
	}

	private static final PortletContextBagPool _portletContextBagPool =
		new PortletContextBagPool();

	private final Map<String, PortletContextBag> _portletContextBagMap =
		new ConcurrentHashMap<>();

}