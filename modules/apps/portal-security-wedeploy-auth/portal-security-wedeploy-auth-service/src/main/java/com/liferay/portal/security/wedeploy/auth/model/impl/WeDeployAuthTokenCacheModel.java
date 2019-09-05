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

package com.liferay.portal.security.wedeploy.auth.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WeDeployAuthToken in entity cache.
 *
 * @author Supritha Sundaram
 * @generated
 */
public class WeDeployAuthTokenCacheModel
	implements CacheModel<WeDeployAuthToken>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WeDeployAuthTokenCacheModel)) {
			return false;
		}

		WeDeployAuthTokenCacheModel weDeployAuthTokenCacheModel =
			(WeDeployAuthTokenCacheModel)obj;

		if (weDeployAuthTokenId ==
				weDeployAuthTokenCacheModel.weDeployAuthTokenId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, weDeployAuthTokenId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{weDeployAuthTokenId=");
		sb.append(weDeployAuthTokenId);
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
		sb.append(", clientId=");
		sb.append(clientId);
		sb.append(", token=");
		sb.append(token);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WeDeployAuthToken toEntityModel() {
		WeDeployAuthTokenImpl weDeployAuthTokenImpl =
			new WeDeployAuthTokenImpl();

		weDeployAuthTokenImpl.setWeDeployAuthTokenId(weDeployAuthTokenId);
		weDeployAuthTokenImpl.setCompanyId(companyId);
		weDeployAuthTokenImpl.setUserId(userId);

		if (userName == null) {
			weDeployAuthTokenImpl.setUserName("");
		}
		else {
			weDeployAuthTokenImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			weDeployAuthTokenImpl.setCreateDate(null);
		}
		else {
			weDeployAuthTokenImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			weDeployAuthTokenImpl.setModifiedDate(null);
		}
		else {
			weDeployAuthTokenImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (clientId == null) {
			weDeployAuthTokenImpl.setClientId("");
		}
		else {
			weDeployAuthTokenImpl.setClientId(clientId);
		}

		if (token == null) {
			weDeployAuthTokenImpl.setToken("");
		}
		else {
			weDeployAuthTokenImpl.setToken(token);
		}

		weDeployAuthTokenImpl.setType(type);

		weDeployAuthTokenImpl.resetOriginalValues();

		return weDeployAuthTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		weDeployAuthTokenId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		clientId = objectInput.readUTF();
		token = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(weDeployAuthTokenId);

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

		if (clientId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientId);
		}

		if (token == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(token);
		}

		objectOutput.writeInt(type);
	}

	public long weDeployAuthTokenId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String clientId;
	public String token;
	public int type;

}