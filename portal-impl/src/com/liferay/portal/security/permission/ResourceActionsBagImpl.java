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

import com.liferay.portal.kernel.security.permission.ResourceActionsBag;

import java.util.HashSet;
import java.util.Set;

/**
 * @author László Csontos
 */
public class ResourceActionsBagImpl implements Cloneable, ResourceActionsBag {

	public ResourceActionsBagImpl() {
	}

	public ResourceActionsBagImpl(ResourceActionsBag resourceActionsBag) {
		_resourceActions.addAll(resourceActionsBag.getResourceActions());
		_resourceGroupDefaultActions.addAll(
			resourceActionsBag.getResourceGroupDefaultActions());
		_resourceGuestDefaultActions.addAll(
			resourceActionsBag.getResourceGuestDefaultActions());
		_resourceGuestUnsupportedActions.addAll(
			resourceActionsBag.getResourceGuestUnsupportedActions());
		_resources.addAll(resourceActionsBag.getResources());
	}

	@Override
	public ResourceActionsBag clone() {
		return new ResourceActionsBagImpl(this);
	}

	@Override
	public Set<String> getResourceActions() {
		return _resourceActions;
	}

	@Override
	public Set<String> getResourceGroupDefaultActions() {
		return _resourceGroupDefaultActions;
	}

	@Override
	public Set<String> getResourceGuestDefaultActions() {
		return _resourceGuestDefaultActions;
	}

	@Override
	public Set<String> getResourceGuestUnsupportedActions() {
		return _resourceGuestUnsupportedActions;
	}

	@Override
	public Set<String> getResources() {
		return _resources;
	}

	private final Set<String> _resourceActions = new HashSet<>();
	private final Set<String> _resourceGroupDefaultActions = new HashSet<>();
	private final Set<String> _resourceGuestDefaultActions = new HashSet<>();
	private final Set<String> _resourceGuestUnsupportedActions =
		new HashSet<>();
	private final Set<String> _resources = new HashSet<>();

}