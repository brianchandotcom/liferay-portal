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

import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissions implements Cloneable, Serializable {

	public ModelPermissions() {
	}

	public void addRolePermissions(String roleName, String actionId) {
		Set<String> roleNames = _actionsMap.get(actionId);

		if (roleNames == null) {
			roleNames = new HashSet<>();

			_actionsMap.put(actionId, roleNames);
		}

		roleNames.add(roleName);

		Set<String> actionIds = _roleNamesMap.get(roleName);

		if (actionIds == null) {
			actionIds = new HashSet<>();

			_roleNamesMap.put(roleName, actionIds);
		}

		actionIds.add(actionId);
	}

	public void addRolePermissions(String roleName, String[] actionIds) {
		if (actionIds == null) {
			return;
		}

		for (String actionId : actionIds) {
			addRolePermissions(roleName, actionId);
		}
	}

	@Override
	public Object clone() {
		return new ModelPermissions(
			(HashMap)_actionsMap.clone(), (HashMap)_roleNamesMap.clone());
	}

	public String[] getActionIds(String roleName) {
		List<String> actionIds = getActionIdsList(roleName);

		return actionIds.toArray(new String[actionIds.size()]);
	}

	public List getActionIdsList(String roleName) {
		Set<String> actionIds = _roleNamesMap.get(roleName);

		return ListUtil.fromCollection(actionIds);
	}

	public Collection<String> getRoleNames() {
		return _roleNamesMap.keySet();
	}

	public Set<String> getRolesWithPermission(String actionId) {
		Set<String> roles = _actionsMap.get(actionId);

		if (roles == null) {
			roles = new HashSet<>();
		}

		return roles;
	}

	public boolean isEmpty() {
		return _roleNamesMap.isEmpty();
	}

	protected ModelPermissions(HashMap actionsMap, HashMap roleNamesMap) {
		_actionsMap.putAll(actionsMap);
		_roleNamesMap.putAll(roleNamesMap);
	}

	private final HashMap<String, Set<String>> _actionsMap = new HashMap<>();
	private final HashMap<String, Set<String>> _roleNamesMap = new HashMap<>();

}