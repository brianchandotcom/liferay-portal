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

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ResourceTypePermission;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ResourceTypePermission in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermission
 * @generated
 */
@ProviderType
public class ResourceTypePermissionCacheModel implements CacheModel<ResourceTypePermission>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceTypePermissionCacheModel)) {
			return false;
		}

		ResourceTypePermissionCacheModel resourceTypePermissionCacheModel = (ResourceTypePermissionCacheModel)obj;

		if ((resourceTypePermissionId == resourceTypePermissionCacheModel.resourceTypePermissionId) &&
				(mvccVersion == resourceTypePermissionCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, resourceTypePermissionId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", resourceTypePermissionId=");
		sb.append(resourceTypePermissionId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ResourceTypePermission toEntityModel() {
		ResourceTypePermissionImpl resourceTypePermissionImpl = new ResourceTypePermissionImpl();

		resourceTypePermissionImpl.setMvccVersion(mvccVersion);
		resourceTypePermissionImpl.setResourceTypePermissionId(resourceTypePermissionId);
		resourceTypePermissionImpl.setGroupId(groupId);

		if (name == null) {
			resourceTypePermissionImpl.setName(StringPool.BLANK);
		}
		else {
			resourceTypePermissionImpl.setName(name);
		}

		resourceTypePermissionImpl.setRoleId(roleId);
		resourceTypePermissionImpl.setActionIds(actionIds);
		resourceTypePermissionImpl.setCompanyId(companyId);

		resourceTypePermissionImpl.resetOriginalValues();

		return resourceTypePermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		resourceTypePermissionId = objectInput.readLong();
		groupId = objectInput.readLong();
		name = objectInput.readUTF();
		roleId = objectInput.readLong();
		actionIds = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(resourceTypePermissionId);
		objectOutput.writeLong(groupId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(roleId);
		objectOutput.writeLong(actionIds);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long resourceTypePermissionId;
	public long groupId;
	public String name;
	public long roleId;
	public long actionIds;
	public long companyId;
}