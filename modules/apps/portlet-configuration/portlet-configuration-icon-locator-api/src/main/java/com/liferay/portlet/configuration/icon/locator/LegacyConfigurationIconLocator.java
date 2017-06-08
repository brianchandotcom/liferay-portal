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

package com.liferay.portlet.configuration.icon.locator;

import com.liferay.portal.kernel.portlet.configuration.icon.locator.PortletConfigurationIconLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortletConfigurationIconLocator.class)
public class LegacyConfigurationIconLocator
	implements PortletConfigurationIconLocator {

	@Override
	public List<String> getDefaultViews(String portletId) {
		List<String> defaultViews = new ArrayList<>();

		Portlet portlet = _portletLocalService.getPortletById(portletId);

		if (portlet == null) {
			return defaultViews;
		}

		Map<String, String> initParams = portlet.getInitParams();

		boolean alwaysDisplayDefaultConfigurationIcons = GetterUtil.getBoolean(
			initParams.get("always-display-default-configuration-icons"));

		if (alwaysDisplayDefaultConfigurationIcons) {
			defaultViews.add(StringPool.DASH);
		}

		return defaultViews;
	}

	@Override
	public String getPath(PortletRequest portletRequest) {
		return StringPool.BLANK;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private PortletLocalService _portletLocalService;

}