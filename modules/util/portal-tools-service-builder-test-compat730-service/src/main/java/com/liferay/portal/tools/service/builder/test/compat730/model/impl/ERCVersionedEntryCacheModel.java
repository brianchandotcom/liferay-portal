/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.compat730.model.ERCVersionedEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ERCVersionedEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCVersionedEntryCacheModel
	implements CacheModel<ERCVersionedEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCVersionedEntryCacheModel)) {
			return false;
		}

		ERCVersionedEntryCacheModel ercVersionedEntryCacheModel =
			(ERCVersionedEntryCacheModel)object;

		if (ercVersionedEntryId ==
				ercVersionedEntryCacheModel.ercVersionedEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ercVersionedEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", ercVersionedEntryId=");
		sb.append(ercVersionedEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ERCVersionedEntry toEntityModel() {
		ERCVersionedEntryImpl ercVersionedEntryImpl =
			new ERCVersionedEntryImpl();

		if (uuid == null) {
			ercVersionedEntryImpl.setUuid("");
		}
		else {
			ercVersionedEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			ercVersionedEntryImpl.setExternalReferenceCode("");
		}
		else {
			ercVersionedEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		ercVersionedEntryImpl.setErcVersionedEntryId(ercVersionedEntryId);
		ercVersionedEntryImpl.setGroupId(groupId);
		ercVersionedEntryImpl.setCompanyId(companyId);

		ercVersionedEntryImpl.resetOriginalValues();

		return ercVersionedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		ercVersionedEntryId = objectInput.readLong();

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

		objectOutput.writeLong(ercVersionedEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public String externalReferenceCode;
	public long ercVersionedEntryId;
	public long groupId;
	public long companyId;

}
// LIFERAY-SERVICE-BUILDER-HASH:1867967546