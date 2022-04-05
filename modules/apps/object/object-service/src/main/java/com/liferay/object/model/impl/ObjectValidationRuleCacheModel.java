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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectValidationRule;
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
 * The cache model class for representing ObjectValidationRule in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectValidationRuleCacheModel
	implements CacheModel<ObjectValidationRule>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectValidationRuleCacheModel)) {
			return false;
		}

		ObjectValidationRuleCacheModel objectValidationRuleCacheModel =
			(ObjectValidationRuleCacheModel)object;

		if ((objectValidationRuleId ==
				objectValidationRuleCacheModel.objectValidationRuleId) &&
			(mvccVersion == objectValidationRuleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectValidationRuleId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectValidationRuleId=");
		sb.append(objectValidationRuleId);
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
		sb.append(", engine=");
		sb.append(engine);
		sb.append(", errorLabel=");
		sb.append(errorLabel);
		sb.append(", name=");
		sb.append(name);
		sb.append(", script=");
		sb.append(script);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectValidationRule toEntityModel() {
		ObjectValidationRuleImpl objectValidationRuleImpl =
			new ObjectValidationRuleImpl();

		objectValidationRuleImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectValidationRuleImpl.setUuid("");
		}
		else {
			objectValidationRuleImpl.setUuid(uuid);
		}

		objectValidationRuleImpl.setObjectValidationRuleId(
			objectValidationRuleId);
		objectValidationRuleImpl.setCompanyId(companyId);
		objectValidationRuleImpl.setUserId(userId);

		if (userName == null) {
			objectValidationRuleImpl.setUserName("");
		}
		else {
			objectValidationRuleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectValidationRuleImpl.setCreateDate(null);
		}
		else {
			objectValidationRuleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectValidationRuleImpl.setModifiedDate(null);
		}
		else {
			objectValidationRuleImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectValidationRuleImpl.setObjectDefinitionId(objectDefinitionId);
		objectValidationRuleImpl.setActive(active);

		if (engine == null) {
			objectValidationRuleImpl.setEngine("");
		}
		else {
			objectValidationRuleImpl.setEngine(engine);
		}

		if (errorLabel == null) {
			objectValidationRuleImpl.setErrorLabel("");
		}
		else {
			objectValidationRuleImpl.setErrorLabel(errorLabel);
		}

		if (name == null) {
			objectValidationRuleImpl.setName("");
		}
		else {
			objectValidationRuleImpl.setName(name);
		}

		if (script == null) {
			objectValidationRuleImpl.setScript("");
		}
		else {
			objectValidationRuleImpl.setScript(script);
		}

		objectValidationRuleImpl.resetOriginalValues();

		return objectValidationRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectValidationRuleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		active = objectInput.readBoolean();
		engine = objectInput.readUTF();
		errorLabel = objectInput.readUTF();
		name = objectInput.readUTF();
		script = (String)objectInput.readObject();
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

		objectOutput.writeLong(objectValidationRuleId);

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

		if (engine == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(engine);
		}

		if (errorLabel == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorLabel);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (script == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(script);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectValidationRuleId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public boolean active;
	public String engine;
	public String errorLabel;
	public String name;
	public String script;

}