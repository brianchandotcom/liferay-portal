/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class DDMDisplayRegistryImpl implements DDMDisplayRegistry {

	public DDMDisplay getDDMDisplay(String portletId) {
		DDMDisplay ddmDisplay = _ddmDisplays.get(portletId);

		if (ddmDisplay == null) {
			ddmDisplay = _ddmDisplays.get(PortletKeys.DYNAMIC_DATA_MAPPING);
		}

		return ddmDisplay;
	}

	public List<DDMDisplay> getDDMDisplays() {
		return ListUtil.fromMapValues(_ddmDisplays);
	}

	public String[] getPortletIds() {
		String[] portletIds = new String[_ddmDisplays.size()];

		int i = 0;

		for (Map.Entry<String, DDMDisplay> entry : _ddmDisplays.entrySet()) {
			DDMDisplay ddmDisplay = entry.getValue();

			portletIds[i++] = ddmDisplay.getPortletId();
		}

		return portletIds;
	}

	public void register(DDMDisplay ddmDisplay) {
		_ddmDisplays.put(ddmDisplay.getPortletId(), ddmDisplay);
	}

	public void unregister(DDMDisplay ddmDisplay) {
		_ddmDisplays.remove(ddmDisplay.getPortletId());
	}

	private Map<String, DDMDisplay> _ddmDisplays =
		new HashMap<String, DDMDisplay>();

}