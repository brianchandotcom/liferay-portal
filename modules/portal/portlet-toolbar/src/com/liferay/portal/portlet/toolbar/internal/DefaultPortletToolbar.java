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

package com.liferay.portal.portlet.toolbar.internal;

import com.liferay.portal.kernel.portlet.toolbar.PortletToolbar;
import com.liferay.portal.kernel.portlet.toolbar.item.PortletToolbarMenuItem;
import com.liferay.portal.kernel.portlet.toolbar.item.locator.PortletToolbarMenuItemLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class DefaultPortletToolbar implements PortletToolbar {

	@Override
	public List<PortletToolbarMenuItem> getContentAdditionMenuItems(
		String portletId, PortletRequest portletRequest) {

		List<PortletToolbarMenuItem> portletToolbarMenuItems =
			new ArrayList<>();

		for (PortletToolbarMenuItemLocator portletToolbarMenuItemLocator :
				_portletToolbarMenuItemLocators) {

			List<PortletToolbarMenuItem>
				curPortletToolbarMenuItems =
					portletToolbarMenuItemLocator.getPortletToolbarMenuItems(
						portletId, portletRequest);

			if (curPortletToolbarMenuItems != null) {
				portletToolbarMenuItems.addAll(curPortletToolbarMenuItems);
			}
		}

		return portletToolbarMenuItems;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removePortletToolbarMenuItemLocator")
	protected void addPortletToolbarMenuItemLocator(
		PortletToolbarMenuItemLocator portletToolbarMenuItemLocator) {

		_portletToolbarMenuItemLocators.add(portletToolbarMenuItemLocator);
	}

	protected void removePortletToolbarMenuItemLocator(
		PortletToolbarMenuItemLocator portletToolbarMenuItemLocator) {

		_portletToolbarMenuItemLocators.remove(portletToolbarMenuItemLocator);
	}

	private final List<PortletToolbarMenuItemLocator>
		_portletToolbarMenuItemLocators = new CopyOnWriteArrayList<>();

}