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

package com.liferay.portal.kernel.service.permission;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissions implements Cloneable, Serializable {

	public ModelPermissions() {
	}

	public void addRolePermissions(String roleName, String actionId) {
		Set<String> roleNames = _roleNamesMap.get(actionId);

		if (roleNames == null) {
			roleNames = new HashSet<>();

			_roleNamesMap.put(actionId, roleNames);
		}

		roleNames.add(roleName);

		Set<String> actionIds = _actionIdsMap.get(roleName);

		if (actionIds == null) {
			actionIds = new HashSet<>();

			_actionIdsMap.put(roleName, actionIds);
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
			new HashMap<String, Set<String>>(_roleNamesMap),
			new HashMap<String, Set<String>>(_actionIdsMap));
	}

	public String[] getActionIds(String roleName) {
		Set<String> actionIds = _actionIdsMap.get(roleName);

		if (actionIds == null) {
			return StringPool.EMPTY_ARRAY;
		}

		return actionIds.toArray(new String[actionIds.size()]);
	}

	public List<String> getActionIdsList(String roleName) {
		Set<String> actionIds = _actionIdsMap.get(roleName);

		return ListUtil.fromCollection(actionIds);
	}

	public Collection<String> getRoleNames() {
		return _actionIdsMap.keySet();
	}

	public Collection<String> getRoleNames(String actionId) {
		Set<String> roleNames = _roleNamesMap.get(actionId);

		if (roleNames == null) {
			roleNames = new HashSet<>();
		}

		return roleNames;
	}

	public boolean isEmpty() {
		return _actionIdsMap.isEmpty();
	}

	protected ModelPermissions(
		Map<String, Set<String>> roleNamesMap,
		Map<String, Set<String>> actionIdsMap) {

		_roleNamesMap.putAll(roleNamesMap);
		_actionIdsMap.putAll(actionIdsMap);
	}

	private final Map<String, Set<String>> _actionIdsMap = new HashMap<>();
	private final Map<String, Set<String>> _roleNamesMap = new HashMap<>();

}