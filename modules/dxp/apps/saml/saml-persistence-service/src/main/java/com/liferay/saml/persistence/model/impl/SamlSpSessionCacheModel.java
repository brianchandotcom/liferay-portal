/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.saml.persistence.model.SamlSpSession;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlSpSession in entity cache.
 *
 * @author Mika Koivisto
 * @generated
 */
public class SamlSpSessionCacheModel
	implements CacheModel<SamlSpSession>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SamlSpSessionCacheModel)) {
			return false;
		}

		SamlSpSessionCacheModel samlSpSessionCacheModel =
			(SamlSpSessionCacheModel)object;

		if (samlSpSessionId == samlSpSessionCacheModel.samlSpSessionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlSpSessionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{samlSpSessionId=");
		sb.append(samlSpSessionId);
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
		sb.append(", samlPeerBindingId=");
		sb.append(samlPeerBindingId);
		sb.append(", assertionXml=");
		sb.append(assertionXml);
		sb.append(", jSessionId=");
		sb.append(jSessionId);
		sb.append(", samlSpSessionKey=");
		sb.append(samlSpSessionKey);
		sb.append(", sessionIndex=");
		sb.append(sessionIndex);
		sb.append(", terminated=");
		sb.append(terminated);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlSpSession toEntityModel() {
		SamlSpSessionImpl samlSpSessionImpl = new SamlSpSessionImpl();

		samlSpSessionImpl.setSamlSpSessionId(samlSpSessionId);
		samlSpSessionImpl.setCompanyId(companyId);
		samlSpSessionImpl.setUserId(userId);

		if (userName == null) {
			samlSpSessionImpl.setUserName("");
		}
		else {
			samlSpSessionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			samlSpSessionImpl.setCreateDate(null);
		}
		else {
			samlSpSessionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			samlSpSessionImpl.setModifiedDate(null);
		}
		else {
			samlSpSessionImpl.setModifiedDate(new Date(modifiedDate));
		}

		samlSpSessionImpl.setSamlPeerBindingId(samlPeerBindingId);

		if (assertionXml == null) {
			samlSpSessionImpl.setAssertionXml("");
		}
		else {
			samlSpSessionImpl.setAssertionXml(assertionXml);
		}

		if (jSessionId == null) {
			samlSpSessionImpl.setJSessionId("");
		}
		else {
			samlSpSessionImpl.setJSessionId(jSessionId);
		}

		if (samlSpSessionKey == null) {
			samlSpSessionImpl.setSamlSpSessionKey("");
		}
		else {
			samlSpSessionImpl.setSamlSpSessionKey(samlSpSessionKey);
		}

		if (sessionIndex == null) {
			samlSpSessionImpl.setSessionIndex("");
		}
		else {
			samlSpSessionImpl.setSessionIndex(sessionIndex);
		}

		samlSpSessionImpl.setTerminated(terminated);

		samlSpSessionImpl.resetOriginalValues();

		return samlSpSessionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		samlSpSessionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		samlPeerBindingId = objectInput.readLong();
		assertionXml = (String)objectInput.readObject();
		jSessionId = objectInput.readUTF();
		samlSpSessionKey = objectInput.readUTF();
		sessionIndex = objectInput.readUTF();

		terminated = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(samlSpSessionId);

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

		objectOutput.writeLong(samlPeerBindingId);

		if (assertionXml == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(assertionXml);
		}

		if (jSessionId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jSessionId);
		}

		if (samlSpSessionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlSpSessionKey);
		}

		if (sessionIndex == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sessionIndex);
		}

		objectOutput.writeBoolean(terminated);
	}

	public long samlSpSessionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long samlPeerBindingId;
	public String assertionXml;
	public String jSessionId;
	public String samlSpSessionKey;
	public String sessionIndex;
	public boolean terminated;

}