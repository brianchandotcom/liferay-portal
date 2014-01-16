/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.ResourceBlock;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ResourceBlock in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlock
 * @generated
 */
public class ResourceBlockCacheModel implements CacheModel<ResourceBlock>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{resourceBlockId=");
		sb.append(resourceBlockId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", permissionsHash=");
		sb.append(permissionsHash);
		sb.append(", referenceCount=");
		sb.append(referenceCount);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ResourceBlock toEntityModel() {
		ResourceBlockImpl resourceBlockImpl = new ResourceBlockImpl();

		resourceBlockImpl.setResourceBlockId(resourceBlockId);
		resourceBlockImpl.setCompanyId(companyId);
		resourceBlockImpl.setGroupId(groupId);

		if (name == null) {
			resourceBlockImpl.setName(StringPool.BLANK);
		}
		else {
			resourceBlockImpl.setName(name);
		}

		if (permissionsHash == null) {
			resourceBlockImpl.setPermissionsHash(StringPool.BLANK);
		}
		else {
			resourceBlockImpl.setPermissionsHash(permissionsHash);
		}

		resourceBlockImpl.setReferenceCount(referenceCount);
		resourceBlockImpl.setMvccVersion(mvccVersion);

		resourceBlockImpl.resetOriginalValues();

		return resourceBlockImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		resourceBlockId = objectInput.readLong();
		companyId = objectInput.readLong();
		groupId = objectInput.readLong();
		name = objectInput.readUTF();
		permissionsHash = objectInput.readUTF();
		referenceCount = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(resourceBlockId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(groupId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (permissionsHash == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(permissionsHash);
		}

		objectOutput.writeLong(referenceCount);
		objectOutput.writeLong(mvccVersion);
	}

	public long resourceBlockId;
	public long companyId;
	public long groupId;
	public String name;
	public String permissionsHash;
	public long referenceCount;
	public long mvccVersion;
}