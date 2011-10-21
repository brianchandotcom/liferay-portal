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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ResourceTypePermission;

import java.io.Serializable;

/**
 * The cache model class for representing ResourceTypePermission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermission
 * @generated
 */
public class ResourceTypePermissionCacheModel implements CacheModel<ResourceTypePermission>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{resourceTypePermissionId=");
		sb.append(resourceTypePermissionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append("}");

		return sb.toString();
	}

	public ResourceTypePermission toEntityModel() {
		ResourceTypePermissionImpl resourceTypePermissionImpl = new ResourceTypePermissionImpl();

		resourceTypePermissionImpl.setResourceTypePermissionId(resourceTypePermissionId);
		resourceTypePermissionImpl.setCompanyId(companyId);
		resourceTypePermissionImpl.setGroupId(groupId);

		if (name == null) {
			resourceTypePermissionImpl.setName(StringPool.BLANK);
		}
		else {
			resourceTypePermissionImpl.setName(name);
		}

		resourceTypePermissionImpl.setRoleId(roleId);
		resourceTypePermissionImpl.setActionIds(actionIds);

		resourceTypePermissionImpl.resetOriginalValues();

		return resourceTypePermissionImpl;
	}

	public long resourceTypePermissionId;
	public long companyId;
	public long groupId;
	public String name;
	public long roleId;
	public long actionIds;
}