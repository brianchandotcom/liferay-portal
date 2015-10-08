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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerBagImpl
	extends UserPermissionCheckerBagImpl implements PermissionCheckerBag {

	public PermissionCheckerBagImpl(long userId, Set<Role> roles) {
		super(
			userId, Collections.<Group>emptySet(),
			Collections.<Organization>emptyList(),
			Collections.<Group>emptySet(), Collections.<Group>emptyList(),
			roles);
	}

	public PermissionCheckerBagImpl(
		UserPermissionCheckerBag userPermissionCheckerBag, Set<Role> roles) {

		super(userPermissionCheckerBag, roles);
	}

	@Override
	public long[] getRoleIds() {
		if (_roleIds == null) {
			List<Role> roles = ListUtil.fromCollection(getRoles());

			long[] roleIds = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				Role role = roles.get(i);

				roleIds[i] = role.getRoleId();
			}

			Arrays.sort(roleIds);

			_roleIds = roleIds;
		}

		return _roleIds;
	}

	private long[] _roleIds;

}