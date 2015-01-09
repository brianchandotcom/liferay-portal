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

import org.osgi.service.component.annotations.*;

/**
 * @author Sergio González
 */
@Component(immediate = true)x
public class DefaultPortletToolbar implements PortletToolbar {

	@Override
	public List<PortletToolbarMenuItem> getContentAdditionMenuItems(
		String portletId, PortletRequest portletRequest) {

		List<PortletToolbarMenuItem> portletToolbarMenuItems =
			new ArrayList<>();

		for (PortletToolbarItemLocator
			portletToolbarItemLocator :
			_portletToolbarItemLocators) {

			List<PortletToolbarMenuItem>
				curPortletToolbarMenuItems =
					portletToolbarItemLocator.getPortletToolbarItems(
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
		unbind = "removePortletToolbarItemLocator")
	protected void addPortletToolbarItemLocator(
		PortletToolbarItemLocator portletToolbarItemLocator) {

		_portletToolbarItemLocators.add(portletToolbarItemLocator);
	}

	protected void removePortletToolbarItemLocator(
		PortletToolbarItemLocator portletToolbarItemLocator) {

		_portletToolbarItemLocators.remove(portletToolbarItemLocator);
	}

	private final List<PortletToolbarItemLocator> _portletToolbarItemLocators =
		new CopyOnWriteArrayList<>();

}