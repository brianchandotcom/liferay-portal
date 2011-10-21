/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.OrgGroupRole;

import java.io.Serializable;

/**
 * The cache model class for representing OrgGroupRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRole
 * @generated
 */
public class OrgGroupRoleCacheModel implements CacheModel<OrgGroupRole>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{organizationId=");
		sb.append(organizationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append("}");

		return sb.toString();
	}

	public OrgGroupRole toEntityModel() {
		OrgGroupRoleImpl orgGroupRoleImpl = new OrgGroupRoleImpl();

		orgGroupRoleImpl.setOrganizationId(organizationId);
		orgGroupRoleImpl.setGroupId(groupId);
		orgGroupRoleImpl.setRoleId(roleId);

		orgGroupRoleImpl.resetOriginalValues();

		return orgGroupRoleImpl;
	}

	public long organizationId;
	public long groupId;
	public long roleId;
}