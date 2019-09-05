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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = DDMDisplayRegistry.class)
public class DDMDisplayRegistry {

	public DDMDisplay getDDMDisplay(String portletId) {
		return _getDDMDisplay(portletId);
	}

	public List<DDMDisplay> getDDMDisplays() {
		return _getDDMDisplays();
	}

	public String[] getPortletIds() {
		return _getPortletIds();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setDDMDisplay(DDMDisplay ddmDisplay) {
		_ddmDisplays.put(ddmDisplay.getPortletId(), ddmDisplay);
	}

	protected void unsetDDMDisplay(DDMDisplay ddmDisplay) {
		_ddmDisplays.remove(ddmDisplay.getPortletId());
	}

	private DDMDisplay _getDDMDisplay(String portletId) {
		return _ddmDisplays.get(portletId);
	}

	private List<DDMDisplay> _getDDMDisplays() {
		return ListUtil.fromMapValues(_ddmDisplays);
	}

	private String[] _getPortletIds() {
		Set<String> portletIds = _ddmDisplays.keySet();

		return portletIds.toArray(new String[0]);
	}

	private final Map<String, DDMDisplay> _ddmDisplays =
		new ConcurrentHashMap<>();

}