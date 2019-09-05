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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LayoutBranch in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutBranchCacheModel
	implements CacheModel<LayoutBranch>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutBranchCacheModel)) {
			return false;
		}

		LayoutBranchCacheModel layoutBranchCacheModel =
			(LayoutBranchCacheModel)obj;

		if ((layoutBranchId == layoutBranchCacheModel.layoutBranchId) &&
			(mvccVersion == layoutBranchCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutBranchId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", layoutBranchId=");
		sb.append(layoutBranchId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", layoutSetBranchId=");
		sb.append(layoutSetBranchId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", master=");
		sb.append(master);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutBranch toEntityModel() {
		LayoutBranchImpl layoutBranchImpl = new LayoutBranchImpl();

		layoutBranchImpl.setMvccVersion(mvccVersion);
		layoutBranchImpl.setLayoutBranchId(layoutBranchId);
		layoutBranchImpl.setGroupId(groupId);
		layoutBranchImpl.setCompanyId(companyId);
		layoutBranchImpl.setUserId(userId);

		if (userName == null) {
			layoutBranchImpl.setUserName("");
		}
		else {
			layoutBranchImpl.setUserName(userName);
		}

		layoutBranchImpl.setLayoutSetBranchId(layoutSetBranchId);
		layoutBranchImpl.setPlid(plid);

		if (name == null) {
			layoutBranchImpl.setName("");
		}
		else {
			layoutBranchImpl.setName(name);
		}

		if (description == null) {
			layoutBranchImpl.setDescription("");
		}
		else {
			layoutBranchImpl.setDescription(description);
		}

		layoutBranchImpl.setMaster(master);

		layoutBranchImpl.resetOriginalValues();

		return layoutBranchImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		layoutBranchId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		layoutSetBranchId = objectInput.readLong();

		plid = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		master = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(layoutBranchId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(layoutSetBranchId);

		objectOutput.writeLong(plid);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(master);
	}

	public long mvccVersion;
	public long layoutBranchId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long layoutSetBranchId;
	public long plid;
	public String name;
	public String description;
	public boolean master;

}