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

package com.liferay.portal.security.sso.openid.connect.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OpenIdConnectSession in entity cache.
 *
 * @author Arthur Chan
 * @generated
 */
public class OpenIdConnectSessionCacheModel
	implements CacheModel<OpenIdConnectSession>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OpenIdConnectSessionCacheModel)) {
			return false;
		}

		OpenIdConnectSessionCacheModel openIdConnectSessionCacheModel =
			(OpenIdConnectSessionCacheModel)object;

		if ((openIdConnectSessionId ==
				openIdConnectSessionCacheModel.openIdConnectSessionId) &&
			(mvccVersion == openIdConnectSessionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, openIdConnectSessionId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", openIdConnectSessionId=");
		sb.append(openIdConnectSessionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", accessToken=");
		sb.append(accessToken);
		sb.append(", idToken=");
		sb.append(idToken);
		sb.append(", providerName=");
		sb.append(providerName);
		sb.append(", refreshToken=");
		sb.append(refreshToken);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OpenIdConnectSession toEntityModel() {
		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			new OpenIdConnectSessionImpl();

		openIdConnectSessionImpl.setMvccVersion(mvccVersion);
		openIdConnectSessionImpl.setOpenIdConnectSessionId(
			openIdConnectSessionId);
		openIdConnectSessionImpl.setCompanyId(companyId);

		if (modifiedDate == Long.MIN_VALUE) {
			openIdConnectSessionImpl.setModifiedDate(null);
		}
		else {
			openIdConnectSessionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (accessToken == null) {
			openIdConnectSessionImpl.setAccessToken("");
		}
		else {
			openIdConnectSessionImpl.setAccessToken(accessToken);
		}

		if (idToken == null) {
			openIdConnectSessionImpl.setIdToken("");
		}
		else {
			openIdConnectSessionImpl.setIdToken(idToken);
		}

		if (providerName == null) {
			openIdConnectSessionImpl.setProviderName("");
		}
		else {
			openIdConnectSessionImpl.setProviderName(providerName);
		}

		if (refreshToken == null) {
			openIdConnectSessionImpl.setRefreshToken("");
		}
		else {
			openIdConnectSessionImpl.setRefreshToken(refreshToken);
		}

		openIdConnectSessionImpl.resetOriginalValues();

		return openIdConnectSessionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		openIdConnectSessionId = objectInput.readLong();

		companyId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		accessToken = objectInput.readUTF();
		idToken = objectInput.readUTF();
		providerName = objectInput.readUTF();
		refreshToken = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(openIdConnectSessionId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(modifiedDate);

		if (accessToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accessToken);
		}

		if (idToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(idToken);
		}

		if (providerName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(providerName);
		}

		if (refreshToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(refreshToken);
		}
	}

	public long mvccVersion;
	public long openIdConnectSessionId;
	public long companyId;
	public long modifiedDate;
	public String accessToken;
	public String idToken;
	public String providerName;
	public String refreshToken;

}