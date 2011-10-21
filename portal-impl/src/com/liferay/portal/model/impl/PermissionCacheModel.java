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
import com.liferay.portal.model.Permission;

import java.io.Serializable;

/**
 * The cache model class for representing Permission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Permission
 * @generated
 */
public class PermissionCacheModel implements CacheModel<Permission>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{permissionId=");
		sb.append(permissionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append(", resourceId=");
		sb.append(resourceId);
		sb.append("}");

		return sb.toString();
	}

	public Permission toEntityModel() {
		PermissionImpl permissionImpl = new PermissionImpl();

		permissionImpl.setPermissionId(permissionId);
		permissionImpl.setCompanyId(companyId);

		if (actionId == null) {
			permissionImpl.setActionId(StringPool.BLANK);
		}
		else {
			permissionImpl.setActionId(actionId);
		}

		permissionImpl.setResourceId(resourceId);

		permissionImpl.resetOriginalValues();

		return permissionImpl;
	}

	public long permissionId;
	public long companyId;
	public String actionId;
	public long resourceId;
}