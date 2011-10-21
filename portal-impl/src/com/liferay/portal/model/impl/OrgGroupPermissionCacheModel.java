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
import com.liferay.portal.model.OrgGroupPermission;

import java.io.Serializable;

/**
 * The cache model class for representing OrgGroupPermission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupPermission
 * @generated
 */
public class OrgGroupPermissionCacheModel implements CacheModel<OrgGroupPermission>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{organizationId=");
		sb.append(organizationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", permissionId=");
		sb.append(permissionId);
		sb.append("}");

		return sb.toString();
	}

	public OrgGroupPermission toEntityModel() {
		OrgGroupPermissionImpl orgGroupPermissionImpl = new OrgGroupPermissionImpl();

		orgGroupPermissionImpl.setOrganizationId(organizationId);
		orgGroupPermissionImpl.setGroupId(groupId);
		orgGroupPermissionImpl.setPermissionId(permissionId);

		orgGroupPermissionImpl.resetOriginalValues();

		return orgGroupPermissionImpl;
	}

	public long organizationId;
	public long groupId;
	public long permissionId;
}