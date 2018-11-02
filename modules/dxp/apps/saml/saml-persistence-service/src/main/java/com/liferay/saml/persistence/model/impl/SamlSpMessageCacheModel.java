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

import com.liferay.saml.persistence.model.SamlSpMessage;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlSpMessage in entity cache.
 *
 * @author Mika Koivisto
 * @see SamlSpMessage
 * @generated
 */
@ProviderType
public class SamlSpMessageCacheModel implements CacheModel<SamlSpMessage>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpMessageCacheModel)) {
			return false;
		}

		SamlSpMessageCacheModel samlSpMessageCacheModel = (SamlSpMessageCacheModel)obj;

		if (samlSpMessageId == samlSpMessageCacheModel.samlSpMessageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlSpMessageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{samlSpMessageId=");
		sb.append(samlSpMessageId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", samlIdpEntityId=");
		sb.append(samlIdpEntityId);
		sb.append(", samlIdpResponseKey=");
		sb.append(samlIdpResponseKey);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlSpMessage toEntityModel() {
		SamlSpMessageImpl samlSpMessageImpl = new SamlSpMessageImpl();

		samlSpMessageImpl.setSamlSpMessageId(samlSpMessageId);
		samlSpMessageImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			samlSpMessageImpl.setCreateDate(null);
		}
		else {
			samlSpMessageImpl.setCreateDate(new Date(createDate));
		}

		if (samlIdpEntityId == null) {
			samlSpMessageImpl.setSamlIdpEntityId("");
		}
		else {
			samlSpMessageImpl.setSamlIdpEntityId(samlIdpEntityId);
		}

		if (samlIdpResponseKey == null) {
			samlSpMessageImpl.setSamlIdpResponseKey("");
		}
		else {
			samlSpMessageImpl.setSamlIdpResponseKey(samlIdpResponseKey);
		}

		if (expirationDate == Long.MIN_VALUE) {
			samlSpMessageImpl.setExpirationDate(null);
		}
		else {
			samlSpMessageImpl.setExpirationDate(new Date(expirationDate));
		}

		samlSpMessageImpl.resetOriginalValues();

		return samlSpMessageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		samlSpMessageId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		samlIdpEntityId = objectInput.readUTF();
		samlIdpResponseKey = objectInput.readUTF();
		expirationDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(samlSpMessageId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (samlIdpEntityId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlIdpEntityId);
		}

		if (samlIdpResponseKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlIdpResponseKey);
		}

		objectOutput.writeLong(expirationDate);
	}

	public long samlSpMessageId;
	public long companyId;
	public long createDate;
	public String samlIdpEntityId;
	public String samlIdpResponseKey;
	public long expirationDate;
}