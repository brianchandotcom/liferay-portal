/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

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
 * @see SamlIdpSpSession
 * @generated
 */
@ProviderType
public class SamlIdpSpSessionCacheModel implements CacheModel<SamlIdpSpSession>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlIdpSpSessionCacheModel)) {
			return false;
		}

		SamlIdpSpSessionCacheModel samlIdpSpSessionCacheModel = (SamlIdpSpSessionCacheModel)obj;

		if (samlIdpSpSessionId == samlIdpSpSessionCacheModel.samlIdpSpSessionId) {
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
		StringBundler sb = new StringBundler(21);

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
		sb.append(", samlSpEntityId=");
		sb.append(samlSpEntityId);
		sb.append(", nameIdFormat=");
		sb.append(nameIdFormat);
		sb.append(", nameIdValue=");
		sb.append(nameIdValue);
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

		if (samlSpEntityId == null) {
			samlIdpSpSessionImpl.setSamlSpEntityId("");
		}
		else {
			samlIdpSpSessionImpl.setSamlSpEntityId(samlSpEntityId);
		}

		if (nameIdFormat == null) {
			samlIdpSpSessionImpl.setNameIdFormat("");
		}
		else {
			samlIdpSpSessionImpl.setNameIdFormat(nameIdFormat);
		}

		if (nameIdValue == null) {
			samlIdpSpSessionImpl.setNameIdValue("");
		}
		else {
			samlIdpSpSessionImpl.setNameIdValue(nameIdValue);
		}

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
		samlSpEntityId = objectInput.readUTF();
		nameIdFormat = objectInput.readUTF();
		nameIdValue = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
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

		if (samlSpEntityId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlSpEntityId);
		}

		if (nameIdFormat == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdFormat);
		}

		if (nameIdValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdValue);
		}
	}

	public long samlIdpSpSessionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long samlIdpSsoSessionId;
	public String samlSpEntityId;
	public String nameIdFormat;
	public String nameIdValue;
}