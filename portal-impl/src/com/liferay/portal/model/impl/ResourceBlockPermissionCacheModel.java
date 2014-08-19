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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ResourceBlockPermission;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ResourceBlockPermission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermission
 * @generated
 */
@ProviderType
public class ResourceBlockPermissionCacheModel implements CacheModel<ResourceBlockPermission>,
	Externalizable, MVCCModel {
	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", resourceBlockPermissionId=");
		sb.append(resourceBlockPermissionId);
		sb.append(", resourceBlockId=");
		sb.append(resourceBlockId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ResourceBlockPermission toEntityModel() {
		ResourceBlockPermissionImpl resourceBlockPermissionImpl = new ResourceBlockPermissionImpl();

		resourceBlockPermissionImpl.setMvccVersion(mvccVersion);
		resourceBlockPermissionImpl.setResourceBlockPermissionId(resourceBlockPermissionId);
		resourceBlockPermissionImpl.setResourceBlockId(resourceBlockId);
		resourceBlockPermissionImpl.setRoleId(roleId);
		resourceBlockPermissionImpl.setActionIds(actionIds);

		resourceBlockPermissionImpl.resetOriginalValues();

		return resourceBlockPermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		resourceBlockPermissionId = objectInput.readLong();
		resourceBlockId = objectInput.readLong();
		roleId = objectInput.readLong();
		actionIds = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(resourceBlockPermissionId);
		objectOutput.writeLong(resourceBlockId);
		objectOutput.writeLong(roleId);
		objectOutput.writeLong(actionIds);
	}

	public long mvccVersion;
	public long resourceBlockPermissionId;
	public long resourceBlockId;
	public long roleId;
	public long actionIds;
}