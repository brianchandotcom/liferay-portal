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

package com.liferay.portal.kernel.portlet.toolbar.internal;

import com.liferay.portal.kernel.portlet.toolbar.PortletToolbar;
import com.liferay.portal.kernel.portlet.toolbar.item.AddContentPortletToolbarItem;
import com.liferay.portal.kernel.portlet.toolbar.item.locator.AddContentPortletToolbarItemLocator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class DefaultPortletToolbar implements PortletToolbar {

	public DefaultPortletToolbar() {
		_addContentPortletToolbarItemLocators = ServiceTrackerCollections.list(
			AddContentPortletToolbarItemLocator.class);
	}

	@Override
	public List<AddContentPortletToolbarItem> getAddContentPortletToolbarItems(
		PortletRequest portletRequest) {

		String portletId = PortalUtil.getPortletId(portletRequest);

		List<AddContentPortletToolbarItem> addContentPortletToolbarItems =
			new ArrayList<>();

		for (AddContentPortletToolbarItemLocator
				addContentPortletToolbarItemLocator :
					_addContentPortletToolbarItemLocators) {

			List<AddContentPortletToolbarItem>
				curAddContentPortletToolbarItems =
					addContentPortletToolbarItemLocator.getPortletToolbarItems(
						portletId, portletRequest);

			if (curAddContentPortletToolbarItems != null) {
				addContentPortletToolbarItems.addAll(
					curAddContentPortletToolbarItems);
			}
		}

		return addContentPortletToolbarItems;
	}

	private final ServiceTrackerList<AddContentPortletToolbarItemLocator>
		_addContentPortletToolbarItemLocators;

}