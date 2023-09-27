/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectAction;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ObjectAction in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectActionCacheModel
	implements CacheModel<ObjectAction>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectActionCacheModel)) {
			return false;
		}

		ObjectActionCacheModel objectActionCacheModel =
			(ObjectActionCacheModel)object;

		if ((objectActionId == objectActionCacheModel.objectActionId) &&
			(mvccVersion == objectActionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectActionId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", objectActionId=");
		sb.append(objectActionId);
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
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", active=");
		sb.append(active);
		sb.append(", conditionExpression=");
		sb.append(conditionExpression);
		sb.append(", description=");
		sb.append(description);
		sb.append(", errorMessage=");
		sb.append(errorMessage);
		sb.append(", label=");
		sb.append(label);
		sb.append(", name=");
		sb.append(name);
		sb.append(", objectActionExecutorKey=");
		sb.append(objectActionExecutorKey);
		sb.append(", objectActionTriggerKey=");
		sb.append(objectActionTriggerKey);
		sb.append(", parameters=");
		sb.append(parameters);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectAction toEntityModel() {
		ObjectActionImpl objectActionImpl = new ObjectActionImpl();

		objectActionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectActionImpl.setUuid("");
		}
		else {
			objectActionImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			objectActionImpl.setExternalReferenceCode("");
		}
		else {
			objectActionImpl.setExternalReferenceCode(externalReferenceCode);
		}

		objectActionImpl.setObjectActionId(objectActionId);
		objectActionImpl.setCompanyId(companyId);
		objectActionImpl.setUserId(userId);

		if (userName == null) {
			objectActionImpl.setUserName("");
		}
		else {
			objectActionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectActionImpl.setCreateDate(null);
		}
		else {
			objectActionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectActionImpl.setModifiedDate(null);
		}
		else {
			objectActionImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectActionImpl.setObjectDefinitionId(objectDefinitionId);
		objectActionImpl.setActive(active);

		if (conditionExpression == null) {
			objectActionImpl.setConditionExpression("");
		}
		else {
			objectActionImpl.setConditionExpression(conditionExpression);
		}

		if (description == null) {
			objectActionImpl.setDescription("");
		}
		else {
			objectActionImpl.setDescription(description);
		}

		if (errorMessage == null) {
			objectActionImpl.setErrorMessage("");
		}
		else {
			objectActionImpl.setErrorMessage(errorMessage);
		}

		if (label == null) {
			objectActionImpl.setLabel("");
		}
		else {
			objectActionImpl.setLabel(label);
		}

		if (name == null) {
			objectActionImpl.setName("");
		}
		else {
			objectActionImpl.setName(name);
		}

		if (objectActionExecutorKey == null) {
			objectActionImpl.setObjectActionExecutorKey("");
		}
		else {
			objectActionImpl.setObjectActionExecutorKey(
				objectActionExecutorKey);
		}

		if (objectActionTriggerKey == null) {
			objectActionImpl.setObjectActionTriggerKey("");
		}
		else {
			objectActionImpl.setObjectActionTriggerKey(objectActionTriggerKey);
		}

		if (parameters == null) {
			objectActionImpl.setParameters("");
		}
		else {
			objectActionImpl.setParameters(parameters);
		}

		objectActionImpl.setStatus(status);

		objectActionImpl.resetOriginalValues();

		return objectActionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		objectActionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		active = objectInput.readBoolean();
		conditionExpression = (String)objectInput.readObject();
		description = objectInput.readUTF();
		errorMessage = objectInput.readUTF();
		label = objectInput.readUTF();
		name = objectInput.readUTF();
		objectActionExecutorKey = objectInput.readUTF();
		objectActionTriggerKey = objectInput.readUTF();
		parameters = (String)objectInput.readObject();

		status = objectInput.readInt();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(objectActionId);

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

		objectOutput.writeLong(objectDefinitionId);

		objectOutput.writeBoolean(active);

		if (conditionExpression == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(conditionExpression);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (errorMessage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorMessage);
		}

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (objectActionExecutorKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(objectActionExecutorKey);
		}

		if (objectActionTriggerKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(objectActionTriggerKey);
		}

		if (parameters == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(parameters);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long objectActionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public boolean active;
	public String conditionExpression;
	public String description;
	public String errorMessage;
	public String label;
	public String name;
	public String objectActionExecutorKey;
	public String objectActionTriggerKey;
	public String parameters;
	public int status;

}