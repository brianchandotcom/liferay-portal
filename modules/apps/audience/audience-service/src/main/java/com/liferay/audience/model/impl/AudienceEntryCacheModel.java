/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.model.impl;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AudienceEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AudienceEntryCacheModel
	implements CacheModel<AudienceEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AudienceEntryCacheModel)) {
			return false;
		}

		AudienceEntryCacheModel audienceEntryCacheModel =
			(AudienceEntryCacheModel)object;

		if ((audienceEntryId == audienceEntryCacheModel.audienceEntryId) &&
			(mvccVersion == audienceEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, audienceEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", audienceEntryId=");
		sb.append(audienceEntryId);
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
		sb.append(", json=");
		sb.append(json);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AudienceEntry toEntityModel() {
		AudienceEntryImpl audienceEntryImpl = new AudienceEntryImpl();

		audienceEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			audienceEntryImpl.setUuid("");
		}
		else {
			audienceEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			audienceEntryImpl.setExternalReferenceCode("");
		}
		else {
			audienceEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		audienceEntryImpl.setAudienceEntryId(audienceEntryId);
		audienceEntryImpl.setCompanyId(companyId);
		audienceEntryImpl.setUserId(userId);

		if (userName == null) {
			audienceEntryImpl.setUserName("");
		}
		else {
			audienceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			audienceEntryImpl.setCreateDate(null);
		}
		else {
			audienceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			audienceEntryImpl.setModifiedDate(null);
		}
		else {
			audienceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (json == null) {
			audienceEntryImpl.setJSON("");
		}
		else {
			audienceEntryImpl.setJSON(json);
		}

		if (name == null) {
			audienceEntryImpl.setName("");
		}
		else {
			audienceEntryImpl.setName(name);
		}

		audienceEntryImpl.resetOriginalValues();

		return audienceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		audienceEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		json = objectInput.readUTF();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(audienceEntryId);

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

		if (json == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(json);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long audienceEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String json;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1974462325