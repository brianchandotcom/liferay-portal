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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoNode in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNodeCacheModel
	implements CacheModel<KaleoNode>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoNodeCacheModel)) {
			return false;
		}

		KaleoNodeCacheModel kaleoNodeCacheModel = (KaleoNodeCacheModel)obj;

		if ((kaleoNodeId == kaleoNodeCacheModel.kaleoNodeId) &&
			(mvccVersion == kaleoNodeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoNodeId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", metadata=");
		sb.append(metadata);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", initial=");
		sb.append(initial);
		sb.append(", terminal=");
		sb.append(terminal);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoNode toEntityModel() {
		KaleoNodeImpl kaleoNodeImpl = new KaleoNodeImpl();

		kaleoNodeImpl.setMvccVersion(mvccVersion);
		kaleoNodeImpl.setKaleoNodeId(kaleoNodeId);
		kaleoNodeImpl.setGroupId(groupId);
		kaleoNodeImpl.setCompanyId(companyId);
		kaleoNodeImpl.setUserId(userId);

		if (userName == null) {
			kaleoNodeImpl.setUserName("");
		}
		else {
			kaleoNodeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoNodeImpl.setCreateDate(null);
		}
		else {
			kaleoNodeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoNodeImpl.setModifiedDate(null);
		}
		else {
			kaleoNodeImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoNodeImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (name == null) {
			kaleoNodeImpl.setName("");
		}
		else {
			kaleoNodeImpl.setName(name);
		}

		if (metadata == null) {
			kaleoNodeImpl.setMetadata("");
		}
		else {
			kaleoNodeImpl.setMetadata(metadata);
		}

		if (description == null) {
			kaleoNodeImpl.setDescription("");
		}
		else {
			kaleoNodeImpl.setDescription(description);
		}

		if (type == null) {
			kaleoNodeImpl.setType("");
		}
		else {
			kaleoNodeImpl.setType(type);
		}

		kaleoNodeImpl.setInitial(initial);
		kaleoNodeImpl.setTerminal(terminal);

		kaleoNodeImpl.resetOriginalValues();

		return kaleoNodeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoNodeId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		name = objectInput.readUTF();
		metadata = objectInput.readUTF();
		description = objectInput.readUTF();
		type = objectInput.readUTF();

		initial = objectInput.readBoolean();

		terminal = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoNodeId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (metadata == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(metadata);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeBoolean(initial);

		objectOutput.writeBoolean(terminal);
	}

	public long mvccVersion;
	public long kaleoNodeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionVersionId;
	public String name;
	public String metadata;
	public String description;
	public String type;
	public boolean initial;
	public boolean terminal;

}