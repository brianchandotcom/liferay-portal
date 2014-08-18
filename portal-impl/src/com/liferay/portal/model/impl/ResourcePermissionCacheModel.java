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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ResourcePermission;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ResourcePermission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermission
 * @generated
 */
@ProviderType
public class ResourcePermissionCacheModel implements CacheModel<ResourcePermission>,
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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", resourcePermissionId=");
		sb.append(resourcePermissionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", scope=");
		sb.append(scope);
		sb.append(", primKey=");
		sb.append(primKey);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ResourcePermission toEntityModel() {
		ResourcePermissionImpl resourcePermissionImpl = new ResourcePermissionImpl();

		resourcePermissionImpl.setMvccVersion(mvccVersion);
		resourcePermissionImpl.setResourcePermissionId(resourcePermissionId);
		resourcePermissionImpl.setCompanyId(companyId);

		if (name == null) {
			resourcePermissionImpl.setName(StringPool.BLANK);
		}
		else {
			resourcePermissionImpl.setName(name);
		}

		resourcePermissionImpl.setScope(scope);

		if (primKey == null) {
			resourcePermissionImpl.setPrimKey(StringPool.BLANK);
		}
		else {
			resourcePermissionImpl.setPrimKey(primKey);
		}

		resourcePermissionImpl.setRoleId(roleId);
		resourcePermissionImpl.setOwnerId(ownerId);
		resourcePermissionImpl.setActionIds(actionIds);

		resourcePermissionImpl.resetOriginalValues();

		return resourcePermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		resourcePermissionId = objectInput.readLong();
		companyId = objectInput.readLong();
		name = objectInput.readUTF();
		scope = objectInput.readInt();
		primKey = objectInput.readUTF();
		roleId = objectInput.readLong();
		ownerId = objectInput.readLong();
		actionIds = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(resourcePermissionId);
		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(scope);

		if (primKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(primKey);
		}

		objectOutput.writeLong(roleId);
		objectOutput.writeLong(ownerId);
		objectOutput.writeLong(actionIds);
	}

	public long mvccVersion;
	public long resourcePermissionId;
	public long companyId;
	public String name;
	public int scope;
	public String primKey;
	public long roleId;
	public long ownerId;
	public long actionIds;
}