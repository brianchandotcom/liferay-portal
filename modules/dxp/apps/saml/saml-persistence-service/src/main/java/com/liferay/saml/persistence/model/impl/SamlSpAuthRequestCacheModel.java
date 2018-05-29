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

import com.liferay.saml.persistence.model.SamlSpAuthRequest;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlSpAuthRequest in entity cache.
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequest
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestCacheModel implements CacheModel<SamlSpAuthRequest>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpAuthRequestCacheModel)) {
			return false;
		}

		SamlSpAuthRequestCacheModel samlSpAuthRequestCacheModel = (SamlSpAuthRequestCacheModel)obj;

		if (samlSpAuthnRequestId == samlSpAuthRequestCacheModel.samlSpAuthnRequestId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlSpAuthnRequestId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{samlSpAuthnRequestId=");
		sb.append(samlSpAuthnRequestId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", samlIdpEntityId=");
		sb.append(samlIdpEntityId);
		sb.append(", samlSpAuthRequestKey=");
		sb.append(samlSpAuthRequestKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlSpAuthRequest toEntityModel() {
		SamlSpAuthRequestImpl samlSpAuthRequestImpl = new SamlSpAuthRequestImpl();

		samlSpAuthRequestImpl.setSamlSpAuthnRequestId(samlSpAuthnRequestId);
		samlSpAuthRequestImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			samlSpAuthRequestImpl.setCreateDate(null);
		}
		else {
			samlSpAuthRequestImpl.setCreateDate(new Date(createDate));
		}

		if (samlIdpEntityId == null) {
			samlSpAuthRequestImpl.setSamlIdpEntityId("");
		}
		else {
			samlSpAuthRequestImpl.setSamlIdpEntityId(samlIdpEntityId);
		}

		if (samlSpAuthRequestKey == null) {
			samlSpAuthRequestImpl.setSamlSpAuthRequestKey("");
		}
		else {
			samlSpAuthRequestImpl.setSamlSpAuthRequestKey(samlSpAuthRequestKey);
		}

		samlSpAuthRequestImpl.resetOriginalValues();

		return samlSpAuthRequestImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		samlSpAuthnRequestId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		samlIdpEntityId = objectInput.readUTF();
		samlSpAuthRequestKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(samlSpAuthnRequestId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (samlIdpEntityId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlIdpEntityId);
		}

		if (samlSpAuthRequestKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlSpAuthRequestKey);
		}
	}

	public long samlSpAuthnRequestId;
	public long companyId;
	public long createDate;
	public String samlIdpEntityId;
	public String samlSpAuthRequestKey;
}