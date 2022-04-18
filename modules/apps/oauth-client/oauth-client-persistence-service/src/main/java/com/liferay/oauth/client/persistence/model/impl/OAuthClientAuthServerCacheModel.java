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

package com.liferay.oauth.client.persistence.model.impl;

import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
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
 * The cache model class for representing OAuthClientAuthServer in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthClientAuthServerCacheModel
	implements CacheModel<OAuthClientAuthServer>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OAuthClientAuthServerCacheModel)) {
			return false;
		}

		OAuthClientAuthServerCacheModel oAuthClientAuthServerCacheModel =
			(OAuthClientAuthServerCacheModel)object;

		if ((oAuthClientAuthServerId ==
				oAuthClientAuthServerCacheModel.oAuthClientAuthServerId) &&
			(mvccVersion == oAuthClientAuthServerCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, oAuthClientAuthServerId);

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
		sb.append(", oAuthClientAuthServerId=");
		sb.append(oAuthClientAuthServerId);
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
		sb.append(", discoveryEndpoint=");
		sb.append(discoveryEndpoint);
		sb.append(", issuer=");
		sb.append(issuer);
		sb.append(", metadataJSON=");
		sb.append(metadataJSON);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuthClientAuthServer toEntityModel() {
		OAuthClientAuthServerImpl oAuthClientAuthServerImpl =
			new OAuthClientAuthServerImpl();

		oAuthClientAuthServerImpl.setMvccVersion(mvccVersion);
		oAuthClientAuthServerImpl.setOAuthClientAuthServerId(
			oAuthClientAuthServerId);
		oAuthClientAuthServerImpl.setCompanyId(companyId);
		oAuthClientAuthServerImpl.setUserId(userId);

		if (userName == null) {
			oAuthClientAuthServerImpl.setUserName("");
		}
		else {
			oAuthClientAuthServerImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuthClientAuthServerImpl.setCreateDate(null);
		}
		else {
			oAuthClientAuthServerImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			oAuthClientAuthServerImpl.setModifiedDate(null);
		}
		else {
			oAuthClientAuthServerImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (discoveryEndpoint == null) {
			oAuthClientAuthServerImpl.setDiscoveryEndpoint("");
		}
		else {
			oAuthClientAuthServerImpl.setDiscoveryEndpoint(discoveryEndpoint);
		}

		if (issuer == null) {
			oAuthClientAuthServerImpl.setIssuer("");
		}
		else {
			oAuthClientAuthServerImpl.setIssuer(issuer);
		}

		if (metadataJSON == null) {
			oAuthClientAuthServerImpl.setMetadataJSON("");
		}
		else {
			oAuthClientAuthServerImpl.setMetadataJSON(metadataJSON);
		}

		if (type == null) {
			oAuthClientAuthServerImpl.setType("");
		}
		else {
			oAuthClientAuthServerImpl.setType(type);
		}

		oAuthClientAuthServerImpl.resetOriginalValues();

		return oAuthClientAuthServerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		oAuthClientAuthServerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		discoveryEndpoint = objectInput.readUTF();
		issuer = objectInput.readUTF();
		metadataJSON = (String)objectInput.readObject();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(oAuthClientAuthServerId);

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

		if (discoveryEndpoint == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(discoveryEndpoint);
		}

		if (issuer == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(issuer);
		}

		if (metadataJSON == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(metadataJSON);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public long oAuthClientAuthServerId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String discoveryEndpoint;
	public String issuer;
	public String metadataJSON;
	public String type;

}