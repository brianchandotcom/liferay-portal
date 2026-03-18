/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.model.impl;

import com.liferay.launch.model.LaunchSet;
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
 * The cache model class for representing LaunchSet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LaunchSetCacheModel
	implements CacheModel<LaunchSet>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LaunchSetCacheModel)) {
			return false;
		}

		LaunchSetCacheModel launchSetCacheModel = (LaunchSetCacheModel)object;

		if ((launchSetId == launchSetCacheModel.launchSetId) &&
			(mvccVersion == launchSetCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, launchSetId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", launchSetId=");
		sb.append(launchSetId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", description=");
		sb.append(description);
		sb.append(", name=");
		sb.append(name);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LaunchSet toEntityModel() {
		LaunchSetImpl launchSetImpl = new LaunchSetImpl();

		launchSetImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			launchSetImpl.setUuid("");
		}
		else {
			launchSetImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			launchSetImpl.setExternalReferenceCode("");
		}
		else {
			launchSetImpl.setExternalReferenceCode(externalReferenceCode);
		}

		launchSetImpl.setLaunchSetId(launchSetId);
		launchSetImpl.setCompanyId(companyId);
		launchSetImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			launchSetImpl.setCreateDate(null);
		}
		else {
			launchSetImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			launchSetImpl.setModifiedDate(null);
		}
		else {
			launchSetImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (description == null) {
			launchSetImpl.setDescription("");
		}
		else {
			launchSetImpl.setDescription(description);
		}

		if (name == null) {
			launchSetImpl.setName("");
		}
		else {
			launchSetImpl.setName(name);
		}

		launchSetImpl.setStatus(status);
		launchSetImpl.setStatusByUserId(statusByUserId);

		if (statusDate == Long.MIN_VALUE) {
			launchSetImpl.setStatusDate(null);
		}
		else {
			launchSetImpl.setStatusDate(new Date(statusDate));
		}

		launchSetImpl.resetOriginalValues();

		return launchSetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		launchSetId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		description = objectInput.readUTF();
		name = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(launchSetId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);
		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long launchSetId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String description;
	public String name;
	public int status;
	public long statusByUserId;
	public long statusDate;

}
// LIFERAY-SERVICE-BUILDER-HASH:1201586582