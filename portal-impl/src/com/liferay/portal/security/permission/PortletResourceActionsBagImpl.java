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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.security.permission.PortletResourceActionsBag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author László Csontos
 */
public class PortletResourceActionsBagImpl
	extends ResourceActionsBagImpl implements PortletResourceActionsBag {

	public PortletResourceActionsBagImpl() {
	}

	public PortletResourceActionsBagImpl(
		PortletResourceActionsBag portletResourceActionsBag) {

		super(portletResourceActionsBag);

		_portletRootModelResources.putAll(
			portletResourceActionsBag.getPortletRootModelResources());
		_resourceLayoutManagerActions.addAll(
			portletResourceActionsBag.getResourceLayoutManagerActions());
	}

	@Override
	public PortletResourceActionsBag clone() {
		return new PortletResourceActionsBagImpl(this);
	}

	@Override
	public Map<String, String> getPortletRootModelResources() {
		return _portletRootModelResources;
	}

	@Override
	public Set<String> getResourceLayoutManagerActions() {
		return _resourceLayoutManagerActions;
	}

	private final Map<String, String> _portletRootModelResources =
		new HashMap<>();
	private final Set<String> _resourceLayoutManagerActions = new HashSet<>();

}