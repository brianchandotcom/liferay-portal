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

package com.liferay.powwow.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.powwow.model.PowwowServer;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PowwowServer in entity cache.
 *
 * @author Shinn Lok
 * @generated
 */
public class PowwowServerCacheModel
	implements CacheModel<PowwowServer>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowServerCacheModel)) {
			return false;
		}

		PowwowServerCacheModel powwowServerCacheModel =
			(PowwowServerCacheModel)obj;

		if (powwowServerId == powwowServerCacheModel.powwowServerId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, powwowServerId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{powwowServerId=");
		sb.append(powwowServerId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", providerType=");
		sb.append(providerType);
		sb.append(", url=");
		sb.append(url);
		sb.append(", apiKey=");
		sb.append(apiKey);
		sb.append(", secret=");
		sb.append(secret);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PowwowServer toEntityModel() {
		PowwowServerImpl powwowServerImpl = new PowwowServerImpl();

		powwowServerImpl.setPowwowServerId(powwowServerId);
		powwowServerImpl.setCompanyId(companyId);
		powwowServerImpl.setUserId(userId);

		if (userName == null) {
			powwowServerImpl.setUserName("");
		}
		else {
			powwowServerImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			powwowServerImpl.setCreateDate(null);
		}
		else {
			powwowServerImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			powwowServerImpl.setModifiedDate(null);
		}
		else {
			powwowServerImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			powwowServerImpl.setName("");
		}
		else {
			powwowServerImpl.setName(name);
		}

		if (providerType == null) {
			powwowServerImpl.setProviderType("");
		}
		else {
			powwowServerImpl.setProviderType(providerType);
		}

		if (url == null) {
			powwowServerImpl.setUrl("");
		}
		else {
			powwowServerImpl.setUrl(url);
		}

		if (apiKey == null) {
			powwowServerImpl.setApiKey("");
		}
		else {
			powwowServerImpl.setApiKey(apiKey);
		}

		if (secret == null) {
			powwowServerImpl.setSecret("");
		}
		else {
			powwowServerImpl.setSecret(secret);
		}

		powwowServerImpl.setActive(active);

		powwowServerImpl.resetOriginalValues();

		return powwowServerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		powwowServerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		providerType = objectInput.readUTF();
		url = objectInput.readUTF();
		apiKey = objectInput.readUTF();
		secret = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(powwowServerId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (providerType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(providerType);
		}

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		if (apiKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(apiKey);
		}

		if (secret == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(secret);
		}

		objectOutput.writeBoolean(active);
	}

	public long powwowServerId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String providerType;
	public String url;
	public String apiKey;
	public String secret;
	public boolean active;

}