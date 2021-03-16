/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Blueprint in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlueprintCacheModel
	implements CacheModel<Blueprint>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BlueprintCacheModel)) {
			return false;
		}

		BlueprintCacheModel blueprintCacheModel = (BlueprintCacheModel)object;

		if ((blueprintId == blueprintCacheModel.blueprintId) &&
			(mvccVersion == blueprintCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, blueprintId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", blueprintId=");
		sb.append(blueprintId);
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
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", configuration=");
		sb.append(configuration);
		sb.append(", selectedElements=");
		sb.append(selectedElements);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Blueprint toEntityModel() {
		BlueprintImpl blueprintImpl = new BlueprintImpl();

		blueprintImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			blueprintImpl.setUuid("");
		}
		else {
			blueprintImpl.setUuid(uuid);
		}

		blueprintImpl.setBlueprintId(blueprintId);
		blueprintImpl.setGroupId(groupId);
		blueprintImpl.setCompanyId(companyId);
		blueprintImpl.setUserId(userId);

		if (userName == null) {
			blueprintImpl.setUserName("");
		}
		else {
			blueprintImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			blueprintImpl.setCreateDate(null);
		}
		else {
			blueprintImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			blueprintImpl.setModifiedDate(null);
		}
		else {
			blueprintImpl.setModifiedDate(new Date(modifiedDate));
		}

		blueprintImpl.setStatus(status);
		blueprintImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			blueprintImpl.setStatusByUserName("");
		}
		else {
			blueprintImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			blueprintImpl.setStatusDate(null);
		}
		else {
			blueprintImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			blueprintImpl.setTitle("");
		}
		else {
			blueprintImpl.setTitle(title);
		}

		if (description == null) {
			blueprintImpl.setDescription("");
		}
		else {
			blueprintImpl.setDescription(description);
		}

		if (configuration == null) {
			blueprintImpl.setConfiguration("");
		}
		else {
			blueprintImpl.setConfiguration(configuration);
		}

		if (selectedElements == null) {
			blueprintImpl.setSelectedElements("");
		}
		else {
			blueprintImpl.setSelectedElements(selectedElements);
		}

		blueprintImpl.setType(type);

		blueprintImpl.resetOriginalValues();

		return blueprintImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		blueprintId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		configuration = (String)objectInput.readObject();
		selectedElements = (String)objectInput.readObject();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(blueprintId);

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

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (configuration == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(configuration);
		}

		if (selectedElements == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(selectedElements);
		}

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public String uuid;
	public long blueprintId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public String configuration;
	public String selectedElements;
	public int type;

}