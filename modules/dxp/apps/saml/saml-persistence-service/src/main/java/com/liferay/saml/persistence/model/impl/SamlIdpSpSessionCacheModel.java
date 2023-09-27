/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.saml.persistence.model.SamlIdpSpSession;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlIdpSpSession in entity cache.
 *
 * @author Mika Koivisto
 * @generated
 */
public class SamlIdpSpSessionCacheModel
	implements CacheModel<SamlIdpSpSession>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SamlIdpSpSessionCacheModel)) {
			return false;
		}

		SamlIdpSpSessionCacheModel samlIdpSpSessionCacheModel =
			(SamlIdpSpSessionCacheModel)object;

		if (samlIdpSpSessionId ==
				samlIdpSpSessionCacheModel.samlIdpSpSessionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlIdpSpSessionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{samlIdpSpSessionId=");
		sb.append(samlIdpSpSessionId);
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
		sb.append(", samlIdpSsoSessionId=");
		sb.append(samlIdpSsoSessionId);
		sb.append(", samlPeerBindingId=");
		sb.append(samlPeerBindingId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlIdpSpSession toEntityModel() {
		SamlIdpSpSessionImpl samlIdpSpSessionImpl = new SamlIdpSpSessionImpl();

		samlIdpSpSessionImpl.setSamlIdpSpSessionId(samlIdpSpSessionId);
		samlIdpSpSessionImpl.setCompanyId(companyId);
		samlIdpSpSessionImpl.setUserId(userId);

		if (userName == null) {
			samlIdpSpSessionImpl.setUserName("");
		}
		else {
			samlIdpSpSessionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			samlIdpSpSessionImpl.setCreateDate(null);
		}
		else {
			samlIdpSpSessionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			samlIdpSpSessionImpl.setModifiedDate(null);
		}
		else {
			samlIdpSpSessionImpl.setModifiedDate(new Date(modifiedDate));
		}

		samlIdpSpSessionImpl.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		samlIdpSpSessionImpl.setSamlPeerBindingId(samlPeerBindingId);

		samlIdpSpSessionImpl.resetOriginalValues();

		return samlIdpSpSessionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		samlIdpSpSessionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		samlIdpSsoSessionId = objectInput.readLong();

		samlPeerBindingId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(samlIdpSpSessionId);

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

		objectOutput.writeLong(samlIdpSsoSessionId);

		objectOutput.writeLong(samlPeerBindingId);
	}

	public long samlIdpSpSessionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long samlIdpSsoSessionId;
	public long samlPeerBindingId;

}