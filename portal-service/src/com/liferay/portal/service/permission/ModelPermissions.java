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

package com.liferay.portal.service.permission;

import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissions implements Serializable {

	public void addRolePermissions(Role role, String actionId) {
		List<Role> roles = getRoles(actionId);

		roles.add(role);

		List<String> actionIds = getActionIds(role);

		actionIds.add(actionId);

		_roles.add(role);
	}

	public void addRolePermissions(Role role, String[] actionIds) {
		for (String actionId : actionIds) {
			addRolePermissions(role, actionId);
		}
	}

	public List<String> getActionIds(Role role) {
		List<String> actionIds = _rolesMap.get(role.getName());

		if (actionIds == null) {
			actionIds = new ArrayList<>();

			_rolesMap.put(role.getName(), actionIds);
		}

		return actionIds;
	}

	public Collection<Role> getRoles() {
		return _roles;
	}

	public List<Role> getRoles(String actionId) {
		List<Role> roles = _actionsMap.get(actionId);

		if (roles == null) {
			roles = new ArrayList<>();

			_actionsMap.put(actionId, roles);
		}

		return roles;
	}

	public boolean isEmpty() {
		return _roles.isEmpty();
	}

	private final Map<String, List<Role>> _actionsMap = new HashMap<>();
	private final Set<Role> _roles = new HashSet<>();
	private final Map<String, List<String>> _rolesMap = new HashMap<>();

}