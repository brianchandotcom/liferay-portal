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

package com.liferay.saml.persistence.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.saml.persistence.model.SamlIdpSsoSession;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlIdpSsoSession in entity cache.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSession
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionCacheModel implements CacheModel<SamlIdpSsoSession>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlIdpSsoSessionCacheModel)) {
			return false;
		}

		SamlIdpSsoSessionCacheModel samlIdpSsoSessionCacheModel = (SamlIdpSsoSessionCacheModel)obj;

		if (samlIdpSsoSessionId == samlIdpSsoSessionCacheModel.samlIdpSsoSessionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlIdpSsoSessionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{samlIdpSsoSessionId=");
		sb.append(samlIdpSsoSessionId);
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
		sb.append(", samlIdpSsoSessionKey=");
		sb.append(samlIdpSsoSessionKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlIdpSsoSession toEntityModel() {
		SamlIdpSsoSessionImpl samlIdpSsoSessionImpl = new SamlIdpSsoSessionImpl();

		samlIdpSsoSessionImpl.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		samlIdpSsoSessionImpl.setCompanyId(companyId);
		samlIdpSsoSessionImpl.setUserId(userId);

		if (userName == null) {
			samlIdpSsoSessionImpl.setUserName("");
		}
		else {
			samlIdpSsoSessionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			samlIdpSsoSessionImpl.setCreateDate(null);
		}
		else {
			samlIdpSsoSessionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			samlIdpSsoSessionImpl.setModifiedDate(null);
		}
		else {
			samlIdpSsoSessionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (samlIdpSsoSessionKey == null) {
			samlIdpSsoSessionImpl.setSamlIdpSsoSessionKey("");
		}
		else {
			samlIdpSsoSessionImpl.setSamlIdpSsoSessionKey(samlIdpSsoSessionKey);
		}

		samlIdpSsoSessionImpl.resetOriginalValues();

		return samlIdpSsoSessionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		samlIdpSsoSessionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		samlIdpSsoSessionKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(samlIdpSsoSessionId);

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

		if (samlIdpSsoSessionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlIdpSsoSessionKey);
		}
	}

	public long samlIdpSsoSessionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String samlIdpSsoSessionKey;
}