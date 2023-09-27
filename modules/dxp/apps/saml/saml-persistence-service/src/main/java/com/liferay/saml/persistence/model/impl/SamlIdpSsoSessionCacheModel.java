/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
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
 * @generated
 */
public class SamlIdpSsoSessionCacheModel
	implements CacheModel<SamlIdpSsoSession>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SamlIdpSsoSessionCacheModel)) {
			return false;
		}

		SamlIdpSsoSessionCacheModel samlIdpSsoSessionCacheModel =
			(SamlIdpSsoSessionCacheModel)object;

		if (samlIdpSsoSessionId ==
				samlIdpSsoSessionCacheModel.samlIdpSsoSessionId) {

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
		SamlIdpSsoSessionImpl samlIdpSsoSessionImpl =
			new SamlIdpSsoSessionImpl();

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
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
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