/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
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
 * @generated
 */
public class SamlSpAuthRequestCacheModel
	implements CacheModel<SamlSpAuthRequest>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SamlSpAuthRequestCacheModel)) {
			return false;
		}

		SamlSpAuthRequestCacheModel samlSpAuthRequestCacheModel =
			(SamlSpAuthRequestCacheModel)object;

		if (samlSpAuthnRequestId ==
				samlSpAuthRequestCacheModel.samlSpAuthnRequestId) {

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
		SamlSpAuthRequestImpl samlSpAuthRequestImpl =
			new SamlSpAuthRequestImpl();

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
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
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