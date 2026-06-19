/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.model.impl;

import com.liferay.audiences.model.AudiencesEntry;
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
 * The cache model class for representing AudiencesEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AudiencesEntryCacheModel
	implements CacheModel<AudiencesEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AudiencesEntryCacheModel)) {
			return false;
		}

		AudiencesEntryCacheModel audiencesEntryCacheModel =
			(AudiencesEntryCacheModel)object;

		if ((audiencesEntryId == audiencesEntryCacheModel.audiencesEntryId) &&
			(mvccVersion == audiencesEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, audiencesEntryId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", audiencesEntryId=");
		sb.append(audiencesEntryId);
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
	public AudiencesEntry toEntityModel() {
		AudiencesEntryImpl audiencesEntryImpl = new AudiencesEntryImpl();

		audiencesEntryImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			audiencesEntryImpl.setExternalReferenceCode("");
		}
		else {
			audiencesEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		audiencesEntryImpl.setAudiencesEntryId(audiencesEntryId);
		audiencesEntryImpl.setCompanyId(companyId);
		audiencesEntryImpl.setUserId(userId);

		if (userName == null) {
			audiencesEntryImpl.setUserName("");
		}
		else {
			audiencesEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			audiencesEntryImpl.setCreateDate(null);
		}
		else {
			audiencesEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			audiencesEntryImpl.setModifiedDate(null);
		}
		else {
			audiencesEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (json == null) {
			audiencesEntryImpl.setJSON("");
		}
		else {
			audiencesEntryImpl.setJSON(json);
		}

		if (name == null) {
			audiencesEntryImpl.setName("");
		}
		else {
			audiencesEntryImpl.setName(name);
		}

		audiencesEntryImpl.resetOriginalValues();

		return audiencesEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		audiencesEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		json = (String)objectInput.readObject();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(audiencesEntryId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(json);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String externalReferenceCode;
	public long audiencesEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String json;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:253194924