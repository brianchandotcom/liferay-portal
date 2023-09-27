/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletBagPool {

	public static PortletBag get(String portletId) {
		return _portletBagPool.get(portletId);
	}

	public static void put(String portletId, PortletBag portletBag) {
		_portletBagPool.put(portletId, portletBag);
	}

	public static PortletBag remove(String portletId) {
		return _portletBagPool.remove(portletId);
	}

	public static void reset() {
		_portletBagPool.clear();
	}

	private PortletBagPool() {
	}

	private static final Map<String, PortletBag> _portletBagPool =
		new ConcurrentHashMap<>();

}