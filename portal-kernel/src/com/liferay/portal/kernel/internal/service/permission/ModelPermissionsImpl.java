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

package com.liferay.portal.kernel.internal.service.permission;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class ModelPermissionsImpl implements ModelPermissions {

	public static final String RESOURCE_NAME_ALL_RESOURCES =
		ModelPermissions.class.getName() + "#ALL_RESOURCES";

	public static final String RESOURCE_NAME_FIRST_RESOURCE =
		ModelPermissions.class.getName() + "#FIRST_RESOURCE";

	public static final String RESOURCE_NAME_UNINITIALIZED =
		ModelPermissions.class.getName() + "#UNINITIALIZED";

	public static boolean isUsed(ModelPermissions modelPermissions) {
		if (modelPermissions instanceof ModelPermissionsImpl) {
			ModelPermissionsImpl modelPermissionsImpl =
				(ModelPermissionsImpl)modelPermissions;

			return modelPermissionsImpl._used;
		}

		return false;
	}

	public static void setUsed(ModelPermissions modelPermissions) {
		if (modelPermissions instanceof ModelPermissionsImpl) {
			ModelPermissionsImpl modelPermissionsImpl =
				(ModelPermissionsImpl)modelPermissions;

			modelPermissionsImpl._used = true;
		}
	}

	public ModelPermissionsImpl() {
	}

	public ModelPermissionsImpl(String resourceName) {
		setResourceName(resourceName);
	}

	@Override
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
	public ModelPermissions clone() {
		return new ModelPermissionsImpl(_actionIdsMap, _resourceName, _used);
	}

	@Override
	public String[] getActionIds(String roleName) {
		Set<String> actionIds = _actionIdsMap.get(roleName);

		if (actionIds == null) {
			return StringPool.EMPTY_ARRAY;
		}

		return actionIds.toArray(new String[0]);
	}

	@Override
	public String getResourceName() {
		return _resourceName;
	}

	@Override
	public Collection<String> getRoleNames() {
		return _actionIdsMap.keySet();
	}

	@Override
	public void setResourceName(String resourceName) {
		_resourceName = Objects.requireNonNull(resourceName);
	}

	private ModelPermissionsImpl(
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