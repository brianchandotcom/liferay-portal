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

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ERCGroupEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCGroupEntryCacheModel
	implements CacheModel<ERCGroupEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCGroupEntryCacheModel)) {
			return false;
		}

		ERCGroupEntryCacheModel ercGroupEntryCacheModel =
			(ERCGroupEntryCacheModel)object;

		if (ercGroupEntryId == ercGroupEntryCacheModel.ercGroupEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ercGroupEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", ercGroupEntryId=");
		sb.append(ercGroupEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ERCGroupEntry toEntityModel() {
		ERCGroupEntryImpl ercGroupEntryImpl = new ERCGroupEntryImpl();

		if (uuid == null) {
			ercGroupEntryImpl.setUuid("");
		}
		else {
			ercGroupEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			ercGroupEntryImpl.setExternalReferenceCode("");
		}
		else {
			ercGroupEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		ercGroupEntryImpl.setErcGroupEntryId(ercGroupEntryId);
		ercGroupEntryImpl.setGroupId(groupId);
		ercGroupEntryImpl.setCompanyId(companyId);

		ercGroupEntryImpl.resetOriginalValues();

		return ercGroupEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		ercGroupEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

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

		objectOutput.writeLong(ercGroupEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public String externalReferenceCode;
	public long ercGroupEntryId;
	public long groupId;
	public long companyId;

}