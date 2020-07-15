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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissions implements Cloneable, Serializable {

	public static final String RESOURCE_NAME_ALL_RESOURCES =
		ModelPermissions.class.getName() + "#ALL_RESOURCES";

	public static final String RESOURCE_NAME_FIRST_RESOURCE =
		ModelPermissions.class.getName() + "#FIRST_RESOURCE";

	public static final String RESOURCE_NAME_UNINITIALIZED =
		ModelPermissions.class.getName() + "#UNINITIALIZED";

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public ModelPermissions() {
	}

	public ModelPermissions(String resourceName) {
		setResourceName(resourceName);
	}

	public void addRolePermissions(String roleName, String... actionIds) {
		if (ArrayUtil.isEmpty(actionIds)) {
			return;
		}

		Set<String> actionIdSet = _actionIdsMap.get(roleName);

		if (actionIdSet == null) {
			actionIdSet = new HashSet<>();

			_actionIdsMap.put(roleName, actionIdSet);
		}

		Collections.addAll(actionIdSet, actionIds);
	}

	@Override
	public Object clone() {
		return new ModelPermissions(_actionIdsMap, _resourceName, _used);
	}

	public String[] getActionIds(String roleName) {
		Set<String> actionIds = _actionIdsMap.get(roleName);

		if (actionIds == null) {
			return StringPool.EMPTY_ARRAY;
		}

		return actionIds.toArray(new String[0]);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public List<String> getActionIdsList(String roleName) {
		Set<String> actionIds = _actionIdsMap.get(roleName);

		return ListUtil.fromCollection(actionIds);
	}

	public String getResourceName() {
		return _resourceName;
	}

	public Collection<String> getRoleNames() {
		return _actionIdsMap.keySet();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Collection<String> getRoleNames(String actionId) {
		Set<String> roleNames = new HashSet<>();

		for (Map.Entry<String, Set<String>> entry : _actionIdsMap.entrySet()) {
			Set<String> actionIds = entry.getValue();

			if (actionIds.contains(actionId)) {
				roleNames.add(entry.getKey());
			}
		}

		return roleNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean isEmpty() {
		return _actionIdsMap.isEmpty();
	}

	public boolean isUsed() {
		return _used;
	}

	public void setResourceName(String resourceName) {
		_resourceName = Objects.requireNonNull(resourceName);
	}

	public void setUsed(boolean used) {
		_used = used;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	protected ModelPermissions(
		Map<String, Set<String>> roleNamesMap,
		Map<String, Set<String>> actionIdsMap) {

		this(actionIdsMap, RESOURCE_NAME_UNINITIALIZED, false);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	protected ModelPermissions(
		Map<String, Set<String>> roleNamesMap,
		Map<String, Set<String>> actionIdsMap, String resourceName) {

		this(actionIdsMap, resourceName, false);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	protected ModelPermissions(
		Map<String, Set<String>> roleNamesMap,
		Map<String, Set<String>> actionIdsMap, String resourceName,
		boolean used) {

		this(actionIdsMap, resourceName, used);
	}

	private ModelPermissions(
		Map<String, Set<String>> actionIdsMap, String resourceName,
		boolean used) {

		_actionIdsMap.putAll(actionIdsMap);
		_resourceName = Objects.requireNonNull(resourceName);
		_used = used;
	}

	private final Map<String, Set<String>> _actionIdsMap = new HashMap<>();
	private String _resourceName = RESOURCE_NAME_UNINITIALIZED;
	private boolean _used;

}