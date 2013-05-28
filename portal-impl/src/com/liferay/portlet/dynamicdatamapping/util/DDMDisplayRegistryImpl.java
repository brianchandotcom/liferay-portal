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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eduardo Garcia
 */
public class DDMDisplayRegistryImpl implements DDMDisplayRegistry {

	@Override
	public DDMDisplay getDDMDisplay(String portletId) {
		return _ddmDisplays.get(portletId);
	}

	@Override
	public List<DDMDisplay> getDDMDisplays() {
		return ListUtil.fromMapValues(_ddmDisplays);
	}

	@Override
	public String[] getPortletIds() {
		Set<String> portletIds = _ddmDisplays.keySet();

		return portletIds.toArray(new String[portletIds.size()]);
	}

	@Override
	public void register(DDMDisplay ddmDisplay) {
		_ddmDisplays.put(ddmDisplay.getPortletId(), ddmDisplay);
	}

	@Override
	public void unregister(DDMDisplay ddmDisplay) {
		_ddmDisplays.remove(ddmDisplay.getPortletId());
	}

	private Map<String, DDMDisplay> _ddmDisplays =
		new HashMap<String, DDMDisplay>();

}