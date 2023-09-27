/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ERCCompanyEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCCompanyEntryCacheModel
	implements CacheModel<ERCCompanyEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCCompanyEntryCacheModel)) {
			return false;
		}

		ERCCompanyEntryCacheModel ercCompanyEntryCacheModel =
			(ERCCompanyEntryCacheModel)object;

		if (ercCompanyEntryId == ercCompanyEntryCacheModel.ercCompanyEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ercCompanyEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", ercCompanyEntryId=");
		sb.append(ercCompanyEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ERCCompanyEntry toEntityModel() {
		ERCCompanyEntryImpl ercCompanyEntryImpl = new ERCCompanyEntryImpl();

		if (uuid == null) {
			ercCompanyEntryImpl.setUuid("");
		}
		else {
			ercCompanyEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			ercCompanyEntryImpl.setExternalReferenceCode("");
		}
		else {
			ercCompanyEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		ercCompanyEntryImpl.setErcCompanyEntryId(ercCompanyEntryId);
		ercCompanyEntryImpl.setCompanyId(companyId);

		ercCompanyEntryImpl.resetOriginalValues();

		return ercCompanyEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		ercCompanyEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(ercCompanyEntryId);

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public String externalReferenceCode;
	public long ercCompanyEntryId;
	public long companyId;

}