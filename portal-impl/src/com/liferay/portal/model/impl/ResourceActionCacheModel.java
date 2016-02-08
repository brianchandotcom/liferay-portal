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

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ResourceAction in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceAction
 * @generated
 */
@ProviderType
public class ResourceActionCacheModel implements CacheModel<ResourceAction>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceActionCacheModel)) {
			return false;
		}

		ResourceActionCacheModel resourceActionCacheModel = (ResourceActionCacheModel)obj;

		if ((resourceActionId == resourceActionCacheModel.resourceActionId) &&
				(mvccVersion == resourceActionCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, resourceActionId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", resourceActionId=");
		sb.append(resourceActionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append(", bitwiseValue=");
		sb.append(bitwiseValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ResourceAction toEntityModel() {
		ResourceActionImpl resourceActionImpl = new ResourceActionImpl();

		resourceActionImpl.setMvccVersion(mvccVersion);
		resourceActionImpl.setResourceActionId(resourceActionId);

		if (name == null) {
			resourceActionImpl.setName(StringPool.BLANK);
		}
		else {
			resourceActionImpl.setName(name);
		}

		if (actionId == null) {
			resourceActionImpl.setActionId(StringPool.BLANK);
		}
		else {
			resourceActionImpl.setActionId(actionId);
		}

		resourceActionImpl.setBitwiseValue(bitwiseValue);

		resourceActionImpl.resetOriginalValues();

		return resourceActionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		resourceActionId = objectInput.readLong();
		name = objectInput.readUTF();
		actionId = objectInput.readUTF();

		bitwiseValue = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(resourceActionId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (actionId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(actionId);
		}

		objectOutput.writeLong(bitwiseValue);
	}

	public long mvccVersion;
	public long resourceActionId;
	public String name;
	public String actionId;
	public long bitwiseValue;
}